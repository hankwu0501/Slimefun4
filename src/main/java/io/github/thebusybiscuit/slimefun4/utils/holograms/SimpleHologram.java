package io.github.thebusybiscuit.slimefun4.utils.holograms;

import io.github.thebusybiscuit.cscorelib2.chat.ChatColors;
import io.github.thebusybiscuit.slimefun4.core.attributes.HologramOwner;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * This utility class provides a few static methods for modifying a simple Text-based Hologram.
 *
 * @author TheBusyBiscuit
 * @deprecated Please use the interface {@link HologramOwner} instead
 */
@Deprecated
public final class SimpleHologram {

    private SimpleHologram() {
    }

    public static void update(@Nonnull Block b, @Nonnull String name) {
        SlimefunPlugin.runSync(() -> {
            ArmorStand hologram = getArmorStand(b, true);
            hologram.setCustomName(ChatColors.color(name));
        });
    }

    public static void remove(@Nonnull Block b) {
        SlimefunPlugin.runSync(() -> {
            ArmorStand hologram = getArmorStand(b, false);

            if (hologram != null) {
                hologram.remove();
            }
        });
    }

    @Nullable
    private static ArmorStand getArmorStand(@Nonnull Block b, boolean createIfNoneExists) {
        Location l = new Location(b.getWorld(), b.getX() + 0.5, b.getY() + 0.7F, b.getZ() + 0.5);
        Collection<Entity> holograms = b.getWorld().getNearbyEntities(l, 0.2, 0.2, 0.2, SimpleHologram::isPossibleHologram);

        for (Entity n : holograms) {
            if (n instanceof ArmorStand) {
                return (ArmorStand) n;
            }
        }

        if (!createIfNoneExists) {
            return null;
        } else {
            return create(l);
        }
    }

    private static boolean isPossibleHologram(@Nonnull Entity n) {
        if (n instanceof ArmorStand) {
            ArmorStand armorstand = (ArmorStand) n;
            return armorstand.isValid() && armorstand.isSilent() && armorstand.isMarker() && !armorstand.hasGravity() && armorstand.isCustomNameVisible();
        } else {
            return false;
        }

    }

    @Nonnull
    public static ArmorStand create(@Nonnull Location l) {
        ArmorStand armorStand = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setSilent(true);
        armorStand.setMarker(true);
        armorStand.setGravity(false);
        armorStand.setBasePlate(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setRemoveWhenFarAway(false);
        return armorStand;
    }

}