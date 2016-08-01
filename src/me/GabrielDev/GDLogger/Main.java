package me.GabrielDev.GDLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	private static Main m;
	public static PluginManager pm;
	MortesLoginSign morte = new MortesLoginSign(this);
	BlocosAndItens blocos = new BlocosAndItens(this);
	ChatCommands eventochat = new ChatCommands(this);
	public List<String> PlayerMonitorBloco = new ArrayList<String>();
	public List<String> PlayerMonitorComando = new ArrayList<String>();
	public List<String> PlayerNick = new ArrayList<String>();
	public List<String> AdminNick = new ArrayList<String>();

	@Override
	public void onEnable() {
		m = this;
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		sender.sendMessage("§3[GDLogger§3] §bPlugin ativado! Feito por §4GabrielDev!");
		sender.sendMessage("§3[GDLogger§3] §bVersão " + getDescription().getVersion());
		sender.sendMessage("§3[GDLogger§3] §bAcesse: http://gamersboard.com.br/");
		getServer().getPluginManager().registerEvents(this.blocos, this);
		getServer().getPluginManager().registerEvents(this.morte, this);
		getServer().getPluginManager().registerEvents(this.eventochat, this);
		adminCheck();
		playerCheck();
		File file = new File(getDataFolder(), "config.yml");
		if (!file.exists()) {
			try {
				saveResource("config_template.yml", false);
				File file2 = new File(getDataFolder(), "config_template.yml");
				file2.renameTo(new File(getDataFolder(), "config.yml"));
				sender.sendMessage("§3[GDLogger§3] §bConfig Gerada!");
			} catch (Exception e) {
				sender.sendMessage("§3[GDLogger§3] §bFalha ao criar a Config!");
			}
		}
	}

	@Override
	public void onDisable() {
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		sender.sendMessage("§3[GDLogger§3] §bPlugin desativado! Feito por §4GabrielDev!");
		HandlerList.unregisterAll();
		PlayerMonitorBloco.clear();
		PlayerMonitorComando.clear();
		AdminNick.clear();
		PlayerNick.clear();
	}

	/*
	 * public List<String> PlayerNome() { List<String> w =
	 * getConfig().getStringList("PlayerNick"); return w; }
	 * 
	 * public List<String> PlayerAdmin() { List<String> b =
	 * getConfig().getStringList("AdminNick"); return b; }
	 */

	public void adminCheck() {
		for (String string : getConfig().getStringList("AdminNick")) {
			AdminNick.add(string);
		}
	}

	public void playerCheck() {
		for (String string : getConfig().getStringList("PlayerNick")) {
			PlayerNick.add(string);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("monitorar")) {
			if (!sender.hasPermission("GDLogger.admin") || !sender.hasPermission("GDLogger.monitorar")) {
				sender.sendMessage("§4Voce nao tem permissao");
				return true;
			} else {
				if (args.length != 2) {
					if (args.length == 0) {
						sender.sendMessage("§6[GDLogger] Opcoes de Monitorar:");
						sender.sendMessage("§6/monitorar [player] [tipo de log]");
						sender.sendMessage("§6/monitorar [player] clear");
						sender.sendMessage("§6/monitorar list [tipo de log]");
						sender.sendMessage("§6LOG disponiveis: §ablocos comandos");
					} else if (args.length == 1) {
						if (args[0].equalsIgnoreCase("list")) {
							sender.sendMessage("§6/monitorar list [tipo de log]");
						} else {
							sender.sendMessage("§6[GDLogger] §4Erro no comando:");
							sender.sendMessage("§6/monitorar [player] [tipo de log]");
							sender.sendMessage("§6/monitorar [player] clear");
						}
					}
				} else {
					String playerTarget = args[0];
					if (args[1].equalsIgnoreCase("blocos")) {
						if (args[0].equalsIgnoreCase("list")) {
							if (PlayerMonitorBloco.isEmpty()) {
								sender.sendMessage("§4Nenhum player esta sendo monitorado!");
							} else {
								for (String string : PlayerMonitorBloco) {
									sender.sendMessage(string);
								}
							}
						} else {
							if (PlayerMonitorComando.contains(playerTarget)) {
								sender.sendMessage("§4So pode criar /monitorar com um tipo de LOG!");
							} else {
								if (!PlayerMonitorBloco.contains(playerTarget)) {
									PlayerMonitorBloco.add(playerTarget);
									sender.sendMessage(
											"§6O jogador: " + playerTarget + " esta sendo monitorado - Blocos");
								} else {
									sender.sendMessage("§4Esse jogador ja esta sendo monitorado! - Blocos");
								}
							}
						}
					} else if (args[1].equalsIgnoreCase("comandos")) {
						if (args[0].equalsIgnoreCase("list")) {
							if (PlayerMonitorComando.isEmpty()) {
								sender.sendMessage("§4Nenhum player esta sendo monitorado!");
							} else {
								for (String string : PlayerMonitorComando) {
									sender.sendMessage(string);
								}
							}
						} else {
							if (PlayerMonitorBloco.contains(playerTarget)) {
								sender.sendMessage("§4So pode criar /monitorar com um tipo de LOG!");
							} else {
								if (!PlayerMonitorComando.contains(playerTarget)) {
									PlayerMonitorComando.add(playerTarget);
									sender.sendMessage(
											"§6O jogador: " + playerTarget + " esta sendo monitorado - Comandos");
								} else {
									sender.sendMessage("§4Esse jogador ja esta sendo monitorado! - Comandos");
								}
							}
						}
					} else if (args[1].equalsIgnoreCase("clear")) {
						if (PlayerMonitorBloco.contains(playerTarget) || PlayerMonitorComando.contains(playerTarget)) {
							if (PlayerMonitorBloco.contains(playerTarget)) {
								PlayerMonitorBloco.remove(playerTarget);
							} else if (PlayerMonitorComando.contains(playerTarget)) {
								PlayerMonitorComando.remove(playerTarget);
							}
						}
					} else {
						sender.sendMessage("§6[GDLogger] Erro no comando:");
						sender.sendMessage("§6/monitorar [player] [tipo de log]");
						sender.sendMessage("§6/monitorar [player] clear");
						sender.sendMessage("§6LOG disponiveis: §blocos comandos");
					}
				}
			}
		}
		return false;
	}

	public static Main getMain() {
		return m;
	}
}
