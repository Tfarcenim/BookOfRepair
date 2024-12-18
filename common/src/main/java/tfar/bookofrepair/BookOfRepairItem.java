package tfar.bookofrepair;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tfar.bookofrepair.platform.Services;

import java.util.List;

public class BookOfRepairItem extends Item {

    public static final String XP = "bookofrepair:xp";
    public static int getRequired() {
        return Services.PLATFORM.getBookXP();
    };

    public BookOfRepairItem(Properties properties) {
        super(properties);
    }


    @Override
    public boolean isFoil(ItemStack $$0) {
        return getXP($$0) >= getRequired();
    }


    @Override
    public int getBarColor(ItemStack $$0) {
        return 0x7fff00;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round((float) getXP(stack) * MAX_BAR_WIDTH / getRequired());
    }

    @Override
    public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> list, TooltipFlag $$3) {
        list.add(Component.translatable("enchantment.minecraft.mending"));
        list.add(Component.literal("XP: "+getXP($$0)+"/"+getRequired()).withStyle(ChatFormatting.GREEN));
        //list.add(Component.translatable("item.durability", this.getMaxDamage() - this.getDamageValue(), this.getMaxDamage()));

        super.appendHoverText($$0, $$1, list, $$3);
    }

    public static int getXP(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(XP) : 0;
    }

    public static int absorb(ItemStack stack, ExperienceOrb orb) {
        int bookXp = getXP(stack);
        int xp = orb.getValue();
        if (getRequired() - bookXp >= xp) {
            stack.getOrCreateTag().putInt(XP,bookXp + xp);
            return 0;
        } else {
            int consumed = getRequired() - bookXp;
            stack.getOrCreateTag().putInt(XP,getRequired());
            return xp - consumed;
        }
    }
}
