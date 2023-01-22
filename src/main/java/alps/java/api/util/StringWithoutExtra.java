package alps.java.api.util;

import alps.java.api.parsing.IPASSGraph;
import org.apache.jena.rdf.model.RDFNode;
import java.net.URI;

public class StringWithoutExtra extends StringWithExtra {

    public StringWithoutExtra(String content) {
        super(content);
    }

    public StringWithoutExtra(String content, String lang) {
        super(content);
    }

    @Override
    public IStringWithExtra clone() {
        return new StringWithoutExtra(getContent());
    }

    @Override
    public RDFNode getNodeFromString(IPASSGraph graph) {
        RDFNode objectNode;
        if (!content.contains("http://") && !content.contains("https://")) {
            try {
                objectNode = graph.createUriNode(content);
            } catch (Exception e) {
                objectNode = graph.createUriNode(new URI(content));
            }
        } else {
            objectNode = graph.createUriNode(new URI(content));
        }
        return objectNode;
    }

    @Override
    public void setExtra(String lang) {
        return;
    }

    @Override
    public String toString() {
        return content;
    }
}
