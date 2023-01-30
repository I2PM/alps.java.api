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
protected final IImplementsFunctionalityCapsule<IModelLayer> implCapsule;
protected final IExtendsFunctionalityCapsule<IModelLayer> extendsCapsule;
protected int priorityNumber;
protected IPASSProcessModel model;
protected LayerType layerType = LayerType.STANDARD;
protected boolean isAbstractType = false;
protected IModelLayer extendedLayer;

private final String ABSTRACT_NAME = "AbstractLayer";
@Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new ModelLayer();
        }

protected ModelLayer()
        {
        implCapsule = new ImplementsFunctionalityCapsule<IModelLayer>(this);
        extendsCapsule = new ExtendsFunctionalityCapsule<IModelLayer>(this);
        }
/**
 * Name of the class, needed for parsing
 */
    private final String className = "ModelLayer";
protected String exportClassname = className;
@Override
public String getClassName()
        {
        return exportClassname;
        }
@Override
protected String getExportTag()
        {
        return OWLTags.abstr;
        }


public ModelLayer(IPASSProcessModel model, String labelForID, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute){
        super(labelForID, comment, additionalLabel, additionalAttribute);
        }
        {
        extendsCapsule = new ExtendsFunctionalityCapsule<IModelLayer>(this);
        implCapsule = new ImplementsFunctionalityCapsule<IModelLayer>(this);
        setContainedBy(model);
        }


    /**
     * Returns a dictionary of all elements saved in the model layer
     * @return
     */
    public Map<String, IPASSProcessModelElement> getElements()
        {
        return new HashMap<String, IPASSProcessModelElement>(elements);
        }

public IPASSProcessModelElement getElement(String id)
        {
        if (elements.containsKey(id))
        {
        return elements[id];
        }
        return null;
        }

protected void checkLayerTypes()
        {
        if (elements.stream().filter()(e -> instanceof IALPSModelElement).collect(Collectors.toList().size()== 0);
        {
        setIsAbstract(false);
        }
        else
        {
        setIsAbstract(true);
        }
        for(IPASSProcessModelElement element: getElements().values())
        {
        if (element instanceof IGuardExtension)
        {
        setLayerType(LayerType.GUARD);
        return;
        }
        else if (element instanceof IMacroExtension)
        {
        setLayerType(LayerType.MACRO);
        return;
        }
        else if (element instanceof ISubjectExtension)
        {
        setLayerType(LayerType.EXTENSION);
        return;
        }

        }
        setLayerType(LayerType.STANDARD);
        }


public void setLayerType(LayerType layerType)
        {
        switch (layerType)
        {
        case LayerType.GUARD:
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
        exportClassname = "Guard" + className;
        this.layerType = layerType;
        addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
        break;
        case LayerType.EXTENSION:
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
        this.layerType = layerType;
        exportClassname = "Extension" + className;
        addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
        break;
        case LayerType.MACRO:
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
        this.layerType = layerType;
        exportClassname = "Macro" + className;
        addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
        break;
        case LayerType.BASE:
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
        this.layerType = layerType;
        exportClassname = "Base" + className;
        addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
        break;
        case LayerType.STANDARD:
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
        this.layerType = layerType;
        exportClassname = className;
        addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
        break;
default:

        break;

        }
        }

public LayerType getLayerType()
        {
        return layerType;
        }

public void setIsAbstract(boolean isAbstract)
        {
        this.isAbstractType = isAbstract;
        if (isAbstract)
        {
        addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + ABSTRACT_NAME));
        }
        else
        {
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + ABSTRACT_NAME));
        }
        }

public boolean isAbstract()
        {
        return isAbstractType;
        }

