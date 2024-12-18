package tfar.bookofrepair;

import net.minecraft.world.item.Item;

public class Init {
    public static final Item BOOK_OF_REPAIR = new BookOfRepairItem(new Item.Properties().stacksTo(1));
}
//step 1 - mending book + 8 emeralds = knowledge book of repairing (in a crafting grid)
//
//step 2: - knowledge book of repairing + 8 enchanted apples = book of repairing (the villager trade)
//
//step 3 - book of repairing + item with not max durability = fully repaired item (in an anvil)