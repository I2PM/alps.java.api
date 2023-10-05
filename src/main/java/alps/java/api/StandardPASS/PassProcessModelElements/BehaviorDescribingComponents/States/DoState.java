package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications.IDoFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IDoState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.StandardPASSState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions.IReceiveTransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions.ISendTransition;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunctions.IDataMappingIncomingToLocal;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunctions.IDataMappingLocalToOutgoing;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.IDataMappingFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Class that represents a DoState
 */
public class DoState extends StandardPASSState implements IDoState {
    protected final ICompatibilityDictionary<String, IDataMappingFunction> generalDataMappingFunctions = new CompatibilityDictionary<String, IDataMappingFunction>();
    protected final ICompatibilityDictionary<String, IDataMappingIncomingToLocal> dataMappingIncomingToLocalDict = new CompatibilityDictionary<String, IDataMappingIncomingToLocal>();
    protected final ICompatibilityDictionary<String, IDataMappingLocalToOutgoing> dataMappingLocalToOutgoingDict = new CompatibilityDictionary<String, IDataMappingLocalToOutgoing>();

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "DoState";
    protected String exportTag = OWLTags.std;
    protected String exportClassname = className;


    protected ISiSiTimeDistribution sisiExecutionDuration;
    protected double sisiCostPerExecution;
    //public ISiSiTimeDistribution sisiExecutionDuration { get; set; }
//public double sisiCostPerExecution { get; set; } = 0;
    private double _sisiEndStayChance = 0;
    Logger Log = Logger.getLogger(DoState.class.getName());

    public ISiSiTimeDistribution getSisiExecutionDuration() {
        return this.sisiExecutionDuration;
    }

    public void setSisiExecutionDuration(ISiSiTimeDistribution sisiExecutionDuration) {
        this.sisiExecutionDuration = sisiExecutionDuration;
    }


    public double getSisiCostPerExecution() {
        return this.sisiCostPerExecution;
    }

    public void setSisiCostPerExecution(double sisiCostPerExecution) {
        this.sisiCostPerExecution = sisiCostPerExecution;
    }

    public double getSisiEndStayChance() {
        return this._sisiEndStayChance;
    }

    public void setSisiEndStayChance(double value) {
        // Add validation logic
        if (value >= 0.0 && value <= 1.0) {
            _sisiEndStayChance = value;
        } else {
            if (value < 0) {
                _sisiEndStayChance = 0;
                Log.warning("Value for sisiEndStayChance is smaller than 0. Setting it to 0.");
            } else if (value > 1) {
                _sisiEndStayChance = 1;
                Log.warning("Value for sisiEndStayChance is larger than 1. Setting it to 1.");
            }
        }

    }

    protected IHasSiSiDistribution.SimpleSimTimeCategory sisiVSMTimeCategory;

    public IHasSiSiDistribution.SimpleSimTimeCategory getSisiVSMTimeCategory() {
        return this.sisiVSMTimeCategory;
    }

    public void setSisiVSMTimeCategory(IHasSiSiDistribution.SimpleSimTimeCategory simpleSimTimeCategory) {
        this.sisiVSMTimeCategory = simpleSimTimeCategory;
    }

    @Override
    public String getClassName() {

        return exportClassname;
    }

    @Override
    protected String getExportTag() {
        return exportTag;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new DoState();
    }

    public DoState() {
    }

    public DoState(ISubjectBehavior behavior, String labelForID, IGuardBehavior guardBehavior,
                   IDoFunction doFunction, Set<ITransition> incomingTransitions, Set<ITransition> outgoingTransitions,
                   Set<IDataMappingIncomingToLocal> dataMappingIncomingToLocal, Set<IDataMappingLocalToOutgoing> dataMappingLocalToOutgoing,
                   String comment, String additionalLabel, List<IIncompleteTriple> additionalAttributes) {

        super(behavior, labelForID, guardBehavior, doFunction, incomingTransitions, null, comment, additionalLabel, additionalAttributes);
        // Set those attributes locally and pass null to base (so no wrong attributes will be set)
        setDataMappingFunctionsIncomingToLocal(dataMappingIncomingToLocal);
        setDataMappingFunctionsLocalToOutgoing(dataMappingLocalToOutgoing);
        setOutgoingTransitions(outgoingTransitions);
    }

    public DoState(ISubjectBehavior behavior) {

        super(behavior, null, null, null, null, null, null, null, null);
        // Set those attributes locally and pass null to base (so no wrong attributes will be set)
        setDataMappingFunctionsIncomingToLocal(null);
        setDataMappingFunctionsLocalToOutgoing(null);
        setOutgoingTransitions(null);
    }

// #################### DataMappingFunctions ####################

