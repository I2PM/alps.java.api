package alps.net.api.api.util;

public interface IValueChangedPublisher<T> {
    /**
     * Registers an observer
     *
     * @param observer the observer to be registered
     * @return true if the observer has been registered successfully, false if not
     */
    boolean register(IValueChangedObserver<T> observer);

    /**
     * De-registers an observer
     *
     * @param observer the observer to be de-registered
     * @param removeCascadeDepth an integer parsing the depth of a cascading delete after this unregister method has been called
     * @return true if the observer has been de-registered successfully, false if not
     */
    boolean unregister(IValueChangedObserver<T> observer, int removeCascadeDepth);
}
