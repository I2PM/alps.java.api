package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.IDataDescribingComponent;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the data type definition class
 * A datatype contains some data object definition.
 * I.e. a complex datatype might be "student" containing 3 String fields: "name", "sirname" and "university".
 * A data object definitions would be "John", "Doe", "KIT", with the datatype "student".
 * The datatype is defining the structure, the data object is the instance
 */
public interface IDataTypeDefinition extends IDataDescribingComponent {
    /**
     * Overrides the current data object definitions
     *
     * @param dataObjectDefinitons the new definitions
     * @param removeCascadeDepth   Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setContainsDataObjectDefintions(Set<IDataObjectDefinition> dataObjectDefinitons, int removeCascadeDepth);

    /**
     * Overrides the current data object definitions
     *
     * @param dataObjectDefinitons the new definitions
     */
    public void setContainsDataObjectDefintions(Set<IDataObjectDefinition> dataObjectDefinitons);

    /**
     * Adds a data object definition to the set of definitions
     *
     * @param dataObjectDefiniton the new data object definition
     */
    public void addContainsDataObjectDefintion(IDataObjectDefinition dataObjectDefiniton);

    /**
     * Returns all data object definitions
     *
     * @return all definitions
     */
    public Map<String, IDataObjectDefinition> getDataObjectDefinitons();

    /**
     * Removes a data object definition from the set of definitions
     *
     * @param modelComponentID   the id of the definition
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void removeDataObjectDefiniton(String modelComponentID, int removeCascadeDepth);

    /**
     * Removes a data object definition from the set of definitions
     *
     * @param modelComponentID the id of the definition
     */
    public void removeDataObjectDefiniton(String modelComponentID);
}
