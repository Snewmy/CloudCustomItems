package me.Sam.customitems;

public class Locale {

    public static Locale instance;

    public Locale() {
        instance = this;
    }

    public String get(String configKey) {
        return CustomItems.instance.messages.getString("prefix") + CustomItems.instance.messages.getString(configKey);
    }
}