    public void setDataMappingFunctionsIncomingToLocal(Set<IDataMappingIncomingToLocal> dataMappingIncomingToLocal, int removeCascadeDepth) {
        for (IDataMappingIncomingToLocal mapping : getDataMappingFunctionsIncomingToLocal().values()) {
            removeDataMappingFunctionIncomingToLocal(mapping.getModelComponentID(), removeCascadeDepth);
        }
        if (dataMappingIncomingToLocal == null) return;
        for (IDataMappingIncomingToLocal mapping : dataMappingIncomingToLocal) {
            addDataMappingFunctionIncomingToLocal(mapping);
        }
    }

    public void setDataMappingFunctionsIncomingToLocal(Set<IDataMappingIncomingToLocal> dataMappingIncomingToLocal) {
        for (IDataMappingIncomingToLocal mapping : getDataMappingFunctionsIncomingToLocal().values()) {
            removeDataMappingFunctionIncomingToLocal(mapping.getModelComponentID(), 0);
        }
        if (dataMappingIncomingToLocal == null) return;
        for (IDataMappingIncomingToLocal mapping : dataMappingIncomingToLocal) {
            addDataMappingFunctionIncomingToLocal(mapping);
        }
    }

    public void addDataMappingFunctionIncomingToLocal(IDataMappingIncomingToLocal dataMappingIncomingToLocal) {
        if (dataMappingIncomingToLocal == null) {
            return;
        }
        if (dataMappingIncomingToLocalDict.tryAdd(dataMappingIncomingToLocal.getModelComponentID(), dataMappingIncomingToLocal)) {
            publishElementAdded(dataMappingIncomingToLocal);
            dataMappingIncomingToLocal.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasDataMappingFunction, dataMappingIncomingToLocal.getUriModelComponentID()));
        }
    }

    public Map<String, IDataMappingIncomingToLocal> getDataMappingFunctionsIncomingToLocal() {
        return new HashMap<String, IDataMappingIncomingToLocal>(dataMappingIncomingToLocalDict);
    }

    public void removeDataMappingFunctionIncomingToLocal(String id, int removeCascadeDepth) {
        if (id == null) return;
        IDataMappingIncomingToLocal mapping = dataMappingIncomingToLocalDict.get(id);
        if (mapping != null) {
            dataMappingIncomingToLocalDict.remove(id);
            mapping.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasDataMappingFunction, mapping.getUriModelComponentID()));
        }
    }

    public void removeDataMappingFunctionIncomingToLocal(String id) {
        removeDataMappingFunctionIncomingToLocal(id, 0);
    }

    public void setDataMappingFunctionsLocalToOutgoing(Set<IDataMappingLocalToOutgoing> dataMappingLocalToOutgoing, int removeCascadeDepth) {
        for (IDataMappingLocalToOutgoing mapping : getDataMappingFunctionsLocalToOutgoing().values()) {
            removeDataMappingFunctionLocalToOutgoing(mapping.getModelComponentID(), removeCascadeDepth);
        }
        if (dataMappingLocalToOutgoing == null) return;
        for (IDataMappingLocalToOutgoing mapping : dataMappingLocalToOutgoing) {
            addDataMappingFunctionLocalToOutgoing(mapping);
        }
    }

    public void setDataMappingFunctionsLocalToOutgoing(Set<IDataMappingLocalToOutgoing> dataMappingLocalToOutgoing) {
        setDataMappingFunctionsLocalToOutgoing(dataMappingLocalToOutgoing, 0);
    }

    public void addDataMappingFunctionLocalToOutgoing(IDataMappingLocalToOutgoing dataMappingLocalToOutgoing) {
        if (dataMappingLocalToOutgoing == null) {
            return;
        }
        if (dataMappingLocalToOutgoingDict.tryAdd(dataMappingLocalToOutgoing.getModelComponentID(), dataMappingLocalToOutgoing)) {
            publishElementAdded(dataMappingLocalToOutgoing);
            dataMappingLocalToOutgoing.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasDataMappingFunction, dataMappingLocalToOutgoing.getUriModelComponentID()));
        }
    }


    public Map<String, IDataMappingLocalToOutgoing> getDataMappingFunctionsLocalToOutgoing() {
        return new HashMap<>(dataMappingLocalToOutgoingDict);
    }

    public void removeDataMappingFunctionLocalToOutgoing(String id, int removeCascadeDepth) {
        if (id == null) return;
        IDataMappingLocalToOutgoing mapping = dataMappingLocalToOutgoingDict.get(id);
        if (mapping != null) {
            dataMappingLocalToOutgoingDict.remove(id);
            mapping.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasDataMappingFunction, mapping.getUriModelComponentID()));
        }
    }

    public void removeDataMappingFunctionLocalToOutgoing(String id) {
        removeDataMappingFunctionLocalToOutgoing(id, 0);
    }

    public void addDataMappingFunction(IDataMappingFunction dataMappingFunction) {
        if (dataMappingFunction == null) {
            return;
        }
        if (generalDataMappingFunctions.tryAdd(dataMappingFunction.getModelComponentID(), dataMappingFunction)) {
            publishElementAdded(dataMappingFunction);
            dataMappingFunction.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasDataMappingFunction, dataMappingFunction.getUriModelComponentID()));
        }
    }

    public Map<String, IDataMappingFunction> getDataMappingFunctions() {
        return new HashMap<String, IDataMappingFunction>(generalDataMappingFunctions);
    }


    public void removeDataMappingFunction(String id, int removeCascadeDepth) {
        if (id == null) return;
        IDataMappingFunction mapping = generalDataMappingFunctions.get(id);
        if (mapping != null) {
            dataMappingLocalToOutgoingDict.remove(id);
            mapping.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasDataMappingFunction, mapping.getUriModelComponentID()));
        }
    }

    public void removeDataMappingFunction(String id) {
        removeDataMappingFunction(id, 0);
    }

