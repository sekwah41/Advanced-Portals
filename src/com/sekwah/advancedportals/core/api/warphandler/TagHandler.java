package com.sekwah.advancedportals.core.api.warphandler;

import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.api.portal.PortalTagExeption;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

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

    /**
     * The events for portal creation and destroying
     */
    public interface Creation {

        /**
         * Example if the player does not have access to use a tag on the portal.
         *
         * @param player     if null the portal has been created by the server or a plugin
         * @param argData
         * @return if the portal can be created.
         * @throws PortalTagExeption message given is the reason the portal cant be made
         */
        void portalCreated(AdvancedPortal portal, PlayerContainer player, String argData) throws PortalTagExeption;

        /**
         * Example if the player does not have access to remove the portal.
         *
         * @param player     if null the portal has been destroyed by the server or a plugin
         * @param argData
         * @return if the portal can be destroyed.
         * @throws PortalTagExeption message given is the reason the portal cant be removed
         */
        void portalDestroyed(AdvancedPortal portal, PlayerContainer player, String argData) throws PortalTagExeption;

    }

    public interface Activation {

        /**
         * Activates before the main part of portal activation.
         *
         * @param player
         * @param activeData
         * @param argData
         */
        void portalPreActivated(AdvancedPortal portal, PlayerContainer player, ActivationData activeData, String argData);

        /**
         * Activates after portal activation
         *
         * @param player
         * @param activeData
         * @param argData
         */
        void portalPostActivated(AdvancedPortal portal, PlayerContainer player, ActivationData activeData, String argData);

        /**
         * Activates if the portal is allowed from pre
         *
         * @param player
         * @param activeData
         * @param argData
         */
        void portalActivated(AdvancedPortal portal, PlayerContainer player, ActivationData activeData, String argData);

    }

    public interface TagStatus {

        /**
         * If the user has access to add the tag (this does not include being added on creation)
         *
         * @param player
         * @param argData
         * @return if the tag will be added.
         */
        boolean tagAdded(AdvancedPortal portal, PlayerContainer player, String argData);

        /**
         * If the user has access to remove the tag (this does not include being added on destruction)
         *
         * @param player
         * @param argData
         * @return if the tag will be removed.
         */
        boolean tagRemoved(AdvancedPortal portal, PlayerContainer player, String argData);

    }

}
