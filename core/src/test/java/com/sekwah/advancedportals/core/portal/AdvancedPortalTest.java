package com.sekwah.advancedportals.core.portal;

import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdvancedPortalTest {
    @Test
    public void updateBounds_sameWorld_setsLocations() {
        AdvancedPortal portal = new AdvancedPortal();
        BlockLocation loc1 = new BlockLocation("world", 0, 0, 0);
        BlockLocation loc2 = new BlockLocation("world", 10, 10, 10);

        portal.updateBounds(loc1, loc2);

        assertEquals("world", portal.getMinLoc().getWorldName());
        assertEquals("world", portal.getMaxLoc().getWorldName());
        assertEquals(0, portal.getMinLoc().getPosX());
        assertEquals(10, portal.getMaxLoc().getPosX());
    }

    @Test
    public void updateBounds_differentWorld_throws() {
        AdvancedPortal portal = new AdvancedPortal();
        BlockLocation loc1 = new BlockLocation("world1", 0, 0, 0);
        BlockLocation loc2 = new BlockLocation("world2", 10, 10, 10);

        assertThrows(IllegalArgumentException.class, () -> portal.updateBounds(loc1, loc2));
    }
}
