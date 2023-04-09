package alps.java.api.StandardPASS;

import alps.java.api.util.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class specifies the most general interface for elements contained inside a PASS model
 * From PASS Doc All sub-class/instances of subclasses of a PASS-ProcessModelElement can be considered elements of PASS Process Models.
 * Every element/sub-class of SimplePASSElement is also a child of PASSProcessModelElement.
 * This is simply a surrogate class to group all simple elements together and differ them from StandardPASS
 */
public interface IPASSProcessModelElement extends IValueChangedPublisher<IPASSProcessModelElement>, IValueChangedObserver<IPASSProcessModelElement> {
    /**
     * In some cases the full model component id (with uri) is needed.
     * If no graph exists, the baseUri is just a tag added in front acting as placeholder for the real uri.
     *
     * @return the id with the uri in front
     */
    String getUriModelComponentID();

    /**
     * Returns the unique id the identifies the current elemen
     *
     * @return
     */
    String getModelComponentID();

    /**
     * Attempts to remove the elment from every part that contains this element (model, layer, behavior...)
     *
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void removeFromEverything(int removeCascadeDepth);

    /**
     * Attempts to remove the elment from every part that contains this element (model, layer, behavior...)
     */
    void removeFromEverything();

    /**
     * Sets the unique id for the current model.
     * Sets the id to the exact passed value.
     * The user must assure that the id is unique inside the current model,
     * otherwise exceptions might be thrown while using the model.
     * To safely create a component with a unique id, use {@link "createUniqueModelComponentID(string)"}
     *
     * @param id the id that will be set as modelComponentID
     */
    void setModelComponentID(String id);


    /**
     * Creates a unique id for the element using a specified label.
     * For this, a {@link "Guid"} is used.
     * The specified label is only used to provide simpler reading.
     * Leaving the label blank or not passing anything causes the element to use guid-only as id.
     *
     * @param labelForID the label that is used together with a guid to generate a valid and unique identifier for the element
     * @param addLabel   If set to true, a modelComponentLabel is also added for the given label.
     *                   If false, only the id will be created.
     * @return the created id for the current element
     */
    String createUniqueModelComponentID(String labelForID, boolean addLabel);

    /**
     * Creates a unique id for the element using a specified label.
     * For this, a {@link "Guid"} is used.
     * The specified label is only used to provide simpler reading.
     * Leaving the label blank or not passing anything causes the element to use guid-only as id.
     *
     * @param labelForID the label that is used together with a guid to generate a valid and unique identifier for the element
     *                   If false, only the id will be created.
     * @return the created id for the current element
     */
    String createUniqueModelComponentID(String labelForID);

    /**
     * Creates a unique id for the element using a specified label.
     * For this, a {@link "Guid"} is used.
     * The specified label is only used to provide simpler reading.
     * Leaving the label blank or not passing anything causes the element to use guid-only as id.
     * the default value of addLabel will be set to true
     *
     * @return the created id for the current element
     */
    String createUniqueModelComponentID();


    /**
     * Method that sets the model component label list, overriding the previous content
     *
     * @param modelComponentLabel the model component label list
     */
    void setModelComponentLabels(List<String> modelComponentLabel);

    /**
     * Method that sets the model component label attribute
     *
     * @param modelComponentLabel the model component label
     */
    void addModelComponentLabel(String modelComponentLabel);

    /**
     * Method that returns the model component labels as strings.
     * Additional language info can be added to each string by setting addLanguageAttribute true
     *
     * @param addLanguageAttribute If set to true, information about the language the label is written in is included in the each label
     * @return The model component label list
     */
    List<String> getModelComponentLabelsAsStrings(boolean addLanguageAttribute);

    /**
     * Method that returns the model component labels as strings.
     * Additional language info can be added to each string by setting addLanguageAttribute true
     * the default value of the param addLanguageAttributeis false
     *
     * @return The model component label list
     */
    List<String> getModelComponentLabelsAsStrings();

    /**
     * Method that returns the model component labels as objects with additional language info
     *
     * @return
     */
    List<IStringWithExtra> getModelComponentLabels();

    /**
     * Clears the list of model component labels
     */
    void clearModelComponentLabels();

    /**
     * Removes a label that has the specified string as content
     *
     * @param label
     */
    void removeModelComponentLabel(String label);

    /**
     * Method that adds a comment attribute
     *
     * @param comment the comment
     */
    void addComment(String comment);

    /**
     * Method that returns the comment attribute
     *
     * @return The comment attribute
     */
    List<String> getComments();

    /**
     * Clears the list of comments
     */
    void clearComments();

    /**
     * Adds an element that is in some undefined relation to the current element.
     * This method is only for adding additional elements/information to another element that cannot be added in another way (using specified methods etc.)
     *
     * @param element the new element
     */
    void addElementWithUnspecifiedRelation(IPASSProcessModelElement element);

    /**
     * @return A set of elements that are in some undefined relation to the current element
     */
    Map<String, IPASSProcessModelElement> getElementsWithUnspecifiedRelation();

    /**
     * Overrides the elements that are in some undefined relation to the current element.
     * This method is only for adding additional elements/information to another element that cannot be added in another way (using specified methods etc.)
     *
     * @param elements the new elements
     */
    void setElementsWithUnspecifiedRelation(Set<IPASSProcessModelElement> elements);

    /**
     * Removes an elements that is in some undefined relation to the current element.
     *
     * @param id the id of the element
     */
    void removeElementWithUnspecifiedRelation(String id);
}
