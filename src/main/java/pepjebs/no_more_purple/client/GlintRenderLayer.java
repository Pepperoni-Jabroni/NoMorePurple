package pepjebs.no_more_purple.client;

import java.util.List;
import java.util.function.Function;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.*;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.util.Identifier;

// This class lovingly yoinked (& updated to 1.18-1.20) from
// https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/tools/client/GlintRenderType.java
// Published under the "CC BY-NC-SA 3.0" Creative Commons License
@Environment(EnvType.CLIENT)
public class GlintRenderLayer {

    public static final List<RenderLayer> glintColor = newRenderList(GlintRenderLayer::buildGlintRenderLayer);
    public static final List<RenderLayer> entityGlintColor = newRenderList(GlintRenderLayer::buildEntityGlintRenderLayer);

    public static final List<RenderLayer> armorGlintColor = newRenderList(GlintRenderLayer::buildArmorGlintRenderLayer);

    public static final List<RenderLayer> translucentGlintColor = newRenderList(GlintRenderLayer::buildTranslucentGlint);

    public static void addGlintTypes(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferAllocator> map) {
        addGlintTypes(map, glintColor);
        addGlintTypes(map, entityGlintColor);
        addGlintTypes(map, armorGlintColor);
        addGlintTypes(map, translucentGlintColor);
    }

    private static List<RenderLayer> newRenderList(Function<String, RenderLayer> func) {
        final var colorNames = NoMorePurpleClientMod.ALL_GLINT_COLORS_WITHOUT_OFF;
        final var array = new RenderLayer[colorNames.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = func.apply(colorNames.get(i));
        }

        return new ObjectImmutableList<>(array);
    }

    public static void addGlintTypes(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferAllocator> map, List<RenderLayer> typeList) {
        for (RenderLayer renderType : typeList) {
            map.computeIfAbsent(renderType, layer -> new BufferAllocator(((RenderLayer) layer).getExpectedBufferSize()));
        }
    }

    private static RenderLayer buildGlintRenderLayer(String name) {
        final Identifier res = Identifier.of(
                NoMorePurpleClientMod.MOD_ID,
                "textures/misc/glint_" + name + ".png"
        );

        var renderSetup = RenderSetup.builder(RenderPipelines.GLINT)
                .texture("Sampler0", res)
                .textureTransform(TextureTransform.GLINT_TEXTURING)
                .build();
        return RenderLayer.of("glint_" + name, renderSetup);
    }

    private static RenderLayer buildEntityGlintRenderLayer(String name) {
        final Identifier res = Identifier.of(
                NoMorePurpleClientMod.MOD_ID,
                "textures/misc/glint_" + name + ".png"
        );

        var renderSetup = RenderSetup.builder(RenderPipelines.ENTITY_TRANSLUCENT)
                .texture("Sampler0", res)
                .textureTransform(TextureTransform.ENTITY_GLINT_TEXTURING)
                .build();
        return RenderLayer.of("entity_glint_" + name, renderSetup);
    }

    private static RenderLayer buildArmorGlintRenderLayer(String name) {
        final Identifier res = Identifier.of(
                NoMorePurpleClientMod.MOD_ID,
                "textures/misc/glint_" + name + ".png"
        );

        var renderSetup = RenderSetup.builder(RenderPipelines.GLINT)
                .texture("Sampler0", res)
                .textureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
                .layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                .build();
        return RenderLayer.of("armor_glint_" + name, renderSetup);
    }

    private static RenderLayer buildTranslucentGlint(String name) {
        final Identifier res = Identifier.of(
                NoMorePurpleClientMod.MOD_ID,
                "textures/misc/glint_" + name + ".png"
        );

        var renderSetup = RenderSetup.builder(RenderPipelines.GLINT)
                .texture("Sampler0", res)
                .textureTransform(TextureTransform.GLINT_TEXTURING)
                .outputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                .build();
        return RenderLayer.of("glint_translucent_" + name, renderSetup);
    }
}