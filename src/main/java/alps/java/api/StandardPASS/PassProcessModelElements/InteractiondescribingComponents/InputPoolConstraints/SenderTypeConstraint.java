package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IInputPoolConstraintHandlingStrategy;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraint;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents a sender type constraint
 */
public class SenderTypeConstraint extends InputPoolConstraint implements ISenderTypeConstraint {
    protected ISubject subject;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "SenderTypeConstraint";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new SenderTypeConstraint();
    }

    protected SenderTypeConstraint() {
    }

    /**
     * @param layer
     * @param labelForID
     * @param inputPoolConstraintHandlingStrategy
     * @param limit
     * @param subject
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public SenderTypeConstraint(IModelLayer layer, String labelForID, IInputPoolConstraintHandlingStrategy inputPoolConstraintHandlingStrategy,
                                int limit, ISubject subject, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, inputPoolConstraintHandlingStrategy, limit, comment, additionalLabel, additionalAttribute);

        setReferencesSubject(subject);
    }

    public SenderTypeConstraint(IModelLayer layer) {
        super(layer, null, null, 0, null, null, null);

        setReferencesSubject(null);
    }

    public void setReferencesSubject(ISubject subject, int removeCascadeDepth) {
        ISubject oldSubj = subject;
        // Might set it to null
        this.subject = subject;

        if (oldSubj != null) {
            oldSubj.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdReferences, oldSubj.getUriModelComponentID()));
        }

        if (!(subject == null)) {
            publishElementAdded(subject);
            subject.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdReferences, subject.getUriModelComponentID()));
        }
    }

    public void setReferencesSubject(ISubject subject) {
        ISubject oldSubj = subject;
        // Might set it to null
        this.subject = subject;

        if (oldSubj != null) {
            oldSubj.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdReferences, oldSubj.getUriModelComponentID()));
        }

        if (!(subject == null)) {
            publishElementAdded(subject);
            subject.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdReferences, subject.getUriModelComponentID()));
        }
    }

    public ISubject getReferenceSubject() {
        return subject;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.references) && element instanceof ISubject subject) {
                setReferencesSubject(subject);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getReferenceSubject() != null)
            baseElements.add(getReferenceSubject());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof ISubject subject && subject.equals(getReferenceSubject()))
                setReferencesSubject(null, removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof ISubject subject && subject.equals(getReferenceSubject()))
                setReferencesSubject(null, 0);
        }
    }
}
