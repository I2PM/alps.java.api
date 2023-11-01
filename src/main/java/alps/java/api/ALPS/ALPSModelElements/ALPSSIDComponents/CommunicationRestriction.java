package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;

import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponent;
import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.ALPS.ALPSModelElements.Simple2DVisualizationPoints.ISimple2DVisualizationPathPoint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;
import alps.java.api.util.priv.Tuple;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * From abstract pass ont:
 * Communication Restriction is a concept on an abstract layer.
 * A Communication Restriction defines that no communication is allowed between the defined subjects
 */
public class CommunicationRestriction extends ALPSSIDComponent implements ICommunicationRestriction {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "CommunicationRestriction";
    private ISubject correspondentA;
    private ISubject correspondentB;
    private double has2DPageRatio;
    private double hasRelative2D_BeginX;
    private double hasRelative2D_BeginY;
    private double hasRelative2D_EndX;
    private double hasRelative2D_EndY;
    Logger Log = Logger.getLogger("CommunicationRestriction");
    private List<ISimple2DVisualizationPathPoint> pathPoints = new ArrayList<ISimple2DVisualizationPathPoint>();

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new CommunicationRestriction();
    }

    public CommunicationRestriction() {
    }

    public CommunicationRestriction(IModelLayer layer, String labelForID,
                                    ISubject correspondentA, ISubject correspondentB,
                                    String comment, String additionalLabel,
                                    List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, comment, additionalLabel, additionalAttribute);
        setCorrespondentA(correspondentA);
        setCorrespondentB(correspondentB);
    }

    public CommunicationRestriction(IModelLayer layer) {
        super(layer, null, null, null, null);
        setCorrespondentA(null);
        setCorrespondentB(null);
    }

    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element instanceof ISubject subj) {
            if (predicate.contains(OWLTags.hasCorrespondent)) {
                if (correspondentA == null) {
                    setCorrespondentA(subj);
                } else if (correspondentB == null) {
                    setCorrespondentB(subj);
                }
            }
        } else if (element instanceof ISimple2DVisualizationPathPoint point) {
            //Console.WriteLine(this.getModelComponentID() + ": PathPoint:" + point.getModelComponentID());
            if (this.pathPoints == null) this.pathPoints = new ArrayList<ISimple2DVisualizationPathPoint>();

            this.pathPoints.add(point);
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    public void setCorrespondents(ISubject correspondentA, ISubject correspondentB, int removeCascadeDepth) {
        setCorrespondentA(correspondentA, removeCascadeDepth);
        setCorrespondentB(correspondentB, removeCascadeDepth);
    }

    public void setCorrespondents(ISubject correspondentA, ISubject correspondentB) {
        setCorrespondentA(correspondentA, 0);
        setCorrespondentB(correspondentB, 0);
    }

    public void setCorrespondentA(ISubject correspondentA, int removeCascadeDepth) {
        ISubject oldCorrespondentA = this.correspondentA;
        // Might set it to null
        this.correspondentA = correspondentA;

        if (oldCorrespondentA != null) {
            if (oldCorrespondentA.equals(correspondentA)) return;
            oldCorrespondentA.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasCorrespondent, correspondentA.getUriModelComponentID()));
        }
        if (!(correspondentA == null)) {
            publishElementAdded(correspondentA);
            correspondentA.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasCorrespondent, correspondentA.getUriModelComponentID()));
        }
    }

    public void setCorrespondentA(ISubject correspondentA) {
        ISubject oldCorrespondentA = this.correspondentA;
        // Might set it to null
        this.correspondentA = correspondentA;

        if (oldCorrespondentA != null) {
            if (oldCorrespondentA.equals(correspondentA)) return;
            oldCorrespondentA.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdHasCorrespondent, correspondentA.getUriModelComponentID()));
        }
        if (!(correspondentA == null)) {
            publishElementAdded(correspondentA);
            correspondentA.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasCorrespondent, correspondentA.getUriModelComponentID()));
        }
    }

    public void setCorrespondentB(ISubject correspondentB, int removeCascadeDepth) {
        ISubject oldCorrespondentB = this.correspondentB;
        // Might set it to null
        this.correspondentB = correspondentB;

        if (oldCorrespondentB != null) {
            if (oldCorrespondentB.equals(correspondentB)) return;
            oldCorrespondentB.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasCorrespondent, correspondentB.getUriModelComponentID()));
        }
        if (!(correspondentB == null)) {
            publishElementAdded(correspondentB);
            correspondentB.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasCorrespondent, correspondentB.getUriModelComponentID()));
        }
    }

    public void setCorrespondentB(ISubject correspondentB) {
        ISubject oldCorrespondentB = this.correspondentB;
        // Might set it to null
        this.correspondentB = correspondentB;

        if (oldCorrespondentB != null) {
            if (oldCorrespondentB.equals(correspondentB)) return;
            oldCorrespondentB.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdHasCorrespondent, correspondentB.getUriModelComponentID()));
        }
        if (!(correspondentB == null)) {
            publishElementAdded(correspondentB);
            correspondentB.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasCorrespondent, correspondentB.getUriModelComponentID()));
        }
    }

    public ISubject getCorrespondentA() {
        return correspondentA;
    }

    public ISubject getCorrespondentB() {
        return correspondentB;
    }

    public Pair<ISubject, ISubject> getCorrespondents() {
        return new ImmutablePair<>(correspondentA, correspondentB);
    }

    public double get2DPageRatio() {
        return has2DPageRatio;
    }

    public void set2DPageRatio(double has2DPageRatio) {
        if (has2DPageRatio > 0) {
            this.has2DPageRatio = has2DPageRatio;
        }
        if (has2DPageRatio == 0) {
            this.has2DPageRatio = 1;
            Log.warning("found 2D page ratio of 0. This is impossible. changed it to 1");
        } else {
            this.has2DPageRatio = Math.abs(has2DPageRatio);
            Log.warning("found negative 2d page ratio. Changed it to positive value");
        }
    }

    public double getRelative2DBeginX() {
        return hasRelative2D_BeginX;
    }

    public void setRelative2DBeginX(double relative2DBeginX) {
        if (relative2DBeginX >= 0 && relative2DBeginX <= 1) {
            hasRelative2D_BeginX = relative2DBeginX;
        } else {
            if (relative2DBeginX < 0) {
                hasRelative2D_BeginX = 0;
                Log.warning("Value for relative2DBeginX is smaller than 0. Setting it to 0.");
            } else if (relative2DBeginX > 1) {
                hasRelative2D_BeginX = 1;
                Log.warning("Value for relative2DBeginX is larger than 1. Setting it to 1.");
            }
        }

    }

    public double getRelative2DBeginY() {
        return hasRelative2D_BeginY;
    }

    public void setRelative2DBeginY(double relative2DBeginY) {
        if (relative2DBeginY >= 0 && relative2DBeginY <= 1) {
            hasRelative2D_BeginY = relative2DBeginY;
        } else {
            if (relative2DBeginY < 0) {
                hasRelative2D_BeginY = 0;
                Log.warning("Value for relative2DBeginY is smaller than 0. Setting it to 0.");
            } else if (relative2DBeginY > 1) {
                hasRelative2D_BeginY = 1;
                Log.warning("Value for relative2DBeginY is larger than 1. Setting it to 1.");
            }
        }

    }

    public double getRelative2DEndX() {
        return hasRelative2D_EndX;
    }

    public void setRelative2DEndX(double relative2DEndX) {
        if (relative2DEndX >= 0 && relative2DEndX <= 1) {
            hasRelative2D_EndX = relative2DEndX;
        } else {
            if (relative2DEndX < 0) {
                hasRelative2D_EndX = 0;
                Log.warning("Value for relative2DEndX is smaller than 0. Setting it to 0.");
            } else if (relative2DEndX > 1) {
                hasRelative2D_EndX = 1;
                Log.warning("Value for relative2DEndX is larger than 1. Setting it to 1.");
            }
        }

    }

    public double getRelative2DEndY() {
        return hasRelative2D_EndY;
    }

    public void setRelative2DEndY(double relative2DEndY) {
        if (relative2DEndY >= 0 && relative2DEndY <= 1) {
            hasRelative2D_EndY = relative2DEndY;
        } else {
            if (relative2DEndY < 0) {
                hasRelative2D_EndY = 0;
                Log.warning("Value for relative2DEndY is smaller than 0. Setting it to 0.");
            } else if (relative2DEndY > 1) {
                hasRelative2D_EndY = 1;
                Log.warning("Value for relative2DEndY is larger than 1. Setting it to 1.");
            }
        }
    }

    public List<ISimple2DVisualizationPathPoint> getSimple2DPathPoints() {
        return this.pathPoints;
    }

    public void addSimple2DPathPoint(ISimple2DVisualizationPathPoint point) {
        this.pathPoints.add(point);
    }
}
