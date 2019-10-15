package me.dandabs.kodoresuinternal;

import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Hello, SpigotMC!");
        this.getCommand("kodoresu").setExecutor(new cmdKodoresu());
    }

    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }
}