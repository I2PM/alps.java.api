package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications.IReceiveFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;

/**
 * Interface of the receive state class
 */
public interface IReceiveState extends IStandardPASSState
        {
                /**
                 * Method that sets the receive function attribute of the instance
                 * @param specification the function specification
                 * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
                 */
        void setFunctionSpecification(IFunctionSpecification specification, int removeCascadeDepth);

                /**
                 * Method that sets the receive function attribute of the instance
                 * @param specification the function specification
                 */
                void setFunctionSpecification(IFunctionSpecification specification);

                /**
                 * Method that returns the receive function attribute of the instance
                 * @return The receive function attribute of the instance
                 */
        IReceiveFunction getFunctionSpecification();

        }