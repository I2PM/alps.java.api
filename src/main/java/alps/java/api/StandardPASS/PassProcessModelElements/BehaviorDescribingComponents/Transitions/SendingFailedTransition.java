package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.ISendState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions.ISendingFailedCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a sending failed transition
 */
public class SendingFailedTransition extends Transition implements ISendingFailedTransition {
// TODO check only keyword for source state

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "SendingFailedTransition";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new SendingFailedTransition();
    }

    public SendingFailedTransition() {
    }

    public SendingFailedTransition(IState sourceState, IState targetState, String labelForID, ITransitionCondition transitionCondition,
                                   ITransition.TransitionType transitionType, String comment, String additionalLabel,
                                   List<IIncompleteTriple> additionalAttribute) {
        super(sourceState, targetState, labelForID, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
    }

    public SendingFailedTransition(IState sourceState, IState targetState) {
        super(sourceState, targetState, null, null, TransitionType.Standard, null, null, null);
    }

    public SendingFailedTransition(ISubjectBehavior behavior, String label,
                                   IState sourceState, IState targetState, ITransitionCondition transitionCondition,
                                   ITransition.TransitionType transitionType,
                                   String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, label, sourceState, targetState, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
    }

    @Override
    public void setSourceState(IState source, int removeCascadeDepth) {
        if (source instanceof ISendState sendState) {
            super.setSourceState(sendState);
        }
    }

    @Override
    public void setSourceState(IState source) {
        if (source instanceof ISendState sendState) {
            super.setSourceState(sendState);
        }
    }

    public ISendState getSourceState() {
        return (ISendState) super.getSourceState();
    }


    @Override
    public void setTransitionCondition(ITransitionCondition sendingFailedCondition, int removeCascadeDepth) {
        if (sendingFailedCondition instanceof ISendingFailedCondition) {
            super.setTransitionCondition(sendingFailedCondition);
        } else {
            super.setTransitionCondition(null);
        }
    }

    @Override
    public void setTransitionCondition(ITransitionCondition sendingFailedCondition) {
        if (sendingFailedCondition instanceof ISendingFailedCondition) {
            super.setTransitionCondition(sendingFailedCondition);
        } else {
            super.setTransitionCondition(null);
        }
    }

    public ISendingFailedCondition getTransitionCondition() {
        return (ISendingFailedCondition) super.getTransitionCondition();
    }


    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.hasTransitionCondition) && element instanceof ITransitionCondition sendingFailed) {
                setTransitionCondition(sendingFailed);
                return true;
            }

            if (predicate.contains(OWLTags.hasSourceState) && element instanceof IState sendState) {
                setSourceState(sendState);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }


}

