package alps.java.api.ALPS.ALPSModelElements.ALPSSBDComponets;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.State;
import alps.java.api.StandardPASS.PassProcessModelElements.IBehaviorDescribingComponent;
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
 * From abstract pass ont:
 * StateGroups/GroupStates and/or Checklist are model objects that can "contain" other SBD-Model elements as their Sub-Shapes in order to group them.
 */
public class GroupState extends State implements IGroupState {
    /**
     * Name of the class, needed for parsing
     */
    private final String CLASS_NAME = "GroupState";
    protected ICompatibilityDictionary<String, IBehaviorDescribingComponent> groupedComponents = new CompatibilityDictionary<String, IBehaviorDescribingComponent>();

    @Override
    public String getClassName() {
        return CLASS_NAME;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new GroupState();
    }

    public GroupState() {
    }

    /**
     * Constructor that creates a new fully specified instance of the group state class
     *
     * @param behavior
     * @param labelForId            a string describing this element which is used to generate the unique model component id
     * @param guardBehavior
     * @param functionSpecification
     * @param incomingTransition
     * @param outgoingTransition
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public GroupState(ISubjectBehavior behavior, String labelForId, IGuardBehavior guardBehavior,
                      IFunctionSpecification functionSpecification, Set<ITransition> incomingTransition, Set<ITransition> outgoingTransition,
                      String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForId, guardBehavior, functionSpecification, incomingTransition, outgoingTransition, comment, additionalLabel, additionalAttribute);
    }

    /**
     * Constructor that creates a new fully specified instance of the group state class
     *
     * @param behavior
     */
    public GroupState(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, null, null, null, null);
    }

    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }


    public boolean addGroupedComponent(IBehaviorDescribingComponent component) {
        if (component == null) {
            return false;
        }

        if (!groupedComponents.tryAdd(component.getModelComponentID(), component)) return false;

        publishElementAdded(component);
        component.register(this);
        addTriple(new IncompleteTriple(OWLTags.stdContains, component.getUriModelComponentID()));
        return true;
    }


    public void setGroupedComponents(Set<IBehaviorDescribingComponent> components, int removeCascadeDepth) {
        for (IBehaviorDescribingComponent component : this.getGroupedComponents().values()) {
            removeGroupedComponent(component.getModelComponentID(), removeCascadeDepth);
        }
        if (components == null) return;
        for (IBehaviorDescribingComponent component : components) {
            addGroupedComponent(component);
        }
    }

    public void setGroupedComponents(Set<IBehaviorDescribingComponent> components) {
        for (IBehaviorDescribingComponent component : this.getGroupedComponents().values()) {
            removeGroupedComponent(component.getModelComponentID(), 0);
        }
        if (components == null) return;
        for (IBehaviorDescribingComponent component : components) {
            addGroupedComponent(component);
        }
    }

    //TODO: out-Parameter
    public boolean removeGroupedComponent(String id, int removeCascadeDepth) {
        if (id == null) return false;
        IBehaviorDescribingComponent component = groupedComponents.get(id);

        if (component == null) return false;

        groupedComponents.remove(id);
        component.unregister(this, removeCascadeDepth);
        removeTriple(new IncompleteTriple(OWLTags.stdContains, component.getUriModelComponentID()));
        return true;
    }

    //TODO: out-Parameter
    public boolean removeGroupedComponent(String id) {
        if (id == null) return false;

        IBehaviorDescribingComponent component = groupedComponents.get(id);

        if (component == null) return false;

        groupedComponents.remove(id);
        component.unregister(this, 0);
        removeTriple(new IncompleteTriple(OWLTags.stdContains, component.getUriModelComponentID()));
        return true;
    }

    public Map<String, IBehaviorDescribingComponent> getGroupedComponents() {
        return new HashMap<String, IBehaviorDescribingComponent>(groupedComponents);
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.ccontains) && element instanceof IBehaviorDescribingComponent component) {
                addGroupedComponent(component);
                return true;

            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }
}
