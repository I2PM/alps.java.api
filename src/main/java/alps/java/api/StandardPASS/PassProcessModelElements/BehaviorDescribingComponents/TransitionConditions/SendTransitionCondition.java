package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions;


import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Class that represents a send transition condition
 */
public class SendTransitionCondition extends MessageExchangeCondition implements ISendTransitionCondition {


    /**
     * Used to parse send types from this library to a new owl on export
     */
    private final String[] sendTypeOWLExportNames = {
            OWLTags.stdSendTypeStandard,
            OWLTags.stdSendTypeSendToNew,
            OWLTags.stdSendTypeSendToKnown,
            OWLTags.stdSendTypeSendToAll
    };

    /**
     * Used to parse send types from owl to this library
     */
    private final String[] sendTypeOWLNames = {
            OWLTags.sendTypeStandard,
            OWLTags.sendTypeSendToNew,
            OWLTags.sendTypeSendToKnown,
            OWLTags.sendTypeSendToAll
    };

    protected int lowerBound;
    protected int upperBound;
    protected SendTypes sendType;
    protected ISubject messageSentTo;
    protected IMessageSpecification messageSpecification;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "SendTransitionCondition";

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new SendTransitionCondition();
    }

    public SendTransitionCondition() {
    }

    @Override
    public String getClassName() {
        return className;
    }


    public SendTransitionCondition(ITransition transition, String labelForID, String toolSpecificDefintion,
                                   IMessageExchange messageExchange, int upperBound, int lowerBound, SendTypes sendType,
                                   ISubject messageSentFromSubject, IMessageSpecification receptionOfMessage,
                                   String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(transition, labelForID, toolSpecificDefintion, messageExchange, comment, additionalLabel, additionalAttribute);
        setMultipleSendLowerBound(lowerBound);
        setMultipleSendUpperBound(upperBound);
        setSendType(sendType);
        setRequiresMessageSentTo(messageSentFromSubject);
        setRequiresSendingOfMessage(receptionOfMessage);
    }

    public SendTransitionCondition(ITransition transition) {
        super(transition, null, null, null, null, null, null);
        setMultipleSendLowerBound(0);
        setMultipleSendUpperBound(0);
        setSendType(SendTypes.STANDARD);
        setRequiresMessageSentTo(null);
        setRequiresSendingOfMessage(null);
    }

    public void setMultipleSendLowerBound(int lowerBound) {
        if (lowerBound == this.lowerBound) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasMultiSendLowerBound, Integer.toString(this.lowerBound),
                IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
        this.lowerBound = (lowerBound > 0) ? lowerBound : 1;
        addTriple(new IncompleteTriple(OWLTags.stdHasMultiSendLowerBound, Integer.toString(lowerBound),
                IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));

    }


    public void setMultipleSendUpperBound(int upperBound) {
        if (upperBound == this.upperBound) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasMultiSendUpperBound, Integer.toString(this.upperBound),
                IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
        this.upperBound = (upperBound > 0) ? upperBound : 1;
        addTriple(new IncompleteTriple(OWLTags.stdHasMultiSendUpperBound, Integer.toString(upperBound),
                IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
    }


    public void setSendType(SendTypes sendType) {
        //TODO: Fehlermeldung hier
        SendTypes oldType = this.sendType;
        this.sendType = sendType;

        if (oldType.equals(sendType)) return;

        removeTriple(new IncompleteTriple(OWLTags.stdHasSendType, sendTypeOWLExportNames[(int) oldType.ordinal()]));
        addTriple(new IncompleteTriple(OWLTags.stdHasSendType, sendTypeOWLExportNames[(int) sendType.ordinal()]));

    }


    public void setRequiresMessageSentTo(ISubject subject, int removeCascadeDepth) {
        ISubject oldSubj = this.messageSentTo;
        // Might set it to null
        this.messageSentTo = subject;

        if (oldSubj != null) {
            if (oldSubj.equals(subject)) return;
            oldSubj.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdRequiresMessageSentTo, oldSubj.getUriModelComponentID()));
        }

        if (!(subject == null)) {
            publishElementAdded(subject);
            subject.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdRequiresMessageSentTo, subject.getUriModelComponentID()));
        }
    }

    public void setRequiresMessageSentTo(ISubject subject) {
        ISubject oldSubj = this.messageSentTo;
        // Might set it to null
        this.messageSentTo = subject;

        if (oldSubj != null) {
            if (oldSubj.equals(subject)) return;
            oldSubj.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdRequiresMessageSentTo, oldSubj.getUriModelComponentID()));
        }

        if (!(subject == null)) {
            publishElementAdded(subject);
            subject.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdRequiresMessageSentTo, subject.getUriModelComponentID()));
        }
    }

    public void setRequiresSendingOfMessage(IMessageSpecification messageSpecification, int removeCascadeDepth) {
        IMessageSpecification oldSpec = this.messageSpecification;
        // Might set it to null
        this.messageSpecification = messageSpecification;

        if (oldSpec != null) {
            if (oldSpec.equals(messageSpecification)) return;
            oldSpec.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdRequiresSendingOfMessage, oldSpec.getUriModelComponentID()));
        }

        if (!(messageSpecification == null)) {
            publishElementAdded(messageSpecification);
            messageSpecification.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdRequiresSendingOfMessage, messageSpecification.getUriModelComponentID()));
        }
    }

    public void setRequiresSendingOfMessage(IMessageSpecification messageSpecification) {
        IMessageSpecification oldSpec = this.messageSpecification;
        // Might set it to null
        this.messageSpecification = messageSpecification;

        if (oldSpec != null) {
            if (oldSpec.equals(messageSpecification)) return;
            oldSpec.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdRequiresSendingOfMessage, oldSpec.getUriModelComponentID()));
        }

        if (!(messageSpecification == null)) {
            publishElementAdded(messageSpecification);
            messageSpecification.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdRequiresSendingOfMessage, messageSpecification.getUriModelComponentID()));
        }
    }


    public int getMultipleLowerBound() {
        return lowerBound;
    }


    public int getMultipleUpperBound() {
        return upperBound;
    }


    public SendTypes getSendType() {
        return sendType;
    }


    public ISubject getRequiresMessageSentTo() {
        return messageSentTo;
    }


    public IMessageSpecification getRequiresSendingOfMessage() {
        return messageSpecification;
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getRequiresMessageSentTo() != null)
            baseElements.add(getRequiresMessageSentTo());
        if (getRequiresSendingOfMessage() != null)
            baseElements.add(getRequiresSendingOfMessage());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof ISubject subj && subj.equals(getRequiresMessageSentTo()))
                setRequiresMessageSentTo(null, removeCascadeDepth);
            if (update instanceof IMessageSpecification specification && specification.equals(getRequiresSendingOfMessage()))
                setRequiresSendingOfMessage(null, removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof ISubject subj && subj.equals(getRequiresMessageSentTo()))
                setRequiresMessageSentTo(null, 0);
            if (update instanceof IMessageSpecification specification && specification.equals(getRequiresSendingOfMessage()))
                setRequiresSendingOfMessage(null, 0);
        }
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (predicate.contains(OWLTags.hasMultiSendLowerBound)) {
            String lower = objectContent;
            setMultipleSendLowerBound(Integer.parseInt(lower));
            return true;
        } else if (predicate.contains(OWLTags.hasMultiSendUpperBound)) {
            String upper = objectContent;
            setMultipleSendUpperBound(Integer.parseInt(upper));
            return true;
        } else if (predicate.contains(OWLTags.hasSendType)) {
            for (SendTypes sendType : EnumSet.allOf(SendTypes.class)) {
                if (objectContent.contains(sendTypeOWLNames[(int) sendType.ordinal()])) {
                    setSendType(sendType);
                    return true;
                }
            }


        } else if (element != null) {
            if (predicate.contains(OWLTags.requiresMessageSentTo) && element instanceof ISubject subject) {
                setRequiresMessageSentTo(subject);
                return true;
            }

            if (predicate.contains(OWLTags.requiresSendingOfMessage) && element instanceof IMessageSpecification messageSpecification) {
                setRequiresSendingOfMessage(messageSpecification);
                return true;
            }


        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }


}
