package alps.java.api.parsing;


import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.StandardPASS.PassProcessModelElements.PASSProcessModel;
import alps.java.api.util.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The main parser class (Singleton).
 *
 * To load a model contained in an owl/rdf formatted file, either use
 * - loadOWLParsingStructure() and pass path references to used ontology classes (i.e. the standard-pass-ont.owl) and afterwards
 * loadModels(paths) with path references to the owl files containing the models or
 * - loadModels(paths, true) with path references to the owl files containing the models.
 * Loading the parsing structure is expensive, so it is advised to do it seperately and not reload it if not neccessary.
 */
public class PASSReaderWriter implements IPASSReaderWriter
        {
        /**
         * The element factory gets an uri that should be parsed and a list of possible instances of classes this uri can be instanciated with.
         * The list of possible instances is stored inside the parsingDict.
         * the element factory than decides which instance to use.
         */
        private IPASSProcessModelElementFactory<IParseablePASSProcessModelElement> elementFactory = new BasicPASSProcessModelElementFactory();

            /**
             *  The matcher creates a mapping between owl classes (found in an owl file) and c# classes (found in the current assembly)
             */
            private final IParsingTreeMatcher matcher = new ParsingTreeMatcher();

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
             * @return The instance of this PASSReaderWriter class
             */
            public static PASSReaderWriter getInstance()
        {
        if (readerWriter == null)
        {
        readerWriter = new PASSReaderWriter();
        }
        return readerWriter;
        }

            /**
             * The private constructor that initializes logs
             */
            private PASSReaderWriter()
        {
        String logName = "logfile.txt";
        String path = Directory.GetCurrentDirectory();
        String cutPath = path.substring(0, path.indexOf("bin")) + "logs\\";

        Directory.CreateDirectory(cutPath);

        if (File.exists(cutPath + logName))
        {
        File.delete(cutPath + logName);
        }
        Console.WriteLine("Writing logs to " + cutPath + logName);

        Log.Logger = new LoggerConfiguration()
        .MinimumLevel.Debug()
        //.WriteTo.Console()
        .WriteTo.File(cutPath + logName)
        .CreateLogger();
        }



public Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> getParsingDict()
        {
        Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> newParsingDict =
        new HashMap<>();
        for(Map.Entry<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer> pair: parsingDict)
        {
        newParsingDict.add(pair.getKey(), new List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>(pair.getValue()));
        }
        return newParsingDict;
        }

public void loadOWLParsingStructure(List<String> filepathsToOWLFiles)
        {
        List<OntologyGraph> owlStructureGraphs = new List<OntologyGraph>();
        Log.Information("Beginnig to create parsing structure matching...");
        for(String filepath: filepathsToOWLFiles)
        {
        try
        {
        // Create a new OntologyGraph
        OntologyGraph owlGraph = new OntologyGraph();
        // Load files into it
        owlGraph.LoadFromFile(filepath);
        owlStructureGraphs.add(owlGraph);
        }
        catch (RdfParseException parseException)
        {
        Log.Error("Parser Error when reading the new File " + parseException);
        Console.WriteLine();
        // TODO error loggen
        }
        }
        Log.Information("Read all structure defining ontology graphs.");
        parsingDict = matcher.loadOWLParsingStructure(owlStructureGraphs);

        }

public List<IPASSProcessModel> loadModels(List<String> filepaths, boolean overrideOWLParsingStructure)
        {
        Console.Write("Reading input owl files...");

        //IList<IPASSProcessModel> passProcessModels = new List<IPASSProcessModel>();
        List<OntologyGraph> loadedModelGraphs = new LinkedList<OntologyGraph>();
        List<OntologyGraph> owlStructureGraphs = new LinkedList<OntologyGraph>();

        for(String filepath: filepaths)
        {
        try
        {
        // Create a new OntologyGraph
        OntologyGraph owlGraph = new();
        // Load files into it
        owlGraph.LoadFromFile(filepath);
        owlStructureGraphs.add(owlGraph);

        if (!isStandardPass(owlGraph.BaseUri.ToString())) { loadedModelGraphs.add(owlGraph); }
        Log.Information("Done reading the new File: " + filepath);
        }
        catch (RdfParseException parseException)
        {
        Log.Error("Parser Error when reading the new File " + parseException);
        Console.WriteLine("Error reading file.");
        return null;
        }
        catch (IOException parseException)
        {
        Log.Error("Parser Error when reading the new File " + parseException);
        Console.WriteLine("Error reading file.");
        return null;
        }
        }

        Console.WriteLine("Done.");
        if (overrideOWLParsingStructure)
        parsingDict = matcher.loadOWLParsingStructure(owlStructureGraphs);

        // Stores all found models across all graphs
        List<IPASSProcessModel> passProcessModels = new LinkedList<IPASSProcessModel>();

        Console.Write("Instantiating in memory models...");
        ConsoleProgressBar bar = new ConsoleProgressBar();
        int count = 0;

        for(OntologyGraph graph: loadedModelGraphs)
        {
        Map<String, List<String>> namedIndividualsDict = findAllNamedIndividualTriples(graph);
        count++;
        bar.report((double)count / filepaths.Count*2);

        // Get models with elements from the current graph and merge it in the list of all models
        passProcessModels = passProcessModels.Union(createClassInstancesFromNamedIndividuals(graph, namedIndividualsDict)).ToList();
        count++;
        bar.report((double)count / filepaths.Count * 2);
        }
        Console.WriteLine("Done.");
        Console.WriteLine("Finished in-memory model creation");

        return passProcessModels;
        }

            /**
             * Verifies whether a triple (as string) is part of a standard pass definition owl or a model
             * @param triple a triple converted to string
             * @return true if it is part of a standard pass document, false if it is only part of a normal model
             */
            private boolean isStandardPass(Triple triple)
        {
        return isStandardPass(triple.toString());
        }

            /**
             * Verifies whether a triple (as string) is part of a standard pass definition owl or a mode
             * @param trpl a triple converted to string
             * @return true if it is part of a standard pass document, false if it is only part of a normal model
             */
            private boolean isStandardPass(String trpl)
        {
        return (trpl.contains("standard") && trpl.contains("pass")) || (trpl.contains("abstract") && trpl.contains("pass"));
        }

            /**
             * Finds and creates all the named individuals in the given files and creates a new list with all the individuals
             * @param graph
             * @return
             */
            private Map<String, List<String>> findAllNamedIndividualTriples(Model graph)
        {
        IEnumerable<Triple> triplesWithNamedIndividualSubject;
        List<Triple> namedIndividualsList = new LinkedList<Triple>();
        Map<String, List<String>> namedIndividualsDict = new HashMap<>();

        // Iterate over triples in the graph
        for(Triple triple: graph.Triples)
        {
        // Add named individuals
        if (triple.Object.ToString().Contains("NamedIndividual") && triple.Subject.ToString().Contains("#") && !isStandardPass(triple))
        {
        namedIndividualsList.add(triple);
        }
        }


        for(Triple t: namedIndividualsList)
        {
        triplesWithNamedIndividualSubject = graph.Triples.WithSubject(t.Subject);

        for(Triple l: triplesWithNamedIndividualSubject)
        {
        // Get the one triple that specifies type and does not contain NamedIndividual as object
        if (l.Predicate.ToString().Contains("type") && !l.Object.ToString().Contains("NamedIndividual"))
        {
        //this.namedIndiviualsType.Add(l);
        if (!namedIndividualsDict.containsKey(t.Subject.ToString()))
        {
        namedIndividualsDict.add(l.Subject.ToString(), new List<string> { l.Object.ToString() });
        }
        else
        {
        namedIndividualsDict[l.Subject.ToString()].Add(l.Object.ToString());
        }
        }
        }
        }

        return namedIndividualsDict;

        }

            /**
             *  Method that creates the empty Instances of the Objects which later get completed by the completetObjects() method
             * @param graph The graph used for parsing
             * @param namedIndividualsDict A dictionary containing the uri for each NamedIndividual as key and the type(s) as value (in the form of a list)
             * @return
             */
            private List<IPASSProcessModel> createClassInstancesFromNamedIndividuals(Model graph, Map<String, List<String>> namedIndividualsDict)
        {
        Map<String, IParseablePASSProcessModelElement> createdElements = new HashMap<>();
        List<IPASSProcessModel> passProcessModels = new LinkedList<IPASSProcessModel>();

        //Object:     owl:Ontology
        //Predicate:  rdf:type
        // The base uri for the current parsing graph
        String baseUri = graph.Triples.WithObject(graph.getUriNode("owl:Ontology")).First().Subject.ToString();

        for(Map.Entry<String, List<String>> pair: namedIndividualsDict)
        {

        // Generates a new modelElement and returns the type this element is instantiated with
        // (i.e. an abstract DoState has the types "AbstractState" and "DoState", but is instantiated with a DoState, so this method returns "DoState"
        String elementType = elementFactory.createInstance(parsingDict, pair.getValue(), IParseablePASSProcessModelElement modelElement);

        // If the factory found a fitting element
        if (!(modelElement == null))
        {
        // The model element receives its triples which define all its characteristics in the form of incomplete triples
        // Incomplete triples carry no information about the subject, as the subjects uri can change during parsing.
        List<Triple> elementTriples = new LinkedList<Triple>(graph.getTriplesWithSubject(graph.GetUriNode(new URI(pair.getKey()))));
        List<IIncompleteTriple> elementIncompleteTriples = new LinkedList<IIncompleteTriple>();

        for(Triple triple: elementTriples)
        elementIncompleteTriples.add(new IncompleteTriple(triple, baseUri));
        modelElement.addTriples(elementIncompleteTriples);

        // Important! The ModelComponentID is overwritten by the suffix of the elements uri (= "baseuri#suffix").
        if (elementTriples.Count > 0)
        modelElement.setModelComponentID(StaticFunctions.removeBaseUri(elementTriples[0].Subject.ToString(), baseUri));

        if (modelElement instanceof IPASSProcessModel)
        {
            IPASSProcessModel passProcessModell = (IPASSProcessModel) modelElement;
        passProcessModels.add(passProcessModell);
        passProcessModell.setBaseURI(baseUri);
        IPASSGraph modelBaseGraph = passProcessModell.getBaseGraph();

        // Add all the triples to the graph that describe the owl file directly (version iri, imports...)
        for(Triple triple: graph.Triples.WithSubject(graph.GetUriNode(new URI(baseUri))))
        modelBaseGraph.addTriple(triple);

        }
        else
        {
        // if the factory could not find a fitting element
        // TODO set Model before adding Attributes
        //modelElement.addAllAttributes(this, pair.Key);
        //modelElement.setModelComponentID(pair.Key);
        }
        createdElements.add(modelElement.getModelComponentID(), modelElement);
        }
        }

        // Now all elements are instanciated and received their describing triples.
        // Triples that do not point to other elements have already been parsed (i.e. hasModelComponentID, hasComment ...)

        if (passProcessModels.Count == 0)
        {
        IPASSProcessModel passProcessModell = new PASSProcessModel(graph.BaseUri.ToString());
        passProcessModell.createUniqueModelComponentID("defaultModelID");
        passProcessModels.Add(passProcessModell);
        }

        // To parse all triples that link to other elements, the model is involved
        // The model is parsed top down; Every element that needs the reference to another can ask the model
        // by passing the suffix of the uri of the required element (which is also the ModelComponentID).
        for(IPASSProcessModel model: passProcessModels) {
            if (model instanceof IParseablePASSProcessModelElement){
                IParseablePASSProcessModelElement parseable = (IParseablePASSProcessModelElement) model;
                parseable.completeObject(createdElements);
        }
        }

        // ? Was tun mit elementen die in keinem Model sind? Wie prüfen ?

        return passProcessModels;
        }


public String exportModel(IPASSProcessModel model, String filepath, Model exportGraph)
        {
        // Get the graph hold by the model and use the export function given by the library
        String fullPath = (filepath.endsWith(".owl")) ? filepath : filepath + ".owl";
        model.getBaseGraph().exportTo(fullPath);
        if (model.getBaseGraph() instanceof PASSGraph graph)
        exportGraph = graph.getGraph();
        else exportGraph = null;
        return fullPath;
        }

public String exportModel(IPASSProcessModel model, String filepath)
        {
        return exportModel(model, filepath, Model graph);
        }

public void setModelElementFactory(IPASSProcessModelElementFactory<IParseablePASSProcessModelElement> elementFactory)
        {
        if (elementFactory == null) return;
        this.elementFactory = elementFactory;
        }
        }

