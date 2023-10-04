package alps.java.api.util;

import alps.java.api.ALPS.ALPSModelElements.Simple2DVisualizationPoints.ISimple2DVisualizationPathPoint;

import java.util.List;

public interface IHasSimple2DVisualizationLine {
    /**
     * The relative positions of the simple 2D Vizualiations are given in % of page hight and width
     * No absolut values are given but a page ratio that defines whether it is a wide or tall format
     *
     * @return
     */
    double get2DPageRatio();

    void set2DPageRatio(double has2DPageRatio);

    double getRelative2DBeginX();

    void setRelative2DBeginX(double relative2DBeginX);

    double getRelative2DBeginY();

    void setRelative2DBeginY(double relative2DBeginY);

    double getRelative2DEndX();

    void setRelative2DEndX(double relative2DEndX);

    double getRelative2DEndY();

    void setRelative2DEndY(double relative2DEndY);

    List<ISimple2DVisualizationPathPoint> getSimple2DPathPoints();

    void addSimple2DPathPoint(ISimple2DVisualizationPathPoint point);
}