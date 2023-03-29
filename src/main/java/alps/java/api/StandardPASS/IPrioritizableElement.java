package alps.java.api.StandardPASS;

public interface IPrioritizableElement {
    /**
     * Sets the priority number of the transition, must be greater than or equal to 0
     * @param nonNegativInteger the priority number
     */
    void setPriorityNumber(int nonNegativInteger);

    /**
     * Returns the priority number of the transition
     * @return the priority number
     */
    int getPriorityNumber();
}
