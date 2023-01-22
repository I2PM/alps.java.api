package alps.java.api.util;

import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;

public class NodeHelper {
    public static String getNodeContent(RDFNode node) {
        if (node instanceof LiteralLabel) {
            LiteralLabel literal = (LiteralLabel) node;
            return literal.toString();
        }
        return node.toString();
    }

    public static String getLangIfContained(RDFNode node) {
        if (node instanceof LiteralLabel) {
            LiteralLabel literal = (LiteralLabel) node;
            return literal.language();
        }
        return "";
    }

    public static String getDataTypeIfContained(RDFNode node) {
        if (node instanceof LiteralLabel) {
            LiteralLabel literal = (LiteralLabel) node;
            if (literal.getDatatype() != null) {
                return literal.getDatatype().toString();
            }
        }
        return "";
    }

    public static String cutURI(String uri) {
        String[] firstCut = uri.split("#");
        String[] secondCut = firstCut[firstCut.length - 1].split(":");
        return secondCut[secondCut.length - 1];
    }
}
