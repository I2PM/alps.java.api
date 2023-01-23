package alps.java.api.parsing;


import alps.java.api.StandardPASS.PASSProcessModelElement;
import alps.java.api.util.ConsoleProgressBar;
import alps.java.api.util.ITreeNode;
import alps.java.api.util.ReflectiveEnumerator;
import alps.java.api.util.TreeNode;
import org.apache.jena.atlas.lib.Pair;

import java.util.*;

/**
 * This class creates trees for the owl class hierarchy and the c# class hierarchy dynamically at runtime.
 * Afterwards, the nodes in the trees are mapped together.
 * The mapping is used by the parser to instantiate owl class instances with c# class instances
 */
public class ParsingTreeMatcher implements IParsingTreeMatcher
        {

public Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>,Integer>>> loadOWLParsingStructure(List<OntologyGraph> owlStructureGraphs)
        {
        Log.Information("Merging all input graphs...");
        Console.Write("Generating class mapping for parser...");
        ConsoleProgressBar consoleBar = new();
        consoleBar.report(0);
        OntologyGraph parsingStructureOntologyGraph = new();

        // Merge the input of all files in one big graph
        for(OntologyGraph owlGraph: owlStructureGraphs)
        {
        parsingStructureOntologyGraph.merge(owlGraph);
        }

        consoleBar.report(0.25);

        Log.Information("Creating owl inheritance tree from merged graphs...");

        // Create the inheritance tree for the loaded owl classes
        // The base classes are the classes that have only child classes, no parent classes.
        // They are possible base classes for the tree structure
        List<OntologyClass> baseClasses = createOWLInheritanceTree(parsingStructureOntologyGraph);

        consoleBar.report(0.5);
        Log.Information("Dynamically created owl class tree");
        Log.Information("Creating c# class inheritance tree from classes known to the assembly...");

        // Create the inheritance tree for the c# classes by recursively finding child classes to the PASSProcessModelElement class
        // Does also find child classes of external projects if they registered themself at the ReflectiveEnumerator class.
        ITreeNode<IParseablePASSProcessModelElement> treeRootNode = createClassInheritanceTree();

        consoleBar.report(0.75);
        Log.Information("Dynamically created c# class tree");

        // Maps a list of possible c# classes to each ontology class
        Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> parsingDict
        = new HashMap<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>>();

        // Map the each base class and its child classes with the c# classes
        for(OntologyClass baseClass: baseClasses)
        {
        createParsingStructureFromTrees(parsingDict, baseClass, treeRootNode);
        }

        Log.Information("Finished class mapping");

        consoleBar.report(1);
        Console.Write("Done.");

        return parsingDict;
        }


// ################################ OWL class tree creation ################################


            /**
             * Creates the inheritance tree for owl classes out of the given files
             * @param parsingStructureOntologyGraph
             * @return
             */
            private List<OntologyClass> createOWLInheritanceTree(OntologyGraph parsingStructureOntologyGraph)
        {
        List<OntologyClass> nodesWithoutParents = new LinkedList<OntologyClass>();

        // Find a root for the tree by finding alls classes that have no parent
        for(OntologyClass ontClass: parsingStructureOntologyGraph.OwlClasses)
        {
        if (!ontClass.toString().contains("auto"))
        {
        // Get all nodes that have no parent and are classes
        if (hasParentClass(ontClass) == false)
        {
        nodesWithoutParents.add(ontClass);
        }
        }
        }

        List<OntologyClass> baseClasses = new LinkedListList<OntologyClass>();

        // Set the PassProcessModelElement as root
        for(OntologyClass ontNode: nodesWithoutParents)
        {
        if (ontNode.toString().ToLower().Contains("passprocessmodelelement"))
        {
        baseClasses.add(ontNode);
        }
        }
        if (baseClasses.Count > 0) return baseClasses;
        Log.Error("No PassProcessModelElement found in the loaded graphs");
        return null;
        }

            /**
             * Checks whether an Ontology class has a parent class or not (in the given graph)
             * @param ontClass the Ontology class
             * @return true if a parent class exists, false if not
             */
            private boolean hasParentClass(OntologyClass ontClass)
        {
        if (ontClass.SuperClasses.ToList().Count > 0)
        {
        for(OntologyClass superClass: ontClass.SuperClasses.ToList())
        {
        if (!(superClass.toString().ToLower().Contains("auto"))) return true;
        }
        }
        return false;
        }


// ########################################################################################


// ################################ Java class tree creation ################################


            /**
             * Creates the inheritance tree for the c# classes known to this library
             * @return
             */
            private ITreeNode<IParseablePASSProcessModelElement> createClassInheritanceTree()
        {
        // Start with the default root: the PASSProcessModelElement class
        ITreeNode<IParseablePASSProcessModelElement> treeRootNode = new TreeNode<IParseablePASSProcessModelElement>(new PASSProcessModelElement());

        // Search recursively for classes that extend this class and add them to the tree
        findChildsAndAdd(treeRootNode);
        return treeRootNode;
        }

private void findChildsAndAdd(ITreeNode<IParseablePASSProcessModelElement> node)
        {
        // Get all classes that are known to the current project and that extend the given node
        IEnumerable<IParseablePASSProcessModelElement> enumerable = ReflectiveEnumerator.getEnumerableOfType(node.getContent());
        for(IParseablePASSProcessModelElement element: enumerable.ToList())
        {
        node.addChild(new TreeNode<IParseablePASSProcessModelElement>(element));
        }
        for(TreeNode<IParseablePASSProcessModelElement> childNode: node.getChildNodes())
        {
        findChildsAndAdd(childNode);
        }
        }


// ########################################################################################


// ##################################### Tree mapping #####################################


            /**
             * Creates a parsing dictionary containing Ontology urls as keys and instances of c# classes that can be used to parse the ontology classes.
             * If an ontlogy class cannot be parsed using any path through both trees (i.e. the trees differ in some places), this class and all its childs will be parsed
             * using the last instance class where the trees did not differ.
             * @param ontClass
             * @para, rootNode
             */
            private void createParsingStructureFromTrees(Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> parsingDict, OntologyClass ontClass, ITreeNode<IParseablePASSProcessModelElement> rootNode)
        {
        // Start with mapping the roots, they are both PASSProcessModelElement
        if (parsingDict.Count == 0) parsingDict.add(removeUri(ontClass.Resource.ToString()), new LinkedList<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>> { (rootNode, 0) });
        // Create a new dictionary for those urls that could not be mapped properly (need that later)
        ICompatibilityDictionary<OntologyClass, String> unmappableDict = new CompatibilityDictionary<OntologyClass, String>();

        // Start to parse childs, passing the parent ontology class and the parent C#-class, as well as the dict off classes already parsed (only the root)
        parseChilds(parsingDict, ontClass, rootNode, unmappableDict);
        Log.Information("##########################################");
        Log.Information("Created parsing structure for owl classes.");
        Log.Information(parsingDict.Count + " classes could be mapped correctly");
        Log.Information(unmappableDict.Count + " were not mapped correctly");
        Log.Information("##########################################");
        for(Map.Entry<OntologyClass, String> pair: unmappableDict.entrySet())
        {
        mapRestWithParentNode(parsingDict, pair.getKey(), pair.getValue());
        }
        }


            /**
             * Tries to find Java-classes that can parse given urls of the ontology
             * both structures (the ontology inheritance classes as well as the c# inheritance classes) are structured as a tree
             * This method is called recursive. It tries to map each child of the given ontology parent class with one or more childs of the given c# parent class.
             * It is asserted that the c# parent class was previously selected to be able to parse the ontology parent class (else this would make no sense)
             * this algorithm is trying to map both tree structures together, containing the mapping inside a parsing dictionary.
             * If a part of the Ontology tree cannot be mapped, the algorithm marks this url in another dict and does not try to map the childs of this element.
             * @param parsingDict Keeps all the valid mappings found down to the current class
             * @param parentOntClass the parent Ontology class that can be parsed with the instance of IPASSProcessModelElement in the current parentNode
             * @param parentNode A node containing an instance representing a valid parsing class for the Ontology class given by parentOntClass
             * @param unmappableDict A dict of elements that could not be mapped
             */
            private void parseChilds(Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> parsingDict, OntologyClass parentOntClass, ITreeNode<IParseablePASSProcessModelElement> parentNode, ICompatibilityDictionary<OntologyClass, String> unmappableDict)
        {
        List<ITreeNode<IParseablePASSProcessModelElement>> childsBeenParsed = new LinkedList<>();

        // Go through all ontology child classes of the ontology class
        for(OntologyClass directSubclass: parentOntClass.DirectSubClasses)
        {
        List<ITreeNode<IParseablePASSProcessModelElement>> parseableClasses = new LinkedList<ITreeNode<IParseablePASSProcessModelElement>>();
        String url = removeUri(directSubclass.Resource.ToString());
        // Go through all c# child classes of the parent c# class instance
        for(ITreeNode<IParseablePASSProcessModelElement> childNode: parentNode.getChildNodes())
        {
        // Check if the class is a correct instanciation for the current url
        if (childNode.getContent().canParse(url) != PASSProcessModelElement.CANNOT_PARSE)
        {
        // add it to parseable classes
        parseableClasses.add(childNode);
        }
        }

        // If no parseable class is found:
        // Add the url to the list of classes that could not be parsed correctly.
        // The parent could be parsed correctly (else this method would not have been called for the child),
        // So the unparsed child will be parsed by using the class that can parse the parent
        if (parseableClasses.Count == 0 && !parsingDict.containsKey(url))
        {
        String parentURI = removeUri(parentOntClass.Resource.ToString());
        unmappableDict.tryAdd(directSubclass, parentURI);
        }
        else
        {
        for(OntologyClass ontClass: unmappableDict.keySet())
        {
        if (removeUri(ontClass.Resource.ToString()).Equals(url)) { unmappableDict.remove(ontClass); break; }
        }

        // We only want to store the most specific instantiation of a class.
        // All classes that are base classes to the currently found class - with our class still being able to parse the url - will be removed from the list
        List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> toBeRemoved = new LinkedList<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>>;
        if (parsingDict.containsKey(url))
        {
        for(ITreeNode<IParseablePASSProcessModelElement> element: parseableClasses)
        {
        for((Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer> tuple: parsingDict[url])
        {
        ITreeNode<IParseablePASSProcessModelElement> node = tuple.Item1;
        if (element.isSubClassOf(node))
        {
        toBeRemoved.add(tuple);
        }
        }
        }
        }
        // Remove all unnecessary classes (because currently found classes are more specific)
        for((ITreeNode<IParseablePASSProcessModelElement>, Integer) tuple: toBeRemoved)
        {
        parsingDict[url].remove(tuple);
        }

        // Add all classes that can parse the url
        for(ITreeNode<IParseablePASSProcessModelElement> element: parseableClasses)
        {
        if (parsingDict.containsKey(url))
        {
        parsingDict[url].Clear();
        }
        addToParsingDict(parsingDict, directSubclass, element, 0);
        childsBeenParsed.add(element);
        }

        // Then parse the childs of the matched pair respectively:
        // Now passing the current url and the matched instance as parents
        for(ITreeNode<IParseablePASSProcessModelElement> element: parseableClasses)
        {
        parseChilds(parsingDict, directSubclass, element, unmappableDict);
        }
        }
        }

        // This path will be taken if there is a more specific implementation of a class
        // Example: In owl, FullySpecifiedSubject exists. In C#, FullySpecifiedSubject and the subclass SpecialFullySpecifiedSubject exist,
        // which should also represent normal FullySpecifiedSubjects from the owl.
        // This code adds the SpecialFullySpecifiedSubject also as possible instanciation to the dictionary
        for(ITreeNode<IParseablePASSProcessModelElement> childNode: parentNode.getChildNodes())
        {
        // Find a child that was not specifically mapped to another owl class
        if (!childsBeenParsed.contains(childNode))
        {
        // Get all childclasses of the childNode
        Queue<ITreeNode<IParseablePASSProcessModelElement>> allNodes = new Queue<ITreeNode<IParseablePASSProcessModelElement>>();
        List<ITreeNode<IParseablePASSProcessModelElement>> mappableElements = new LinkedList<ITreeNode<IParseablePASSProcessModelElement>>();
        allNodes.enqueue(childNode);
        while (allNodes.Count > 0)
        {
        ITreeNode<IParseablePASSProcessModelElement> currentElement = allNodes.Dequeue();
        mappableElements.add(currentElement);
        for(ITreeNode<IParseablePASSProcessModelElement> child: currentElement.getChildNodes())
        {
        allNodes.Enqueue(child);
        }
        }
        String url = removeUri(parentOntClass.Resource.ToString());
        // Takes all the nodes that say they can parse the url and adds them to the dictionary
        mappableElements.Where(x => x.getContent().canParse(url) != PASSProcessModelElement.CANNOT_PARSE).toList().ForEach(x => addToParsingDict(parsingDict, parentOntClass, x, 0));
        }
        }
        }


            /**
             * Adds mapped elements to the parsing dictionary.
             * If there exists a list for the given key, the value is being added to the existing list.
             * @param parsingDict the dictionary used for parsing
             * @param ontClass the Ontology class used (url as key)
             * @param element the instance that can parse the ontology class (used as value)
             */
            private void addToParsingDict(Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> parsingDict, OntologyClass ontClass, ITreeNode<IParseablePASSProcessModelElement> element, int depth)
        {
        String ontResource = removeUri(ontClass.Resource.ToString());

        // If the key (the name of the owl class) is present in the mapping, add the new found class to the existing list (the value)
        if (parsingDict.containsKey(ontResource))
        {
        parsingDict[ontResource].Add((element, depth));
        }

        // If not, create a new entry with a new list containing one element
        else parsingDict.add(ontResource, new List<(ITreeNode<IParseablePASSProcessModelElement>, int)> { (element, depth) });
        }

            /**
             * This method maps all child classes of an owl class with the same java class.
             * If no specific c# class exists for an owl class, then the parser assumes that no specific java class exists for the children of the owl class exists as well.
             * All owl classes that are more specific than the mappable parent will not have a c# equivalent, so they are all parsed by using the mapped parent java class.
             * @param parsingDict The parsing dictionary
             * @param ontClass The ontology class that could not be mapped to a c# class
             * @param parentNodeKey the node that was mapped with the parent ontology class of the ontClass
             */

private void mapRestWithParentNode(Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> parsingDict, OntologyClass ontClass, String parentNodeKey)
        {
        mapRestWithParentNode(parsingDict, ontClass, parentNodeKey, 1);
        }

            /**
             * Do not use this directly, use:
             * {@link "mapRestWithParentNode(IDictionary{string, IList{(ITreeNode{IParseablePASSProcessModelElement}, int)}}, OntologyClass, ITreeNode{IParseablePASSProcessModelElement})"}
             * instead
             */
            private void mapRestWithParentNode(Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> parsingDict, OntologyClass ontClass, String parentNodeKey, int depth)
        {
        String ontResource = removeUri(ontClass.Resource.ToString());
        List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>> possibleMappedClasses = parsingDict[parentNodeKey];
        if (!(parsingDict.containsKey(ontResource)))
        {
        String possibleClasses = String.join(";", possibleMappedClasses.toList().Select(x => x.Item1.getContent().getClassName()));
        Log.Warning("Could not map " + ontResource + " correctly, mapped with " + possibleClasses + " instead");
        for((ITreeNode<IParseablePASSProcessModelElement>, Integer) possibleMappedClassPair: possibleMappedClasses)
        {
        addToParsingDict(parsingDict, ontClass, possibleMappedClassPair.Item1, depth);
        }
        }
        for(var childOntClass: ontClass.DirectSubClasses.ToList().Where(childOntClass => !(parsingDict.containsKey(removeUri(childOntClass.Resource.ToString())))))
        {
        mapRestWithParentNode(parsingDict, childOntClass, parentNodeKey, depth + 1);
        }
        }

            /**
             * Removes a base uri from a string if it is concathenated using #
             * @param stringWithUri
             * @return
             */
            private static String removeUri(String stringWithUri)
        {
        String[] splitStr = stringWithUri.split('#');
        return splitStr[splitStr.length - 1];
        }
        }


