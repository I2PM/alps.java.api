package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunctions;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a data mapping incoming to local
 */
public class DataMappingIncomingToLocal extends DataMappingFunction implements IDataMappingIncomingToLocal {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "DataMappingIncomingToLocal";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new DataMappingIncomingToLocal();
    }

    protected DataMappingIncomingToLocal() {
    }

    public DataMappingIncomingToLocal(IPASSProcessModel model, String labelForID, String dataMappingString, String feelExpression,
                                      String toolSpecificDefinition, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(model, labelForID, dataMappingString, feelExpression, toolSpecificDefinition, comment, additionalLabel, additionalAttribute);
    }

    public DataMappingIncomingToLocal(IPASSProcessModel model) {
        super(model, null, null, null, null, null, null, null);
    }

}
