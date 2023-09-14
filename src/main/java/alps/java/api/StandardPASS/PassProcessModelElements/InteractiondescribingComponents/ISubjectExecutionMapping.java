package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.IInteractionDescribingComponent;

/**
 *  A separte object containing a (most likely) String representation of some formal description
 * of how this subject is mapped to the users of an execution envoironment (whos is allowed to be responsible)
 * Theoretically there is a difference between static mapping (e.g. a subject mapped to a user group) and
 * dynamic mapping where the mapping is determined during runtim to e.g., find the current boss for one user
 * for non of these things formal definitions exist yet.
 */

public interface ISubjectExecutionMapping extends IInteractionDescribingComponent {
    String getExecutionMappingDefinition();

    /**
     * Set the string that SHOULD contain The definition of how this subject is to be mapped to
     * the users of an execution enviroment
     * @param executionMappingDefintion No specific format has been developed for this yet
     */
    void setExecutionMappingDefinition(String executionMappingDefintion);

    SubjectExecutionMappingTypes getexecutionMappingType();
    SubjectExecutionMappingTypes setexecutionMappingType();


    /**
     * To represent the different types of mapping
     */
    public enum SubjectExecutionMappingTypes
{
    GeneralExecutionMapping,
    StaticExecutionMapping,
    DynamicExecutionMapping
}

}
