package io.tguduru.spring.jdbc.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * @author Guduru, Thirupathi Reddy
 *         created 10/3/18
 */
public class Configuration {
    private static Configuration configurationInstance;
    private final YamlConfig yamlConfig;

    private Configuration() {
        Yaml yaml = new Yaml(new Constructor(YamlConfig.class));
        yamlConfig = yaml.load(getClass().getClassLoader().getResourceAsStream("config.yaml"));
    }

    public static Configuration getInstance() {
        if (configurationInstance == null) {
            configurationInstance = new Configuration();
        }
        return configurationInstance;
    }

    public YamlConfig getYamlConfig() {
        return yamlConfig;
    }
}
