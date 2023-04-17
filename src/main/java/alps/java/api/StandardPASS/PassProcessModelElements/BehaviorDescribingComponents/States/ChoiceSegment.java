package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.State;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.CompatibilityDictionary;
import alps.java.api.util.ICompatibilityDictionary;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that represents a Choice Segment
 */
public class ChoiceSegment extends State implements IChoiceSegment {
    protected ICompatibilityDictionary<String, IChoiceSegmentPath> choiceSegmentPathDict = new CompatibilityDictionary<String, IChoiceSegmentPath>();

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ChoiceSegment";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ChoiceSegment();
    }

    protected ChoiceSegment() {
    }

    public ChoiceSegment(ISubjectBehavior behavior, String labelForID, IGuardBehavior guardBehavior,
                         IFunctionSpecification functionSpecification, Set<ITransition> incomingTransition, Set<ITransition> outgoingTransition,
                         Set<IChoiceSegmentPath> choiceSegmentPathList, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, guardBehavior, functionSpecification, incomingTransition, outgoingTransition, comment, additionalLabel, additionalAttribute);
        setContainsChoiceSegmentPaths(choiceSegmentPathList);
    }

    public ChoiceSegment(ISubjectBehavior behavior) {
        super(behavior, null,null,null,null,null,null,null,null);
        setContainsChoiceSegmentPaths(null);
    }


    public void setContainsChoiceSegmentPaths(Set<IChoiceSegmentPath> choiceSegmentPaths, int removeCascadeDepth) {
        for (IChoiceSegmentPath path : getChoiceSegmentPaths().values()) {
            removeChoiceSegmentPath(path.getModelComponentID(), removeCascadeDepth);
        }
        if (choiceSegmentPaths == null) return;
        for (IChoiceSegmentPath path : choiceSegmentPaths) {
            addContainsChoiceSegmentPath(path);
        }
    }

    public void setContainsChoiceSegmentPaths(Set<IChoiceSegmentPath> choiceSegmentPaths) {
        for (IChoiceSegmentPath path : getChoiceSegmentPaths().values()) {
            removeChoiceSegmentPath(path.getModelComponentID(), 0);
        }
        if (choiceSegmentPaths == null) return;
        for (IChoiceSegmentPath path : choiceSegmentPaths) {
            addContainsChoiceSegmentPath(path);
        }
    }

    public void addContainsChoiceSegmentPath(IChoiceSegmentPath choiceSegmentPath) {
        if (choiceSegmentPath == null) {
            return;
        }
        if (choiceSegmentPathDict.tryAdd(choiceSegmentPath.getModelComponentID(), choiceSegmentPath)) {
            publishElementAdded(choiceSegmentPath);
            choiceSegmentPath.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdContains, choiceSegmentPath.getUriModelComponentID()));
        }

    }


    public Map<String, IChoiceSegmentPath> getChoiceSegmentPaths() {
        return new HashMap<String, IChoiceSegmentPath>(choiceSegmentPathDict);
    }

    //TODO: Out-Parameter
    public void removeChoiceSegmentPath(String id, int removeCascadeDepth) {
        if (id == null) return;
        if (choiceSegmentPathDict.getOrDefault(id, out IChoiceSegmentPath path)) {
            choiceSegmentPathDict.remove(id);
            path.unregister(this, removeCascadeDepth);
            addTriple(new IncompleteTriple(OWLTags.stdContains, path.getUriModelComponentID()));
        }
    }
    //TODO: Out-Parameter
    public void removeChoiceSegmentPath(String id) {
        if (id == null) return;
        if (choiceSegmentPathDict.getOrDefault(id, out IChoiceSegmentPath path)) {
            choiceSegmentPathDict.remove(id);
            path.unregister(this, 0);
            addTriple(new IncompleteTriple(OWLTags.stdContains, path.getUriModelComponentID()));
        }
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.ccontains) && element instanceof IChoiceSegmentPath path) {
                addContainsChoiceSegmentPath(path);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IChoiceSegmentPath component : getChoiceSegmentPaths().values())
            baseElements.add(component);
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IChoiceSegmentPath path)
                removeChoiceSegmentPath(path.getModelComponentID(), removeCascadeDepth);
        }
    }



    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IChoiceSegmentPath path)
                removeChoiceSegmentPath(path.getModelComponentID(), 0);
        }
    }

}

