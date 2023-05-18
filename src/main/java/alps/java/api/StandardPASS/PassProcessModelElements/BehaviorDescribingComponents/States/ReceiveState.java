package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications.IReceiveFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions.IReceiveTransition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents a receive state
 */
public class ReceiveState extends StandardPASSState implements IReceiveState {

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ReceiveState";
    protected String exportTag = OWLTags.std;
    protected String exportClassname = className;

    @Override
    public String getClassName() {
        return exportClassname;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ReceiveState();
    }

    protected ReceiveState() {
    }

    @Override
    protected String getExportTag() {
        return exportTag;
    }

    public ReceiveState(ISubjectBehavior behavior, String labelForID, IGuardBehavior guardBehavior,
                        IReceiveFunction functionSpecification,
                        Set<ITransition> incomingTransition, Set<ITransition> outgoingTransition, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, guardBehavior, functionSpecification, incomingTransition, outgoingTransition, comment, additionalLabel, additionalAttribute);
    }

    public ReceiveState(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, null, null, null, null);
    }


    public IReceiveFunction getFunctionSpecification() {
        return (IReceiveFunction) super.getFunctionSpecification();
    }


    public void setFunctionSpecification(IFunctionSpecification specification, int removeCascadingDepth) {
        if (specification instanceof IReceiveFunction) {
            super.setFunctionSpecification(specification, removeCascadingDepth);
        } else {
            super.setFunctionSpecification(null);
        }
    }

    public void setFunctionSpecification(IFunctionSpecification specification) {
        if (specification instanceof IReceiveFunction) {
            super.setFunctionSpecification(specification, 0);
        } else {
            super.setFunctionSpecification(null);
        }
    }

    @Override
    public void addOutgoingTransition(ITransition transition) {
        if (transition instanceof IReceiveTransition)
            super.addOutgoingTransition(transition);
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.hasFunctionSpecification) && element instanceof IReceiveFunction receiveFunction) {
                setFunctionSpecification(receiveFunction);
                return true;
            }
        } else if (predicate.contains(OWLTags.type)) {
            if (objectContent.contains("AbstractReceiveState")) {
                setIsStateType(StateType.Abstract);
                return true;
            } else if (objectContent.contains("FinalizedReceiveState")) {
                setIsStateType(StateType.Finalized);
                return true;
            }
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


}