package alps.java.api.util;

 public interface IHasSiSiDistribution {

interface IHasSiSiTimeCategory
{
    /**
     * For Simple Simulations for state Set the type of time Spend
     * Standard;Main Processing Time;Secondary Processing Time;Waiting Time
     * @return
     */
    SimpleSimTimeCategory getSisiVSMTimeCategory();

    /**
     * For Simple Simulations for state Set the type of time Spend
     * Standard;Main Processing Time;Secondary Processing Time;Waiting Time
     * @param simpleSimTimeCategory
     */
    void setSisiVSMTimeCategory(SimpleSimTimeCategory simpleSimTimeCategory);
}
 interface IHasDuration
{
    /**
     * For simple simulation of processes: The (expected) transmission time of this kind of message. Necessary only for simulation purposes
     * @return
     */
    ISiSiTimeDistribution getSisiExecutionDuration();


    /**
     * For simple simulation of processes: The (expected) transmission time of this kind of message. Necessary only for simulation purposes
     * @param sisiExecutionDuration
     */
    void setSisiExecutionDuration (ISiSiTimeDistribution sisiExecutionDuration);

}


/**
 * For Simple Simulations for state Set the type of time Spend
 * Standard;Main Processing Time;Secondary Processing Time;Waiting Time
 */
 enum SimpleSimTimeCategory
{
    Standard,
    Main,
    Secondary,
    Waiting
}

}
