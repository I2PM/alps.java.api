package alps.java.api.StandardPASS.PassProcessModelElements;

import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents.ISubjectExtension;
import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents.SubjectExtension;
import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents.SubjectExtensions.GuardExtension;
import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents.SubjectExtensions.MacroExtension;
import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.ALPS.ALPSModelElements.ModelLayer;
import alps.java.api.FunctionalityCapsules.IImplementsFunctionalityCapsule;
import alps.java.api.FunctionalityCapsules.ImplementsFunctionalityCapsule;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IStateReference;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IFullySpecifiedSubject;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IMacroBehavior;
import alps.java.api.parsing.IPASSGraph;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.parsing.PASSGraph;
import alps.java.api.util.*;
import org.apache.jena.graph.Triple;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/// <summary>
/// Class that represents a PASS Process Model
/// This model contains all elements known in the current context.
/// </summary>
public class PASSProcessModel extends PASSProcessModelElement, implements IPASSProcessModel {
    /// <summary>
    /// All elements held by the model (sum of all elements held by the layers)
    /// </summary>
    protected ICompatibilityDictionary<String, IPASSProcessModelElement> allModelElements = new CompatibilityDictionary<String, IPASSProcessModelElement>();

    /// <summary>
    /// A default layer, created when an element is added but no layer is specified.
    /// Might be null
    /// </summary>
    protected IModelLayer baseLayer;

    protected ICompatibilityDictionary<String, ISubject> startSubjects = new CompatibilityDictionary<String, ISubject>();
    protected String baseURI;
    protected IPASSGraph baseGraph;
    protected boolean layered;
    protected final IImplementsFunctionalityCapsule<IPASSProcessModel> implCapsule;

