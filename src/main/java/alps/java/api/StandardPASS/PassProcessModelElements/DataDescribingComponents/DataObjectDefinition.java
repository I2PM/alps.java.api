package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents;


import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents a data object definition
 */
public class DataObjectDefinition extends DataDescribingComponent implements IDataObjectDefinition {
    protected IDataTypeDefinition dataTypeDefintion;
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "DataObjectDefinition";


    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new DataObjectDefinition();
    }

    public DataObjectDefinition() {
    }

    public DataObjectDefinition(IPASSProcessModel model, String labelForID,
                                IDataTypeDefinition dataTypeDefintion, String comment,
                                String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(model, labelForID, comment, additionalLabel, additionalAttribute);
        setDataTypeDefinition(dataTypeDefintion);
    }

    public DataObjectDefinition(IPASSProcessModel model) {
        super(model, null, null, null, null);
        setDataTypeDefinition(null);
    }

    public void setDataTypeDefinition(IDataTypeDefinition dataTypeDefintion, int removeCascadeDepth) {
        IDataTypeDefinition oldDef = dataTypeDefintion;
        // Might set it to null
        this.dataTypeDefintion = dataTypeDefintion;

        if (oldDef != null) {
            if (oldDef.equals(dataTypeDefintion)) return;
            oldDef.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasDataType, oldDef.getUriModelComponentID()));
        }

        if (!(dataTypeDefintion == null)) {
            publishElementAdded(dataTypeDefintion);
            dataTypeDefintion.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasDataType, dataTypeDefintion.getUriModelComponentID()));
        }
    }

    public void setDataTypeDefinition(IDataTypeDefinition dataTypeDefintion) {
        IDataTypeDefinition oldDef = dataTypeDefintion;
        // Might set it to null
        this.dataTypeDefintion = dataTypeDefintion;

        if (oldDef != null) {
            if (oldDef.equals(dataTypeDefintion)) return;
            oldDef.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdHasDataType, oldDef.getUriModelComponentID()));
        }

        if (!(dataTypeDefintion == null)) {
            publishElementAdded(dataTypeDefintion);
            dataTypeDefintion.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasDataType, dataTypeDefintion.getUriModelComponentID()));
        }
    }

    public IDataTypeDefinition getDataTypeDefinition() {
        return dataTypeDefintion;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.hasDataType) && element instanceof IDataTypeDefinition definition) {
                setDataTypeDefinition(definition);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }


    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getDataTypeDefinition() != null)
            baseElements.add(getDataTypeDefinition());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IDataTypeDefinition definition && definition.equals(getDataTypeDefinition()))
                setDataTypeDefinition(null, removeCascadeDepth);
        }
    }

}
