package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions.ITimeTransitionCondition;

/**
 * Interface to the time transition class
 */
public interface ITimeTransition extends ITransition {

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
