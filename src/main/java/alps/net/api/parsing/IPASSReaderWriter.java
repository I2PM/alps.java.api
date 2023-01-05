package alps.net.api.parsing;

import alps.net.api.StandardPASS;
import java.util.List;
import org.apache.jena.rdf.model.Model;

public interface IPASSReaderWriter {
    /// <summary>
    /// Loads in all the required files given by filepaths.
    /// The files must be written in correct owl format.
    /// If the structure defining owl files are passed alongside the model defining ones,
    /// it must be declared via boolean that the current parsing structure should be overwritten.
    /// It is advised to only load the structure defining owl files once via <see cref="loadOWLParsingStructure"/>,
    /// because the creation of the parsing structure is likely to be an expensive operation.
    /// </summary>
    /// <param name="filepaths">The list of filepaths to valid formatted owl files</param>
    /// <param name="overrideOWLParsingStructure">Default false, should be set true when the structure defining owl files
    ///     are passed alongside the model defining ones, and the current parsing structure should be overwritten.</param>
    /// <returns>A list of <see cref="IPASSProcessModel"/> the were created from the given owl</returns>
    List<IPASSProcessModel> loadModels(List<String> filepaths, boolean overrideOWLParsingStructure);

    /// <summary>
    /// The abstract pass ont and/or the standard pass ont must be given
    /// either inside the same owl file or as seperate files. These files will be used
    /// to create a parsing structure for models.
    /// This operation is likely to be an expensive one and should only be used once if loaded models share the same structure.
    /// </summary>
    /// <param name="filepathsToOWLFiles">The list of filepaths to valid formatted owl files containing the structure for models and components</param>
    void loadOWLParsingStructure(List<String> filepathsToOWLFiles);

    /// <summary>
    /// Exports the given model as rdf/owl formatted file.
    /// The file will be saved under filename, this might be given as dynamic or static filepath.
    /// </summary>
    /// <param name="model">the model that should be exported</param>
    /// <param name="filepath">the path where the file will be saved</param>
    /// <param name="exportGraph">A graph representation of the exported owl file</param>
    /// <returns>The full filepath to the file that was exported</returns>
    String exportModel(IPASSProcessModel model, String filepath, Model exportGraph);

    /// <summary>
    /// Exports the given model as rdf/owl formatted file.
    /// The file will be saved under filename, this might be given as dynamic or static filepath.
    /// </summary>
    /// <param name="model">the model that should be exported</param>
    /// <param name="filepath">the path where the file will be saved</param>
    /// <returns>The full filepath to the file that was exported</returns>
    String exportModel(IPASSProcessModel model, String filepath);

    /// <summary>
    /// Allows to set another factory to create the elements.
    /// The standard factory <see cref="BasicPASSProcessModelElementFactory"/> is used automatically and decides on mapped elements based on the name similarity.
    /// If the factory should choose other elements, a new factory can be inserted here.
    /// </summary>
    /// <param name="factory"></param>
    void setModelElementFactory(IPASSProcessModelElementFactory<IParseablePASSProcessModelElement> factory);
}