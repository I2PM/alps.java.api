package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;

import alps.java.api.ALPS.ALPSModelElements.IALPSSIDComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;

import java.util.Map;
import java.util.Set;

/**
 * Interface for a subject extension
 */
public interface ISubjectExtension extends IALPSSIDComponent, ISubject {
    /**
     * Adds an extension behavior to the extension subject
     *
     * @param behavior the new behavior
     */
    public void addExtensionBehavior(ISubjectBehavior behavior);

    /**
     * @return A set of extension behaviors that belong to this subject extension
     */
    public Map<String, ISubjectBehavior> getExtensionBehaviors();

    /**
     * Overrides the set of behaviors that belong to this extension
     *
     * @param behaviors          the new behaviors
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setExtensionBehaviors(Set<ISubjectBehavior> behaviors, int removeCascadeDepth);

    /**
     * Overrides the set of behaviors that belong to this extension
     *
     * @param behaviors the new behaviors
     */
    public void setExtensionBehaviors(Set<ISubjectBehavior> behaviors);

    /**
     * Removes a behavior from the set of behaviors belonging to this subject extension
     *
     * @param id                 the id of the behavior
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void removeExtensionBehavior(String id, int removeCascadeDepth);

    /**
     * Removes a behavior from the set of behaviors belonging to this subject extension
     *
     * @param id the id of the behavior
     */

    public void removeExtensionBehavior(String id);

    /**
     * Sets the subject that is extended by this extension
     *
     * @param subject            the extended subject
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setExtendedSubject(ISubject subject, int removeCascadeDepth);

    /**
     * Sets the subject that is extended by this extension
     *
     * @param subject the extended subject
     */
    public void setExtendedSubject(ISubject subject);


    /**
     * @return The subject that is extended by this extension
     */
    public ISubject getExtendedSubject();
}