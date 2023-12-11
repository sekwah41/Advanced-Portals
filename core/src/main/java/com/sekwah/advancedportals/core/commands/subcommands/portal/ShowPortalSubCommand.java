package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerTempData;
import com.sekwah.advancedportals.core.services.PortalTempDataServices;
import com.sekwah.advancedportals.core.util.Debug;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.core.util.Lang;

import java.awt.*;
import java.util.List;

/**
 * This will be different from the old show command and I believe it is 1.16+ till the latest version as of writing this.
 */
public class ShowPortalSubCommand implements SubCommand, SubCommand.SubCommandOnInit {

    @Inject
    PortalTempDataServices tempDataServices;

    @Inject
    GameScheduler gameScheduler;

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
        return Lang.translate("command.portal.show.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.portal.show.detailedhelp");
    }

    @Override
    public void registered() {
        gameScheduler.intervalTickEvent("show_portal", () -> {
            System.out.println("check visibility");
        }, 1, 20);
    }
}
