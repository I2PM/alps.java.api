package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications;


import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a send function
 */
public class SendFunction extends CommunicationAct implements ISendFunction {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "SendFunction";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new SendFunction();
    }

    protected SendFunction() {
    }

    public SendFunction(ISubjectBehavior behavior, String labelForID, String toolSpecificDefinition,
                        String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, toolSpecificDefinition, comment, additionalLabel, additionalAttribute);


    }

    public SendFunction(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, null);


    }
}