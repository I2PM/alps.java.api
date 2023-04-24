package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IInputPoolConstraintHandlingStrategy;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraint;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents an message sender type constraint
 */

public class MessageSenderTypeConstraint extends InputPoolConstraint implements IMessageSenderTypeConstraint {
    protected IMessageSpecification messageSpecification;
    protected ISubject subject;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "MessageSenderTypeConstraint";

    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new MessageSenderTypeConstraint();
    }

    protected MessageSenderTypeConstraint() {
    }

    /**
     * @param layer
     * @param labelForID
     * @param inputPoolConstraintHandlingStrategy
     * @param limit
     * @param messageSpecification
     * @param subject
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public MessageSenderTypeConstraint(IModelLayer layer, String labelForID, IInputPoolConstraintHandlingStrategy inputPoolConstraintHandlingStrategy,
                                       int limit, IMessageSpecification messageSpecification, ISubject subject, String comment,
                                       String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, inputPoolConstraintHandlingStrategy, limit, comment, additionalLabel, additionalAttribute);
        setReferencedMessageSpecification(messageSpecification);
        setReferencedSubject(subject);
    }

    public MessageSenderTypeConstraint(IModelLayer layer) {
        super(layer, null, null, 0, null, null, null);
        setReferencedMessageSpecification(null);
        setReferencedSubject(null);
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

    public void setReferencedSubject(ISubject subject, int removeCascadeDepth) {
        ISubject oldSubj = subject;
        // Might set it to null
        this.subject = subject;

        if (oldSubj != null) {
            if (oldSubj.equals(subject)) return;
            oldSubj.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdReferences, oldSubj.getUriModelComponentID()));
        }

        if (!(subject == null)) {
            publishElementAdded(subject);
            subject.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdReferences, subject.getUriModelComponentID()));
        }
    }

    public void setReferencedSubject(ISubject subject) {
        ISubject oldSubj = subject;
        // Might set it to null
        this.subject = subject;

        if (oldSubj != null) {
            if (oldSubj.equals(subject)) return;
            oldSubj.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdReferences, oldSubj.getUriModelComponentID()));
        }

        if (!(subject == null)) {
            publishElementAdded(subject);
            subject.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdReferences, subject.getUriModelComponentID()));
        }
    }

    public IMessageSpecification getReferencedMessageSpecification() {
        return messageSpecification;
    }


    public ISubject getReferencedSubject() {
        return subject;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.references) && element instanceof IMessageSpecification specification) {
                setReferencedMessageSpecification(specification);
                return true;
            }

            if (predicate.contains(OWLTags.references) && element instanceof ISubject subject) {
                setReferencedSubject(subject);
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
        if (getReferencedSubject() != null)
            baseElements.add(getReferencedSubject());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IMessageSpecification specification && specification.equals(getReferencedMessageSpecification()))
                setReferencedMessageSpecification(null, removeCascadeDepth);
            if (update instanceof ISubject subject && subject.equals(getReferencedSubject()))
                setReferencedSubject(null, removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IMessageSpecification specification && specification.equals(getReferencedMessageSpecification()))
                setReferencedMessageSpecification(null, 0);
            if (update instanceof ISubject subject && subject.equals(getReferencedSubject()))
                setReferencedSubject(null, 0);
        }
    }
}
