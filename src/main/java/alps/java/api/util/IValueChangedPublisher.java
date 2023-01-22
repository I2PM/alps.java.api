package alps.java.api.util;

    /**
     * Interface that represents a publisher that informs {@link IValueChangedObserver} about components being added or removed.
     * Once an element gets added to / removed from another component, this component can call the notify methods ({@link "updateAdded"}, {@link "updateRemoved"})
     * on its observers.
     * @param <T>
     */
    public interface IValueChangedPublisher<T> {

        /**
         * Registers an observer
         * @param observer the observer
         * @return
         */
        boolean register(IValueChangedObserver<T> observer);

        /**
         * De-registers an observer
         * @param observer the observer
         * @param removeCascadeDepth An integer parsing the depth of a cascading delete after this unregister method has been called
         * @return
         */
        boolean unregister(IValueChangedObserver<T> observer, int removeCascadeDepth);
}
