package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions.ITimeTransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class that represents a time transition
 */
public class TimeTransition extends Transition implements ITimeTransition {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "TimeTransition";

    @Override
    public String getClassName() {
        return className;
    }

    public static final Map<Integer, String> timeTransitionTypesExportNames = new HashMap<>();

    static {
        timeTransitionTypesExportNames.put((int) TimeTransitionType.CalendarBasedReminder.ordinal(), OWLTags.CalendarBasedReminderTransitionClassName);
        timeTransitionTypesExportNames.put((int) TimeTransitionType.TimeBasedReminder.ordinal(), OWLTags.TimeBasedReminderTransitionClassName);
        timeTransitionTypesExportNames.put((int) TimeTransitionType.BusinessDayTimer.ordinal(), OWLTags.BusinessDayTimerTransitionClassName);
        timeTransitionTypesExportNames.put((int) TimeTransitionType.DayTimeTimer.ordinal(), OWLTags.DayTimeTimerTransitionClassName);
        timeTransitionTypesExportNames.put((int) TimeTransitionType.YearMonthTimer.ordinal(), OWLTags.YearMonthTimerTransitionClassName);
    }


    private TimeTransitionType timeTransitionType = TimeTransitionType.BusinessDayTimer;
    public double _sisiChoiceChance;
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
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new TimeTransition();
    }

    public ITimeTransitionCondition getTransitionCondition() {
        return (ITimeTransitionCondition) transitionCondition;
    }

    @Override
    public void setTransitionCondition(ITransitionCondition transitionCondition, int removeCascadeDepth) {
        if (transitionCondition instanceof ITimeTransitionCondition)
            super.setTransitionCondition(transitionCondition, removeCascadeDepth);
    }

    @Override
    public void setTransitionCondition(ITransitionCondition transitionCondition) {
        if (transitionCondition instanceof ITimeTransitionCondition)
            super.setTransitionCondition(transitionCondition, 0);
    }

    public void setTimeTransitionType(TimeTransitionType type) {
        TimeTransitionType oldType = this.timeTransitionType;
        this.timeTransitionType = type;

        if (oldType.equals(timeTransitionType)) return;

        // Removes the export tag (if it exists) which defines the element as pure TimeTransition instance
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));

        // Removes the export tag (if it exists) which defines the element as instance of the previously specified transition type
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + timeTransitionTypesExportNames.get((int) oldType.ordinal())));

        // Adds the export tag which defines the element as instance of the newly specified transition type
        addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + timeTransitionTypesExportNames.get((int) timeTransitionType.ordinal())));
    }

    public TimeTransitionType getTimeTransitionType() {
        return timeTransitionType;
    }

    public TimeTransition() {
        setTimeTransitionType(TimeTransitionType.DayTimeTimer);
    }

    /**
     * Constructor that creates a new fully specified instance of the timer transition class
     *
     * @param sourceState
     * @param targetState
     * @param labelForID
     * @param transitionCondition
     * @param transitionType
     * @param timeTransitionType
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public TimeTransition(IState sourceState, IState targetState, String labelForID, ITimeTransitionCondition transitionCondition,
                          ITransition.TransitionType transitionType, TimeTransitionType timeTransitionType,
                          String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(sourceState, targetState, labelForID, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
        setTimeTransitionType(timeTransitionType);
    }

    /**
     * Constructor that creates a new fully specified instance of the timer transition class
     *
     * @param sourceState
     * @param targetState
     */
    public TimeTransition(IState sourceState, IState targetState) {
        super(sourceState, targetState, null, null, TransitionType.Standard, null, null, null);
        setTimeTransitionType(TimeTransitionType.DayTimeTimer);
    }

    public TimeTransition(ISubjectBehavior behavior, String labelForID,
                          IState sourceState, IState targetState, ITimeTransitionCondition transitionCondition,
                          ITransition.TransitionType transitionType, TimeTransitionType timeTransitionType,
                          String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, sourceState, targetState, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
        setTimeTransitionType(timeTransitionType);
    }

    public TimeTransition(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, TransitionType.Standard, null, null, null);
        setTimeTransitionType(TimeTransitionType.DayTimeTimer);
    }

    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {

        // Parse a child of a TimeTransition correctly.
        if (predicate.contains(OWLTags.type)) {
            for (Map.Entry<Integer, String> specificPair : timeTransitionTypesExportNames.entrySet()) {
                if (objectContent.contains(specificPair.getValue())) {
                    setTimeTransitionType(TimeTransitionType.values()[specificPair.getKey()]);
                    return true;
                }
            }
        }else if (predicate.contains(OWLTags.abstrHasSimpleSimTransitionChoiceChance))
        {
            try
            {
                this.setSisiChoiceChance(Double.parseDouble(objectContent));
            }
            catch (NumberFormatException e)
            {
                Logger logger = Logger.getLogger("TimeTransition");
                logger.warning("could not parse the value " + objectContent + " as valid double");
            }
            return true;
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);


    }
}
