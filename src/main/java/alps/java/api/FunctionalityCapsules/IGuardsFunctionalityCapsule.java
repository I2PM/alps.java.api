package alps.java.api.FunctionalityCapsules;

import alps.java.api.StandardPASS.IGuardingElement;

/**
 * Encapsulates the extends behavior.
 * Elements can hold this capsule and delegate methods to it
 *
 * @param <T>
 */
public interface IGuardsFunctionalityCapsule<T> extends IGuardingElement<T>, IFunctionalityCapsule<T> {
}
