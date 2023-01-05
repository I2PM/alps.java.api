package alps.net.api.parsing;

import alps.net.api.util.*;
import org.apache.jena.graph.Triple;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XML;
import org.apache.jena.vocabulary.XSD;
import org.apache.jena.vocabulary.SWRLA;
import org.apache.jena.vocabulary.PassOnt;
import org.apache.jena.vocabulary.OWL;

/// <summary>
/// This class is an adapter class for the <see cref="IGraph"/> interface.
/// It uses an <see cref="OntologyGraph"/> as internal graph.
/// </summary>
public class PASSGraph implements IPASSGraph{
    public interface IGraphCallback{
        void notify(Triple triple);
        String getSubjectName();
        void notifyModelComponentIDChanged(String oldID, String newID);
    }

    public static final String EXAMPLE_BASE_URI_PLACEHOLDER = "baseuri:";
    public static final String EXAMPLE_BASE_URI_PLACEHOLDER_MAPPING_KEY = "baseuri";

    private ICompatibilityDictionary<String, IGraphCallback> elements = new CompatibilityDictionary<String, IGraphCallback>();

    //TODO:neu implementieren
    private ICompatibilityDictionary<String, String> namespaceMappings = new CompatibilityDictionary<String, String>{
        { "rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#"};
        { "rdfs", "http://www.w3.org/2000/01/rdf-schema#"};
        { "xml", "http://www.w3.org/XML/1998/namespace"};
        { "xsd", "http://www.w3.org/2001/XMLSchema#"};
        { "swrla", "http://swrl.stanford.edu/ontologies/3.3/swrla.owl#"};
        { "abstract-pass-ont", "http://www.imi.kit.edu/abstract-pass-ont#"};
        { "standard-pass-ont", "http://www.i2pm.net/standard-pass-ont#"};
        { "owl", "http://www.w3.org/2002/07/owl#"};
        { "", "http://www.w3.org/1999/02/22-rdf-syntax-ns#"}
        }
    private String baseURI;

    //TODO:neu implementieren
    public boolean containsNonBaseURI(String input) {
        for(KeyValuePair<String, String> nameMapping in namespaceMappings)
        {
            if (input.contains(nameMapping.Value) && !nameMapping.Key.Equals(EXAMPLE_BASE_URI_PLACEHOLDER.replace(":", "")))
                return true;
        }
        return false;
    }
    protected final String EXAMPLE_BASE_URI = "http://www.imi.kit.edu/exampleBaseURI";
    protected Model baseGraph;

    public PASSGraph(String baseURI)
    {
        if (baseURI == null)
        this.baseURI = EXAMPLE_BASE_URI;
            else
        this.baseURI = baseURI;
        namespaceMappings.add(EXAMPLE_BASE_URI_PLACEHOLDER_MAPPING_KEY, baseURI + "#");

        Model exportGraph = new Model() {
        };

        // Adding all namespaceMappings (exchange short acronyms like owl: with the complete uri)
        for(KeyValuePair<String, String> nameMapping in namespaceMappings)
        {
            exportGraph.NamespaceMap.AddNamespace(nameMapping.Key, new URI(nameMapping.Value));
        }
        exportGraph.NamespaceMap.AddNamespace("", new URI(baseURI + "#"));
        exportGraph.BaseUri = new URI(baseURI);

        Resource subjectNode;
        Resource predicateNode;
        Resource objectNode;
        Triple triple;


        subjectNode = exportGraph.createUriNode(exportGraph.BaseUri);
        predicateNode = exportGraph.CreateUriNode("rdf:type");
        objectNode = exportGraph.CreateUriNode("owl:Ontology");
        triple = new Triple(subjectNode, predicateNode, objectNode);
        exportGraph.Assert(triple);


        // Adding import triples for standard pass and abstract pass
        subjectNode = exportGraph.CreateUriNode(exportGraph.BaseUri);
        predicateNode = exportGraph.CreateUriNode("owl:imports");

        objectNode = exportGraph.CreateUriNode(new Uri("http://www.i2pm.net/standard-pass-ont"));
        triple = new Triple(subjectNode, predicateNode, objectNode);
        exportGraph.Assert(triple);
        objectNode = exportGraph.CreateUriNode(new Uri("http://www.imi.kit.edu/abstract-pass-ont"));
        triple = new Triple(subjectNode, predicateNode, objectNode);
        exportGraph.Assert(triple);

        baseGraph = exportGraph;
    }

    public void changeBaseURI(String newUri)
    {
        if (newUri == null)
        this.baseURI = EXAMPLE_BASE_URI;
            else
        this.baseURI = newUri;

        namespaceMappings[EXAMPLE_BASE_URI_PLACEHOLDER_MAPPING_KEY] = baseURI + "#";
        // baseGraph.NamespaceMap.RemoveNamespace("");
        // baseGraph.NamespaceMap.RemoveNamespace(EXAMPLE_BASE_URI_PLACEHOLDER_MAPPING_KEY);
        baseGraph.NamespaceMap.AddNamespace("", new URI(baseURI + "#"));
        baseGraph.NamespaceMap.AddNamespace(EXAMPLE_BASE_URI_PLACEHOLDER_MAPPING_KEY, new URI(baseURI + "#"));
        //exportGraph.NamespaceMap.AddNamespace("", new Uri(baseURI + "#"));
    }

    public Model getGraph()
    {
        return baseGraph;
    }

    public void addTriple(Triple t)
    {
        if (baseGraph.Triples.Contains(t)) return;
        baseGraph.Assert(t);
        string subjWithoutUri = t.Subject.ToString().Replace(baseURI + "#", "");
        if (elements.ContainsKey(subjWithoutUri))
        {
            elements[subjWithoutUri].notifyTriple(t);
        }
    }

    public Resource createUriNode()
    {
        return baseGraph.createUriNode();
    }
    public Resource createUriNode(URI uri)
    {
        return baseGraph.CreateUriNode(uri);
    }
    public Resource createUriNode(String qname)
    {
        return baseGraph.CreateUriNode(qname);
    }

    public Literal createLiteralNode(String literal)
    {
        return baseGraph.CreateLiteralNode(literal);
    }
    public Literal createLiteralNode(String literal, URI datadef)
    {
        return baseGraph.CreateLiteralNode(literal, datadef);
    }
    public Literal createLiteralNode(String literal, String langspec)
    {
        return baseGraph.CreateLiteralNode(literal, langspec);
    }

    public void removeTriple(Triple t) { baseGraph.Retract(t); }


    public void register(IGraphCallback element)
    {
        elements.tryAdd(element.getSubjectName(), element);
    }

    public void unregister(IGraphCallback element)
    {
        elements.remove(element.getSubjectName());
    }

    public void modelComponentIDChanged(String oldID, String newID)
    {
        List<IGraphCallback> elementsToNotify = new List<IGraphCallback>();
        for (Triple t: baseGraph.Triples)
        {
            if (t.toString().contains(oldID))
            {
                String subjWithoutUri = t.Subject.ToString().Replace(baseURI + "#", "");
                if (elements.containsKey(subjWithoutUri))
                {
                    elementsToNotify.Add(elements[subjWithoutUri]);
                }
            }
        }
        for(IGraphCallback parseable: elementsToNotify)
        {
            parseable.notifyModelComponentIDChanged(oldID, newID);
        }
    }

    public void exportTo(String filepath)
    {
        IRdfWriter writer = new RdfXmlWriter();
        String fullPath = (filepath.endsWith(".owl")) ? filepath : filepath + ".owl";
        writer.Save(baseGraph, fullPath);
    }

    public String getBaseURI()
    {
        return baseURI;
    }

}

