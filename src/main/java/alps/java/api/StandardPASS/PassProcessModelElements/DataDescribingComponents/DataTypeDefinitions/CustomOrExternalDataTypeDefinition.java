package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataTypeDefinitions;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataTypeDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.IDataObjectDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents a custom or external data type definition
 */
public class CustomOrExternalDataTypeDefinition extends DataTypeDefinition implements ICustomOrExternalDataTypeDefinition {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "CustomOrExternalDataTypeDefinition";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new CustomOrExternalDataTypeDefinition();
    }

    public CustomOrExternalDataTypeDefinition() {
    }

    public CustomOrExternalDataTypeDefinition(IPASSProcessModel model, String labelForID, Set<IDataObjectDefinition> dataObjectDefiniton,
                                              String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(model, labelForID, dataObjectDefiniton, comment, additionalLabel, additionalAttribute);
    }

    public CustomOrExternalDataTypeDefinition(IPASSProcessModel model) {
        super(model, null, null, null, null, null);
    }
}
