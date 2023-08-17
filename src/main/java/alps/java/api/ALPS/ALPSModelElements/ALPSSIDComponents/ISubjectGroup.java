package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;

import java.util.Map;
import java.util.Set;

public interface ISubjectGroup {
    /**
     * Returns all contained Subjects.
     * @return all contained Subjects
     */
    Map<String, ISubject> getContainedSubjects();

    /**
     * Sets a set of Subjects as contained subjects for this Group subject, overwriting old subjects.
     * @param subjects The set of Subjects
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setSubjects(Set<ISubject> subjects, int removeCascadeDepth);

    /**
     * Sets a set of Subjects as contained subjects for this Group subject, overwriting old subjects.
     * @param subjects The set of Subjects
     */
    void setSubjects(Set<ISubject> subjects);

    /**
     * Removes an Subject from the list of contained subjects
     * @param id the id of the subject
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     * @return a boolean indicating whether the process of removal was a success
     */
    boolean removeSubject(String id, int removeCascadeDepth);

    /**
     * Removes an Subject from the list of contained subjects
     * @param id the id of the subject
     * @return a boolean indicating whether the process of removal was a success
     */
    boolean removeSubject(String id);

}
