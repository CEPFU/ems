/**
 * 
 */
package de.fu_berlin.agdb.crepe.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sun.tools.java.CompilerError;

import com.sun.tools.javac.Main;

import de.fu_berlin.agdb.crepe.algebra.Algebra;
import de.fu_berlin.agdb.crepe.algebra.Profile;

/**
 * Loads profiles from profile files. 
 * @author Ralf Oechsner
 *
 */
public class ProfileLoader implements Processor {

	private Algebra algebra;
	private String profilesFolder;
	
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * Loads profiles from profile files into an algebra.
	 * @param algebra algebra the profiles are loaded to
	 */
	public ProfileLoader(Algebra algebra, String profilesFolder) {
		
		this.algebra = algebra;
		this.profilesFolder = profilesFolder;
	}
	
	/**
	 * Compiles profile definition and returns a profile list that can be added to an algebra.
	 * @param definition profile definitions
	 * @return profile list
	 * @throws IOException thrown when directory doesn't exist or files can't be written
	 * @throws CompilerError thrown when the profile can't be compiled
	 */
	public List<Profile> load(String definition) throws IOException, CompilerError {
		
		String javaDir = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + this.profilesFolder
				+ System.getProperty("file.separator") + "java";
		File javaDirFile = new File(javaDir);
		
		if (!javaDirFile.exists()) {
			javaDirFile.mkdirs();
		}

		File file = File.createTempFile("Profile", ".java",
				javaDirFile);

		// Set the file to delete on exit
		file.deleteOnExit();
		// Get the file name and extract a class name from it
		String filename = file.getName();
		String classname = filename.substring(0, filename.length()-5);

		PrintWriter out = new PrintWriter(new FileOutputStream(file));
		out.println("/**");
		out.println(" * Source created on " + new Date());
		out.println(" */");
		out.println("import java.util.List;");
		out.println("import de.fu_berlin.agdb.crepe.algebra.Match;");
		out.println("import de.fu_berlin.agdb.crepe.algebra.Profile;");
		out.println("import de.fu_berlin.agdb.crepe.data.Event;");
		out.println("import static de.fu_berlin.agdb.crepe.algebra.dsl.ProfileBuilder.*;");
		out.println("import static de.fu_berlin.agdb.crepe.algebra.dsl.WindowBuilder.*;");
		out.println("import static de.fu_berlin.agdb.crepe.algebra.dsl.NotificationBuilder.*;");
		out.println("import static de.fu_berlin.agdb.crepe.algebra.dsl.CoreBuilder.*;");
		out.println("import static de.fu_berlin.agdb.crepe.algebra.dsl.LogicOperatorBuilder.*;");
		out.println("import static de.fu_berlin.agdb.crepe.algebra.dsl.TemporalOperatorBuilder.*;");
		out.println("import static de.fu_berlin.agdb.crepe.algebra.dsl.NumericOperatorBuilder.*;");
		out.println("import static de.fu_berlin.agdb.crepe.algebra.dsl.OutputAdapterBuilder.*;");
		out.println("import static de.fu_berlin.agdb.crepe.algebra.dsl.WriterBuilder.*;");
		out.println("public class " + classname + " {");
		out.println("    public static List<Profile> profilesDefinition() {");
		out.println("        beginProfiles();");
		out.print("        "); 
		out.println(definition);
		out.println("        return endProfiles();");	
		out.println("    }");
		out.println("}");
		out.flush();
		out.close();

		String[] args = new String[] {
				"-d", javaDir,
				javaDir + System.getProperty("file.separator") + filename
		};
		
		
		if (Main.compile(args) != 0) {
			
			// return codes of compile: 0 = OK, 1 = ERROR, 2 = CMDERR, 3 = SYSERR, 4 = ABNORMAL
			throw new CompilerError("Failed to compile profile file.");
		}

		List<Profile> profiles = null;
		
		new File(file.getParent(), classname + ".class").deleteOnExit();
		try {
			// try to access the class and run its main method
			File root = new File(javaDir);
			URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
			Class<?> clazz = Class.forName(classname, true, classLoader);

			// type safety is guaranteed because the return type of the profilesDefinition function is
			// hard coded in the header
			Method profilesDefinition = clazz.getMethod("profilesDefinition");
			@SuppressWarnings("unchecked")
			List<Profile> profilez = (List<Profile>) profilesDefinition.invoke(null);
			profiles = profilez;
		}
		catch (InvocationTargetException e) {
			logger.error("Error while loading profile: ", e);
		} catch (ClassNotFoundException e) {
			logger.error("Error while loading profile: ", e);
		} catch (NoSuchMethodException e) {
			logger.error("Error while loading profile: ", e);
		} catch (SecurityException e) {
			logger.error("Error while loading profile: ", e);
		} catch (IllegalAccessException e) {
			logger.error("Error while loading profile: ", e);
		} catch (IllegalArgumentException e) {
			logger.error("Error while loading profile: ", e);
		}
		
		return profiles;
	}

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		
		Object filename = exchange.getIn().getHeader("camelfilename");
		if (filename != null) {
			logger.info("Loading profiles from: " + filename);
		}
		
		try {
		
			List<Profile> profiles = this.load(exchange.getIn().getBody(String.class));
			if (profiles != null) {
				logger.info("The following profiles were loaded:");
				for (Profile curProfile : profiles) {
					this.algebra.addProfile(curProfile);
					logger.info(curProfile);
				}
			}
			else {
				logger.error("Profiles could not be loaded!");
			}
		}
		catch (CompilerError e1) {
			
			logger.error("Profile file compilation error!", e1);
		}
		catch (IOException e2) {
			
			logger.error("Profile file could not be read.", e2);
		}
	}
}
