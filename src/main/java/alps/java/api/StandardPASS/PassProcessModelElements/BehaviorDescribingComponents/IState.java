package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.StandardPASS.IImplementingElement;
import alps.java.api.StandardPASS.IImplementingElementT;
import alps.java.api.StandardPASS.PassProcessModelElements.IBehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.util.IHasSimple2DVisualizationBox;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the state class
 */
public interface IState extends IBehaviorDescribingComponent, IImplementingElementT<IState>, IHasSimple2DVisualizationBox {
    /**
     * Method that sets the incoming transition attribute of the instance
     *
     * @param transition incoming transition attribute
     */
    void addIncomingTransition(ITransition transition);

    /**
     * Method that returns the incoming transition attribute of the instance
     *
     * @return The incoming transition attribute of the instance
     */
    Map<String, ITransition> getIncomingTransitions();

    /**
     * Method that sets the outgoing transition attribute of the instance
     *
     * @param transition outgoing transition attribute
     */
    void addOutgoingTransition(ITransition transition);

    /**
     * Method that returns the outgoing transition attribute of the instance
     *
     * @return The outgoing transition attribute of the instance
     */
    Map<String, ITransition> getOutgoingTransitions();

    /**
     * Method that sets the function specification attribute of the instance
     *
     * @param functionSpecification function specification attribute
     * @param removeCascadeDepth    Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setFunctionSpecification(IFunctionSpecification functionSpecification, int removeCascadeDepth);

    /**
     * Method that sets the function specification attribute of the instance
     *
     * @param functionSpecification function specification attribute
     */
    void setFunctionSpecification(IFunctionSpecification functionSpecification);

    /**
     * Method that returns the function specification attribute of the instance
     *
     * @return The function specification attribute of the instance
     */
    IFunctionSpecification getFunctionSpecification();

    /**
     * Method that sets the guard behavior attribute of the instance
     *
     * @param guardBehavior      guard behavior attribute
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setGuardBehavior(IGuardBehavior guardBehavior, int removeCascadeDepth);

    /**
     * Method that sets the guard behavior attribute of the instance
     *
     * @param guardBehavior guard behavior attribute
     */
    void setGuardBehavior(IGuardBehavior guardBehavior);

    /**
     * Method that returns the guard behavior attribute of the instance
     *
     * @return The guard behavior attribute of the instance
     */
    IGuardBehavior getGuardBehavior();

    /**
     * Method that returns the action attribute (describing the bundle of state and outgoing transitions).
     * No setter exists, because the action is atomatically created and should not be modified.
     *
     * @return The action attribute of the instance
     */
    IAction getAction();

    /**
     * Checks if the state is of the given type
     *
     * @param stateType the specified type
     * @return true if the state is of this type
     */
    boolean isStateType(StateType stateType);

    /**
     * ets a new type for this state.
     * This must not override the old type, a state can have multiple types at once.
     * Used to make state i.e. an EndState, declared finalized, abstract...
     *
     * @param stateType the new state type
     */
    void setIsStateType(StateType stateType);

    /**
     * Removes a type from the list of types this state currently is of.
     *
     * @param stateType the type that is removed
     */
    void removeStateType(StateType stateType);

    /**
     * Represent different types the state can be of.
     * A state can have several types at onc
     */
    public enum StateType {
        EndState,
        InitialStateOfBehavior,
        InitialStateOfChoiceSegmentPath,
        Abstract,
        Finalized
    }

    /**
     * Overrides the outgoing transitions for the state
     *
     * @param transitions        The new outgoing transitions
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setOutgoingTransitions(Set<ITransition> transitions, int removeCascadeDepth);

    /**
     * Overrides the outgoing transitions for the state
     *
     * @param transitions The new outgoing transitions
     */
    public void setOutgoingTransitions(Set<ITransition> transitions);

    /**
     * Overrides the incoming transitions for the state
     *
     * @param transitions        The new incoming transitions
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setIncomingTransitions(Set<ITransition> transitions, int removeCascadeDepth);

    /**
     * Overrides the incoming transitions for the state
     *
     * @param transitions The new incoming transitions
     */
    public void setIncomingTransitions(Set<ITransition> transitions);

    /**
     * Deletes a transition from the outgoing transitions
     *
     * @param modelCompID        The id of the outgoing transition
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void removeOutgoingTransition(String modelCompID, int removeCascadeDepth);

    /**
     * Deletes a transition from the outgoing transitions
     *
     * @param modelCompID The id of the outgoing transition
     */
    public void removeOutgoingTransition(String modelCompID);

    /**
     * Deletes a transition from the incoming transitions
     *
     * @param modelCompID        The id of the incoming transition
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void removeIncomingTransition(String modelCompID, int removeCascadeDepth);

    /**
     * Deletes a transition from the incoming transitions
     *
     * @param modelCompID The id of the incoming transition
     */
    public void removeIncomingTransition(String modelCompID);
}
