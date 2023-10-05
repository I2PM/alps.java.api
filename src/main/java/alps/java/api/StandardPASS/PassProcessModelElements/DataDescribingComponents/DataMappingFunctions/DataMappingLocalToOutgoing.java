package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunctions;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a data mapping local to outgoing
 */
public class DataMappingLocalToOutgoing extends DataMappingFunction implements IDataMappingLocalToOutgoing {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "DataMappingLocalToOutgoing";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new DataMappingLocalToOutgoing();
    }

    public DataMappingLocalToOutgoing() {
    }

    public DataMappingLocalToOutgoing(IPASSProcessModel model, String labelForID, String dataMappingString, String feelExpression,
                                      String toolSpecificDefinition, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(model, labelForID, dataMappingString, feelExpression, toolSpecificDefinition, comment, additionalLabel, additionalAttribute);
    }

    public DataMappingLocalToOutgoing(IPASSProcessModel model) {
        super(model, null, null, null, null, null, null, null);
    }

}