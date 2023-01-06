package alps.net.api.StandardPASS.PassProcessModelElements;

import alps.net.api.ALPS.ALPSModelElements.IModelLayer;
import alps.net.api.StandardPASS.IPASSProcessModelElement;
import alps.net.api.StandardPASS.PASSProcessModelElement;
import alps.net.api.parsing.*;
import alps.net.api.util.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/// <summary>
/// Class that represents a BehaviorDescriptionComponent
/// </summary>

    public class BehaviorDescribingComponent extends
        PASSProcessModelElement implements IBehaviorDescribingComponent {

        protected ISubjectBehavior subjectBehavior;

        /// <summary>
        /// Name of the class, needed for parsing
        /// </summary>
        private final String className = "BehaviorDescribingComponent";

        @Override
        public String getClassName() {
            return className;
        }

        @Override
        public IParseablePASSProcessModelElement getParsedInstance() {
            return new BehaviorDescribingComponent();
        }

        protected BehaviorDescribingComponent() {
        }

        /// <summary>
        /// Constructor that creates an instance of the behavior description component
        /// </summary>
        /// <param name="label"></param>
        /// <param name="comment"></param>
        /// <param name="additionalAttribute"></param>
        public BehaviorDescribingComponent(ISubjectBehavior subjectBehavior, String labelForID, String comment,
                                           String additionalLabel, java.util.List<IIncompleteTriple> additionalAttribute) {

        super(labelForID, comment, additionalLabel, additionalAttribute) {
            setContainedBy(subjectBehavior);
        }

    }

        public void setContainedBy(ISubjectBehavior subjectBehavior)
                {
                if (this.subjectBehavior != null)
                {
                if (this.subjectBehavior.equals(subjectBehavior)) return;
                removeTriple(new IncompleteTriple(OWLTags.stdBelongsTo, this.subjectBehavior.getUriModelComponentID()));
                }

                // Might set it to null
                this.subjectBehavior = subjectBehavior;
                if (!(subjectBehavior == null))
                {
                subjectBehavior.addBehaviorDescribingComponent(this);
                addTriple(new IncompleteTriple(OWLTags.stdBelongsTo, subjectBehavior.getUriModelComponentID()));
                }
                }


        public boolean getContainedBy(ISubjectBehavior behavior)
                {
                behavior = subjectBehavior;
                return subjectBehavior != null;
                }
        @Override
        protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
                {
                if (element != null)
                {
                if (predicate.contains(OWLTags.belongsTo) && element instanceof ISubjectBehavior)
                {
                ISubjectBehavior behavior = (ISubjectBehavior) element;
                setContainedBy(behavior);
                return true;
                }
                }
                return super.parseAttribute(predicate, objectContent, lang, dataType, element);
                }


        @Override
        public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification)
                {
                Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
                    if (specification == ConnectedElementsSetSpecification.ALL) {
                        ISubjectBehavior behavior = getContainedBy();
                        if (behavior != null) {
                            baseElements.add(behavior);
                        }
                    }

                    return baseElements;
                }

        @Override
        protected Map<String, IParseablePASSProcessModelElement> getDictionaryOfAllAvailableElements()
                {
                    ISubjectBehavior behavior = getContainedBy();
                    if (behavior == null) return null;

                    IModelLayer layer = behavior.getContainedBy();
                    if (layer == null) return null;

                    IPASSProcessModel model = layer.getContainedBy();
                    if (model == null) return null;

                    Map<String, IPASSProcessModelElement> allElements = model.getAllElements();
                    Map<String, IParseablePASSProcessModelElement> allParseableElements = new HashMap<>();

                    for (Map.Entry<String, IPASSProcessModelElement> pair : allElements.entrySet()) {
                        if (pair.getValue() instanceof IParseablePASSProcessModelElement) {
                            IParseablePASSProcessModelElement parseable = (IParseablePASSProcessModelElement) pair.getValue();
                            allParseableElements.put(pair.getKey(), parseable);
                        }
                    }

                    return allParseableElements;

                }

        public void removeFromContainer()
                {
                if (subjectBehavior != null)
                subjectBehavior.removeBehaviorDescribingComponent(getModelComponentID());
                subjectBehavior = null;
                }
                }
