package tfar.bookofrepair.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.bookofrepair.BookOfRepair;
import tfar.bookofrepair.Init;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BookOfRepair.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        makeOneLayerItem(Init.BOOK_OF_REPAIR);
        //makeSimpleBlockItem(ModBlocks.DEPOSIT_STATION.asItem());
    }

    protected void makeSimpleBlockItem(Item item, ResourceLocation loc) {
        String s = BuiltInRegistries.ITEM.getKey(item).toString();
        getBuilder(s)
                .parent(getExistingFile(loc));
    }

    protected ModelFile.ExistingModelFile generated = getExistingFile(mcLoc("item/generated"));

    protected void makeSimpleBlockItem(Item item) {
        makeSimpleBlockItem(item, BookOfRepair.id("block/" + BuiltInRegistries.ITEM.getKey(item).getPath()));
    }

    protected void makeOneLayerItem(Item item, ResourceLocation texture) {
        makeOneLayerItem(item,texture,generated);
    }

    protected void makeOneLayerItem(Item item, ResourceLocation texture, ModelFile parent) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        if (existingFileHelper.exists(texture, PackType.CLIENT_RESOURCES, ".png", "textures")) {
            getBuilder(path).parent(parent)
                    .texture("layer0",texture);
        } else {
            System.out.println("no texture for " + item + " found, skipping");
        }
    }


    protected void makeOneLayerItem(Item item) {
        makeOneLayerItem(item,generated);
    }

    protected void makeOneLayerItem(Item item,ModelFile parent) {
        ResourceLocation texture = BuiltInRegistries.ITEM.getKey(item);
        makeOneLayerItem(item, texture.withPrefix("item/"),parent);
    }

}
