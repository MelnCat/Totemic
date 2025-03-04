package pokefenn.totemic.init;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.item.TotemKnifeItem;
import pokefenn.totemic.item.TotemPoleItem;
import pokefenn.totemic.item.music.FluteItem;
import pokefenn.totemic.item.music.InfusedFluteItem;

@ObjectHolder(Totemic.MOD_ID)
public final class ModItems {
    public static final FluteItem flute = null;
    public static final InfusedFluteItem infused_flute = null;
    public static final TotemKnifeItem totem_whittling_knife = null;

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(new FluteItem(new Properties().stacksTo(1).tab(Totemic.itemGroup)).setRegistryName("flute"),
                new InfusedFluteItem(new Properties().stacksTo(1).tab(Totemic.itemGroup)).setRegistryName("infused_flute"),
                new TotemKnifeItem(new Properties().stacksTo(1).durability(250).tab(Totemic.itemGroup)).setRegistryName("totem_whittling_knife"));

        for(Block block: ModBlocks.getBlocksWithItemBlock())
            event.getRegistry().register(makeItemBlock(block));
    }

    private static Item makeItemBlock(Block block) {
        //TODO: Possibly there is a better solution
        if(block instanceof TotemPoleBlock) {
            return new TotemPoleItem((TotemPoleBlock)block, new Properties().tab(Totemic.itemGroup)).setRegistryName(block.getRegistryName());
        }
        else
            return new BlockItem(block, new Properties().tab(Totemic.itemGroup)).setRegistryName(block.getRegistryName());
    }
}
