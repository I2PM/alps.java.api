package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;


import alps.java.api.StandardPASS.PassProcessModelElements.IInteractionDescribingComponent;

/**
 * Interface to the message exchange class
 * Interface to the message exchange class
 * Note that message exchanges are just a combination of a receiver, a sender, and a message (spec)
 * In a visual modeling approach often message exchanges are grouped individually
 * You can find these in MessageExchangeList-Objects. Those also contain the rudamentary information in regards to
 * 2d routing of the accordings
 */
public interface IMessageExchange extends IInteractionDescribingComponent
        {
            /**
             * Method that sets the message specification attribute of the instance
             * @param messageSpecification the type of message
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
        void setMessageType(IMessageSpecification messageSpecification, int removeCascadeDepth);

            /**
             * Method that sets the message specification attribute of the instance
             * @param messageSpecification the type of message
             */
            void setMessageType(IMessageSpecification messageSpecification);


            /**
             * Method that returns the message specification attribute of the instance
             * @return The message specification attribute of the instance
             */
        IMessageSpecification getMessageType();

            /**
             * Method that sets the receiver attribute of the instance
             * @param receiver
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
        void setReceiver(ISubject receiver, int removeCascadeDepth);

            /**
             * Method that sets the receiver attribute of the instance
             * @param receiver
             */
            void setReceiver(ISubject receiver);

            /**
             * Method that returns the receiver attribute of the instance
             * @return The receiver attribute of the instance
             */
        ISubject getReceiver();

            /**
             * Method that sets the sender attribute of the instance
             * @param sender
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
        void setSender(ISubject sender, int removeCascadeDepth);

            /**
             * Method that sets the sender attribute of the instance
             * @param sender
             */
            void setSender(ISubject sender);

            /**
             * Method that returns the sender attribute of the instance
             * @return The sender attribute of the instance
             */
        ISubject getSender();

        }

