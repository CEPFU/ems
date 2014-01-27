package de.fu_berlin.agdb.ems.importer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import de.fu_berlin.agdb.ems.core.Tag;
import de.fu_berlin.agdb.ems.data.Attribute;
import de.fu_berlin.agdb.ems.data.Event;
import de.fu_berlin.agdb.ems.data.IAttribute;
import de.fu_berlin.agdb.ems.data.IEvent;

/**
 * Importer for comma separated files.
 * @author Ralf Oechsner
 *
 */
public class CSVImporter implements IImporter, ISplitter {

	private List<IEvent> events;
	private String timeStampCaption;
	private String timeStampFormat;
	private String separator;
	
	/**
	 * Importer for CSV files.
	 * @param csvText text of CSV file. First line must contain captions of columns.
	 * @param timeStampCaption caption of time stamp column (case sensitive!).
	 * @param timeStampFormat format string of time stamp (see java.text.SimpleDateFormat).
	 * @param separator separator of columns (e.g. "," or ";" or "\t").
	 */
	public CSVImporter(@Tag("timeStampCaption") String timeStampCaption, @Tag("timeStampFormat") String timeStampFormat, @Tag("separator") String separator) {

		this.timeStampCaption = timeStampCaption;
		this.timeStampFormat = timeStampFormat;
		this.separator = separator;
		this.events = new ArrayList<IEvent>();
	}
	
	
	
	/**
	 * Parses the CSV file.
	 */
	private void parseCSV(String csvText) {
			
		Scanner scanner = new Scanner(csvText);
		
		// first line contains attribute names. So it must exist.
		if (!scanner.hasNextLine()) {
			scanner.close();
			return;
		}
		
		// first line is stored as a list which is used for keys
		String line = scanner.nextLine();
		StringTokenizer st = new StringTokenizer(line, this.separator);
		List<String> keys = new ArrayList<String>();
		int timeStampCol = 0;
		int col = 0;
		while(st.hasMoreTokens()) {
			
			String token = st.nextToken();
			if (!token.equals(this.timeStampCaption)) {
				keys.add(token);  // TODO: check on duplicate entries
			}
			else {
				timeStampCol = col;
				keys.add(null);  // seems stupid but is important: keys are mapped to columns so time stamp column
				                 // is represented as an empty list element
			}
			
			col++;
		}
						
		// parse every line and look for attributes
		while (scanner.hasNextLine()) {
			
			
			Event curEvent = new Event();
			Map<String, IAttribute> curAttributes = new HashMap<String, IAttribute>();
			
			line = scanner.nextLine();

			st = new StringTokenizer(line, this.separator);
			col = 0;
			while(st.hasMoreTokens()) {
				String token = st.nextToken();
				// normal attribute
				if (col != timeStampCol) {
					if (col == 2) {
						curAttributes.put(keys.get(col), new Attribute(Integer.valueOf(token)));
					} else {
						curAttributes.put(keys.get(col), new Attribute(token));
					}
				}
				// time stamp
				else {
					DateFormat df = new SimpleDateFormat(this.timeStampFormat);
					try {
						curEvent.setTimeStamp(df.parse(token));
					} catch (ParseException e) {
						// wrong format so ignored TODO: what then?
						e.printStackTrace();
					}
				}
				
				col++;
			}
			
			curEvent.setAttributes(curAttributes);
			this.events.add(curEvent);
		}
		
		scanner.close();
	}
	
	public List<IEvent> getEvents() {

		return this.events;
	}

	@Override
	public List<IEvent> splitMessage(String header, String body) {

		this.parseCSV(body);
		
		return this.events;
	}

	@Override
	public void load(String text) {
	
		this.parseCSV(text);
	}
}
