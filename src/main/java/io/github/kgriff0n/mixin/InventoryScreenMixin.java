package io.github.kgriff0n.mixin;

import io.github.kgriff0n.ShulkerPreview;
import io.github.kgriff0n.screen.FakeShulkerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.kgriff0n.ShulkerPreview.SHULKER_COLORS;
import static io.github.kgriff0n.ShulkerPreview.client;

@Mixin(HandledScreen.class)
public class InventoryScreenMixin {

    @Shadow @Nullable protected Slot focusedSlot;

    @Inject(at = @At("HEAD"), method = "keyPressed")
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (ShulkerPreview.key.matchesKey(keyCode, scanCode) && this.focusedSlot != null) {
            ItemStack stack = this.focusedSlot.getStack();
            if (SHULKER_COLORS.containsKey(stack.getItem())) {
                client.setScreen(new FakeShulkerScreen(stack, client.currentScreen));
            }
        }
    }
}
