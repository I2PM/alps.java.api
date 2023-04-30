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
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.ParsedStateReferenceStub;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IFullySpecifiedSubject;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IMacroBehavior;
import alps.java.api.parsing.IPASSGraph;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.parsing.PASSGraph;
import alps.java.api.src.OWLTags;
import alps.java.api.util.*;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Statement;

import java.io.File;
import java.util.*;

/**
 * Class that represents a PASS Process Model
 * This model contains all elements known in the current context.
 */
public class PASSProcessModel extends PASSProcessModelElement implements IPASSProcessModel {
    /**
     * All elements held by the model (sum of all elements held by the layers)
     */
    protected ICompatibilityDictionary<String, IPASSProcessModelElement> allModelElements = new CompatibilityDictionary<String, IPASSProcessModelElement>();

    /**
     * A default layer, created when an element is added but no layer is specified.
     * Might be null
     */
    protected IModelLayer baseLayer;

    protected ICompatibilityDictionary<String, ISubject> startSubjects = new CompatibilityDictionary<String, ISubject>();
    protected String baseURI;
    protected IPASSGraph baseGraph;
    protected boolean layered;
    protected final IImplementsFunctionalityCapsule<IPASSProcessModel> implCapsule;

    /**
     * Name of the class, needed for parsing
     */
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

    /**
     * Constructor that creates a new fully specified instance of the pass process modell class
     */
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

    public void setAllElements(Set<IPASSProcessModelElement> elements) {
        for (IPASSProcessModelElement element : this.getAllElements().values()) {
            removeElement(element.getModelComponentID(), 0);
        }
        if (elements == null) return;
        for (IPASSProcessModelElement element : elements) {
            addElement(element);
        }
    }


