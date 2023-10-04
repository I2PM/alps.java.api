package alps.java.api.util;

/**
 * For SimpleSimulation: expected cost of a state per Execution
 */
public interface IHasSiSiCostPerExecution {
    /**
     * The expected cost per Exectuion for a state
     *
     * @return
     */
    double getSisiCostPerExecution();

    /**
     * The expected cost per Exectuion for a stat
     *
     * @param sisiCostPerExecution The expected cost per Exectuion for a stat
     */
    void setSisiCostPerExecution(double sisiCostPerExecution);
}
