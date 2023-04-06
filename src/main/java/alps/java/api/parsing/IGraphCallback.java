package alps.java.api.parsing;

import org.apache.jena.rdf.model.Statement;

public interface IGraphCallback{
    void notifyTriple(Statement triple);
    String getSubjectName();
    void notifyModelComponentIDChanged(String oldID, String newID);
}