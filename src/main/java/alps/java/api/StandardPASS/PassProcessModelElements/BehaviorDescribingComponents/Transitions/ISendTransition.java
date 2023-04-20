package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions.ISendTransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunctions.IDataMappingLocalToOutgoing;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the send transition
 */
public interface ISendTransition extends ICommunicationTransition {
    /**
     * Adds a data mapping function (maps local data to the data contained by the message specification) to the set of mapping functions
     *
     * @param dataMappingLocalToOutgoing the new data mapping function
     */
    void addDataMappingFunction(IDataMappingLocalToOutgoing dataMappingLocalToOutgoing);

    /**
     * Removes a data mapping function from the set of mapping functions
     *
     * @param mappingID          the id of the mapping function
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void removeDataMappingFunction(String mappingID, int removeCascadeDepth);

    /**
     * Removes a data mapping function from the set of mapping functions
     *
     * @param mappingID the id of the mapping function
     */
    void removeDataMappingFunction(String mappingID);

    /**
     * Overrides the data mapping functions (maps local data to the data contained by the message specification)
     *
     * @param dataMappingsLocalToOutgoing the new data mapping functions
     * @param removeCascadeDepth          Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setDataMappingFunctionsLocalToOutgoing(Set<IDataMappingLocalToOutgoing> dataMappingsLocalToOutgoing, int removeCascadeDepth);

    /**
     * Overrides the data mapping functions (maps local data to the data contained by the message specification)
     *
     * @param dataMappingsLocalToOutgoing the new data mapping functions
     */
    void setDataMappingFunctionsLocalToOutgoing(Set<IDataMappingLocalToOutgoing> dataMappingsLocalToOutgoing);

    /**
     * Gets all data mapping functions (maps local data to the data contained by the message specification) for this instance
     *
     * @return all data mapping functions
     */
    Map<String, IDataMappingLocalToOutgoing> getDataMappingFunctions();

    /**
     * Method that returns the transition condition attribute of the instance
     *
     * @return The transition condition attribute of the instance
     */

    ISendTransitionCondition getTransitionCondition();

    /**
     * Method that sets the transition condition attribute of the instance
     *
     * @param condition          the transition condition
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setTransitionCondition(ITransitionCondition condition, int removeCascadeDepth);

    /**
     * Method that sets the transition condition attribute of the instance
     *
     * @param condition the transition condition
     */
    void setTransitionCondition(ITransitionCondition condition);
}