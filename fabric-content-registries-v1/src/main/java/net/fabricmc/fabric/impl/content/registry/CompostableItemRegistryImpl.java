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

import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Item;

import net.fabricmc.fabric.api.content.registry.v1.util.ItemContentRegistry;

public class CompostableItemRegistryImpl extends ContentRegistryImpl<Item, Float> implements ItemContentRegistry<Float> {
	public static final ItemContentRegistry<Float> INSTANCE = new CompostableItemRegistryImpl();

	public CompostableItemRegistryImpl() {
		super("compostable_item_registry",
				ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE::put, // Putter
				ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE::removeFloat, // Remover
				item -> ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.getOrDefault(item, 0.0F)); // Getter
	}
}