    /// <summary>
    /// Name of the class, needed for parsing
    /// </summary>
    private final String className = "PASSProcessModel";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new PASSProcessModel();
    }

    protected PASSProcessModel() {
        implCapsule = new ImplementsFunctionalityCapsule<IPASSProcessModel>(this);
    }

    /// <summary>
    /// Constructor that creates a new fully specified instance of the pass process modell class
    /// </summary>
    public PASSProcessModel(String baseURI, String labelForID, Set<IMessageExchange> messageExchanges, Set<ISubject> relationsToModelComponent,
                            Set<ISubject> startSubject, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(labelForID, comment, additionalLabel, additionalAttribute);
        implCapsule = new ImplementsFunctionalityCapsule<IPASSProcessModel>(this);
        if (!(baseURI == null || baseURI.equals(""))) {
            setBaseURI(baseURI);
        }

        if (relationsToModelComponent != null)
            for (ISubject subj : relationsToModelComponent)
                addElement(subj);
        if (messageExchanges != null)
            for (IMessageExchange mExch : messageExchanges)
                addElement(mExch);
        setStartSubjects(startSubject);
    }

    public PASSProcessModel(String baseURI) {
        super(null, null, null, null);
        implCapsule = new ImplementsFunctionalityCapsule<IPASSProcessModel>(this);
        if (!(baseURI == null || baseURI.equals(""))) {
            setBaseURI(baseURI);
        }


        setStartSubjects(null);
    }


    // ######################## All contained elements methods ########################


    public Map<String, IPASSProcessModelElement> getAllElements() {
        return new HashMap<String, IPASSProcessModelElement>(allModelElements);
    }

    public void setAllElements(Set<IPASSProcessModelElement> elements, int removeCascadeDepth) {
        for (IPASSProcessModelElement element : this.getAllElements().values()) {
            removeElement(element.getModelComponentID(), removeCascadeDepth);
        }
        if (elements == null) return;
        for (IPASSProcessModelElement element : elements) {
            addElement(element);
        }
    }


    //TODO: Count
    public void addElement(IPASSProcessModelElement pASSProcessModelElement, String layerID) {
        if (pASSProcessModelElement == null) return;


        // Check for layer, create default if non matches

        if (justAddElement(pASSProcessModelElement)) {

            if (pASSProcessModelElement instanceof IModelLayer) {
                if (getModelLayers().Count > 1) setIsLayered(true);

            } else {
                if (pASSProcessModelElement instanceof IInteractionDescribingComponent || pASSProcessModelElement instanceof ISubjectBehavior) {
                    if (layerID == null) {
                        layerID = getBaseLayer().getModelComponentID();
                    }
                    justAddElementToLayer(pASSProcessModelElement, layerID);
                    if (pASSProcessModelElement instanceof ISubject) {
                        ISubject subj = (ISubject) pASSProcessModelElement;
                    }
                    if (subj.isRole(ISubject.Role.StartSubject) {
                        addStartSubject(subj);
                    }
                }
            }
            // if added, register and send signal to next higher observers so they can add and register as well
            pASSProcessModelElement.register(this);
            publishElementAdded(pASSProcessModelElement);
            if (exportGraph != null && pASSProcessModelElement instanceof IParseablePASSProcessModelElement) {
                IParseablePASSProcessModelElement parseable = (IParseablePASSProcessModelElement) pASSProcessModelElement;
                parseable.setExportGraph(exportGraph);
            }
            if (pASSProcessModelElement instanceof IInteractionDescribingComponent || pASSProcessModelElement instanceof IModelLayer || pASSProcessModelElement instanceof ISubjectBehavior) {
                addTriple(new IncompleteTriple(OWLTags.stdContains, pASSProcessModelElement.getUriModelComponentID()));
            }
        }
    }

    /// <summary>
    /// this method is used to just add the element to the list, and not trigger any observers etc.
    /// </summary>
    /// <param name="pASSProcessModelElement">the element that should be added</param>
    /// <returns>true if it could be added, false if not</returns>
    protected boolean justAddElement(IPASSProcessModelElement pASSProcessModelElement) {
        if (pASSProcessModelElement == null) {
            return false;
        }
        if (pASSProcessModelElement.equals(this)) return false;
        if (!allModelElements.containsKey(pASSProcessModelElement.getModelComponentID())) {
            allModelElements.tryAdd(pASSProcessModelElement.getModelComponentID(), pASSProcessModelElement);
            if (pASSProcessModelElement instanceof IContainableElement<IPASSProcessModel>) {
                IContainableElement<IPASSProcessModel> containable = (IContainableElement<IPASSProcessModel>) pASSProcessModelElement;
                containable.setContainedBy(this);
            }
            if (pASSProcessModelElement instanceof IParseablePASSProcessModelElement) {
                IParseablePASSProcessModelElement parseable = (IParseablePASSProcessModelElement) pASSProcessModelElement;
                parseable.setExportGraph(baseGraph);
            }

            return true;
        }
        return false;
    }

    //TODO: trygetvalue() Methode ersatz finden, Count Ersatz finden, Ersatz getModelComponentID
    public void removeElement(String modelComponentID, int removeCascadeDepth) {
        if (modelComponentID == null) {
            return;
        }
        IPASSProcessModelElement element = allModelElements.get(modelComponentID);
        if (element != null) {
            if (justRemoveElement(element)) {
                if (element instanceof IModelLayer) {
                    if (getModelLayers().Count < 2) setIsLayered(false);
                }

                // Might be a start subj
                if (element instanceof ISubject) {
                    ISubject subj = (ISubject) element;
                    removeStartSubject(subj.getModelComponentID());
                }

                removeTriple(new IncompleteTriple(OWLTags.stdContains, element.getUriModelComponentID()));
                element.unregister(this, removeCascadeDepth);
                for (IPASSProcessModelElement otherComponent : getAllElements().values()) {
                    otherComponent.updateRemoved(element, this, removeCascadeDepth);
                }
                element.removeFromEverything();
                if (getBaseLayer() != null)
                    if (getBaseLayer().getElements().Count() == 0) {
                        baseLayer.removeFromEverything();
                    }

            }
        }
    }


    /// <summary>
    /// this method is used to just remove the element from the list, and not trigger any observers etc.
    /// </summary>
    /// <param name="element">the element being removed</param>
    /// <returns>true if it could be removed, false if not</returns>
    protected boolean justRemoveElement(IPASSProcessModelElement element) {
        if (element == null) {
            return false;
        }
        if (allModelElements.remove(element.getModelComponentID())) {
            for (IModelLayer layer : getModelLayers().values())
                if (layer.getElements().containsKey(element.getModelComponentID()))
                    layer.removeContainedElement(element.getModelComponentID());
            return true;
        }
        return false;
    }


    // ######################## StartSubject methods ########################


    public void addStartSubject(ISubject startSubject) {
        if (startSubject == null) {
            return;
        }
        if (startSubjects.tryAdd(startSubject.getModelComponentID(), startSubject)) {
            startSubject.assignRole(ISubject.Role.StartSubject);
            addElement(startSubject);
            addTriple(new IncompleteTriple(OWLTags.stdHasStartSubject, startSubject.getUriModelComponentID()));
        }
    }

    public void setStartSubjects(Set<ISubject> startSubjects, int removeCascadeDepth) {
        for (ISubject startSubj : this.startSubjects.values()) {
            removeStartSubject(startSubj.getModelComponentID(), removeCascadeDepth);
        }
        if (startSubjects == null) return;
        for (ISubject startSubj : startSubjects) {
            addStartSubject(startSubj);
        }
    }

    public void removeStartSubject(String id, int removeCascadeDepth) {
        if (id == null) return;
        ISubject subj = startSubjects.get(id);
        if (subj != null) {
            // Do not remove the element completely, only remove it as start subject
            //removeElement(id, removeCascadeDepth);
            subj.removeRole(ISubject.Role.StartSubject);
            startSubjects.remove(id);
            removeTriple(new IncompleteTriple(OWLTags.stdHasStartSubject, subj.getUriModelComponentID()));
        }
    }

    public Map<String, ISubject> getStartSubjects() {
        return new HashMap<String, ISubject>(startSubjects);
    }


    // ######################## Contained layer methods ########################


    public void setLayers(Set<IModelLayer> modelLayers, int removeCascadeDepth) {
        for (IModelLayer layer : getModelLayers().values()) {
            removeLayer(layer.getModelComponentID(), removeCascadeDepth);
        }
        if (modelLayers == null) return;
        for (IModelLayer layer : modelLayers) {
            addLayer(layer);
        }
    }

    public void removeLayer(String id, int removeCascadeDepth) {
        removeElement(id);
    }

    //TODO: ofTType() Ersatz und getModelComponentID
    public Map<String, IModelLayer> getModelLayers() {
        Map<String, IModelLayer> resultDict = new HashMap<String, IModelLayer>();
        for (IModelLayer layer : getAllElements().values().ofType < IModelLayer > ()) {
            resultDict.add(layer.getModelComponentID(), layer);
        }
        return resultDict;
    }

    public void addLayer(IModelLayer layer) {
        addElement(layer);
    }


    public IModelLayer getBaseLayer() {
        if (baseLayer == null) {
            baseLayer = new ModelLayer(this, "defaultBaseLayer");

        }
        return baseLayer;
    }

    public void setBaseLayer(IModelLayer layer) {
        addElement(layer);
        this.baseLayer = layer;
    }


    /// <summary>
    /// this method is used to just add the element to the list, and not trigger any observers etc.
    /// </summary>
    /// <param name="pASSProcessModelElement">the element that should be added</param>
    /// <returns>true if it could be added, false if not</returns>
    protected boolean justAddElementToLayer(IPASSProcessModelElement pASSProcessModelElement, String layerID) {
        if (pASSProcessModelElement == null) {
            return false;
        }
        if (layerID == null) {
            return false;
        }
        if (getModelLayers().containsKey(layerID)) {
            IPASSProcessModelElement element = getAllElements().get(layerID);
            if (element != null) {
                if (element instanceof IModelLayer layer) {
                    IModelLayer layer = (IModelLayer) element;

                    layer.addElement(pASSProcessModelElement);
                    return true;
                }
            }
            return false;
        }


        public void setBaseURI (String baseURI)
        {
            if (baseURI != null) {
                String formattedBaseURI = baseURI.trim().Replace(" ", "_");
                this.baseURI = formattedBaseURI;
                if (getBaseGraph() == null) {
                    this.baseGraph = new PASSGraph(baseURI);
                    setExportGraph(baseGraph);
                } else {
                    this.baseGraph.changeBaseURI(baseURI);
                }
            }
        }

        public void setIsLayered ( boolean layered)
        {
            this.layered = layered;
        }

        public boolean isLayered ()
        {
            return layered;
        }


        @Override
        public void completeObject (Map < String, IParseablePASSProcessModelElement > allElements)
        {
            boolean checkLayers = false;

            List<IStateReference> refList = new List<IStateReference>();
            List<IState> newStates = new List<IState>();
            for (ParsedStateReferenceStub reference : allElements.Values.OfType < ParsedStateReferenceStub > ()) {
                newStates.add(reference.transformToState(allElements));
                refList.add(reference);
            }

            for (IStateReference reference : refList)
                allElements.remove(reference.getModelComponentID());

            for (IState state : newStates)
                if (state instanceof IParseablePASSProcessModelElement parseabelState) {
                    IParseablePASSProcessModelElement parseableState = (IParseablePASSProcessModelElement) state;
                    allElements.Add(state.getModelComponentID(), parseabelState);
                }

            // Go through triples, filter all Layers
            for (Triple triple : getTriples()) {
                String predicateContent = NodeHelper.getNodeContent(triple.Predicate);
                if (predicateContent.contains(OWLTags.contains)) {
                    String objectContent = NodeHelper.getNodeContent(triple.Object);

                    String possibleID = objectContent;
                    String[] splitted = possibleID.split('#');
                    if (splitted.length > 1)
                        possibleID = splitted[splitted.length - 1];

                    if (allElements.ContainsKey(possibleID)) {
                        if (allElements.get(possibleID) instanceof IModelLayer && allElements.get(possibleID) instanceof IParseablePASSProcessModelElement) {
                            IModelLayer layer = (IModelLayer) allElements.get(possibleID);
                            IParseablePASSProcessModelElement parseable = (IParseablePASSProcessModelElement) allElements.get(possibleID);
                            // Parse the layer
                            String lang = NodeHelper.getLangIfContained(triple.Object);
                            String dataType = NodeHelper.getDataTypeIfContained(triple.Object);
                            parseAttribute(predicateContent, possibleID, lang, dataType, parseable);
                        }
                    }
                }
            }
            if (getModelLayers().Count == 0) {
                getBaseLayer();
                checkLayers = true;
            } else {
                setIsLayered(true);
                for (IModelLayer layer : getModelLayers().values()) {

                    // Complete the layer first
                    if (layer instanceof IParseablePASSProcessModelElement) {
                        IParseablePASSProcessModelElement parseable = (IParseablePASSProcessModelElement) layer;
                        parseable.completeObject(allElements);
                    }

                    // Find non-abstract layer that is marked as baselayer
                    if (!layer.isAbstract() && layer.getLayerType() == IModelLayer.LayerType.BASE) {
                        this.baseLayer = layer;
                    }

                }
                if (baseLayer == null) {
                    // if there was no explicit base layer, find a standard layer and make it base layer
                    for (IModelLayer layer : getModelLayers().values()) {
                        if (!layer.isAbstract() && layer.getLayerType() == IModelLayer.LayerType.STANDARD) {
                            this.baseLayer = layer;
                            break;
                        }

                    }
                }
            }

            for (Triple triple : getTriples()) {
                IParseablePASSProcessModelElement element = new IParseablePASSProcessModelElement();
                parseAttribute(triple, allElements, element);
                // Calling parsing method
                // If attribute contains a reference to a PassProcessModelElement, pass this to the method

            }


            //foreach (IParseablePASSProcessModelElement element in getAllElements().Values)
            for (IParseablePASSProcessModelElement element : allElements.Values) {
                if (!(element instanceof IPASSProcessModel))
                    element.completeObject(allElements);
            }

            if (checkLayers) {
                Map<String, List<String>> doubleBehaviors = new HashMap<String, List<String>>();
                if (getAllElements().values().fType < ISubjectBehavior > ().Count() > 1)
                    for (ISubjectBehavior behavior : getAllElements().values().OfType < ISubjectBehavior > ()) {
                        if (behavior.getSubject() != null && behavior.getSubject() instanceof IFullySpecifiedSubject) {
                            IFullySpecifiedSubject subject = (IFullySpecifiedSubject) behavior.getSubject();
                            if (doubleBehaviors.containsKey(subject.getModelComponentID()))
                                doubleBehaviors[subject.getModelComponentID()].Add(behavior.getModelComponentID());
                                //TODO: else statement neu
                            else doubleBehaviors.add(subject.getModelComponentID(), new List<String> {
                                behavior.getModelComponentID()
                            });
                        }
                    }
                for (Map<String, List<String>> pair : doubleBehaviors) {
                    if (pair.Value.Count > 1)
                        fixLayers(pair.Key, pair.Value);

                }
                if (getModelLayers().Count > 1) {
                    setIsLayered(true);
                }
            }

            for (IParseablePASSProcessModelElement element : getAllElements().values()) {
                element.setExportGraph(baseGraph);
            }
        }

        /// <summary>
        /// Fix the layers if the imported model is not multi-layered.
        /// All elements get loaded into one layer, afterwards this method is called to split the elements onto multiple layers.
        /// </summary>
        /// <param name="idOfSubject"></param>
        /// <param name="idsOfBehaviors"></param>
        private void fixLayers (String idOfSubject, List < String > idsOfBehaviors)
        {
            /*
             * BaseBehavior Verlinkung
             * Prioritätsnummer
             *

             */

            // Case: A subject holds multiple behaviors in one layer -> split the behaviors up to several layers
            IFullySpecifiedSubject extendedSubject = (IFullySpecifiedSubject) getAllElements()[idOfSubject];
            ISubjectBehavior baseBehavior = null;
            if (extendedSubject.getSubjectBaseBehavior() != null) {
                // If there is an explicit base behavior, keep this in base layer
                baseBehavior = extendedSubject.getSubjectBaseBehavior();
            } else {
                // find a "normal" behavior with the lowest priority number to keep in base layer
                int lowestPrio = int.MaxValue;
                for (String id : idsOfBehaviors) {
                    ISubjectBehavior behavior = (ISubjectBehavior) getAllElements()[id];
                    if (!(behavior instanceof IGuardBehavior || behavior instanceof IMacroBehavior)) {
                        if (behavior.getPriorityNumber() < lowestPrio)
                            baseBehavior = behavior;
                    }
                }
            }

            // Make sure the selected behavior is in the base layer
            getBaseLayer().addElement(baseBehavior);
            for (String id : idsOfBehaviors) {
                if (!id.equals(baseBehavior.getModelComponentID())) {
                    ISubjectBehavior behaviorOfSubjectExtension = (ISubjectBehavior) getAllElements()[id];
                    IModelLayer layer = new ModelLayer(this);
                    ISubjectExtension subjectExtension;

                    // Create the extension subject and add it to a new layer with correct layer type
                    if (behaviorOfSubjectExtension instanceof IMacroBehavior) {
                        layer.setLayerType(IModelLayer.LayerType.MACRO);
                        subjectExtension = new MacroExtension(layer);

                    } else if (behaviorOfSubjectExtension instanceof IGuardBehavior) {
                        layer.setLayerType(IModelLayer.LayerType.GUARD);
                        subjectExtension = new GuardExtension(layer);
                    } else {
                        layer.setLayerType(IModelLayer.LayerType.EXTENSION);
                        subjectExtension = new SubjectExtension(layer);
                    }

                    // Cross-link the new extension to the old subject
                    subjectExtension.setExtendedSubject(extendedSubject);
                    subjectExtension.addExtensionBehavior(behaviorOfSubjectExtension);
                    extendedSubject.addBehavior(behaviorOfSubjectExtension);

                    // Move the behavior to the new layer
                    getBaseLayer().removeContainedElement(behaviorOfSubjectExtension.getModelComponentID());
                    layer.addElement(behaviorOfSubjectExtension);

                    //addLayer(layer);
                }
            }
            // TODO
        }
        @Override
        protected boolean parseAttribute (String predicate, String objectContent, String lang, sSring
        dataType, IParseablePASSProcessModelElement element)
        {
            if (implCapsule != null && implCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
                return true;
            else if (predicate.Contains(OWLTags.contains) && element != null) {
                if (element instanceof IModelLayer layer) {
                    IModelLayer layer = (IModelLayer) element;
                    addLayer(layer);
                } else addElement(element);

                return true;
            }

            return super.parseAttribute(predicate, objectContent, lang, dataType, element);
        }
        @Override
        public Set<IPASSProcessModelElement> getAllConnectedElements (ConnectedElementsSetSpecification specification)
        {
            Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
            for (IPASSProcessModelElement component : getAllElements().values())
                baseElements.add(component);
            return baseElements;
        }

        public void updateAdded (IPASSProcessModelElement update, IPASSProcessModelElement caller)
        {
            super.updateAdded(update, caller);

            if (justAddElement(update)) {
                if (update instanceof IInteractionDescribingComponent || update instanceof ISubjectBehavior) {
                    String layerToAdd = null;
                    if (!(caller instanceof IModelLayer)) {
                        // Search for the layer containing the caller
                        for (IModelLayer layer : getModelLayers().values()) {
                            if (layer.getElements().containsKey(caller.getModelComponentID())) {
                                layerToAdd = layer.getModelComponentID();
                                break;
                            }
                        }
                        if (layerToAdd == null) {
                            layerToAdd = getBaseLayer().getModelComponentID();
                        }

                        justAddElementToLayer(update, layerToAdd);
                    }
                }
                // if added, register. Do not send a signal to next higher observers, because they should be already registered on the element that caused the update,
                // and will be informed themselves
                update.register(this);
                if (exportGraph != null && update instanceof IParseablePASSProcessModelElement) {
                    IParseablePASSProcessModelElement parseable = (IParseablePASSProcessModelElement) update;
                    parseable.setExportGraph(exportGraph);
                }
                //update.completeObject(ref allElements);
            }
                    /*else
                    {
                        if (caller is IModelLayer callingLayer && !callingLayer.Equals(baseLayer))
                        {
                            if (baseLayer != null && baseLayer.getElements().ContainsKey(update.getModelComponentID()))
                            {
                                baseLayer.removeContainedElement(update.getModelComponentID());
                            }
                        }
                    }*/
            if (baseLayer != null && baseLayer.getElements().Count() == 0) {
                baseLayer.removeFromEverything();
            }

        }


        public void updateRemoved (IPASSProcessModelElement update, IPASSProcessModelElement caller,
        int removeCascadeDepth)
        {
            super.updateRemoved(update, caller, removeCascadeDepth);
            removeElement(update.getModelComponentID(), removeCascadeDepth);

        }


        @Override
        protected void successfullyParsedElement (IParseablePASSProcessModelElement parsedElement)
        {
            if (parsedElement instanceof IContainableElement<IPASSProcessModel>) {
                IContainableElement<IPASSProcessModel> containable = (IContainableElement<IPASSProcessModel>) parsedElement;
                containable.setContainedBy(this);
            }
            parsedElement.setExportGraph(baseGraph);
        }

        public IPASSGraph getBaseGraph ()
        {
            return exportGraph;
        }
        @Override
        public void notifyModelComponentIDChanged (String oldID, String newID)
        {
            if (allModelElements.containsKey(oldID)) {
                IPASSProcessModelElement element = allModelElements[oldID];
                allModelElements.remove(oldID);
                allModelElements.add(element.getModelComponentID(), element);
            }
            super.notifyModelComponentIDChanged(oldID, newID);
        }

        public String export (String filepath)
        {
            // Get the graph hold by the model and use the export function given by the library
            String fullPath = (filepath.EndsWith(".owl")) ? filepath : filepath + ".owl";
            getBaseGraph().exportTo(fullPath);
            FileInfo writtenFile = new FileInfo(fullPath);
            if (File.Exists(fullPath)) return writtenFile.FullName;
            return "";
        }

        public void setImplementedInterfacesIDReferences (Set < String > implementedInterfacesIDs)
        {
            implCapsule.setImplementedInterfacesIDReferences(implementedInterfacesIDs);
        }

        public void addImplementedInterfaceIDReference (String implementedInterfaceID)
        {
            implCapsule.addImplementedInterfaceIDReference(implementedInterfaceID);
        }

        public void removeImplementedInterfacesIDReference (String implementedInterfaceID)
        {
            implCapsule.removeImplementedInterfacesIDReference(implementedInterfaceID);
        }

        public Set<String> getImplementedInterfacesIDReferences ()
        {
            return implCapsule.getImplementedInterfacesIDReferences();
        }

        public void setImplementedInterfaces (Set < IPASSProcessModel > implementedInterface,int removeCascadeDepth)
        {
            implCapsule.setImplementedInterfaces(implementedInterface, removeCascadeDepth);
        }

        public void addImplementedInterface (IPASSProcessModel implementedInterface)
        {
            implCapsule.addImplementedInterface(implementedInterface);
        }

        public void removeImplementedInterfaces (String id,int removeCascadeDepth)
        {
            implCapsule.removeImplementedInterfaces(id, removeCascadeDepth);
        }

        public Map<String, IPASSProcessModel> getImplementedInterfaces ()
        {
            return implCapsule.getImplementedInterfaces();
        }
    }
}

