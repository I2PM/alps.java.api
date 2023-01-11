package alps.net.api.StandardPASS;

import java.util.Map;
import java.util.Set;

public interface IImplementingElementT<T extends IPASSProcessModelElement> extends IImplementingElement {
    /// <summary>
    /// Sets the set of implemented interfaces for the instance
    /// </summary>
    /// <param name="implementedInterfaces">The set of implemented interfaces</param>
    /// <param name="removeCascadeDepth">Parses the depth of a cascading delete for elements that are connected to the currently deleted one</param>
    void setImplementedInterfaces(Set<T> implementedInterfaces, int removeCascadeDepth);

    /// <summary>
    /// Adds an implemented interface
    /// </summary>
    /// <param name="implementedInterface">the new interface</param>
    void addImplementedInterface(T implementedInterface);

    /// <summary>
    /// Returns the interfaces implemented by this instance
    /// </summary>
    /// <returns>the implemented interfaces</returns>
    Map<String, T> getImplementedInterfaces();

}


