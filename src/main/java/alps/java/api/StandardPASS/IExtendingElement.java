package alps.java.api.StandardPASS;

public interface IExtendingElement<T> {
    void setExtendedElement(T element);

    void setExtendedElementID(String elementID);

    T getExtendedElement();

    String getExtendedElementID();

    /**
     * here should be an explanation
     * @return return something
     */
    boolean isExtension();
}
