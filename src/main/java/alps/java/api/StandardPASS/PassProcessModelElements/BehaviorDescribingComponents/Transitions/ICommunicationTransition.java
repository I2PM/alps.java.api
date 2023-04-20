package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions.IMessageExchangeCondition;

/**
 * Interface to the communication transition class
 */
public interface ICommunicationTransition extends ITransition {
    /**
     * Method that returns the transition condition attribute of the instance
     *
     * @return The transition condition attribute of the instance
     */
    IMessageExchangeCondition getTransitionCondition();

    /**
     * Method that sets the transition condition attribute of the instance
     *
     * @param condition          the transition condition
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setTransitionCondition(ITransitionCondition condition, int removeCascadeDepth);
}
