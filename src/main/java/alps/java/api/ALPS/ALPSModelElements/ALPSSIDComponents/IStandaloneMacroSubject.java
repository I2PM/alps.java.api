package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;

import alps.java.api.ALPS.ALPSModelElements.IALPSSIDComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IMacroBehavior;

public interface IStandaloneMacroSubject extends IALPSSIDComponent, ISubject
        {
            /**
             * Sets a behavior for the current subject.
             * @param behavior The behavior
             * @param removeCascadeDepth
             */
        void setBehavior(IMacroBehavior behavior, int removeCascadeDepth);
        void setBehavior(IMacroBehavior behavior);

            /**
             * Get the behavior contained by this subject
             * @return A macro behaviors
             */
        IMacroBehavior getBehavior();
        }