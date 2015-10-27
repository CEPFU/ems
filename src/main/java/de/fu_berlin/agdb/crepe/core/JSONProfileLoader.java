package de.fu_berlin.agdb.crepe.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fu_berlin.agdb.crepe.algebra.Algebra;
import de.fu_berlin.agdb.crepe.algebra.Profile;
import de.fu_berlin.agdb.crepe.algebra.windows.EndlessWindow;
import de.fu_berlin.agdb.crepe.json.algebra.JSONProfile;
import org.apache.camel.Exchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author Simon Kalt
 */
public class JSONProfileLoader extends ProfileLoader {

    private static Logger logger = LogManager.getLogger();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Algebra algebra;
    private String profilesFolder;

    /**
     * Loads profiles from profile files into an algebra.
     *
     * @param algebra algebra the profiles are loaded to
     */
    public JSONProfileLoader(Algebra algebra, String profilesFolder) {
        super(algebra, profilesFolder);
        this.algebra = algebra;
        this.profilesFolder = profilesFolder;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String filename = exchange.getIn().getHeader("camelfilename", String.class);
        if (filename == null)
            return;

        if (filename.endsWith(".json")) {
            logger.info("Loading profile from: " + filename);
            try {
                List<Profile> profiles = this.load(exchange.getIn().getBody(String.class));
                for (Profile profile : profiles) {
                    this.algebra.addProfile(profile);
                    logger.info("The following profile was loaded:");
                    logger.info(profile);
                }
            } catch (IOException ex) {
                logger.error("Profile could not be loaded!", ex);
            }
        } else {
            super.process(exchange);
        }
    }

    public List<Profile> load(String definition) throws IOException {
        JSONProfile jsonProfile = objectMapper.readValue(definition, JSONProfile.class);
        return Collections.singletonList(jsonProfile.getProfile(new EndlessWindow()));
    }
}
