package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataTypeDefinitions;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.IDataObjectDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents a OWL data type definition
 */
public class OWLDataTypeDefinition extends CustomOrExternalDataTypeDefinition implements IOWLDataTypeDefinition {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "OWLDataTypeDefinition";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new OWLDataTypeDefinition();
    }

    protected OWLDataTypeDefinition() {
    }

    public OWLDataTypeDefinition(IPASSProcessModel model, String labelForID, Set<IDataObjectDefinition> dataObjectDefiniton,
                                 String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {

        super(model, labelForID, dataObjectDefiniton, comment, additionalLabel, additionalAttribute);
    }

    public OWLDataTypeDefinition(IPASSProcessModel model) {

        super(model, null, null, null, null, null);
    }

}
