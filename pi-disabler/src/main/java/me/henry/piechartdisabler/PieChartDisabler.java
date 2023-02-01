package me.henry.piechartdisabler;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class PieChartDisabler extends JavaPlugin implements Listener {

    private Set<Player> allowedPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        if (event.getNewGameMode().equals(GameMode.SPECTATOR) && !allowedPlayers.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        if (event.isSneaking() && !allowedPlayers.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("pioverride") && args.length == 1) {
            Player player = Bukkit.getPlayer(args[0]);
            if (player != null) {
                allowedPlayers.add(player);
                sender.sendMessage(String.format("Player %s is now allowed to access the pie chart", player.getName()));
            } else {
                sender.sendMessage(String.format("Player %s not found", args[0]));
            }
            return true;
        }
        return false;
    }
}
