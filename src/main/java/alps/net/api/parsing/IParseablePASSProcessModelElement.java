package alps.net.api.parsing;

import alps.net.api.StandardPASS.*;
import alps.net.api.util.*;
import alps.net.api.PASSGraph;
import org.apache.jena.graph.Triple;

import java.util.Dictionary;
import java.util.List;

/// <summary>
/// Interface that extends the standard PASSProcessModelElement interface for parsing methods
/// </summary>
public interface IParseablePASSProcessModelElement {

    /// <summary>
    /// This method verifies whether a class can parse an ontology class, given by the string name of the ontology class.
    /// Example: The class PASSProcessModel should not return <see cref="CANNOT_PARSE"/> for the string "PASSProcessModel".
    /// A high return value indicates that this class can parse the ontology class better than another class with a lower return value.
    /// Every value != <see cref="CANNOT_PARSE"/> indicates that the class can parse the ontology class.
    /// </summary>
    /// <param name="className"></param>
    /// <returns></returns>
    int canParse(String className);

    /// <summary>
    /// Completes the element by replacing temporary string ids with real objects
    /// </summary>
    /// <param name="allElements">a dict of all pass elements</param>
    void completeObject(Dictionary<String, IParseablePASSProcessModelElement> allElements);

    void setExportGraph(IPASSGraph graph);


    /// <summary>
    /// Returns the name of the current class.
    /// Used for parsing, must match the name of the class in the ontology
    /// </summary>
    /// <returns>The name of the current class</returns>
    String getClassName();

    String getBaseURI();

    void addTriple(IIncompleteTriple triple);

    /// <summary>
    /// Adds a  list ofIncomplete Triple to the element that will either be parsed right away, or delayed
    /// (depending on whether there is a graph available or not)
    /// </summary>
    /// <param name="triple">the triple that is being saved</param>
    void addTriples(List<IIncompleteTriple> triples);

    /// <summary>
    /// Adds a list of complete triples to the element.
    /// If the element contains an Incomplete Triple containing the same information as a complete triple, the incomplete will be deleted.
    /// The content of the triple will be parsed if possible.
    /// </summary>
    /// <param name="triples"></param>
    void addTriples(List<Triple> triples);

    /// <summary>
    /// Adds a complete triple to the element.
    /// If the element contains an Incomplete Triple containing the same information, it will be deleted.
    /// The content of the triple will be parsed if possible.
    /// </summary>
    /// <param name="triples"></param>
    void addTriple(Triple triple);

    /// <summary>
    /// Returns all the triples currently contained by the class.
    /// They describe connection to other elements etc.
    /// </summary>
    List<Triple> getTriples();

    /// <summary>
    /// Returns all the triples currently contained by the class as incomplete triples (not containing the subject).
    /// They describe connection to other elements etc.
    /// </summary>
    List<IIncompleteTriple> getIncompleteTriples();

    /// <summary>
    /// Removes a triple from either the complete or incomplete triples.
    /// </summary>
    /// <param name="incTriple">An incomplete triple coding the value that should be deleted.
    /// The deleted object must not be incomplete, but can as well be a complete triple</param>
    /// <returns></returns>
    boolean removeTriple(IIncompleteTriple incTriple);

    /// <summary>
    /// Determines wheter there is a triple (complete or incomplete), holding the same data as the given incomplete triple
    /// </summary>
    /// <param name="incTriple">An incomplete triple coding the value that should be found.
    /// The found object must not be incomplete, but can as well be a complete triple</param>
    /// <returns></returns>
    boolean containsTriple(IIncompleteTriple incTriple);


    /// <summary>
    /// Replaces an old triple with a new one.
    /// </summary>
    /// <param name="oldTriple">An incomplete triple holding the data to find the triple to be replaced</param>
    /// <param name="newTriple">An incomplete triple holding the data to replace the old triple</param>
    void replaceTriple(IIncompleteTriple oldTriple, IIncompleteTriple newTriple);

    IParseablePASSProcessModelElement getParsedInstance();


}