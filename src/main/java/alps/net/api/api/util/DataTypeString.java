package alps.net.api.api.util;

import alps.net.api.parsing;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
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
    public RDFNode getNodeFromString(IPASSGraph graph) {
        //neu implementieren Methodendekklaration passt
        Resource datatype = model.createResource(getExtra());
        Literal literal = model.createTypedLiteral(getContent(), datatype);
        model.add(literal, RDF.type, datatype);
        return literal;
    }

    @Override
    public IStringWithExtra clone() {
        return new DataTypeString(getContent(), getExtra());
    }
}
