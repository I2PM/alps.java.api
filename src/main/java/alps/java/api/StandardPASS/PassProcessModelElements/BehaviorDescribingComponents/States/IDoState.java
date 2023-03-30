package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications.IDoFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunctions.IDataMappingIncomingToLocal;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunctions.IDataMappingLocalToOutgoing;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the DoState class
 */

public interface IDoState extends IStandardPASSState
        {
            /**
             * Overrides the functions that define how incoming data will be mapped to local data
             * @param dataMappingIncomingToLocal the set of functions that define how incoming data will be mapped to local data
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
        void setDataMappingFunctionsIncomingToLocal(Set<IDataMappingIncomingToLocal> dataMappingIncomingToLocal, int removeCascadeDepth);

            /**
             * Overrides the functions that define how incoming data will be mapped to local data
             * @param dataMappingIncomingToLocal the set of functions that define how incoming data will be mapped to local data
             */
            void setDataMappingFunctionsIncomingToLocal(Set<IDataMappingIncomingToLocal> dataMappingIncomingToLocal);

            /**
             * Adds a new function that defines how incoming data will be mapped to local data
             * @param dataMappingIncomingToLocal the new mapping function
             */
        void addDataMappingFunctionIncomingToLocal(IDataMappingIncomingToLocal dataMappingIncomingToLocal);

            /**
             * Gets the set of functions that define how incoming data will be mapped to local data
             * @return
             */
        Map<String, IDataMappingIncomingToLocal> getDataMappingFunctionsIncomingToLocal();

            /**
             * Removes a data mapping function (outgoing to local)
             * @param id the id of the function
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
        void removeDataMappingFunctionIncomingToLocal(String id, int removeCascadeDepth);

            /**
             * Removes a data mapping function (outgoing to local)
             * @param id the id of the function
             */
            void removeDataMappingFunctionIncomingToLocal(String id);


            /**
             * Overrides the functions that define how local data will be mapped to outgoing data
             * @param dataMappingIncomingToLocal the set of functions that define how local data will be mapped to outgoing data
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
        void setDataMappingFunctionsLocalToOutgoing(Set<IDataMappingLocalToOutgoing> dataMappingIncomingToLocal, int removeCascadeDepth);

            /**
             * Overrides the functions that define how local data will be mapped to outgoing data
             * @param dataMappingIncomingToLocal the set of functions that define how local data will be mapped to outgoing data
             */
            void setDataMappingFunctionsLocalToOutgoing(Set<IDataMappingLocalToOutgoing> dataMappingIncomingToLocal);

            /**
             * Adds a new function that defines how local data will be mapped to outgoing data
             * @param dataMappingIncomingToLocal the new function
             */
        void addDataMappingFunctionLocalToOutgoing(IDataMappingLocalToOutgoing dataMappingIncomingToLocal);

            /**
             * Gets the set of functions that define how local data will be mapped to outgoing data
             * @return the set of functions that define how incoming data will be mapped to local data
             */

        Map<String, IDataMappingLocalToOutgoing> getDataMappingFunctionsLocalToOutgoing();

            /**
             * Removes a data mapping function (local to outgoing)
             * @param id >the id of the function
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
        void removeDataMappingFunctionLocalToOutgoing(String id, int removeCascadeDepth);

            /**
             * Removes a data mapping function (local to outgoing)
             * @param id >the id of the function
             */
            void removeDataMappingFunctionLocalToOutgoing(String id);

            /**
             * Sets the function specification
             * @param specification the function specification
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
         void setFunctionSpecification(IFunctionSpecification specification, int removeCascadeDepth);

            /**
             * Sets the function specification
             * @param specification the function specification
             */
            void setFunctionSpecification(IFunctionSpecification specification);

            /**
             * Gets the function specification
             * @return the function specification
             */
            IDoFunction getFunctionSpecification();

        }