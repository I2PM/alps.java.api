package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subject;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.List;
import java.util.Set;

/**
 * Class that represents an interface subject
 */
public class InterfaceSubject extends Subject implements IInterfaceSubject {
    protected IFullySpecifiedSubject referencedSubject;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "InterfaceSubject";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new InterfaceSubject();
    }

    public InterfaceSubject() {
    }

    /**
     * @param layer
     * @param labelForID
     * @param incomingMessageExchange
     * @param outgoingMessageExchange
     * @param maxSubjectInstanceRestriction
     * @param referencedSubject             If the InterfaceSubject is referencing another FullySpecifiedSubject, this can be passed here
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public InterfaceSubject(IModelLayer layer, String labelForID, Set<IMessageExchange> incomingMessageExchange,
                            Set<IMessageExchange> outgoingMessageExchange, int maxSubjectInstanceRestriction, IFullySpecifiedSubject referencedSubject,
                            String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, incomingMessageExchange, outgoingMessageExchange, maxSubjectInstanceRestriction, comment, additionalLabel, additionalAttribute);
        setReferencedSubject(referencedSubject);
    }

    public InterfaceSubject(IModelLayer layer) {
        super(layer, null, null, null, 1, null, null, null);
        setReferencedSubject(null);
    }


    public void setReferencedSubject(IFullySpecifiedSubject fullySpecifiedSubject, int removeCascadeDepth) {
        IFullySpecifiedSubject oldSubject = this.referencedSubject;
        // Might set it to null
        this.referencedSubject = fullySpecifiedSubject;

        if (oldSubject != null) {
            if (oldSubject.equals(referencedSubject)) return;
            oldSubject.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdReferences, oldSubject.getUriModelComponentID()));
        }

        // Might set it to null
        this.referencedSubject = fullySpecifiedSubject;
        if (!(fullySpecifiedSubject == null)) {
            publishElementAdded(fullySpecifiedSubject);
            fullySpecifiedSubject.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdReferences, fullySpecifiedSubject.getUriModelComponentID()));
        }
    }

    public void setReferencedSubject(IFullySpecifiedSubject fullySpecifiedSubject) {
        IFullySpecifiedSubject oldSubject = this.referencedSubject;
        // Might set it to null
        this.referencedSubject = fullySpecifiedSubject;

        if (oldSubject != null) {
            if (oldSubject.equals(referencedSubject)) return;
            oldSubject.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdReferences, oldSubject.getUriModelComponentID()));
        }

        // Might set it to null
        this.referencedSubject = fullySpecifiedSubject;
        if (!(fullySpecifiedSubject == null)) {
            publishElementAdded(fullySpecifiedSubject);
            fullySpecifiedSubject.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdReferences, fullySpecifiedSubject.getUriModelComponentID()));
        }
    }


    public IFullySpecifiedSubject getReferencedSubject() {
        return referencedSubject;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.references) && element instanceof IFullySpecifiedSubject subject) {
                setReferencedSubject(subject);
                return true;
            }
        } else if (predicate.contains(OWLTags.abstrHasSimpleSimInterfaceSubjectResponseDefinition)) {
            this.setSimpleSimInterfaceSubjectResponseDefinition(objectContent);
            return true;
        }

        //setSimpleSimInterfaceSubjectResponseDefinition
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getReferencedSubject() != null) baseElements.add(getReferencedSubject());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof ISubject subj && subj.equals(getReferencedSubject()))
                setReferencedSubject(null, removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof ISubject subj && subj.equals(getReferencedSubject()))
                setReferencedSubject(null, 0);
        }
    }

    protected String simpleSimInterfaceSubjectResponseDefinition;

    public void setSimpleSimInterfaceSubjectResponseDefinition(String simpleSimInterfaceSubjectResponseDefinitionString) {

        this.simpleSimInterfaceSubjectResponseDefinition = simpleSimInterfaceSubjectResponseDefinitionString;
    }


    public void setSimpleSimInterfaceSubjectResponseDefinition(Node simpleSimInterfaceSubjectResponseDefinition) {
        this.simpleSimInterfaceSubjectResponseDefinition = simpleSimInterfaceSubjectResponseDefinition.toString();
    }

    public String getSimpleSimInterfaceSubjectResponseDefinition() {
        return simpleSimInterfaceSubjectResponseDefinition;
    }
}
