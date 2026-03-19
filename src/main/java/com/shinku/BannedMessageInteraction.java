package com.shinku;

import javax.annotation.Nonnull;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.protocol.WaitForDataFrom;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class BannedMessageInteraction extends SimpleInstantInteraction {

    @Nonnull
    public static final BuilderCodec<BannedMessageInteraction> CODEC = BuilderCodec.builder(
            BannedMessageInteraction.class, BannedMessageInteraction::new, SimpleInstantInteraction.CODEC
        ).build();

    public BannedMessageInteraction() {}

    @Override
    protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
        CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
        if (commandBuffer == null) return;

        Ref<EntityStore> ref = context.getEntity();
        Player playerComponent = commandBuffer.getComponent(ref, Player.getComponentType());
        if (playerComponent == null) {
            context.getState().state = InteractionState.Failed;
            return;
        }

        // Safe check: Only the Server has the config loaded. 
        // The server will read it and send the chat packet to the player.
        if (NotEnoughGliders.CONFIG != null && NotEnoughGliders.CONFIG.get() != null) {
            String disableMsg = NotEnoughGliders.CONFIG.get().disableMessage;
            if (disableMsg != null && !disableMsg.isEmpty()) {
                playerComponent.sendMessage(Message.raw(disableMsg).color("#FF0000"));
            }
        }

        // Instantly finish so it doesn't hold up any chains
        context.getState().state = InteractionState.Finished;
    }

    // Keep it 0-latency so it doesn't break prediction
    @Override
    public boolean needsRemoteSync() {
        return false;
    }

    @Nonnull
    @Override
    public WaitForDataFrom getWaitForDataFrom() {
        return WaitForDataFrom.None;
    }
}