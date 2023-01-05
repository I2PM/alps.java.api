package alps.net.api.api.util;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import alps.net.api.parsing;

/// <summary>
/// A language specific string is a string combined with a language specifier.
/// </summary>
public interface IStringWithExtra {

    /// <summary>
    /// Returns the content, the actual string
    /// </summary>
    /// <returns>the actual string</returns>
    String getContent();

    /// <summary>
    /// Sets the content, the actual string
    /// </summary>
    /// <param name="content"></param>
    void setContent(String content);

    /// <summary>
    /// Returns the extra that is specified in addition to the string.
    /// What this is depends on the concrete implementation.
    /// </summary>
    /// <returns>the extra information</returns>
    String getExtra();

    /// <summary>
    /// Sets the extra that is specified in addition to the string.
    /// What this is depends on the concrete implementation.
    /// </summary>
    /// <param name="extra">the extra information</param>
    void setExtra(String extra);

    /// <summary>
    /// Creates a <see cref="INode"/> from the current string (a literal node)
    /// therefor it needs a graph as context
    /// </summary>
    /// <param name="graph">the context in which the node is created</param>
    /// <returns>the created node</returns>
    RDFNode getNodeFromString(IPASSGraph graph);

    /// <summary>
    /// Clones the current string with extra to get a new instance
    /// </summary>
    /// <returns>a clone of the current string</returns>
    IStringWithExtra clone();
}
