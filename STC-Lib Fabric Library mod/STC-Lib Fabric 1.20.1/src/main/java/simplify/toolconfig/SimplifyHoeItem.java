/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package simplify.toolconfig;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SimplifyHoeItem
extends SimplifyToolItem {
    /**
     * A map of input blocks to predicate-consumer action pairs.
     * 
     * <p>Tilling works so that if the predicate succeeds, the consumer (the real action)
     * is executed, and the hoe is damaged.
     */
    protected static final Map<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>> TILLING_ACTIONS = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Pair.of(SimplifyHoeItem::canTillFarmland, SimplifyHoeItem.createTillAction(Blocks.FARMLAND.getDefaultState())), Blocks.DIRT_PATH, Pair.of(SimplifyHoeItem::canTillFarmland, SimplifyHoeItem.createTillAction(Blocks.FARMLAND.getDefaultState())), Blocks.DIRT, Pair.of(SimplifyHoeItem::canTillFarmland, SimplifyHoeItem.createTillAction(Blocks.FARMLAND.getDefaultState())), Blocks.COARSE_DIRT, Pair.of(SimplifyHoeItem::canTillFarmland, SimplifyHoeItem.createTillAction(Blocks.DIRT.getDefaultState())), Blocks.ROOTED_DIRT, Pair.of(itemUsageContext -> true, SimplifyHoeItem.createTillAndDropAction(Blocks.DIRT.getDefaultState(), Items.HANGING_ROOTS))));
    private final TagKey<Block> effectiveBlocks;

    public SimplifyHoeItem(SimplifyToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, BlockTags.HOE_MINEABLE, settings);
        this.effectiveBlocks = BlockTags.HOE_MINEABLE;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos;
        World world = context.getWorld();
        Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair = TILLING_ACTIONS.get(world.getBlockState(blockPos = context.getBlockPos()).getBlock());
        if (pair == null) {
            return ActionResult.PASS;
        }
        Predicate<ItemUsageContext> predicate = pair.getFirst();
        Consumer<ItemUsageContext> consumer = pair.getSecond();
        if (predicate.test(context)) {
            PlayerEntity playerEntity = context.getPlayer();
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!world.isClient) {
                consumer.accept(context);
                if (playerEntity != null) {
                    context.getStack().damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
                }
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    /**
     * {@return a tilling action that sets a block state}
     * 
     * @param result the tilled block state
     */
    public static Consumer<ItemUsageContext> createTillAction(BlockState result) {
        return context -> {
            context.getWorld().setBlockState(context.getBlockPos(), result, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            context.getWorld().emitGameEvent(GameEvent.BLOCK_CHANGE, context.getBlockPos(), GameEvent.Emitter.of(context.getPlayer(), result));
        };
    }

    /**
     * {@return a tilling action that sets a block state and drops an item}
     * 
     * @param droppedItem the item to drop
     * @param result the tilled block state
     */
    public static Consumer<ItemUsageContext> createTillAndDropAction(BlockState result, ItemConvertible droppedItem) {
        return context -> {
            context.getWorld().setBlockState(context.getBlockPos(), result, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            context.getWorld().emitGameEvent(GameEvent.BLOCK_CHANGE, context.getBlockPos(), GameEvent.Emitter.of(context.getPlayer(), result));
            Block.dropStack(context.getWorld(), context.getBlockPos(), context.getSide(), new ItemStack(droppedItem));
        };
    }

    /**
     * {@return whether the used block can be tilled into farmland}
     * This method is used as the tilling predicate for most vanilla blocks except rooted dirt.
     */
    public static boolean canTillFarmland(ItemUsageContext context) {
        return context.getSide() != Direction.DOWN && context.getWorld().getBlockState(context.getBlockPos().up()).isAir();
    }
    @Override
    public boolean isSuitableFor(BlockState state) {
        int i = this.getMaterial().getMiningLevel();
        if (i < MiningLevels.DIAMOND && state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        }
        if (i < MiningLevels.IRON && state.isIn(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        }
        if (i < MiningLevels.STONE && state.isIn(BlockTags.NEEDS_STONE_TOOL)) {
            return false;
        }
        return state.isIn(this.effectiveBlocks);
    }
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.OAK_LEAVES)) {
            return 15.0F;
        }
        if (state.isOf(Blocks.SPRUCE_LEAVES)) {
            return 100f;
        }
        if (state.isOf(Blocks.BIRCH_LEAVES)) {
            return 100f;
        }
        if (state.isOf(Blocks.JUNGLE_LEAVES)) {
            return 100f;
        }
        if (state.isOf(Blocks.ACACIA_LEAVES)) {
            return 100f;
        }
        if (state.isOf(Blocks.DARK_OAK_LEAVES)) {
            return 100f;
        }
        if (state.isOf(Blocks.MANGROVE_LEAVES)) {
            return 100f;
        }
        if (state.isOf(Blocks.CHERRY_LEAVES)) {
            return 100f;
        }
            return state.isIn(BlockTags.HOE_MINEABLE) ? 1.5F : 1.0F;


    }
}

