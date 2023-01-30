package alps.java.api.ALPS.ALPSModelElements;


import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents.ISubjectExtension;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.*;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;
import java.util.Set;


/**
 * From abstract pass ont:
 * Abstract Process Elements are only used on abstract layers that do not specify complete behaviors.
 */
public class ExtensionBehavior extends SubjectBehavior implements IExtensionBehavior
{

    /**
     * Name of the class, needed for parsing
     */
    private final String CLASS_NAME = "ExtensionBehavior";
    @Override
    public String getClassName()
    {
        return CLASS_NAME;
    }
    @Override
    public IParseablePASSProcessModelElement getParsedInstance()
    {
        return new ExtensionBehavior();
    }

    protected ExtensionBehavior() { }

    /**
     *
     * @param layer
     * @param labelForId
     * @param subject
     * @param behaviorDescribingComponents
     * @param initialStateOfBehavior
     * @param priorityNumber
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public ExtensionBehavior(IModelLayer layer, String labelForId, ISubject subject, Set<IBehaviorDescribingComponent> behaviorDescribingComponents,
                             IState initialStateOfBehavior, int priorityNumber, String comment, String additionalLabel,
                             List<IIncompleteTriple> additionalAttribute){
        super(layer, labelForId, subject, behaviorDescribingComponents, initialStateOfBehavior, priorityNumber, comment, additionalLabel, additionalAttribute);
        {
        }
        @Override
        public void setSubject(ISubject subj, int removeCascadeDepth)
        {
            ISubject oldSubj = this.subj;

            // Might set it to null
            this.subj = subj;

            if (oldSubj != null)
            {
                if (oldSubj.equals(subj)) return;
                if (oldSubj instanceof IParseablePASSProcessModelElement parseable)
                    removeTriple(new IncompleteTriple(OWLTags.stdHasInitialState, parseable.getUriModelComponentID()));
                if (oldSubj instanceof ISubjectExtension oldExtension)
                {
                    oldExtension.removeExtensionBehavior(getModelComponentID());
                }
            }

            if (subj instanceof ISubjectExtension extension){

            }else{
                return;
            }

            if (extension instanceof IParseablePASSProcessModelElement parseable2)
                addTriple(new IncompleteTriple(OWLTags.stdHasInitialState, parseable2.getUriModelComponentID()));
            extension.addExtensionBehavior(this);

        }
        @Override
        protected  String getExportTag() {
            return OWLTags.abstr;
        }

    }
}
