package alps.java.LibraryExample;

/**
 * This is an interface for all classes that do not belong to the standard library
 * It helps the factory to differentiate between library and non-library classes
 */
public interface IAdditionalFunctionalityElement {
    String getAdditionalFunctionality();

    void setAdditionalFunctionality(String additionalFunctionality);
}
