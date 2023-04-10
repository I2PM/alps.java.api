package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.IInteractionDescribingComponent;

/**
 * Interface to the InputPoolConstraint class
 */
public interface IInputPoolConstraint extends IInteractionDescribingComponent {
    /**
     * Sets the handling strategy for the input pool contstraint (how to handle incoming messages)
     *
     * @param inputPoolConstraintHandlingStrategy the handling strategy
     * @param removeCascadeDepth                  Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setInputPoolConstraintHandlingStrategy(IInputPoolConstraintHandlingStrategy inputPoolConstraintHandlingStrategy, int removeCascadeDepth);

    /**
     * Sets the handling strategy for the input pool contstraint (how to handle incoming messages)
     *
     * @param inputPoolConstraintHandlingStrategy the handling strategy
     */
    void setInputPoolConstraintHandlingStrategy(IInputPoolConstraintHandlingStrategy inputPoolConstraintHandlingStrategy);


    /**
     * returns the current handling strategy for the input pool constraint (how to handle incoming messages)
     *
     * @return the handling strategy
     */
    IInputPoolConstraintHandlingStrategy getInputPoolConstraintHandlingStrategy();

    /**
     * Sets a limit for the input pool constraint
     *
     * @param nonNegativInteger the new limit
     */
    void setLimit(int nonNegativInteger);

    /**
     * Returns the current limit for the input pool constraint
     *
     * @return the current limit
     */
    int getLimit();

}
