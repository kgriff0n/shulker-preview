package io.github.kgriff0n;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.Collections;

public class ShulkerUtil {

    /**
     * Allows you to get an inventory from an item (especially a shulker box).
     * @param stack the item whose inventory you want
     * @return An array list, with 27 elements, an empty slot is represented by null
     */
    public static ArrayList<ItemStack> getShulkerInventory(ItemStack stack) {

        /* Initialize an empty inventory */
        ArrayList<ItemStack> shulkerInventory = new ArrayList<>(Collections.nCopies(27, null));
        int slot;

        NbtCompound nbt = stack.getNbt();
        if (nbt != null) {
            if (nbt.contains("BlockEntityTag")) {
                NbtCompound blockEntityTag = nbt.getCompound("BlockEntityTag");
                if (blockEntityTag.contains("Items")) {
                    NbtList items = blockEntityTag.getList("Items", 10);
                    for (NbtElement item : items) {
                        slot = ((NbtCompound) item).getByte("Slot");
                        shulkerInventory.set(slot, ItemStack.fromNbt((NbtCompound) item));
                    }
                }
            }
        }
        return shulkerInventory;
    }

}
