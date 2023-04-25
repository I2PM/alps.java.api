package alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the GuardBehavior class
 */

public interface IGuardBehavior extends ISubjectBehavior {
    /**
     * Overrides the behaviors that are guarded by this GuardBehavior
     *
     * @param behaviors          the new set of guarded behaviors
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setGuardedBehaviors(Set<ISubjectBehavior> behaviors, int removeCascadeDepth);

    /**
     * Overrides the behaviors that are guarded by this GuardBehavior
     *
     * @param behaviors the new set of guarded behaviors
     */
    void setGuardedBehaviors(Set<ISubjectBehavior> behaviors);

    /**
     * Adds a behavior to the set of guarded behaviors
     *
     * @param behavior the new guarded behavior
     */
    public void addGuardedBehavior(ISubjectBehavior behavior);

    /**
     * Returns all behaviors that are guarded by this instance
     *
     * @return A set of behaviors
     */
    public Map<String, ISubjectBehavior> getGuardedBehaviors();

    /**
     * Removes a behavior from the set of guarded behaviors
     *
     * @param id
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void removeGuardedBehavior(String id, int removeCascadeDepth);

    /**
     * Removes a behavior from the set of guarded behaviors
     *
     * @param id
     */
    public void removeGuardedBehavior(String id);

    /**
     * Overrides the states that are guarded by this GuardBehavior
     *
     * @param guardedStates      the new set of guarded states
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setGuardedStates(Set<IState> guardedStates, int removeCascadeDepth);

    /**
     * Overrides the states that are guarded by this GuardBehavior
     *
     * @param guardedStates the new set of guarded states
     */
    public void setGuardedStates(Set<IState> guardedStates);

    /**
     * Adds a state to the set of guarded states
     *
     * @param state the new guarded state
     */
    public void addGuardedState(IState state);

    /**
     * Returns all states that are guarded by this instan
     *
     * @return A set of states
     */
    public Map<String, IState> getGuardedStates();

    /**
     * Removes a state from the set of guarded states
     *
     * @param id                 the id of the state that is guarded
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void removeGuardedState(String id, int removeCascadeDepth);

    /**
     * Removes a state from the set of guarded states
     *
     * @param id the id of the state that is guarded
     */
    public void removeGuardedState(String id);

}


