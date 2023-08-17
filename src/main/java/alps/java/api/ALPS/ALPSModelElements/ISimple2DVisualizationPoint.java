package alps.java.api.ALPS.ALPSModelElements;

import alps.java.api.ALPS.IALPSModelElement;

/**
 * An interface to define points for a simple visual representation of model elements
 */
public interface ISimple2DVisualizationPoint extends IALPSModelElement {
    /**
     * Gets the relative x coordinate of the point
     *
     * @return the relative x coordinate
     */
    double getRelative2DPosX();

    /**
     * Gets the relative y coordinate of the point
     *
     * @return the relative y coordinate<
     */
    double getRelative2DPosY();

    /**
     * Sets the relative x coordinate of the point
     *
     * @param posx the relative x coordinate
     */
    void setRelative2DPosX(double posx);

    /**
     * Sets the relative y coordinate of the point
     *
     * @param posy the relative y coordinate
     */
    void setRelative2DPosY(double posy);
}