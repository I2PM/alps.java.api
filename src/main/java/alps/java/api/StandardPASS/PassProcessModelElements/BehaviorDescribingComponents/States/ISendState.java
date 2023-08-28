package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications.ISendFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions.ISendTransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions.ISendingFailedTransition;
import alps.java.api.util.IHasSiSiCostPerExecution;
import alps.java.api.util.IHasSiSiDistribution;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the send type class
 */
public interface ISendState extends IStandardPASSState, IHasSiSiDistribution.IHasDuration, IHasSiSiCostPerExecution {

    /**
     * Method that sets the send transition attribute of the instance
     *
     * @param sendTransition     the send transition
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setSendTransition(ISendTransition sendTransition, int removeCascadeDepth);

    /**
     * Method that sets the send transition attribute of the instance
     *
     * @param sendTransition the send transition
     */
    void setSendTransition(ISendTransition sendTransition);

    /**
     * Method that returns the send transition attribute of the instance
     *
     * @return The send transition attribute of the instance
     */
    ISendTransition getSendTransition();

    /**
     * Method that adds a sending failed transition to the set of sending failed transitions
     *
     * @param sendingFailedTransition the transition that is executed when the sending of a message fails
     */
    void addSendingFailedTransition(ISendingFailedTransition sendingFailedTransition);

    /**
     * Removes a sending failed transition of the state
     *
     * @param id                   the id of the transition
     * @param removeCascadingDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void removeSendingFailedTransition(String id, int removeCascadingDepth);

    /**
     * Removes a sending failed transition of the state
     *
     * @param id the id of the transition
     */
    void removeSendingFailedTransition(String id);

    /**
     * Overrides all sending failed transitions for the state
     *
     * @param transitions        the transition that are executed when the sending of a message fails
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setSendingFailedTransitions(Set<ISendingFailedTransition> transitions, int removeCascadeDepth);

    /**
     * Overrides all sending failed transitions for the state
     *
     * @param transitions the transition that are executed when the sending of a message fails
     */
    void setSendingFailedTransitions(Set<ISendingFailedTransition> transitions);

    /**
     * Method that sets the sending failed transition attribute of the instance
     *
     * @return The sending failed transition attribute of the instance
     */
    Map<String, ISendingFailedTransition> getSendingFailedTransitions();

    /**
     * Gets the function specification for the current state
     *
     * @return the function specification
     */
    ISendFunction getFunctionSpecification();

    /**
     * Sets the function specification  for the current state
     *
     * @param specification      the function specification
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setFunctionSpecification(IFunctionSpecification specification, int removeCascadeDepth);

    /**
     * Sets the function specification  for the current state
     *
     * @param specification the function specification
     */
    void setFunctionSpecification(IFunctionSpecification specification);

}