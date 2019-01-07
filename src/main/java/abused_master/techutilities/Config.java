package abused_master.techutilities;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class Config {

    public static YamlFile config;
    public static boolean generateCopper;
    public static boolean generateTin;
    public static boolean generateLead;
    public static boolean generateSilver;
    public static boolean generatePlatinum;
    public static boolean generateNickel;

    public static void initConfig(String location) {
        config = new YamlFile(new File(location));

        if (config.exists()) {
            try {
                config.load();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Loading Tech Utilities configuration...\n");
        }else {
            System.out.println("Creating new configuration file...\n");
            try {
                config.createNewFile(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            config.set("generateCopper", true);
            config.set("generateTin", true);
            config.set("generateLead", true);
            config.set("generateSilver", true);
            config.set("generatePlatinum", true);
            config.set("generateNickel", true);
        }

        generateCopper = config.getBoolean("generateCopper");
        generateTin = config.getBoolean("generateTin");
        generateLead = config.getBoolean("generateLead");
        generateSilver = config.getBoolean("generateSilver");
        generatePlatinum = config.getBoolean("generatePlatinum");
        generateNickel = config.getBoolean("generateNickel");

        try {
            config.saveWithComments();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
