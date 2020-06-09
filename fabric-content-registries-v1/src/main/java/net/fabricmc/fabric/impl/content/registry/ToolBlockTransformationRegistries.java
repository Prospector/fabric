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

package net.fabricmc.fabric.impl.content.registry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

import net.fabricmc.fabric.api.content.registry.v1.util.ContentRegistry;

public final class ToolBlockTransformationRegistries {
	public static final ContentRegistry<Block, BlockState> TILLABLE_BLOCKS = new ContentRegistryImpl<>("tillable_block_registry", HoeAccessor::addTillable, HoeAccessor::removeTillable, HoeAccessor::getTilled);
	public static final ContentRegistry<Block, BlockState> FLATTENABLE_BLOCKS = new ContentRegistryImpl<>("flattenable_block_registry", ShovelAccessor::addFlattenable, ShovelAccessor::removeFlattenable, ShovelAccessor::getFlattenable);
	public static final ContentRegistry<Block, Block> STRIPPABLE_BLOCKS = new ContentRegistryImpl<>("strippable_block_registry", AxeAccessor::addStrippable, AxeAccessor::removeStrippable, AxeAccessor::getStrippable);

	static class HoeAccessor extends HoeItem {
		private HoeAccessor(ToolMaterial material, float attackSpeed, Settings settings) {
			super(material, attackSpeed, settings);
		}

		static void addTillable(Block block, BlockState tilled) {
			TILLED_BLOCKS.put(block, tilled);
		}

		static void removeTillable(Block block) {
			TILLED_BLOCKS.remove(block);
		}

		static BlockState getTilled(Block block) {
			return TILLED_BLOCKS.get(block);
		}
	}

	static class ShovelAccessor extends ShovelItem {
		private ShovelAccessor(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
			super(material, attackDamage, attackSpeed, settings);
		}

		static void addFlattenable(Block block, BlockState flattened) {
			PATH_BLOCKSTATES.put(block, flattened);
		}

		static void removeFlattenable(Block block) {
			PATH_BLOCKSTATES.remove(block);
		}

		static BlockState getFlattenable(Block block) {
			return PATH_BLOCKSTATES.get(block);
		}
	}

	static class AxeAccessor extends AxeItem {
		private AxeAccessor(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
			super(material, attackDamage, attackSpeed, settings);
		}

		static void addStrippable(Block block, Block stripped) {
			STRIPPED_BLOCKS.put(block, stripped);
		}

		static void removeStrippable(Block block) {
			STRIPPED_BLOCKS.remove(block);
		}

		static Block getStrippable(Block block) {
			return STRIPPED_BLOCKS.get(block);
		}
	}
}