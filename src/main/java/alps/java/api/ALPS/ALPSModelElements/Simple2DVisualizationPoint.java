package alps.java.api.ALPS.ALPSModelElements;


import alps.java.api.ALPS.ALPSModelElement;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

public class Simple2DVisualizationPoint extends ALPSModelElement implements ISimple2DVisualizationPoint
{

    protected double posx, posy;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "Simple2DVisualizationPoint";

    public Simple2DVisualizationPoint(String labelForID, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute){
        super(labelForID, comment, additionalLabel, additionalAttribute); }

    public String getClassName()
    {
        return className;
    }
    @Override
    protected String getExportTag()
    {
        return OWLTags.abstr;
    }

    public double getRelative2D_PosX()
    {
        return posx;
    }

    public double getRelative2D_PosY()
    {
        return posy;
    }

    public void setRelative2D_PosX(double posx)
    {
        this.posx = posx;
    }

    public void setRelative2D_PosY(double posy)
    {
        this.posy = posy;
    }
    @Override
    public IParseablePASSProcessModelElement getParsedInstance()
    {
        return new Simple2DVisualizationPoint();
    }

    protected Simple2DVisualizationPoint() { }
}