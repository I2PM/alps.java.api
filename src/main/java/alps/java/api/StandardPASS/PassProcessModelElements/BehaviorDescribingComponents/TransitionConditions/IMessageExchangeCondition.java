package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;

/**
 * Interface to the message exchange condition class
 */
public interface IMessageExchangeCondition extends ITransitionCondition {
    /**
     * Sets the message exchange that is required to be sent for this condition to apply
     *
     * @param messageExchange    The message exchange
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setRequiresPerformedMessageExchange(IMessageExchange messageExchange, int removeCascadeDepth);

    /**
     * Sets the message exchange that is required to be sent for this condition to apply
     *
     * @param messageExchange The message exchange
     */
    void setRequiresPerformedMessageExchange(IMessageExchange messageExchange);

    /**
     * Gets the message exchange that is required to be sent for this condition to apply
     *
     * @return the message exchange
     */
    IMessageExchange getRequiresPerformedMessageExchange();

}

