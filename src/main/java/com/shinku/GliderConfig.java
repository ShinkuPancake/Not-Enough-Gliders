package com.shinku;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;

public class GliderConfig {
    public boolean consumeEssence = true;
    public boolean enableDefaultGliderRecipe = true;
    public boolean enableSkysoarerGliderRecipe = true;
    public boolean enableVerdantGliderRecipe = true;
    public boolean enableRiptideGliderRecipe = true;
    public boolean enableMagmaGliderRecipe = true;
    public boolean enableGlacialGliderRecipe = true;
    public boolean enableVoltGliderRecipe = true;
    public boolean enableVoidGliderRecipe = true;
    public boolean enableCosmosGliderRecipe = true;
    
    public String[] disabledInstances = new String[0]; 
    public String disableMessage = "Glider has been disabled in this instance";

    public static final BuilderCodec<GliderConfig> CODEC;

    static {
        CODEC = BuilderCodec.builder(GliderConfig.class, GliderConfig::new)
            .append(new KeyedCodec<>("GliderConsumeEssenceOnUse", Codec.BOOLEAN), 
                (config, v) -> config.consumeEssence = v, config -> config.consumeEssence).add()
            .append(new KeyedCodec<>("EnableDefaultGliderRecipe", Codec.BOOLEAN), 
                (config, v) -> config.enableDefaultGliderRecipe = v, config -> config.enableDefaultGliderRecipe).add()
            .append(new KeyedCodec<>("EnableSkysoarerGliderRecipe", Codec.BOOLEAN), 
                (config, v) -> config.enableSkysoarerGliderRecipe = v, config -> config.enableSkysoarerGliderRecipe).add()
            .append(new KeyedCodec<>("EnableVerdantGliderRecipe", Codec.BOOLEAN), 
                (config, v) -> config.enableVerdantGliderRecipe = v, config -> config.enableVerdantGliderRecipe).add()
            .append(new KeyedCodec<>("EnableRiptideGliderRecipe", Codec.BOOLEAN), 
                (config, v) -> config.enableRiptideGliderRecipe = v, config -> config.enableRiptideGliderRecipe).add()
            .append(new KeyedCodec<>("EnableMagmaGliderRecipe", Codec.BOOLEAN), 
                (config, v) -> config.enableMagmaGliderRecipe = v, config -> config.enableMagmaGliderRecipe).add()
            .append(new KeyedCodec<>("EnableGlacialGliderRecipe", Codec.BOOLEAN), 
                (config, v) -> config.enableGlacialGliderRecipe = v, config -> config.enableGlacialGliderRecipe).add()
            .append(new KeyedCodec<>("EnableVoltGliderRecipe", Codec.BOOLEAN), 
                (config, v) -> config.enableVoltGliderRecipe = v, config -> config.enableVoltGliderRecipe).add()
            .append(new KeyedCodec<>("EnableVoidGliderRecipe", Codec.BOOLEAN), 
                (config, v) -> config.enableVoidGliderRecipe = v, config -> config.enableVoidGliderRecipe).add()
            .append(new KeyedCodec<>("EnableCosmosGliderRecipe", Codec.BOOLEAN), 
                (config, v) -> config.enableCosmosGliderRecipe = v, config -> config.enableCosmosGliderRecipe).add()
            
            .append(new KeyedCodec<>("DisabledInstances", new ArrayCodec<>(Codec.STRING, String[]::new)), 
                (config, v) -> config.disabledInstances = v, config -> config.disabledInstances).add()
            
            .append(new KeyedCodec<>("DisableMessage", Codec.STRING), 
                (config, v) -> config.disableMessage = v, config -> config.disableMessage).add()
            .build();
    }
}