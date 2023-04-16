package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects;


import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;

/**
 * Interface to the interface subject class
 */

public interface IInterfaceSubject extends ISubject {
    // Sollte eigentlich eine Methode containsBehavior haben, da aber max 0 gilt
    // existiert diese nicht

    /**
     * Sets the subject referenced by this interface subject
     *
     * @param fullySpecifiedSubject the referenced subject
     * @param removeCascadeDepth    Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setReferencedSubject(IFullySpecifiedSubject fullySpecifiedSubject, int removeCascadeDepth);

    /**
     * Sets the subject referenced by this interface subject
     *
     * @param fullySpecifiedSubject the referenced subject
     */
    void setReferencedSubject(IFullySpecifiedSubject fullySpecifiedSubject);

    /**
     * Returns the subject referenced by this interface subject
     *
     * @return the referenced subject
     */
    IFullySpecifiedSubject getReferencedSubject();

}

