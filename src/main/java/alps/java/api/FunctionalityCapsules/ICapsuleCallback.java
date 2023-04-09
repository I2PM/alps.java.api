package alps.java.api.FunctionalityCapsules;

import alps.java.api.StandardPASS.*;
import alps.java.api.parsing.IParseablePASSProcessModelElement;

public interface ICapsuleCallback extends IParseablePASSProcessModelElement {
    /**
     * Publishes that an element has been added to this component
     *
     * @param element the added element
     */
    void publishElementAdded(IPASSProcessModelElement element);

    /**
     * Publishes that an element has been removed from this component
     *
     * @param element            the removed element
     * @param removeCascadeDepth An integer that specifies the depth of a cascading delete for connected elements (to the deleted element)
     *                           0 deletes only the given element, 1 the adjacent elements etc
     */
    void publishElementRemoved(IPASSProcessModelElement element, int removeCascadeDepth);

    /**
     * Publishes that an element has been removed from this component
     * the default value of removeCascadeDepth is 0
     *
     * @param element the removed element
     */
    void publishElementRemoved(IPASSProcessModelElement element);
}
