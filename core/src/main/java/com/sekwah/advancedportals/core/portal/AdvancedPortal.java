package com.sekwah.advancedportals.core.portal;

import com.google.gson.annotations.SerializedName;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sekwah41
 */
public class AdvancedPortal implements TagTarget {

    @Inject
    transient TagRegistry tagRegistry;

    @SerializedName("max")
    private BlockLocation maxLoc;

    @SerializedName("min")
    private BlockLocation minLoc;

    @SerializedName("t")
    private String[] triggerBlocks = {"PORTAL"};

    @SerializedName("a")
    private HashMap<String, String[]> args = new HashMap<>();

    public AdvancedPortal(BlockLocation minLoc, BlockLocation maxLoc) {
        this.updateBounds(minLoc, maxLoc);
    }

    public BlockLocation getMaxLoc() {
        return this.maxLoc;
    }

    public BlockLocation getMinLoc() {
        return this.minLoc;
    }

    @Override
    public String[] getArgValues(String argName) {
        return this.args.get(argName);
    }

    @Override
    public void setArgValues(String argName, String[] argValues) {
        this.args.put(argName, argValues);
    }

    @Override
    public void addArg(String argName, String argValues) {

    }

    @Override
    public void removeArg(String arg) {
        this.args.remove(arg);
    }

    /**
     * Updates the bounds of the portal based on the provided locations.
     *
     * @param loc1 The first location.
     * @param loc2 The second location.
     */
    public void updateBounds(BlockLocation loc1, BlockLocation loc2) {
        int minX = Math.min(loc1.posX, loc2.posX);
        int minY = Math.min(loc1.posY, loc2.posY);
        int minZ = Math.min(loc1.posZ, loc2.posZ);

        int maxX = Math.max(loc1.posX, loc2.posX);
        int maxY = Math.max(loc1.posY, loc2.posY);
        int maxZ = Math.max(loc1.posZ, loc2.posZ);

        this.minLoc = new BlockLocation(loc1.worldName, minX, minY, minZ);
        this.maxLoc = new BlockLocation(loc2.worldName, maxX, maxY, maxZ);
    }

    public boolean hasTriggerBlock(String blockMaterial) {
        for(String triggerBlock : triggerBlocks) {
            if(blockMaterial.equals(triggerBlock)) {
                return true;
            }
        }
        return false;
    }

    public boolean activate(PlayerContainer player) {
        ActivationData data = new ActivationData();
        DataTag[] portalTags = new DataTag[args.size()];
        int i = 0;
        for(Map.Entry<String, String[]> entry : args.entrySet()) {
            portalTags[i++] = new DataTag(entry.getKey(), entry.getValue());
        }

        for(DataTag portalTag : portalTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            if(activationHandler != null) {
                activationHandler.preActivated(this, player, data, this.getArgValues(portalTag.NAME));
            }
        }
        for(DataTag portalTag : portalTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            if(activationHandler != null) {
                activationHandler.activated(this, player, data, this.getArgValues(portalTag.NAME));
            }
        }
        for(DataTag portalTag : portalTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            if(activationHandler != null) {
                activationHandler.postActivated(this, player, data, this.getArgValues(portalTag.NAME));
            }
        }
        return true;
    }

    public boolean isLocationInPortal(PlayerLocation playerLocation) {
        return this.isLocationInPortal(playerLocation, 0);
    }

    public boolean isLocationInPortal(PlayerLocation playerLocation, int additionalArea) {
        double playerX = playerLocation.getPosX();
        double playerY = playerLocation.getPosY();
        double playerZ = playerLocation.getPosZ();

        return playerX >= this.minLoc.posX - additionalArea &&
                playerX < this.maxLoc.posX + 1 + additionalArea &&
                playerY >= this.minLoc.posY - additionalArea &&
                playerY < this.maxLoc.posY + 1 + additionalArea &&
                playerZ >= this.minLoc.posZ - additionalArea &&
                playerZ < this.maxLoc.posZ + 1 + additionalArea;
    }


    public void setArgValues(DataTag portalTag) {
        this.setArgValues(portalTag.NAME, portalTag.VALUES);
    }

    public ArrayList<DataTag> getArgs() {
        ArrayList<DataTag> tagList = new ArrayList<>();
        for(Map.Entry<String, String[]> entry : this.args.entrySet()){
            tagList.add(new DataTag(entry.getKey(), entry.getValue()));
        }
        return tagList;
    }

    public void setTriggerBlocks(String[] triggerBlocks) {
        this.triggerBlocks = triggerBlocks;
    }

    public String[] getTriggerBlocks() {
        return triggerBlocks;
    }
}
