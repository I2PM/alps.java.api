package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.PayloadDescriptions;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.PayloadDescription;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

public class PayloadPhysicalObjectDescription extends PayloadDescription implements IPayloadPhysicalObjectDescription {
    private final String className = "PayloadPhysicalObjectDescription";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new PayloadPhysicalObjectDescription();
    }

    public PayloadPhysicalObjectDescription() {
    }

    public PayloadPhysicalObjectDescription(IPASSProcessModel model, String labelForID,
                                            String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(model, labelForID, comment, additionalLabel, additionalAttribute);
    }

    public PayloadPhysicalObjectDescription(IPASSProcessModel model) {
        super(model, null, null, null, null);
    }
}
