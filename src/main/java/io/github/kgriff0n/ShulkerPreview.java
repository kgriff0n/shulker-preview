package io.github.kgriff0n;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.kgriff0n.screen.FakeShulkerScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.awt.*;
import java.util.HashMap;

public class ShulkerPreview implements ClientModInitializer {

    public static final String MOD_ID = "shulker-preview";

	public static Minecraft client;

	public static final HashMap<Item, Color> SHULKER_COLORS = new HashMap<>();

	public static KeyMapping key;

	@Override
	public void onInitializeClient() {

		client = Minecraft.getInstance();

		SHULKER_COLORS.put(Items.SHULKER_BOX, new Color(142, 108, 142));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.white(), new Color(225, 230, 230));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.lightGray(), new Color(137, 137, 128));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.gray(), new Color(60, 65, 68));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.black(), new Color(31, 31, 35));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.brown(), new Color(113, 70, 39));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.red(), new Color(152, 36, 34));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.orange(), new Color(241, 114, 15));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.yellow(), new Color(249, 196, 35));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.lime(), new Color(110, 185, 24));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.green(), new Color(83, 107, 29));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.cyan(), new Color(22, 133, 144));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.lightBlue(), new Color(57, 177, 215));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.blue(), new Color(49, 52, 152));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.purple(), new Color(113, 37, 166));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.magenta(), new Color(183, 61, 172));
		SHULKER_COLORS.put(Items.DYED_SHULKER_BOX.pink(), new Color(239, 135, 166));

        KeyMapping.Category category = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "main"));
		key = KeyMappingHelper.registerKeyMapping(new KeyMapping(
				"key.open_shulker",
				InputConstants.Type.KEYBOARD,
				InputConstants.KEY_V,
				category
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				ItemStack stack = client.player.getMainHandItem();
				while (key.consumeClick()) {
					if (SHULKER_COLORS.containsKey(stack.getItem())) {
						client.setScreenAndShow(new FakeShulkerScreen(stack, null));
					}
				}
			}
		});
	}
}