package me.GabrielDev.GDLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatCommands implements Listener {

	Main plugin;

	public ChatCommands(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void ChatLog(AsyncPlayerChatEvent a) throws Exception {
		Player p = a.getPlayer();
		String msg = a.getMessage();
		String playerName = p.getName();
		SimpleDateFormat fmt1 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar now = Calendar.getInstance();
		PrintWriter logChat = new PrintWriter(
				new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + "logChat.log", true)));
		if (Main.getMain().getConfig().getBoolean("LogDoPlayer.LogChat")
				&& Main.getMain().getConfig().getBoolean("PlayerLog") && plugin.PlayerNick.contains(p.getName())) {
			String StaffFile = p.getName() + ".log";
			PrintWriter StaffPlayer = new PrintWriter(
					new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + StaffFile, true)));
			StaffPlayer.println(
					"[" + fmt1.format(now.getTime()) + "] " + "O Staff " + playerName + " digitou no Chat : " + msg);
			StaffPlayer.close();
		} else if (p.hasPermission("GDLogger.chat") || p.hasPermission("GDLogger.usar")) {
			logChat.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " digitou no Chat: " + msg);
			logChat.close();
		}

	}

	@EventHandler(priority = EventPriority.HIGH)
	public void CommandLog(PlayerCommandPreprocessEvent a) throws Exception {
		Player p = a.getPlayer();
		String msg = a.getMessage();
		String[] args = msg.split(" ");
		String playerName = p.getName();
		SimpleDateFormat fmt1 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar now = Calendar.getInstance();
		PrintWriter logCmd = new PrintWriter(
				new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + "logComando.log", true)));
		if (Main.getMain().PlayerMonitorComando.contains(playerName)) {
			for (String string : Main.getMain().AdminNick) {
				Player p5 = Bukkit.getPlayer(string);
				if (args[0].equalsIgnoreCase("/login")) {
					if (Main.getMain().getConfig().getBoolean("AnularSenha")) {
						p5.sendMessage("§6[Monitorar] §a" + playerName + " digitou: /login");
					}
				} else {
					p5.sendMessage("§6[Monitorar] §a" + playerName + " digitou: " + msg);
				}

			}
		}
		if (Main.getMain().getConfig().getBoolean("LogDoPlayer.LogComando")
				&& Main.getMain().getConfig().getBoolean("PlayerLog") && plugin.PlayerNick.contains(p.getName())) {
			String StaffFile = p.getName() + ".log";
			PrintWriter StaffPlayer = new PrintWriter(
					new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + StaffFile, true)));
			StaffPlayer.println(
					"[" + fmt1.format(now.getTime()) + "] " + "O Staff " + playerName + " digitou o COMANDO : " + msg);
			StaffPlayer.close();
		} else if (p.hasPermission("GDLogger.cmd") || p.hasPermission("GDLogger.usar")) {
			if (args[0].equalsIgnoreCase("/g")) {
				if (Main.getMain().getConfig().getBoolean("ChatGlobal")) {
					PrintWriter logChat = new PrintWriter(new BufferedWriter(
							new FileWriter(plugin.getDataFolder() + File.separator + "logChat.log", true)));
					logChat.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " digitou no chat GLOBAL: "
							+ args[1]);
					logChat.close();
				} else {
					logCmd.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " usou o COMANDO: " + msg);
					logCmd.close();
				}
			} else if (args[0].equalsIgnoreCase("/login")) {
				if (Main.getMain().getConfig().getBoolean("AnularSenha")) {
					logCmd.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " usou o COMANDO: /login");
					logCmd.close();
				} else {
					if (args.length <= 2) {
						logCmd.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " logou com a senha: "
								+ args[1]);
						logCmd.close();
					}
				}
			} else {
				logCmd.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " usou o COMANDO: " + msg);
				logCmd.close();
			}

		}
	}
}
