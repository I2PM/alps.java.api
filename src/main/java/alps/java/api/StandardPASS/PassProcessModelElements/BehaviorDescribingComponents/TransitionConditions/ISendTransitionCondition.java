package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions;


import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;

/**
 * Interface to the send transition condition class
 */
public interface ISendTransitionCondition extends IMessageExchangeCondition
        {
            /**
             * The different send types as an enum.
             */
            public enum SendTypes
{
    STANDARD,
    SEND_TO_NEW,
    SEND_TO_KNOWN,
    SEND_TO_ALL
}


            /**
             * Method that sets the lower bound attribute of the instance
             * @param lowerBound the lower bound
             */
    void setMultipleSendLowerBound(int lowerBound);

            /**
             * Method that returns the lower bound attribute of the instance
             * @return The lower bound attribute of the instance
             */
    int getMultipleLowerBound();

            /**
             * Method that sets the upper bound attribute of the instance
             * @param upperBound the upper bound
             */
    void setMultipleSendUpperBound(int upperBound);

            /**
             * Method that returns the upper bound attribute of the instance
             * @return The upper bound attribute of the instance
             */
    int getMultipleUpperBound();

            /**
             * Method that sets the send type attribute of the instance
             * @param sendType the send type
             */
    void setSendType(SendTypes sendType);

            /**
             * Method that returns the send type attribute of the instance
             * @return The send type attribute of the instance
             */
    SendTypes getSendType();

            /**
             * Sets the subject that must be the receiver of the {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.MessageSpecification} for this Condition to apply.
             * @param subject The corresponding receiving subject
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
    void setRequiresMessageSentTo(ISubject subject, int removeCascadeDepth);
            /**
             * Sets the subject that must be the receiver of the {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.MessageSpecification} for this Condition to apply.
             * @param subject The corresponding receiving subject
             */
            void setRequiresMessageSentTo(ISubject subject);


            /**
             * Returns the subject that must be the receiver of the {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.MessageSpecification} (specified by {@link #setRequiresSendingOfMessage(IMessageSpecification, int)} for this Condition to apply.
             * @return The corresponding receiving subject
             */
    ISubject getRequiresMessageSentTo();

            /**
             * Sets the messageSpecification that must be send for this Condition to apply.
             * @param messageSpecification The corresponding message specification
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
    void setRequiresSendingOfMessage(IMessageSpecification messageSpecification, int removeCascadeDepth);

            /**
             * Sets the messageSpecification that must be send for this Condition to apply.
             * @param messageSpecification The corresponding message specification
             */
            void setRequiresSendingOfMessage(IMessageSpecification messageSpecification);

            /**
             * Returns the messageSpecification that must be send for this Condition to apply.
             * @return The corresponding message specification
             */
    IMessageSpecification getRequiresSendingOfMessage();
}

