package com.sekwah.advancedportals.core.portal;

import com.google.gson.annotations.SerializedName;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.services.PlayerDataServices;
import com.sekwah.advancedportals.core.tags.activation.TriggerBlockTag;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.*;

/**
 * @author sekwah41
 */
public class AdvancedPortal implements TagTarget {

    @Inject
    private transient TagRegistry tagRegistry;

    @SerializedName("max")
    private BlockLocation maxLoc;

    @SerializedName("min")
    private BlockLocation minLoc;

    @SerializedName("a")
    private HashMap<String, String[]> args = new HashMap<>();

    @Inject
    private transient PlayerDataServices playerDataServices;

    @Inject
    transient ConfigRepository configRepository;

    public AdvancedPortal(BlockLocation minLoc, BlockLocation maxLoc, TagRegistry tagRegistry, PlayerDataServices playerDataServices) {
        this.tagRegistry = tagRegistry;
        this.playerDataServices = playerDataServices;
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

    /*public boolean hasTriggerBlock(String blockMaterial) {
        for(String triggerBlock : triggerBlocks) {
            if(blockMaterial.equals(triggerBlock)) {
                return true;
            }
        }
        return false;
    }*/

    /**
     *
     * @param player
     * @param moveActivated if the portal was activated by a move event (won't trigger knockback)
     * @return
     */
    public boolean activate(PlayerContainer player, boolean moveActivated) {
        var playerData = playerDataServices.getPlayerData(player);
        if(playerData.isInPortal()) return false;
        playerData.setInPortal(true);
        if(playerData.hasJoinCooldown()) {
            var cooldown = (int) Math.ceil(playerData.getJoinCooldownLeft() / 1000D);
            player.sendMessage(Lang.translateInsertVariables("portal.cooldown.join", cooldown,
                    Lang.translate(cooldown == 1 ? "time.second" : "time.seconds")));
            if(configRepository.playFailSound()) {
                player.playSound("block.portal.travel", 0.05f, new Random().nextFloat() * 0.4F + 0.8F);
            }
            return false;
        }

        ActivationData data = new ActivationData(moveActivated);
        DataTag[] portalTags = new DataTag[args.size()];
        int i = 0;
        for(Map.Entry<String, String[]> entry : args.entrySet()) {
            portalTags[i++] = new DataTag(entry.getKey(), entry.getValue());
        }

        for(DataTag portalTag : portalTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            if(activationHandler != null) {
                if(!activationHandler.preActivated(this, player, data, this.getArgValues(portalTag.NAME))) {
                    return false;
                }
            }
        }
        for(DataTag portalTag : portalTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            if(activationHandler != null) {
                if(!activationHandler.activated(this, player, data, this.getArgValues(portalTag.NAME))) {
                    return false;
                }
            }
        }
        for(DataTag portalTag : portalTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(portalTag.NAME);
            if(activationHandler != null) {
                activationHandler.postActivated(this, player, data, this.getArgValues(portalTag.NAME));
            }
        }
        if(data.hasActivated()) {
            playerData.setNetherPortalCooldown(1000);
            return true;
        }
        return false;
    }


    public boolean isLocationInPortal(BlockLocation loc) {
        return this.isLocationInPortal(loc, 0);
    }

    public boolean isLocationInPortal(PlayerLocation loc) {
        return this.isLocationInPortal(loc.toBlockPos(), 0);
    }


    public boolean isLocationInPortal(PlayerLocation loc, int additionalArea) {
        return this.isLocationInPortal(loc.toBlockPos(), additionalArea);
    }

    public boolean isLocationInPortal(BlockLocation loc, int additionalArea) {
        double playerX = loc.posX;
        double playerY = loc.posY;
        double playerZ = loc.posZ;

        return Objects.equals(loc.worldName, this.minLoc.worldName) && playerX >= this.minLoc.posX - additionalArea &&
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

    public boolean isTriggerBlock(String blockMaterial) {
        var triggerBlocks = this.getArgValues(TriggerBlockTag.TAG_NAME);
        if(triggerBlocks != null) {
            for(String triggerBlock : triggerBlocks) {
                if(blockMaterial.equals(triggerBlock)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getName() {
        return getArgValues("name")[0];
    }
}
