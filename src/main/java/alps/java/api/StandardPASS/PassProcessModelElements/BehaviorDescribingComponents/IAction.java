package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.IBehaviorDescribingComponent;

import java.util.Map;

/**
 * Interface that represents an action. This is a construct used in the ontology, but is only present here to guarantee a correct standard.
 * A user should not create own actions, they will be created automatically when creating a state.
 * They are only used for export, so there are no writing methods provided to the user.
 * However, when imported, the correct actions should be loaded and parsed correctly.
 */

public interface IAction extends IBehaviorDescribingComponent
        {

            /**
             * Returns the state attribute of the action class
             * @return The state attribute of the action class
             */
        IState getState();

            /**
             * Returns the outgoing transitions that are connected to the state
             * @return The outgoing transitions
             */
            public Map<String, ITransition> getContainedTransitions();

        }
