package alps.java.api.ALPS.ALPSModelElements;

import alps.java.api.ALPS.ALPSModelElement;
import alps.java.api.ALPS.ALPSModelElements.ALPSSBDComponets.IGuardReceiveState;
import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents.ISubjectExtension;
import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents.SubjectExtensions.IGuardExtension;
import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents.SubjectExtensions.IMacroExtension;
import alps.java.api.ALPS.IALPSModelElement;
import alps.java.api.FunctionalityCapsules.ExtendsFunctionalityCapsule;
import alps.java.api.FunctionalityCapsules.IExtendsFunctionalityCapsule;
import alps.java.api.FunctionalityCapsules.IImplementsFunctionalityCapsule;
import alps.java.api.FunctionalityCapsules.ImplementsFunctionalityCapsule;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.IInteractionDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.*;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints.IMessageSenderTypeConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints.IMessageTypeConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints.ISenderTypeConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IFullySpecifiedSubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IInterfaceSubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IMultiSubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.ISingleSubject;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class that represents a model layer
 */
public class ModelLayer extends ALPSModelElement implements IModelLayer {

    protected ICompatibilityDictionary<String, IPASSProcessModelElement> elements = new CompatibilityDictionary<String, IPASSProcessModelElement>();
    protected IImplementsFunctionalityCapsule<IModelLayer> implCapsule;
    protected IExtendsFunctionalityCapsule<IModelLayer> extendsCapsule;
    protected int priorityNumber;
    protected IPASSProcessModel model;
    protected LayerType layerType = LayerType.STANDARD;
    protected boolean isAbstractType = false;
    protected IModelLayer extendedLayer;

