package com.sekwah.advancedportals.core.warphandler;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import java.util.List;
import javax.annotation.Nullable;

/**
 * If a tag can be used for any of them then either make it cast the target or
 * if it doesn't need a target then the exact same tag can be registered into
 * both and ignore the portal info.
 *
 * <p>Will probably make better documentation on how to do so or some tutorial
 * videos though take a look at the source code on GitHub for how the current
 * tags are added.
 *
 * <p>Also, not sure if its good practice or not in java however these all
 * extend TagHandler, so they can be accepted in 1 method nicer than if they
 * didn't
 *
 * @author sekwah41
 */
public interface Tag {
    /**
     * By default, all tags should be able to use either.
     *
     * <p>Though you may have use cases where you want to limit it to one or the
     * other.
     */
    enum TagType { PORTAL, DESTINATION }

    enum Priority { HIGHEST, HIGH, NORMAL, LOW, LOWEST }

    /**
     * Used to flag where the auto complete should show more or less info.
     *
     * @return
     */
    TagType[] getTagTypes();

    String getName();

    /**
     * If the tag should not show in suggestions after the first tag
     * @return
     */
    default boolean isSingleValueTag() {
        return false;
    }

    @Nullable
    String[] getAliases();

    String description();

    interface OrderPriority {
        Priority getPriority();
    }

    interface DenyBehavior {
        enum Behaviour { SILENT, KNOCKBACK }

        Behaviour getDenyBehavior();
    }

    interface AutoComplete extends Tag {
        /**
         * This is used to get the auto complete for the tag. This is called
         * when the player is typing the tag.
         *
         * @param argData
         * @return
         */
        @Nullable
        List<String> autoComplete(String argData);
    }

    interface Split extends Tag {
        /**
         * This is used to split the tag into the arguments if multiple are
         * supported
         *
         * @return null if the tag does not support splitting
         */
        @Nullable
        default String splitString() {
            return ",";
        }
    }

    /**
     * The events for portal creation and destroying
     */
    interface Creation extends Tag {
        /**
         * Example if the player does not have access to use the tag.
         *
         * @param player if null then created by the server or a plugin
         * @param argData
         * @return If the tag is valid or allowed creation
         */
        boolean created(TagTarget target, PlayerContainer player,
                        String[] argData);

        /**
         * Example if the player does not have access to remove the portal or
         * destination.
         *
         * @param player if null then removed by the server or a plugin
         * @param argData
         */
        void destroyed(TagTarget target, PlayerContainer player,
                       String[] argData);
    }

    /**
     * Order of activation Portal and Desti: preActivated activated
     * postActivated
     *
     * <p>Order of them combined: Portal.preActivated Portal.activated - when
     * desti tag is hit (if listed) then next two actions are activated -
     * Desti.preActivate - Desti.activate Portal.postActivate - when desti tag
     * is hit (if listed) then the next action is activated - Desti.postActivate
     */
    interface Activation extends Tag {

        /**
         * Activates before the main part of activation. This should be for
         * prechecks e.g. if the player has enough money before then taking the
         * money in postActivated.
         *
         * @param player
         * @param activeData
         * @param argData
         * @return If the tag has allowed the warp
         */
        boolean preActivated(TagTarget target, PlayerContainer player,
                             ActivationData activeData, String[] argData);

        /**
         * Activates after activation, should be used for actions such as
         * removing money for a teleport.
         *
         * <p>Any actions to do with player location should be done in activate
         *
         * @param player
         * @param activationData
         * @param argData
         */
        void postActivated(TagTarget target, PlayerContainer player,
                           ActivationData activationData, String[] argData);

        /**
         * Activates if the portal is allowed from preActivating. Should be used
         * to set the intended warp location
         *
         * <p>You should do some second checks if it can be dependent on the
         * preActivate, the destination tags will also be triggered here if a
         * desti is listed.
         *
         * @param player
         * @param activationData
         * @param argData
         * @return Action performed (only return false if the tag failed to do
         *     anything)
         */
        boolean activated(TagTarget target, PlayerContainer player,
                          ActivationData activationData, String[] argData);
    }

    /**
     * Triggers when a tag is added or removed from a portal or destination after it's initial creation
     * If this behavior is not defined then it will automatically allow adding and removing
     */
    interface TagStatus extends Tag {

        default boolean canAlterTag() {
            return true;
        }

        /**
         * If the user has access to add the tag (this does not include being
         * added on creation)
         *
         * @param target the target of the tag
         * @param player if null then removed by the server or a plugin
         * @param argData the data for the tag
         * @param index the index of the tag in the list (if it is the only tag
         *     it will be 0)
         * @return if the tag will be added.
         */
        boolean tagAdded(TagTarget target, PlayerContainer player, int index,
                         String argData);

        /**
         * If the user has access to remove the tag (this does not include being
         * added on destruction)
         *
         * @param target the target of the tag
         * @param player if null then removed by the server or a plugin
         * @param argData the data of the tag to be removed
         * @param index the index of the tag in the list (if it is the only tag
         *     it will be 0)
         * @return if the tag will be removed.
         */
        boolean tagRemoved(TagTarget target, PlayerContainer player, int index,
                           String argData);
    }
}
