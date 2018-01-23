package com.sekwah.advancedportals.core;

import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

public class CoreListeners {

    public void playerJoin(PlayerContainer player) {
        if(player.isOp()) {
            if(!Lang.translate("translatedata.lastchange").equals(AdvancedPortalsCore.lastTranslationUpdate)) {
                player.sendMessage(Lang.translateColor("messageprefix.negative"
                        + Lang.translateInsertVariablesColor("translatedata.translationsoutdated", AdvancedPortalsCore.getTranslationName())));
                player.sendMessage(Lang.translateColor("messageprefix.negative"
                        + Lang.translateColor("translatedata.replacecommand")));
            }
        }
    }

}
