package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications;

import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a receive function class
 */
public class ReceiveFunction extends CommunicationAct implements IReceiveFunction {
    /**
     * Name of the class, needed for parsin
     */
    private final String className = "ReceiveFunction";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ReceiveFunction();
    }

    protected ReceiveFunction() {
    }

    public ReceiveFunction(ISubjectBehavior behavior, String labelForID, String toolSpecificDefinition,
                           String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, toolSpecificDefinition, comment, additionalLabel, additionalAttribute);
    }
    public ReceiveFunction(ISubjectBehavior behavior) {
        super(behavior);
    }
}