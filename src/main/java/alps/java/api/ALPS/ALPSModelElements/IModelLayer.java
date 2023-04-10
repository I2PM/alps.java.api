package alps.java.api.ALPS.ALPSModelElements;

import alps.java.api.ALPS.IALPSModelElement;
import alps.java.api.StandardPASS.*;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.*;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints.IMessageSenderTypeConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints.IMessageTypeConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints.ISenderTypeConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IFullySpecifiedSubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IInterfaceSubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IMultiSubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.ISingleSubject;
import alps.java.api.util.*;

import java.util.Map;

/**
 * Defines an interface for a model layer
 */
public interface IModelLayer extends IALPSModelElement, IPrioritizableElement, IContainableElement<IPASSProcessModel>,
        IImplementingElementT<IModelLayer>, IExtendingElement<IModelLayer>, IAbstractElement {
    /**
     * Represents the type of the layer
     */
    public enum LayerType {
        STANDARD,
        BASE,
        EXTENSION,
        MACRO,
        GUARD
    }


    /**
     * Sets the layertype for the layer
     *
     * @param layerType
     */
    void setLayerType(LayerType layerType);

    /**
     * Returns the layer type for the current layer
     *
     * @return
     */
    LayerType getLayerType();


    /**
     * Returns all elements contained inside the layer.
     * The key is the ModelComponentID to each value item.
     *
     * @return A dictionary containing all elements
     */
    Map<String, IPASSProcessModelElement> getElements();

    /**
     * Adds an element to the layer
     *
     * @param value
     */
    void addElement(IPASSProcessModelElement value);

    /**
     * @param id
     * @return
     */
    IPASSProcessModelElement getElement(String id);

    /**
     * @param extendedLayer
     * @param removeCascadeDepth
     */
    public void setExtendedLayer(IModelLayer extendedLayer, int removeCascadeDepth);

    /**
     * @param extendedLayer
     */
    public void setExtendedLayer(IModelLayer extendedLayer);

    /**
     * Returns a fully specified subject depending on its position
     * (inside the list of all fully specified subjects in the layer)
     *
     * @param numberOfElement the position
     * @return
     */
    IFullySpecifiedSubject getFullySpecifiedSubject(int numberOfElement);


    /**
     * Returns an interface subject depending on its position
     * (inside the list of interface subjects in the layer)
     *
     * @param numberOfElement the position
     * @return
     */
    IInterfaceSubject getInterfaceSubject(int numberOfElement);

    /**
     * Returns a multi subject depending on its position
     * (inside the list of interface subjects in the layer)
     *
     * @param numberOfElement the position
     * @return
     */
    IMultiSubject getMultiSubject(int numberOfElement);


    /**
     * Returns a single subject depending on its position
     * (inside the list of single subjects in the layer)
     *
     * @param numberOfElement the position
     * @return
     */
    ISingleSubject getSingleSubject(int numberOfElement);


    /**
     * Returns a message exchange depending on its position
     * (inside the list of message exchanges in the layer)
     *
     * @param numberOfElement the position
     * @return
     */
    IMessageExchange getMessageExchange(int numberOfElement);


    /**
     * Returns an input pool constraint depending on its position
     * (inside the list of input pool constraints in the layer)
     *
     * @param numberOfElement the position
     * @return
     */
    IInputPoolConstraint getInputPoolConstraint(int numberOfElement);


    /**
     * Returns a message sender type constraint depending on its position
     * (inside the list of message sender type constraints in the layer)
     *
     * @param numberOfElement the position
     * @return
     */
    IMessageSenderTypeConstraint getMessageSenderTypeConstraint(int numberOfElement);


    /**
     * Returns a message type constraint depending on its position
     * (inside the list of message type constraints in the layer)
     *
     * @param numberOfElement the position
     * @return
     */
    IMessageTypeConstraint getMessageTypeConstraint(int numberOfElement);


    /**
     * Returns a sender type constraint depending on its position
     * (inside the list of sender type constraints in the layer)
     *
     * @param numberOfElement the position
     * @return
     */
    ISenderTypeConstraint getSenderTypeConstraint(int numberOfElement);


    /**
     * Returns an input pool constraint handling strategy depending on its position
     * (inside the list of input pool constraint handling strategies in the layer)
     *
     * @param numberOfElement the position
     * @return
     */
    IInputPoolConstraintHandlingStrategy getInputPoolConstraintHandlingStrategy(int numberOfElement);

    /**
     * Returns a message exchange list depending on its position
     * (inside the list of message exchange lists in the layer)
     *
     * @param numberOfElement the position
     * @return
     */
    IMessageExchangeList getMessageExchangeList(int numberOfElement);

    /**
     * Returns a message exchange list depending on its position
     * (inside the list of message exchange lists in the layer)
     *
     * @param numberOfElement the position
     * @return
     */
    IMessageSpecification getMessageSpecification(int numberOfElement);

    /**
     * Deletes an element depending on its id, if it is contained inside the layer
     *
     * @param modelComponentID   the id of the element
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     * @return
     */
    boolean removeContainedElement(String modelComponentID, int removeCascadeDepth);

    /**
     * Deletes an element depending on its id, if it is contained inside the layer
     *
     * @param modelComponentID the id of the element
     * @return
     */
    boolean removeContainedElement(String modelComponentID);


}

