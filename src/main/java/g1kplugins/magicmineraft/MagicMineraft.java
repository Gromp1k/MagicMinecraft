package g1kplugins.magicmineraft;

import g1kplugins.magicmineraft.handlers.WandHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MagicMineraft extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("MagicMineraft::onEnable()");

        Bukkit.getPluginManager().registerEvents(new WandHandler(this),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("~MagicMineraft::onDisable()");
    }
}
