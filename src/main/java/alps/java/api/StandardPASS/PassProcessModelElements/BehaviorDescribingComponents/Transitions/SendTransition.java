package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;


import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.ISendState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions.ISendTransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunctions.IDataMappingLocalToOutgoing;
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
 * Class that represents a send transition
 */
public class SendTransition extends CommunicationTransition implements ISendTransition {
    protected final ICompatibilityDictionary<String, IDataMappingLocalToOutgoing> dataMappingsLocalToOutgoing = new CompatibilityDictionary<String, IDataMappingLocalToOutgoing>();

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "SendTransition";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new SendTransition();
    }

    public SendTransition() {
    }

    public SendTransition(IState sourceState, IState targetState, String labelForID, ITransitionCondition transitionCondition,
                          ITransition.TransitionType transitionType, Set<IDataMappingLocalToOutgoing> dataMappingLocalToOutgoing, String comment, String additionalLabel,
                          List<IIncompleteTriple> additionalAttribute) {
        super(sourceState, targetState, labelForID, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
        setDataMappingFunctionsLocalToOutgoing(dataMappingLocalToOutgoing);
    }

    public SendTransition(IState sourceState, IState targetState) {
        super(sourceState, targetState, null, null, TransitionType.Standard, null, null, null);
        setDataMappingFunctionsLocalToOutgoing(null);
    }

    public SendTransition(ISubjectBehavior behavior, String label,
                          IState sourceState, IState targetState, ITransitionCondition transitionCondition,
                          ITransition.TransitionType transitionType, Set<IDataMappingLocalToOutgoing> dataMappingLocalToOutgoing,
                          String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, label, sourceState, targetState, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
        setDataMappingFunctionsLocalToOutgoing(dataMappingLocalToOutgoing);
    }

    public SendTransition(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, TransitionType.Standard, null, null, null);
        setDataMappingFunctionsLocalToOutgoing(null);
    }


    public void addDataMappingFunction(IDataMappingLocalToOutgoing dataMappingLocalToOutgoing) {
        if (dataMappingLocalToOutgoing == null) {
            return;
        }
        if (dataMappingsLocalToOutgoing.getOrDefault(dataMappingLocalToOutgoing.getModelComponentID(), dataMappingLocalToOutgoing) != null) {
            publishElementAdded(dataMappingLocalToOutgoing);
            dataMappingLocalToOutgoing.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasDataMappingFunction, dataMappingLocalToOutgoing.getModelComponentID()));
        }
    }

    public void removeDataMappingFunction(String mappingID, int removeCascadeDepth) {
        if (mappingID == null) return;
        IDataMappingLocalToOutgoing mapping =dataMappingsLocalToOutgoing.get(mappingID);
        if (mapping != null) {
            dataMappingsLocalToOutgoing.remove(mappingID);
            mapping.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasDataMappingFunction, mappingID));
        }
    }

    public void removeDataMappingFunction(String mappingID) {
        if (mappingID == null) return;
        IDataMappingLocalToOutgoing mapping =dataMappingsLocalToOutgoing.get(mappingID);
        if (mapping != null) {
            dataMappingsLocalToOutgoing.remove(mappingID);
            mapping.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdHasDataMappingFunction, mappingID));
        }
    }

    public void setDataMappingFunctionsLocalToOutgoing(Set<IDataMappingLocalToOutgoing> dataMappingsLocalToOutgoing, int removeCascadeDepth) {
        for (IDataMappingLocalToOutgoing mapping : getDataMappingFunctions().values()) {
            removeDataMappingFunction(mapping.getModelComponentID(), removeCascadeDepth);
        }
        if (dataMappingsLocalToOutgoing == null) return;
        for (IDataMappingLocalToOutgoing mapping : dataMappingsLocalToOutgoing) {
            addDataMappingFunction(mapping);
        }
    }

    public void setDataMappingFunctionsLocalToOutgoing(Set<IDataMappingLocalToOutgoing> dataMappingsLocalToOutgoing) {
        for (IDataMappingLocalToOutgoing mapping : getDataMappingFunctions().values()) {
            removeDataMappingFunction(mapping.getModelComponentID(), 0);
        }
        if (dataMappingsLocalToOutgoing == null) return;
        for (IDataMappingLocalToOutgoing mapping : dataMappingsLocalToOutgoing) {
            addDataMappingFunction(mapping);
        }
    }

    public Map<String, IDataMappingLocalToOutgoing> getDataMappingFunctions() {
        return new HashMap<String, IDataMappingLocalToOutgoing>(dataMappingsLocalToOutgoing);
    }

    public ISendTransitionCondition getTransitionCondition() {
        return (ISendTransitionCondition) transitionCondition;
    }

    public void setTransitionCondition(ITransitionCondition condition, int removeCascadeDepth) {
        if (condition instanceof ISendTransitionCondition receiveCondition)
            super.setTransitionCondition(receiveCondition, removeCascadeDepth);
        else super.setTransitionCondition(null, removeCascadeDepth);
    }

    public void setTransitionCondition(ITransitionCondition condition) {
        if (condition instanceof ISendTransitionCondition receiveCondition)
            super.setTransitionCondition(receiveCondition, 0);
        else super.setTransitionCondition(null, 0);
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.hasDataMappingFunction) && element instanceof IDataMappingLocalToOutgoing dataMapping) {
                addDataMappingFunction(dataMapping);
                return true;
            }

            if (predicate.contains(OWLTags.hasTransitionCondition) && element instanceof ITransitionCondition sendCondition) {
                setTransitionCondition(sendCondition);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public void setSourceState(IState state, int removeCascadeDepth) {
        if (state instanceof ISendState) {
            super.setSourceState(state, removeCascadeDepth);
        }
    }

    @Override
    public void setSourceState(IState state) {
        if (state instanceof ISendState) {
            super.setSourceState(state, 0);
        }
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IDataMappingLocalToOutgoing mapping : getDataMappingFunctions().values())
            baseElements.add(mapping);
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IDataMappingLocalToOutgoing mapping)
                removeDataMappingFunction(mapping.getModelComponentID(), removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IDataMappingLocalToOutgoing mapping)
                removeDataMappingFunction(mapping.getModelComponentID(), 0);
        }
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (dataMappingsLocalToOutgoing.containsKey(oldID)) {
            IDataMappingLocalToOutgoing element = dataMappingsLocalToOutgoing.get(oldID);
            dataMappingsLocalToOutgoing.remove(oldID);
            dataMappingsLocalToOutgoing.put(element.getModelComponentID(), element);
        }

        super.notifyModelComponentIDChanged(oldID, newID);
    }
}