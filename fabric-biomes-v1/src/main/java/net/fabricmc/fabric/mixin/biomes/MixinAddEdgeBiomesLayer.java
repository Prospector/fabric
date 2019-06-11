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

package net.fabricmc.fabric.mixin.biomes;

import net.fabricmc.fabric.impl.biomes.InternalBiomeData;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.AddEdgeBiomesLayer;
import net.minecraft.world.biome.layer.LayerRandomnessSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AddEdgeBiomesLayer.class)
public class MixinAddEdgeBiomesLayer {

	@Inject(at = @At("HEAD"), method = "sample", cancellable = true)
	private void sample(LayerRandomnessSource random, int north, int east, int south, int west, int center, CallbackInfoReturnable<Integer> info) {
		Biome centerBiome = Registry.BIOME.get(center);

		if (InternalBiomeData.SHORE_MAP.containsKey(centerBiome) && fabric_isShore(north, east, south, west)) {
			info.setReturnValue(InternalBiomeData.SHORE_MAP.get(centerBiome).pickRandom(random));
		}
		else if (InternalBiomeData.EDGE_MAP.containsKey(centerBiome) && fabric_isEdge(north, east, south, west, center)) {
			info.setReturnValue(InternalBiomeData.EDGE_MAP.get(centerBiome).pickRandom(random));
		}
	}

	private boolean fabric_isEdge(int north, int east, int south, int west, int biome) {
		return north != biome || east != biome || south != biome || west != biome;
	}

	private boolean fabric_isShore(int north, int east, int south, int west) {
		return fabric_isOceanBiome(north) || fabric_isOceanBiome(east) || fabric_isOceanBiome(south) || fabric_isOceanBiome(west);
	}

	private boolean fabric_isOceanBiome(int rawId) {
		Biome biome = Registry.BIOME.get(rawId);
		return biome != null && biome.getCategory() == Biome.Category.OCEAN;
	}

}
