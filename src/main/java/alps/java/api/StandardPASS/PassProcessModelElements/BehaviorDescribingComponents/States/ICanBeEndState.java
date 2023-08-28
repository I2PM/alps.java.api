package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

public interface ICanBeEndState
{

    /**
     * Support function that allows to easily set Do/receive state to be an End State
     * Removes or adds the StateType.EndState from this state
     * Equal to remove/set Statetype(StateType.EndState) method
     * @param isEndState true= make this State an end state, false = remove end-State status
     */
         void setEndState(boolean isEndState);

    /**
     * direct way to determin the whether this state is of the EndState type.
     * @return True if this state has the StateType.EndState attribute
     */
        boolean isEndState();

}
