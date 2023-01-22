import alps.java.api.FunctionalityCapsules.ICapsuleCallback;
import alps.java.api.FunctionalityCapsules.IExtendsFunctionalityCapsule;
import alps.java.api.parsing.*;
import alps.java.api.StandardPASS.*;
import alps.java.api.util.*;
/// <summary>
/// Encapsulates the extends behavior.
/// Elements can hold this capsule and delegate methods to it
/// </summary>
/// <typeparam name="T"></typeparam>



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
                if (getExtendedElement() != null || getExtendedElementID() != null)
                return true;
                return false;
                }

        public boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
                {
                if (predicate.contains(OWLTags.extends))
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
    //TODO: removeTriple und addTriple austauschen
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
