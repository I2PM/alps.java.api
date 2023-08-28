package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications.IReceiveFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;

/**
 * Interface of the receive state class
 */
public interface IReceiveState extends IStandardPASSState, ICanBeEndState {
    /**
     * Method that sets the receive function attribute of the instance
     *
     * @param specification      the function specification
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setFunctionSpecification(IFunctionSpecification specification, int removeCascadeDepth);

    /**
     * Method that sets the receive function attribute of the instance
     *
     * @param specification the function specification
     */
    void setFunctionSpecification(IFunctionSpecification specification);

    /**
     * Method that returns the receive function attribute of the instance
     *
     * @return The receive function attribute of the instance
     */
    IReceiveFunction getFunctionSpecification();

    /**
     * To Define whether the waiting time here is factored into the cost calculation
     * (e.g. if the subject carrier can use the time otherwise,
     * this value is 0% and waiting is not factored into the active time and cost
     * for the subject exectuion.With a value of 100% the subject carrier is considered
     * to be waiting actively and may not do other tasks therefore costing the time
     * @return
     */
    double getSisiBilledWaitingTime();

    /**
     * To Define whether the waiting time here is factored into the cost calculation
     * (e.g. if the subject carrier can use the time otherwise,
     * this value is 0% and waiting is not factored into the active time and cost
     * for the subject exectuion. With a value of 100% the subject carrier is considered
     * to be waiting actively and may not do other tasks therefore costing the time
     * @param billedWaitingTime value must be between 0 and 1
     */
    void setSiSiBilledWaitingTime(double billedWaitingTime);


    /**
     * Support Function that allows to easily set this Do State to be an End State
     * Removes or adds the StateType.EndState from this states end States
     * @param isEndState true= make this State an end state, false = remove end-State status
     */
    void setEndState(boolean isEndState);
}