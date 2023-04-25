package alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the subject base behavior class
 */
public interface ISubjectBaseBehavior extends ISubjectBehavior {
    /**
     * Get all the end states this behavior contains.
     * All these are as well listed in the overall amount of BehaviorDescribingComponents this behavior holds.
     *
     * @return A dictionary of states with their ids as keys
     */
    Map<String, IState> getEndStates();

    /**
     * Sets the set of end states for the instance
     *
     * @param endStates          The set of end states
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setEndStates(Set<IState> endStates, int removeCascadeDepth);

    /**
     * Sets the set of end states for the instance
     *
     * @param endStates The set of end states
     */
    void setEndStates(Set<IState> endStates);

    /**
     * Makes a state an end state (if it was not already).
     * Adds the state to the list of behavior describing components (if it was not contained already).
     *
     * @param endState the new end state
     */
    void registerEndState(IState endState);

    /**
     * Removes the EndState type from a specified end state.
     * Does not delete the state from the behavior, use {@link #removeBehaviorDescribingComponent(String, int)} for this
     *
     * @param id                 the id of the end state that should be removed
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void unregisterEndState(String id, int removeCascadeDepth);

    /**
     * Removes the EndState type from a specified end state.
     * Does not delete the state from the behavior, use {@link #removeBehaviorDescribingComponent(String, int)} for this
     *
     * @param id the id of the end state that should be removed
     */
    void unregisterEndState(String id);
}


