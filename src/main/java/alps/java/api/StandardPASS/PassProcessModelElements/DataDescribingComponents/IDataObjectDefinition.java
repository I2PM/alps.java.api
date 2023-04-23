package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.IDataDescribingComponent;

/**
 * Interace to the data object definition class
 * A data object belongs to exactly one data type
 * I.e. a complex datatype might be "student" containing 3 String fields: "name", "sirname" and "university".
 * A data object definitions would be "John", "Doe", "KIT", with the datatype "student".
 * The datatype is defining the structure, the data object is the instance
 */
public interface IDataObjectDefinition extends IDataDescribingComponent {
    /**
     * Sets the datatype definition for the data object definition
     *
     * @param dataTypeDefintion  the datatype definition
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setDataTypeDefinition(IDataTypeDefinition dataTypeDefintion, int removeCascadeDepth);

    /**
     * Sets the datatype definition for the data object definition
     *
     * @param dataTypeDefintion the datatype definition
     */
    void setDataTypeDefinition(IDataTypeDefinition dataTypeDefintion);

    /**
     * Returns the datatype definition for the data object definition
     *
     * @return the datatype definition
     */
    IDataTypeDefinition getDataTypeDefinition();
}