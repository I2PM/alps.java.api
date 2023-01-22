package alps.java.api.util;

import alps.java.api.parsing.IPASSGraph;
import org.apache.jena.rdf.model.RDFNode;
import java.net.URI;
import java.net.URISyntaxException;

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
                try {
                    objectNode = graph.createUriNode(new URI(content));
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else {
            try {
                objectNode = graph.createUriNode(new URI(content));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return objectNode;
    }

    @Override
    public void setExtra(String lang) {

    }

    @Override
    public String toString() {
        return content;
    }
}
