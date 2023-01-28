package alps.java.api.StandardPASS;

import java.util.Map;
import java.util.Set;

/**
 * An interface for classes that can (in a PASS context) implement other PASS objects which act as interfaces.
 * @param <T> The type of the implemented classes, usually the type of the implementing class itself
 */
public interface IImplementingElementT<T extends IPASSProcessModelElement> extends IImplementingElement {
    /**
     * Sets the set of implemented interfaces for the instance
     * @param implementedInterfaces The set of implemented interfaces
     * @param removeCascadeDepth >Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setImplementedInterfaces(Set<T> implementedInterfaces, int removeCascadeDepth);

    /**
     * dds an implemented interface
     * @param implementedInterface the new interface
     */
    void addImplementedInterface(T implementedInterface);

    /**
     * Returns the interfaces implemented by this instance
     * @return the implemented interfaces
     */
    Map<String, T> getImplementedInterfaces();

}


