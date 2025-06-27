package com.sekwah.advancedportals.core.warphandler;

import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by on 30/07/2016.
 *
 * @author sekwah41
 */
public class ActivationData {
    private boolean warpAllowed = true;

    private WarpedStatus warpStatus = WarpedStatus.NOTACTIVATED;

    private PlayerLocation wantedLocation;

    private final TriggerType triggerType;

    private final Map<String, String> metadata = new HashMap<>();

    public WarpedStatus getWarped() {
        return this.warpStatus;
    }

    public ActivationData(TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    public void setWarpStatus(WarpedStatus warped) {
        if (this.warpStatus == WarpedStatus.WARPED
            || (this.warpStatus == WarpedStatus.ACTIVATED
                && warped != WarpedStatus.WARPED)) {
            return;
        }
        this.warpStatus = warped;
    }

    public TriggerType getTriggerType() {
        return this.triggerType;
    }

    public String getMetadata(String key) {
        return this.metadata.get(key);
    }

    public void setMetadata(String key, String value) {
        this.metadata.put(key, value);
    }

    /**
     * In case you need to set the status back down a step for whatever reason.
     * However it is not recommended.
     *
     * @param warped
     */
    public void setWarpStatusAbsolute(WarpedStatus warped) {
        this.warpStatus = warped;
    }

    public boolean getAllowed() {
        return this.warpAllowed;
    }

    public void setAllowed(boolean allowed) {
        this.warpAllowed = allowed;
    }

    public boolean hasActivated() {
        return this.warpStatus != WarpedStatus.NOTACTIVATED;
    }

    public enum WarpedStatus {
        /**
         * Player has moved or something major has happened. (only one of these
         * should activate)
         */
        WARPED,
        /**
         * Shows that the portal has been activated even if a major function is
         * not performed.
         */
        ACTIVATED,
        /**
         * Nothing has activated on the portal (may need to come up with a new
         * name)
         */
        NOTACTIVATED
    }
}
