package alps.java.api.StandardPASS;

import alps.java.api.FunctionalityCapsules.*;
import alps.java.api.parsing.*;
import alps.java.api.src.OWLTags;
import alps.java.api.util.*;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Resource;
import

import java.net.URI;
import java.util.*;

/**
 * Root class for the inheritans graphs. Represents a PASS process model element
 */
        public class PASSProcessModelElement implements ICapsuleCallback
        {

                /**
                 * This list contains the additional attributes as snd entry and the types for each of the additional attributes as first
                 */

                protected String exportSubjectNodeName = null;

                protected String BASE_URI_PLACEHOLDER = "baseuri:";

                protected final String EXAMPLE_BASE_URI = "http://www.imi.kit.edu/exampleBaseURI";
                public static final int CANNOT_PARSE = -1;

                protected final List<IValueChangedObserver<IPASSProcessModelElement>> observerList = new ArrayList<IValueChangedObserver<IPASSProcessModelElement>>();
                protected List<Triple> additionalAttributeTriples = new ArrayList<Triple>();
                protected List<IIncompleteTriple> additionalIncompleteTriples = new ArrayList<IIncompleteTriple>();


                protected Set<IStringWithExtra> modelComponentLabels = new HashSet<IStringWithExtra>();
                protected Set<IStringWithExtra> comments = new HashSet<IStringWithExtra>();
                protected ICompatibilityDictionary<String, IPASSProcessModelElement> additionalElements = new CompatibilityDictionary<String, IPASSProcessModelElement>();
                protected UUID guid = UUID.randomUUID();
                protected String modelComponentID = "";
                protected IPASSGraph exportGraph;

                /**
                 * Name of the class, needed for parsing
                 */
                private final String className = "PASSProcessModelElement";


                public String getClassName()
                        {
                        return className;
                        }

                protected String getExportTag()
                        {
                        return OWLTags.std;
                        }


                /**
                 *  Constructor that creates a fully specified instance of the PASS Process Model Element class
                 * @param labelForID a string describing the element used to generate the model id
                 * @param comment the comment
                 * @param additionalLabel
                 * @param additionalAttributes ist of additional attributes
                 */
                //TODO: KOnstruktor überladen
                public PASSProcessModelElement(String labelForID, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttributes)
                        {
                        if (labelForID == null || labelForID.equals(""))
                        createUniqueModelComponentID(getClassName(), false);
                        else
                        createUniqueModelComponentID(labelForID, (additionalLabel == null || additionalLabel.equals("")));

                        if (additionalLabel != null && !additionalLabel.equals(""))
                        addModelComponentLabel(additionalLabel);
                        if (comment != null && !comment.equals(""))
                        addComment(comment);

                        if (additionalAttributes != null)
                        addTriples(additionalAttributes);

                        addTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.stdNamedIndividual));
                        addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));

                        }


                /**
                 * Adds an incomplete Triple to the element that will either be parsed right away or delayed,
                 * depending on whether there is a graph with a base uri available or not.
                 * @param triple the triple that is being saved
                 */
                public void addTriple(IIncompleteTriple triple)
                        {
                        if (containsTriple(triple)) return;
                        if (exportGraph == null)
                        {
                        additionalIncompleteTriples.add(triple);
                        IStringWithExtra extraString = triple.getObjectWithExtra();
                        parseAttribute(triple.getPredicate(), extraString.getContent(), getExtraOrNullLang(extraString), getExtraOrNullData(extraString), null);
                        }
                        else
                        {
                        completeIncompleteTriple(triple);
                        }
                        }
                private String getExtraOrNullLang(Object extraString){
                        if(extraString instanceof LanguageSpecificString){
                                return ((LanguageSpecificString) extraString).getExtra();
                        }else{
                                return null;
                        }
                }

                private String getExtraOrNullData(Object extraString){
                        if(extraString instanceof DataTypeString){
                                return ((DataTypeString) extraString).getExtra();
                        }else{
                                return null;
                        }
                }

                /**
                 * Adds a  list ofIncomplete Triple to the element that will either be parsed right away, or delayed
                 * (depending on whether there is a graph available or not)
                 * @param triples the triple that is being saved
                 */
                /* public void addTriples(List<IIncompleteTriple> triples)
                        {
                        if (triples != null)
                        {
                        for (IIncompleteTriple triple:  triples)
                        addTriple(triple);
                        }
                        } */

                /**
                 * Adds a list of complete triples to the element.
                 * If the element contains an Incomplete Triple containing the same information as a complete triple, the incomplete will be deleted.
                 * The content of the triple will be parsed if possible.
                 * @param triples
                 */
                public void addTriples(List<Triple> triples)
                        {
                        if (triples != null)
                        {
                        for (Triple triple: triples)
                        {
                        addTriple(triple);
                        }
                        }
                        }

                /// <summary>
                /// Adds a complete triple to the element.
                /// If the element contains an Incomplete Triple containing the same information, it will be deleted.
                /// The content of the triple will be parsed if possible.
                /// </summary>
                /// <param name="triples"></param>
                public void addTriple(Triple triple)
                        {
                        // Do not add the triple if it is alreay contained
                        if (triple == null)
                        return;
                        if (additionalAttributeTriples.contains(triple)) return;

                        // If a graph with a base URI is available, the base uri of the Triple might clash with the base uri defined by the graph.
                        // Convert the triple to an incomplete triple that is parsed back to a Triple using the graphs uri
                        IIncompleteTriple incTriple = new IncompleteTriple(triple);
                        if (!(exportGraph == null))
                        addTriple(incTriple);

                        // If no graph is available
                        else
                        {
                        // Remove all incomplete triples that are encoding the same information
                        if (getIncompleteTriple(incTriple) != null)
                        removeTriple(incTriple);

                        // Parse the information encoded by the triple
                        additionalAttributeTriples.add(triple);
                        parseAttribute(triple, _);
                        }
                        }

                /// <summary>
                /// Tries to parse an incomplete triple and add it as complete triple
                /// this is only possible if a graph is given. A valid base uri must not be provided,
                /// otherwise an example base uri is used.
                /// </summary>
                /// <param name="triple">The incomplete triple</param>
                protected void completeIncompleteTriple(IIncompleteTriple triple)
                        {
                        if (exportGraph == null) return;
                        Resource subjectNode;


                        // Generate subject node uri from modelComponentID
                        if (exportSubjectNodeName == null || exportSubjectNodeName.equals(""))
                        {
                        subjectNode = exportGraph.createUriNode(StaticFunctions.addGenericBaseURI(getModelComponentID()));
                        }
                        // Generate it from preset name
                        else
                        {

                        if (exportSubjectNodeName.equals(getBaseURI()))
                        subjectNode = exportGraph.createUriNode(new URI(getBaseURI()));
                        else
                        subjectNode = exportGraph.createUriNode(StaticFunctions.addGenericBaseURI(exportSubjectNodeName));

                        }


                        // other nodes are evaluated from the provided incomplete triple
                        Triple completeTriple = triple.getRealTriple(exportGraph, subjectNode);

                        additionalAttributeTriples.add(completeTriple);
                        exportGraph.addTriple(completeTriple);
                        additionalIncompleteTriples.remove(triple);
                        }

                public List<Triple> getTriples()
                        {
                        return new List<Triple>(additionalAttributeTriples);
                        }
                public List<IIncompleteTriple> getIncompleteTriples()
                        {
                        return new List<IIncompleteTriple>(additionalIncompleteTriples);
                        }

                /// <summary>
                /// Removes a triple from either the complete or incomplete triples.
                /// </summary>
                /// <param name="incTriple">An incomplete triple coding the value that should be deleted.
                /// The deleted object must not be incomplete, but can as well be a complete triple</param>
                /// <returns></returns>
                public boolean removeTriple(IIncompleteTriple incTriple)
                        {
                        Triple foundTriple = getTriple(incTriple);
                        if (foundTriple != null)
                        {
                        if (exportGraph != null) exportGraph.removeTriple(foundTriple);
                        return additionalAttributeTriples.remove(foundTriple);
                        }
                        IIncompleteTriple foundIncompleteTriple = getIncompleteTriple(incTriple);
                        if (foundIncompleteTriple != null)
                        return additionalIncompleteTriples.remove(foundIncompleteTriple);
                        return false;
                        }

                /// <summary>
                /// Determines wheter there is a triple (complete or incomplete), holding the same data as the given incomplete triple
                /// </summary>
                /// <param name="incTriple">An incomplete triple coding the value that should be found.
                /// The found object must not be incomplete, but can as well be a complete triple</param>
                /// <returns></returns>
                public boolean containsTriple(IIncompleteTriple incTriple)
                        {
                        return (getTriple(incTriple) != null) || (getIncompleteTriple(incTriple) != null);
                        }

                /// <summary>
                /// Returns a real triple which contains the same data as the given incomplete triple.
                /// </summary>
                /// <param name="searchedTriple">An incomplete triple providing the data to be searched for</param>
                /// <returns></returns>
                protected Triple getTriple(IIncompleteTriple searchedTriple)
                        {
                        String predicateToSearchFor = NodeHelper.cutURI(searchedTriple.getPredicate());
                        String objectContentToSearchFor = NodeHelper.cutURI(searchedTriple.getObject());
                        for(Triple triple:  getTriples())
                        {
                        String predicateToMatch = NodeHelper.cutURI(NodeHelper.getNodeContent(triple.getPredicate()));
                        String objectContentToMatch = NodeHelper.cutURI(NodeHelper.getNodeContent(triple.Object));
                        if (predicateToSearchFor.equals(predicateToMatch) && objectContentToSearchFor.equals(objectContentToMatch))
                        return triple;
                        }
                        return null;
                        }

                /// <summary>
                /// Returns an incomplete triple which contains the same data as the given incomplete triple.
                /// </summary>
                /// <param name="searchedTriple">An incomplete triple providing the data to be searched for</param>
                /// <returns></returns>
                protected IIncompleteTriple getIncompleteTriple(IIncompleteTriple searchedTriple)
                        {
                        String predicateToSearchFor = NodeHelper.cutURI(searchedTriple.getPredicate());
                        String objectContentToSearchFor = NodeHelper.cutURI(searchedTriple.getObject());
                        for (IIncompleteTriple triple: getIncompleteTriples())
                        {
                        String predicateToMatch = NodeHelper.cutURI(triple.getPredicate());
                        String objectContentToMatch = NodeHelper.cutURI(triple.getObject());
                        if (predicateToSearchFor.equals(predicateToMatch) && objectContentToSearchFor.equals(objectContentToMatch))
                        return triple;
                        }
                        return null;
                        }

                /// <summary>
                /// Replaces an old triple with a new one.
                /// </summary>
                /// <param name="oldTriple">An incomplete triple holding the data to find the triple to be replaced</param>
                /// <param name="newTriple">An incomplete triple holding the data to replace the old triple</param>
                public void replaceTriple(IIncompleteTriple oldTriple, IIncompleteTriple newTriple)
                        {
                        if (oldTriple.equals(newTriple)) return;
                        if (oldTriple != null && containsTriple(oldTriple))
                        {
                        if (removeTriple(oldTriple))
                        addTriple(newTriple);
                        }
                        else
                        {
                        addTriple(newTriple);
                        }
                        }

                public String getBaseURI()
                        {
                        return PASSGraph.EXAMPLE_BASE_URI_PLACEHOLDER;
                        }

                // ############################ ModelComponentID ############################

                public String getModelComponentID()
                        {
                        return modelComponentID;
                        }

                public String getUriModelComponentID()
                        {
                        return StaticFunctions.addGenericBaseURI(getModelComponentID());
                        }

                public void setModelComponentID(String id)
                        {
                        if (id == null || id.equals("")) return;
                        String oldID = modelComponentID;
                        if (!id.equals(oldID))
                        {
                        String modifiedID = id.trim().replace(" ", "_");

                        IIncompleteTriple oldTriple = new IncompleteTriple(OWLTags.stdHasModelComponentID, getModelComponentID(), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString);

                        modelComponentID = modifiedID;

                        IIncompleteTriple newTriple = new IncompleteTriple(OWLTags.stdHasModelComponentID, modifiedID, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString);

                        replaceTriple(oldTriple, newTriple);

                        invalidateTriplesContainingString(oldID);
                        if (exportGraph != null) exportGraph.modelComponentIDChanged(oldID, id);
                        publishNewModelComponentID(oldID);
                        }
                        }


                /// <summary>
                /// This method is used to replace invalid triples with incomplete representations.
                /// A triple might be invalid if the base URI of the model graph or the ModelComponentID of the current element has changed.
                /// On such a change, the nodes inside the graph must be updated.
                /// Therefore, the triples are stored as incomplete triples, which are later parsed back to Triples once the new id or URI is known.
                /// </summary>
                /// <param name="containedString"></param>
                protected void invalidateTriplesContainingString(String containedString)
                        {
                        List<IIncompleteTriple> triplesToBeChanged = new List<IIncompleteTriple>();
                        IIncompleteTriple owlNamedIndivTriple = null;
                        for (Triple triple: getTriples())
                        {
                        if (triple.toString().contains(containedString))
                        {
                        IIncompleteTriple newTriple = new IncompleteTriple(triple);
                        if (newTriple.getPredicate().contains("http://www.w3.org/1999/02/22-rdf-syntax-ns#type") && newTriple.getObject().contains("http://www.w3.org/2002/07/owl#NamedIndividual"))
                        owlNamedIndivTriple = newTriple;
                        else
                        triplesToBeChanged.add(newTriple);
                        }
                        }




                        for (IIncompleteTriple triple: triplesToBeChanged)
                        {
                        removeTriple(triple);
                        }

                        // Needed as the underlying graph behaves weirdly and does not type the element with NamedIndividual
                        if (owlNamedIndivTriple != null)
                        {
                        removeTriple(owlNamedIndivTriple);
                        }
                        //

                        for (IIncompleteTriple triple: triplesToBeChanged)
                        {
                        addTriple(triple);
                        }

                        // Needed as the underlying graph behaves weirdly and does not type the element with NamedIndividual
                        if (owlNamedIndivTriple != null)
                        {
                        addTriple(owlNamedIndivTriple);
                        }
                        //
                        }


                public String createUniqueModelComponentID(String labelForID, boolean addLabel)
                        {
                        if (labelForID == null || labelForID.equals(""))
                        {
                        setModelComponentID(guid.toString());
                        }
                        else
                        {
                        setModelComponentID(labelForID + ("-") + guid.toString());
                        if (addLabel) addModelComponentLabel(labelForID);
                        }
                        return getModelComponentID();
                        }

                // ############################ ModelComponent labels ############################

                public void setModelComponentLabels(List<String> modelComponentLabels)
                        {
                        clearModelComponentLabels();
                        for (String label: modelComponentLabels)
                        {
                        addModelComponentLabel(label);
                        }
                        }

                public void addModelComponentLabel(String modelComponentLabel)
                        {
                        IStringWithExtra extraString = new LanguageSpecificString(modelComponentLabel);
                        modelComponentLabels.add(extraString);
                        addTriple(new IncompleteTriple(OWLTags.stdHasModelComponentLabel, extraString));

                        }

                public void addModelComponentLabel(IStringWithExtra modelComponentLabel)
                        {
                        for (IStringWithExtra label: modelComponentLabels)
                        {
                        if (label.equals(modelComponentLabel)) return;
                        }
                        if (modelComponentLabels.add(modelComponentLabel))
                        addTriple(new IncompleteTriple(OWLTags.stdHasModelComponentLabel, modelComponentLabel));
                        }

                public List<String> getModelComponentLabelsAsStrings(boolean addLanguageAttribute)
                        {
                        List<String> labels = new List<String>();
                        for(IStringWithExtra label: modelComponentLabels)
                        {
                        labels.add((addLanguageAttribute) ? label.toString() : label.getContent());
                        }
                        return labels;
                        }

                public List<IStringWithExtra> getModelComponentLabels()
                        {
                        return new List<IStringWithExtra>(modelComponentLabels);
                        }

                public void clearModelComponentLabels()
                        {
                        modelComponentLabels.clear();
                        }

                public void removeModelComponentLabel(String label)
                        {
                        if (label == null) return;
                        IStringWithExtra labelString = new LanguageSpecificString(label);
                        if (modelComponentLabels.contains(labelString))
                        modelComponentLabels.remove(labelString);
                        }

                // ############################ Comments ############################

                public void addComment(IStringWithExtra comment)
                        {
                        if (comment == null || comment.toString().equals("")) return;
                        comments.add(comment);
                        addTriple(new IncompleteTriple(OWLTags.rdfsComment, comment));
                        }

                public void addComment(String comment)
                        {
                        if (comment == null || comment.equals("")) return;
                        IStringWithExtra extraComment = new LanguageSpecificString(comment);
                        if (comments.add(extraComment))
                        addTriple(new IncompleteTriple(OWLTags.rdfsComment, extraComment));
                        }

                public List<String> getComments()
                        {
                        List<String> commentsAsString = new List<String>();
                        for(IStringWithExtra langString: comments)
                        {
                        commentsAsString.add(langString.toString());
                        }
                        return commentsAsString;
                        }

                public void clearComments()
                        {
                        comments.clear();
                        }




                public void completeObject(Map<String, IParseablePASSProcessModelElement> allElements)
                        {
                        if (parsingStarted) return;
                        parsingStarted = true;
                        List<IParseablePASSProcessModelElement> successfullyParsedElements = new List<IParseablePASSProcessModelElement>();
                        for(Triple triple: getTriples())
                        {
                        parseAttribute(triple, allElements, IParseablePASSProcessModelElement parsedElement);
                        if (parsedElement != null)
                        {
                        successfullyParsedElements.add(parsedElement);
                        }
                        }

                        for(IParseablePASSProcessModelElement element: successfullyParsedElements)
                        {
                        element.completeObject(allElements);
                        }
                        }

                protected boolean parsingStarted = false;

                protected boolean parseAttribute(Triple triple, IParseablePASSProcessModelElement parsedElement)
                        {

                        // Calling parsing method
                        // If attribute contains a reference to a PassProcessModelElement, pass this to the method
                        Map<String, IParseablePASSProcessModelElement> allElements = getDictionaryOfAllAvailableElements();
                        return parseAttribute(triple, allElements, parsedElement);
                        }

                protected boolean parseAttribute(Triple triple, Map<String, IParseablePASSProcessModelElement> allElements, IParseablePASSProcessModelElement parsedElement)
                        {

                        // Calling parsing method
                        // If attribute contains a reference to a PassProcessModelElement, pass this to the method
                        parsedElement = null;
                        setExportXMLName(NodeHelper.getNodeContent(triple.Subject));
                        String predicateContent = NodeHelper.getNodeContent(triple.Predicate);
                        String objectContent = NodeHelper.getNodeContent(triple.Object);
                        String lang = NodeHelper.getLangIfContained(triple.Object);
                        String dataType = NodeHelper.getDataTypeIfContained(triple.Object);
                        String possibleID = objectContent;
                        if (possibleID.split('#').Length > 1)
                        possibleID = possibleID.split('#')[possibleID.split('#').Length - 1];

                        if (triple.Subject != null && triple.Subject.ToString() != "")
                        {
                        exportSubjectNodeName = triple.Subject.ToString();
                        }

                        if (allElements != null && allElements.containsKey(possibleID))
                        {
                        if (parseAttribute(predicateContent, possibleID, lang, dataType, allElements[possibleID]) && allElements[possibleID] != this)
                        {
                        parsedElement = allElements[possibleID];
                        successfullyParsedElement(parsedElement);
                        return true;
                        }
                        return false;
                        }
                        else
                        {
                        return parseAttribute(predicateContent, objectContent, lang, dataType, null);
                        }

                        }

                protected void successfullyParsedElement(IParseablePASSProcessModelElement parsedElement) { return; }

                /// <summary>
                /// Provides access to the dictionary of all available elements inside the current model.
                /// </summary>
                /// <returns>A dictionary containing model component ids as keys and elements as values</returns>
                protected Map<String, IParseablePASSProcessModelElement> getDictionaryOfAllAvailableElements()
                        {
                        return null;
                        }

                /// <summary>
                /// Gets called while parsing a triple from a set of triples where this element is subject.
                /// The predicate and objectContent are derived directly from the triple,
                /// lang and dataType might be null (they will never both be NonNull at the same time)
                /// If the object specifies an uri to another element and the collection of all available elements contains this element,
                /// the element is passed as well
                /// </summary>
                /// <param name="predicate">the predicate contained by the triple</param>
                /// <param name="objectContent">the content of the object contained by the triple</param>
                /// <param name="lang">the lang attribute of the object if one was specified</param>
                /// <param name="dataType">the datatype attribute of the object if one was specified</param>
                /// <param name="element">the element the objectContent points to (if it does and the element exists)</param>
                /// <returns></returns>
                protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
                        {
                        if (predicate.toLowerCase().contains(OWLTags.rdfsComment))
                        {
                        addComment(new LanguageSpecificString(objectContent, lang));
                        return true;
                        }

                        else if (predicate.contains(OWLTags.hasModelComponentLabel))
                        {
                        addModelComponentLabel(new LanguageSpecificString(objectContent, lang));
                        return true;
                        }

                        else if (predicate.contains(OWLTags.hasModelComponentID))
                        {
                        setModelComponentID(objectContent.split('#')[objectContent.split('#').Length - 1]);
                        return true;
                        }
                        if (!(element == null))
                        {
                        addElementWithUnspecifiedRelation(element);
                        return true;
                        }



                        return false;
                        }





                public int canParse(String className)
                        {
                        if (className.toLowerCase().equals(getClassName().toLowerCase()))
                        {
                        return getClassName().length();
                        }
                        return -1;
                        }

                // Observer/Publisher Methods

                public boolean register(IValueChangedObserver<IPASSProcessModelElement> observer)
                        {
                        if (observer != null && !observerList.contains(observer))
                        {
                        observerList.add(observer);
                        // Might not call this every time
                        informObserverAboutConnectedObjects(observer, ObserverInformType.ADDED);
                        return true;
                        }
                        return false;
                        }

                public boolean unregister(IValueChangedObserver<IPASSProcessModelElement> observer, int removeCascadeDepth)
                        {
                        if (observer != null && observerList.contains(observer))
                        {
                        observerList.remove(observer);
                        // Might not call this every time
                        //if (removeCascadeDepth > 0)
                        informObserverAboutConnectedObjects(observer, ObserverInformType.REMOVED, removeCascadeDepth);
                        return true;
                        }
                        return false;

                        }

                /// <summary>
                /// Publishes that an element has been added to this component
                /// </summary>
                /// <param name="element">the added element</param>
                public void publishElementAdded(IPASSProcessModelElement element)
                        {
                        List<IValueChangedObserver<IPASSProcessModelElement>> localObserver = new List<IValueChangedObserver<IPASSProcessModelElement>>(observerList);
                        for(IValueChangedObserver<IPASSProcessModelElement> observer: localObserver)
                        {
                        observer.updateAdded(element, this);
                        }
                        }

                /// <summary>
                /// Publishes that an element has been removed from this component
                /// </summary>
                /// <param name="element">the removed element</param>
                /// <param name="removeCascadeDepth">An integer that specifies the depth of a cascading delete for connected elements (to the deleted element)
                /// 0 deletes only the given element, 1 the adjacent elements etc.</param>
                public void publishElementRemoved(IPASSProcessModelElement element, int removeCascadeDepth)
                        {
                        for(IValueChangedObserver<IPASSProcessModelElement> observer: new List<IValueChangedObserver<IPASSProcessModelElement>>(observerList))
                        {
                        observer.updateRemoved(element, this, removeCascadeDepth);
                        }
                        }

                protected void publishNewModelComponentID(String oldID)
                        {
                        List<IValueChangedObserver<IPASSProcessModelElement>> localObserver = new List<IValueChangedObserver<IPASSProcessModelElement>>(observerList);
                        for(IValueChangedObserver<IPASSProcessModelElement> observer: localObserver)
                        {
                        observer.notifyModelComponentIDChanged(oldID, this.getModelComponentID());
                        }
                        }

                public void updateAdded(IPASSProcessModelElement update, IPASSProcessModelElement caller)
                        {
                        return;
                        }

                public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth)
                        {
                        return;
                        }

                protected enum ObserverInformType { ADDED, REMOVED }

                /// <summary>
                /// This enum specifies a group of elements that should be returned on function call
                /// </summary>
                public enum ConnectedElementsSetSpecification
                {
                    /// <summary>
                    /// All elements that are somehow connected to the class
                    /// </summary>
                    ALL,
                    /// <summary>
                    /// All elements that should be added to other components via cascading add
                    /// </summary>
                    TO_ADD,
                    /// <summary>
                    /// All elements that should be deleted from other components via cascading delete
                    /// </summary>
                    TO_REMOVE_DIRECTLY_ADJACENT,

                    TO_REMOVE_ADJACENT_AND_MORE,

                    TO_ALWAYS_REMOVE
                }

                    protected void informObserverAboutConnectedObjects(IValueChangedObserver<IPASSProcessModelElement> observer, ObserverInformType informType, int removeCascadeDepth = 0)
                    {
                        if (informType == ObserverInformType.ADDED)
                        {
                            for(IPASSProcessModelElement element: getAllConnectedElements(ConnectedElementsSetSpecification.TO_ADD))
                            observer.updateAdded(element, this);
                        }
                        if (informType == ObserverInformType.REMOVED)
                        {
                            ConnectedElementsSetSpecification connectedSpecification;
                            if (removeCascadeDepth > 1) connectedSpecification = ConnectedElementsSetSpecification.TO_REMOVE_ADJACENT_AND_MORE;
                            else if (removeCascadeDepth == 1) connectedSpecification = ConnectedElementsSetSpecification.TO_REMOVE_DIRECTLY_ADJACENT;
                            else connectedSpecification = ConnectedElementsSetSpecification.TO_ALWAYS_REMOVE;
                            for(IPASSProcessModelElement element: getAllConnectedElements(connectedSpecification))
                            observer.updateRemoved(element, this, (removeCascadeDepth > 0) ? (removeCascadeDepth - 1) : 0);
                        }
                    }


                    public void removeFromEverything(int removeCascadeDepth)
                    {
                        publishElementRemoved(this, removeCascadeDepth);
                    }
                public void removeFromEverything()
                {
                        publishElementRemoved(this, 0);
                }


                    @Override
                    public boolean equals(Object obj)
                {
                    if (obj instanceof IPASSProcessModelElement)
                    {
                            IPASSProcessModelElement element = (IPASSProcessModelElement) obj;
                        return element.getModelComponentID().equals(getModelComponentID());
                    }
                    return false;
                }
                @Override
                public int GetHashCode()
                        {
                        return modelComponentID.getHashCode();
                        }


                public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) // TODO add depth everywhere
                        {
                        return new HashSet<IPASSProcessModelElement>();
                        }

                public void setExportGraph( IPASSGraph graph)
                        {

                        if (this.exportGraph != null)
                        {
                        if (this.exportGraph.equals(graph)) return;
                        this.exportGraph.unregister(this);
                        }
                        this.exportGraph = graph;
                        if (graph == null) return;
                        graph.register(this);
                        String baseURI = getBaseURI();
                        if (baseURI != null && !baseURI.equals(""))
                        invalidateTriplesContainingString("");
                        for(IIncompleteTriple triple: getIncompleteTriples())
                        {
                        completeIncompleteTriple(triple);
                        }
                        for(Triple triple: getTriples())
                        {
                        graph.addTriple(triple);
                        }
                        }

                public void notifyModelComponentIDChanged(String oldID, String newID)
                        {
                        for (IIncompleteTriple t: getIncompleteTriples())
                        {
                        if (t.toString().contains(oldID))
                        {
                        IIncompleteTriple newIncompleteTriple;
                        String predicate = t.getPredicate();
                        if (t.getObjectWithExtra() != null)
                        {
                        IStringWithExtra extra = t.getObjectWithExtra();
                        extra.setContent(extra.getContent().replace(oldID, newID));
                        newIncompleteTriple = new IncompleteTriple(predicate, extra);
                        }
                        else
                        {
                        String objectStr = t.getObject().replace(oldID, newID);
                        newIncompleteTriple = new IncompleteTriple(predicate, objectStr);
                        }
                        replaceTriple(t, newIncompleteTriple);
                        }
                        }
                        for(Triple t: getTriples())
                        {
                        if (t.toString().contains(oldID))
                        {
                        IIncompleteTriple oldIncompleteTriple = new IncompleteTriple(t);
                        IIncompleteTriple newIncompleteTriple;
                        String predicate = oldIncompleteTriple.getPredicate();
                        if (oldIncompleteTriple.getObjectWithExtra() != null)
                        {
                        IStringWithExtra extra = oldIncompleteTriple.getObjectWithExtra();
                        extra.setContent(extra.getContent().replace(oldID, newID));
                        newIncompleteTriple = new IncompleteTriple(predicate, extra);
                        }
                        else
                        {
                        String objectStr = oldIncompleteTriple.getObject().replace(oldID, newID);
                        newIncompleteTriple = new IncompleteTriple(predicate, objectStr);
                        }
                        replaceTriple(oldIncompleteTriple, newIncompleteTriple);
                        }
                        }
                        }

                public IParseablePASSProcessModelElement getParsedInstance()
                        {
                        return new PASSProcessModelElement();
                        }

                public void addElementWithUnspecifiedRelation(IPASSProcessModelElement element)
                        {
                        if (element == null) { return; }
                        if (additionalElements.tryAdd(element.getModelComponentID(), element))
                        {
                        addTriple(new IncompleteTriple(OWLTags.stdContains, element.getUriModelComponentID()));
                        }
                        }

                public Map<String, IPASSProcessModelElement> getElementsWithUnspecifiedRelation()
                        {
                        return new HashMap<String, IPASSProcessModelElement>(additionalElements);
                        }

                public void setElementsWithUnspecifiedRelation(Set<IPASSProcessModelElement> elements)
                        {
                        for (IPASSProcessModelElement element: getElementsWithUnspecifiedRelation().values())
                        {
                        removeElementWithUnspecifiedRelation(element.getModelComponentID());
                        }
                        if (elements == null) return;
                        for(IPASSProcessModelElement element: elements)
                        {
                        addElementWithUnspecifiedRelation(element);
                        }
                        }

                public void removeElementWithUnspecifiedRelation(String id)
                        {
                        if (id == null) return;
                        IPASSProcessModelElement element = additionalElements.get(id);
                        if (element != null)
                        {
                        additionalElements.remove(id);
                        removeTriple(new IncompleteTriple(OWLTags.stdContains, element.getUriModelComponentID()));
                        }
                        }

                public String getSubjectName()
                        {
                        return getModelComponentID();
                        }

                public void notifyTriple(Triple triple)
                        {
                        addTriple(triple);
                        }

                protected void setExportXMLName(String xmlTag)
                        {
                        if (exportSubjectNodeName == null || exportSubjectNodeName.equals(""))
                        exportSubjectNodeName = xmlTag;
                        }

                public PASSProcessModelElement() { }
                        }

