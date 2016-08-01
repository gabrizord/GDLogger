package me.GabrielDev.GDLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MortesLoginSign implements Listener {

	Main plugin;

	public MortesLoginSign(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void DeathLog(PlayerDeathEvent a) throws Exception {
		Player p1 = a.getEntity();
		String playerName = p1.getName();
		String worldName = p1.getWorld().getName();
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar now = Calendar.getInstance();
		PrintWriter logMorte = new PrintWriter(
				new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + "logMorte.log", true)));
		if (p1.getKiller() instanceof Player) {
			Player p2 = p1.getKiller();
			String player2Name = p2.getName();
			String worldName2 = p2.getWorld().getName();
			SimpleDateFormat fmt12 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
			Calendar now1 = Calendar.getInstance();
			if (p2.hasPermission("GDLogger.matou") || p2.hasPermission("GDLogger.usar")) {
				logMorte.println("[" + fmt12.format(now1.getTime()) + "] " + player2Name + " Matou em " + worldName2
						+ " X:" + (int) p2.getLocation().getX() + " Y:" + (int) p2.getLocation().getY() + " Z:"
						+ (int) p2.getLocation().getZ());
				logMorte.close();
			}
		} else {
			if (p1.hasPermission("GDLogger.morreu") || p1.hasPermission("GDLogger.usar")) {
				logMorte.println("[" + fmt.format(now.getTime()) + "] " + playerName + " Morreu causa nula, em: "
						+ worldName + " X:" + (int) p1.getLocation().getX() + " Y:" + (int) p1.getLocation().getY()
						+ " Z:" + (int) p1.getLocation().getZ());
				logMorte.close();
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void QuitLog(PlayerQuitEvent b) throws Exception {
		Player p3 = b.getPlayer();
		String playerName = b.getPlayer().getName();
		String worldName = p3.getWorld().getName();
		SimpleDateFormat fmt1 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar now = Calendar.getInstance();
		PrintWriter logQuit = new PrintWriter(
				new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + "logQuit.log", true)));
		if (p3.hasPermission("GDLogger.quit") || p3.hasPermission("GDLogger.usar")) {
			logQuit.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " desconectou em World: " + worldName
					+ " X:" + (int) p3.getLocation().getX() + " Y:" + (int) p3.getLocation().getY() + " Z:"
					+ (int) p3.getLocation().getZ());
			logQuit.close();
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void JoinLog(PlayerJoinEvent b) throws Exception {
		Player p4 = b.getPlayer();
		String ip = p4.getServer().getIp();
		String playerName = b.getPlayer().getName();
		String worldName = p4.getWorld().getName();
		SimpleDateFormat fmt1 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar now = Calendar.getInstance();
		PrintWriter logQuit = new PrintWriter(
				new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + "logQuit.log", true)));
		if (p4.hasPermission("GDLogger.join") || p4.hasPermission("GDLogger.usar")) {
			logQuit.println("[" + fmt1.format(now.getTime()) + "] " + playerName + "[" + ip + "] Conectou em World: "
					+ worldName + " X:" + (int) p4.getLocation().getX() + " Y:" + (int) p4.getLocation().getY() + " Z:"
					+ (int) p4.getLocation().getZ());
			logQuit.close();
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void SignLog(SignChangeEvent a) throws Exception {
		Player p = a.getPlayer();
		String playerName = a.getPlayer().getName();
		String worldName = p.getWorld().getName();
		Location bloco = a.getBlock().getLocation();
		SimpleDateFormat fmt1 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar now = Calendar.getInstance();
		PrintWriter logSign = new PrintWriter(
				new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + "logSign.log", true)));
		if (Main.getMain().getConfig().getBoolean("LogDoPlayer.LogSign")
				&& Main.getMain().getConfig().getBoolean("PlayerLog") && plugin.PlayerNick.contains(p.getName())) {
			String StaffFile = p.getName() + ".log";
			PrintWriter StaffPlayer = new PrintWriter(
					new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + StaffFile, true)));
			if (a.getLine(0).contains("AdminShop")) {
				StaffPlayer.println("[" + fmt1.format(now.getTime()) + "] " + playerName
						+ " criou uma Placa ADMINSHOP em " + "Mundo: " + worldName + " X: " + bloco.getBlockX() + " Y: "
						+ bloco.getBlockY() + " Z: " + bloco.getBlockZ());
				StaffPlayer.close();
			} else {
				StaffPlayer.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " criou uma Placa em "
						+ "Mundo: " + worldName + " X: " + bloco.getBlockX() + " Y: " + bloco.getBlockY() + " Z: "
						+ bloco.getBlockZ());
				StaffPlayer.close();
			}
		} else if (p.hasPermission("GDLogger.sign") || p.hasPermission("GDLogger.usar")) {
			logSign.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " Criou uma placa em: " + "Mundo: "
					+ worldName + " X: " + bloco.getBlockX() + " Y: " + bloco.getBlockY() + " Z: " + bloco.getBlockZ());
			logSign.println("[" + fmt1.format(now.getTime()) + "] " + "Linha 1 - " + a.getLine(0));
			logSign.println("[" + fmt1.format(now.getTime()) + "] " + "Linha 2 - " + a.getLine(1));
			logSign.println("[" + fmt1.format(now.getTime()) + "] " + "Linha 3 - " + a.getLine(2));
			logSign.println("[" + fmt1.format(now.getTime()) + "] " + "Linha 4 - " + a.getLine(3));
			logSign.println("");
			logSign.close();
		}
	}
}
