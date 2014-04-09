/**
 * 
 */
package de.fu_berlin.agdb.ems.outputadapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TreeSet;

import de.fu_berlin.agdb.ems.data.IAttribute;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * @author Ralf Oechsner
 *
 */
public class CSVOutputAdapter implements IOutputAdapter {

	private String timeStampCaption;
	private String timeStampFormat;
	private String delimiter;
	private String output;
	private String[] captions = null;
	private DateFormat dateFormat;
	
	public CSVOutputAdapter(String timeStampCaption, String timeStampFormat, String delimiter) {
		
		this.timeStampCaption = timeStampCaption;
		this.timeStampFormat = timeStampFormat;
		this.dateFormat = new SimpleDateFormat(this.timeStampFormat);
		this.delimiter = delimiter;
	}
	
	public CSVOutputAdapter(String timeStampCaption, String timeStampFormat, String delimiter, String[] captions) {
		
		this(timeStampCaption, timeStampFormat, delimiter);
		this.captions = captions;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.outputadapters.IOutputAdapter#load(de.fu_berlin.agdb.ems.data.IEvent[])
	 */
	@Override
	public void load(IEvent[] events) {
		
		// always empty the output string before loading new events
		this.output = "";
		
		// if constructor which has no captions is used
		if (this.captions == null) {
			TreeSet<String> cpts = new TreeSet<String>();
			cpts.add(this.timeStampCaption);
			for (IEvent curEvent : events) {
				cpts.addAll(curEvent.getAttributes().keySet());
			}
			// captions have order of attributes, therefore the set is reversed
			this.captions = cpts.descendingSet().toArray(new String[cpts.size()]);
		}
		
		for (int i = 0; i < this.captions.length; i++) {
			this.output += this.captions[i];
			if (i < this.captions.length - 1) {
				this.output += delimiter;
			}
		}
		this.output += System.getProperty("line.separator");
		
		for (IEvent curEvent : events) {
			for (int i = 0; i < this.captions.length; i++) {
				
				if (this.captions[i].equals(this.timeStampCaption)) {
					this.output += this.dateFormat.format(curEvent.getTimeStamp());
				}
				else {
					IAttribute attribute = curEvent.getAttributes().get(this.captions[i]);
					if (attribute != null) {
						this.output += attribute.getValue();

					}
				}
				if (i < this.captions.length - 1) {
					this.output += delimiter;
				}
			}
			this.output += System.getProperty("line.separator"); // one event per line
		}
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.outputadapters.IOutputAdapter#getOutput()
	 */
	@Override
	public String getOutput() {

		return this.output;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String s = "CSVOutputAdapter(" + timeStampCaption
				+ ", " + timeStampFormat + ", " + delimiter;
		if (this.captions != null) {
			s += ", " + Arrays.toString(captions);
		}
		
		return s + ")";
	}

	
}
