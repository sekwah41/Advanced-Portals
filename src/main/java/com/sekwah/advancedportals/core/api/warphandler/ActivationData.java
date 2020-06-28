package com.sekwah.advancedportals.core.api.warphandler;


import com.sekwah.advancedportals.core.data.PlayerLocation;

/**
 * Created by on 30/07/2016.
 *
 * @author sekwah41
 */
public class ActivationData {

    private boolean warpAllowed = true;

    private WarpedStatus warpStatus = WarpedStatus.NOTACTIVATED;

    private PlayerLocation wantedLocation;

    public WarpedStatus getWarped() {
        return this.warpStatus;
    }

    public void setWarpStatus(WarpedStatus warped) {
        if (this.warpStatus == WarpedStatus.WARPED) {
            return;
        } else if (this.warpStatus == WarpedStatus.NOTACTIVATED) {
            return;
        }
        this.warpStatus = warped;
    }

    /**
     * In case you need to set the status back down a step for whatever reason. However it is not recommended.
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

    public enum WarpedStatus {
        /**
         * Player has moved or something major has happened. (only one of these should activate)
         */
        WARPED,
        /**
         * Shows that the portal has been activated even if a major function is not performed.
         */
        ACTIVATED,
        /**
         * Nothing has activated on the portal (may need to come up with a new name)
         */
        NOTACTIVATED;
    }

}
