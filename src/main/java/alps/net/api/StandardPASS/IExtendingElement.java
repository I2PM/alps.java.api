package alps.net.api.StandardPASS;

public interface IExtendingElement<T> {
    void setExtendedElement(T element);

    void setExtendedElementID(String elementID);

    T getExtendedElement();

    String getExtendedElementID();

    boolean isExtension();
}
