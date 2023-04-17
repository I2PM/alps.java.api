package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.IBehaviorDescribingComponent;

/**
 * Interface to the FunctionSpecification class
 */
public interface IFunctionSpecification extends IBehaviorDescribingComponent {
    /**
     * Sets a tool-specific Definition
     *
     * @param toolSpecificDefinition a tool-specific Definition
     */
    void setToolSpecificDefinition(String toolSpecificDefinition);

    /**
     * Returns the tool-specific Definition
     *
     * @return the tool-specific Definition
     */
    String getToolSpecificDefinition();

}
