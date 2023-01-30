package alps.java.api.ALPS.ALPSModelElements.ALPSSBDComponets;

import alps.java.api.ALPS.ALPSModelElements.IALPSSBDComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.IBehaviorDescribingComponent;

import java.util.Map;
import java.util.Set;

public interface IGroupState extends IALPSSBDComponent, IState
        {
            /**
             * Adds a component to the group of components grouped by this state
             * @param component the new component
             * @return whether the process of adding was successful
             */
        boolean addGroupedComponent(IBehaviorDescribingComponent component);

            /**
             * Overrides the set of grouped components
             * @param components the new components
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
        void setGroupedComponents(Set<IBehaviorDescribingComponent> components, int removeCascadeDepth);

            /**
             * Removes a component from the group
             * @param id
             * @param removeCascadeDepth
             * @return
             */
        boolean removeGroupedComponent(String id, int removeCascadeDepth);

            /**
             *
             * @return A dictionary of grouped states, mapped with their id
             */

            Map<String, IBehaviorDescribingComponent> getGroupedComponents();
        }