    private final String ABSTRACT_NAME = "AbstractLayer";

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ModelLayer();
    }

    public ModelLayer() {
        implCapsule = new ImplementsFunctionalityCapsule<IModelLayer>(this);
        extendsCapsule = new ExtendsFunctionalityCapsule<IModelLayer>(this);
    }

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ModelLayer";
    protected String exportClassname = className;

    @Override
    public String getClassName() {
        return exportClassname;
    }

    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }


    public ModelLayer(IPASSProcessModel model, String labelForID, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(labelForID, comment, additionalLabel, additionalAttribute);
        extendsCapsule = new ExtendsFunctionalityCapsule<IModelLayer>(this);
        implCapsule = new ImplementsFunctionalityCapsule<IModelLayer>(this);
        setContainedBy(model);
    }

    public ModelLayer(IPASSProcessModel model) {
        super(null, null, null, null);
        extendsCapsule = new ExtendsFunctionalityCapsule<IModelLayer>(this);
        implCapsule = new ImplementsFunctionalityCapsule<IModelLayer>(this);
        setContainedBy(model);
    }

    public ModelLayer(IPASSProcessModel model, String labelForID) {
        super(labelForID, null, null, null);
        extendsCapsule = new ExtendsFunctionalityCapsule<IModelLayer>(this);
        implCapsule = new ImplementsFunctionalityCapsule<IModelLayer>(this);
        setContainedBy(model);
    }

    /**
     * Returns a dictionary of all elements saved in the model layer
     *
     * @return
     */
    public Map<String, IPASSProcessModelElement> getElements() {
        return new HashMap<String, IPASSProcessModelElement>(elements);
    }

    public IPASSProcessModelElement getElement(String id) {
        if (elements.containsKey(id)) {
            return elements.get(id);
        }
        return null;
    }

    protected void checkLayerTypes() {
        if (elements.values().stream().filter(element -> element instanceof IALPSModelElement).count() == 0) {
            setIsAbstract(false);
        } else {
            setIsAbstract(true);
        }
        for (IPASSProcessModelElement element : elements.values()) {
            if (element instanceof IGuardExtension) {
                setLayerType(LayerType.GUARD);
                return;
            } else if (element instanceof IMacroExtension) {
                setLayerType(LayerType.MACRO);
                return;
            } else if (element instanceof ISubjectExtension) {
                setLayerType(LayerType.EXTENSION);
                return;
            }
        }
        setLayerType(LayerType.STANDARD);
    }


    public void setLayerType(LayerType layerType) {
        switch (layerType) {
            case GUARD:
                removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                exportClassname = "Guard" + className;
                this.layerType = layerType;
                addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                break;
            case EXTENSION:
                removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                this.layerType = layerType;
                exportClassname = "Extension" + className;
                addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                break;
            case MACRO:
                removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                this.layerType = layerType;
                exportClassname = "Macro" + className;
                addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                break;
            case BASE:
                removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                this.layerType = layerType;
                exportClassname = "Base" + className;
                addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                break;
            case STANDARD:
                removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                this.layerType = layerType;
                exportClassname = className;
                addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                break;
            default:

                break;

        }
    }

    public LayerType getLayerType() {
        return layerType;
    }

    public void setIsAbstract(boolean isAbstract) {
        this.isAbstractType = isAbstract;
        if (isAbstract) {
            addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + ABSTRACT_NAME));
        } else {
            removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + ABSTRACT_NAME));
        }
    }

    public boolean isAbstract() {
        return isAbstractType;
    }

    public boolean removeContainedElement(String modelComponentID, int removeCascadeDepth) {
        if (modelComponentID == null) return false;
        IPASSProcessModelElement element = elements.get(modelComponentID);
        if (element != null) {
            elements.remove(modelComponentID);
            element.unregister(this, removeCascadeDepth);
            if (element instanceof IContainableElement) {
                IContainableElement<IModelLayer> containable = (IContainableElement<IModelLayer>) element;
                IModelLayer layer = containable.getContainedBy();
                if (layer != null && layer == this) {
                    containable.removeFromContainer();
                }
            }
            removeTriple(new IncompleteTriple(OWLTags.stdContains, element.getUriModelComponentID()));
            checkLayerTypes();

        }
        return false;


    }

    public boolean removeContainedElement(String modelComponentID) {
        if (modelComponentID == null) return false;
        IPASSProcessModelElement element = elements.get(modelComponentID);
        if (element != null) {
            elements.remove(modelComponentID);
            element.unregister(this, 0);
            if (element instanceof IContainableElement) {
                IContainableElement<IModelLayer> containable = (IContainableElement<IModelLayer>) element;
                IModelLayer layer = containable.getContainedBy();
                if (layer != null && layer == this) {
                    containable.removeFromContainer();
                }
            }
            removeTriple(new IncompleteTriple(OWLTags.stdContains, element.getUriModelComponentID()));
            checkLayerTypes();

        }
        return false;


    }

    /**
     * Adds an IPASSProcessModelElement to the layer.
     * Only elements of type {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IFullySpecifiedSubject}, {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IInterfaceSubject},
     * {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IMultiSubject},{@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.ISingleSubject},
     * {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange}, {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IInputPoolConstraint},
     * {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints.IMessageSenderTypeConstraint}, {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints.IMessageTypeConstraint},
     * {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints.ISenderTypeConstraint}, {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IInputPoolConstraintHandlingStrategy},
     * {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchangeList}, {@link alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageSpecification} are allowed to be added.
     *
     * @param element The element that will be added
     */
    public void addElement(IPASSProcessModelElement element) {
        if (element == null) {
            return;
        }
        if (element instanceof IInteractionDescribingComponent || element instanceof ISubjectBehavior)
            if (elements.tryAdd(element.getModelComponentID(), element)) {
                if (element instanceof ISubjectExtension subjExt) {
                    if (!(element instanceof IMacroExtension) && getLayerType() == LayerType.MACRO
                            || !(element instanceof IGuardExtension) && getLayerType() == LayerType.GUARD
                            || (element instanceof IMacroExtension || element instanceof IGuardExtension) && getLayerType() == LayerType.EXTENSION
                            || (element instanceof IGuardReceiveState) && getLayerType() != LayerType.GUARD) {
                        elements.remove(element.getModelComponentID());
                        return;
                    }
                    List<ISubjectExtension> subjectExtensions = elements.values().stream()
                            .filter(eelement -> element instanceof ISubjectExtension)
                            .map(eelement -> (ISubjectExtension) element)
                            .collect(Collectors.toList());

                    for (ISubjectExtension ext : subjectExtensions) {
                        if (ext.getExtendedSubject() != null && subjExt.getExtendedSubject() != null && ext.getExtendedSubject().equals(subjExt.getExtendedSubject())) {
                            elements.remove(element.getModelComponentID());
                            return;
                        }
                    }
                }
                publishElementAdded(element);
                element.register(this);

                checkLayerTypes();
                if (element instanceof IContainableElement) {
                    IContainableElement<IModelLayer> containable = (IContainableElement<IModelLayer>) element;
                    containable.setContainedBy(this);
                }
                addTriple(new IncompleteTriple(OWLTags.stdContains, element.getUriModelComponentID()));
            }
    }


    /**
     * Returns a fully specified subject depending on its position
     *
     * @param numberOfElement the position in the list of subjects
     * @return
     */

    public IFullySpecifiedSubject getFullySpecifiedSubject(int numberOfElement) {
        List<IFullySpecifiedSubject> fullySpecifiedSubjects = elements.values().stream()
                .filter(element -> element instanceof IFullySpecifiedSubject)
                .map(element -> (IFullySpecifiedSubject) element).toList();
        if(fullySpecifiedSubjects.size() > 0) {
            return fullySpecifiedSubjects.get(numberOfElement);
        }else{
            System.out.println("No FullySpecifiedSubject found");
            return null;
        }
    }


    /**
     * @param numberOfElement
     * @return
     */
    public IInterfaceSubject getInterfaceSubject(int numberOfElement) {
        List<IInterfaceSubject> interfaceSubjects = elements.values().stream()
                .filter(element -> element instanceof IInterfaceSubject)
                .map(element -> (IInterfaceSubject) element)
                .collect(Collectors.toList());

        return interfaceSubjects.get(numberOfElement);
    }


    /**
     * Returns a multi subject depending on its position
     *
     * @param numberOfElement
     * @return
     */
    public IMultiSubject getMultiSubject(int numberOfElement) {
        List<IMultiSubject> multiSubjects = elements.values().stream()
                .filter(element -> element instanceof IMultiSubject)
                .map(element -> (IMultiSubject) element)
                .collect(Collectors.toList());

        return multiSubjects.get(numberOfElement);
    }


    /**
     * Returns a single subject depending on its position
     *
     * @param numberOfElement
     * @return
     */
    public ISingleSubject getSingleSubject(int numberOfElement) {
        List<ISingleSubject> singleSubjects = elements.values().stream()
                .filter(element -> element instanceof ISingleSubject)
                .map(element -> (ISingleSubject) element)
                .collect(Collectors.toList());

        return singleSubjects.get(numberOfElement);
    }

    /**
     * Returns a message exchange depending on its position
     *
     * @param numberOfElement
     * @return
     */
    public IMessageExchange getMessageExchange(int numberOfElement) {
        List<IMessageExchange> messageExchanges = elements.values().stream()
                .filter(element -> element instanceof IMessageExchange)
                .map(element -> (IMessageExchange) element)
                .collect(Collectors.toList());

        return messageExchanges.get(numberOfElement);
    }


    /**
     * Returns an input pool constraint depending on its position
     *
     * @param numberOfElement
     * @return The object
     */
    public IInputPoolConstraint getInputPoolConstraint(int numberOfElement) {
        List<IInputPoolConstraint> inputPoolConstraints = elements.values().stream()
                .filter(element -> element instanceof IInputPoolConstraint)
                .map(element -> (IInputPoolConstraint) element)
                .collect(Collectors.toList());

        return inputPoolConstraints.get(numberOfElement);
    }


    /**
     * Returns a message sender type constraint depending on its position
     *
     * @param numberOfElement
     * @return The object
     */
    public IMessageSenderTypeConstraint getMessageSenderTypeConstraint(int numberOfElement) {
        List<IMessageSenderTypeConstraint> messageSenderTypeConstraints = elements.values().stream()
                .filter(element -> element instanceof IMessageSenderTypeConstraint)
                .map(element -> (IMessageSenderTypeConstraint) element)
                .collect(Collectors.toList());

        return messageSenderTypeConstraints.get(numberOfElement);
    }


    /**
     * Returns a sender type constraint depending on its position
     *
     * @param numberOfElement
     * @return The object
     */
    public ISenderTypeConstraint getSenderTypeConstraint(int numberOfElement) {
        List<ISenderTypeConstraint> senderTypeConstraints = elements.values().stream()
                .filter(element -> element instanceof ISenderTypeConstraint)
                .map(element -> (ISenderTypeConstraint) element)
                .collect(Collectors.toList());

        return senderTypeConstraints.get(numberOfElement);
    }


    /**
     * Returns a message type constraint depending on its position
     *
     * @param numberOfElement
     * @return The object
     */
    public IMessageTypeConstraint getMessageTypeConstraint(int numberOfElement) {
        List<IMessageTypeConstraint> messageTypeConstraints = elements.values().stream()
                .filter(element -> element instanceof IMessageTypeConstraint)
                .map(element -> (IMessageTypeConstraint) element)
                .collect(Collectors.toList());

        return messageTypeConstraints.get(numberOfElement);
    }


    /**
     * Returns a input pool constraint handling strategy depending on its position
     *
     * @param numberOfElement
     * @return The object
     */
    public IInputPoolConstraintHandlingStrategy getInputPoolConstraintHandlingStrategy(int numberOfElement) {
        List<IInputPoolConstraintHandlingStrategy> inputPoolConstraintHandlingStrategies = elements.values().stream()
                .filter(element -> element instanceof IInputPoolConstraintHandlingStrategy)
                .map(element -> (IInputPoolConstraintHandlingStrategy) element)
                .collect(Collectors.toList());

        return inputPoolConstraintHandlingStrategies.get(numberOfElement);
    }


    /**
     * Returns the message exchange list depending on its position
     *
     * @param numberOfElement
     * @return the object
     */
    public IMessageExchangeList getMessageExchangeList(int numberOfElement) {
        List<IMessageExchangeList> messageExchangeLists = elements.values().stream()
                .filter(element -> element instanceof IMessageExchangeList)
                .map(element -> (IMessageExchangeList) element)
                .collect(Collectors.toList());

        return messageExchangeLists.get(numberOfElement);
    }


    /**
     * Returns a message specification depending on its position
     *
     * @param numberOfElement
     * @return The object
     */
    public IMessageSpecification getMessageSpecification(int numberOfElement) {
        List<IMessageSpecification> messageSpecifications = elements.values().stream()
                .filter(element -> element instanceof IMessageSpecification)
                .map(element -> (IMessageSpecification) element)
                .collect(Collectors.toList());

        return messageSpecifications.get(numberOfElement);
    }

    @Override
    protected void successfullyParsedElement(IParseablePASSProcessModelElement parsedElement) {
        if (parsedElement instanceof IContainableElement) {
            IContainableElement<IModelLayer> containable = (IContainableElement<IModelLayer>) parsedElement;
            containable.setContainedBy(this);
        }
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (implCapsule != null && implCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
            return true;
        else if (extendsCapsule != null && extendsCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
            return true;
        else if (element != null) {
            if (predicate.contains(OWLTags.ccontains)) {
                addElement(element);
                return true;
            } else if (predicate.contains(OWLTags.eextends) && element instanceof IModelLayer layer) {
                setExtendedLayer(layer);
                return true;
            }
        } else {
            if (predicate.contains(OWLTags.type)) {
                if (objectContent.contains("MacroLayer")) {
                    setLayerType(LayerType.MACRO);
                    return true;
                } else if (objectContent.contains("GuardLayer")) {
                    setLayerType(LayerType.GUARD);
                    return true;
                } else if (objectContent.contains("ExtensionLayer")) {
                    setLayerType(LayerType.EXTENSION);
                    return true;
                } else if (objectContent.contains("BaseLayer")) {
                    setLayerType(LayerType.BASE);
                    return true;
                } else if (objectContent.contains(ABSTRACT_NAME)) {
                    setIsAbstract(true);
                    return true;
                }
            } else {
                if (predicate.contains(OWLTags.hasPriorityNumber)) {
                    String prio = objectContent;
                    prio = prio.split("^")[0];
                    if(prio.contains("@")){
                        setPriorityNumber(1);
                    }else {
                        setPriorityNumber(Integer.parseInt(prio));
                    }
                    return true;
                }
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    public void updateAdded(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateAdded(update, caller);
        IPASSProcessModel model = getContainedBy();

        if (model != null) {
            // If the element is already in another layer, do not add it
            List<IModelLayer> modelLayers = elements.values().stream()
                    .filter(element -> element instanceof IModelLayer)
                    .map(element -> (IModelLayer) element)
                    .collect(Collectors.toList());

            for (IModelLayer layer : modelLayers) {
                if (layer.getElements().containsKey(update.getModelComponentID())) {
                    return;
                }
            }

            addElement(update);
            model.updateAdded(update, caller);
        } else {
            addElement(update);
        }
    }

    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller);
        removeContainedElement(update.getModelComponentID(), removeCascadeDepth);

    }

    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller);
        removeContainedElement(update.getModelComponentID(), 0);

    }


    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IPASSProcessModelElement element : getElements().values())
            baseElements.add(element);
        if (getContainedBy() != null)
            baseElements.add(getContainedBy());
        return baseElements;
    }


    public void setPriorityNumber(int nonNegativInteger) {
        removeTriple(new IncompleteTriple(OWLTags.stdHasPriorityNumber, Integer.toString(priorityNumber), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
        if (nonNegativInteger > 0) priorityNumber = nonNegativInteger;
        else priorityNumber = 1;
        addTriple(new IncompleteTriple(OWLTags.stdHasPriorityNumber, Integer.toString(priorityNumber), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
    }

    public int getPriorityNumber() {
        return priorityNumber;
    }


    public boolean getContainedBy(IPASSProcessModel model) {
        model = this.model;
        return this.model != null;
    }

    public void setContainedBy(IPASSProcessModel container) {
        if (container == null) return;
        if (container.equals(model)) return;
        this.model = container;
        this.model.addLayer(this);
    }

    public IPASSProcessModel getContainedBy() {
        return this.model;
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (elements.containsKey(oldID)) {
            IPASSProcessModelElement element = elements.get(oldID);
            elements.remove(oldID);
            elements.put(element.getModelComponentID(), element);
        }
        super.notifyModelComponentIDChanged(oldID, newID);
    }


    public void setExtendedLayer(IModelLayer extendedLayer, int removeCascadeDepth) {
        IModelLayer oldExtendedLayer = this.extendedLayer;
        // Might set it to null
        this.extendedLayer = extendedLayer;

        if (oldExtendedLayer != null) {
            if (oldExtendedLayer.equals(extendedLayer)) return;
            removeTriple(new IncompleteTriple(OWLTags.abstrExtends, oldExtendedLayer.getUriModelComponentID()));
        }

        if (!(extendedLayer == null)) {
            addTriple(new IncompleteTriple(OWLTags.abstrExtends, extendedLayer.getUriModelComponentID()));
        }
    }

    public void setExtendedLayer(IModelLayer extendedLayer) {
        IModelLayer oldExtendedLayer = this.extendedLayer;
        // Might set it to null
        this.extendedLayer = extendedLayer;

        if (oldExtendedLayer != null) {
            if (oldExtendedLayer.equals(extendedLayer)) return;
            removeTriple(new IncompleteTriple(OWLTags.abstrExtends, oldExtendedLayer.getUriModelComponentID()));
        }

        if (!(extendedLayer == null)) {
            addTriple(new IncompleteTriple(OWLTags.abstrExtends, extendedLayer.getUriModelComponentID()));
        }
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

    public void setImplementedInterfaces(Set<IModelLayer> implementedInterface, int removeCascadeDepth) {
        implCapsule.setImplementedInterfaces(implementedInterface, removeCascadeDepth);
    }

    public void setImplementedInterfaces(Set<IModelLayer> implementedInterface) {
        implCapsule.setImplementedInterfaces(implementedInterface, 0);
    }

    public void addImplementedInterface(IModelLayer implementedInterface) {
        implCapsule.addImplementedInterface(implementedInterface);
    }

    public void removeImplementedInterfaces(String id, int removeCascadeDepth) {
        implCapsule.removeImplementedInterfaces(id, removeCascadeDepth);
    }

    public void removeImplementedInterfaces(String id) {
        implCapsule.removeImplementedInterfaces(id, 0);
    }

    public Map<String, IModelLayer> getImplementedInterfaces() {
        return implCapsule.getImplementedInterfaces();
    }

    public void setExtendedElement(IModelLayer element) {
        extendsCapsule.setExtendedElement(element);
    }

    public void setExtendedElementID(String elementID) {
        extendsCapsule.setExtendedElementID(elementID);
    }

    public IModelLayer getExtendedElement() {
        return extendsCapsule.getExtendedElement();
    }

    public String getExtendedElementID() {
        return extendsCapsule.getExtendedElementID();
    }

    public boolean isExtension() {
        return extendsCapsule.isExtension();
    }

    public void removeFromContainer() {
        if (model != null)
            model.removeElement(getModelComponentID());
        model = null;
    }
}


