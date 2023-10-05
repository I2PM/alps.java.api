package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataTypeDefinitions;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataTypeDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.IDataObjectDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents a model built in data type
 */
public class ModelBuiltInDataTypes extends DataTypeDefinition implements IModelBuiltInDataTypes {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ModelBuiltInDataTypes";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ModelBuiltInDataTypes();
    }

    public ModelBuiltInDataTypes() {
    }

    public ModelBuiltInDataTypes(IPASSProcessModel model, String labelForID, Set<IDataObjectDefinition> dataObjectDefiniton,
                                 String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(model, labelForID, dataObjectDefiniton, comment, additionalLabel, additionalAttribute);
    }

    public ModelBuiltInDataTypes(IPASSProcessModel model) {
        super(model, null, null, null, null, null);
    }
}