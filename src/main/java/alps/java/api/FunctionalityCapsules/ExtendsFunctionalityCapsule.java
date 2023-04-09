package alps.java.api.FunctionalityCapsules;

import alps.java.api.parsing.*;
import alps.java.api.StandardPASS.*;
import alps.java.api.src.OWLTags;
import alps.java.api.util.*;

    public class ExtendsFunctionalityCapsule<T extends  IPASSProcessModelElement> implements IExtendsFunctionalityCapsule<T>
            {
        protected T extendedElement;
        protected String extendedElementID;
        protected final ICapsuleCallback callback;

        public ExtendsFunctionalityCapsule(ICapsuleCallback callback)
                {
                this.callback = callback;
                }

        public T getExtendedElement()
                {
                return extendedElement;
                }

        public String getExtendedElementID()
                {
                if ((extendedElement !=  null) && !extendedElement.getModelComponentID().equals(extendedElementID))
                {
                setExtendedElementID(extendedElement.getModelComponentID());
                }
                return extendedElementID;
                }

        public boolean isExtension()
                {
                        return getExtendedElement() != null || getExtendedElementID() != null;
                }
//TODO: Typparamater checken
        public boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
                {
                if (predicate.contains(OWLTags.eextends))
                {
                if (element instanceof T)
                {
                    T fittingElement = (T) element;
                setExtendedElement(fittingElement);
                return true;
                }
                else
                {
                setExtendedElementID(objectContent);
                return true;
                }
                }
                return false;
                }

        public void setExtendedElement(T element)
                {
                T oldExtends = extendedElement;
                // Might set it to null
                this.extendedElement = element;

                if (oldExtends != null)
                {
                if (oldExtends.equals(element)) return;
                oldExtends.unregister(callback);
                callback.removeTriple(new IncompleteTriple(OWLTags.abstrExtends, oldExtends.getUriModelComponentID()));
                }

                if (extendedElement != null)
                {
                callback.publishElementAdded(extendedElement);
                extendedElement.register(callback);
                callback.addTriple(new IncompleteTriple(OWLTags.abstrExtends, extendedElement.getUriModelComponentID()));
                }
                }

        public void setExtendedElementID(String elementID)
                {
                this.extendedElementID = elementID;
                }
                }
