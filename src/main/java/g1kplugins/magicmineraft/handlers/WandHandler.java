package g1kplugins.magicmineraft.handlers;


import g1kplugins.magicmineraft.MagicMineraft;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;


public class WandHandler implements Listener {
    private final MagicMineraft plugin;
    public WandHandler(MagicMineraft plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onWandSpellCast(PlayerInteractEvent event)
    {
        if(!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(event.getHand() != EquipmentSlot.HAND) return;

        Player caster = event.getPlayer();
        if (caster.getInventory().getItemInMainHand().getType() != Material.STICK) return;

        Location spellLocation = caster.getLocation().add(0,2,0);
        Vector spellDirection = spellLocation.getDirection().normalize().multiply(0.5f);

        Particle.DustTransition transition = new Particle.DustTransition( Color.fromBGR(0,0,0),Color.GREEN,1.5f);

        double radius = 2.5;
        boolean didHit = false;
        for(int i = 0; i < 45 && !didHit; ++i)
        {
            spellLocation.add(spellDirection);
            caster.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, spellLocation,4,transition);

            Optional<Entity> closestEntity = spellLocation.getWorld().getNearbyEntities(spellLocation,radius,radius,radius).stream()
                    .filter(entity -> entity.getLocation().distance(spellLocation) <= 1.75 && !entity.equals(caster))
                    .min(Comparator.comparingDouble(entity -> entity.getLocation().distance(spellLocation)));

            if (closestEntity.isPresent())
            {
                didHit = true;
                Bukkit.getLogger().info("WandHandler::onWandSpellCast - in radius!");
                spellLocation.getWorld().strikeLightning( closestEntity.get().getLocation());
            }
        }

    }
}
