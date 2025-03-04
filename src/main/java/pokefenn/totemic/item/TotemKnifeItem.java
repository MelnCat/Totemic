package pokefenn.totemic.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.init.ModBlocks;

public class TotemKnifeItem extends Item {
    public static final String KNIFE_TOTEM_KEY = "effect";

    public TotemKnifeItem(Properties props) {
        super(props);
    }

    @OnlyIn(Dist.CLIENT)
    private static ITextComponent getCarvingName(@Nullable TotemEffect effect) {
        if(effect != null)
            return effect.getDisplayName();
        else
            return new TranslationTextComponent("block.totemic.totem_base");
    }

    @Nullable
    public static TotemEffect getCarvingEffect(ItemStack stack) {
        if(stack.hasTag()) {
            String key = stack.getTag().getString(KNIFE_TOTEM_KEY);
            if(!key.isEmpty())
                return TotemicRegistries.totemEffects().getValue(new ResourceLocation(key));
            else
                return null;
        }
        else
            return null;
    }

    @SuppressWarnings("resource")
    @Override
    public ActionResultType useOn(ItemUseContext c) {
        if(c.getPlayer().isCrouching()) {
            //TODO
            return ActionResultType.FAIL;
        }
        else {
            BlockState state = c.getLevel().getBlockState(c.getClickedPos());

            TotemWoodType woodType = TotemWoodType.fromLog(state);
            if(woodType == null) {
                //Fall back to oak if it is an unrecognized log type
                if(BlockTags.LOGS_THAT_BURN.contains(state.getBlock()))
                    woodType = TotemWoodType.OAK;
                else
                    return ActionResultType.FAIL;
            }

            BlockState newState;
            TotemEffect effect = getCarvingEffect(c.getItemInHand());
            if(effect != null) {
                newState = ModBlocks.getTotemPoles().get(woodType, effect).getStateForPlacement(new BlockItemUseContext(c));
            }
            else {
                newState = ModBlocks.getTotemBases().get(woodType).getStateForPlacement(new BlockItemUseContext(c));
            }

            c.getLevel().setBlock(c.getClickedPos(), newState, 3);
            newState.getBlock().setPlacedBy(c.getLevel(), c.getClickedPos(), newState, c.getPlayer(), c.getItemInHand());
            c.getItemInHand().hurtAndBreak(1, c.getPlayer(), player -> player.broadcastBreakEvent(c.getHand()));
            c.getLevel().playSound(c.getPlayer(), c.getClickedPos(), SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

            return ActionResultType.sidedSuccess(c.getLevel().isClientSide);
        }
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        return new TranslationTextComponent(getDescriptionId(stack), getCarvingName(getCarvingEffect(stack)));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent(getDescriptionId() + ".tooltip"));
    }
}
