/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.climbable.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import net.fabricmc.fabric.climbable.api.FabricClimbableTags;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
	public MixinLivingEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow
	public abstract BlockState getBlockState();

	@Inject(method = "isClimbing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getBlockState()Lnet/minecraft/block/BlockState;"), cancellable = true)
	public void onIsClimbing(CallbackInfoReturnable<Boolean> info) {
		if (this.getBlockState().matches(FabricClimbableTags.CLIMBABLE)) {
			info.setReturnValue(true);
		}
	}
}