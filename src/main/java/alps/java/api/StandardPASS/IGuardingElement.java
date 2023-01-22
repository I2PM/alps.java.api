package alps.java.api.StandardPASS;

import java.util.Map;
import java.util.Set;

public interface IGuardingElement<T> {
    /// <summary>
    /// Sets the set of guarded elements for the instance
    /// </summary>
    /// <param name="implementedInterfaces">The set of guarded elements</param>
    /// <param name="removeCascadeDepth">Parses the depth of a cascading delete for elements that are connected to the currently deleted one</param>
    void setGuardedElements(Set<T> guardedElements, int removeCascadeDepth);

    /// <summary>
    /// Adds an guarded element
    /// </summary>
    /// <param name="implementedInterface">the new guarded element</param>
    void addGuardedElement(T guardedElement);

    /// <summary>
    /// Removes a specified guarded element from the set of guarded elements.
    /// </summary>
    /// <param name="id">the id of the guarded element that should be removed</param>
    /// <param name="removeCascadeDepth">Parses the depth of a cascading delete for elements that are connected to the currently deleted one</param>
    void removeGuardedElement(String id, int removeCascadeDepth);

    /// <summary>
    /// Returns the elements guarded by this instance
    /// </summary>
    /// <returns>the guarded elements</returns>
    Map<String, T> getGuardedElements();

    /// <summary>
    /// Sets the set of guarded elements for the instance
    /// </summary>
    /// <param name="implementedInterfacesIDs">The set of guarded elements</param>
    void setGuardedElementsIDReferences(Set<String> guardedElementsIDs);

    /// <summary>
    /// Adds an guarded element
    /// </summary>
    /// <param name="implementedInterfaceID">the new guarded element</param>
    void addGuardedElementIDReference(String guardedElementID);

    /// <summary>
    /// Removes a specified guarded element from the set of guarded elements.
    /// </summary>
    /// <param name="id">the id of the guarded element that should be removed</param>
    void removeGuardedElementIDReference(String id);

    /// <summary>
    /// Returns the elements guarded by this instance
    /// </summary>
    /// <returns>the guarded elements</returns>
    Set<String> getGuardedElementsIDReferences();
}
