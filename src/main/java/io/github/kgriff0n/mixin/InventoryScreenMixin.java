package io.github.kgriff0n.mixin;

import io.github.kgriff0n.ShulkerPreview;
import io.github.kgriff0n.screen.FakeShulkerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.kgriff0n.ShulkerPreview.SHULKER_COLORS;
import static io.github.kgriff0n.ShulkerPreview.client;

@Mixin(HandledScreen.class)
public class InventoryScreenMixin {

    @Inject(at = @At("HEAD"), method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", cancellable = true)
    private void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if (button == 1) {
            ItemStack stack = slot.getStack();
            if (SHULKER_COLORS.containsKey(stack.getItem())) {
                ci.cancel();
                client.setScreen(new FakeShulkerScreen(stack, client.currentScreen));
            }
        }
    }

}
