package alps.java.api.util;

import alps.java.api.parsing.*;
import org.apache.jena.rdf.model.RDFNode;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * A class to represent a string along with a specified dataType
 * This class is useful to represent an object (in a triple store context),
 * where the literal node might contain an additional data type information.
 * The datatype is stored as extra.
 */
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
    public RDFNode getNodeFromString(IPASSGraph graph) {
        if (getExtra().contains("www")) {
            try {
                return graph.createLiteralNode(getContent(), new URI(getExtra()));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return graph.createLiteralNode(getContent(), graph.createUriNode(getExtra()).getURI());
    }

    @Override
    public IStringWithExtra clone() {
        return new DataTypeString(getContent(), getExtra());
    }
}
