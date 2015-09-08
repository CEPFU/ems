package de.fu_berlin.agdb.crepe.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.tuple.Triple;

import de.fu_berlin.agdb.crepe.data.IEvent;
import de.fu_berlin.agdb.crepe.inputadapters.IInputAdapter;
import de.fu_berlin.agdb.crepe.loader.ILoader;

public class SourceHandler implements Runnable{

	public static final String LOADER_HANDLER_QUEUE_URI = "direct:loader_handler_queue";

	private ArrayList<Triple<ILoader, IInputAdapter, ProducerTemplate>> activeTriples;
	private String outputURI;
	
	public SourceHandler(String outputURI) {
		activeTriples = new ArrayList<Triple<ILoader, IInputAdapter, ProducerTemplate>>();
		this.outputURI = outputURI;
	}
	
	@Override
	public void run() {
		while (true) {
			for (Triple<ILoader, IInputAdapter, ProducerTemplate> triple : activeTriples) {
				triple.getMiddle().load(triple.getLeft().getText());
				List<IEvent> events = triple.getMiddle().getEvents();
				if(events != null){
					for (IEvent iEvent : events) {
						triple.getRight().sendBody(outputURI, iEvent);
					}
				}
			}
		}
	}
	
	public void addLoaderAndInputAdapter(Triple<ILoader, IInputAdapter, ProducerTemplate> triple){
		synchronized (activeTriples) {
			activeTriples.add(triple);
		}
	}
}
