package io.github.kgriff0n.mixin;

import io.github.kgriff0n.ShulkerPreview;
import io.github.kgriff0n.screen.FakeShulkerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.kgriff0n.ShulkerPreview.SHULKER_COLORS;
import static io.github.kgriff0n.ShulkerPreview.client;

@Mixin(AbstractContainerScreen.class)
public class InventoryScreenMixin {

    @Shadow @Nullable protected Slot hoveredSlot;

    @Inject(at = @At("HEAD"), method = "keyPressed")
    private void keyPressed(KeyEvent input, CallbackInfoReturnable<Boolean> cir) {
        if (ShulkerPreview.key.matches(input) && this.hoveredSlot != null) {
            ItemStack stack = this.hoveredSlot.getItem();
            if (SHULKER_COLORS.containsKey(stack.getItem())) {
                client.setScreen(new FakeShulkerScreen(stack, client.screen));
            }
        }
    }
}
