package alps.java.api.parsing;

import alps.java.api.util.ITreeNode;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IParsingTreeMatcher {
    /**
     * Creates a dictionary that maps owl classes with c# classes.
     * The owl classes are extracted from given class-defining owl files (i.e. the standard-pass-ont, abstract-pass-ont).
     * The c# classes are evaluated dynamically at runtime from all classes known to this assembly.
     *
     * @param owlStructureGraphs
     */
    public Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> loadOWLParsingStructure(List<OntModel> owlStructureGraphs);


}