package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractionDescribingComponent;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents an InputConstraintHandlingStrategy
 */
public class InputPoolConstraintHandlingStrategy extends InteractionDescribingComponent implements IInputPoolConstraintHandlingStrategy {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "InputPoolContstraintHandlingStrategy";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new InputPoolConstraintHandlingStrategy();
    }

    protected InputPoolConstraintHandlingStrategy() {
    }

    /**
     * @param layer
     * @param labelForID          a string describing this element which is used to generate the unique model component id
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public InputPoolConstraintHandlingStrategy(IModelLayer layer, String labelForID, String comment,
                                               String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, comment, additionalLabel, additionalAttribute);
    }

    public InputPoolConstraintHandlingStrategy(IModelLayer layer) {
        super(layer, null, null, null, null);
    }

}