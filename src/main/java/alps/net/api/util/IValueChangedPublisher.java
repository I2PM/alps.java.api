package alps.net.api.util;

/// <summary>
/// Interface that represents a publisher that informs <see cref="IValueChangedObserver"/> about components being added or removed.
/// Once an element gets added to / removed from another component, this component can call the notify methods (<see cref="updateAdded"/>, <see cref="updateRemoved"/>)
/// on its observers.
/// </summary>
/// <typeparam name="T"></typeparam>
public interface IValueChangedPublisher<T> {

    /// <summary>
    /// Registers an observer
    /// </summary>
    /// <param name="observer">the observer</param>
    boolean register(IValueChangedObserver<T> observer);

    /// <summary>
    /// De-registers an observer
    /// </summary>
    /// <param name="observer">The observer</param>
    /// <param name="removeCascadeDepth">An integer parsing the depth of a cascading delete after this unregister method has been called</param>
    boolean unregister(IValueChangedObserver<T> observer, int removeCascadeDepth);
}
