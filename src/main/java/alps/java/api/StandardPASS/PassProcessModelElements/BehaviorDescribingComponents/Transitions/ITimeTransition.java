package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions.ITimeTransitionCondition;
import alps.java.api.util.IHasSiSiChoiceChance;

/**
 * Interface to the time transition class
 */
public interface ITimeTransition extends ITransition, IHasSiSiChoiceChance {

    public enum TimeTransitionType {
        TimeBasedReminder,
        BusinessDayTimer,
        CalendarBasedReminder,
        DayTimeTimer,
        YearMonthTimer
    }

    public void setTimeTransitionType(TimeTransitionType type);

    public TimeTransitionType getTimeTransitionType();

    public ITimeTransitionCondition getTransitionCondition();
}
