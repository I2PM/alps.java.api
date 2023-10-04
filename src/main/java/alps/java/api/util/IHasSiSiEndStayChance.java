package alps.java.api.util;

/**
 * For SimpleSimulation: to define
 */
public interface IHasSiSiEndStayChance {
    /**
     * For do-end states to define what the likelihood of remining in the state is
     * If there should be a do transition to leave the state
     * SHOULD be a value between 0 and 1
     *
     * @return
     */
    public double getSisiEndStayChance();

    /**
     * For do-end states to define what the likelihood of remining in the state is
     * If there should be a do transition to leave the state
     * SHOULD be a value between 0 and 1
     *
     * @param value
     */
    public void setSisiEndStayChance(double value);
}
