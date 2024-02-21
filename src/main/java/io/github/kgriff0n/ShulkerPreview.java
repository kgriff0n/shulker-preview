package io.github.kgriff0n;

import io.github.kgriff0n.screen.FakeShulkerScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.HashMap;

public class ShulkerPreview implements ClientModInitializer {

	public static final HashMap<Item, Color> SHULKER_COLORS = new HashMap<>();

	private static KeyBinding key;

	@Override
	public void onInitializeClient() {

		SHULKER_COLORS.put(Items.SHULKER_BOX, new Color(142, 108, 142));
		SHULKER_COLORS.put(Items.WHITE_SHULKER_BOX, new Color(225, 230, 230));
		SHULKER_COLORS.put(Items.LIGHT_GRAY_SHULKER_BOX, new Color(137, 137, 128));
		SHULKER_COLORS.put(Items.GRAY_SHULKER_BOX, new Color(60, 65, 68));
		SHULKER_COLORS.put(Items.BLACK_SHULKER_BOX, new Color(31, 31, 35));
		SHULKER_COLORS.put(Items.BROWN_SHULKER_BOX, new Color(113, 70, 39));
		SHULKER_COLORS.put(Items.RED_SHULKER_BOX, new Color(152, 36, 34));
		SHULKER_COLORS.put(Items.ORANGE_SHULKER_BOX, new Color(241, 114, 15));
		SHULKER_COLORS.put(Items.YELLOW_SHULKER_BOX, new Color(249, 196, 35));
		SHULKER_COLORS.put(Items.LIME_SHULKER_BOX, new Color(110, 185, 24));
		SHULKER_COLORS.put(Items.GREEN_SHULKER_BOX, new Color(83, 107, 29));
		SHULKER_COLORS.put(Items.CYAN_SHULKER_BOX, new Color(22, 133, 144));
		SHULKER_COLORS.put(Items.LIGHT_BLUE_SHULKER_BOX, new Color(57, 177, 215));
		SHULKER_COLORS.put(Items.BLUE_SHULKER_BOX, new Color(49, 52, 152));
		SHULKER_COLORS.put(Items.PURPLE_SHULKER_BOX, new Color(113, 37, 166));
		SHULKER_COLORS.put(Items.MAGENTA_SHULKER_BOX, new Color(183, 61, 172));
		SHULKER_COLORS.put(Items.PINK_SHULKER_BOX, new Color(239, 135, 166));

		key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Open Shulker",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_V,
				"Shulker Preview"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				ItemStack stack = client.player.getMainHandStack();
				while (key.wasPressed()) {
					if (SHULKER_COLORS.containsKey(stack.getItem())) {
						client.setScreen(new FakeShulkerScreen(client.player.getMainHandStack()));
					}
				}
			}
		});
	}
}