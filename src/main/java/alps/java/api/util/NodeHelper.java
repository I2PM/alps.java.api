package alps.java.api.util;

import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;

public class NodeHelper {
    public static String getNodeContent(RDFNode node) {
        if (node instanceof Literal literal) {
            return literal.getValue().toString();
        }
        return node.toString();
    }

    public static String getLangIfContained(RDFNode node) {
        if (node instanceof Literal literal) {
            return literal.getLanguage();
        }
        return "";
    }

    public static String getDataTypeIfContained(RDFNode node) {
        if (node instanceof Literal literal) {
            if (literal.getDatatype() != null) {
                return literal.getDatatype().toString();
            }
        }
        return "";
    }

    public static String cutURI(String uri) {
        String firstCut = uri.split("#")[uri.split("#").length - 1];
        return firstCut.split(":")[firstCut.split(":").length - 1];
    }
}
