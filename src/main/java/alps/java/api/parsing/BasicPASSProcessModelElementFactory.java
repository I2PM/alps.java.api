package alps.java.api.parsing;

import alps.java.api.StandardPASS.PASSProcessModelElement;
import alps.java.api.util.ITreeNode;
import org.apache.jena.atlas.lib.Pair;

import java.util.*;

/**
 * A basic factory that creates standard ModelElements contained inside the alps.net.api library
 */
public class BasicPASSProcessModelElementFactory implements IPASSProcessModelElementFactory<IParseablePASSProcessModelElement>
        {
    public String createInstance(Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> parsingDict, List<String> names,IParseablePASSProcessModelElement element){
            element = new PASSProcessModelElement();
            Set<String> bestParseableNames = new HashSet<String>();
            int lowestParseDiff = int.MaxValue;

            // Check how good the instantiations for the names are.
            // Only use the names where instantiation-pairs have lowest numbers
            for(String uriName: names)
            {
            String name = removeUri(uriName);
            if (!parsingDict.containsKey(name)) continue;

            // The Item2 for each item signalizes how far off the c# class is mapped to an owl class.
            // Example: in owl, "BlueSubject" is subclass of "Subject". In c# we only know "Subject".
            // The owl "Subject" class has a mapping score of 0 and is mapped to the c# class "Subject"
            // The owl "BlueSubject" class has a mapping score of 1 and is mapped to the c# class "Subject", the last known parent class
            for((ITreeNode<IParseablePASSProcessModelElement>, int) pair: parsingDict[name])
            {
            if (pair.Item2 > lowestParseDiff) continue;

            // If the mapping is equally good as the mappings which were already found, add it
            if (pair.Item2 == lowestParseDiff)
            {
            bestParseableNames.add(name);
            }

            // If the mapping is better than the mappings which were already found, delete the old mappings and add it
            else
            {
            lowestParseDiff = pair.Item2;
            bestParseableNames.clear();
            bestParseableNames.add(name);
            }
            }
            }

            Map<IParseablePASSProcessModelElement, String> possibleElements = new HashMap<IParseablePASSProcessModelElement, String>();
            // Gather all possible instantiations
            for(String name: bestParseableNames)
            {
            for((ITreeNode<IParseablePASSProcessModelElement>, int) pair: parsingDict[name])
            possibleElements.add(pair.Item1.getContent(), name);
            }

            if (bestParseableNames.Count > 1)
            {
            boolean foundSubclass;

            // If one of the possible instances is superclass to another possible instance, throw the superclass out (only want most specific instance)
            do
            {
            foundSubclass = false;
            List<IParseablePASSProcessModelElement> remove = new LinkedList<>(IParseablePASSProcessModelElement);
            for(IParseablePASSProcessModelElement someElement: possibleElements.keySet())
            {
            // If none of the elements are equal and no other type in the list is subclass of the current type, continue
            if (!possibleElements.keySet().Any(someOtherElement => someElement.equals(someOtherElement) &&
            someOtherElement.GetType()
            .IsSubclassOf(someElement.getClass()))) continue;

            // Else add the redundant/parent type to be removed later
            remove.add(someElement);
            foundSubclass = true;
            }

            // Delete after finishing iteration over list
            for(IParseablePASSProcessModelElement someElement: remove)
            {
            possibleElements.remove(someElement);
            }
            } while (foundSubclass);
            }

            switch (possibleElements.Count)
            {
            // Take the only element and return a new instance
            case 1:
            element = possibleElements.keySet().First().getParsedInstance();
            return possibleElements.values().First();

            // Still some elements left that are both
            // - equally good in parsing
            // - no superclass to another class
            // parse the one having the longest matching name (longer name -> more specific instance ?)
            case > 1:
            {
            Map<IParseablePASSProcessModelElement, String> selectedPair = decideForElement(possibleElements);
            element = selectedPair.keySet().getParsedInstance();
            return selectedPair.values();
            }
    default:
            return null;
            }
            }
    private static String removeUri(String stringWithUri)
            {
            String[] splitStr = stringWithUri.split('#');

            // return only the last part
            return splitStr[splitStr.length -1 ];
            }

    protected Map<IParseablePASSProcessModelElement, String> decideForElement(Map<IParseablePASSProcessModelElement, String> possibleElements)
            {
            int max = -1;
            Map<IParseablePASSProcessModelElement, String> maxPair = new HashMap<IParseablePASSProcessModelElement, String>();
            int counter = 0;
            for(Map.Entry<IParseablePASSProcessModelElement, String> pair: possibleElements.entrySet())
            {
            int parseability = pair.getKey().canParse(pair.getValue());
            if (parseability > max)
            {
            max = parseability;
            maxPair = pair;
            }
            counter++;
            }

            return maxPair;

            }
            }

