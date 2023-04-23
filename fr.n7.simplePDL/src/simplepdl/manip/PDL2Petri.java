package simplepdl.manip;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import petrinet.Arc;
import petrinet.ArcKind;
import petrinet.Node;
import petrinet.PetriNet;
import petrinet.PetrinetFactory;
import petrinet.PetrinetPackage;
import petrinet.Place;
import petrinet.Transition;
import simplepdl.Process;
import simplepdl.ResourceUtilisation;
import simplepdl.SimplepdlPackage;
import simplepdl.WorkDefinition;
import simplepdl.WorkSequence;

public class PDL2Petri {

	static HashMap<String, Node> nodes = new HashMap<String, Node>();
	static HashMap<String, Place> resources = new HashMap<String, Place>();
	
	static PetrinetFactory myFactory;
	static PetriNet petrinet;
	
	public static void main(String[] args) {
		// Charger le package SimplePDL afin de l'enregistrer dans le registre d'Eclipse.
		SimplepdlPackage packageInstanceSimplePDL = SimplepdlPackage.eINSTANCE;
		// Charger le package PetriNet afin de l'enregistrer dans le registre d'Eclipse.
		PetrinetPackage packageInstancePetriNet = PetrinetPackage.eINSTANCE;

		// Enregistrer l'extension ".xmi" comme devant Ãªtre ouverte Ã 
		// l'aide d'un objet "XMIResourceFactoryImpl"
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());

		// CrÃ©er un objet resourceSetImpl qui contiendra une ressource EMF (le modÃ¨le)
		ResourceSet resSet = new ResourceSetImpl();

		// Créer le modèle de sortie (PetriNet.xmi)
		URI outputURI = URI.createURI("models/PetriNet_java.xmi");
		Resource output = resSet.createResource(outputURI);

		// Créer un objet resourceSetImpl qui contiendra une ressource EMF (le modèle)
		ResourceSet resSetModel = new ResourceSetImpl();

		// Charger la ressource (notre modèle)
		URI modelURI = URI.createURI("models/ExampleSujet.xmi");
		Resource resource = resSetModel.getResource(modelURI, true);

		// Récupérer le premier élément du modèle (élément racine)
		Process process = (Process) resource.getContents().get(0);

		// La fabrique pour fabriquer les Ã©lÃ©ments de SimplePDL
		myFactory = PetrinetFactory.eINSTANCE;
		petrinet = myFactory.createPetriNet();
		petrinet.setName(process.getName());
		
		output.getContents().add(petrinet);

		for (Object pe : process.getProcesselements()) {
			if (pe instanceof WorkDefinition) {
				convertWorkDefinition((WorkDefinition) pe);
			} else if (pe instanceof WorkSequence) {
				convertWorkSequence((WorkSequence) pe);
			} else if (pe instanceof simplepdl.Resource) { // On précise le package car il existe ecore.Resource
				convertResource((simplepdl.Resource) pe);
			}	
		}
		
		// Sauver la ressource
	    try {
	    	output.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void convertWorkDefinition(WorkDefinition wd) {
		String name = wd.getName();
		/* places */
		Place ready = myFactory.createPlace();
		ready.setName(name + "_ready");
		ready.setNbToken(1);
		petrinet.getNodes().add(ready);
		nodes.put(ready.getName(), ready);
		Place started = myFactory.createPlace();
		started.setName(name + "_started");
		started.setNbToken(0);
		petrinet.getNodes().add(started);
		nodes.put(started.getName(), started);
		Place running = myFactory.createPlace();
		running.setName(name + "_running");
		running.setNbToken(0);
		petrinet.getNodes().add(running);
		nodes.put(running.getName(), running);
		Place finished = myFactory.createPlace();
		finished.setName(name + "_finished");
		finished.setNbToken(0);
		petrinet.getNodes().add(finished);
		nodes.put(finished.getName(), finished);
		
		/* transitions */
		Transition start = myFactory.createTransition();
		start.setName(name + "_start");
		petrinet.getNodes().add(start);
		nodes.put(start.getName(), start);
		Transition finish = myFactory.createTransition();
		finish.setName(name + "_finish");
		petrinet.getNodes().add(finish);
		nodes.put(finish.getName(), finish);
		
		/* arcs */
		Arc ready_start = myFactory.createArc();
		ready_start.setKind(ArcKind.NORMAL);
		ready_start.setSource(ready);
		ready_start.setTarget(start);
		ready_start.setWeight(1);
		petrinet.getArcs().add(ready_start);
		Arc start_running = myFactory.createArc();
		start_running.setKind(ArcKind.NORMAL);
		start_running.setSource(start);
		start_running.setTarget(running);
		start_running.setWeight(1);
		petrinet.getArcs().add(start_running);
		Arc start_started = myFactory.createArc();
		start_started.setKind(ArcKind.NORMAL);
		start_started.setSource(start);
		start_started.setTarget(started);
		start_started.setWeight(1);
		petrinet.getArcs().add(start_started);
		Arc running_finish = myFactory.createArc();
		running_finish.setKind(ArcKind.NORMAL);
		running_finish.setSource(running);
		running_finish.setTarget(finish);
		running_finish.setWeight(1);
		petrinet.getArcs().add(running_finish);
		Arc finish_finished = myFactory.createArc();
		finish_finished.setKind(ArcKind.NORMAL);
		finish_finished.setSource(finish);
		finish_finished.setTarget(finished);
		finish_finished.setWeight(1);
		petrinet.getArcs().add(finish_finished);
		
		/* resources */
		for (ResourceUtilisation ru : wd.getResourceutilisation()) {
			Arc request = myFactory.createArc();
			request.setSource(resources.get(ru.getResource().getName()));
			request.setTarget(start);
			request.setWeight(ru.getQuantity());
			Arc restore = myFactory.createArc();
			restore.setSource(finish);
			restore.setTarget(resources.get(ru.getResource().getName()));
			restore.setWeight(ru.getQuantity());
			petrinet.getArcs().add(request);
			petrinet.getArcs().add(restore);
		}
	}
	
	public static void convertWorkSequence(WorkSequence ws) {
		
		Arc arc = myFactory.createArc();
		arc.setKind(ArcKind.READ);
		arc.setWeight(1);
		
		switch (ws.getLinkType()) {
		case START_TO_START: {
			arc.setSource(nodes.get(ws.getPredecessor().getName() + "_started"));
			arc.setTarget(nodes.get(ws.getSuccessor().getName() + "_start"));
			break;
		}
		case START_TO_FINISH: {
			arc.setSource(nodes.get(ws.getPredecessor().getName() + "_started"));
			arc.setTarget(nodes.get(ws.getSuccessor().getName()+ "_finish"));
			break;
		}
		case FINISH_TO_START: {
			arc.setSource(nodes.get(ws.getPredecessor().getName() + "_finished"));
			arc.setTarget(nodes.get(ws.getSuccessor().getName() + "_start"));
			break;
		}
		case FINISH_TO_FINISH: {
			arc.setSource(nodes.get(ws.getPredecessor().getName() + "_finished"));
			arc.setTarget(nodes.get(ws.getSuccessor().getName() + "_finish"));
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + ws.getLinkType());
		}
		
		petrinet.getArcs().add(arc);
	}
	
	public static void convertResource(simplepdl.Resource r) {
		Place resource = myFactory.createPlace();
		resource.setName(r.getName());
		resource.setNbToken(r.getQuantity());
		resources.put(resource.getName(), resource);
		petrinet.getNodes().add(resource);
	}

}
