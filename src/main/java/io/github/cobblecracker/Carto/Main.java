package io.github.cobblecracker.Carto;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.entity.Player;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {
    private static final String MAIN_COMMAND = "carto";
    private MapRegistry registry;

    @Override
    public void onEnable() {
        getLogger().info("Enabled");
        getCommand(MAIN_COMMAND).setExecutor(this);
        this.
        registry = new MapRegistry(getDataFolder());
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled");
    }

    private void registerMapInHand(Player registrar) {
        PlayerInventory inventory = registrar.getInventory();
        ItemStack itemInHand = inventory.getItemInMainHand();
        if (itemInHand.getType() != Material.MAP) {
            getLogger().warning("Player tried to register not a map");
            return;
        }

        MapMeta meta = (MapMeta) itemInHand.getItemMeta();
        if (!meta.hasMapView()) {
            getLogger().warning("Map has no map view");
            return;
        }
        int mapId = meta.getMapId();

        MapRecord newRecord = new MapRecord(mapId, registrar.getDisplayName(), registrar.getUniqueId().toString());

        if(registry.register(newRecord)) {
            registrar.sendMessage("success");
        } else {
            registrar.sendMessage("fail");
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(MAIN_COMMAND)) {
            if (!(sender instanceof Player))
                return true;

            if (args.length < 1) {
                sender.sendMessage("Incorrect Syntax!!");
                sender.sendMessage("/carto add");
                return true;
            }


            String subCmd = args[0];

            if (subCmd.equalsIgnoreCase("add")) {
                sender.sendMessage("adding to cartographic registry");
                registerMapInHand((Player) sender);
            }
        }
        return false;
    }



    public List<String> onTabComplete(CommandSender sender, //registers the auto tab completer
                                      Command command,
                                      String alias,
                                      String[] args) {
        if (command.getName().equalsIgnoreCase(MAIN_COMMAND)) {  //your command name
            List<String> l = new ArrayList<String>(); //makes a ArrayList


            if (args.length == 1) {
                l.add("add"); //Possibility #1
            }

            return l;

        }
        return null;
    }
}
