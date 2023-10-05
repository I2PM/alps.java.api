package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;


import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IReceiveState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions.IReceiveTransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunctions.IDataMappingIncomingToLocal;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.IDataMappingFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
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
 * Class that represents a receive transition
 */
public class ReceiveTransition extends CommunicationTransition implements IReceiveTransition {
    protected final ICompatibilityDictionary<String, IDataMappingIncomingToLocal> dataMappingsIncomingToLocal = new CompatibilityDictionary<String, IDataMappingIncomingToLocal>();
    protected int priorityNumber = 1;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ReceiveTransition";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ReceiveTransition();
    }

    public ReceiveTransition() {
    }

    public ReceiveTransition(IState sourceState, IState targetState, String labelForID, ITransitionCondition transitionCondition,
                             ITransition.TransitionType transitionType, Set<IDataMappingIncomingToLocal> dataMappingIncomingToLocal,
                             int priorityNumber, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(sourceState, targetState, labelForID, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
        setDataMappingFunctionsIncomingToLocal(dataMappingIncomingToLocal);
        setPriorityNumber(priorityNumber);
    }

    public ReceiveTransition(IState sourceState, IState targetState) {
        super(sourceState, targetState, null, null, TransitionType.Standard, null, null, null);
        setDataMappingFunctionsIncomingToLocal(null);
        setPriorityNumber(0);
    }

    public ReceiveTransition(ISubjectBehavior behavior, String label,
                             IState sourceState, IState targetState, ITransitionCondition transitionCondition,
                             ITransition.TransitionType transitionType, Set<IDataMappingIncomingToLocal> dataMappingIncomingToLocal,
                             int priorityNumber, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, label, sourceState, targetState, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
        setDataMappingFunctionsIncomingToLocal(dataMappingIncomingToLocal);
        setPriorityNumber(priorityNumber);
    }

    public ReceiveTransition(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, TransitionType.Standard, null, null, null);
        setDataMappingFunctionsIncomingToLocal(null);
        setPriorityNumber(0);
    }

    public void setSourceState(IState state, int removeCascadeDepth) {
        if (state instanceof IReceiveState) {
            super.setSourceState(state, removeCascadeDepth);
        }
    }

    public void setSourceState(IState state) {
        if (state instanceof IReceiveState) {
            super.setSourceState(state, 0);
        }
    }

    public void addDataMappingFunction(IDataMappingIncomingToLocal dataMappingIncomingToLocal) {
        if (dataMappingIncomingToLocal == null) {
            return;
        }
        if (dataMappingsIncomingToLocal.getOrDefault(dataMappingIncomingToLocal.getModelComponentID(), dataMappingIncomingToLocal)!=null) {
            publishElementAdded(dataMappingIncomingToLocal);
            dataMappingIncomingToLocal.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasDataMappingFunction, dataMappingIncomingToLocal.getModelComponentID()));
        }
    }

    //TODO: out-Parameter
    public void removeDataMappingFunction(String mappingID, int removeCascadeDepth) {
        if (mappingID == null) return;
        IDataMappingIncomingToLocal mapping = dataMappingsIncomingToLocal.get(mappingID);
        if (mapping != null) {
            dataMappingsIncomingToLocal.remove(mappingID);
            mapping.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasDataMappingFunction, mappingID));
        }
    }

    public void removeDataMappingFunction(String mappingID) {
        if (mappingID == null) return;
        IDataMappingIncomingToLocal mapping = dataMappingsIncomingToLocal.get(mappingID);
        if (mapping!=null) {
            dataMappingsIncomingToLocal.remove(mappingID);
            mapping.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdHasDataMappingFunction, mappingID));
        }
    }

    public void setDataMappingFunctionsIncomingToLocal(Set<IDataMappingIncomingToLocal> dataMappingsIncomingToLocal, int removeCascadeDepth) {
        for (IDataMappingIncomingToLocal mapping : getDataMappingFunctions().values()) {
            removeDataMappingFunction(mapping.getModelComponentID(), removeCascadeDepth);
        }
        if (dataMappingsIncomingToLocal == null) return;
        for (IDataMappingIncomingToLocal mapping : dataMappingsIncomingToLocal) {
            addDataMappingFunction(mapping);
        }
    }

    public void setDataMappingFunctionsIncomingToLocal(Set<IDataMappingIncomingToLocal> dataMappingsIncomingToLocal) {
        for (IDataMappingIncomingToLocal mapping : getDataMappingFunctions().values()) {
            removeDataMappingFunction(mapping.getModelComponentID(), 0);
        }
        if (dataMappingsIncomingToLocal == null) return;
        for (IDataMappingIncomingToLocal mapping : dataMappingsIncomingToLocal) {
            addDataMappingFunction(mapping);
        }
    }

    public Map<String, IDataMappingIncomingToLocal> getDataMappingFunctions() {
        return new HashMap<String, IDataMappingIncomingToLocal>(dataMappingsIncomingToLocal);
    }


    public void setPriorityNumber(int positiveInteger) {
        if (positiveInteger == this.priorityNumber) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasPriorityNumber, Integer.toString(this.priorityNumber), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
        priorityNumber = (positiveInteger > 0) ? positiveInteger : 1;
        addTriple(new IncompleteTriple(OWLTags.stdHasPriorityNumber, Integer.toString(priorityNumber), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
    }


    public int getPriorityNumber() {
        return priorityNumber;
    }


    public IReceiveTransitionCondition getTransitionCondition() {
        return (IReceiveTransitionCondition) transitionCondition;
    }

    @Override
    public void setTransitionCondition(ITransitionCondition condition, int removeCascadeDepth) {
        if (condition instanceof IReceiveTransitionCondition receiveCondition)
            super.setTransitionCondition(receiveCondition);
        else super.setTransitionCondition(null);
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (predicate.contains(OWLTags.hasPriorityNumber)) {
            String prio = objectContent;
            setPriorityNumber(Integer.parseInt(prio));
            return true;
        } else if (element != null) {
            if (predicate.contains(OWLTags.hasDataMappingFunction) && element instanceof IDataMappingIncomingToLocal dataMapping) {
                addDataMappingFunction(dataMapping);
                return true;
            }

            if (predicate.contains(OWLTags.hasTransitionCondition) && element instanceof ITransitionCondition receiveCond) {
                setTransitionCondition(receiveCond);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IDataMappingIncomingToLocal mapping : getDataMappingFunctions().values())
            baseElements.add(mapping);
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IDataMappingIncomingToLocal mapping)
                removeDataMappingFunction(mapping.getModelComponentID(), removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IDataMappingIncomingToLocal mapping)
                removeDataMappingFunction(mapping.getModelComponentID(), 0);
        }
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (dataMappingsIncomingToLocal.containsKey(oldID)) {
            IDataMappingIncomingToLocal element = dataMappingsIncomingToLocal.get(oldID);
            dataMappingsIncomingToLocal.remove(oldID);
            dataMappingsIncomingToLocal.put(element.getModelComponentID(), element);
        }

        super.notifyModelComponentIDChanged(oldID, newID);
    }

}