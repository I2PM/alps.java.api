package alps.java.LibraryExample;


import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents.IStandaloneMacroSubject;
import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IDoState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IMacroState;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataMappingFunctions.IDataMappingIncomingToLocal;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.IDataMappingFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.IBehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IFullySpecifiedSubject;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IMacroBehavior;
import alps.java.api.parsing.IPASSReaderWriter;
import alps.java.api.parsing.PASSReaderWriter;
import alps.java.api.util.ReflectiveEnumerator;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {


        // Needs to be called once

        // Now the reflective enumerator searches for classes in the library assembly as well as in the current.
        Class<?> executingClass = Main.class;

        ReflectiveEnumerator.addClassToCheckForTypes(executingClass);


        IPASSReaderWriter io = PASSReaderWriter.getInstance();


        // Set own factory as parsing factory to parse ontology classes to the right instances

        io.setModelElementFactory(new AdditionalFunctionalityClassFactory());


        List<String> paths = new ArrayList<String>();
        paths.add("C:\\Users\\sanja\\OneDrive\\Desktop\\alps.java.api\\src\\main\\java\\alps\\java\\LibraryExample\\standard_PASS_ont_v_1.1.0.owl");
        paths.add("C:\\Users\\sanja\\OneDrive\\Desktop\\alps.java.api\\src\\main\\java\\alps\\java\\LibraryExample\\ALPS_ont_v_0.8.0.owl");


        // Load these files once (no future calls needed)

        // This call creates both parsing trees and the parsing dictionary

        io.loadOWLParsingStructure(paths);


        // This loads models from the specified owl.

        // Every owl instance of a FullySpecifiedSubject is parsed to an AdditionalFunctionalityFullySpecifiedSubject

        // IList<IPASSProcessModel> models = io.loadModels(new List<string> { "C:\\Data\\ExportImportTest1.owl" });

        List<String> modelPaths = new ArrayList<>();
        modelPaths.add("C:\\Users\\sanja\\OneDrive\\Desktop\\Hiwi\\Testing\\ExportImportTest1.owl");
        List<IPASSProcessModel> models = io.loadModels(modelPaths);
//Bis hierhin funktiniert es ohne Fehlermeldungen!!!!!

       /** // IDictionary of all elements

        Map<String, IPASSProcessModelElement> allElements = models.get(0).getAllElements();

        // Drop the keys, keep values

        Collection<IPASSProcessModelElement> onlyElements = models.get(0).getAllElements().values();

        // Filter for a specific interface (Enumerable, not so easy to use -> convert to list)

        List<IAdditionalFunctionalityElement> onlyAdditionalFunctionalityElements = new ArrayList<IAdditionalFunctionalityElement>();
        for (IPASSProcessModelElement element : onlyElements) {
            if (element instanceof IAdditionalFunctionalityElement)
                onlyAdditionalFunctionalityElements.add((IAdditionalFunctionalityElement) element);
        }

        //some output examples for a parsed model

        System.out.println("Number ob Models loaded: " + models.size());


        System.out.println("Found " + onlyAdditionalFunctionalityElements.size() +

                " AdditionalFunctionalityElements in First model!");


        System.out.println();


        Map<String, IModelLayer> layers = models.get(0).getModelLayers();

        System.out.println("Layers in first model: " + layers.size());


        IModelLayer firstLayer = layers.values().iterator().next();


        IFullySpecifiedSubject mySubject = firstLayer.getFullySpecifiedSubject(1);

        if (mySubject != null) {


            Map<String, ISubjectBehavior> mySubjectBehaviors = mySubject.getBehaviors();

            System.out.println();

            System.out.println("Found a subject: " + mySubject.getModelComponentID());

            System.out.println("Numbers of behaviors in subject: " + mySubjectBehaviors.size());


            ISubjectBehavior firstBehavior = mySubjectBehaviors.values().iterator().next();

            System.out.println("Numbers of Elements in Behavior: " + firstBehavior.getBehaviorDescribingComponents().size());

            System.out.println("First Element: " + firstBehavior.getBehaviorDescribingComponents().values().iterator().next().getModelComponentID());

            IState firstState = firstBehavior.getInitialStateOfBehavior();

            if (firstState != null) {

                System.out.println("Initial State of Behavior: " + firstState.getModelComponentID());

            }


            iterateStates(firstBehavior);


            System.out.println();


            findMappingFunctionIn(allElements);

        }


        IStandaloneMacroSubject mySMS = getStandaloneMacroSubjectFrom(firstLayer);

        if (mySMS != null) {


            IMacroBehavior myMB = mySMS.getBehavior();

            System.out.println();

            System.out.println("found macro behavior: " + (myMB != null));

            System.out.println("States of Macro Behavior: ");

            iterateStates(myMB);

            System.out.println();

        }

    }


    private static IStandaloneMacroSubject getStandaloneMacroSubjectFrom(IModelLayer layer) {

        IStandaloneMacroSubject result = null;

        System.out.println("Subjects:");

        for (Map.Entry<String, IPASSProcessModelElement> kvp : layer.getElements().entrySet()) {

            IPASSProcessModelElement myComponent = kvp.getValue();


            if (myComponent instanceof ISubject) {

                System.out.println("Subject: " + myComponent.getModelComponentID());

                if (myComponent instanceof IStandaloneMacroSubject) {

                    result = (IStandaloneMacroSubject) myComponent;

                }

            }

        }

        return result;

    }


    private static void iterateStates(ISubjectBehavior someBehavior) {

        System.out.println("States of Behavior: " + someBehavior.getModelComponentID());


        for (Map.Entry<String, IBehaviorDescribingComponent> kvp : someBehavior.getBehaviorDescribingComponents().entrySet()) {

            IPASSProcessModelElement myComponent = kvp.getValue();

            if (myComponent instanceof IState) {

                System.out.println("state: " + myComponent.getModelComponentID());


                IState myIstate = (IState) myComponent;


                System.out.println(" - start: " + myIstate.isStateType(IState.StateType.InitialStateOfBehavior));

                System.out.println(" - end: " + myIstate.isStateType(IState.StateType.EndState));

                if (myIstate instanceof IDoState) {

                    IDoState myDo = (IDoState) myIstate;

                    iterateThroughStateAttributes(myDo);

                }


            }

        }

    }


    private static void findMappingFunctionIn(Map<String, IPASSProcessModelElement> allElements) {


        System.out.println("Data Mapping Functions: ");

        for (Map.Entry<String, IPASSProcessModelElement> kvp : allElements.entrySet()) {

            IPASSProcessModelElement myComponent = kvp.getValue();

            if (myComponent instanceof IDataMappingFunction) {

                IDataMappingFunction myDataMapping = (IDataMappingFunction) myComponent;

                System.out.println(" Found a Data Mapping Function: " + myDataMapping.getModelComponentID());

                System.out.println(" - typename: " + myDataMapping.getClass().getName());

                System.out.println(" - string: " + myDataMapping.getDataMappingString());

            }

        }

        System.out.println("");

    }


    private static void iterateThroughStateAttributes(IState someState) {


        System.out.println("  Attribute Details for State: " + someState.getModelComponentLabels().get(0));


        if (someState instanceof IDoState) {

            IDoState myDo = (IDoState) someState;

            Map<String, IDataMappingIncomingToLocal> myMapDic = myDo.getDataMappingFunctionsIncomingToLocal();

            System.out.println("   - number of data mappings: " + myMapDic.size());

            System.out.println("   - number of unspecific Relations : " + myDo.getElementsWithUnspecifiedRelation().size());

            for (Map.Entry<String, IPASSProcessModelElement> myFunc : myDo.getElementsWithUnspecifiedRelation().entrySet()) {

                System.out.println("     - element: " + myFunc.getValue().getModelComponentID());

                //Console.WriteLine("     - tool specific def: " + myFunc.Value.getToolSpecificDefinition());


            }

        }


        if (someState instanceof IMacroState) {

            IMacroState myMacroState = ((IMacroState) someState);

            if (myMacroState.getReferencedMacroBehavior() != null) {

                System.out.println("   - Macro State, called macroBehavior: " + myMacroState.getReferencedMacroBehavior().getModelComponentID());

                IMacroBehavior myMB = myMacroState.getReferencedMacroBehavior();

            }

        }


        Map<String, IPASSProcessModelElement> myDic = someState.getElementsWithUnspecifiedRelation();


        for (Map.Entry<String, IPASSProcessModelElement> att : myDic.entrySet()) {

            System.out.println("   - unspecific special: " + att.getKey() + " value: " + att.getValue());


        }

*/
    }

}

