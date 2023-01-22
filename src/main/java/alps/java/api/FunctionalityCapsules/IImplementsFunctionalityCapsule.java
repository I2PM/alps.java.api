package alps.java.api.FunctionalityCapsules;

import alps.java.api.StandardPASS.IImplementingElementT;
import alps.java.api.StandardPASS.IPASSProcessModelElement;

public interface IImplementsFunctionalityCapsule<T extends IPASSProcessModelElement> extends IImplementingElementT<T>, IFunctionalityCapsule<T> {
        }