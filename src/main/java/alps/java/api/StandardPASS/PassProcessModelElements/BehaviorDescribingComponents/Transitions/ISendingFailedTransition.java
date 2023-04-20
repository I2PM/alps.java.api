package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.ISendState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions.ISendingFailedCondition;

/**
 * Interface to the sending failed transition
 */
public interface ISendingFailedTransition extends ITransition {
// No new xml tags, only part of the signature changed

    /**
     * Method that sets the source state (where the transition is coming from)
     *
     * @param source             the source state
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setSourceState(IState source, int removeCascadeDepth);

    /**
     * Method that sets the source state (where the transition is coming from)
     *
     * @param source the source state
     */
    public void setSourceState(IState source);

    /**
     * Method that returns the source state (where the transition is coming from)
     *
     * @return The source state attribute of the instance
     */
    public ISendState getSourceState();

    /**
     * Method that sets the transition condition attribute of the instance
     *
     * @param sendingFailedCondition the transition condition
     * @param removeCascadeDepth     Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setTransitionCondition(ITransitionCondition sendingFailedCondition, int removeCascadeDepth);

    /**
     * Method that sets the transition condition attribute of the instance
     *
     * @param sendingFailedCondition the transition condition
     */
    public void setTransitionCondition(ITransitionCondition sendingFailedCondition);

    /**
     * Method that returns the transition condition attribute of the instance
     *
     * @return The transition condition attribute of the instance
     */
    public ISendingFailedCondition getTransitionCondition();

}