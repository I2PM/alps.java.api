package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions;


import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;

/**
 * Interface to the receive transition condition class
 */
public interface IReceiveTransitionCondition extends IMessageExchangeCondition {

    /**
     * The different receive types as an enum.
     */
    public enum ReceiveTypes {
        STANDARD,
        RECEIVE_FROM_KNOWN,
        RECEIVE_FROM_ALL
    }

    /**
     * Method that sets the lower bound attribute of the instance
     *
     * @param lowerBound the lower bound
     */
    void setMultipleReceiveLowerBound(int lowerBound);

    /**
     * Method that returns the lower bound attribute of the instance
     *
     * @return The lower bound attribute of the instance
     */
    int getMultipleLowerBound();

    /**
     * Method that sets the upper bound attribute of the instance
     *
     * @param upperBound the upper bound
     */
    void setMultipleReceiveUpperBound(int upperBound);

    /**
     * Method that sets the receive type attribute of the instance
     *
     * @return the receive type
     */
    int getMultipleUpperBound();

    /**
     * Method that sets the receive type attribute of the instance
     *
     * @param receiveType the receive type
     */
    void setReceiveType(ReceiveTypes receiveType);

    /**
     * Method that returns the receive type attribute of the instance
     *
     * @return The receive type attribute of the instance
     */
    ReceiveTypes getReceiveType();

    /**
     * Sets the subject that must be the sender of the {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.MessageSpecification} for this Condition to apply.
     *
     * @param subject            the subject the message is sent from
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setMessageSentFrom(ISubject subject, int removeCascadeDepth);

    /**
     * Sets the subject that must be the sender of the {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.MessageSpecification} for this Condition to apply.
     *
     * @param subject the subject the message is sent from
     */
    void setMessageSentFrom(ISubject subject);

    /**
     * Returns the subject that must be the sender of the {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.MessageSpecification} for this Condition to apply
     *
     * @return The subject attribute of the instance
     */
    ISubject getMessageSentFrom();

    /**
     * Method that sets the message specification attribute of the instance
     *
     * @param messageSpecification the specification of the message
     * @param removeCascadeDepth   Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setReceptionOfMessage(IMessageSpecification messageSpecification, int removeCascadeDepth);

    /**
     * Method that sets the message specification attribute of the instance
     *
     * @param messageSpecification the specification of the message
     */
    void setReceptionOfMessage(IMessageSpecification messageSpecification);

    /**
     * Method that returns the message specification attribute of the instance
     *
     * @return The message specification attribute of the instance
     */
    IMessageSpecification getReceptionOfMessage();
}

