package alps.net.api.util;

import alps.net.api.parsing;
import org.apache.jena.rdf.model.Resource;

import java.net.URI;

/// <summary>
/// A class to represent a string along with a specified dataType
/// This class is useful to represent an object (in a triple store context),
/// where the literal node might contain an additional data type information.
/// The datatype is stored as extra.
/// </summary>
public class DataTypeString extends StringWithExtra {
    public DataTypeString(String input) {
        super(input);
    }

    public DataTypeString(String content, String extra) {
        super(content, extra);
    }

    @Override
    public void setContent(String content) {
        if (content == null) {
            this.content = "";
            return;
        }
        if (content.contains("@")) {
            this.content = content.split("@")[0];
            setExtra(content.split("@")[1]);
        } else {
            this.content = content;
        }
    }

    @Override
    public void setExtra(String extra) {
        if (extra == null) {
            this.extra = "";
            return;
        }
        if (extra.contains("@")) {
            setContent(extra);
        } else {
            this.extra = extra;
        }
    }

    @Override
    public Resource getNodeFromString(IPASSGraph graph) {
        if (getExtra().contains("www"))
            return graph.createLiteralNode(getContent(), new URI(getExtra()));
        return graph.createLiteralNode(getContent(), graph.createUriNode(getExtra()).Uri);
    }

    @Override
    public IStringWithExtra clone() {
        return new DataTypeString(getContent(), getExtra());
    }
}