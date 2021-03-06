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

package net.fabricmc.fabric.mixin.registry;

import net.fabricmc.fabric.impl.registry.ListenableRegistry;
import net.fabricmc.fabric.impl.registry.trackers.*;
import net.fabricmc.fabric.impl.registry.trackers.vanilla.BiomeParentTracker;
import net.fabricmc.fabric.impl.registry.trackers.vanilla.BlockInitTracker;
import net.fabricmc.fabric.impl.registry.trackers.vanilla.BlockItemTracker;
import net.minecraft.Bootstrap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class MixinBootstrap {
	@SuppressWarnings("unchecked")
	@Inject(method = "setOutputStreams", at = @At("RETURN"))
	private static void initialize(CallbackInfo info) {
		// These seemingly pointless accesses are done to make sure each
		// static initializer is called, to register vanilla-provided blocks
		// and items from the respective classes - otherwise, they would
		// duplicate our calls from below.
		Object oBiome = Biomes.THE_END;
		Object oBlock = Blocks.AIR;
		Object oFluid = Fluids.EMPTY;
		Object oItem = Items.AIR;

		// state ID tracking
		StateIdTracker.register(Registry.BLOCK, Block.STATE_IDS, (block) -> block.getStateFactory().getStates());
		StateIdTracker.register(Registry.FLUID, Fluid.STATE_IDS, (fluid) -> fluid.getStateFactory().getStates());

		// map tracking
		BiomeParentTracker.register(Registry.BIOME);
		BlockItemTracker.register(Registry.ITEM);

		// block initialization, like Blocks
		BlockInitTracker.register(Registry.BLOCK);
	}
}
