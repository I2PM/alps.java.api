package alps.java.api.util;

/**
 * Objects that have a simple 2D visualization may implement this interface to indicate their
 * coordinates in a visual 2D environment
 */
public interface IHasSimple2DVisualizationBox {
    /**
     * The relative positions of the simple 2D visualizations are given in % of page height and width
     * No absolute values are given but a page ratio that defines whether it is a wide or tall format
     *
     * @return
     */
    double get2DPageRatio();

    void set2DPageRatio(double value2DPageRatio);

    /**
     * The relative height of 2D object (depending on the page ratio)
     *
     * @return
     */
    double getRelative2DHeight();

    void setRelative2DHeight(double relative2DHeight);

    /**
     * The relative width of 2D object (depending on the page ratio)
     *
     * @return
     */
    double getRelative2DWidth();

    void setRelative2DWidth(double relative2DWidth);

    /**
     * The relative width of 2D object (depending on the page ratio)
     *
     * @return
     */
    double getRelative2DPosX();

    void setRelative2DPosX(double relative2DPosX);

    /**
     * The relative position (Y) of 2D object (depending on the page ratio)
     *
     * @return
     */
    double getRelative2DPosY();

    void setRelative2DPosY(double relative2DPosY);
}