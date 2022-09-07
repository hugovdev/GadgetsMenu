package me.hugo.gadgetsmenu.gadget.action.list;

import me.hugo.gadgetsmenu.GadgetsMenu;
import me.hugo.gadgetsmenu.gadget.action.GadgetAction;
import me.hugo.gadgetsmenu.player.GadgetPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class ChickenShooterAction implements GadgetAction, Listener {

    @Override
    public boolean onLeftClick(GadgetsMenu main, GadgetPlayer player) {
        Egg egg = player.getPlayer().launchProjectile(Egg.class);

        egg.setMetadata("explosive", new FixedMetadataValue(main, "explosive"));

        return true;
    }

    @Override
    public boolean onRightClick(GadgetsMenu main, GadgetPlayer player) {
        Chicken chicken = (Chicken) player.getPlayer().getWorld().spawnEntity(player.getPlayer().getEyeLocation(), EntityType.CHICKEN);
        chicken.setInvulnerable(true);
        chicken.setVelocity(player.getPlayer().getLocation().getDirection().multiply(1.2f));

        Bukkit.getScheduler().runTaskLater(main, task -> {
            Location deathLocation = chicken.getLocation();
            deathLocation.getWorld().playSound(deathLocation, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
            deathLocation.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, deathLocation, 1);

            chicken.remove();
        }, 10L);

        return true;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!event.getEntity().hasMetadata("explosive")) return;

        Location hitLocation = event.getEntity().getLocation();
        event.getEntity().remove();

        hitLocation.getWorld().playSound(hitLocation, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
        hitLocation.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, hitLocation, 1);
    }
}
