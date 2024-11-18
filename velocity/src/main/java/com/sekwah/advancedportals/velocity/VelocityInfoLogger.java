package com.sekwah.advancedportals.velocity;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.velocity.connector.MinecraftToAnsi;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.slf4j.Logger;

public class VelocityInfoLogger extends InfoLogger {
    private final Logger logger;
    private final ProxyServer proxy;
    private final LegacyComponentSerializer serializer =
        LegacyComponentSerializer.builder()
            .character('&')
            .hexCharacter('#')
            .hexColors()
            .build();

    public VelocityInfoLogger(Logger logger, ProxyServer proxy) {
        this.logger = logger;
        this.proxy = proxy;
    }

    @Override
    public void warning(String s) {
        logger.warn(MinecraftToAnsi.convert(s));
    }

    @Override
    public void info(String s) {
        logger.info(MinecraftToAnsi.convert(s));
    }

    @Override
    public void error(Exception e) {
        logger.error(MinecraftToAnsi.convert("\u00A7c" + e.getMessage()), e);
    }
}
