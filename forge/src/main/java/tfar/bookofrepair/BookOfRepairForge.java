package tfar.bookofrepair;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import tfar.bookofrepair.datagen.ModDatagen;
import tfar.bookofrepair.mixin.ExperienceOrbMixin;

import java.util.List;

@Mod(BookOfRepair.MOD_ID)
public class BookOfRepairForge {
    
    public BookOfRepairForge() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER,TomlConfig.SERVER_SPEC);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ModDatagen::gather);
        bus.addListener(this::register);
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
        MinecraftForge.EVENT_BUS.addListener(this::pickupXP);
        MinecraftForge.EVENT_BUS.addListener(this::trading);
        MinecraftForge.EVENT_BUS.addListener(this::anvil);
        // Use Forge to bootstrap the Common mod.
        BookOfRepair.init();
        
    }

    void register(RegisterEvent event) {
        event.register(Registries.ITEM,BookOfRepair.id("book_of_repair"),() -> Init.BOOK_OF_REPAIR);
    }

    void pickupXP(PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        ExperienceOrb orb = event.getOrb();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.getItem() instanceof BookOfRepairItem) {
                int remainder = BookOfRepairItem.absorb(stack,orb);
                if (remainder > 0) {
                    ((ExperienceOrbMixin)orb).setValue(remainder);
                } else {
                    ((ExperienceOrbMixin)orb).setValue(0);
                   // event.setCanceled(true);
                    break;
                }
            }
        }
    }

    void trading(VillagerTradesEvent event) {
        VillagerProfession type = event.getType();
        if (type == VillagerProfession.LIBRARIAN) {
            event.getTrades().get(1).add(
                    // ItemsAndEmeraldsToItems(ItemLike pFromItem, int pFromCount, int pEmeraldCost, Item pToItem, int pToCount, int pMaxUses, int pVillagerXp) {
                    new VillagerTrades.ItemsAndEmeraldsToItems(Items.BOOK,1,10,Init.BOOK_OF_REPAIR,1,14,10));
        }
    }

    void anvil(AnvilUpdateEvent event) {
        ItemStack right = event.getRight();
        ItemStack left = event.getLeft();
        if (left.isDamaged() && left.isRepairable() && right.getItem() instanceof BookOfRepairItem && BookOfRepairItem.getXP(right) >= BookOfRepairItem.getRequired()) {
            ItemStack result = left.copy();
            result.setDamageValue(0);
            event.setOutput(result);
            event.setCost(1);
        }
    }
}