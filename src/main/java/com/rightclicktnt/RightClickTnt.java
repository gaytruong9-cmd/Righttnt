package com.rightclicktnt;

   import net.fabricmc.api.ModInitializer;
   import net.fabricmc.fabric.api.event.player.UseBlockCallback;
   import net.minecraft.block.Blocks;
   import net.minecraft.entity.player.PlayerEntity;
   import net.minecraft.sound.SoundCategory;
   import net.minecraft.sound.SoundEvents;
   import net.minecraft.util.ActionResult;
   import net.minecraft.util.Hand;
   import net.minecraft.util.hit.BlockHitResult;
   import net.minecraft.util.math.BlockPos;
   import net.minecraft.world.World;

   public class RightClickTnt implements ModInitializer {

   	@Override
   	public void onInitialize() {
   		UseBlockCallback.EVENT.register(this::onUseBlock);
   	}

   	private ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
   		if (hand != Hand.MAIN_HAND) {
   			return ActionResult.PASS;
   		}
   		if (world.isClient) {
   			return ActionResult.SUCCESS;
   		}
   		if (player.isSneaking()) {
   			return ActionResult.PASS;
   		}

   		BlockPos clickedPos = hitResult.getBlockPos();
   		BlockPos placePos = clickedPos.offset(hitResult.getSide());

   		if (!world.getBlockState(placePos).isAir()) {
   			return ActionResult.PASS;
   		}

   		world.setBlockState(placePos, Blocks.COBWEB.getDefaultState());
   		world.playSound(null, placePos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);

   		return ActionResult.SUCCESS;
   	}
   }
