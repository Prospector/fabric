package net.fabricmc.fabric.impl.biomes;

import net.fabricmc.fabric.api.biomes.v1.Climate;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class ClimateBiomeInjection {

	private Biome biome;
	private Climate climate;

	public ClimateBiomeInjection(final Biome biome, final Climate climate) {
		this.biome = biome;
		this.climate = climate;
	}

	public Biome getBiome() {
		return biome;
	}

	public Climate getClimate() {
		return climate;
	}

	public int getRawId() {
		return Registry.BIOME.getRawId(biome);
	}

}
