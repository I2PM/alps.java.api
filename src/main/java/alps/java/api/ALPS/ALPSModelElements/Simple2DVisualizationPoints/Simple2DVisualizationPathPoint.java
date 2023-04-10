package alps.java.api.ALPS.ALPSModelElements.Simple2DVisualizationPoints;


import alps.java.api.ALPS.ALPSModelElements.Simple2DVisualizationPoint;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

public class Simple2DVisualizationPathPoint extends Simple2DVisualizationPoint implements ISimple2DVisualizationPathPoint {
    protected ISimple2DVisualizationPathPoint nextPoint, previousPoint;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "Simple2DVisualizationPathPoint";

    //TODO:Konstruktor überladen
    public Simple2DVisualizationPathPoint(String labelForID, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(labelForID, comment, additionalLabel, additionalAttribute);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public ISimple2DVisualizationPathPoint getNextPathPoint() {
        return nextPoint;
    }

    public ISimple2DVisualizationPathPoint getPreviousPathPoint() {
        return previousPoint;
    }

    /**
     * Sets the next path point in this chain
     * Automatically updates the previousPathPoint-parameter of the next point
     *
     * @param point the new next point
     */
    public void setNextPathPoint(ISimple2DVisualizationPathPoint point) {
        if (point == null || point.equals(getNextPathPoint())) return;
        this.nextPoint = point;
        point.setPreviousPathPoint(this);
    }

    /**
     * Sets the previous path point in this chain
     * Automatically updates the nextPathPoint-parameter of the previous point
     *
     * @param point the new previous point
     */
    public void setPreviousPathPoint(ISimple2DVisualizationPathPoint point) {
        if (point == null || point.equals(getPreviousPathPoint())) return;
        this.previousPoint = point;
        point.setNextPathPoint(this);
    }

    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new Simple2DVisualizationPathPoint();
    }

    protected Simple2DVisualizationPathPoint() {
    }
}


