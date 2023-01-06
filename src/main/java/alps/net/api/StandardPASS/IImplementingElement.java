package alps.net.api.StandardPASS;

import java.util.Map;
import java.util.Set;

        public interface IImplementingElement {
            /// <summary>
            /// Removes a specified interface from the set of implemented interfaces.
            /// </summary>
            /// <param name="id">the id of the interface that should be removed</param>
            /// <param name="removeCascadeDepth">Parses the depth of a cascading delete for elements that are connected to the currently deleted one</param>
            void removeImplementedInterfaces(String id, int removeCascadeDepth);

            /// <summary>
            /// Sets the set of implemented interfaces for the instance
            /// </summary>
            /// <param name="implementedInterfacesIDs">The set of implemented interfaces</param>
            void setImplementedInterfacesIDReferences(Set<String> implementedInterfacesIDs);

            /// <summary>
            /// Adds an implemented interface
            /// </summary>
            /// <param name="implementedInterfaceID">the new interface</param>
            void addImplementedInterfaceIDReference(String implementedInterfaceID);

            /// <summary>
            /// Removes a specified interface from the set of implemented interfaces.
            /// </summary>
            /// <param name="id">the id of the interface that should be removed</param>
            void removeImplementedInterfacesIDReference(String id);

            /// <summary>
            /// Returns the interfaces implemented by this instance
            /// </summary>
            /// <returns>the implemented interfaces</returns>
            Set<String> getImplementedInterfacesIDReferences();
        }

        /// <summary>
        /// An interface for classes that can (in a PASS context) implement other PASS objects which act as interfaces.
        /// </summary>
        /// <typeparam name="T">The type of the implemented classes, usually the type of the implementing class itself</typeparam>
        public interface IImplementingElement<T extends IPASSProcessModelElement> extends IImplementingElement {
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


