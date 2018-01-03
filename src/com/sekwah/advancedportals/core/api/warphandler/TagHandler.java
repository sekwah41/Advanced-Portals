package com.sekwah.advancedportals.api.warphandler;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * Created by on 30/07/2016.
 *
 * @author sekwah41
 */
public class TagHandler {

    /**
     * When the user wants to activate the tag, not all will support this of course. its just a _ before or after the tag
     * to specify
     */
    public enum ActivatePref{
        BEFORE,
        AFTER,
        DEFAULT;
    }

    public interface Creation {

        /**
         * Example if the player does not have access to use a tag on the portal.
         *
         * @param player     if null the portal has been created by the server or a plugin
         * @param activeData
         * @param argData
         * @return if the portal can be created.
         */
        boolean portalCreated(@Nullable Player player, ActivationData activeData, String argData);

        /**
         * Example if the player does not have access to remove the portal.
         *
         * @param player     if null the portal has been destroyed by the server or a plugin
         * @param activeData
         * @param argData
         * @return if the portal can be destroyed.
         */
        boolean portalDestroyed(@Nullable Player player, ActivationData activeData, String argData);

    }

    public interface Activation {

        /**
         * Activates before the main part of portal activation.
         *
         * @param player
         * @param activeData
         * @param argData
         */
        void portalPreActivated(Player player, ActivationData activeData, String argData);

        /**
         * Activates after portal activation
         *
         * @param player
         * @param activeData
         * @param argData
         */
        void portalPostActivated(Player player, ActivationData activeData, String argData);

        /**
         * Activates if the portal is allowed from pre
         *
         * @param player
         * @param activeData
         * @param argData
         */
        void portalActivated(Player player, ActivationData activeData, String argData);

    }

    public interface TagStatus {

        /**
         * If the user has access to add the tag
         *
         * @param player
         * @param activeData
         * @param argData
         * @return if the tag will be added.
         */
        boolean tagAdded(@Nullable Player player, ActivationData activeData, String argData);

        /**
         * If the user has access to remove the tag
         *
         * @param player
         * @param activeData
         * @param argData
         * @return if the tag will be removed.
         */
        boolean tagRemoved(@Nullable Player player, ActivationData activeData, String argData);

    }

}
