package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.SubjectExecutionMappings;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.SubjectExecutionMapping;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

public class DynamicSubjectExecutionMapping extends SubjectExecutionMapping implements IDynamicSubjectExecutionMapping {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "DynamicSubjectExecutionMapping";

    @Override
    public String getClassName() {
        return className;
    }

    @Override

    public IParseablePASSProcessModelElement getParsedInstance() {
        return new DynamicSubjectExecutionMapping();
    }

    protected DynamicSubjectExecutionMapping() {
    }


    public DynamicSubjectExecutionMapping(IModelLayer layer, String labelForID, String executionMapping,
                                          String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, comment, executionMapping, additionalLabel, additionalAttribute);
    }

    public DynamicSubjectExecutionMapping(IModelLayer layer) {
        super(layer, null, null, null, null, null);
    }
}
