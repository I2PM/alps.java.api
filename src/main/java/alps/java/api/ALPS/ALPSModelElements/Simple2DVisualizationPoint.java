package alps.java.api.ALPS.ALPSModelElements;


import alps.java.api.ALPS.ALPSModelElement;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public class Simple2DVisualizationPoint extends ALPSModelElement implements ISimple2DVisualizationPoint
{

    private double has2DPageRatio;
    private double hasRelative2D_PosX;
    private double hasRelative2D_PosY;
    Logger logger = Logger.getLogger(Simple2DVisualizationPoint.class.getName());


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

    public double getRelative2DPosX()
    {
        return this.hasRelative2D_PosX;
    }

    public double getRelative2DPosY()
    {
        return this.hasRelative2D_PosY;
    }

    public void setRelative2DPosX(double posx)
    {
        if (posx >= 0 && posx <= 1)
        {
            this.hasRelative2D_PosX = posx;
        }
        else
        {
            if (posx < 0)
            {
                this.hasRelative2D_PosX = 0;
                logger.warning("Value for posx is smaller than 0. Setting it to 0.");
            }
            else if (posx > 1)
            {
                this.hasRelative2D_PosX = 1;
                logger.warning("Value for posx is larger than 1. Setting it to 1.");
            }
        }
    }

    public void setRelative2DPosY(double posy)
    {
        if (posy >= 0 && posy <= 1)
        {
            this.hasRelative2D_PosY = posy;
        }
        else
        {
            if (posy < 0)
            {
                this.hasRelative2D_PosY = 0;
                logger.warning("Value for posy is smaller than 0. Setting it to 0.");
            }
            else if (posy > 1)
            {
                this.hasRelative2D_PosY = 1;
                logger.warning("Value for posy is larger than 1. Setting it to 1.");
            }
        }

    }


    public double getHas2DPageRatio() { return has2DPageRatio; }
    public void setHas2DPageRatio(double has2DPageRatio){
        if (has2DPageRatio > 0)
        {
            this.has2DPageRatio = has2DPageRatio;
        }
        if (has2DPageRatio == 0)
        {
            this.has2DPageRatio = 1;
            logger.warning("found 2D page ratio of 0. This is impossible. changed it to 1");
        }
        else
        {
            this.has2DPageRatio = Math.abs(has2DPageRatio);
            logger.warning("found negative 2d page ratio. Changed it to positive value");
        }
    }
    @Override
    public IParseablePASSProcessModelElement getParsedInstance()
    {
        return new Simple2DVisualizationPoint();
    }

    public Simple2DVisualizationPoint() { }
@Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        //TODO: Bei alps.net.api ausgeklammert
    Locale customCulture = new Locale("en", "US");
    NumberFormat numberFormat = NumberFormat.getInstance(customLocale);
    numberFormat.setGroupingUsed(false); // Deaktiviere Gruppierung, z.B. Tausendertrennzeichen

    if (predicate.contains(OWLTags.abstrHas2DPageRatio)) {
        try {
            setHas2DPageRatio(numberFormat.parse(objectContent).doubleValue());
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    } else if (predicate.contains(OWLTags.abstrHasRelative2D_PosX)) {
        try {
            setRelative2DPosX(numberFormat.parse(objectContent).doubleValue());
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    } else if (predicate.contains(OWLTags.abstrHasRelative2D_PosY)) {
        try {
            setRelative2DPosY(numberFormat.parse(objectContent).doubleValue());
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
}
}
