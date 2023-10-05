package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;


import alps.java.api.ALPS.ALPSModelElements.IExtensionBehavior;
import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IFullySpecifiedSubject;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.CompatibilityDictionary;
import alps.java.api.util.ICompatibilityDictionary;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * From abstract pass ont:
 * An actor extension is a standard or abstract subject that can/should only exist on layers that extend other layers.
 * The idea of this subject is, that it extends another subject on an underlying layer
 */
public class SubjectExtension extends Subject implements ISubjectExtension {
    protected final ICompatibilityDictionary<String, ISubjectBehavior> extensionBehavior = new CompatibilityDictionary<String, ISubjectBehavior>();
    protected ISubject extendedSubj;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "SubjectExtension";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new SubjectExtension();
    }

    public SubjectExtension() {
    }

    public SubjectExtension(IModelLayer layer, String labelForID, ISubject extendedSubject, Set<ISubjectBehavior> extensionBehavior,
                            Set<IMessageExchange> incomingMessageExchange, Set<IMessageExchange> outgoingMessageExchange,
                            int maxSubjectInstanceRestriction, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, incomingMessageExchange, outgoingMessageExchange, maxSubjectInstanceRestriction, comment, additionalLabel, additionalAttribute);
        setExtendedSubject(extendedSubject);
        setExtensionBehaviors(extensionBehavior);
    }

    public SubjectExtension(IModelLayer layer) {
        super(layer, null, null, null, 1, null, null, null);
        setExtendedSubject(null);
        setExtensionBehaviors(null);
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if ((predicate.contains(OWLTags.ccontains) || predicate.contains(OWLTags.containsBehavior)) && element instanceof ISubjectBehavior behavior) {
                addExtensionBehavior(behavior);
                return true;
            }
            if (predicate.contains(OWLTags.eextends) && element instanceof ISubject subject) {
                setExtendedSubject(subject);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }


    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(PASSProcessModelElement.ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (ISubjectBehavior behavior : getExtensionBehaviors().values())
            baseElements.add(behavior);
        if (getExtendedSubject() != null)
            baseElements.add(getExtendedSubject());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IExtensionBehavior extBehavior)
                removeExtensionBehavior(extBehavior.getModelComponentID(), removeCascadeDepth);
            if (update instanceof ISubject subj && subj.equals(getExtendedSubject()))
                setExtendedSubject(null, removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IExtensionBehavior extBehavior)
                removeExtensionBehavior(extBehavior.getModelComponentID(), 0);
            if (update instanceof ISubject subj && subj.equals(getExtendedSubject()))
                setExtendedSubject(null, 0);
        }
    }

    public void addExtensionBehavior(ISubjectBehavior behavior) {
        if (behavior == null) {
            return;
        }
        if (extensionBehavior.tryAdd(behavior.getModelComponentID(), behavior)) {
            publishElementAdded(behavior);
            behavior.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdContainsBehavior, behavior.getUriModelComponentID()));
            if (extendedSubj != null && extendedSubj instanceof IFullySpecifiedSubject subj)
                subj.addBehavior(behavior);
        }
    }

    public Map<String, ISubjectBehavior> getExtensionBehaviors() {
        return new HashMap<String, ISubjectBehavior>(extensionBehavior);
    }

    public void setExtensionBehaviors(Set<ISubjectBehavior> behaviors, int removeCascadeDepth) {
        for (ISubjectBehavior behavior : getExtensionBehaviors().values()) {
            removeExtensionBehavior(behavior.getModelComponentID(), removeCascadeDepth);
        }
        if (behaviors == null) return;
        for (ISubjectBehavior behavior : behaviors) {
            addExtensionBehavior(behavior);
        }
    }

    public void setExtensionBehaviors(Set<ISubjectBehavior> behaviors) {
        for (ISubjectBehavior behavior : getExtensionBehaviors().values()) {
            removeExtensionBehavior(behavior.getModelComponentID(), 0);
        }
        if (behaviors == null) return;
        for (ISubjectBehavior behavior : behaviors) {
            addExtensionBehavior(behavior);
        }
    }

    public void removeExtensionBehavior(String id, int removeCascadeDepth) {
        if (id == null) return;
        ISubjectBehavior behavior = extensionBehavior.get(id);
        if (behavior != null) {
            extensionBehavior.remove(id);
            behavior.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdContainsBehavior, behavior.getUriModelComponentID()));
        }
    }

    public void removeExtensionBehavior(String id) {
        if (id == null) return;
        ISubjectBehavior behavior = extensionBehavior.get(id);
        if (behavior != null) {
            extensionBehavior.remove(id);
            behavior.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdContainsBehavior, behavior.getUriModelComponentID()));
        }
    }

    public void setExtendedSubject(ISubject subject, int removeCascadeDepth) {
        ISubject oldExtended = this.extendedSubj;
        // Might set it to null
        this.extendedSubj = subject;

        if (oldExtended != null) {
            if (oldExtended.equals(subject)) return;
            oldExtended.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.abstrExtends, oldExtended.getUriModelComponentID()));
            if (oldExtended instanceof IFullySpecifiedSubject subj)
                for (ISubjectBehavior behavior : getExtensionBehaviors().values()) {
                    subj.removeBehavior(behavior.getModelComponentID());
                }
        }

        if (!(subject == null)) {
            publishElementAdded(subject);
            subject.register(this);
            addTriple(new IncompleteTriple(OWLTags.abstrExtends, subject.getUriModelComponentID()));
            if (extendedSubj instanceof IFullySpecifiedSubject subj)
                for (ISubjectBehavior behavior : getExtensionBehaviors().values()) {
                    subj.addBehavior(behavior);
                }
        }
    }

    public void setExtendedSubject(ISubject subject) {
        ISubject oldExtended = this.extendedSubj;
        // Might set it to null
        this.extendedSubj = subject;

        if (oldExtended != null) {
            if (oldExtended.equals(subject)) return;
            oldExtended.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.abstrExtends, oldExtended.getUriModelComponentID()));
            if (oldExtended instanceof IFullySpecifiedSubject subj)
                for (ISubjectBehavior behavior : getExtensionBehaviors().values()) {
                    subj.removeBehavior(behavior.getModelComponentID());
                }
        }

        if (!(subject == null)) {
            publishElementAdded(subject);
            subject.register(this);
            addTriple(new IncompleteTriple(OWLTags.abstrExtends, subject.getUriModelComponentID()));
            if (extendedSubj instanceof IFullySpecifiedSubject subj)
                for (ISubjectBehavior behavior : getExtensionBehaviors().values()) {
                    subj.addBehavior(behavior);
                }
        }
    }


    public ISubject getExtendedSubject() {
        return extendedSubj;
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (extensionBehavior.containsKey(oldID)) {
            ISubjectBehavior element = extensionBehavior.get(oldID);
            extensionBehavior.remove(oldID);
            extensionBehavior.put(element.getModelComponentID(), element);
        }
        super.notifyModelComponentIDChanged(oldID, newID);
    }

}
