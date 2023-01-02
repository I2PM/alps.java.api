package alps.net.api.api.util;

//import alps.net.api.api.parsing.StringWithExtra;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

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
        Model model = ModelFactory.createDefaultModel();
        Resource subject = model.createResource(graph.createUriNode(getExtra()).getURI());
        Literal object = model.createLiteral(getContent());
        model.add(subject, null, object);
        return model.listObjects().next();
    }

    @Override
    public DataTypeString clone() {
        return new DataTypeString(getContent(), getExtra());
    }
}