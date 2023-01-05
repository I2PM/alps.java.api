package alps.net.api.api.util;


/// <summary>
/// Interface that represents an observer that waits for components to be added or removed.
/// Once an element gets added to / removed from another component, this component might be notified (via <see cref="updateAdded"/>, <see cref="updateRemoved"/>)
/// by the publisher (<see cref="IValueChangedPublisher"/>).
/// </summary>
/// <typeparam name="T"></typeparam>
public interface IValueChangedObserver<T> {
    void updateAdded(T update, T caller);

    void updateRemoved(T update, T caller, int removeCascadeDepth);


    void notifyModelComponentIDChanged(String oldID, String newID);
}

