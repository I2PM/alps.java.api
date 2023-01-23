package alps.java.api.parsing;

import alps.java.api.util.ITreeNode;
import org.apache.jena.atlas.lib.Pair;

import java.util.List;
import java.util.Map;

public interface IParsingTreeMatcher
{
    /**
     * Creates a dictionary that maps owl classes with c# classes.
     * The owl classes are extracted from given class-defining owl files (i.e. the standard-pass-ont, abstract-pass-ont).
     * The c# classes are evaluated dynamically at runtime from all classes known to this assembly.
     * @param
     */
        public Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> loadOWLParsingStructure(List<OntologyGraph> owlStructureGraphs);


}