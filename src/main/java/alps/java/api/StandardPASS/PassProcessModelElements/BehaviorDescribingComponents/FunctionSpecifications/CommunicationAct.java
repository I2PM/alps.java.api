package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a communication act
 */
public class CommunicationAct extends FunctionSpecification implements ICommunicationAct {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "CommunicationAct";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new CommunicationAct();
    }

    protected CommunicationAct() {
    }

    public CommunicationAct(ISubjectBehavior behavior, String labelForID, String toolSpecificDefinition,
                            String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, toolSpecificDefinition, comment, additionalLabel, additionalAttribute);
    }

    public CommunicationAct(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, null);
    }

}