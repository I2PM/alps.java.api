package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.SubjectExecutionMappings;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.SubjectExecutionMapping;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

public class StaticSubjectExecutionMapping extends SubjectExecutionMapping implements IStaticSubjectExecutionMapping {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "StaticSubjectExecutionMapping";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new StaticSubjectExecutionMapping();
    }

    protected StaticSubjectExecutionMapping() {
    }

    public StaticSubjectExecutionMapping(IModelLayer layer, String labelForID, String executionMapping,
                                         String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, comment, executionMapping, additionalLabel, additionalAttribute);
    }

    public StaticSubjectExecutionMapping(IModelLayer layer) {
        super(layer, null, null, null, null, null);
    }
}
