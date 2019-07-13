package io.github.cobblecracker.Carto;

import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;


public class Main extends JavaPlugin {
    static final String MAIN_COMMAND = "carto";
    private MapRegistry registry;

    @Inject
    private RegisterMapCommand registerMapCommand;

    @Override
    public void onEnable() {
        InjectionBinder module = new InjectionBinder(this);
        Injector injector = module.createInjector();
        injector.injectMembers(this);

        this.getLogger().info("Enabled");

        registry = new MapRegistry(this.getDataFolder());

        this.getCommand(MAIN_COMMAND).setExecutor(this.registerMapCommand);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled");
    }


    public MapRegistry getRegistry() {
        return registry;
    }
}
