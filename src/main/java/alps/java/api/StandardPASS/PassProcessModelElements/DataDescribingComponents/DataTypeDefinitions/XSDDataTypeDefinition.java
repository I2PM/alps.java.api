package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataTypeDefinitions;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.IDataObjectDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents a XSD data type definition
 */
public class XSDDataTypeDefinition extends CustomOrExternalDataTypeDefinition implements IXSDDataTypeDefinition {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "XSD-DataTypeDefinition";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new XSDDataTypeDefinition();
    }

    public XSDDataTypeDefinition() {
    }

    public XSDDataTypeDefinition(IPASSProcessModel model, String labelForID, Set<IDataObjectDefinition> dataObjectDefiniton,
                                String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(model, labelForID, dataObjectDefiniton, comment, additionalLabel, additionalAttribute);
    }

    public XSDDataTypeDefinition(IPASSProcessModel model) {
        super(model, null, null, null, null, null);
    }

}
