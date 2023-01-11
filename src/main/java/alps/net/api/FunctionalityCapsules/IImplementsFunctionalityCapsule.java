package alps.net.api.FunctionalityCapsules;

import alps.net.api.StandardPASS.IImplementingElementT;
import alps.net.api.StandardPASS.IPASSProcessModelElement;

public interface IImplementsFunctionalityCapsule<T extends IPASSProcessModelElement> extends IImplementingElementT<T>, IFunctionalityCapsule<T> {
        }