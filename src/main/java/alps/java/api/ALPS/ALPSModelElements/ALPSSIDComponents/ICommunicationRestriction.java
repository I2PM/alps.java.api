package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;

import alps.java.api.ALPS.ALPSModelElements.IALPSSIDComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import org.apache.commons.lang3.tuple.Pair;

public interface ICommunicationRestriction extends IALPSSIDComponent
        {

        void setCorrespondents(ISubject correspondentA, ISubject correspondentB, int removeCascadeDepth);
        void setCorrespondents(ISubject correspondentA, ISubject correspondentB);

        void setCorrespondentA(ISubject correspondentA, int removeCascadeDepth);
        void setCorrespondentA(ISubject correspondentA);

        void setCorrespondentB(ISubject correspondentB, int removeCascadeDepth);
        void setCorrespondentB(ISubject correspondentB);

        ISubject getCorrespondentA();

        ISubject getCorrespondentB();

        Pair<ISubject, ISubject> getCorrespondents();
        }