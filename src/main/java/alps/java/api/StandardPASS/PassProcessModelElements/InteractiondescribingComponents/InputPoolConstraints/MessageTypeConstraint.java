package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IInputPoolConstraintHandlingStrategy;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraint;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents a message type constraint
 */
public class MessageTypeConstraint extends InputPoolConstraint implements IMessageTypeConstraint {
    protected IMessageSpecification messageSpecification;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "MessageTypeConstraint";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new MessageTypeConstraint();
    }

    protected MessageTypeConstraint() {
    }

    /**
     * Constructor that creates a new fully specified instance of the message type constraint class
     *
     * @param layer
     * @param labelForID
     * @param inputPoolConstraintHandlingStrategy
     * @param limit
     * @param messageSpecification
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public MessageTypeConstraint(IModelLayer layer, String labelForID, IInputPoolConstraintHandlingStrategy inputPoolConstraintHandlingStrategy,
                                 int limit, IMessageSpecification messageSpecification, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {

        super(layer, labelForID, inputPoolConstraintHandlingStrategy, limit, comment, additionalLabel, additionalAttribute);
        setReferencedMessageSpecification(messageSpecification);

    }

    public MessageTypeConstraint(IModelLayer layer) {

        super(layer, null, null, 0, null, null, null);
        setReferencedMessageSpecification(null);

    }

    public void setReferencedMessageSpecification(IMessageSpecification messageSpecification, int removeCascadeDepth) {
        IMessageSpecification oldMessage = messageSpecification;
        // Might set it to null
        this.messageSpecification = messageSpecification;

        if (oldMessage != null) {
            if (oldMessage.equals(messageSpecification)) return;
            oldMessage.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdReferences, oldMessage.getUriModelComponentID()));
        }

        if (!(messageSpecification == null)) {
            publishElementAdded(messageSpecification);
            messageSpecification.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdReferences, messageSpecification.getUriModelComponentID()));
        }
    }

    public void setReferencedMessageSpecification(IMessageSpecification messageSpecification) {
        IMessageSpecification oldMessage = messageSpecification;
        // Might set it to null
        this.messageSpecification = messageSpecification;

        if (oldMessage != null) {
            if (oldMessage.equals(messageSpecification)) return;
            oldMessage.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdReferences, oldMessage.getUriModelComponentID()));
        }

        if (!(messageSpecification == null)) {
            publishElementAdded(messageSpecification);
            messageSpecification.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdReferences, messageSpecification.getUriModelComponentID()));
        }
    }

    public IMessageSpecification getReferencedMessageSpecification() {
        return messageSpecification;
    }


    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.references) && element instanceof IMessageSpecification specification) {
                setReferencedMessageSpecification(specification);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }


    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getReferencedMessageSpecification() != null)
            baseElements.add(getReferencedMessageSpecification());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IMessageSpecification specification && specification.equals(getReferencedMessageSpecification()))
                setReferencedMessageSpecification(null, removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IMessageSpecification specification && specification.equals(getReferencedMessageSpecification()))
                setReferencedMessageSpecification(null, 0);
        }
    }
}

