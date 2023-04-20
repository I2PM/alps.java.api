package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;

import alps.java.api.StandardPASS.IPrioritizableElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions.IReceiveTransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunctions.IDataMappingIncomingToLocal;

import java.util.Map;
import java.util.Set;

/**
 * interface of the receive transition class
 */
public interface IReceiveTransition extends ICommunicationTransition, IPrioritizableElement {
    /**
     * Adds a data mapping function (maps data contained by the message specification to the local data) to the set of mapping functions
     *
     * @param dataMappingIncomingToLocal the new mapping function
     */
    public void addDataMappingFunction(IDataMappingIncomingToLocal dataMappingIncomingToLocal);

    /**
     * Removes a data mapping function from the set of mapping functions
     *
     * @param mappingID          the id of the mapping function
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void removeDataMappingFunction(String mappingID, int removeCascadeDepth);

    /**
     * Removes a data mapping function from the set of mapping functions
     *
     * @param mappingID the id of the mapping function
     */
    public void removeDataMappingFunction(String mappingID);

    /**
     * Overrides the data mapping functions (maps data contained by the message specification to the local data)
     *
     * @param dataMappingsIncomingToLocal the new data mapping functions
     * @param removeCascadeDepth          Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setDataMappingFunctionsIncomingToLocal(Set<IDataMappingIncomingToLocal> dataMappingsIncomingToLocal, int removeCascadeDepth);

    /**
     * Overrides the data mapping functions (maps data contained by the message specification to the local data)
     *
     * @param dataMappingsIncomingToLocal the new data mapping functions
     */
    public void setDataMappingFunctionsIncomingToLocal(Set<IDataMappingIncomingToLocal> dataMappingsIncomingToLocal);

    /**
     * Gets all data mapping functions (maps data contained by the message specification to the local data) for this instance
     *
     * @return all data mapping functions
     */
    public Map<String, IDataMappingIncomingToLocal> getDataMappingFunctions();

    /**
     * Method that returns the transition condition attribute of the instance
     *
     * @return The transition condition attribute of the instance
     */
    public IReceiveTransitionCondition getTransitionCondition();

    /**
     * Method that sets the transition condition attribute of the instance
     *
     * @param condition          the transition condition
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setTransitionCondition(ITransitionCondition condition, int removeCascadeDepth);

    /**
     * Method that sets the transition condition attribute of the instance
     *
     * @param condition the transition condition
     */
    public void setTransitionCondition(ITransitionCondition condition);

}