package com.sekwah.advancedportals.core.api.warphandler;

import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.api.portal.PortalException;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

/**
 * Created by on 30/07/2016.
 *
 * @author sekwah41
 */
public class TagHandler {

    /**
     * The events for portal creation and destroying
     */
    public interface Creation<T> {

        /**
         * Example if the player does not have access to use a tag on the portal.
         *
         * @param player     if null the portal has been created by the server or a plugin
         * @param argData
         * @return if the portal can be created.
         * @throws PortalException message given is the reason the portal cant be made
         */
        void portalCreated(T target, PlayerContainer player, String argData) throws PortalException;

        /**
         * Example if the player does not have access to remove the portal.
         *
         * @param player     if null the portal has been destroyed by the server or a plugin
         * @param argData
         * @return if the portal can be destroyed.
         * @throws PortalException message given is the reason the portal cant be removed
         */
        void portalDestroyed(T target, PlayerContainer player, String argData) throws PortalException;

    }

    public interface Activation<T> {

        /**
         * Activates before the main part of portal activation.
         *
         * @param player
         * @param activeData
         * @param argData
         */
        void preActivated(T target, PlayerContainer player, ActivationData activeData, String argData);

        /**
         * Activates after portal activation
         *
         * @param player
         * @param activeData
         * @param argData
         */
        void postActivated(T target, PlayerContainer player, ActivationData activeData, String argData);

        /**
         * Activates if the portal is allowed from pre
         *
         * @param player
         * @param activeData
         * @param argData
         */
        void activated(T target, PlayerContainer player, ActivationData activeData, String argData);

    }

    public interface TagStatus<T> {

        /**
         * If the user has access to add the tag (this does not include being added on creation)
         *
         * @param player
         * @param argData
         * @return if the tag will be added.
         */
        boolean tagAdded(T target, PlayerContainer player, String argData);

        /**
         * If the user has access to remove the tag (this does not include being added on destruction)
         *
         * @param player
         * @param argData
         * @return if the tag will be removed.
         */
        boolean tagRemoved(T target, PlayerContainer player, String argData);

    }

}
