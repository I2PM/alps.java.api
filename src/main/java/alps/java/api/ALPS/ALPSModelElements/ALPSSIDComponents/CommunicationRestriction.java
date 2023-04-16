package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;

import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponent;
import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;
import alps.java.api.util.priv.Tuple;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

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

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new CommunicationRestriction();
    }

    protected CommunicationRestriction() {
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
}
