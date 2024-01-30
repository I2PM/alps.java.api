package alps.java.api.parsing;


import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.StandardPASS.PassProcessModelElements.PASSProcessModel;
import alps.java.api.util.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RiotException;
import org.apache.jena.sparql.function.library.leviathan.log;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

/**
 * The main parser class (Singleton).
 *
 * <p>To load a model contained in an owl/rdf formatted file, either use:</p>
 * <ul>
 *   <li>loadOWLParsingStructure() and pass path references to used ontology classes (i.e. the standard-pass-ont.owl) and afterwards
 *   loadModels(paths) with path references to the owl files containing the models</li>
 *   <li>loadModels(paths, true) with path references to the owl files containing the models.</li>
 * </ul>
 * <p>Loading the parsing structure is expensive, so it is advised to do it separately and not reload it if not necessary.</p>
 */

public class PASSReaderWriter implements IPASSReaderWriter {
    /**
     * The element factory gets an uri that should be parsed and a list of possible instances of classes this uri can be instanciated with.
     * The list of possible instances is stored inside the parsingDict.
     * the element factory than decides which instance to use.
     */
    private IPASSProcessModelElementFactory<IParseablePASSProcessModelElement> elementFactory = new BasicPASSProcessModelElementFactory();

    /**
     * The matcher creates a mapping between owl classes (found in an owl file) and c# classes (found in the current assembly)
     */
    private final IParsingTreeMatcher matcher = new ParsingTreeMatcher();
    private static final Logger LOGGER = Logger.getLogger(PASSReaderWriter.class.getName());

    /**
     * A parsing dictionary that contains the mapping between ontology classes and a list of possible java classes
     */
    private Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> parsingDict
            = new HashMap<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>>();

    /**
     * Holds the current instance of the PASSReaderWriter class.
     * The class is only instanciated once
     */
    private static PASSReaderWriter readerWriter;

    /**
     * Get the current instance of the PASSReaderWriter class.
     * The class structure must only be loaded once, afterwards each class can instantly load models
     * without overwriting the parsing structure when fetching this instance.
     *
     * @return The instance of this PASSReaderWriter class
     */
    public static PASSReaderWriter getInstance() {
        if (readerWriter == null) {
            readerWriter = new PASSReaderWriter();
        }
        return readerWriter;
    }

