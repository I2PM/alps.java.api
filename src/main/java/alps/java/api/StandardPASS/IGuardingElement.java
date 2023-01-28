package alps.java.api.StandardPASS;

import java.util.Map;
import java.util.Set;

public interface IGuardingElement<T> {
    /**
     * Sets the set of guarded elements for the instance
     * @param guardedElements The set of guarded elements
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setGuardedElements(Set<T> guardedElements, int removeCascadeDepth);

    /**
     * Adds an guarded element
     * @param guardedElement the new guarded element
     */
    void addGuardedElement(T guardedElement);

    /**
     * Removes a specified guarded element from the set of guarded elements.
     * @param id the id of the guarded element that should be removed
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void removeGuardedElement(String id, int removeCascadeDepth);

    /**
     * Returns the elements guarded by this instance
     * @return the guarded elements
     */
    Map<String, T> getGuardedElements();

    /**
     * Sets the set of guarded elements for the instance
     * @param guardedElementsIDs The set of guarded elements
     */
    void setGuardedElementsIDReferences(Set<String> guardedElementsIDs);

    /**
     * Adds an guarded element
     * @param guardedElementID the new guarded element
     */
    void addGuardedElementIDReference(String guardedElementID);

    /**
     * Removes a specified guarded element from the set of guarded elements.
     * @param id the id of the guarded element that should be removed
     */
    void removeGuardedElementIDReference(String id);

    /**
     * Returns the elements guarded by this instance
     * @return the guarded element
     */
    Set<String> getGuardedElementsIDReferences();
}
