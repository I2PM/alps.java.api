package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the choice segment class
 */
public interface IChoiceSegment extends IState
        {
            /**
             * Overrides the set of {@link IChoiceSegmentPath} that are contained by the segment
             * @param choiceSegmentPaths the new segment paths
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
        void setContainsChoiceSegmentPaths(Set<IChoiceSegmentPath> choiceSegmentPaths, int removeCascadeDepth);

            /**
             * Overrides the set of {@link IChoiceSegmentPath} that are contained by the segment
             * @param choiceSegmentPaths the new segment paths
             */
            void setContainsChoiceSegmentPaths(Set<IChoiceSegmentPath> choiceSegmentPaths);

            /**
             * Returns all contained paths
             * @return Returns a set of {@link IChoiceSegmentPath} that are contained by the segment
             */
        Map<String, IChoiceSegmentPath> getChoiceSegmentPaths();

            /**
             * Adds a {@link IChoiceSegmentPath} that is contained by the segment
             * @param choiceSegmentPath the new path
             */
        void addContainsChoiceSegmentPath(IChoiceSegmentPath choiceSegmentPath);

            /**
             * Removes a {@link IChoiceSegmentPath} that is contained by the segment.
             * @param id The ModelComponentID of the path.
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
        void removeChoiceSegmentPath(String id, int removeCascadeDepth);

            /**
             * Removes a {@link IChoiceSegmentPath} that is contained by the segment.
             * @param id The ModelComponentID of the path.
             */
            void removeChoiceSegmentPath(String id);


        }