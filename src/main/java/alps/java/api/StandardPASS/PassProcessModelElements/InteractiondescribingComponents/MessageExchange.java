package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractionDescribingComponent;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents a message exchange
 */

public class MessageExchange extends InteractionDescribingComponent implements IMessageExchange {
    protected IMessageSpecification messageType;
    protected ISubject receiver;
    protected ISubject sender;
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "MessageExchange";
    private MessageExchangeType messageExchangeType = MessageExchangeType.StandardMessageExchange;
    protected boolean isAbstractType = false;
    private final String ABSTRACT_NAME = "AbstractPASSMessageExchange";

    public void setIsAbstract(boolean isAbstract) {
        this.isAbstractType = isAbstract;
        if (isAbstract) {
            addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + ABSTRACT_NAME));
        } else {
            removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + ABSTRACT_NAME));
        }
    }

    public boolean isAbstract() {
        return isAbstractType;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new MessageExchange();
    }

    public MessageExchange() {
    }

    /**
     * @param layer
     * @param labelForID
     * @param messageSpecification
     * @param senderSubject
     * @param receiverSubject
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public MessageExchange(IModelLayer layer, String labelForID, IMessageSpecification messageSpecification, ISubject senderSubject,
                           ISubject receiverSubject, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, comment, additionalLabel, additionalAttribute);
        setMessageType(messageSpecification);
        setReceiver(receiverSubject);
        setSender(senderSubject);
    }

    /**
     * @param layer
     */
    public MessageExchange(IModelLayer layer) {
        super(layer, null, null, null, null);
        setMessageType(null);
        setReceiver(null);
        setSender(null);
    }

    public void setMessageType(IMessageSpecification messageType, int removeCascadeDepth) {
        IMessageSpecification oldSpecification = this.messageType;
        // Might set it to null
        this.messageType = messageType;

        if (oldSpecification != null) {
            if (oldSpecification.equals(messageType)) return;
            oldSpecification.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasMessageType, oldSpecification.getUriModelComponentID()));
        }

        if (!(messageType == null)) {
            publishElementAdded(messageType);
            messageType.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasMessageType, messageType.getUriModelComponentID()));
        }
    }

    public void setMessageType(IMessageSpecification messageType) {
        IMessageSpecification oldSpecification = this.messageType;
        // Might set it to null
        this.messageType = messageType;

        if (oldSpecification != null) {
            if (oldSpecification.equals(messageType)) return;
            oldSpecification.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdHasMessageType, oldSpecification.getUriModelComponentID()));
        }

        if (!(messageType == null)) {
            publishElementAdded(messageType);
            messageType.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasMessageType, messageType.getUriModelComponentID()));
        }
    }


    public void setReceiver(ISubject receiver, int removeCascadeDepth) {
        ISubject oldReceiver = this.receiver;
        // Might set it to null
        this.receiver = receiver;

        if (oldReceiver != null) {
            if (oldReceiver.equals(receiver)) return;
            oldReceiver.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasReceiver, oldReceiver.getUriModelComponentID()));
        }

        if (!(receiver == null)) {
            publishElementAdded(receiver);
            receiver.register(this);
            receiver.addIncomingMessageExchange(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasReceiver, receiver.getUriModelComponentID()));
        }
    }

    public void setReceiver(ISubject receiver) {
        ISubject oldReceiver = this.receiver;
        // Might set it to null
        this.receiver = receiver;

        if (oldReceiver != null) {
            if (oldReceiver.equals(receiver)) return;
            oldReceiver.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdHasReceiver, oldReceiver.getUriModelComponentID()));
        }

        if (!(receiver == null)) {
            publishElementAdded(receiver);
            receiver.register(this);
            receiver.addIncomingMessageExchange(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasReceiver, receiver.getUriModelComponentID()));
        }
    }


    public void setSender(ISubject sender, int removeCascadeDepth) {
        ISubject oldSender = this.sender;
        // Might set it to null
        this.sender = sender;

        if (oldSender != null) {
            if (oldSender.equals(sender)) return;
            oldSender.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasSender, oldSender.getUriModelComponentID()));
        }

        if (!(sender == null)) {
            publishElementAdded(sender);
            sender.register(this);
            sender.addOutgoingMessageExchange(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasSender, sender.getUriModelComponentID()));
        }
    }

    public void setSender(ISubject sender) {
        ISubject oldSender = this.sender;
        // Might set it to null
        this.sender = sender;

        if (oldSender != null) {
            if (oldSender.equals(sender)) return;
            oldSender.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdHasSender, oldSender.getUriModelComponentID()));
        }

        if (!(sender == null)) {
            publishElementAdded(sender);
            sender.register(this);
            sender.addOutgoingMessageExchange(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasSender, sender.getUriModelComponentID()));
        }
    }

    public IMessageSpecification getMessageType() {
        return messageType;
    }


    public ISubject getReceiver() {
        return receiver;
    }


    public ISubject getSender() {
        return sender;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.hasMessageType) && element instanceof IMessageSpecification specification) {
                setMessageType(specification);
                return true;
            }

            //int i = getAdditionalAttribute().IndexOf(allElements[s].getModelComponentID());

            else if (element instanceof ISubject subject) {
                if (predicate.contains(OWLTags.hasReceiver)) {
                    setReceiver(subject);
                    return true;
                } else if (predicate.contains(OWLTags.hasSender)) {
                    setSender(subject);
                    return true;
                }


            }
        }
        if (predicate.contains(OWLTags.type)) {
            // Console.WriteLine(" - parsing object content transition type: " + objectContent);

            if (objectContent.toLowerCase().contains(MessageExchangeType.FinalizedMessageExchange.toString().toLowerCase())) {
                setMessageExchangeType(MessageExchangeType.FinalizedMessageExchange);
                setIsAbstract(true);
                return true;
            } else if (objectContent.toLowerCase().contains(MessageExchangeType.AbstractMessageExchange.toString().toLowerCase())) {
                setMessageExchangeType(MessageExchangeType.AbstractMessageExchange);
                setIsAbstract(true);
                return true;
            } else if (objectContent.contains(ABSTRACT_NAME)) {
                setIsAbstract(true);
                return true;
            }


        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (specification == ConnectedElementsSetSpecification.ALL || specification == ConnectedElementsSetSpecification.TO_ADD) {
            if (getReceiver() != null) baseElements.add(getReceiver());
            if (getSender() != null) baseElements.add(getSender());
            if (getMessageType() != null) baseElements.add(getMessageType());
        }
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof ISubject subj) {
                if (subj.equals(getReceiver()))
                    setReceiver(null, removeCascadeDepth);
                if (subj.equals(getSender()))
                    setSender(null, removeCascadeDepth);
            }
            if (update instanceof IMessageSpecification specification && specification.equals(getMessageType()))
                setMessageType(null, removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof ISubject subj) {
                if (subj.equals(getReceiver()))
                    setReceiver(null, 0);
                if (subj.equals(getSender()))
                    setSender(null, 0);
            }
            if (update instanceof IMessageSpecification specification && specification.equals(getMessageType()))
                setMessageType(null, 0);
        }
    }

    public void setMessageExchangeType(MessageExchangeType type) {
        this.messageExchangeType = type;
    }

    public MessageExchangeType getMessageExchangeType() {
        return this.messageExchangeType;
    }
}