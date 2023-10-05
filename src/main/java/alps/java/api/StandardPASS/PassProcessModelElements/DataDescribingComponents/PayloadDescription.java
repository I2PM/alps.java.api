package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a payload description
 */
public class PayloadDescription extends DataDescribingComponent implements IPayloadDescription {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "PayloadDescription";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new PayloadDescription();
    }

    public PayloadDescription() {
    }

    /**
     * Constructor that creates a new fully specified instance of the payload description class
     *
     * @param model
     * @param labelForID
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public PayloadDescription(IPASSProcessModel model, String labelForID, String comment,
                              String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(model, labelForID, comment, additionalLabel, additionalAttribute);
    }

    /**
     * Constructor that creates a new fully specified instance of the payload description class
     *
     * @param model
     */
    public PayloadDescription(IPASSProcessModel model) {
        super(model, null, null, null, null);
    }

}
