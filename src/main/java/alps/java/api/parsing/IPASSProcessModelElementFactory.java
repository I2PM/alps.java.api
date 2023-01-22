package alps.java.api.parsing;

import alps.java.api.util.ITreeNode;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/// <summary>
/// An interface for factories that create PASSProcessModelElements
/// If own classes should be parsed by the library, an own implementation of a
/// IPASSProcessModelElementFactory must be provided
/// </summary>
public interface IPASSProcessModelElementFactory<T extends IParseablePASSProcessModelElement> {
    /// <summary>
    /// Creates an instance of an <see cref="IParseablePASSProcessModelElement"/>, depending on given names and parsing possibilities.
    /// </summary>
    /// <param name="parsingDict">A dictionary with possible instances for a given uri/name (key).
    ///     The mapped value is list of pairs. Each pair contains a possible instance as first item and and int indicating how good the name is parsed using this instance.
    ///     0 meaning the instance is a perfect match. 1 meaning the name cannot be directly parsed and is instantiated with the instance of the parent class and so on.</param>
    /// <param name="names">names of types the owl class belongs to</param>
    /// <param name="element">will contain a new instance of an <see cref="IParseablePASSProcessModelElement"/> if parsing was successful</param>
    /// <returns>true if parsing was successful, false if not</returns>
    String createInstance(Map<String, List<Pair<ITreeNode<IParseablePASSProcessModelElement>, Integer>>> parsingDict, List<String> names, T element);

}
