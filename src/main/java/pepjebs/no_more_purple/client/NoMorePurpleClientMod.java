package pepjebs.no_more_purple.client;

import com.mojang.brigadier.arguments.StringArgumentType;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.DyeColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pepjebs.no_more_purple.config.NoMorePurpleConfig;

import java.util.Arrays;

public class NoMorePurpleClientMod implements ClientModInitializer {

    public static final String MOD_ID = "no_more_purple";
    public static final String COMMAND_ID = "glint_color";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static NoMorePurpleConfig CONFIG = null;

    @Override
    public void onInitializeClient() {
        // Register config
        AutoConfig.register(NoMorePurpleConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(NoMorePurpleConfig.class).getConfig();

        // Register the "/glintcolor <color>" command
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    ClientCommandManager.literal(COMMAND_ID)
                            .then(ClientCommandManager.argument(COMMAND_ID, StringArgumentType.string())
                                .suggests(new GlintColorSuggestionProvider())
                                .executes(context -> {
                                    CONFIG.glintColor = context.getArgument(COMMAND_ID, String.class);
                                    AutoConfig.getConfigHolder(NoMorePurpleConfig.class).save();
                                    return 0;
                                })
                            )
            );
        });
    }

    public static String[] listGlintColors() {
        return new String[]{
                "white",
                "orange",
                "magenta",
                "light_blue",
                "yellow",
                "lime",
                "pink",
                "gray",
                "light_gray",
                "cyan",
                "purple",
                "blue",
                "brown",
                "green",
                "red",
                "black",
                "rainbow",
                "light",
                "none",
                "off"
        };
    }

    private static int changeColor() {
        String confColor = CONFIG.glintColor.toLowerCase();
        int color = Arrays.stream(DyeColor.values())
                .filter(d -> d.getName().compareTo(confColor)==0)
                .findFirst()
                .map(DyeColor::getId)
                .orElse(-1);
        if (confColor.compareTo("rainbow") == 0) {
            color = DyeColor.values().length;
        } else if (confColor.compareTo("light") == 0){
            color = DyeColor.values().length + 1;
        } else if (confColor.compareTo("none") == 0){
            color = DyeColor.values().length + 2;
        } else if (confColor.compareTo("off") == 0){
            color = -1;
        }
        return color;
    }

    @Environment(EnvType.CLIENT)
    public static RenderLayer getGlint() {
        int color = changeColor();
        if (color == -1)
            return RenderLayer.getGlint();
        return GlintRenderLayer.glintColor.get(color);
    }

    @Environment(EnvType.CLIENT)
    public static RenderLayer getEntityGlint() {
        int color = changeColor();
        if (color == -1)
            return RenderLayer.getEntityGlint();
        return GlintRenderLayer.entityGlintColor.get(color);
    }

    @Environment(EnvType.CLIENT)
    public static RenderLayer getEntityGlintDirect() {
        int color = changeColor();
        if (color == -1)
            return RenderLayer.getDirectEntityGlint();
        return GlintRenderLayer.entityGlintDirectColor.get(color);
    }

    @Environment(EnvType.CLIENT)
    public static RenderLayer getArmorEntityGlint() {
        int color = changeColor();
        if (color == -1)
            return RenderLayer.getArmorEntityGlint();
        return GlintRenderLayer.armorEntityGlintColor.get(color);
    }
}
