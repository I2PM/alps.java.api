package alps.net.api.parsing;

import org.apache.jena.graph.Triple;

public interface IGraphCallback{
    void notify(Triple triple);
    String getSubjectName();
    void notifyModelComponentIDChanged(String oldID, String newID);
}