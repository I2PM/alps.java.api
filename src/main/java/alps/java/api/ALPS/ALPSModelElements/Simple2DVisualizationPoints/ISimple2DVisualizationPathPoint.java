package alps.java.api.ALPS.ALPSModelElements.Simple2DVisualizationPoints;

import alps.java.api.ALPS.ALPSModelElements.ISimple2DVisualizationPoint;

/**
 * An interface to define paths (consisting of points) for a simple visual representation of model elements
 * A path is a double linked list of path points
 */
public interface ISimple2DVisualizationPathPoint extends ISimple2DVisualizationPoint {
    /**
     * Sets the next path point in this chain
     *
     * @param point the new next point
     */
    void setNextPathPoint(ISimple2DVisualizationPathPoint point);

    /**
     * Sets the previous path point in this chain
     *
     * @param point the new previous point
     */
    void setPreviousPathPoint(ISimple2DVisualizationPathPoint point);

    /**
     * @return The next point in the path
     */
    ISimple2DVisualizationPathPoint getNextPathPoint();

    /**
     * @return The previous point in the path
     */
    ISimple2DVisualizationPathPoint getPreviousPathPoint();
}
