package alps.java.api.util;

import alps.java.api.parsing.*;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            Pattern pattern = Pattern.compile("\\[(.*?)\\]");
            Matcher matcher = pattern.matcher(extra);

            if (matcher.find()) {
                String stringwithArrow = matcher.group(1);
                int arrowIndex = stringwithArrow.indexOf("->");
                if (arrowIndex != -1) {
                    this.extra= stringwithArrow.substring(0, arrowIndex).trim();
                } else {
                    this.extra= stringwithArrow;
            }} else {
                this.extra = extra;
            }
        }
    }

    @Override
    public Node getNodeFromString(IPASSGraph graph) {
        if (getExtra().contains("www")) {
            try {
                Literal literal = graph.createLiteralNode(getContent(), new URI(getExtra()));
                return literal.asNode();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        Literal literal = graph.createLiteralNode(getContent(), graph.createUriNode(getExtra()).getURI());
        return literal.asNode();
    }

    @Override
    public IStringWithExtra clone() {
        return new DataTypeString(getContent(), getExtra());
    }
}
