package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

/**
 *  Class that represents a DoTransition
 */
public class DoTransition extends Transition implements IDoTransition
        {
protected int priorityNumber = 0;
/**
 * Name of the class, needed for parsing
 */
            private final String className = "DoTransition";

@Override
public String getClassName()
        {
        return className;
        }
@Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new DoTransition();
        }

protected DoTransition() { }
public DoTransition(IState sourceState, IState targetState, string labelForID = null, ITransitionCondition transitionCondition = null,
                    ITransition.TransitionType transitionType = ITransition.TransitionType.Standard, int priorityNumber = 0, string comment = null, string additionalLabel = null,
                    IList<IIncompleteTriple> additionalAttribute = null) : base(sourceState, targetState, labelForID, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute) {
        setPriorityNumber(priorityNumber);
        }

public DoTransition(ISubjectBehavior behavior, string label = null,
                    IState sourceState = null, IState targetState = null, ITransitionCondition transitionCondition = null,
                    ITransition.TransitionType transitionType = ITransition.TransitionType.Standard,
                    int priorityNumber = 0, string comment = null, string additionalLabel = null, IList<IIncompleteTriple> additionalAttribute = null)
        : base(behavior, label, sourceState, targetState, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute) {
        setPriorityNumber(priorityNumber);
        }

public new void setSourceState(IState state, int removeCascadeDepth = 0)
        {
        if (state is IDoState)
        {
        base.setSourceState(state);
        }
        }


public void setPriorityNumber(int positiveInteger)
        {
        if (positiveInteger == this.priorityNumber) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasPriorityNumber, this.priorityNumber.ToString(), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
        priorityNumber = (positiveInteger > 0) ? positiveInteger : 1;
        addTriple(new IncompleteTriple(OWLTags.stdHasPriorityNumber, priorityNumber.ToString(), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
        }


public int getPriorityNumber()
        {
        return priorityNumber;
        }


protected override bool parseAttribute(string predicate, string objectContent, string lang, string dataType, IParseablePASSProcessModelElement element)
        {
        if (predicate.Contains(OWLTags.hasPriorityNumber))
        {
        string prio = objectContent;
        setPriorityNumber(int.Parse(prio));
        return true;
        }
        return base.parseAttribute(predicate, objectContent, lang, dataType, element);
        }

        }