public boolean removeContainedElement(String modelComponentID, int removeCascadeDepth)
        {
        if (modelComponentID == null) return false;
        if (elements.tryGetValue(modelComponentID, out IPASSProcessModelElement element))
        {
        elements.remove(modelComponentID);
        element.unregister(this, removeCascadeDepth);
        if (element instanceof IContainableElement<IModelLayer> containable && containable.getContainedBy(out IModelLayer layer) && layer == this)
        containable.removeFromContainer();
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
     * @param element The element that will be added
     */
    public void addElement(IPASSProcessModelElement element)
        {
        if (element == null) { return; }
        if (element instanceof IInteractionDescribingComponent || element instanceof ISubjectBehavior)
        if (elements.tryAdd(element.getModelComponentID(), element))
        {
        if (element instanceof ISubjectExtension subjExt)
        {
        if (!(element instanceof IMacroExtension) && getLayerType() == LayerType.MACRO
        || !(element instanceof IGuardExtension) && getLayerType() == LayerType.GUARD
        || (element instanceof IMacroExtension || element instanceof IGuardExtension) && getLayerType() == LayerType.EXTENSION
        || (element instanceof IGuardReceiveState) && getLayerType() != LayerType.GUARD)
        {
        elements.remove(element.getModelComponentID());
        return;
        }
        for(ISubjectExtension ext: getElements().values().OfType<ISubjectExtension>())
        {
        if (ext.getExtendedSubject() != null && subjExt.getExtendedSubject() != null && ext.getExtendedSubject().Equals(subjExt.getExtendedSubject()))
        {
        elements.remove(element.getModelComponentID());
        return;
        }
        }
        }
        publishElementAdded(element);
        element.register(this);

        checkLayerTypes();
        if (element instanceof IContainableElement<IModelLayer> containable)
        containable.setContainedBy(this);
        addTriple(new IncompleteTriple(OWLTags.stdContains, element.getUriModelComponentID()));
        }
        }


    /**
     * Returns a fully specified subject depending on its position
     * @param numberOfElement the position in the list of subjects
     * @return
     */
    public IFullySpecifiedSubject getFullySpecifiedSubject(int numberOfElement)
        {
        return elements.values().OfType<IFullySpecifiedSubject>().ElementAt(numberOfElement);
        }


    /**
     * @param numberOfElement
     * @return
     */
    public IInterfaceSubject getInterfaceSubject(int numberOfElement)
        {
        return elements.values().OfType<IInterfaceSubject>().ElementAt(numberOfElement);
        }


    /**
     *  Returns a multi subject depending on its position
     * @param numberOfElement
     * @return
     */
    public IMultiSubject getMultiSubject(int numberOfElement)
        {
        return elements.values().OfType<IMultiSubject>().ElementAt(numberOfElement);
        }


    /**
     *Returns a single subject depending on its position
     * @param numberOfElement
     * @return
     */
    public ISingleSubject getSingleSubject(int numberOfElement)
        {
        return elements.values().OfType<ISingleSubject>().ElementAt(numberOfElement);
        }

    /**
     * Returns a message exchange depending on its position
     * @param numberOfElement
     * @return
     */
    public IMessageExchange getMessageExchange(int numberOfElement)
        {
        return elements.values().OfType<IMessageExchange>().ElementAt(numberOfElement);
        }


    /**
     * Returns an input pool constraint depending on its position
     * @param numberOfElement
     * @return The object
     */
    public IInputPoolConstraint getInputPoolConstraint(int numberOfElement)
        {
        return elements.values().OfType<IInputPoolConstraint>().ElementAt(numberOfElement);
        }


    /**
     * Returns a message sender type constraint depending on its position
     * @param numberOfElement
     * @return The object
     */
    public IMessageSenderTypeConstraint getMessageSenderTypeConstraint(int numberOfElement)
        {
        return elements.values().OfType<IMessageSenderTypeConstraint>().ElementAt(numberOfElement);
        }


    /**
     * Returns a sender type constraint depending on its position
     * @param numberOfElement
     * @return The object
     */
    public ISenderTypeConstraint getSenderTypeConstraint(int numberOfElement)
        {
        return elements.values().OfType<ISenderTypeConstraint>().ElementAt(numberOfElement);
        }


    /**
     * Returns a message type constraint depending on its position
     * @param numberOfElement
     * @return The object
     */
    public IMessageTypeConstraint getMessageTypeConstraint(int numberOfElement)
        {
        return elements.values().OfType<IMessageTypeConstraint>().ElementAt(numberOfElement);
        }


    /**
     * Returns a input pool constraint handling strategy depending on its position
     * @param numberOfElement
     * @return The object
     */
    public IInputPoolConstraintHandlingStrategy getInputPoolConstraintHandlingStrategy(int numberOfElement)
        {
        return elements.values().OfType<IInputPoolConstraintHandlingStrategy>().ElementAt(numberOfElement);
        }


    /**
     * Returns the message exchange list depending on its position
     * @param numberOfElement
     * @return the object
     */
    public IMessageExchangeList getMessageExchangeList(int numberOfElement)
        {
        return elements.values().OfType<IMessageExchangeList>().ElementAt(numberOfElement);
        }


    /**
     * Returns a message specification depending on its position
     * @param numberOfElement
     * @return The object
     */
    public IMessageSpecification getMessageSpecification(int numberOfElement)
        {
        return elements.values().OfType<IMessageSpecification>().ElementAt(numberOfElement);
        }
@Override
protected void successfullyParsedElement(IParseablePASSProcessModelElement parsedElement)
        {
        if (parsedElement instanceof IContainableElement<IModelLayer> containable)
        containable.setContainedBy(this);
        }
@Override
protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
        {
        if (implCapsule != null && implCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
        return true;
        else if (extendsCapsule != null && extendsCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
        return true;
        else if (element != null)
        {
        if (predicate.contains(OWLTags.ccontains))
        {
        addElement(element);
        return true;
        }
        else if (predicate.contains(OWLTags.eextends) && element instanceof IModelLayer layer)
        {
        setExtendedLayer(layer);
        return true;
        }
        }
        else
        {
        if (predicate.contains(OWLTags.type))
        {
        if (objectContent.contains("MacroLayer"))
        {
        setLayerType(LayerType.MACRO);
        return true;
        }
        else if (objectContent.contains("GuardLayer"))
        {
        setLayerType(LayerType.GUARD);
        return true;
        }
        else if (objectContent.contains("ExtensionLayer"))
        {
        setLayerType(LayerType.EXTENSION);
        return true;
        }
        else if (objectContent.contains("BaseLayer"))
        {
        setLayerType(LayerType.BASE);
        return true;
        }
        else if (objectContent.contains(ABSTRACT_NAME))
        {
        setIsAbstract(true);
        return true;
        }
        }
        else
        {
        if (predicate.contains(OWLTags.hasPriorityNumber))
        {
        String prio = objectContent;
        prio = prio.split("^")[0];
        setPriorityNumber(Integer.parse(prio));
        return true;
        }
        }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
        }

public void updateAdded(IPASSProcessModelElement update, IPASSProcessModelElement caller)
        {
        super.updateAdded(update, caller);

        if (getContainedBy(out IPASSProcessModel model))
        {
        // If the element is already in another layer, do not add it
        for(IModelLayer layer: model.getAllElements().values().OfType<IModelLayer>())
        {
        if (layer.getElements().containsKey(update.getModelComponentID()))
        {
        return;
        }
        }

        addElement(update);
        model.updateAdded(update, caller);
        }
        else
        {
        addElement(update);
        }
        }

public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth)
        {
        super.updateRemoved(update, caller);
        removeContainedElement(update.getModelComponentID(), removeCascadeDepth);

        }


@Override
public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification)
        {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for(IPASSProcessModelElement element: getElements().values())
        baseElements.add(element);
        if (getContainedBy() != null)
        baseElements.add(getContainedBy());
        return baseElements;
        }


public void setPriorityNumber(int nonNegativInteger)
        {
        removeTriple(new IncompleteTriple(OWLTags.stdHasPriorityNumber, this.priorityNumber.toString(), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
        if (nonNegativInteger > 0) priorityNumber = nonNegativInteger;
        else priorityNumber = 1;
        addTriple(new IncompleteTriple(OWLTags.stdHasPriorityNumber, priorityNumber.toString(), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
        }

public int getPriorityNumber()
        {
        return priorityNumber;
        }


public boolean getContainedBy(out IPASSProcessModel model)
        {
        model = this.model;
        return this.model != null;
        }

public void setContainedBy(IPASSProcessModel container)
        {
        if (container == null) return;
        if (container.equals(model)) return;
        this.model = container;
        this.model.addLayer(this);
        }

public IPASSProcessModel getContainedBy()
        {
        return this.model;
        }
@Override
public void notifyModelComponentIDChanged(String oldID, String newID)
        {
        if (elements.containsKey(oldID))
        {
        IPASSProcessModelElement element = elements[oldID];
        elements.remove(oldID);
        elements.add(element.getModelComponentID(), element);
        }
        super.notifyModelComponentIDChanged(oldID, newID);
        }



public void setExtendedLayer(IModelLayer extendedLayer, int removeCascadeDepth)
        {
        IModelLayer oldExtendedLayer = this.extendedLayer;
        // Might set it to null
        this.extendedLayer = extendedLayer;

        if (oldExtendedLayer != null)
        {
        if (oldExtendedLayer.equals(extendedLayer)) return;
        removeTriple(new IncompleteTriple(OWLTags.abstrExtends, oldExtendedLayer.getUriModelComponentID()));
        }

        if (!(extendedLayer == null))
        {
        addTriple(new IncompleteTriple(OWLTags.abstrExtends, extendedLayer.getUriModelComponentID()));
        }
        }

public void setImplementedInterfacesIDReferences(Set<String> implementedInterfacesIDs)
        {
        implCapsule.setImplementedInterfacesIDReferences(implementedInterfacesIDs);
        }

public void addImplementedInterfaceIDReference(String implementedInterfaceID)
        {
        implCapsule.addImplementedInterfaceIDReference(implementedInterfaceID);
        }

public void removeImplementedInterfacesIDReference(String implementedInterfaceID)
        {
        implCapsule.removeImplementedInterfacesIDReference(implementedInterfaceID);
        }

public Set<String> getImplementedInterfacesIDReferences()
        {
        return implCapsule.getImplementedInterfacesIDReferences();
        }

public void setImplementedInterfaces(Set<IModelLayer> implementedInterface, int removeCascadeDepth)
        {
        implCapsule.setImplementedInterfaces(implementedInterface, removeCascadeDepth);
        }

public void addImplementedInterface(IModelLayer implementedInterface)
        {
        implCapsule.addImplementedInterface(implementedInterface);
        }

public void removeImplementedInterfaces(String id, int removeCascadeDepth)
        {
        implCapsule.removeImplementedInterfaces(id, removeCascadeDepth);
        }

public Map<String, IModelLayer> getImplementedInterfaces()
        {
        return implCapsule.getImplementedInterfaces();
        }

public void setExtendedElement(IModelLayer element)
        {
        extendsCapsule.setExtendedElement(element);
        }

public void setExtendedElementID(String elementID)
        {
        extendsCapsule.setExtendedElementID(elementID);
        }

public IModelLayer getExtendedElement()
        {
        return extendsCapsule.getExtendedElement();
        }

public String getExtendedElementID()
        {
        return extendsCapsule.getExtendedElementID();
        }

public boolean isExtension()
        {
        return extendsCapsule.isExtension();
        }

public void removeFromContainer()
        {
        if (model != null)
        model.removeElement(getModelComponentID());
        model = null;
        }
        }


