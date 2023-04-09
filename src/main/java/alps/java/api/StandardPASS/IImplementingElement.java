package alps.java.api.StandardPASS;

import java.util.Set;

public interface IImplementingElement {

    /**
     * Removes a specified interface from the set of implemented interfaces.
     *
     * @param id                 the id of the interface that should be removed
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void removeImplementedInterfaces(String id, int removeCascadeDepth);

    /**
     * Removes a specified interface from the set of implemented interfaces.
     *
     * @param id the id of the interface that should be removed
     */
    void removeImplementedInterfaces(String id);

    /**
     * Sets the set of implemented interfaces for the instance
     *
     * @param implementedInterfacesIDs The set of implemented interfaces
     */
    void setImplementedInterfacesIDReferences(Set<String> implementedInterfacesIDs);

    /**
     * Adds an implemented interface
     *
     * @param implementedInterfaceID The set of implemented interfaces
     */
    void addImplementedInterfaceIDReference(String implementedInterfaceID);

    /**
     * Removes a specified interface from the set of implemented interfaces.
     *
     * @param id the id of the interface that should be removed
     */
    void removeImplementedInterfacesIDReference(String id);

    /**
     * Returns the interfaces implemented by this instance
     *
     * @return the implemented interfaces
     */
    Set<String> getImplementedInterfacesIDReferences();
}
