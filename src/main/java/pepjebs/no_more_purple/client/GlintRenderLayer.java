package pepjebs.no_more_purple.client;

import java.util.List;
import java.util.function.Function;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.util.Identifier;
import net.minecraft.util.TriState;

// This class lovingly yoinked (& updated to 1.18-1.20) from
// https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/tools/client/GlintRenderType.java
// Published under the "CC BY-NC-SA 3.0" Creative Commons License
@Environment(EnvType.CLIENT)
public class GlintRenderLayer extends RenderLayer {

    public static final List<RenderLayer> glintColor = newRenderList(GlintRenderLayer::buildGlintRenderLayer);
    public static final List<RenderLayer> entityGlintColor = newRenderList(GlintRenderLayer::buildEntityGlintRenderLayer);

    public static final List<RenderLayer> armorGlintColor = newRenderList(GlintRenderLayer::buildArmorGlintRenderLayer);
    public static final List<RenderLayer> armorEntityGlintColor = newRenderList(GlintRenderLayer::buildArmorEntityGlintRenderLayer);

    public static final List<RenderLayer> translucentGlintColor = newRenderList(GlintRenderLayer::buildTranslucentGlint);

    public static void addGlintTypes(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferAllocator> map) {
        addGlintTypes(map, glintColor);
        addGlintTypes(map, entityGlintColor);
        addGlintTypes(map, armorGlintColor);
        addGlintTypes(map, armorEntityGlintColor);
        addGlintTypes(map, translucentGlintColor);
    }

    public GlintRenderLayer(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
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
        final Identifier res = Identifier.of(NoMorePurpleClientMod.MOD_ID, "textures/misc/glint_" + name + ".png");

        return RenderLayer.of("glint_" + name, VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 256, MultiPhaseParameters.builder()
                .program(RenderPhase.GLINT_PROGRAM)
                .texture(new Texture(res, TriState.TRUE, false))
                .writeMaskState(COLOR_MASK)
                .cull(DISABLE_CULLING)
                .depthTest(EQUAL_DEPTH_TEST)
                .transparency(GLINT_TRANSPARENCY)
                .texturing(GLINT_TEXTURING)
                .build(false));
    }

    private static RenderLayer buildEntityGlintRenderLayer(String name) {
        final Identifier res = Identifier.of(NoMorePurpleClientMod.MOD_ID, "textures/misc/glint_" + name + ".png");

        return RenderLayer.of("entity_glint_" + name, VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 256, MultiPhaseParameters.builder()
                .program(RenderPhase.ENTITY_GLINT_PROGRAM)
                .texture(new Texture(res, TriState.TRUE, false))
                .writeMaskState(COLOR_MASK)
                .cull(DISABLE_CULLING)
                .depthTest(EQUAL_DEPTH_TEST)
                .transparency(GLINT_TRANSPARENCY)
                .target(ITEM_ENTITY_TARGET)
                .texturing(ENTITY_GLINT_TEXTURING)
                .build(false));
    }

    private static RenderLayer buildArmorGlintRenderLayer(String name) {
        final Identifier res = Identifier.of(NoMorePurpleClientMod.MOD_ID, "textures/misc/glint_" + name + ".png");

        return RenderLayer.of("armor_glint_" + name, VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 256, MultiPhaseParameters.builder()
                .program(RenderPhase.ARMOR_ENTITY_GLINT_PROGRAM)
                .texture(new Texture(res, TriState.TRUE, false))
                .writeMaskState(COLOR_MASK)
                .cull(DISABLE_CULLING)
                .depthTest(EQUAL_DEPTH_TEST)
                .transparency(GLINT_TRANSPARENCY)
                .texturing(GLINT_TEXTURING)
                .layering(VIEW_OFFSET_Z_LAYERING)
                .build(false));
    }

    private static RenderLayer buildArmorEntityGlintRenderLayer(String name) {
        final Identifier res = Identifier.of(NoMorePurpleClientMod.MOD_ID, "textures/misc/glint_" + name + ".png");

        return RenderLayer.of("armor_entity_glint_" + name, VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 256, MultiPhaseParameters.builder()
                .program(RenderPhase.ARMOR_ENTITY_GLINT_PROGRAM)
                .texture(new Texture(res, TriState.TRUE, false))
                .writeMaskState(COLOR_MASK)
                .cull(DISABLE_CULLING)
                .depthTest(EQUAL_DEPTH_TEST)
                .transparency(GLINT_TRANSPARENCY)
                .texturing(ENTITY_GLINT_TEXTURING)
                .layering(VIEW_OFFSET_Z_LAYERING)
                .build(false));
    }

    private static RenderLayer buildTranslucentGlint(String name) {
        final Identifier res = Identifier.of(NoMorePurpleClientMod.MOD_ID, "textures/misc/glint_" + name + ".png");

        return RenderLayer.of("glint_translucent_" + name, VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 256, RenderLayer.MultiPhaseParameters.builder()
                .program(TRANSLUCENT_GLINT_PROGRAM)
                .texture(new Texture(res, TriState.TRUE, false))
                .writeMaskState(COLOR_MASK)
                .cull(DISABLE_CULLING)
                .depthTest(EQUAL_DEPTH_TEST)
                .transparency(GLINT_TRANSPARENCY)
                .texturing(GLINT_TEXTURING)
                .target(ITEM_ENTITY_TARGET)
                .build(false));
    }
}