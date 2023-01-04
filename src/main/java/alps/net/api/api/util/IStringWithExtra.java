package alps.net.api.api.util;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

/**
 * A language specific string is a string combined with a language specifier.
 */
public interface IStringWithExtra {

    /**
     * Returns the content, the actual string
     * @return the actual string
     */
    String getContent();

    /**
     * Sets the content, the actual string
     * @param content the content to set
     */
    void setContent(String content);

    /**
     * Returns the extra that is specified in addition to the string.
     * What this is depends on the concrete implementation.
     * @return the extra information
     */
    String getExtra();

    /**
     * Sets the extra that is specified in addition to the string.
     * What this is depends on the concrete implementation.
     * @param extra the extra information to set
     */
    void setExtra(String extra);


    RDFNode getNodeFromString(IPASSGraph graph);

    /**
     * Clones the current string with extra to get a new instance
     * @return a clone of the current string
     */
    IStringWithExtra clone();
}
