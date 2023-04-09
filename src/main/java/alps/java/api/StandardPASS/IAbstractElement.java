package alps.java.api.StandardPASS;

/**
 * Interface for elements that can be abstract
 */
public interface IAbstractElement {
    /**
     * Marks/Unmarks the element as abstrac
     *
     * @param isAbstract whether the element is abstract or not
     */
    void setIsAbstract(boolean isAbstract);

    /**
     * Checks whether the element is abstract or not
     *
     * @return the result of the check
     */
    boolean isAbstract();
}
