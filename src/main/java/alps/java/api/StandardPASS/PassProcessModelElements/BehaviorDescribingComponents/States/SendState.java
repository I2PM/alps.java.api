package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications.ISendFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions.IDoTransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions.IReceiveTransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions.ISendTransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions.ISendingFailedTransition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.ISiSiTimeDistribution;
import alps.java.api.util.IncompleteTriple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 */
public class SendState extends StandardPASSState implements ISendState {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "SendState";
    protected String exportTag = OWLTags.std;
    protected String exportClassname = className;
    protected ISiSiTimeDistribution sisiExecutionDuration;
    protected double sisiCostPerExecution;
    private static final Logger logger = Logger.getLogger("SendState");

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
        return new SendState();
    }

    protected SendState() {
    }

    public SendState(ISubjectBehavior behavior, String labelForID, IGuardBehavior guardBehavior,
                     ISendFunction functionSpecification,
                     Set<ITransition> incomingTransition, ISendTransition sendTransition,
                     Set<ISendingFailedTransition> sendingFailedTransitions, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, guardBehavior, functionSpecification, incomingTransition, null, comment, additionalLabel, additionalAttribute);

        // Not passing these to base, rather pass null to avoid setting false values
        setSendTransition(sendTransition);
        setSendingFailedTransitions(sendingFailedTransitions);
    }

    public SendState(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, null, null, null, null);

        // Not passing these to base, rather pass null to avoid setting false values
        setSendTransition(null);
        setSendingFailedTransitions(null);
    }

    public ISendFunction getFunctionSpecification() {
        return (ISendFunction) super.getFunctionSpecification();
    }

    @Override
    public void setFunctionSpecification(IFunctionSpecification specification, int removeCascadingDepth) {
        // Only set if it is SendFunction
        if (specification instanceof ISendFunction) {
            super.setFunctionSpecification(specification, removeCascadingDepth);
        } else {
            super.setFunctionSpecification(null);
        }
    }

    @Override
    public void setFunctionSpecification(IFunctionSpecification specification) {
        // Only set if it is SendFunction
        if (specification instanceof ISendFunction) {
            super.setFunctionSpecification(specification, 0);
        } else {
            super.setFunctionSpecification(null);
        }
    }

    @Override
    public void addOutgoingTransition(ITransition transition) {
        if (transition == null) return;
        if (transition instanceof ISendTransition sendTrans) {
            setSendTransition(sendTrans);
        } else if (!(transition instanceof IDoTransition || transition instanceof IReceiveTransition)) {
            // if no do or receive transition
            super.addOutgoingTransition(transition);
        }
    }


    public void setSendTransition(ISendTransition sendTransition, int removeCascadingDepth) {
        if (sendTransition == null) return;
        for (ITransition trans : getOutgoingTransitions().values()) {
            // Can only have one send transition
            if (trans instanceof ISendTransition)
                if (trans.equals(sendTransition)) return;
            removeOutgoingTransition(trans.getModelComponentID(), removeCascadingDepth);
            break;
        }
        super.addOutgoingTransition(sendTransition);
    }

    public void setSendTransition(ISendTransition sendTransition) {
        if (sendTransition == null) return;
        for (ITransition trans : getOutgoingTransitions().values()) {
            // Can only have one send transition
            if (trans instanceof ISendTransition)
                if (trans.equals(sendTransition)) return;
            removeOutgoingTransition(trans.getModelComponentID(), 0);
            break;
        }
        super.addOutgoingTransition(sendTransition);
    }

    public ISendTransition getSendTransition() {
        for (ITransition trans : outgoingTransitions.values()) {
            if (trans instanceof ISendTransition sendTrans) return sendTrans;
        }
        return null;
    }

    public void addSendingFailedTransition(ISendingFailedTransition sendingFailedTransition) {
        addOutgoingTransition(sendingFailedTransition);
    }

    public Map<String, ISendingFailedTransition> getSendingFailedTransitions() {
        Map<String, ISendingFailedTransition> failedTransitions = new HashMap<String, ISendingFailedTransition>();
        for (ITransition trans : outgoingTransitions.values()) {
            if (trans instanceof ISendingFailedTransition sendTrans)
                failedTransitions.put(sendTrans.getModelComponentID(), sendTrans);
        }
        return failedTransitions;
    }

    public void removeSendingFailedTransition(String id, int removeCascadingDepth) {
        if (id == null) return;
        ITransition transition = outgoingTransitions.get(id);
        if (transition != null) {
            if (transition instanceof ISendingFailedTransition sendFailedTransition) {
                removeOutgoingTransition(sendFailedTransition.getModelComponentID(), removeCascadingDepth);
            }
        }
    }

    public void removeSendingFailedTransition(String id) {
        if (id == null) return;
        ITransition transition = outgoingTransitions.get(id);
        if (transition != null) {
            if (transition instanceof ISendingFailedTransition sendFailedTransition) {
                removeOutgoingTransition(sendFailedTransition.getModelComponentID(), 0);
            }
        }
    }

    public void setSendingFailedTransitions(Set<ISendingFailedTransition> transitions, int removeCascadingDepth) {
        for (ITransition trans : getOutgoingTransitions().values()) {
            if (trans instanceof ISendingFailedTransition sendFailed)
                removeOutgoingTransition(sendFailed.getModelComponentID(), removeCascadingDepth);
        }
        if (transitions == null) return;
        for (ISendingFailedTransition sendingFailed : transitions) {
            addSendingFailedTransition(sendingFailed);
        }
    }

    public void setSendingFailedTransitions(Set<ISendingFailedTransition> transitions) {
        for (ITransition trans : getOutgoingTransitions().values()) {
            if (trans instanceof ISendingFailedTransition sendFailed)
                removeOutgoingTransition(sendFailed.getModelComponentID(), 0);
        }
        if (transitions == null) return;
        for (ISendingFailedTransition sendingFailed : transitions) {
            addSendingFailedTransition(sendingFailed);
        }
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.hasFunctionSpecification) && element instanceof IFunctionSpecification sendFunction) {
                setFunctionSpecification(sendFunction);
                return true;
            }

            if (predicate.contains(OWLTags.hasOutgoingTransition) && element instanceof ITransition transition) {
                addOutgoingTransition(transition);
                return true;
            }
        } else if (predicate.contains(OWLTags.type)) {
            if (objectContent.contains("AbstractSendState")) {
                setIsStateType(StateType.Abstract);
                return true;
            } else if (objectContent.contains("FinalizedSendState")) {
                setIsStateType(StateType.Finalized);
                return true;
            }
        }
        else if (predicate.contains(OWLTags.abstrHasSimpleSimDurationMeanValue))
        {
            if (this.sisiExecutionDuration == null)
            {
                this.sisiExecutionDuration = new SisiTimeDistribution();
            }
            this.sisiExecutionDuration.meanValue = SisiTimeDistribution.ConvertXSDDurationStringToFractionsOfDay(objectContent);
            return true;
        }
        else if (predicate.contains(OWLTags.abstrHasSimpleSimDurationDeviation))
        {
            if (this.sisiExecutionDuration == null)
            {
                this.sisiExecutionDuration = new SisiTimeDistribution();
            }
            this.sisiExecutionDuration.standardDeviation = SisiTimeDistribution.ConvertXSDDurationStringToFractionsOfDay(objectContent);
            return true;
        }
        else if (predicate.contains(OWLTags.abstrHasSimpleSimDurationMinValue))
        {
            if (this.sisiExecutionDuration == null)
            {
                this.sisiExecutionDuration = new SisiTimeDistribution();
            }
            this.sisiExecutionDuration.minValue = SisiTimeDistribution.ConvertXSDDurationStringToFractionsOfDay(objectContent);
            return true;
        }
        else if (predicate.contains(OWLTags.abstrHasSimpleSimDurationMaxValue))
        {
            if (this.sisiExecutionDuration == null)
            {
                this.sisiExecutionDuration = new SisiTimeDistribution();
            }
            this.sisiExecutionDuration.maxValue = SisiTimeDistribution.ConvertXSDDurationStringToFractionsOfDay(objectContent);
            return true;
        }
        else if (predicate.contains(OWLTags.abstrHasSimpleSimCostPerExecution))
        {
            try
            {
                this.sisiCostPerExecution = Double.parseDouble(objectContent);
            }
            catch (NumberFormatException e)
            {
                logger.warning("could not parse the value " + objectContent + " as valid double");
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
                    if (!stateType.equals(StateType.EndState)) {
                        super.setIsStateType(stateType);
                    }
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
    public ISiSiTimeDistribution getSisiExecutionDuration()
    {
        return this.sisiExecutionDuration;
    }

    public void setSisiExecutionDuration(ISiSiTimeDistribution sisiExecutionDuration)
    {
        this.sisiExecutionDuration = sisiExecutionDuration;
    }


    public double getSisiCostPerExecution()
    {
        return this.sisiCostPerExecution;
    }

    public void setSisiCostPerExecution(double sisiCostPerExecution)
    {
        this.sisiCostPerExecution = sisiCostPerExecution;
    }
}
