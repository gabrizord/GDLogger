package me.GabrielDev.GDLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class BlocosAndItens implements Listener {

	Main plugin;

	public BlocosAndItens(Main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void PlaceLog(BlockPlaceEvent a) throws Exception {
		Player p5 = a.getPlayer();
		Block b1 = a.getBlock();
		String worldName = b1.getLocation().getWorld().getName();
		String playerName = p5.getName();
		int blocoId = b1.getTypeId();
		int blocoData = b1.getData();
		SimpleDateFormat fmt1 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar now = Calendar.getInstance();
		PrintWriter logBlocos = new PrintWriter(
				new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + "logBlocos.log", true)));
		if (Main.getMain().PlayerMonitorBloco.contains(playerName)) {
			for (String string : Main.getMain().AdminNick) {
				Player p = Bukkit.getPlayer(string);
				p.sendMessage("§6[Monitorar] §a" + playerName + " colocou o bloco: " + blocoId + ":" + blocoData);
			}
		}
		if (Main.getMain().getConfig().getBoolean("LogDoPlayer.LogColocarBloco")
				&& Main.getMain().getConfig().getBoolean("PlayerLog")
				&& Main.getMain().PlayerNick.contains(p5.getName())) {
			String StaffFile = p5.getName() + ".log";
			PrintWriter StaffPlayer = new PrintWriter(
					new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + StaffFile, true)));
			if (b1.getType() != Material.SIGN_POST) {
				StaffPlayer.println("[" + fmt1.format(now.getTime()) + "] " + "O Staff " + playerName
						+ " COLOCOU o bloco : " + blocoId + ":" + blocoData + " em: " + worldName + " X:"
						+ (int) b1.getLocation().getX() + " Y:" + (int) b1.getLocation().getY() + " Z:"
						+ (int) b1.getLocation().getZ());
				StaffPlayer.close();
			}
		} else if (p5.hasPermission("GDLogger.place") || p5.hasPermission("GDLogger.usar")) {
			if (b1.getType() != Material.SIGN_POST) {
				logBlocos.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " Colocou o bloco >> "
						+ blocoId + ":" + blocoData + " << em: " + worldName + " X:" + (int) b1.getLocation().getX()
						+ " Y:" + (int) b1.getLocation().getY() + " Z:" + (int) b1.getLocation().getZ());
				logBlocos.close();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void BreakLog(BlockBreakEvent a) throws Exception {
		Player p6 = a.getPlayer();
		Block b = a.getBlock();
		String worldName = b.getLocation().getWorld().getName();
		String playerName = p6.getName();
		int blocoId = b.getTypeId();
		int blocoData = b.getData();
		SimpleDateFormat fmt1 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar now = Calendar.getInstance();
		PrintWriter logBlocos = new PrintWriter(
				new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + "logBlocos.log", true)));
		if (Main.getMain().getConfig().getBoolean("LogDoPlayer.LogQuebraBloco")
				&& Main.getMain().getConfig().getBoolean("PlayerLog") && plugin.PlayerNick.contains(p6.getName())) {
			String StaffFile = p6.getName() + ".log";
			PrintWriter StaffPlayer = new PrintWriter(
					new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + StaffFile, true)));
			StaffPlayer
					.println("[" + fmt1.format(now.getTime()) + "] " + "O Staff " + playerName + " QUEBROU o bloco : "
							+ blocoId + ":" + blocoData + " em: " + worldName + " X:" + (int) b.getLocation().getX()
							+ " Y:" + (int) b.getLocation().getY() + " Z:" + (int) b.getLocation().getZ());
			StaffPlayer.close();
		} else if (p6.hasPermission("GDLogger.break") || p6.hasPermission("GDLogger.usar")) {
			if (b.getType() != Material.SIGN_POST) {
				logBlocos.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " Removeu o bloco >> "
						+ blocoId + ":" + blocoData + " << em: " + worldName + " X:" + (int) b.getLocation().getX()
						+ " Y:" + (int) b.getLocation().getY() + " Z:" + (int) b.getLocation().getZ());
				logBlocos.close();
			}
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void DropLog(PlayerDropItemEvent b) throws Exception {
		Player p7 = b.getPlayer();
		String playerName = b.getPlayer().getName();
		String worldName = p7.getWorld().getName();
		int id = b.getItemDrop().getItemStack().getTypeId();
		int id2 = b.getItemDrop().getItemStack().getData().getData();
		int quant = b.getItemDrop().getItemStack().getAmount();
		SimpleDateFormat fmt1 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar now = Calendar.getInstance();
		PrintWriter logItens = new PrintWriter(
				new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + "logItens.log", true)));
		if (Main.getMain().getConfig().getBoolean("LogDoPlayer.LogDrop")
				&& Main.getMain().getConfig().getBoolean("PlayerLog") && plugin.PlayerNick.contains(p7.getName())) {
			String StaffFile = p7.getName() + ".log";
			PrintWriter StaffPlayer = new PrintWriter(
					new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + StaffFile, true)));
			StaffPlayer.println("[" + fmt1.format(now.getTime()) + "] " + "O Staff " + playerName + " DROPOU " + quant
					+ " do item " + id + ":" + id2 + " em: " + worldName + " X:" + (int) p7.getLocation().getX() + " Y:"
					+ (int) p7.getLocation().getY() + " Z:" + (int) p7.getLocation().getZ());
			StaffPlayer.close();
		} else if (p7.hasPermission("GDLogger.drop") || p7.hasPermission("GDLogger.usar")) {
			logItens.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " DROPOU " + quant + " do item >> "
					+ id + ":" + id2 + " << em: " + worldName + " X:" + (int) p7.getLocation().getX() + " Y:"
					+ (int) p7.getLocation().getY() + " Z:" + (int) p7.getLocation().getZ());
			logItens.close();
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void PickLog(PlayerPickupItemEvent a) throws Exception {
		Player p = a.getPlayer();
		String playerName = a.getPlayer().getName();
		String worldName = p.getWorld().getName();
		int idP = a.getItem().getItemStack().getTypeId();
		int idP2 = a.getItem().getItemStack().getData().getData();
		int quant = a.getItem().getItemStack().getAmount();
		SimpleDateFormat fmt1 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Calendar now = Calendar.getInstance();
		PrintWriter logItens = new PrintWriter(
				new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + "logItens.log", true)));
		if (Main.getMain().getConfig().getBoolean("LogDoPlayer.LogPick")
				&& Main.getMain().getConfig().getBoolean("PlayerLog") && plugin.PlayerNick.contains(p.getName())) {
			String StaffFile = p.getName() + ".log";
			PrintWriter StaffPlayer = new PrintWriter(
					new BufferedWriter(new FileWriter(plugin.getDataFolder() + File.separator + StaffFile, true)));
			StaffPlayer.println("[" + fmt1.format(now.getTime()) + "] " + "O Staff " + playerName + " PEGOU do chao "
					+ quant + " do item " + idP + ":" + idP2 + " em: " + worldName + " X:"
					+ (int) p.getLocation().getX() + " Y:" + (int) p.getLocation().getY() + " Z:"
					+ (int) p.getLocation().getZ());
			StaffPlayer.close();
		} else if (p.hasPermission("GDLogger.pick") || p.hasPermission("GDLogger.usar")) {
			logItens.println("[" + fmt1.format(now.getTime()) + "] " + playerName + " Pegou do chao " + quant
					+ " do item >> " + idP + ":" + idP2 + " << em: " + worldName + " X:" + (int) p.getLocation().getX()
					+ " Y:" + (int) p.getLocation().getY() + " Z:" + (int) p.getLocation().getZ());
			logItens.close();
		}
	}

	/*
	 * @EventHandler(priority = EventPriority.HIGH) public void
	 * EnchantLog(EnchantItemEvent a) throws Exception { // String playerName =
	 * a.getEnchanter().getName(); Player p = a.getEnchanter(); // int item =
	 * a.getItem().getTypeId(); if
	 * (Main.getMain().getConfig().getBoolean("LogDoPlayer.LogEnchant") &&
	 * Main.getMain().getConfig().getBoolean("PlayerLog") &&
	 * plugin.PlayerNick.contains(p.getName())) { } else { if
	 * (p.hasPermission("GDLogger.enchant") || p.hasPermission("GDLogger.usar"))
	 * { ItemStack stack = a.getItem(); p.sendMessage("123"); Map<Enchantment,
	 * Integer> enchants = stack.getEnchantments(); for (Enchantment enchant :
	 * enchants.keySet()) { p.sendMessage("Encantamento: " + enchant.getName());
	 * p.sendMessage("abc"); }
	 * 
	 * }
	 * 
	 * } }
	 */
}

// SimpleDateFormat fmt1 = new SimpleDateFormat("HH:mm:ss
// dd/MM/yyyy");
// Calendar now = Calendar.getInstance();
/*
 * PrintWriter logItens = new PrintWriter(new BufferedWriter( new
 * FileWriter(plugin.getDataFolder() + File.separator + "logItens.log", true)));
 * logItens.println("[" + fmt1.format(now.getTime()) + "] " + playerName +
 * " Encantou " + " o item >> " + item + " << " + "para: " + enchantment1 + ":"
 * + level + " em: " + p.getWorld().getName() + " X:" + (int)
 * p.getLocation().getX() + " Y:" + (int) p.getLocation().getY() + " Z:" + (int)
 * p.getLocation().getZ()); logItens.close(); }
 */
