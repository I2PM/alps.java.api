package alps.net.api.StandardPASS.PassProcessModelElements;

import alps.net.api.StandardPASS.IPASSProcessModelElement;
import alps.net.api.StandardPASS.PASSProcessModelElement;
import alps.net.api.parsing.*;
import alps.net.api.util.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/// <summary>
    /// Class that represents a data describing component
    /// </summary>
    public class DataDescribingComponent extends PASSProcessModelElement implements IDataDescribingComponent {
        protected IPASSProcessModel model;

        /// <summary>
        /// Name of the class, needed for parsing
        /// </summary>
        private final String className = "DataDescribingComponent";
        @Override
        public String getClassName()
                {
                return className;
                }

        public void setContainedBy(IPASSProcessModel model)
                {
                if (model != null && model.equals(this.model)) return;
                this.model = model;
                if (model != null)
                {
                model.addElement(this);
                }
                }


        public boolean getContainedBy(IPASSProcessModel model)
                {
                model = this.model;
                return this.model != null;
                }
        @Override
        public IParseablePASSProcessModelElement getParsedInstance()
                {
                return new DataDescribingComponent();
                }

        protected DataDescribingComponent() { }
        /// <summary>
        /// Constructor that creates a new fully specified instance of the data describing component class
        /// </summary>
        /// <param name="additionalAttribute"></param>
        /// <param name="modelComponentID"></param>
        /// <param name="modelComponentLabel"></param>
        /// <param name="comment"></param>
        public DataDescribingComponent(IPASSProcessModel model, String labelForID, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
                super(labelForID, comment, additionalLabel, additionalAttribute)
            {
                setContainedBy(model);
            }
        }

        @Override
        protected Map<String, IParseablePASSProcessModelElement> getDictionaryOfAllAvailableElements()
                {
                if (model == null) return null;
                Map<String, IPASSProcessModelElement> allElements = model.getAllElements();
                Map<String, IParseablePASSProcessModelElement> allParseableElements = new HashMap<String, IParseablePASSProcessModelElement>();
                for(Map<String, IPASSProcessModelElement> pair: allElements)
                    if (pair.values() instanceof IParseablePASSProcessModelElement) {
                        IParseablePASSProcessModelElement parseable = (IParseablePASSProcessModelElement) pair.get();
                        allParseableElements.put(pair.get(), parseable);
                    }
                    return allParseableElements;

                }
        @Override
        public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification)
                {
                Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
                if (specification == ConnectedElementsSetSpecification.ALL)
                if (getContainedBy(IPASSProcessModel model)) baseElements.add(model);
                return baseElements;
                }

        public void removeFromContainer()
                {
                if (model != null)
                model.removeElement(getModelComponentID());
                model = null;
                }
                }

