package alps.java.api.parsing;

import alps.java.api.util.*;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

/**
 * This class is an adapter class for the {@link Model} interface.
 * It uses an {@link org.apache.jena.ontology.OntModel}as internal graph
 */
public class PASSGraph implements IPASSGraph {


    public static final String EXAMPLE_BASE_URI_PLACEHOLDER = "baseuri:";
    public static final String EXAMPLE_BASE_URI_PLACEHOLDER_MAPPING_KEY = "baseuri";

    private ICompatibilityDictionary<String, IGraphCallback> elements = new CompatibilityDictionary<String, IGraphCallback>();

    private final ICompatibilityDictionary<String, String> namespaceMappings = new CompatibilityDictionary<String, String>() {{
        put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        put("xml", "http://www.w3.org/XML/1998/namespace");
        put("xsd", "http://www.w3.org/2001/XMLSchema#");
        put("swrla", "http://swrl.stanford.edu/ontologies/3.3/swrla.owl#");
        put("abstract-pass-ont", "http://www.imi.kit.edu/abstract-pass-ont#");
        put("standard-pass-ont", "http://www.i2pm.net/standard-pass-ont#");
        put("owl", "http://www.w3.org/2002/07/owl#");
        put("", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
    }};
    private String baseURI;

    public boolean containsNonBaseUri(String input) {
        for (Map.Entry<String, String> nameMapping : namespaceMappings.entrySet()) {
            if (input.contains(nameMapping.getValue()) && !nameMapping.getKey().equals(EXAMPLE_BASE_URI_PLACEHOLDER.replace(":", "")))
                return true;
        }
        return false;
    }

    protected static final String EXAMPLE_BASE_URI = "http://www.imi.kit.edu/exampleBaseURI";
    protected Model baseGraph;

    public PASSGraph(String baseURI) {
        if (baseURI == null)
            this.baseURI = EXAMPLE_BASE_URI;
        else
            this.baseURI = baseURI;
        namespaceMappings.put(EXAMPLE_BASE_URI_PLACEHOLDER_MAPPING_KEY, baseURI + "#");

        Model exportGraph = ModelFactory.createDefaultModel();

        // Adding all namespaceMappings (exchange short acronyms like owl: with the complete uri)
        for (Map.Entry<String, String> nameMapping : namespaceMappings.entrySet()) {
            exportGraph.setNsPrefix(nameMapping.getKey(), nameMapping.getValue());
        }
        exportGraph.setNsPrefix("", baseURI + "#");
        exportGraph.setNsPrefix("base", baseURI);

        Resource subjectNode;
        Property predicateNode;
        Resource objectNode;


        subjectNode = exportGraph.createResource(baseURI);
        predicateNode = exportGraph.createProperty("rdf:type");
        objectNode = exportGraph.createResource("owl:Ontology");
        exportGraph.add(subjectNode, predicateNode, objectNode);


        // Adding import triples for standard pass and abstract pass
        subjectNode = exportGraph.createResource(baseURI);
        predicateNode = exportGraph.createProperty("owl:imports");

        objectNode = exportGraph.createResource("http://www.i2pm.net/standard-pass-ont");
        exportGraph.add(subjectNode, predicateNode, objectNode);
        objectNode = exportGraph.createResource("http://www.imi.kit.edu/abstract-pass-ont");
        exportGraph.add(subjectNode, predicateNode, objectNode);

        baseGraph = exportGraph;
    }

    public void changeBaseURI(String newUri) {
        if (newUri == null)
            this.baseURI = EXAMPLE_BASE_URI;
        else
            this.baseURI = newUri;

        namespaceMappings.put(EXAMPLE_BASE_URI_PLACEHOLDER_MAPPING_KEY, baseURI + "#");
        // baseGraph.NamespaceMap.RemoveNamespace("");
        // baseGraph.NamespaceMap.RemoveNamespace(EXAMPLE_BASE_URI_PLACEHOLDER_MAPPING_KEY);
        baseGraph.setNsPrefix("", baseURI + "#");
        baseGraph.setNsPrefix(EXAMPLE_BASE_URI_PLACEHOLDER_MAPPING_KEY, baseURI + "#");
        //exportGraph.NamespaceMap.AddNamespace("", new Uri(baseURI + "#"));
    }

    public Model getGraph() {
        return baseGraph;
    }

    public void addTriple(Triple t) {
        if (baseGraph.getGraph().contains(t)) return;
        baseGraph.getGraph().add(t);
        String subjWithoutUri = t.getSubject().toString().replace(baseURI + "#", "");
        if (elements.containsKey(subjWithoutUri)) {
            elements.get(subjWithoutUri).notifyTriple(t);
        }
    }

    public Node createUriNode() {

        Resource resource = baseGraph.createResource();
        return resource.asNode();
    }

    public Node createUriNode(URI uri) {
        Resource resource =  baseGraph.createResource(uri.toString());
        return resource.asNode();
    }

    @Override
    public Property createUriNodeProp(URI uri) {
        return baseGraph.createProperty(uri.toString());
    }

    public Node createUriNode(String qname) {
        Resource resource = baseGraph.createResource(qname);
        return resource.asNode();
    }

    public Literal createLiteralNode(String literal) {
        return baseGraph.createLiteral(literal);
    }

    public Literal createLiteralNode(String literal, URI datadef) {
        return baseGraph.createLiteral(literal, datadef.toString());
    }

    public Literal createLiteralNode(String literal, String langspec) {
        return baseGraph.createLiteral(literal, langspec);
    }

    public void removeTriple(Triple t) {
        baseGraph.remove(t);
    }


    public void register(IGraphCallback element) {
        elements.tryAdd(element.getSubjectName(), element);
    }

    public void unregister(IGraphCallback element) {
        elements.remove(element.getSubjectName());
    }

    public void modelComponentIDChanged(String oldID, String newID) {
        List<IGraphCallback> elementsToNotify = new ArrayList<IGraphCallback>();
        StmtIterator stmtIter = baseGraph.listStatements();
        while (stmtIter.hasNext()) {
            Statement t = stmtIter.next();
            if (t.toString().contains(oldID)) {
                String subjWithoutUri = t.getSubject().toString().replace(baseURI + "#", "");
                if (elements.containsKey(subjWithoutUri)) {
                    elementsToNotify.add(elements.get(subjWithoutUri));
                }
            }
        }
        for (IGraphCallback parseable : elementsToNotify) {
            parseable.notifyModelComponentIDChanged(oldID, newID);
        }
    }

    public void exportTo(String filepath) {
        String fullPath = filepath.endsWith(".owl") ? filepath : filepath + ".owl";
        try (FileOutputStream fos = new FileOutputStream(fullPath)) {
            RDFDataMgr.write(fos, baseGraph, RDFFormat.RDFXML);
        } catch (IOException e) {
            // Handle the exception (e.g., print an error message or rethrow the exception)
            e.printStackTrace();
        }
    }

    public String getBaseURI() {
        return baseURI;
    }

}

