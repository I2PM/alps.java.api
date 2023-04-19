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

import java.util.List;
import java.util.Set;

/**
 * Class that represents a receive transition condition
 */
public class ReceiveTransitionCondition extends MessageExchangeCondition implements IReceiveTransitionCondition {
    /**
     * Used to parse send types from this library to a new owl on export
     */
    private final String[] receiveTypeOWLExportNames = {
            OWLTags.stdReceiveTypeStandard,
            OWLTags.stdReceiveTypeReceiveFromKnown,
            OWLTags.stdReceiveTypeReceiveFromAll
    };

    /**
     * Used to parse send types from owl to this library
     */
    private final String[] receiveTypeOWLNames = {
            OWLTags.receiveTypeStandard,
            OWLTags.receiveTypeReceiveFromKnown,
            OWLTags.receiveTypeReceiveFromAll
    };


    protected int lowerBound;
    protected int upperBound;
    protected ReceiveTypes receiveType;
    protected ISubject messageSentFromSubject;
    protected IMessageSpecification receptionOfMessage;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ReceiveTransitionCondition";

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ReceiveTransitionCondition();
    }

    protected ReceiveTransitionCondition() {
    }

    @Override
    public String getClassName() {
        return className;
    }

    public ReceiveTransitionCondition(ITransition transition, String labelForID, String toolSpecificDefintion, IMessageExchange messageExchange,
                                      int upperBound, int lowerBound, ReceiveTypes receiveType, ISubject requiredMessageSendFromSubject,
                                      IMessageSpecification requiresReceptionOfMessage, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(transition, labelForID, toolSpecificDefintion, messageExchange, comment, additionalLabel, additionalAttribute);
        setMultipleReceiveLowerBound(lowerBound);
        setMultipleReceiveUpperBound(upperBound);
        setReceiveType(receiveType);
        setMessageSentFrom(requiredMessageSendFromSubject);
        setReceptionOfMessage(requiresReceptionOfMessage);

        // TODO specification, subject, exchange redundand
    }

    public ReceiveTransitionCondition(ITransition transition) {
        super(transition, null, null, null, null, null, null);
        setMultipleReceiveLowerBound(0);
        setMultipleReceiveUpperBound(0);
        setReceiveType(ReceiveTypes.STANDARD);
        setMessageSentFrom(null);
        setReceptionOfMessage(null);

        // TODO specification, subject, exchange redundand
    }

    public void setMultipleReceiveLowerBound(int lowerBound) {
        if (this.lowerBound == lowerBound) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasMultiReceiveLowerBound, Integer.toString(this.lowerBound),
                IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
        this.lowerBound = (lowerBound > 0) ? lowerBound : 1;
        addTriple(new IncompleteTriple(OWLTags.stdHasMultiReceiveLowerBound, Integer.toString(lowerBound),
                IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
    }


    public void setMultipleReceiveUpperBound(int upperBound) {
        if (this.upperBound == upperBound) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasMultiReceiveUpperBound, Integer.toString(this.upperBound),
                IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
        this.upperBound = (upperBound > 0) ? upperBound : 1;
        addTriple(new IncompleteTriple(OWLTags.stdHasMultiReceiveUpperBound, Integer.toString(upperBound),
                IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
    }


    public void setReceiveType(ReceiveTypes receiveType) {
        ReceiveTypes oldType = this.receiveType;
        this.receiveType = receiveType;

        if (oldType.equals(receiveType)) return;

        removeTriple(new IncompleteTriple(OWLTags.stdHasReceiveType, receiveTypeOWLExportNames[(int) oldType]));
        addTriple(new IncompleteTriple(OWLTags.stdHasReceiveType, receiveTypeOWLExportNames[(int) receiveType]));
    }


    public void setMessageSentFrom(ISubject subject, int removeCascadeDepth) {
        ISubject oldSubj = this.messageSentFromSubject;
        // Might set it to null
        this.messageSentFromSubject = subject;

        if (oldSubj != null) {
            if (oldSubj.equals(subject)) return;
            oldSubj.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdRequiresMessageSentFrom, oldSubj.getUriModelComponentID()));
        }

        if (!(subject == null)) {
            publishElementAdded(subject);
            subject.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdRequiresMessageSentFrom, subject.getUriModelComponentID()));
        }
    }

    public void setMessageSentFrom(ISubject subject) {
        ISubject oldSubj = this.messageSentFromSubject;
        // Might set it to null
        this.messageSentFromSubject = subject;

        if (oldSubj != null) {
            if (oldSubj.equals(subject)) return;
            oldSubj.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdRequiresMessageSentFrom, oldSubj.getUriModelComponentID()));
        }

        if (!(subject == null)) {
            publishElementAdded(subject);
            subject.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdRequiresMessageSentFrom, subject.getUriModelComponentID()));
        }
    }

    public void setReceptionOfMessage(IMessageSpecification messageSpecification, int removeCascadeDepth) {
        IMessageSpecification oldSpec = this.receptionOfMessage;
        // Might set it to null
        this.receptionOfMessage = messageSpecification;

        if (oldSpec != null) {
            if (oldSpec.equals(messageSpecification)) return;
            oldSpec.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdRequiresReceptionOfMessage, oldSpec.getUriModelComponentID()));
        }

        if (!(messageSpecification == null)) {
            publishElementAdded(messageSpecification);
            messageSpecification.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdRequiresReceptionOfMessage, messageSpecification.getUriModelComponentID()));
        }
    }

    public void setReceptionOfMessage(IMessageSpecification messageSpecification) {
        IMessageSpecification oldSpec = this.receptionOfMessage;
        // Might set it to null
        this.receptionOfMessage = messageSpecification;

        if (oldSpec != null) {
            if (oldSpec.equals(messageSpecification)) return;
            oldSpec.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdRequiresReceptionOfMessage, oldSpec.getUriModelComponentID()));
        }

        if (!(messageSpecification == null)) {
            publishElementAdded(messageSpecification);
            messageSpecification.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdRequiresReceptionOfMessage, messageSpecification.getUriModelComponentID()));
        }
    }

    public int getMultipleLowerBound() {
        return lowerBound;
    }


    public int getMultipleUpperBound() {
        return upperBound;
    }


    public ReceiveTypes getReceiveType() {
        return receiveType;
    }


    public ISubject getMessageSentFrom() {
        return messageSentFromSubject;
    }


    public IMessageSpecification getReceptionOfMessage() {
        return receptionOfMessage;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (predicate.contains(OWLTags.hasMultiReceiveLowerBound)) {
            String tmpLowerBound = objectContent;
            setMultipleReceiveLowerBound(int.Parse(tmpLowerBound));
            return true;
        } else if (predicate.contains(OWLTags.hasMultiReceiveUpperBound)) {
            String tmpUpperBound = objectContent;
            setMultipleReceiveUpperBound(int.Parse(tmpUpperBound));
            return true;
        } else if (predicate.contains(OWLTags.hasReceiveType)) {
            for (int i : Enum.GetValues(typeof(ReceiveTypes))) {
                if (objectContent.contains(receiveTypeOWLNames[i])) {
                    setReceiveType((ReceiveTypes) i);
                    return true;
                }
            }
        } else if (element != null) {


            if (predicate.contains(OWLTags.requiresMessageSentFrom) && element instanceof ISubject subject) {
                setMessageSentFrom(subject);
                return true;
            }
            if (predicate.contains(OWLTags.requiresReceptionOfMessage) && element instanceof IMessageSpecification messageSpecification) {
                setReceptionOfMessage(messageSpecification);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getMessageSentFrom() != null)
            baseElements.add(getMessageSentFrom());
        if (getReceptionOfMessage() != null)
            baseElements.add(getReceptionOfMessage());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof ISubject subj && subj.equals(getMessageSentFrom()))
                setMessageSentFrom(null, removeCascadeDepth);
            if (update instanceof IMessageSpecification specification && specification.equals(getReceptionOfMessage()))
                setReceptionOfMessage(null, removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof ISubject subj && subj.equals(getMessageSentFrom())) setMessageSentFrom(null, 0);
            if (update instanceof IMessageSpecification specification && specification.equals(getReceptionOfMessage()))
                setReceptionOfMessage(null, 0);
        }
    }
}