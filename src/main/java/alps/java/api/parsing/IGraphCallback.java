package alps.java.api.parsing;

import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Statement;

public interface IGraphCallback{
    void notifyTriple(Triple triple);
    String getSubjectName();
    void notifyModelComponentIDChanged(String oldID, String newID);
}