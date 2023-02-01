package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;

import alps.java.api.ALPS.ALPSModelElements.IALPSSIDComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import org.apache.commons.lang3.tuple.Pair;

/**
 * An interface for abstract communication channels.
 * This also represents Uni- and BiDirectionalCommunicationChannels
 * The direction can be set using the {@link "setIsUniDirectional(bool)"} Method.
 */
public interface ICommunicationChannel extends IALPSSIDComponent
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

            /**
             * Sets the direction of the channel.
             * This might be either Uni- or BiDirectional.
             * In case of a UniDirectional, the CorrespondentA should be assumed as the Sender,
             * and Correspondent should be assumed as the Receiver.
             * @param isUniDirectional If true, this channel acts as UniDirectionalCommunicationChannel.<br></br>
             * If false, this channel acts as BiDirectionalCommunicationChannel.
             */
        void setIsUniDirectional(boolean isUniDirectional);

            /**
             * Returns whether this channel is a Bi- or UniDirectionalCommunicationChannel.
             * @return If true, the channel is UniDirectional, assuming the CorrespondentA as the Sender and CorrespondentB as Receiver.
             * If false, the channel is BiDirectional
             */
        boolean isUniDirectional();
        }