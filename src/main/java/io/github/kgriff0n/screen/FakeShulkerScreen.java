package io.github.kgriff0n.screen;

import io.github.kgriff0n.ShulkerPreview;
import io.github.kgriff0n.ShulkerUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;


public class FakeShulkerScreen extends Screen {

    private static final Identifier TEXTURE = new Identifier("shulker-preview", "textures/gui/fake_shulker_box.png");

    private final int backgroundWidth = 176;
    private final int backgroundHeight = 78;

    private final Color color;
    private final Text title;
    private final ArrayList<ItemStack> inventory;
    private final Screen parent;

    private int x = 0;
    private int y = 0;

    public FakeShulkerScreen(ItemStack shulker, Screen parent) {
        super(Text.literal("Fake Shulker"));
        this.color = ShulkerPreview.SHULKER_COLORS.get(shulker.getItem());
        this.title = shulker.getName();
        this.inventory = ShulkerUtil.getShulkerInventory(shulker);
        this.parent = parent;

    }

    @Override
    protected void init() {
        this.x = (this.width - this.backgroundWidth) / 2;
        this.y = (this.height - this.backgroundHeight) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.renderBackground(context);

        this.renderItems(context, this.inventory, this.x + 8, this.y + 18);

        int selectedSlot = getSlot(mouseX, mouseY);
        if (-1 < selectedSlot && selectedSlot < this.inventory.size() && this.inventory.get(selectedSlot) != null) {
            this.renderTooltip(context, this.inventory.get(selectedSlot), mouseX, mouseY);
        }
    }

    @Override
    public void renderBackground(DrawContext context) {
        context.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f);
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        context.drawText(textRenderer, this.title, this.x + 8, this.y + 6, 0x404040, false);
        context.setShaderColor(1f, 1f, 1f, 1f);
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }

    private void renderItems(DrawContext context, ArrayList<ItemStack> inventory, int x, int y) {
        int baseX = x;
        int count = 0;
        for (ItemStack item : inventory) {
            count++;
            if (item != null) {
                context.drawItem(item, x, y);
                context.drawItemInSlot(textRenderer, item, x, y);
            }
            x += 18;
            if (count % 9 == 0) {
                x = baseX;
                y += 18;
            }
        }
    }

    private int getSlot(int i, int j) {
        int x = this.x + 8;
        int y = this.y + 18;

        int slotX = (i - x) / 18;
        int slotY = (j - y) / 18;

        if (i < x || j < y || i > x + 9 * 18 || j > y + 3 * 18) {
            return -1;
        }

        return slotX + slotY * 9;
    }

    private void renderTooltip(DrawContext context, ItemStack stack, int x, int y) {
        context.drawItemTooltip(textRenderer, stack, x, y);
    }
}
