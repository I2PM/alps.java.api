package alps.net.api.StandardPASS.PassProcessModelElements;

import alps.net.api.ALPS;
import alps.net.api.StandardPASS.IImplementingElement;
import alps.net.api.StandardPASS.IPASSProcessModelElement;
import alps.net.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.net.api.parsing;
import alps.net.api.parsing.IPASSGraph;

import java.util.Map;
import java.util.Set;


/// <summary>
/// Interface of the pass process model class
/// </summary>
public interface IPASSProcessModel extends IPASSProcessModelElement, IImplementingElement<IPASSProcessModel>
    {

            /// <summary>
            /// Sets the base uri for the model.
            /// This uri is important for the exporter to function properly.
            /// Every element inside the model will be parsed with this specified uri.
            /// </summary>
            /// <param name="baseURI">the base uri</param>
            void setBaseURI(String baseURI);

            /// <summary>
            /// Returns the base graph behind the model.
            /// This graph collects all information held by the model in the form of nodes and triples.
            /// This information is redundand for anyone who has access to the model, but is used for exporting the model to owl.
            /// </summary>
            /// <returns>The underlying graph</returns>
            IPASSGraph getBaseGraph();

            /// <summary>
            /// Sets whether the model is layered or not
            /// </summary>
            void setIsLayered(boolean layered);

            /// <summary>
            /// Sets whether the model is layered or not
            /// </summary>
            /// <returns>true if layered, false if not</returns>
            boolean isLayered();


            // ######################## StartSubject methods ########################

            /// <summary>
            /// Method that sets the start subject attribute of the instance
            /// </summary>
            /// <param name="startSubject">the new start subject</param>
            void addStartSubject(ISubject startSubject);

            /// <summary>
            /// Method that overrides the current set of start subjects
            /// </summary>
            /// <param name="startSubjects"></param>
            void setStartSubjects(Set<ISubject> startSubjects, int removeCascadeDepth);

        /// <summary>
        /// Method that removes a specified subject as start subject.
        /// Does NOT remove the element from the model completely
        /// </summary>
        /// <param name="id"></param>
        /// <param name="removeCascadeDepth">Parses the depth of a cascading delete for elements that are connected to the currently deleted one</param>
        void removeStartSubject(String id, int removeCascadeDepth);

        /// <summary>
        /// Method that returns the dictionary of all start subjects
        /// </summary>
        /// <returns>The known start subjects</returns>
        Map<String, ISubject> getStartSubjects();

        // ######################## All contained elements methods ########################

        /// <summary>
        /// Adds a <see cref="IPASSProcessModelElement"/> to the model
        /// </summary>
        /// <param name="pASSProcessModelElement">the new model element</param>
        /// <param name="layerID">the layer it should be added to.
        /// If null, the element will be added to the base (default) layer</param>
        void addElement(IPASSProcessModelElement pASSProcessModelElement, String layerID);

        /// <summary>
        /// Overrides the model elements currently contained by the model.
        /// </summary>
        /// <param name="elements">The new model elements</param>
        /// <param name="removeCascadeDepth">Parses the depth of a cascading delete for elements that are connected to the currently deleted one</param>
        void setAllElements(Set<IPASSProcessModelElement> elements, int removeCascadeDepth);

        /// <summary>
        /// Returns a dictionary containing all known PASSProcessModelElements (in the current context) mapped with their model component id.
        /// </summary>
        /// <returns>The dict of all PASSProcessModelElements</returns>
        Map<String, IPASSProcessModelElement> getAllElements();

        /// <summary>
        /// Removes a <see cref="IPASSProcessModelElement"/> specified by its id
        /// </summary>
        /// <param name="modelComponentID">the model component id of the element that should be removed</param>
        /// <param name="removeCascadeDepth">Parses the depth of a cascading delete for elements that are connected to the currently deleted one</param>
        void removeElement(String modelComponentID, int removeCascadeDepth);

        // ######################## Contained layer methods ########################

        /// <summary>
        /// Adds a new model Layer to the model
        /// </summary>
        /// <param name="modelLayer">The new model layer</param>
        void addLayer(IModelLayer modelLayer);

        /// <summary>
        /// Overrides the layers currently contained by the model.
        /// </summary>
        /// <param name="modelLayers">The new model layers</param>
        /// <param name="removeCascadeDepth">Parses the depth of a cascading delete for elements that are connected to the currently deleted one</param>
        void setLayers(Set<IModelLayer> modelLayers, int removeCascadeDepth);

        /// <summary>
        /// Removes a model layer specified by its model component id
        /// </summary>
        /// <param name="id">the model component id of the layer</param>
        /// <param name="removeCascadeDepth">Parses the depth of a cascading delete for elements that are connected to the currently deleted one</param>
        void removeLayer(String id, int removeCascadeDepth = 0);

        /// <summary>
        /// Returns a dictionary containing all known Model layers (in the current context) mapped with their model component id.
        /// </summary>
        /// <returns>The dict of all model layers</returns>
        IDictionary<String, IModelLayer> getModelLayers();

        /// <summary>
        /// Returns the current base layer (the standard layer of the model)
        /// </summary>
        /// <returns>The current base layer</returns>
        IModelLayer getBaseLayer();

        /// <summary>
        /// Sets a layer as the base layer for this model.
        /// The base layer is the standard layer, and should not extend any other layers.
        /// </summary>
        /// <param name="layer">The model layer</param>
        void setBaseLayer(IModelLayer layer);

        /// <summary>
        /// Exports the current model to the specified path using the underlying OWLGraph and TripleStore.
        /// The result is a owl/rdf file at the specified location.
        /// </summary>
        /// <param name="filepath">The specified location for saving the file</param>
        /// <returns>The absolute path the file was written to.</returns>
        String export(String filepath);
        }

