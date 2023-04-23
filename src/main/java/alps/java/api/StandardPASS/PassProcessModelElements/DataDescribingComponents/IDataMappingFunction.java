package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents;


import alps.java.api.StandardPASS.PassProcessModelElements.IDataDescribingComponent;

/**
 * Interface to the data mapping function class
 */
public interface IDataMappingFunction extends IDataDescribingComponent {
    /**
     * Sets the data mapping string for the data mapping function
     *
     * @param dataMappingString the data mapping string
     */
    void setDataMappingString(String dataMappingString);

    /**
     * Returns the data mapping string
     *
     * @return the data mapping string
     */
    String getDataMappingString();

    /**
     * Sets the feel expression string for the data mapping function
     *
     * @param FeelExpression the feel expression
     */
    void setFeelExpressionAsDataMapping(String FeelExpression);

    /**
     * Returns the feel expression string for the data mapping function
     *
     * @return the feel expression
     */
    String getFeelExpressionAsDataMapping();

    /**
     * Sets a tool specific definition for the data mapping function
     *
     * @param toolSpecificDefinition a tool specific definition
     */
    void setToolSpecificDefinition(String toolSpecificDefinition);

    /**
     * Returns the tool specific definition for the data mapping function
     *
     * @return a tool specific definition
     */
    String getToolSpecificDefinition();

    /**
     * Checks whether the instance has a toolspecific definition
     *
     * @return true if it contains a definition
     */
    boolean hasToolSpecificDefinition();

    /**
     * Checks whether the instance has a toolspecific definition
     *
     * @return true if it contains a definition
     */
    public boolean hasFeelExpression();
}



