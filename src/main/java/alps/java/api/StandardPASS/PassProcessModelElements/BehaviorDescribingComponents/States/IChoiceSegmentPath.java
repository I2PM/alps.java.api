package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.util.IContainableElement;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the choice segment path class
 */
public interface IChoiceSegmentPath extends IState, IContainableElement<IChoiceSegment>
    {


        /**
         * Sets an ending state for the choice segment path
         * @param state an ending state
         * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
         */
            void setEndState(IState state, int removeCascadeDepth);

        /**
         * Sets an ending state for the choice segment path
         * @param state an ending state
         */
        void setEndState(IState state);

        /**
         * Returns the ending state for the choice segment path
         * @return the ending stat
         */
        IState getEndState();

        /**
         * Sets an initial state for the choice segment path
         * @param state an initial state
         * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
         */
            void setInitialState(IState state, int removeCascadeDepth);

        /**
         * Sets an initial state for the choice segment path
         * @param state an initial state
         */
        void setInitialState(IState state);

        /**
         * Returns the initial state for the choice segment path
         * @return the initial state
         */
        IState getInitialState();

        /**
         * Sets whether the path is optional to end or not
         * @param endChoice whether the path is optional to end or not
         */
        void setIsOptionalToEndChoiceSegmentPath(boolean endChoice);

        /**
         * Returns whether the path is optional to end or not
         * @return whether the path is optional to end or not
         */
        boolean getIsOptionalToEndChoiceSegmentPath();

        /**
         * Sets whether the path is optional to start or not
         * @param endChoice whether the path is optional to start or not
         */
        void setIsOptionalToStartChoiceSegmentPath(boolean endChoice);

        /**
         * Returns whether the path is optional to start or not
         * @return whether the path is optional to start or not
         */
            boolean getIsOptionalToStartChoiceSegmentPath();

        /**
         * Overrides the set of states contained in the choice segment path
         * @param containedStates the new states
         * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
         */
            void setContainedStates(Set<IState> containedStates, int removeCascadeDepth);

        /**
         * Overrides the set of states contained in the choice segment path
         * @param containedStates the new states
         */
        void setContainedStates(Set<IState> containedStates);

        /**
         * Adds a state to the choice segment path
         * @param containedState the new state
         */
        void addContainedState(IState containedState);

        /**
         * Gets all contained states
         * @return all states
         */
        Map<String, IState> getContainedStates();

        /**
         * Removes a state from the path
         * @param id the id of the state
         * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
         */
        void removeContainedState(String id, int removeCascadeDepth);
        /**
         * Removes a state from the path
         * @param id the id of the state
         */
        void removeContainedState(String id);



        }