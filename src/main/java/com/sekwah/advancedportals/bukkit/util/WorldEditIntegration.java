package com.sekwah.advancedportals.bukkit.util;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.regions.selector.CuboidRegionSelector;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldEditIntegration {
    private static Region getCurrentSelection(Player player) {
        LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
        try {
            return localSession.getSelection(BukkitAdapter.adapt(player.getWorld()));
        } catch (IncompleteRegionException e) {
            return null;
        }
    }

    public static boolean validateSelection(Player player) {
        return getCurrentSelection(player) instanceof CuboidRegion;
    }

    public static Location getPos1(Player player) {
        Region currentSelection = getCurrentSelection(player);
        if (!(currentSelection instanceof CuboidRegion)) return null;
        return BukkitAdapter.adapt(player.getWorld(), ((CuboidRegion) currentSelection).getPos1());
    }

    public static Location getPos2(Player player) {
        Region currentSelection = getCurrentSelection(player);
        if (currentSelection == null) return null;;
        return BukkitAdapter.adapt(player.getWorld(), ((CuboidRegion) currentSelection).getPos2());
    }

    public static void explainRegion(Player player, Location pos1, Location pos2) {
        LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
        RegionSelector selector = new CuboidRegionSelector(BukkitAdapter.adapt(player.getWorld()), BukkitAdapter.asBlockVector(pos1), BukkitAdapter.asBlockVector(pos2));
        localSession.setRegionSelector(BukkitAdapter.adapt(player.getWorld()), selector);
        selector.explainRegionAdjust(BukkitAdapter.adapt(player), localSession);

    }
}
