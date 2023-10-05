package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents;


import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.CompatibilityDictionary;
import alps.java.api.util.ICompatibilityDictionary;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that represents a Data Type Definition
 */
public class DataTypeDefinition extends DataDescribingComponent implements IDataTypeDefinition {
    protected ICompatibilityDictionary<String, IDataObjectDefinition> dataObjectDefinitons = new CompatibilityDictionary<String, IDataObjectDefinition>();

    /**
     * Name of the class, needed for parsing
     */
    private static String className = "DataTypeDefinition";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new DataTypeDefinition();
    }

    public DataTypeDefinition() {
    }

    public DataTypeDefinition(IPASSProcessModel model, String labelForID,
                              Set<IDataObjectDefinition> dataObjectDefiniton, String comment,
                              String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(model, labelForID, comment, additionalLabel, additionalAttribute);
        setContainsDataObjectDefintions(dataObjectDefiniton);
    }


    public void setContainsDataObjectDefintions(Set<IDataObjectDefinition> dataObjectDefinitons, int removeCascadeDepth) {
        for (IDataObjectDefinition dataObjectDefinition : getDataObjectDefinitons().values()) {
            removeDataObjectDefiniton(dataObjectDefinition.getModelComponentID(), removeCascadeDepth);
        }
        if (dataObjectDefinitons == null) return;
        for (IDataObjectDefinition dataObjectDefinition : dataObjectDefinitons) {
            addContainsDataObjectDefintion(dataObjectDefinition);
        }
    }

    public void setContainsDataObjectDefintions(Set<IDataObjectDefinition> dataObjectDefinitons) {
        for (IDataObjectDefinition dataObjectDefinition : getDataObjectDefinitons().values()) {
            removeDataObjectDefiniton(dataObjectDefinition.getModelComponentID(), 0);
        }
        if (dataObjectDefinitons == null) return;
        for (IDataObjectDefinition dataObjectDefinition : dataObjectDefinitons) {
            addContainsDataObjectDefintion(dataObjectDefinition);
        }
    }

    public void addContainsDataObjectDefintion(IDataObjectDefinition dataObjectDefiniton) {
        if (dataObjectDefiniton == null) {
            return;
        }
        if (dataObjectDefinitons.getOrDefault(dataObjectDefiniton.getModelComponentID(), dataObjectDefiniton) != null) {
            publishElementAdded(dataObjectDefiniton);
            dataObjectDefiniton.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdContains, dataObjectDefiniton.getUriModelComponentID()));
        }
    }

    public Map<String, IDataObjectDefinition> getDataObjectDefinitons() {
        return new HashMap<String, IDataObjectDefinition>(dataObjectDefinitons);
    }


    public void removeDataObjectDefiniton(String modelComponentID, int removeCascadeDepth) {
        if (modelComponentID == null) return;
        IDataObjectDefinition definition = dataObjectDefinitons.get(modelComponentID);
        if (definition != null) {
            dataObjectDefinitons.remove(modelComponentID);
            definition.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, definition.getUriModelComponentID()));
        }
    }

    public void removeDataObjectDefiniton(String modelComponentID) {
        if (modelComponentID == null) return;
        IDataObjectDefinition definition = dataObjectDefinitons.get(modelComponentID);
        if (definition != null) {
            dataObjectDefinitons.remove(modelComponentID);
            definition.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, definition.getUriModelComponentID()));
        }
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.ccontains) && element instanceof IDataObjectDefinition definition) {
                addContainsDataObjectDefintion(definition);
                return true;
            }

        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IDataObjectDefinition definition : getDataObjectDefinitons().values())
            baseElements.add(definition);
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IDataObjectDefinition definition)
                removeDataObjectDefiniton(definition.getModelComponentID(), removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IDataObjectDefinition definition)
                removeDataObjectDefiniton(definition.getModelComponentID(), 0);
        }
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (dataObjectDefinitons.containsKey(oldID)) {
            IDataObjectDefinition element = dataObjectDefinitons.get(oldID);
            dataObjectDefinitons.remove(oldID);
            dataObjectDefinitons.put(element.getModelComponentID(), element);
        }
        super.notifyModelComponentIDChanged(oldID, newID);
    }

}