package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a receive type
 */
public class ReceiveType extends BehaviorDescribingComponent implements IReceiveType {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ReceiveType";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ReceiveType();
    }

    public ReceiveType() {
    }

    /**
     * @param behavior
     * @param label
     */
    //TODO: Konstruktor überladen
    public ReceiveType(ISubjectBehavior behavior, String label) {
        super(behavior, label, null, null, null);
    }

    public ReceiveType(ISubjectBehavior behavior, String label, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, label, comment, additionalLabel, additionalAttribute);
    }

}
