package alps.java.api.util;

import alps.java.api.parsing.IPASSGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.RDFNode;


/**
 * A language specific string is a string combined with a language specifier.
 */
public interface IStringWithExtra {

    /**
     * Returns the content, the actual string
     *
     * @return the actual String
     */
    String getContent();

    /**
     * Sets the content, the actual string
     *
     * @param content
     */
    void setContent(String content);

    /**
     * Returns the extra that is specified in addition to the string.
     * What this is depends on the concrete implementation
     *
     * @return the extra information
     */
    String getExtra();

    /**
     * Sets the extra that is specified in addition to the string.
     * What this is depends on the concrete implementation.
     *
     * @param extra
     */
    void setExtra(String extra);

    /**
     * Creates a {@link RDFNode} from the current string (a literal node)
     * therefor it needs a graph as context
     *
     * @param graph the context in which the node is created
     * @return the created Node
     */
    Node getNodeFromString(IPASSGraph graph);

    /**
     * Clones the current string with extra to get a new instance
     *
     * @return clone of the current String
     */
    IStringWithExtra clone();

}
