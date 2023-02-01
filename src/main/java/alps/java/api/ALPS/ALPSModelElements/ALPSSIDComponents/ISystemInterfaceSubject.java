package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IInterfaceSubject;

import java.util.Map;
import java.util.Set;

public interface ISystemInterfaceSubject extends IInterfaceSubject
        {
            /**
             * Returns all contained InterfaceSubjects.
             * @return all contained InterfaceSubjects
             */
        Map<String, IInterfaceSubject> getInterfaceSubjects();

            /**
             * Adds an InterfaceSubject to the list of contained InterfaceSubjects.
             * @param subject The new InterfaceSubject
             * @return a bool indicating whether the process of adding was a success
             */
        boolean addInterfaceSubject(IInterfaceSubject subject);

            /**
             * Sets a set of InterfaceSubjects as contained subjects for this subject, overwriting old subjects.
             * @param subjects The set of InterfaceSubjects
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
        void setInterfaceSubjects(Set<IInterfaceSubject> subjects, int removeCascadeDepth);

        void setInterfaceSubjects(Set<IInterfaceSubject> subjects);

            /**
             * Removes an InterfaceSubject from the list of contained subjects
             * @param id the id of the subject
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             * @return a bool indicating whether the process of removal was a success
             */
        boolean removeInterfaceSubject(String id, int removeCascadeDepth);
        boolean removeInterfaceSubject(String id);
        }