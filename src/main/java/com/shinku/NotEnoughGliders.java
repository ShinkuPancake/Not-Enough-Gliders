package com.shinku;

import java.util.Map;

import javax.annotation.Nonnull;

import com.hypixel.hytale.builtin.crafting.BenchRecipeRegistry;
import com.hypixel.hytale.server.core.modules.entity.condition.Condition;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction; 
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;

public class NotEnoughGliders extends JavaPlugin {

    public static Config<GliderConfig> CONFIG;

    public NotEnoughGliders(@Nonnull JavaPluginInit init) {
        super(init);
        CONFIG = this.withConfig("GliderConfig", GliderConfig.CODEC);
    }

    @Override
    protected void setup() {
        CONFIG.save();
        
        // 1. Register Conditions (Used for backend logic/stats)
        final var conditions = this.getCodecRegistry(Condition.CODEC);
        
        conditions.register(
            "ShinkuGliderConsumeEssence", 
            UseEssenceCondition.class, 
            UseEssenceCondition.CODEC
        );
            
        conditions.register(
            "ShinkuBannedWorld", 
            BannedWorldCondition.class, 
            BannedWorldCondition.CODEC
        );
            
        // 2. Register Interactions (Used for the red warning message)
        final var interactions = this.getCodecRegistry(Interaction.CODEC);
        
        interactions.register(
            "ShinkuBannedMessage", 
            BannedMessageInteraction.class, 
            BannedMessageInteraction.CODEC
        );
    }

    @Override
    protected void start() {
        // Delayed recipe removal to ensure CraftingPlugin is ready
        com.hypixel.hytale.server.core.HytaleServer.SCHEDULED_EXECUTOR.schedule(() -> {
            try {
                Class<?> craftingPluginClass = Class.forName("com.hypixel.hytale.builtin.crafting.CraftingPlugin");
                java.lang.reflect.Field registriesField = craftingPluginClass.getDeclaredField("registries");
                registriesField.setAccessible(true);
                
                @SuppressWarnings("unchecked")
                Map<String, BenchRecipeRegistry> registries = (Map<String, BenchRecipeRegistry>) registriesField.get(null);

                if (registries != null) {
                    BenchRecipeRegistry workbench = registries.get("Workbench");

                    if (workbench != null) {
                        GliderConfig cfg = CONFIG.get();

                        Map<String, Boolean> gliderToggles = Map.of(
                            "Glider", cfg.enableDefaultGliderRecipe,
                            "Skysoarer_Glider", cfg.enableSkysoarerGliderRecipe,
                            "Verdant_Glider", cfg.enableVerdantGliderRecipe,
                            "Riptide_Glider", cfg.enableRiptideGliderRecipe,
                            "Magma_Glider", cfg.enableMagmaGliderRecipe,
                            "Glider_Of_The_Cosmos", cfg.enableCosmosGliderRecipe
                        );

                        gliderToggles.forEach((id, isEnabled) -> checkAndRemove(workbench, id, isEnabled));
                        workbench.recompute();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 1, java.util.concurrent.TimeUnit.SECONDS);
    }

    private void checkAndRemove(BenchRecipeRegistry registry, String itemId, boolean isEnabled) {
        if (!isEnabled) {
            String recipeId = itemId + "_Recipe_Generated_0";
            registry.removeRecipe(recipeId); 
        }
    }
}