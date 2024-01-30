package alps.java.api.util;


/**
 * Interface that represents an observer that waits for components to be added or removed.
 * Once an element gets added to / removed from another component, this component might be notified (via {@link #updateAdded(Object, Object)} {@link #updateRemoved(Object, Object)})
 * by the publisher {@link IValueChangedPublisher}.
 *
 * @param <T>
 */
public interface IValueChangedObserver<T> {
    void updateAdded(T update, T caller);

    void updateRemoved(T update, T caller, int removeCascadeDepth);

    void updateRemoved(T update, T caller);


    void notifyModelComponentIDChanged(String oldID, String newID);
}