    /**
     * The private constructor that initializes logs
     */
    private PASSReaderWriter() {
        String logName = "logfile.txt";
        String path = System.getProperty("user.dir");
        Path logDirectory = Paths.get(path, "logs");

        try {
            Files.createDirectories(logDirectory);
            Path logFile = logDirectory.resolve(logName);
            if (Files.exists(logFile)) {
                Files.delete(logFile);
            }
            System.out.println("Writing logs to " + logFile);

            FileHandler fileHandler = new FileHandler(logFile.toString(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> getParsingDict() {
        Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> newParsingDict =
                new HashMap<>();
        for (Map.Entry<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> pair : parsingDict.entrySet()) {
            newParsingDict.put(pair.getKey(), new ArrayList<>(pair.getValue()));
        }
        return newParsingDict;
    }

    public void loadOWLParsingStructure(List<String> filepathsToOWLFiles) {
        List<OntModel> owlStructureGraphs = new ArrayList<>();
        LOGGER.info("Beginnig to create parsing structure matching...");
        for (String filepath : filepathsToOWLFiles) {
            try {
                // Create a new OntologyGraph
                OntModel owlGraph = ModelFactory.createOntologyModel();
                // Load files into it
                owlGraph.read(filepath);
                owlStructureGraphs.add(owlGraph);
            } catch (RiotException parseException) {
                LOGGER.log(Level.INFO, "Parser Error when reading the new File " + parseException);
                System.out.println();
            }
        }
        LOGGER.info("Read all structure defining ontology graphs.");
        parsingDict = matcher.loadOWLParsingStructure(owlStructureGraphs);

    }

    public List<IPASSProcessModel> loadModels(List<String> filepaths, boolean overrideOWLParsingStructure) {
        System.out.println("Reading input owl files...");

        //IList<IPASSProcessModel> passProcessModels = new List<IPASSProcessModel>();
        List<OntModel> loadedModelGraphs = new ArrayList<>();
        List<OntModel> owlStructureGraphs = new ArrayList<>();

        for (String filepath : filepaths) {
            try {
                // Create a new OntologyGraph
                OntModel owlGraph = ModelFactory.createOntologyModel();
                // Load files into it
                owlGraph.read(filepath);
                owlStructureGraphs.add(owlGraph);
                //TODO: Die zeile neu implementieren
                if (!isStandardPass(owlGraph.getNsPrefixURI(""))) {
                    loadedModelGraphs.add(owlGraph);
                }
                LOGGER.info("Done reading the new File: " + filepath);
            } catch (RiotException parseException) {
                LOGGER.log(Level.INFO, "Parser Error when reading the new File " + parseException);
                System.out.println("Error reading file.");
                return null;
            }
        }

        System.out.println("Done.");
        if (overrideOWLParsingStructure)
            parsingDict = matcher.loadOWLParsingStructure(owlStructureGraphs);

        // Stores all found models across all graphs
        List<IPASSProcessModel> passProcessModels = new LinkedList<IPASSProcessModel>();

        System.out.println("Instantiating in memory models...");
        ConsoleProgressBar bar = new ConsoleProgressBar();
        int count = 0;

        for (OntModel graph : loadedModelGraphs) {
            Map<String, List<String>> namedIndividualsDict = findAllNamedIndividualTriples(graph);
            count++;
            bar.report((double) count / filepaths.size() * 2);

            // Get models with elements from the current graph and merge it in the list of all models
            List<IPASSProcessModel> createdInstances = createClassInstancesFromNamedIndividuals(graph, namedIndividualsDict);
            passProcessModels.addAll(createdInstances);
            passProcessModels = passProcessModels.stream().distinct().collect(Collectors.toList());
            count++;
            bar.report((double) count / filepaths.size() * 2);
        }
        System.out.println("Done.");
        System.out.println("Finished in-memory model creation");

        return passProcessModels;
    }

    public List<IPASSProcessModel> loadModels(List<String> filepaths) {
        System.out.println("Reading input owl files...");

        //IList<IPASSProcessModel> passProcessModels = new List<IPASSProcessModel>();
        List<OntModel> loadedModelGraphs = new ArrayList<>();
        List<OntModel> owlStructureGraphs = new ArrayList<>();

        for (String filepath : filepaths) {
            try {
                // Create a new OntologyGraph
                OntModel owlGraph = ModelFactory.createOntologyModel();
                // Load files into it
                owlGraph.read(filepath);
                owlStructureGraphs.add(owlGraph);
                //TODO: Die zeile neu implementieren
                if (!isStandardPass(owlGraph.getNsPrefixURI(""))) {
                    loadedModelGraphs.add(owlGraph);
                }
                LOGGER.info("Done reading the new File: " + filepath);
            } catch (RiotException parseException) {
                LOGGER.log(Level.INFO, "Parser Error when reading the new File " + parseException);
                System.out.println("Error reading file.");
                return null;
            }
        }

        System.out.println("Done.");

        // Stores all found models across all graphs
        List<IPASSProcessModel> passProcessModels = new LinkedList<IPASSProcessModel>();

        System.out.println("Instantiating in memory models...");
        ConsoleProgressBar bar = new ConsoleProgressBar();
        int count = 0;

        for (OntModel graph : loadedModelGraphs) {
            Map<String, List<String>> namedIndividualsDict = findAllNamedIndividualTriples(graph);
            count++;
            bar.report((double) count / filepaths.size() * 2);

            // Get models with elements from the current graph and merge it in the list of all models
            List<IPASSProcessModel> createdInstances = createClassInstancesFromNamedIndividuals(graph, namedIndividualsDict);
            passProcessModels.addAll(createdInstances);
            passProcessModels = passProcessModels.stream().distinct().collect(Collectors.toList());
            count++;
            bar.report((double) count / filepaths.size() * 2);
        }
        System.out.println("Done.");
        System.out.println("Finished in-memory model creation");

        return passProcessModels;
    }

    /**
     * The .NetRDF library has a few problems with input files that are basically stupid
     * E.g. an rdf:RDF xmlns="" at the beginning of an RDF document  may cause an
     * Invalid URI exception. Sadly some tools like Protegée generate exactly stuff like that
     * This method simple tries to clean known problems
     *
     * @param filePath
     */
    private void preCleanFile(String filePath) {
        String xmlnsConstruct = "xmlns=\"\"";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder fileContents = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(xmlnsConstruct)) {
                    line = line.replace(xmlnsConstruct, "");
                }
                fileContents.append(line).append(System.lineSeparator());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(fileContents.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Verifies whether a triple (as string) is part of a standard pass definition owl or a model
     *
     * @param triple a triple converted to string
     * @return true if it is part of a standard pass document, false if it is only part of a normal model
     */
    private boolean isStandardPass(Statement triple) {
        return isStandardPass(triple.toString());
    }

    /**
     * Verifies whether a triple (as string) is part of a standard pass definition owl or a mode
     *
     * @param trpl a triple converted to string
     * @return true if it is part of a standard pass document, false if it is only part of a normal model
     */
    private boolean isStandardPass(String trpl) {
        return (trpl.contains("standard") && trpl.contains("pass")) || (trpl.contains("abstract") && trpl.contains("pass"));
    }

    /**
     * Finds and creates all the named individuals in the given files and creates a new list with all the individuals
     *
     * @param graph
     * @return
     */
    private Map<String, List<String>> findAllNamedIndividualTriples(Model graph) {
        StmtIterator stmtIterator = graph.listStatements();
        List<Statement> namedIndividualsList = new ArrayList<>();
        Map<String, List<String>> namedIndividualsDict = new HashMap<>();

        // Iterate over triples in the graph
        //TODO: hier kommt die Liste durcheinander
        for (Statement triple : graph.listStatements().toList()) {

            // Add named individuals
            if (triple.getObject().toString().contains("NamedIndividual") && triple.getSubject().toString().contains("#") && !isStandardPass(triple)) {
                namedIndividualsList.add(triple);
            }
        }

        for (Statement t : namedIndividualsList) {
            Resource subject = t.getSubject();
            StmtIterator triplesWithNamedIndividualSubject = graph.listStatements(subject, null, (RDFNode) null);

            while (triplesWithNamedIndividualSubject.hasNext()) {
                Statement l = triplesWithNamedIndividualSubject.next();
                RDFNode predicate = l.getPredicate();
                RDFNode object = l.getObject();

                // Get the one triple that specifies type and does not contain NamedIndividual as object
                if (predicate.toString().contains("type") && !object.toString().contains("NamedIndividual")) {
                    if (!namedIndividualsDict.containsKey(subject.toString())) {
                        List<String> list = new ArrayList<>();
                        list.add(object.toString());
                        namedIndividualsDict.put(subject.toString(), list);
                    } else {
                        namedIndividualsDict.get(subject.toString()).add(object.toString());
                    }
                }
            }
        }
        return namedIndividualsDict;
    }

    /**
     * Method that creates the empty Instances of the Objects which later get completed by the completetObjects() method
     *
     * @param graph                The graph used for parsing
     * @param namedIndividualsDict A dictionary containing the uri for each NamedIndividual as key and the type(s) as value (in the form of a list)
     * @return
     */
    private List<IPASSProcessModel> createClassInstancesFromNamedIndividuals(Model graph, Map<String, List<String>> namedIndividualsDict) {
        Map<String, IParseablePASSProcessModelElement> createdElements = new HashMap<>();
        List<IPASSProcessModel> passProcessModels = new ArrayList<IPASSProcessModel>();

        //Object:     owl:Ontology
        //Predicate:  rdf:type
        // The base uri for the current parsing graph
        String baseUri = graph.listStatements(null, RDF.type, OWL.Ontology).next().getSubject().toString();

        for (Map.Entry<String, List<String>> pair : namedIndividualsDict.entrySet()) {

            // Generates a new modelElement and returns the type this element is instantiated with
            // (i.e. an abstract DoState has the types "AbstractState" and "DoState", but is instantiated with a DoState, so this method returns "DoState"
            IParseablePASSProcessModelElement modelElement = elementFactory.createInstance(parsingDict, pair.getValue());
            //String elementType;
            // If the factory found a fitting element
            if (modelElement != null) {
                //String elementType;
                // The model element receives its triples which define all its characteristics in the form of incomplete triples
                /**StmtIterator elementTriples = graph.listStatements(graph.getResource(new URI(pair.getKey()).toString()), null, (RDFNode) null);
                 List<IIncompleteTriple> elementIncompleteTriples = new ArrayList<>();

                 while (elementTriples.hasNext()) {
                 Statement triple = elementTriples.next();
                 elementIncompleteTriples.add(new IncompleteTriple(triple, baseUri));
                 }
                 modelElement.addIncompleteTriples(elementIncompleteTriples);
                 */
                // The model element receives its triples which define all its characteristics in the form of incomplete triples
                // Incomplete triples carry no information about the subject, as the subjects uri can change during parsing.
                URI uri = null; // pair.getKey() muss eine String-URL sein
                try {
                    uri = new URI(pair.getKey());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                Resource resource = graph.createResource(uri.toString());
                Node subject = resource.asNode();

                List<Triple> elementTriples = new ArrayList<>();
                ExtendedIterator<Triple> iterator = graph.getGraph().find(Triple.createMatch(subject, Node.ANY, Node.ANY));
                while (iterator.hasNext()) {
                    elementTriples.add(iterator.next());
                }
                List<IIncompleteTriple> elementIncompleteTriples = new ArrayList<>();
                for (Triple triple : elementTriples) {
                    elementIncompleteTriples.add(new IncompleteTriple(triple, baseUri));
                }
                modelElement.addIncompleteTriples(elementIncompleteTriples);
                // Important! The ModelComponentID is overwritten by the suffix of the elements uri (= "baseuri#suffix").
                if (elementTriples.size() > 0) {
                    modelElement.setModelComponentID(StaticFunctions.removeBaseUri(elementTriples.get(0).getSubject().toString(), baseUri));
                }

                if (modelElement instanceof IPASSProcessModel passProcessModel) {
                    passProcessModels.add(passProcessModel);
                    passProcessModel.setBaseURI(baseUri);
                    IPASSGraph modelBaseGraph = passProcessModel.getBaseGraph();

                    // Add all the triples to the graph that describe the owl file directly (version iri, imports...)
                    Resource ressubject = graph.createResource(baseUri);

                    StmtIterator iter = graph.listStatements(ressubject, null, (RDFNode) null);
                    while (iter.hasNext()) {
                        Statement stmt = iter.nextStatement();
                        modelBaseGraph.addTriple(stmt.asTriple());
                    }


                } else {
                    // if the factory could not find a fitting element
                    // TODO set Model before adding Attributes
                    //modelElement.addAllAttributes(this, pair.Key);
                    //modelElement.setModelComponentID(pair.Key);
                }
                createdElements.put(modelElement.getModelComponentID(), modelElement);
            }
        }

        // Now all elements are instanciated and received their describing triples.
        // Triples that do not point to other elements have already been parsed (i.e. hasModelComponentID, hasComment ...)

        if (passProcessModels.isEmpty()) {
            IPASSProcessModel passProcessModell = new PASSProcessModel(graph.getNsPrefixURI(""));
            passProcessModell.createUniqueModelComponentID("defaultModelID");
            passProcessModels.add(passProcessModell);
        }

        // To parse all triples that link to other elements, the model is involved
        // The model is parsed top down; Every element that needs the reference to another can ask the model
        // by passing the suffix of the uri of the required element (which is also the ModelComponentID).
        for (IPASSProcessModel model : passProcessModels) {
            if (model instanceof IParseablePASSProcessModelElement parseable) {
                parseable.completeObject(createdElements);
            }
        }

        // ? Was tun mit elementen die in keinem Model sind? Wie prüfen ?

        return passProcessModels;
    }

    /**
     * // TODO: Out-Methode
     * public String exportModel(IPASSProcessModel model, String filepath, Model exportGraph) {
     * // Get the graph hold by the model and use the export function given by the library
     * String fullPath = (filepath.endsWith(".owl")) ? filepath : filepath + ".owl";
     * model.getBaseGraph().exportTo(fullPath);
     * if (model.getBaseGraph() instanceof PASSGraph graph)
     * exportGraph = graph.getGraph();
     * else exportGraph = null;
     * return fullPath;
     * }
     * //TODO Out-Methode
     * public String exportModel(IPASSProcessModel model, String filepath) {
     * Model graph;
     * <p>
     * return exportModel(model, filepath, Model graph);
     * }
     */
    //TODO: Methode anpassen
    public Model exportModel(IPASSProcessModel model, String filepath) {
        // Get the graph hold by the model and use the export function given by the library
        Model exportGraph;
        String fullPath = (filepath.endsWith(".owl")) ? filepath : filepath + ".owl";
        model.getBaseGraph().exportTo(fullPath);
        if (model.getBaseGraph() instanceof PASSGraph graph)
            exportGraph = graph.getGraph();
        else exportGraph = null;
        return exportGraph;
    }

    public void setModelElementFactory(IPASSProcessModelElementFactory<IParseablePASSProcessModelElement> elementFactory) {
        if (elementFactory == null) return;
        this.elementFactory = elementFactory;
    }
}

