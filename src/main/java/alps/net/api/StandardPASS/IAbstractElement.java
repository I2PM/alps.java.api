package alps.net.api.StandardPASS;

/// <summary>
/// Interface for elements that can be abstract
/// </summary>
public interface IAbstractElement {
    /// <summary>
    /// Marks/Unmarks the element as abstract
    /// </summary>
    /// <param name="isAbstract">whether the element is abstract or not</param>
    void setIsAbstract(boolean isAbstract);

    /// <summary>
    /// Checks whether the element is abstract or not
    /// </summary>
    /// <returns>the result of the check</returns>
    boolean isAbstract();
}
