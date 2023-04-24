package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataObjectDefinitions;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataObjectDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.IDataTypeDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a payload data object definition
 */
public class PayloadDataObjectDefinition extends DataObjectDefinition implements IPayloadDataObjectDefinition {
    private final String className = "PayloadDataObjectDefinition";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new PayloadDataObjectDefinition();
    }

    protected PayloadDataObjectDefinition() {
    }

    public PayloadDataObjectDefinition(IPASSProcessModel model, String labelForID,
                                       IDataTypeDefinition dataTypeDefintion, String comment, String additionalLabel,
                                       List<IIncompleteTriple> additionalAttribute) {
        super(model, labelForID, dataTypeDefintion, comment, additionalLabel, additionalAttribute);
    }

    public PayloadDataObjectDefinition(IPASSProcessModel model) {
        super(model, null, null, null, null, null);
    }

}