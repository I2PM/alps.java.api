package alps.net.api.api.util;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;

public class NodeHelper {
    public static String getNodeContent(RDFNode node) {
        if (node.isLiteral()) {
            Literal literal = node.asLiteral();
            return literal.getString();
        }
        return node.toString();
    }

    public static String getLangIfContained(RDFNode node) {
        if (node.isLiteral()) {
            Literal literal = node.asLiteral();
            return literal.getLanguage();
        }
        return "";
    }

    public static String getDataTypeIfContained(RDFNode node) {
        if (node.isLiteral()) {
            Literal literal = node.asLiteral();
            if (literal.getDatatype() != null) {
                return literal.getDatatype().getURI();
            }
        }
        return "";
    }

    public static String cutURI(String uri) {
        String[] firstCut = uri.split("#");
        String firstCutResult = firstCut[firstCut.length - 1];
        String[] secondCut = firstCutResult.split(":");
        return secondCut[secondCut.length - 1];
    }
}
