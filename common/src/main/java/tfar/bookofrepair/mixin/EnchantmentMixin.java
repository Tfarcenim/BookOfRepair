package tfar.bookofrepair.mixin;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(method = "isDiscoverable",at = @At("HEAD"),cancellable = true)
    private void noMending(CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this == Enchantments.MENDING) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isTradeable",at = @At("HEAD"),cancellable = true)
    private void noMendingTrade(CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this == Enchantments.MENDING) {
            cir.setReturnValue(false);
        }
    }
}
