package com.drypted.highlight_em_all.client.mixin;

import com.drypted.highlight_em_all.client.HighlightEmAllClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "isCurrentlyGlowing", at = @At("HEAD"), cancellable = true)
    private void onIsGlowing(CallbackInfoReturnable<Boolean> cir) {
        // Only run logic if the bed radar timer is currently running
        if (HighlightEmAllClient.remainingGlowTicks > 0) {
            Entity entity = (Entity) (Object) this;

            // Safety: Ensure we are operating exclusively on the client-side level copy
            if (entity.level() != null && entity.level().isClientSide() && entity instanceof Monster monster) {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null) {
                    double dx = monster.getX() - player.getX();
                    double dy = monster.getY() - player.getY();
                    double dz = monster.getZ() - player.getZ();
                    double distSq = dx * dx + dz * dz;
                    double deltaY = Math.abs(dy);

                    // DEBUG: Log telemetry for every loaded monster evaluated in the scene frame
                    // HighlightEmAllClient.LOGGER.info("[HighlightEmAll] Evaluating Monster: '{}' | DistSq: {} | DeltaY: {}", 
                    //     monster.getType().getDescription().getString(), distSq, deltaY);

                    // Bounding validation rule check
                    if (distSq <= 64.0 && deltaY <= 5.0) {
                        // HighlightEmAllClient.LOGGER.info("[HighlightEmAll] -> SUCCESS! Forcing glow outline for: {}", monster.getType().getDescription().getString());
                        cir.setReturnValue(true);
                    } else {
                        // HighlightEmAllClient.LOGGER.info("[HighlightEmAll] -> Fail: Monster out of radar limits.");
                    }
                }
            }
        }
    }
}