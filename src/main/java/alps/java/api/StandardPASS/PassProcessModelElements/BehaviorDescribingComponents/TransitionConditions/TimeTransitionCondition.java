package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionCondition;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents a time transition condition
 */
public class TimeTransitionCondition extends TransitionCondition implements ITimeTransitionCondition {
    protected String timeValue = "";
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "TimeTransitionCondition";

    private TimeTransitionConditionType timeTransitionConditionType;

    /**
     * This dictionary is used to simplify parsing, since the specific subclasses of the TimeTransitionCondition are not modelled as classes explicitly.
     * The choice is made because the classes do not have different functionality (only when it comes to im- and export), so it simplifies the usage of
     * TimeTransitionCondition for users of the library.
     */
    Map<Integer, SpecificTimeTransitionCondition> specificConditions = new HashMap<Integer, SpecificTimeTransitionCondition>() {{
        // CalendarBasedReminderTransitionCondition
        put((int) TimeTransitionConditionType.CalendarBasedReminder.ordinal(),
                new SpecificTimeTransitionCondition(OWLTags.CalendarBasedReminderTransitionClassName, OWLTags.hasCalendarBasedFrequencyOrDate,
                        OWLTags.stdHasTimeBasedReoccuranceFrequencyOrDate, OWLTags.xsdDataTypeString));

        // TimeBasedReminderTransitionCondition
        put((int) TimeTransitionConditionType.TimeBasedReminder.ordinal(),
                new SpecificTimeTransitionCondition(OWLTags.TimeBasedReminderTransitionConditionClassName, OWLTags.hasTimeBasedReoccuranceFrequencyOrDate,
                        OWLTags.stdHasTimeBasedReoccuranceFrequencyOrDate, OWLTags.xsdDataTypeString));

        // BusinessDayTimerTransitionCondition
        put((int) TimeTransitionConditionType.BusinessDayTimer.ordinal(),
                new SpecificTimeTransitionCondition(OWLTags.BusinessDayTimerTransitionConditionClassName, OWLTags.hasBusinessDayDurationTimeOutTime,
                        OWLTags.stdHasBusinessDayDurationTimeOutTime, OWLTags.xsdDayTimeDuration));

        // DayTimeTimerTransitionCondition
        put((int) TimeTransitionConditionType.DayTimeTimer.ordinal(),
                new SpecificTimeTransitionCondition(OWLTags.DayTimeTimerTransitionConditionClassName, OWLTags.hasDayTimeDurationTimeOutTime,
                        OWLTags.stdHasDayTimeDurationTimeOutTime, OWLTags.xsdDayTimeDuration));

        // YearMonthTimerTransitionCondition
        put((int) TimeTransitionConditionType.YearMonthTimer.ordinal(),
                new SpecificTimeTransitionCondition(OWLTags.YearMonthTimerTransitionConditionClassName, OWLTags.hasYearMonthDurationTimeOutTime,
                        OWLTags.stdHasYearMonthDurationTimeOutTime, OWLTags.xsdYearMonthDuration));
    }};

    /**
     * Needed for the {@link #setTimeValue(String)} Method. If the type changed since the time value was set the last time,
     * the old triple parsing the time value must be replaced by a triple containing a different predicate
     * (All different classes define differnt predicates for the time value).
     */
    public TimeTransitionConditionType lastUsedTypeForExportFunctions;

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new TimeTransitionCondition();
    }

    public TimeTransitionCondition() {
        lastUsedTypeForExportFunctions = TimeTransitionConditionType.DayTimeTimer;
        //TODO: auskommentiert, da sonst fehlermeldung
        //setTimeTransitionConditionType(TimeTransitionConditionType.DayTimeTimer);
    }

    public TimeTransitionCondition(ITransition transition, String labelForID, String toolSpecificDefintion, String timeValue,
                                   TimeTransitionConditionType timeTransitionConditionType, String comment,
                                   String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(transition, labelForID, toolSpecificDefintion, comment, additionalLabel, additionalAttribute);
        lastUsedTypeForExportFunctions = timeTransitionConditionType;
        setTimeTransitionConditionType(timeTransitionConditionType);
        setTimeValue(timeValue);
    }

    public TimeTransitionCondition(ITransition transition) {
        super(transition, null, null, null, null, null);
        lastUsedTypeForExportFunctions = TimeTransitionConditionType.DayTimeTimer;
        setTimeTransitionConditionType(TimeTransitionConditionType.DayTimeTimer);
        setTimeValue(null);
    }
