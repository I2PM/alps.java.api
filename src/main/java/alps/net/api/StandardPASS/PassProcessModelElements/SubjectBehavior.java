package alps.net.api.StandardPASS.PassProcessModelElements;

import alps.net.api.ALPS.*;
import alps.net.api.ALPS.ALPSModelElements.IModelLayer;
import alps.net.api.FunctionalityCapsules.*;
import alps.net.api.StandardPASS.IPASSProcessModelElement;
import alps.net.api.StandardPASS.PASSProcessModelElement;
import alps.net.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.net.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.net.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IFullySpecifiedSubject;
import alps.net.api.parsing.*;
import alps.net.api.src.*;
import alps.net.api.util.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/// <summary>
    /// Class that represents a subject behavior
    /// </summary>
    public class SubjectBehavior extends PASSProcessModelElement implements ISubjectBehavior
            {
        /// <summary>
        /// Contains all components held by the subject behavior
        /// </summary>
        protected ICompatibilityDictionary<String, IBehaviorDescribingComponent> behaviorDescriptionComponents = new CompatibilityDictionary<String, IBehaviorDescribingComponent>();

        // The capsules are used to externalize functionality that is used across multiple classes redundandly
        protected final IImplementsFunctionalityCapsule<ISubjectBehavior> implCapsule;
        protected final IExtendsFunctionalityCapsule<ISubjectBehavior> extendsCapsule;

        protected IState initialStateOfBehavior;
        protected int priorityNumber;
        protected ISubject subj;
        protected IModelLayer layer;

        /// <summary>
        /// Name of the class, needed for parsing
        /// </summary>
        private final String className = "SubjectBehavior";
        @Override
        public String getClassName()
                {
                return className;
                }
        @Override
        public IParseablePASSProcessModelElement getParsedInstance()
                {
                return new SubjectBehavior();
                }

        protected SubjectBehavior() {
                implCapsule = new ImplementsFunctionalityCapsule<ISubjectBehavior>(this);
                extendsCapsule = new ExtendsFunctionalityCapsule<ISubjectBehavior>(this);
                }

        /// <summary>
        /// Creates a new SubjectBehavior from scratch
        /// </summary>
        public SubjectBehavior(IModelLayer layer, String labelForID, ISubject subject,
                               Set<IBehaviorDescribingComponent> behaviorDescribingComponents, IState initialStateOfBehavior,
                               int priorityNumber, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute){
                 super(labelForID, comment, additionalLabel, additionalAttribute);
                implCapsule = new ImplementsFunctionalityCapsule<ISubjectBehavior>(this);
                extendsCapsule = new ExtendsFunctionalityCapsule<ISubjectBehavior>(this);
                setContainedBy(layer);
                setSubject(subject);
                setBehaviorDescribingComponents(behaviorDescribingComponents);
                setInitialState(initialStateOfBehavior);
                setPriorityNumber(priorityNumber);
                }



        public boolean getContainedBy(IModelLayer layer)
                {
                layer = this.layer;
                return layer != null;
                }

        public void setContainedBy(IModelLayer layer)
                {
                if (layer == null) return;
                if (layer.equals(this.layer)) return;
                this.layer = layer;
                layer.addElement(this);
                }
        @Override
        protected Map<String, IParseablePASSProcessModelElement> getDictionaryOfAllAvailableElements()
                {
                if (layer == null) return null;
                IPASSProcessModel model = layer.getContainedBy();
                if (model == null) return null;
                Map<String, IPASSProcessModelElement> allElements = model.getAllElements();
                Map<String, IParseablePASSProcessModelElement> allParseableElements = new HashMap<String, IParseablePASSProcessModelElement>();
                for (Map.Entry<String, IPASSProcessModelElement> pair : allElements.entrySet()) {
                    if (pair.getValue() instanceof IParseablePASSProcessModelElement parseable) {
                        allParseableElements.put(pair.getKey(), parseable);
                        }
                    }
                    return allParseableElements;

                }




        // ######################## BehaviorDescribingComponent()s methods ########################


        public boolean addBehaviorDescribingComponent(IBehaviorDescribingComponent component)
                {
                if (component == null) { return false; }
                if (behaviorDescriptionComponents.tryAdd(component.getModelComponentID(), component))
                {
                publishElementAdded(component);
                if (component instanceof IContainableElement) {
                    IContainableElement<ISubjectBehavior> containable = (IContainableElement<ISubjectBehavior>) component;
                    containable.setContainedBy(this);
                }
                component.register(this);
                addTriple(new IncompleteTriple(OWLTags.stdContains, component.getUriModelComponentID()));
                return true;
            }
                return false;
            }


        public void setBehaviorDescribingComponents(Set<IBehaviorDescribingComponent> components, int removeCascadeDepth)
                {
                for(IBehaviorDescribingComponent component: this.getBehaviorDescribingComponents().values())
                {
                removeBehaviorDescribingComponent(component.getModelComponentID(), removeCascadeDepth);
                }
                if (components ==null) return;
                for(IBehaviorDescribingComponent component: components)
                {
                addBehaviorDescribingComponent(component);
                }
                }

        public boolean removeBehaviorDescribingComponent(String id, int removeCascadeDepth)
                {
                if (id == null) return false;
                if (behaviorDescriptionComponents.tryGetValue(id, IBehaviorDescribingComponent component))
                {
                behaviorDescriptionComponents.remove(id);
                component.unregister(this, removeCascadeDepth);
                if (layer != null)
                if (layer.getContainedBy(IPASSProcessModel model))
                model.removeElement(id);

                for(IBehaviorDescribingComponent otherComponent: getBehaviorDescribingComponents().Values)
                {
                otherComponent.updateRemoved(component, this, removeCascadeDepth);
                }
                if (component.equals(initialStateOfBehavior))
                {
                setInitialState(null);
                }
                if (component instanceof IStat){
                    IState state = (IState) component;
                    if (state.isStateType(IState.StateType.EndState)){
                removeTriple(new IncompleteTriple(OWLTags.stdHasEndState, state.getUriModelComponentID()));
                removeTriple(new IncompleteTriple(OWLTags.stdContains, component.getUriModelComponentID()));
                return true;
                }
                return false;
                }
        public Map<String, IBehaviorDescribingComponent> getBehaviorDescribingComponents()
                {
                return new HashMap<String, IBehaviorDescribingComponent>(behaviorDescriptionComponents);
                }



        public void setInitialState(IState initialStateOfBehavior, int removeCascadeDepth)
                {
                IState oldInitialState = this.initialStateOfBehavior;
                // Might set it to null
                this.initialStateOfBehavior = initialStateOfBehavior;

                if (oldInitialState != null)
                {
                if (oldInitialState.equals(initialStateOfBehavior)) return;
                //removeBehaviorDescribingComponent(oldInitialState.getModelComponentID(), removeCascadeDepth);
                oldInitialState.removeStateType(IState.StateType.InitialStateOfBehavior);
                removeTriple(new IncompleteTriple(OWLTags.stdHasInitialState, oldInitialState.getUriModelComponentID()));
                }

                if (!(initialStateOfBehavior == null))
                {
                addBehaviorDescribingComponent(initialStateOfBehavior);
                initialStateOfBehavior.setIsStateType(IState.StateType.InitialStateOfBehavior);
                addTriple(new IncompleteTriple(OWLTags.stdHasInitialState, initialStateOfBehavior.getUriModelComponentID()));
                }
                }

        public void setPriorityNumber(int positiveNumber)
                {
                if (positiveNumber == this.priorityNumber) return;
                removeTriple(new IncompleteTriple(OWLTags.stdHasPriorityNumber, this.priorityNumber.toString(),
                IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
                this.priorityNumber = (positiveNumber > 0) ? positiveNumber : 1;
                addTriple(new IncompleteTriple(OWLTags.stdHasPriorityNumber, positiveNumber.toString(),
                IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
                }


        public IState getInitialStateOfBehavior()
                {
                return initialStateOfBehavior;
                }

        public int getPriorityNumber()
                {
                return priorityNumber;
                }

        @Override
        public Set<IPASSProcessModelElement> getAllConnectedElements(PASSProcessModelElement.ConnectedElementsSetSpecification specification)
                {
                Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
                for (IBehaviorDescribingComponent component: getBehaviorDescribingComponents().values())
                baseElements.add(component);
                if (getInitialStateOfBehavior() != null) baseElements.add(getInitialStateOfBehavior());
                if (specification == ConnectedElementsSetSpecification.ALL)
                if (getSubject() != null) baseElements.add(getSubject());
                return baseElements;
                }
        @Override
        protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
                {
                if (implCapsule != null && implCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
                return true;
                else if (extendsCapsule != null && extendsCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
                return true;
                else if (element != null)
                {
                if (predicate.contains(OWLTags.contains) && element instanceof IBehaviorDescribingComponent) {
                IBehaviorDescribingComponent component = (IBehaviorDescribingComponent) element;
                addBehaviorDescribingComponent(component);
                return true;

                }

                else if ((predicate.contains(OWLTags.hasInitialStateOfBehavior) || predicate.contains(OWLTags.hasInitialState)) && element instanceof IState)
                {
                IState initialState = (IState)element;

                setInitialState(initialStateOfBehavior);
                return true;
                }
                if (predicate.contains(OWLTags.belongsTo) && element instanceof IFullySpecifiedSubject)
                {
                IFullySpecifiedSubject subj = (IFullySpecifiedSubject) element;
                setSubject(subj);
                return true;

                }
                }
                else if (predicate.contains(OWLTags.hasPriorityNumber))
                {
                String prio = objectContent;
                setPriorityNumber(int.Parse(prio));
                return true;
                }
                return super.parseAttribute(predicate, objectContent, lang, dataType, element);
                }
        @Override
        protected void successfullyParsedElement(IParseablePASSProcessModelElement parsedElement) {
            super.successfullyParsedElement(parsedElement);
            if (parsedElement instanceof IContainableElement<ISubjectBehavior>) {
                IContainableElement<ISubjectBehavior> containable = (IContainableElement<ISubjectBehavior>) parsedElement;
                containable.setContainedBy(this);
            }
        }

        @Override
        public void updateAdded(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
            super.updateAdded(update, caller);
            if (update instanceof IBehaviorDescribingComponent) {
                IBehaviorDescribingComponent behaviorComp = (IBehaviorDescribingComponent)update;
                addBehaviorDescribingComponent(behaviorComp);
            }
        }
        @Override
        public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth = 0)
                {
                super.updateRemoved(update, caller, removeCascadeDepth);
                if (update != null)
                {
                if (update instanceof IBehaviorDescribingComponent component)
                {
                    IBehaviorDescribingComponent component =(IBehaviorDescribingComponent) update;
                }
                removeBehaviorDescribingComponent(component.getModelComponentID(), removeCascadeDepth);

                }
                }
                }

        @Override
        public void notifyModelComponentIDChanged(String oldID, String newID)
                {
                if (behaviorDescriptionComponents.containsKey(oldID))
                {
                IBehaviorDescribingComponent element = behaviorDescriptionComponents[oldID];
                behaviorDescriptionComponents.remove(oldID);
                behaviorDescriptionComponents.add(element.getModelComponentID(), element);
                }
                super.notifyModelComponentIDChanged(oldID, newID);
                }

        public void setSubject(ISubject subj, int removeCascadeDepth)
                {
                if (subj instanceof IFullySpecifiedSubject)
                {
                    IFullySpecifiedSubject fullySpecified = (IFullySpecifiedSubject) subj;
                ISubject oldSubj = this.subj;

                // Might set it to null
                this.subj = subj;

                if (oldSubj != null)
                {
                if (oldSubj.equals(subj)) return;
                if (oldSubj instanceof IParseablePASSProcessModelElement parseable)
                    IParseablePASSProcessModelElement parseable = (IParseablePASSProcessModelElement) oldSubj;
                removeTriple(new IncompleteTriple(OWLTags.stdBelongsTo, parseable.getUriModelComponentID()));
                if (oldSubj instanceof IFullySpecifiedSubject oldFullySpecified)
                {
                    IFullySpecifiedSubject oldFullySpecified = (IFullySpecifiedSubject) oldSubj;
                oldFullySpecified.removeBehavior(getModelComponentID());
                }
                }

                if (fullySpecified != null)
                {
                if (fullySpecified instanceof IParseablePASSProcessModelElement parseable)
                    IParseablePASSProcessModelElement parseable = (IParseablePASSProcessModelElement) fullySpecified;
                addTriple(new IncompleteTriple(OWLTags.stdBelongsTo, parseable.getUriModelComponentID()));
                fullySpecified.addBehavior(this);
                }
                }
                }


        public ISubject getSubject()
                {
                return subj;
                }




        // ##################### Capsule Methods (Calls only get forwarded) #####################

        public void setImplementedInterfacesIDReferences(Set<String> implementedInterfacesIDs)
                {
                implCapsule.setImplementedInterfacesIDReferences(implementedInterfacesIDs);
                }

        public void addImplementedInterfaceIDReference(String implementedInterfaceID)
                {
                implCapsule.addImplementedInterfaceIDReference(implementedInterfaceID);
                }

        public void removeImplementedInterfacesIDReference(String implementedInterfaceID)
                {
                implCapsule.removeImplementedInterfacesIDReference(implementedInterfaceID);
                }

        public Set<String> getImplementedInterfacesIDReferences()
                {
                return implCapsule.getImplementedInterfacesIDReferences();
                }

        public void setImplementedInterfaces(Set<ISubjectBehavior> implementedInterface, int removeCascadeDepth)
                {
                implCapsule.setImplementedInterfaces(implementedInterface, removeCascadeDepth);
                }

        public void addImplementedInterface(ISubjectBehavior implementedInterface)
                {
                implCapsule.addImplementedInterface(implementedInterface);
                }

        public void removeImplementedInterfaces(String id, int removeCascadeDepth)
                {
                implCapsule.removeImplementedInterfaces(id, removeCascadeDepth);
                }

        public Map<String, ISubjectBehavior> getImplementedInterfaces()
                {
                return implCapsule.getImplementedInterfaces();
                }

        public void setExtendedElement(ISubjectBehavior element)
                {
                extendsCapsule.setExtendedElement(element);
                }

        public void setExtendedElementID(String elementID)
                {
                extendsCapsule.setExtendedElementID(elementID);
                }

        public ISubjectBehavior getExtendedElement()
                {
                return extendsCapsule.getExtendedElement();
                }

        public String getExtendedElementID()
                {
                return extendsCapsule.getExtendedElementID();
                }

        public boolean isExtension()
                {
                return extendsCapsule.isExtension();
                }

        public void removeFromContainer()
                {
                if (layer != null)
                layer.removeContainedElement(getModelComponentID());
                layer = null;
                }

                // ###########################################################
                }
                }
