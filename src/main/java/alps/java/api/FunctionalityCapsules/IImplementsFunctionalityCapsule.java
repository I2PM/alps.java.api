package alps.java.api.FunctionalityCapsules;

import alps.java.api.StandardPASS.IImplementingElementT;
import alps.java.api.StandardPASS.IPASSProcessModelElement;

/**
 * This encapsulates the functionality for handling implements relations between elements.
 * Every element can hold a capsule, delegating all the incoming calls to this capsule.
 *
 * @param <T>
 */
public interface IImplementsFunctionalityCapsule<T extends IPASSProcessModelElement> extends IImplementingElementT<T>, IFunctionalityCapsule<T> {
}