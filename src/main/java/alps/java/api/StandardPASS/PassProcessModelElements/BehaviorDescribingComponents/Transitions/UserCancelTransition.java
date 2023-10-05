package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.logging.Logger;

/**
 * Class that represents a user cancel transition
 */
public class UserCancelTransition extends Transition implements IUserCancelTransition {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "UserCancelTransition";
    private double _sisiChoiceChance;
    public double getSisiChoiceChance()
    {
        return this._sisiChoiceChance;
    }

    public void setSisiChoiceChance(double value)
    {
        if (value >= 0.0) { _sisiChoiceChance = value; }
        else { throw new IllegalArgumentException("_sisiChoiceChance" + "Value must be between 0.0 and 1.0."); }
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new UserCancelTransition();
    }


    public UserCancelTransition() {
    }

    public UserCancelTransition(IState sourceState, IState targetState, String labelForID, ITransitionCondition transitionCondition,
                                ITransition.TransitionType transitionType, String comment, String additionalLabel,
                                List<IIncompleteTriple> additionalAttribute) {
        super(sourceState, targetState, labelForID, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
    }

    public UserCancelTransition(IState sourceState, IState targetState) {
        super(sourceState, targetState, null, null, TransitionType.Standard, null, null, null);
    }

    public UserCancelTransition(ISubjectBehavior behavior, String labelForID,
                                IState sourceState, IState targetState, ITransitionCondition transitionCondition,
                                ITransition.TransitionType transitionType,
                                String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, sourceState, targetState, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
    }

    public UserCancelTransition(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, TransitionType.Standard, null, null, null);
    }
    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
    {

        if (predicate.contains(OWLTags.abstrHasSimpleSimTransitionChoiceChance))
        {
            try
            {
                this.setSisiChoiceChance(Double.parseDouble(objectContent));
            }
            catch (NumberFormatException e)
            {
                Logger logger = Logger.getLogger("UserCancelTransition");
                logger.warning("could not parse the value " + objectContent + " as valid double");
            }
            return true;
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);


    }
}
