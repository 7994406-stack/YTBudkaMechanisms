package com.ytbudka.mechanisms.block;
import com.ytbudka.mechanisms.blockentity.CoalGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
public class CoalGeneratorBlock extends BaseEntityBlock {
    public CoalGeneratorBlock(Properties p) { super(p); }
    @Override public RenderShape getRenderShape(BlockState s) { return RenderShape.MODEL; }
    @Override public BlockEntity newBlockEntity(BlockPos p, BlockState s) { return new CoalGeneratorBlockEntity(p, s); }
    @Override public InteractionResult use(BlockState s, Level l, BlockPos p, Player player, InteractionHand h, BlockHitResult r) {
        if (!l.isClientSide()) NetworkHooks.openScreen((ServerPlayer)player, (MenuProvider)l.getBlockEntity(p), p);
        return InteractionResult.sidedSuccess(l.isClientSide());
    }
    @Override public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level l, BlockState s, BlockEntityType<T> t) {
        return l.isClientSide() ? null : (lvl, pos, st, be) -> ((CoalGeneratorBlockEntity)be).tick();
    }
}
