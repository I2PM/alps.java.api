package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;

/**
 * Interface to the time transition condition class
 */
public interface ITimeTransitionCondition extends ITransitionCondition {

    public enum TimeTransitionConditionType {
        TimeBasedReminder,
        BusinessDayTimer,
        CalendarBasedReminder,
        DayTimeTimer,
        YearMonthTimer
    }


    /**
     * Method that sets the time value attribute of the instance
     *
     * @param timeValue the time value
     */
    void setTimeValue(String timeValue);

    /**
     * Method that returns the time value attribute of the instance
     *
     * @return The time value attribute of the instance
     */
    String getTimeValue();

    public void setTimeTransitionConditionType(TimeTransitionConditionType type);

    public TimeTransitionConditionType getTimeTransitionType();
}

