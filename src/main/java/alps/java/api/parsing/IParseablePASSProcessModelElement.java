package alps.java.api.parsing;

import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.util.*;

import java.util.List;
import java.util.Map;

/**
 * Interface that extends the standard PASSProcessModelElement interface for parsing methods
 */
public interface IParseablePASSProcessModelElement extends IPASSProcessModelElement, IGraphCallback {

    /**
     * This method verifies whether a class can parse an ontology class, given by the string name of the ontology class.
     * Example: The class PASSProcessModel should not return {@link "CANNOT_PARSE"} for the string "PASSProcessModel".
     * A high return value indicates that this class can parse the ontology class better than another class with a lower return value.
     * Every value !={@link "CANNOT PARSE"} indicates that the class can parse the ontology class.
     *
     * @param className
     * @return
     */
    int canParse(String className);

    /**
     * Completes the element by replacing temporary string ids with real objects
     *
     * @param allElements a dict of all pass elements
     */
    void completeObject(Map<String, IParseablePASSProcessModelElement> allElements);

    void setExportGraph(IPASSGraph graph);


    /**
     * Returns the name of the current class.
     * Used for parsing, must match the name of the class in the ontology
     *
     * @return The name of the current class
     */
    String getClassName();

    String getBaseURI();

    void addTriple(IIncompleteTriple triple);

    /**
     * Adds a  list ofIncomplete Triple to the element that will either be parsed right away, or delayed
     * (depending on whether there is a graph available or not)
     * @param triples the triple that is being saved
     */

    void addIncompleteTriples(List<IIncompleteTriple> triples);

    /**
     * Adds a list of complete triples to the element.
     * If the element contains an Incomplete Triple containing the same information as a complete triple, the incomplete will be deleted.
     * The content of the triple will be parsed if possible.
     *
     * @param triples the triple that is being saved
     */
    void addTriples(List<Triple> triples);

    /**
     * Adds a complete triple to the element.
     * If the element contains an Incomplete Triple containing the same information, it will be deleted.
     * The content of the triple will be parsed if possible.
     *
     * @param triple
     */
    void addTriple(Triple triple);

    /**
     * Returns all the triples currently contained by the class.
     * They describe connection to other elements etc
     *
     * @return
     */
    List<Triple> getTriples();

    /**
     * Returns all the triples currently contained by the class as incomplete triples (not containing the subject).
     * They describe connection to other elements etc.
     *
     * @return
     */
    List<IIncompleteTriple> getIncompleteTriples();

    /**
     * Removes a triple from either the complete or incomplete triples.
     *
     * @param incTriple An incomplete triple coding the value that should be deleted.
     *                  The deleted object must not be incomplete, but can as well be a complete triple
     * @return
     */
    boolean removeTriple(IIncompleteTriple incTriple);

    /**
     * Determines wheter there is a triple (complete or incomplete), holding the same data as the given incomplete triple
     *
     * @param incTriple An incomplete triple coding the value that should be found.
     *                  The found object must not be incomplete, but can as well be a complete triple
     * @return
     */
    boolean containsTriple(IIncompleteTriple incTriple);


    /**
     * Replaces an old triple with a new one.
     *
     * @param oldTriple An incomplete triple holding the data to find the triple to be replaced
     * @param newTriple An incomplete triple holding the data to replace the old triple
     */
    void replaceTriple(IIncompleteTriple oldTriple, IIncompleteTriple newTriple);

    IParseablePASSProcessModelElement getParsedInstance();


}
