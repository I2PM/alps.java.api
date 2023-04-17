package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents an DoFunction
 */
public class DoFunction extends FunctionSpecification implements IDoFunction {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "DoFunction";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new DoFunction();
    }

    protected DoFunction() {
    }

    public DoFunction(ISubjectBehavior behavior, String labelForID, String toolSpecificDefinition,
                      String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, toolSpecificDefinition, comment, additionalLabel, additionalAttribute);
    }

    public DoFunction(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, null);
    }

}