package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataObjectDefinitions;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataObjectDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.IDataTypeDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a data object list definition
 */
public class DataObjectListDefinition extends DataObjectDefinition implements IDataObjectListDefinition
        {
/**
 * Name of the class, needed for parsing
 */
            private final String className = "DataObjectListDefintion";

@Override
public String getClassName()
        {
        return className;
        }
        @Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new DataObjectListDefinition();
        }

protected DataObjectListDefinition() { }
public DataObjectListDefinition(IPASSProcessModel model, String labelForID,
                                IDataTypeDefinition dataTypeDefintion, String comment,
                                String additionalLabel, List<IIncompleteTriple> additionalAttribute){
        super(model, labelForID, dataTypeDefintion, comment, additionalLabel, additionalAttribute);
        }
            public DataObjectListDefinition(IPASSProcessModel model){
                super(model, null, null, null, null, null);
            }
        }
