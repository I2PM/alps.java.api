package alps.java.api.util;

public interface IHasSiSiChoiceChance {
    /**
     * For DoTransition or TimeTransition or UserCancelTransition
     * SHOULD be a value between 0 and 1 but can also be higher
     * @return
     */
    double getSisiChoiceChance();

    /**
     *  For DoTransition or TimeTransition or UserCancelTransition
     *  SHOULD be a value between 0 and 1 but can also be higher (but not below 0)
     * @param value
     */
    void setSisiChoiceChance(double value);
}
