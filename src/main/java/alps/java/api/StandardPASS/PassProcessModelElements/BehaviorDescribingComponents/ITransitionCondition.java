package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.IBehaviorDescribingComponent;

/**
 * Interface of the transition condition class
 */
public interface ITransitionCondition extends IBehaviorDescribingComponent
        {
            /**
             * Method that sets the tool specific definition attribute of the instance
             * @param toolSpecificDefintion The tool specific definition
             */
        void setToolSpecificDefinition(String toolSpecificDefintion);

            /**
             * Method that returns the tool specific definition attribute of the instance
             * @return The tool specific definition attribute of the instance
             */
        String getToolSpecificDefinition();
        }
