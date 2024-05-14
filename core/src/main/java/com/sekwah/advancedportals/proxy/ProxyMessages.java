package com.sekwah.advancedportals.proxy;

public enum ProxyMessages {
    CHANNEL_NAME("AdvancedPortals:Warp"),
    SERVER_DESTINATION("AdvancedPortals:ProxyPortal"),
    ENTER_PORTAL("AdvancedPortals:EnterPortal"),
    PROXY_COMMAND("AdvancedPortals:ProxyCommand");

    private final String text;

    ProxyMessages(String string) {
        this.text = string;
    }

    @Override
    public String toString() {
        return text;
    }
}
