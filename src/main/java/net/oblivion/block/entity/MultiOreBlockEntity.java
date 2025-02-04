package net.oblivion.block.entity;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.oblivion.block.MultiOreBlock;
import net.oblivion.init.BlockInit;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MultiOreBlockEntity extends BlockEntity {

    private int drillCount = 7;

    public MultiOreBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.MULTI_ORE_BLOCK_ENTITY, pos, state);
    }

    public MultiOreBlockEntity(BlockPos pos, BlockState state, int drillCount) {
        this(pos, state);
        this.drillCount = drillCount;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.drillCount = nbt.getInt("DrillCount");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt("DrillCount", this.drillCount);
    }

    public void decrementDrillCount(@Nullable BlockPos dropStackPos) {
        this.drillCount--;
        handleMultiOre(dropStackPos);
    }

    public int getDrillCount() {
        return this.drillCount;
    }

    private void handleMultiOre(@Nullable BlockPos dropStackPos) {
        if (this.getWorld() != null && !this.getWorld().isClient()) {
            dropStackPos = dropStackPos != null ? dropStackPos : this.getPos();
            if (this.drillCount > 0) {
                LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld) this.getWorld()).add(LootContextParameters.ORIGIN, Vec3d.ofCenter(this.getPos()))
                        .add(LootContextParameters.TOOL, new ItemStack(Items.NETHERITE_PICKAXE));

                Storage<ItemVariant> storage = ItemStorage.SIDED.find(world, dropStackPos, null);

                for (ItemStack stack : this.getCachedState().getDroppedStacks(builder)) {
                    if (storage != null) {
                        try (Transaction transaction = Transaction.openOuter()) {
                            long amountInserted = storage.insert(ItemVariant.of(stack), stack.getCount(), transaction);
                            if (amountInserted == stack.getCount()) {
                                transaction.commit();
                            } else {
                                ItemScatterer.spawn(this.getWorld(), dropStackPos.getX(), dropStackPos.getY(), dropStackPos.getZ(), stack);
                            }
                        }
                    } else {
                        ItemScatterer.spawn(this.getWorld(), dropStackPos.getX(), dropStackPos.getY(), dropStackPos.getZ(), stack);
                    }
                }
            } else {
                List<BlockPos> list = ((MultiOreBlock) this.getCachedState().getBlock()).getMultiOreBlockPoses(this.getWorld(), this.getPos());
                if (!list.isEmpty()) {
                    boolean isMultiOreEmpty = true;
                    for (BlockPos blockPos : list) {
                        if (this.getWorld().getBlockState(blockPos).getBlock() instanceof MultiOreBlock && this.getWorld().getBlockEntity(blockPos) instanceof MultiOreBlockEntity multiOreBlockEntity) {
                            if (multiOreBlockEntity.getDrillCount() > 0) {
                                isMultiOreEmpty = false;
                                multiOreBlockEntity.decrementDrillCount(dropStackPos);
                                break;
                            }
                        }
                    }
                    if (isMultiOreEmpty) {
                        ((MultiOreBlock) this.getCachedState().getBlock()).breakMultiOre(this.getWorld(), this.getPos(), null);
                    }
                } else {
                    ((MultiOreBlock) this.getCachedState().getBlock()).breakMultiOre(this.getWorld(), this.getPos(), null);
                }

            }
        }
    }

}
