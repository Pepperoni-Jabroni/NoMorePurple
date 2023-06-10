package pepjebs.no_more_purple.mixin;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pepjebs.no_more_purple.client.NoMorePurpleClientMod;

@Mixin(ArmorFeatureRenderer.class)
public class ArmorFeatureRendererNoMoPurpleMixin {

    @Redirect(method = "renderGlint", at = @At(value = "INVOKE", target="Lnet/minecraft/client/render/RenderLayer;getArmorEntityGlint()Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer getArmorEntityGlint() {
        return NoMorePurpleClientMod.getArmorEntityGlint();
    }
}
