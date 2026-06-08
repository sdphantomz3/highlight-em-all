package com.drypted.highlight_em_all.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.world.entity.monster.Monster;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HighlightEmAllClient implements ClientModInitializer {
    public static List<Monster> glowingMonsters = new ArrayList<>();
    public static int remainingGlowTicks = 0;
    public static final String MOD_ID = "highlight_em_all";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("[HighlightEmAll] successfully initialized!");

        // ClientTickEvents.END_CLIENT_TICK.register(client -> {
        //     if (remainingGlowTicks > 0) {
        //         remainingGlowTicks--;
        //         // DEBUG: Log the status every 10 ticks (0.5 seconds) to avoid spamming too heavily
        //         if (remainingGlowTicks % 10 == 0) {
        //             LOGGER.info("[HighlightEmAll] Timer Ticking. Remaining ticks: {}", remainingGlowTicks);
        //         }
        //     }
        // });
    }
}