package alps.java.api.StandardPASS.PassProcessModelElements;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IImplementingElementT;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.parsing.IPASSGraph;

import java.util.Map;
import java.util.Set;


/**
 * Interface of the pass process model class
 */
public interface IPASSProcessModel extends IPASSProcessModelElement, IImplementingElementT<IPASSProcessModel>
    {

        /**
         * Sets the base uri for the model.
         * This uri is important for the exporter to function properly.
         * Every element inside the model will be parsed with this specified uri.
         * @param baseURI the base uri
         */
        void setBaseURI(String baseURI);

        /**
         * Returns the base graph behind the model.
         * This graph collects all information held by the model in the form of nodes and triples.
         * This information is redundand for anyone who has access to the model, but is used for exporting the model to owl.
         * @return The underlying graph
         */
        IPASSGraph getBaseGraph();

        /**
         * Sets whether the model is layered or not
         * @param layered
         */
        void setIsLayered(boolean layered);

        /**
         * Sets whether the model is layered or not
         * @return true if layered, false if not
         */
        boolean isLayered();


            // ######################## StartSubject methods ########################

        /**
         * Method that sets the start subject attribute of the instance
         * @param startSubject the new start subject
         */
        void addStartSubject(ISubject startSubject);

        /**
         * Method that overrides the current set of start subjects
         * @param startSubjects
         * @param removeCascadeDepth
         */
            void setStartSubjects(Set<ISubject> startSubjects, int removeCascadeDepth);

        /**
         * Method that overrides the current set of start subjects
         * @param startSubjects
         */
        void setStartSubjects(Set<ISubject> startSubjects);

        /**
         * Method that removes a specified subject as start subject.
         * Does NOT remove the element from the model completely
         * @param id
         * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
         */
        void removeStartSubject(String id, int removeCascadeDepth);

        /**
         * Method that removes a specified subject as start subject.
         * Does NOT remove the element from the model completely
         * @param id
         */
        void removeStartSubject(String id);

        /**
         * Method that returns the dictionary of all start subjects
         * @return The known start subjects
         */
        Map<String, ISubject> getStartSubjects();

        // ######################## All contained elements methods ########################

        /**
         * Adds a {@link IPASSProcessModelElement} to the model
         * @param pASSProcessModelElement the new model element
         * @param layerID the layer it should be added to.
         * If null, the element will be added to the base (default) layer
         */
        void addElement(IPASSProcessModelElement pASSProcessModelElement, String layerID);

        /**
         * Adds a {@link IPASSProcessModelElement} to the model
         * @param pASSProcessModelElement the new model element
         * If null, the element will be added to the base (default) layer
         */
        void addElement(IPASSProcessModelElement pASSProcessModelElement);

        /**
         * Overrides the model elements currently contained by the model.
         * @param elements The new model elements
         * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
         */
        void setAllElements(Set<IPASSProcessModelElement> elements, int removeCascadeDepth);

        /**
         * Overrides the model elements currently contained by the model.
         * @param elements The new model elements
         */
        void setAllElements(Set<IPASSProcessModelElement> elements);

        /**
         * Returns a Map containing all known PASSProcessModelElements (in the current context) mapped with their model component id.
         * @return
         */
        Map<String, IPASSProcessModelElement> getAllElements();

        /**
         *  Removes a {@link IPASSProcessModelElement} specified by its id
         * @param modelComponentID the model component id of the element that should be removed
         * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
         */
        void removeElement(String modelComponentID, int removeCascadeDepth);

        /**
         *  Removes a {@link IPASSProcessModelElement} specified by its id
         * @param modelComponentID the model component id of the element that should be removed
         */
        void removeElement(String modelComponentID);

        // ######################## Contained layer methods ########################

        /**
         * Adds a new model Layer to the model
         * @param modelLayer The new model layer
         */
        void addLayer(IModelLayer modelLayer);

        /**
         * Overrides the layers currently contained by the model.
         * @param modelLayers The new model layers
         * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
         */
        void setLayers(Set<IModelLayer> modelLayers, int removeCascadeDepth);

        /**
         * Overrides the layers currently contained by the model.
         * @param modelLayers The new model layers
         */
        void setLayers(Set<IModelLayer> modelLayers);

        /**
         *  Removes a model layer specified by its model component id
         * @param id the model component id of the layer
         * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
         */
        void removeLayer(String id, int removeCascadeDepth);

        /**
         *  Removes a model layer specified by its model component id
         * @param id the model component id of the layer
         */
        void removeLayer(String id);

        /**
         * Returns a map containing all known Model layers (in the current context) mapped with their model component id.
         * @return The map of all model layers
         */
        Map<String, IModelLayer> getModelLayers();

        /**
         * Returns the current base layer (the standard layer of the model)
         * @return The current base layer
         */
        IModelLayer getBaseLayer();

        /**
         * Sets a layer as the base layer for this model.
         * The base layer is the standard layer, and should not extend any other layers.
         * @param layer The model layer
         */
        void setBaseLayer(IModelLayer layer);

        /**
         *  Exports the current model to the specified path using the underlying OWLGraph and TripleStore.
         * The result is a owl/rdf file at the specified location.
         * @param filepath The specified location for saving the file
         * @return The absolute path the file was written to.
         */
        String export(String filepath);
        }

