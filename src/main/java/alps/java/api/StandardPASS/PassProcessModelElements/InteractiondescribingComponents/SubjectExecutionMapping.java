package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractionDescribingComponent;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;

public class SubjectExecutionMapping extends InteractionDescribingComponent implements ISubjectExecutionMapping {
    private String _executionMapping;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "SubjectExecutionMapping";
    private SubjectExecutionMappingTypes executionMappingType;

    // Getter-Methode für executionMappingType
    public SubjectExecutionMappingTypes getExecutionMappingType() {
        return executionMappingType;
    }

    // Setter-Methode für executionMappingType
    public void setExecutionMappingType(SubjectExecutionMappingTypes executionMappingType) {
        this.executionMappingType = executionMappingType;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new SubjectExecutionMapping();
    }

    protected SubjectExecutionMapping() {
    }

    /**
     * @param layer
     * @param labelForID
     * @param executionMapping
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public SubjectExecutionMapping(IModelLayer layer, String labelForID, String executionMapping, String comment,
                                   String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, comment, additionalLabel, additionalAttribute);
        setExecutionMappingDefinition(executionMapping);
        executionMappingType = SubjectExecutionMappingTypes.GeneralExecutionMapping;
    }

    public SubjectExecutionMapping(IModelLayer layer) {
        super(layer, null, null, null, null);
        setExecutionMappingDefinition(null);
    }

    public String getExecutionMappingDefinition() {
        return this._executionMapping;
    }

    public void setExecutionMappingDefinition(String mapping) {
        executionMappingType = SubjectExecutionMappingTypes.GeneralExecutionMapping;
    }

    @Override
    public SubjectExecutionMappingTypes getexecutionMappingType() {
        return null;
    }

    @Override
    public SubjectExecutionMappingTypes setexecutionMappingType() {
        return null;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (predicate.contains(OWLTags.hasExecutionMappingDefinition)) {
            setExecutionMappingDefinition(objectContent);
            return true;
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

}
