package dev.alexzvn.recipe.helper;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static String message(String key) {
        return Util.color(
            config().getString("message_prefix") + config().getString("message." + key)
        );
    }

    protected static FileConfiguration config() {
        return Util.plugin().getConfig();
    }

    public static Double getDouble(String key) {
        return  config().getDouble(key);
    }

    public static String getString(String key) {
        return  config().getString(key);
    }

    public static boolean getBoolean(String key) {
        return  config().getBoolean(key);
    }

    public static int getInt(String key) {
        return  config().getInt(key);
    }

    public static long getLong(String key) {
        return  config().getLong(key);
    }

    public static Float getFloat(String key) {
        Double d = config().getDouble(key);

        return d.floatValue();
    }
}
