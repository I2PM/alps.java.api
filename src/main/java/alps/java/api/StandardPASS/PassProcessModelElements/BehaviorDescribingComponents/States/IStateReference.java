package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;

/**
 * Interface to the state reference class
 */
public interface IStateReference extends IState
        {
            /**
             * Sets a state that is referenced by this state.
             * @param state The referenced state
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
            public void setReferencedState(IState state, int removeCascadeDepth);

            /**
             * Sets a state that is referenced by this state.
             * @param state The referenced state
             */
            public void setReferencedState(IState state);

            /**
             * Gets the state that is referenced by this state.
             * @return The referenced state
             */
            public IState getReferencedState();

        }
