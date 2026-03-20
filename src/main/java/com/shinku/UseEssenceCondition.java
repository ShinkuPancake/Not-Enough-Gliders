package com.shinku;

import java.time.Instant;

import javax.annotation.Nonnull;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.modules.entity.condition.Condition;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class UseEssenceCondition extends Condition {

    public static final BuilderCodec<UseEssenceCondition> CODEC = 
        BuilderCodec.builder(UseEssenceCondition.class, UseEssenceCondition::new, Condition.BASE_CODEC)
                    .build();

    public UseEssenceCondition() {
        super();
    }
    
    public UseEssenceCondition(boolean inverse) {
        super(inverse);
    }

    @Override
    public boolean eval0(@Nonnull ComponentAccessor<EntityStore> accessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant time) {
        return NotEnoughGliders.CONFIG.get().consumeEssence;
    }
}