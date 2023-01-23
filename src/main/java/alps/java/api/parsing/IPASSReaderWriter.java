package alps.java.api.parsing;


import java.util.List;

import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import org.apache.jena.rdf.model.Model;

public interface IPASSReaderWriter {

    /**
     * Loads in all the required files given by filepaths.
     * The files must be written in correct owl format.
     * If the structure defining owl files are passed alongside the model defining ones,
     * it must be declared via boolean that the current parsing structure should be overwritten.
     * It is advised to only load the structure defining owl files once via {@link "loadOWLParsingStructure"},
     * because the creation of the parsing structure is likely to be an expensive operation.
     * @param filepaths The list of filepaths to valid formatted owl files
     * @param overrideOWLParsingStructure Default false, should be set true when the structure defining owl files
     * are passed alongside the model defining ones, and the current parsing structure should be overwritten.
     * @return A list of {@link IPASSProcessModel} the were created from the given owl
     */
    List<IPASSProcessModel> loadModels(List<String> filepaths, boolean overrideOWLParsingStructure);

    /**
     * The abstract pass ont and/or the standard pass ont must be given
     * either inside the same owl file or as seperate files. These files will be used
     * to create a parsing structure for models.
     * This operation is likely to be an expensive one and should only be used once if loaded models share the same structure.
     * @param filepathsToOWLFiles The list of filepaths to valid formatted owl files containing the structure for models and components
     */
    void loadOWLParsingStructure(List<String> filepathsToOWLFiles);

    /**
     * Exports the given model as rdf/owl formatted file.
     * The file will be saved under filename, this might be given as dynamic or static filepath.
     * @param model the model that should be exported
     * @param filepath the path where the file will be saved
     * @param exportGraph A graph representation of the exported owl file
     * @return The full filepath to the file that was exported
     */
    String exportModel(IPASSProcessModel model, String filepath, Model exportGraph);

    /**
     * Exports the given model as rdf/owl formatted file.
     * The file will be saved under filename, this might be given as dynamic or static filepath.
     * @param model the model that should be exported
     * @param filepath the path where the file will be saved
     * @return The full filepath to the file that was exported
     */
    String exportModel(IPASSProcessModel model, String filepath);

    /**
     * Allows to set another factory to create the elements.
     * The standard factory {@link BasicPASSProcessModelElementFactory} is used automatically and decides on mapped elements based on the name similarity.
     * If the factory should choose other elements, a new factory can be inserted here.
     * @param factory
     */

    void setModelElementFactory(IPASSProcessModelElementFactory<IParseablePASSProcessModelElement> factory);
}
