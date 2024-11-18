package com.sekwah.advancedportals.core.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.services.PlayerDataServices;
import com.sekwah.advancedportals.core.tags.TriggerBlockTag;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import com.sekwah.advancedportals.core.warphandler.TriggerType;
import java.util.*;

/**
 * @author sekwah41
 */
public class AdvancedPortal implements TagTarget {
    @Inject
    private transient TagRegistry tagRegistry;

    private BlockLocation maxLoc;

    private BlockLocation minLoc;

    private final HashMap<String, String[]> args = new HashMap<>();

    private transient List<DataTag> portalTags = new ArrayList<>();

    private transient boolean isSorted = false;

    @Inject
    private transient PlayerDataServices playerDataServices;

    @Inject
    transient ConfigRepository configRepository;

    public AdvancedPortal() {
        this.minLoc = new BlockLocation();
        this.maxLoc = new BlockLocation();
    }

    public AdvancedPortal(BlockLocation minLoc, BlockLocation maxLoc,
                          TagRegistry tagRegistry,
                          PlayerDataServices playerDataServices) {
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
        this.isSorted = false;
        this.args.put(argName, argValues);
    }

    @Override
    public void addArg(String argName, String argValues) {
        // TODO need to add the ability to add args after creation
    }

    public void updatePortalTagList() {
        portalTags.clear();
        int i = 0;
        for (Map.Entry<String, String[]> entry : args.entrySet()) {
            this.portalTags.add(new DataTag(entry.getKey(), entry.getValue()));
        }

        // sort the tags by priority
        this.portalTags.sort(Comparator.comparingInt(o -> {
            var tag = tagRegistry.getTag(o.NAME);
            if (tag instanceof Tag.OrderPriority tagPriority) {
                return tagPriority.getPriority().ordinal();
            } else {
                return Tag.Priority.NORMAL.ordinal();
            }
        }));
        isSorted = true;
    }

    @Override
    public void removeArg(String arg) {
        this.isSorted = false;
        this.args.remove(arg);
    }

    /**
     * Updates the bounds of the portal based on the provided locations.
     *
     * @param loc1 The first location.
     * @param loc2 The second location.
     */
    public void updateBounds(BlockLocation loc1, BlockLocation loc2) {
        int minX = Math.min(loc1.getPosX(), loc2.getPosX());
        int minY = Math.min(loc1.getPosY(), loc2.getPosY());
        int minZ = Math.min(loc1.getPosZ(), loc2.getPosZ());

        int maxX = Math.max(loc1.getPosX(), loc2.getPosX());
        int maxY = Math.max(loc1.getPosY(), loc2.getPosY());
        int maxZ = Math.max(loc1.getPosZ(), loc2.getPosZ());

        this.minLoc = new BlockLocation(loc1.getWorldName(), minX, minY, minZ);
        this.maxLoc = new BlockLocation(loc2.getWorldName(), maxX, maxY, maxZ);
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
     * @param player        The player on the server attempting to use an
     *     advanced
     *                      portal
     * @param triggerType   The type of trigger that activated the portal
     * @return Whether the portal was successfully activated
     */
    public ActivationResult activate(PlayerContainer player,
                                     TriggerType triggerType) {
        if (!isSorted) {
            updatePortalTagList();
        }

        var playerData = playerDataServices.getPlayerData(player);

        if (playerData.hasJoinCooldown()) {
            var cooldown =
                (int) Math.ceil(playerData.getJoinCooldownLeft() / 1000D);
            player.sendMessage(Lang.translateInsertVariables(
                "portal.cooldown.join", cooldown,
                Lang.translate(cooldown == 1 ? "time.second"
                                             : "time.seconds")));
            if (configRepository.playFailSound()) {
                var rand = new Random();
                player.playSound("block.portal.travel", 0.05f,
                                 rand.nextFloat() * 0.4F + 0.8F);
            }
            return ActivationResult.FAILED_DO_KNOCKBACK;
        }

        ActivationData data = new ActivationData(triggerType);

        for (DataTag portalTag : this.portalTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(
                portalTag.NAME, Tag.TagType.PORTAL);
            if (activationHandler != null) {
                var preActivated = activationHandler.preActivated(
                    this, player, data, this.getArgValues(portalTag.NAME));

                if (!preActivated) {
                    if (activationHandler
                            instanceof Tag.DenyBehavior denyBehavior
                        && denyBehavior.getDenyBehavior()
                            == Tag.DenyBehavior.Behaviour.SILENT) {
                        return ActivationResult.FAILED_DO_NOTHING;
                    }
                    return ActivationResult.FAILED_DO_KNOCKBACK;
                }
            }
        }
        for (DataTag portalTag : this.portalTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(
                portalTag.NAME, Tag.TagType.PORTAL);
            if (activationHandler != null
                && !activationHandler.activated(
                    this, player, data, this.getArgValues(portalTag.NAME))) {
                return ActivationResult.FAILED_DO_KNOCKBACK;
            }
        }
        for (DataTag portalTag : this.portalTags) {
            Tag.Activation activationHandler = tagRegistry.getActivationHandler(
                portalTag.NAME, Tag.TagType.PORTAL);
            if (activationHandler != null) {
                activationHandler.postActivated(
                    this, player, data, this.getArgValues(portalTag.NAME));
            }
        }
        if (data.hasActivated()) {
            playerData.setPortalBlockCooldown(1000);
            playerData.setInPortal(this.getName());
            return ActivationResult.SUCCESS;
        }
        return ActivationResult.FAILED_DO_KNOCKBACK;
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
        double playerX = loc.getPosX();
        double playerY = loc.getPosY();
        double playerZ = loc.getPosZ();

        return Objects.equals(loc.getWorldName(), this.minLoc.getWorldName())
            && playerX >= this.minLoc.getPosX() - additionalArea
            && playerX < this.maxLoc.getPosX() + 1 + additionalArea
            && playerY >= this.minLoc.getPosY() - additionalArea
            && playerY < this.maxLoc.getPosY() + 1 + additionalArea
            && playerZ >= this.minLoc.getPosZ() - additionalArea
            && playerZ < this.maxLoc.getPosZ() + 1 + additionalArea;
    }

    public void setArgValues(DataTag portalTag) {
        this.setArgValues(portalTag.NAME, portalTag.VALUES);
    }

    public ArrayList<DataTag> getArgs() {
        ArrayList<DataTag> tagList = new ArrayList<>();
        for (Map.Entry<String, String[]> entry : this.args.entrySet()) {
            tagList.add(new DataTag(entry.getKey(), entry.getValue()));
        }
        return tagList;
    }

    public boolean isTriggerBlock(String blockMaterial) {
        var triggerBlocks = this.getArgValues(TriggerBlockTag.TAG_NAME);
        if (triggerBlocks != null) {
            for (String triggerBlock : triggerBlocks) {
                if (blockMaterial.equals(triggerBlock)) {
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