//TODO: hier kriege ich NullPointerException, weil die Map specificConditions null ist, warum wird das überhaupt aufgerufen?
    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        // Check if one of the predicates - defined by the different Condition types - is the predicate of the triple
        // For example "hasCalendarBasedFrquencyOrDate" for CalendarBased...
        Map<Integer, SpecificTimeTransitionCondition> specificConditions = new HashMap<Integer, SpecificTimeTransitionCondition>() {{
            // CalendarBasedReminderTransitionCondition
            put((int) TimeTransitionConditionType.CalendarBasedReminder.ordinal(),
                    new SpecificTimeTransitionCondition(OWLTags.CalendarBasedReminderTransitionClassName, OWLTags.hasCalendarBasedFrequencyOrDate,
                            OWLTags.stdHasTimeBasedReoccuranceFrequencyOrDate, OWLTags.xsdDataTypeString));

            // TimeBasedReminderTransitionCondition
            put((int) TimeTransitionConditionType.TimeBasedReminder.ordinal(),
                    new SpecificTimeTransitionCondition(OWLTags.TimeBasedReminderTransitionConditionClassName, OWLTags.hasTimeBasedReoccuranceFrequencyOrDate,
                            OWLTags.stdHasTimeBasedReoccuranceFrequencyOrDate, OWLTags.xsdDataTypeString));

            // BusinessDayTimerTransitionCondition
            put((int) TimeTransitionConditionType.BusinessDayTimer.ordinal(),
                    new SpecificTimeTransitionCondition(OWLTags.BusinessDayTimerTransitionConditionClassName, OWLTags.hasBusinessDayDurationTimeOutTime,
                            OWLTags.stdHasBusinessDayDurationTimeOutTime, OWLTags.xsdDayTimeDuration));

            // DayTimeTimerTransitionCondition
            put((int) TimeTransitionConditionType.DayTimeTimer.ordinal(),
                    new SpecificTimeTransitionCondition(OWLTags.DayTimeTimerTransitionConditionClassName, OWLTags.hasDayTimeDurationTimeOutTime,
                            OWLTags.stdHasDayTimeDurationTimeOutTime, OWLTags.xsdDayTimeDuration));

            // YearMonthTimerTransitionCondition
            put((int) TimeTransitionConditionType.YearMonthTimer.ordinal(),
                    new SpecificTimeTransitionCondition(OWLTags.YearMonthTimerTransitionConditionClassName, OWLTags.hasYearMonthDurationTimeOutTime,
                            OWLTags.stdHasYearMonthDurationTimeOutTime, OWLTags.xsdYearMonthDuration));
        }};
        for (SpecificTimeTransitionCondition specific : specificConditions.values()) {
            if (predicate.contains(specific.getTimeValuePredicate(false))) {
                setTimeValue(objectContent);
                return true;
            }
        }

        // Parse a child of a TimeTransitionCondition correctly.
        if (predicate.contains(OWLTags.type)) {
            for (Map.Entry<Integer, SpecificTimeTransitionCondition> specificPair : specificConditions.entrySet()) {
                if (objectContent.contains(specificPair.getValue().getExportString())) {
                    setTimeTransitionConditionType(TimeTransitionConditionType.values()[specificPair.getKey()]);
                    return true;
                }
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    public void setTimeTransitionConditionType(TimeTransitionConditionType type) {
        TimeTransitionConditionType oldType = this.timeTransitionConditionType;
        this.timeTransitionConditionType = type;

        if (oldType.equals(timeTransitionConditionType)) return;

        // Removes the export tag (if it exists) which defines the element as pure TimeTransitionCondition instance
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));

        // Removes the export tag (if it exists) which defines the element as instance of the previously specified transition condition type
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + specificConditions.get((int) oldType.ordinal()).getExportString()));

        // Adds the export tag which defines the element as instance of the newly specified transition condition type
        addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + specificConditions.get((int) timeTransitionConditionType.ordinal()).getExportString()));

        // Important! The time value must be exported again using the new type to get correct triples
        setTimeValue(timeValue);
    }

    public TimeTransitionConditionType getTimeTransitionType() {
        return timeTransitionConditionType;
    }

    public void setTimeValue(String timeValue) {
        // The last check is important! If the type changed, the time value triples must be changed too since the predicate differs for different TimeTransitionConditions.
        if (timeValue != null && timeValue.equals(this.timeValue) && lastUsedTypeForExportFunctions == timeTransitionConditionType)
            return;

        // We remove the last timeValue triple, which may have a different predicate than the current since the TimeTransitionCondition type could have been different
        SpecificTimeTransitionCondition specificCond = specificConditions.get((int) lastUsedTypeForExportFunctions.ordinal());
        removeTriple(new IncompleteTriple(specificCond.getTimeValuePredicate(true),
                this.timeValue, IncompleteTriple.LiteralType.DATATYPE, specificCond.getDataType()));


        this.timeValue = (timeValue == null || timeValue.equals("")) ? null : timeValue;
        if (timeValue != null) {
            // We fetch the predicate we need with the current TimeTransitionCondition type and export the value with it
            SpecificTimeTransitionCondition newSpecificCond = specificConditions.get((int) timeTransitionConditionType.ordinal());
            addTriple(new IncompleteTriple(newSpecificCond.getTimeValuePredicate(true), timeValue, IncompleteTriple.LiteralType.DATATYPE, newSpecificCond.getDataType()));
            lastUsedTypeForExportFunctions = timeTransitionConditionType;
        }

    }

    protected String getTimeTag(boolean withStd) {
        if (withStd)
            return OWLTags.stdHasTimeValue;
        return OWLTags.hasTimeValue;
    }

    protected String getTimeDatatype() {
        return OWLTags.xsdDataTypeString;
    }


    public String getTimeValue() {
        return timeValue;
    }


    /**
     * Small helper class that keeps all information regarding specific TimeTransitionCondition classes which are not modelled as classes explicitly
     */
    class SpecificTimeTransitionCondition {
        private String exportString, timeValuePredicate, timeValuePredicateWithPrefix, dataType;

        public SpecificTimeTransitionCondition(String exportString, String timeValuePredicate, String timeValuePredicateWithPrefix, String dataType) {
            this.exportString = exportString;
            this.timeValuePredicate = timeValuePredicate;
            this.timeValuePredicateWithPrefix = timeValuePredicateWithPrefix;
            this.dataType = dataType;
        }

        /**
         * The export string is the class name of the sepcific subclass.
         * For example for the TimeBasedReminderTransitionCondition, it would be "TimeBasedReminderTransitionCondition".
         * It is used for parsing triples to class data and vice versa
         *
         * @return
         */
        public String getExportString() {
            return exportString;
        }

        /**
         * The time value string is the triple predicate that is used by each specific subclass.
         * For example the class CalendarBasedReminderTransitionCondition uses the predicate "hasCalendarBasedFrequencyOrDate" to describe its time string,
         * while the class DayTimeTimerTransitionCondition uses "hasDayTimeDurationTimeOutTime"
         *
         * @param withPrefix if this is true, the predicate also contains the owl prefix, usually "standard-pass-ont:"
         * @return
         */
        public String getTimeValuePredicate(boolean withPrefix) {
            return (withPrefix) ? timeValuePredicateWithPrefix : timeValuePredicate;
        }

        /**
         * The datatype is the type of the time value for each specific subclass.
         * For example for the DayTimeTimerTransitionCondition it is "xsd:DayTimeDuration",
         * while for YearMonthTimerTransitionCondition it is "xsd:YearMonthDuration"
         *
         * @return
         */
        public String getDataType() {
            return dataType;
        }
    }
}
