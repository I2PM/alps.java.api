package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;

/**
 * Class that represents an FunctionSpecification
 */

public class FunctionSpecification extends BehaviorDescribingComponent implements IFunctionSpecification {
    protected String toolSpecificDefinition;
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "FunctionSpecification";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new FunctionSpecification();
    }

    public FunctionSpecification() {
    }

    /**
     * @param behavior
     * @param labelForID
     * @param toolSpecificDefinition
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public FunctionSpecification(ISubjectBehavior behavior, String labelForID, String toolSpecificDefinition,
                                 String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, comment, additionalLabel, additionalAttribute);
        setToolSpecificDefinition(toolSpecificDefinition);
    }

    public FunctionSpecification(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null);
        setToolSpecificDefinition(null);
    }

    public void setToolSpecificDefinition(String toolSpecificDefinition) {
        if (toolSpecificDefinition != null && toolSpecificDefinition.equals(this.toolSpecificDefinition)) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasToolSpecificDefinition, this.toolSpecificDefinition, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString));
        this.toolSpecificDefinition = (toolSpecificDefinition == null || toolSpecificDefinition.equals("")) ? null : toolSpecificDefinition;
        if (toolSpecificDefinition != null) {
            addTriple(new IncompleteTriple(OWLTags.stdHasToolSpecificDefinition, toolSpecificDefinition, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString));
        }
    }


    public String getToolSpecificDefinition() {
        return toolSpecificDefinition;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (predicate.contains(OWLTags.hasToolSpecificDefinition)) {
            setToolSpecificDefinition(objectContent);
            return true;
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

}