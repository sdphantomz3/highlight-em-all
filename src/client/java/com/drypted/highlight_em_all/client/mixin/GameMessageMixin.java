package com.drypted.highlight_em_all.client.mixin;

import com.drypted.highlight_em_all.client.HighlightEmAllClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GameMessageMixin {

    // "setOverlayMessage" is the rock-solid Mojang mapping name for setting Action Bar text
    @Inject(method = "setOverlayMessage", at = @At("HEAD"))
    private void onSetOverlayMessage(Component component, boolean animate, CallbackInfo ci) {
        // DEBUG LOG: This will absolutely trigger for every single action bar pop-up
        // HighlightEmAllClient.LOGGER.info("[HighlightEmAll] Intercepted Action Bar text: '{}'", component.getString());

        if (component.getContents() instanceof TranslatableContents translatable) {
            // HighlightEmAllClient.LOGGER.info("[HighlightEmAll] Translation Key found: '{}'", translatable.getKey());
            
            if ("block.minecraft.bed.not_safe".equals(translatable.getKey())) {
                // HighlightEmAllClient.LOGGER.info("[HighlightEmAll] -> MATCH! Bed warning found. Setting ticks to 60.");
                HighlightEmAllClient.remainingGlowTicks = 60;
            }
        } else {
            // FALLBACK: In case a server or plugin stripped the translation key and sent raw text
            String rawText = component.getString().toLowerCase();
            if (rawText.contains("monsters nearby") || rawText.contains("not safe")) {
                // HighlightEmAllClient.LOGGER.info("[HighlightEmAll] -> RAW TEXT MATCH! Setting ticks to 60.");
                HighlightEmAllClient.remainingGlowTicks = 60;
            }
        }
    }
}