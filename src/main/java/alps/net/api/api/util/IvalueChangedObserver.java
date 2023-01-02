package alps.net.api.api.util;


/**
 * Interface that represents an observer that waits for components to be added or removed.
 * Once an element gets added to / removed from another component, this component might be notified (via {@link #updateAdded}, {@link #updateRemoved})
 * by the publisher ({@link IValueChangedPublisher}).
 *
 * @param <T> the type of component that this observer is interested in
 */
public interface IValueChangedObserver<T> {
    /**
     * This method is called when a new component of type T has been added.
     *
     * @param update the new component that has been added
     * @param caller the component that has added the new component
     */
    void updateAdded(T update, T caller);

    /**
     * This method is called when a component of type T has been removed.
     *
     * @param update the component that has been removed
     * @param caller the component that has removed the component
     * @param removeCascadeDepth an integer parsing the depth of a cascading delete after this unregister method has been called
     */
    void updateRemoved(T update, T caller, int removeCascadeDepth);

    /**
     * This method is called when the ID of a model component has changed.
     *
     * @param oldID the old ID of the component
     * @param newID the new ID of the component
     */
    void notifyModelComponentIDChanged(String oldID, String newID);
}

/**
 * Interface that represents a publisher that informs {@link IValueChangedObserver} about components being added or removed.
 * Once an element gets added to / removed from another component, this component can call the notify methods ({@link #updateAdded}, {@link #updateRemoved})
 * on its observers.
 *
 * @param <T> the type of component that this publisher is responsible for
 */
