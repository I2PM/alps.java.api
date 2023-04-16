package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractionDescribingComponent;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;

public class SubjectExecutionMapping extends InteractionDescribingComponent implements ISubjectExecutionMapping {
    protected String executionMapping;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "SubjectExecutionMapping";

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
        setExecutionMapping(executionMapping);
    }

    public SubjectExecutionMapping(IModelLayer layer) {
        super(layer, null, null, null, null);
        setExecutionMapping(null);
    }

    public String getExecutionMapping() {
        return executionMapping;
    }

    public void setExecutionMapping(String mapping) {
        if (mapping != null && mapping.equals(this.executionMapping)) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasExecutionMappingDefinition, mapping, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString));
        executionMapping = (mapping == null || mapping.equals("")) ? null : mapping;
        if (mapping != null && !mapping.equals(""))
            addTriple(new IncompleteTriple(OWLTags.stdBelongsTo, mapping, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString));
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (predicate.contains(OWLTags.hasExecutionMappingDefinition)) {
            setExecutionMapping(objectContent);
            return true;
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

}
