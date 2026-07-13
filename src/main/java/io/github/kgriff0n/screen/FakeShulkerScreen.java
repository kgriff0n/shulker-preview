package io.github.kgriff0n.screen;

import io.github.kgriff0n.ShulkerPreview;
import java.awt.*;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import static io.github.kgriff0n.ShulkerPreview.MOD_ID;


public class FakeShulkerScreen extends Screen {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/fake_shulker_box.png");

    private final int backgroundWidth = 176;
    private final int backgroundHeight = 78;

    private final Color color;
    private final Component title;
    private final List<ItemStack> inventory;
    private final Screen parent;

    private int x = 0;
    private int y = 0;

    public FakeShulkerScreen(ItemStack shulker, Screen parent) {
        super(Component.literal("Fake Shulker"));
        this.color = ShulkerPreview.SHULKER_COLORS.get(shulker.getItem());
        this.title = shulker.getHoverName();
        this.inventory = shulker.get(DataComponents.CONTAINER).allItemsCopyStream().toList();
        this.parent = parent;

    }

    @Override
    protected void init() {
        this.x = (this.width - this.backgroundWidth) / 2;
        this.y = (this.height - this.backgroundHeight) / 2;
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        super.extractRenderState(context, mouseX, mouseY, delta);
        this.extractBackground(context, mouseX, mouseY, delta);

        this.renderItems(context, this.inventory, this.x + 8, this.y + 18);

        int selectedSlot = getSlot(mouseX, mouseY);
        if (   -1 < selectedSlot && selectedSlot < this.inventory.size()
            && !this.inventory.get(selectedSlot).is(Items.AIR)) {
            this.renderTooltip(context, this.inventory.get(selectedSlot), mouseX, mouseY);
        }
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.x, this.y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256, this.color.getRGB());
        context.text(font, this.title, this.x + 8, this.y + 6, 0xFF404040, false);
    }

    @Override
    public void onClose() {
        minecraft.setScreen(parent);
    }

    private void renderItems(GuiGraphicsExtractor context, List<ItemStack> inventory, int x, int y) {
        int baseX = x;
        int count = 0;
        for (ItemStack item : inventory) {
            count++;
            context.item(item, x, y);
            context.itemDecorations(font, item, x, y);
            x += 18;
            if (count % 9 == 0) {
                x = baseX;
                y += 18;
            }
        }
    }

    private int getSlot(int i, int j) {
        int x = this.x + 7;
        int y = this.y + 17;

        int slotX = (i - x) / 18;
        int slotY = (j - y) / 18;

        if (i < x || j < y || i > x + 9 * 18 - 1 || j > y + 3 * 18 - 1) {
            return -1;
        }

        return slotX + slotY * 9;
    }

    private void renderTooltip(GuiGraphicsExtractor context, ItemStack stack, int x, int y) {
        context.setTooltipForNextFrame(font, stack, x, y);
    }
}
