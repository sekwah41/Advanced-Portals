package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerTempData;
import com.sekwah.advancedportals.core.services.PortalTempDataServices;
import com.sekwah.advancedportals.core.util.Debug;
import com.sekwah.advancedportals.core.util.FriendlyDataOutput;
import com.sekwah.advancedportals.core.util.Lang;

import java.awt.*;
import java.util.List;

public class DebugPortalsSubCommand implements SubCommand {

    @Inject
    PortalTempDataServices tempDataServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        sender.sendMessage("Debug");
        if(sender.getPlayerContainer() != null) {
            PlayerContainer playerContainer = sender.getPlayerContainer();
            PlayerTempData tempData = tempDataServices.getPlayerTempData(playerContainer);
            if(tempData.getPos1() != null) {
                Debug.addMarker(sender.getPlayerContainer(), tempData.getPos1(), "Pos1", new Color(0, 255, 0), 1000 * 10);
            }
            if(tempData.getPos2() != null) {
                Debug.addMarker(sender.getPlayerContainer(), tempData.getPos2(), "Pos2", new Color(255, 0, 0), 1000 * 10);
            }

            if (tempData.getPos1() != null && tempData.getPos2() != null) {
                int minX = Math.min(tempData.getPos1().posX, tempData.getPos2().posX);
                int minY = Math.min(tempData.getPos1().posY, tempData.getPos2().posY);
                int minZ = Math.min(tempData.getPos1().posZ, tempData.getPos2().posZ);

                int maxX = Math.max(tempData.getPos1().posX, tempData.getPos2().posX);
                int maxY = Math.max(tempData.getPos1().posY, tempData.getPos2().posY);
                int maxZ = Math.max(tempData.getPos1().posZ, tempData.getPos2().posZ);

                for (int x = minX; x <= maxX; x++) {
                    for (int y = minY; y <= maxY; y++) {
                        for (int z = minZ; z <= maxZ; z++) {
                            if ((x == minX || x == maxX) && (y == minY || y == maxY || z == minZ || z == maxZ) ||
                                    (y == minY || y == maxY) && (x == minX || x == maxX || z == minZ || z == maxZ) ||
                                    (z == minZ || z == maxZ) && (x == minX || x == maxX || y == minY || y == maxY)) {

                                var pos = new BlockLocation(tempData.getPos1().worldName, x, y, z);
                                if (pos.equals(tempData.getPos1()) || pos.equals(tempData.getPos2()))
                                    continue;
                                Debug.addMarker(sender.getPlayerContainer(), pos, "", new Color(255, 0, 0, 100), 1000 * 10);
                            }
                        }
                    }
                }
            }
            /*int minX = Math.min(tempData.getPos1().posX, tempData.getPos2().posX);
            int minY = Math.min(tempData.getPos1().posY, tempData.getPos2().posY);
            int minZ = Math.min(tempData.getPos1().posZ, tempData.getPos2().posZ);

            int maxX = Math.max(tempData.getPos1().posX, tempData.getPos2().posX);
            int maxY = Math.max(tempData.getPos1().posY, tempData.getPos2().posY);
            int maxZ = Math.max(tempData.getPos1().posZ, tempData.getPos2().posZ);

            FriendlyDataOutput out = new FriendlyDataOutput();

            out.writeUtf("minecraft:overworld");

            // Bounding Box
            out.writeInt(minX);
            out.writeInt(minY);
            out.writeInt(minZ);
            out.writeInt(maxX);
            out.writeInt(maxY);
            out.writeInt(maxZ);

            // Count
            out.writeInt(0);

            playerContainer.sendPacket("minecraft:debug/structures", out.toByteArray());*/
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.portal.list.debug");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.portal.list.debug");
    }
}