// ########################################

    @Override
    public void addOutgoingTransition(ITransition transition) {
        if (!(transition instanceof ISendTransition || transition instanceof IReceiveTransition))
            super.addOutgoingTransition(transition);
    }


    public void setFunctionSpecification(IFunctionSpecification specification, int removeCascadeDepth) {
        if (specification instanceof IDoFunction) {
            super.setFunctionSpecification(specification, removeCascadeDepth);
        } else {
            super.setFunctionSpecification(null, removeCascadeDepth);
        }
    }

    public void setFunctionSpecification(IFunctionSpecification specification) {
        setFunctionSpecification(specification, 0);
    }

    public IDoFunction getFunctionSpecification() {
        return (IDoFunction) super.getFunctionSpecification();
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.hasDataMappingFunction) && element instanceof IDataMappingIncomingToLocal
                    incomingMapping) {
                addDataMappingFunctionIncomingToLocal(incomingMapping);
                return true;
            } else if (predicate.contains(OWLTags.hasDataMappingFunction) && element instanceof IDataMappingLocalToOutgoing
                    outgoingMapping) {
                addDataMappingFunctionLocalToOutgoing(outgoingMapping);
                return true;

            } else if (predicate.contains(OWLTags.hasDataMappingFunction) && element instanceof IDataMappingFunction functionMapping) {
                addDataMappingFunction(functionMapping);
                return true;
            } else if (predicate.contains(OWLTags.hasFunctionSpecification) && element instanceof IDoFunction function) {
                setFunctionSpecification(function);
                return true;
            }

            if (predicate.contains(OWLTags.abstrHasSimpleSimDurationMeanValue)) {

            }
        } else if (predicate.contains(OWLTags.type)) {
            if (objectContent.contains("AbstractDoState")) {
                setIsStateType(StateType.Abstract);
                return true;
            } else if (objectContent.contains("FinalizedDoState")) {
                setIsStateType(StateType.Finalized);
                return true;
            }
        } else if (predicate.contains(OWLTags.abstrHasSimpleSimDurationMeanValue)) {
            if (this.sisiExecutionDuration == null) {
                this.sisiExecutionDuration = new SiSiTimeDistribution();
            }
            this.sisiExecutionDuration.setMeanValue(SiSiTimeDistribution.convertXSDDurationStringToFractionsOfDay(objectContent));
            return true;
        } else if (predicate.contains(OWLTags.abstrHasSimpleSimDurationDeviation)) {
            if (this.sisiExecutionDuration == null) {
                this.sisiExecutionDuration = new SiSiTimeDistribution();
            }
            this.sisiExecutionDuration.setStandardDeviation(SiSiTimeDistribution.convertXSDDurationStringToFractionsOfDay(objectContent));
            return true;
        } else if (predicate.contains(OWLTags.abstrHasSimpleSimDurationMinValue)) {
            if (this.sisiExecutionDuration == null) {
                this.sisiExecutionDuration = new SiSiTimeDistribution();
            }
            this.sisiExecutionDuration.setMinValue(SiSiTimeDistribution.convertXSDDurationStringToFractionsOfDay(objectContent));
            return true;
        } else if (predicate.contains(OWLTags.abstrHasSimpleSimDurationMaxValue)) {
            if (this.sisiExecutionDuration == null) {
                this.sisiExecutionDuration = new SiSiTimeDistribution();
            }
            this.sisiExecutionDuration.setMaxValue(SiSiTimeDistribution.convertXSDDurationStringToFractionsOfDay(objectContent));
            return true;
        } else if (predicate.contains(OWLTags.abstrHasSimpleSimCostPerExecution)) {
            try {
                this.sisiCostPerExecution = Double.parseDouble(objectContent);
            } catch (Exception e) {
                Log.warning("could not parse the value " + objectContent + " as valid double");
            }
            return true;
        } else if (predicate.contains(OWLTags.abstrHasSimpleSimEndStayChance)) {
            try {
                _sisiEndStayChance = Double.parseDouble(objectContent);
            } catch (Exception e) {
                Log.warning("could not parse the value " + objectContent + " as valid double");
            }
            return true;
        } else if (predicate.contains(OWLTags.abstrHasSimpleSimVSMTimeCategory)) {
            try {
                this.sisiVSMTimeCategory = parseSimpleSimTimeCategory(objectContent);
            } catch (Exception e) {
                Log.warning("could not parse the value " + objectContent + " as valid Time Category");
            }
            return true;
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public void setIsStateType(StateType stateType) {
        if (!stateTypes.contains(stateType)) {
            switch (stateType) {
                case Abstract:
                    removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                    exportTag = OWLTags.abstr;
                    exportClassname = "Abstract" + className;
                    addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                    if (isStateType(StateType.Finalized))
                        removeStateType(StateType.Finalized);
                    break;
                case Finalized:
                    removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                    exportTag = OWLTags.abstr;
                    exportClassname = "Finalized" + className;
                    addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                    if (isStateType(StateType.Abstract))
                        removeStateType(StateType.Abstract);
                    break;
                default:
                    super.setIsStateType(stateType);
                    break;
            }
        }
    }

    @Override
    public void removeStateType(StateType stateType) {
        if (stateTypes.contains(stateType)) {
            switch (stateType) {
                case Abstract:
                    removeTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "Abstract" + getExportTag() + getClassName()));
                    stateTypes.remove(stateType);
                    exportTag = OWLTags.std;
                    exportClassname = className;
                    addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                    break;
                case Finalized:
                    removeTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "Finalized" + getExportTag() + getClassName()));
                    stateTypes.remove(stateType);
                    exportTag = OWLTags.std;
                    exportClassname = className;
                    addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                    break;
                default:
                    super.removeStateType(stateType);
                    break;
            }
        }
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IDataMappingIncomingToLocal component : getDataMappingFunctionsIncomingToLocal().values())
            baseElements.add(component);
        for (IDataMappingLocalToOutgoing component : getDataMappingFunctionsLocalToOutgoing().values())
            baseElements.add(component);
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IDataMappingIncomingToLocal mappingIn)
                removeDataMappingFunctionIncomingToLocal(mappingIn.getModelComponentID(), removeCascadeDepth);
            if (update instanceof IDataMappingLocalToOutgoing mappingOut)
                removeDataMappingFunctionLocalToOutgoing(mappingOut.getModelComponentID(), removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        updateRemoved(update, caller, 0);
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (dataMappingIncomingToLocalDict.containsKey(oldID)) {
            IDataMappingIncomingToLocal element = dataMappingIncomingToLocalDict.get(oldID);
            dataMappingIncomingToLocalDict.remove(oldID);
            dataMappingIncomingToLocalDict.put(element.getModelComponentID(), element);
        }

        if (dataMappingLocalToOutgoingDict.containsKey(oldID)) {
            IDataMappingLocalToOutgoing element = dataMappingLocalToOutgoingDict.get(oldID);
            dataMappingLocalToOutgoingDict.remove(oldID);
            dataMappingLocalToOutgoingDict.put(element.getModelComponentID(), element);
        }

        super.notifyModelComponentIDChanged(oldID, newID);
    }

    public void setEndState(boolean isEndState) {
        if (isEndState) {
            if (!this.isStateType(StateType.EndState)) {
                this.setIsStateType(StateType.EndState);
            }
        } else {
            if (this.isStateType(StateType.EndState)) {
                this.removeStateType(StateType.EndState);
            }
        }
    }

    public boolean isEndState() {
        return this.isStateType(StateType.EndState);
    }

    /**
     * The method will try to pars a given value and return on the the according enum types
     * If pasring is not possible the default value will be given.
     *
     * @param value
     * @return
     */
    private static IHasSiSiDistribution.SimpleSimTimeCategory parseSimpleSimTimeCategory(String value) {
        if (value == null || value.isEmpty()) {
            value = "nothing correct";
        }

        for (IHasSiSiDistribution.SimpleSimTimeCategory type : IHasSiSiDistribution.SimpleSimTimeCategory.values()) {
            if (value.toLowerCase().contains(type.toString().toLowerCase())) {
                return type;
            }
        }

        return IHasSiSiDistribution.SimpleSimTimeCategory.Standard;

    }
}
