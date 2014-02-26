/**
 * 
 */
package de.fu_berlin.agdb.ems.algebra.dsl;

import java.util.ArrayList;
import java.util.List;

import de.fu_berlin.agdb.ems.algebra.Operator;
import de.fu_berlin.agdb.ems.algebra.Profile;
import de.fu_berlin.agdb.ems.algebra.notifications.Notification;

/**
 * @author Ralf Oechsner
 *
 */
public class ProfileBuilder {
	
	private static List<Profile> profileList;
	
	/**
	 * Starts block with profiles. Is used in profile files automatically by 
	 * the ProfileLoader. Shouldn't be used by the user!
	 */
	public static void beginProfiles() {
		
		profileList = new ArrayList<Profile>();
	}
	
	/**
	 * Ends a block with profiles. Is used in profile files automatically by
	 * the ProfileLoader. Shouldn't be used by the user!
	 * @return list with profiles
	 */
	public static List<Profile> endProfiles() {
		
		// profile list is automatically emptied with the next beginProfiles
		// can't be emptied before because the list is still needed
		return profileList;
	}
	
	/**
	 * Creates a profile, which is automatically added to the algebra (if the function
	 * is used in a profile file which is loaded with ProfileLoader).
	 * @param rule rule of the profile
	 * @param notifications notification that is thrown when the rule matches
	 */
	public static void profile(Operator rule, Notification ... notifications) {
		
		Profile profile = new Profile(rule, notifications);
		
		profileList.add(profile);
	}
}
