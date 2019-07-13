package io.github.cobblecracker.Carto;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.MapMeta;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class RegisterMapCommand implements CommandExecutor {

    @Inject
    private Main plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(Main.MAIN_COMMAND)) {
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



    public List<String> onTabComplete(CommandSender sender,
                                      Command command,
                                      String alias,
                                      String[] args) {
        if (command.getName().equalsIgnoreCase(Main.MAIN_COMMAND)) {
            List<String> l = new ArrayList<String>();
            if (args.length == 1) {
                l.add("add");
            }
            return l;
        }
        return null;
    }

    private void registerMapInHand(Player registrar) {
        PlayerInventory inventory = registrar.getInventory();
        ItemStack itemInHand = inventory.getItemInMainHand();
        if (itemInHand.getType() != Material.MAP) {
            plugin.getLogger().warning("Player tried to register not a map");
            return;
        }

        MapMeta meta = (MapMeta) itemInHand.getItemMeta();
        if (!meta.hasMapView()) {
            plugin.getLogger().warning("Map has no map view");
            return;
        }
        int mapId = meta.getMapId();

        MapRecord newRecord = new MapRecord(mapId, registrar.getDisplayName(), registrar.getUniqueId().toString());

        if(plugin.getRegistry().register(newRecord)) {
            registrar.sendMessage("success");
        } else {
            registrar.sendMessage("fail");
        }
    }
}