    public void addElement(IPASSProcessModelElement pASSProcessModelElement, String layerID) {
        if (pASSProcessModelElement == null) return;


        // Check for layer, create default if non matches

        if (justAddElement(pASSProcessModelElement)) {

            if (pASSProcessModelElement instanceof IModelLayer) {
                if (getModelLayers().size() > 1) setIsLayered(true);

            } else {
                if (pASSProcessModelElement instanceof IInteractionDescribingComponent || pASSProcessModelElement instanceof ISubjectBehavior) {
                    if (layerID == null) {
                        layerID = getBaseLayer().getModelComponentID();
                    }
                    justAddElementToLayer(pASSProcessModelElement, layerID);
                    if (pASSProcessModelElement instanceof ISubject subj && subj.isRole(ISubject.Role.StartSubject)) {
                        addStartSubject(subj);
                    }
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

    public void addElement(IPASSProcessModelElement pASSProcessModelElement) {
        if (pASSProcessModelElement == null) return;


        // Check for layer, create default if non matches

        if (justAddElement(pASSProcessModelElement)) {

            if (pASSProcessModelElement instanceof IModelLayer) {
                if (getModelLayers().size() > 1) setIsLayered(true);

            } else {
                if (pASSProcessModelElement instanceof IInteractionDescribingComponent || pASSProcessModelElement instanceof ISubjectBehavior) {
                    String layerID = getBaseLayer().getModelComponentID();
                    justAddElementToLayer(pASSProcessModelElement, layerID);
                    if (pASSProcessModelElement instanceof ISubject subj && subj.isRole(ISubject.Role.StartSubject)) {
                        addStartSubject(subj);
                    }
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

    /**
     * this method is used to just add the element to the list, and not trigger any observers etc.
     *
     * @param pASSProcessModelElement the element that should be added
     * @return true if it could be added, false if not
     */
    protected boolean justAddElement(IPASSProcessModelElement pASSProcessModelElement) {
        if (pASSProcessModelElement == null) {
            return false;
        }
        if (pASSProcessModelElement.equals(this)) {
            return false;
        }
        if (!allModelElements.containsKey(pASSProcessModelElement.getModelComponentID())) {
            allModelElements.tryAdd(pASSProcessModelElement.getModelComponentID(), pASSProcessModelElement);
            if (pASSProcessModelElement instanceof IContainableElement<IPASSProcessModel> containable) {
                containable.setContainedBy(this);
            }
            if (pASSProcessModelElement instanceof IParseablePASSProcessModelElement parseable) {
                parseable.setExportGraph(baseGraph);
            }

            return true;
        }
        return false;
    }

    public void removeElement(String modelComponentID, int removeCascadeDepth) {
        if (modelComponentID == null) {
            return;
        }
        IPASSProcessModelElement element = allModelElements.get(modelComponentID);
        if (element != null) {
            if (justRemoveElement(element)) {
                if (element instanceof IModelLayer) {
                    if (getModelLayers().size() < 2) setIsLayered(false);
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
                    if (getBaseLayer().getElements().size() == 0) {
                        baseLayer.removeFromEverything();
                    }

            }
        }
    }

    public void removeElement(String modelComponentID) {
        if (modelComponentID == null) {
            return;
        }
        IPASSProcessModelElement element = allModelElements.get(modelComponentID);
        if (element != null) {
            if (justRemoveElement(element)) {
                if (element instanceof IModelLayer) {
                    if (getModelLayers().size() < 2) setIsLayered(false);
                }

                // Might be a start subj
                if (element instanceof ISubject) {
                    ISubject subj = (ISubject) element;
                    removeStartSubject(subj.getModelComponentID());
                }

                removeTriple(new IncompleteTriple(OWLTags.stdContains, element.getUriModelComponentID()));
                element.unregister(this, 0);
                for (IPASSProcessModelElement otherComponent : getAllElements().values()) {
                    otherComponent.updateRemoved(element, this, 0);
                }
                element.removeFromEverything();
                if (getBaseLayer() != null)
                    if (getBaseLayer().getElements().size() == 0) {
                        baseLayer.removeFromEverything();
                    }

            }
        }
    }


    /**
     * this method is used to just remove the element from the list, and not trigger any observers etc.
     *
     * @param element the element being removed
     * @return true if it could be removed, false if not
     */
    protected boolean justRemoveElement(IPASSProcessModelElement element) {
        if (element == null) {
            return false;
        }
        Object removedElement = allModelElements.remove(element.getModelComponentID());
        if (removedElement != null) {
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

    public void setStartSubjects(Set<ISubject> startSubjects) {
        for (ISubject startSubj : this.startSubjects.values()) {
            removeStartSubject(startSubj.getModelComponentID(), 0);
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

    public void removeStartSubject(String id) {
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

    public void setLayers(Set<IModelLayer> modelLayers) {
        for (IModelLayer layer : getModelLayers().values()) {
            removeLayer(layer.getModelComponentID(), 0);
        }
        if (modelLayers == null) return;
        for (IModelLayer layer : modelLayers) {
            addLayer(layer);
        }
    }

    public void removeLayer(String id, int removeCascadeDepth) {
        removeElement(id);
    }

    public void removeLayer(String id) {
        removeElement(id);
    }

    public Map<String, IModelLayer> getModelLayers() {
        Map<String, IModelLayer> resultDict = new HashMap<String, IModelLayer>();
        for (Object element : getAllElements().values()) {
            if (element instanceof IModelLayer) {
                IModelLayer layer = (IModelLayer) element;
                resultDict.put(layer.getModelComponentID(), layer);
            }
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


    /**
     * this method is used to just add the element to the list, and not trigger any observers etc.
     *
     * @param pASSProcessModelElement the element that should be added
     * @param layerID
     * @return true if it could be added, false if not
     */
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
                    layer.addElement(pASSProcessModelElement);
                    return true;
                }
            }
        }
        return false;
    }


    public void setBaseURI(String baseURI) {
        if (baseURI != null) {
            String formattedBaseURI = baseURI.trim().replace(" ", "_");
            this.baseURI = formattedBaseURI;
            if (getBaseGraph() == null) {
                this.baseGraph = new PASSGraph(baseURI);
                setExportGraph(baseGraph);
            } else {
                this.baseGraph.changeBaseURI(baseURI);
            }
        }
    }

    public void setIsLayered(boolean layered) {
        this.layered = layered;
    }

    public boolean isLayered() {
        return layered;
    }

    //TODO: ref-Parameter, ref-Methode
    @Override
    public void completeObject(Map<String, IParseablePASSProcessModelElement> allElements) {
        boolean checkLayers = false;

        List<IStateReference> refList = new ArrayList<IStateReference>();
        List<IState> newStates = new ArrayList<IState>();
        for (Object element : allElements.values()) {
            if (element instanceof ParsedStateReferenceStub) {
                ParsedStateReferenceStub reference = (ParsedStateReferenceStub) element;
                newStates.add(reference.transformToState(allElements));
                refList.add(reference);
            }
        }

        for (IStateReference reference : refList)
            allElements.remove(reference.getModelComponentID());

        for (IState state : newStates)
            if (state instanceof IParseablePASSProcessModelElement parseabelState) {
                allElements.put(state.getModelComponentID(), parseabelState);
            }

        // Go through triples, filter all Layers
        for (Statement triple : getTriples()) {
            String predicateContent = NodeHelper.getNodeContent(triple.getPredicate());
            if (predicateContent.contains(OWLTags.ccontains)) {
                String objectContent = NodeHelper.getNodeContent(triple.getObject());

                String possibleID = objectContent;
                String[] splitted = possibleID.split("#");
                if (splitted.length > 1)
                    possibleID = splitted[splitted.length - 1];

                if (allElements.containsKey(possibleID)) {
                    if (allElements.get(possibleID) instanceof IModelLayer && allElements.get(possibleID) instanceof IParseablePASSProcessModelElement) {
                        IModelLayer layer = (IModelLayer) allElements.get(possibleID);
                        IParseablePASSProcessModelElement parseable = (IParseablePASSProcessModelElement) allElements.get(possibleID);
                        // Parse the layer
                        String lang = NodeHelper.getLangIfContained(triple.getObject());
                        String dataType = NodeHelper.getDataTypeIfContained(triple.getObject());
                        parseAttribute(predicateContent, possibleID, lang, dataType, parseable);
                    }
                }
            }
        }
        if (getModelLayers().size() == 0) {
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

        for (Statement triple : getTriples()) {
            IParseablePASSProcessModelElement element = parseAttribute(triple, allElements);
            // Calling parsing method
            // If attribute contains a reference to a PassProcessModelElement, pass this to the method

        }

        //TODO: ref-Methode
        //foreach (IParseablePASSProcessModelElement element in getAllElements().Values)
        for (IParseablePASSProcessModelElement element : allElements.values()) {
            if (!(element instanceof IPASSProcessModel))
                element.completeObject(ref allElements);
        }

        if (checkLayers) {
            Map<String, List<String>> doubleBehaviors = new HashMap<>();
            if (getAllElements().values().stream().filter(e -> e instanceof ISubjectBehavior).count() > 1) {
                for (IParseablePASSProcessModelElement element : getAllElements().values()) {
                    if (element instanceof ISubjectBehavior) {
                        ISubjectBehavior behavior = (ISubjectBehavior) element;
                        if (behavior.getSubject() != null && behavior.getSubject() instanceof IFullySpecifiedSubject) {
                            IFullySpecifiedSubject subject = (IFullySpecifiedSubject) behavior.getSubject();
                            if (doubleBehaviors.containsKey(subject.getModelComponentID())) {
                                doubleBehaviors.get(subject.getModelComponentID()).add(behavior.getModelComponentID());
                            } else {
                                List<String> newList = new ArrayList<>();
                                newList.add(behavior.getModelComponentID());
                                doubleBehaviors.put(subject.getModelComponentID(), newList);
                            }
                        }
                    }
                }
            }
            for (Map.Entry<String, List<String>> pair : doubleBehaviors.entrySet()) {
                if (pair.getValue().size() > 1) {
                    fixLayers(pair.getKey(), pair.getValue());
                }
            }
            if (getModelLayers().size() > 1) {
                setIsLayered(true);
            }
        }

        for (IParseablePASSProcessModelElement element : getAllElements().values()) {
            element.setExportGraph(baseGraph); // removed 'ref' keyword, as it is not needed in Java
        }
    }

    /**
     * Fix the layers if the imported model is not multi-layered.
     * All elements get loaded into one layer, afterwards this method is called to split the elements onto multiple layers.
     *
     * @param idOfSubject
     * @param idsOfBehaviors
     */
    private void fixLayers(String idOfSubject, List<String> idsOfBehaviors) {
        /*
         * BaseBehavior Verlinkung
         * Prioritätsnummer
         *

         */

        // Case: A subject holds multiple behaviors in one layer -> split the behaviors up to several layers
        IFullySpecifiedSubject extendedSubject = (IFullySpecifiedSubject) getAllElements().get(idOfSubject);
        ISubjectBehavior baseBehavior = null;
        if (extendedSubject.getSubjectBaseBehavior() != null) {
            // If there is an explicit base behavior, keep this in base layer
            baseBehavior = extendedSubject.getSubjectBaseBehavior();
        } else {
            // find a "normal" behavior with the lowest priority number to keep in base layer
            int lowestPrio = Integer.MAX_VALUE;
            for (String id : idsOfBehaviors) {
                ISubjectBehavior behavior = (ISubjectBehavior) getAllElements().get(id);
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
                ISubjectBehavior behaviorOfSubjectExtension = (ISubjectBehavior) getAllElements().get(id);
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
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String
            dataType, IParseablePASSProcessModelElement element) {
        if (implCapsule != null && implCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
            return true;
        else if (predicate.contains(OWLTags.ccontains) && element != null) {
            if (element instanceof IModelLayer layer) {
                addLayer(layer);
            } else addElement(element);

            return true;
        }

        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IPASSProcessModelElement component : getAllElements().values())
            baseElements.add(component);
        return baseElements;
    }

    public void updateAdded(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
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
        if (baseLayer != null && baseLayer.getElements().size() == 0) {
            baseLayer.removeFromEverything();
        }

    }


    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller,
                              int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        removeElement(update.getModelComponentID(), removeCascadeDepth);

    }

    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        removeElement(update.getModelComponentID(), 0);

    }


    @Override
    protected void successfullyParsedElement(IParseablePASSProcessModelElement parsedElement) {
        if (parsedElement instanceof IContainableElement<IPASSProcessModel> containable) {
            containable.setContainedBy(this);
        }
        parsedElement.setExportGraph(baseGraph);
    }

    public IPASSGraph getBaseGraph() {
        return exportGraph;
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (allModelElements.containsKey(oldID)) {
            IPASSProcessModelElement element = allModelElements.get(oldID);
            allModelElements.remove(oldID);
            allModelElements.put(element.getModelComponentID(), element);
        }
        super.notifyModelComponentIDChanged(oldID, newID);
    }

    public String export(String filepath) {
        // Get the graph hold by the model and use the export function given by the library
        String fullPath = (filepath.endsWith(".owl")) ? filepath : filepath + ".owl";
        getBaseGraph().exportTo(fullPath);
        File writtenFile = new File(fullPath);
        if (writtenFile.exists()) {
            return writtenFile.getAbsolutePath();
        }
        return "";
    }

    public void setImplementedInterfacesIDReferences(Set<String> implementedInterfacesIDs) {
        implCapsule.setImplementedInterfacesIDReferences(implementedInterfacesIDs);
    }

    public void addImplementedInterfaceIDReference(String implementedInterfaceID) {
        implCapsule.addImplementedInterfaceIDReference(implementedInterfaceID);
    }

    public void removeImplementedInterfacesIDReference(String implementedInterfaceID) {
        implCapsule.removeImplementedInterfacesIDReference(implementedInterfaceID);
    }

    public Set<String> getImplementedInterfacesIDReferences() {
        return implCapsule.getImplementedInterfacesIDReferences();
    }

    public void setImplementedInterfaces(Set<IPASSProcessModel> implementedInterface, int removeCascadeDepth) {
        implCapsule.setImplementedInterfaces(implementedInterface, removeCascadeDepth);
    }

    public void setImplementedInterfaces(Set<IPASSProcessModel> implementedInterface) {
        implCapsule.setImplementedInterfaces(implementedInterface, 0);
    }

    public void addImplementedInterface(IPASSProcessModel implementedInterface) {
        implCapsule.addImplementedInterface(implementedInterface);
    }

    public void removeImplementedInterfaces(String id, int removeCascadeDepth) {
        implCapsule.removeImplementedInterfaces(id, removeCascadeDepth);
    }

    public void removeImplementedInterfaces(String id) {
        implCapsule.removeImplementedInterfaces(id, 0);
    }

    public Map<String, IPASSProcessModel> getImplementedInterfaces() {
        return implCapsule.getImplementedInterfaces();
    }
}


