package abused_master.techutilities.blocks;

import abused_master.techutilities.Config;
import abused_master.techutilities.TechUtilities;
import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;

public class BlockResources extends Block {

    public BlockResources(float hardness) {
        super(FabricBlockSettings.of(Material.STONE).strength(hardness, 1.0f).build());
    }

    public enum EnumResourceOres {
        COPPER_ORE(1.0F, 3, 64, 40, Config.generateCopper),
        TIN_ORE(1.0F, 4, 64, 40, Config.generateTin),
        LEAD_ORE(2.0F, 3, 50, 40, Config.generateLead),
        SILVER_ORE(3.0F, 3, 40, 35, Config.generateSilver),
        NICKEL_ORE(2.0F, 4, 60, 40, Config.generateNickel),
        PLATINUM_ORE(3.0f, 2, 16, 10, Config.generatePlatinum);

        private BlockResources blockOres;
        private Identifier oresIdentifier;
        private int maxHeight;
        private int spawnRate;
        private boolean generateOre;
        private int veinSize;

        EnumResourceOres(float hardness, int veinSize, int maxHeight, int spawnRate, boolean generate) {
            this.blockOres = new BlockResources(hardness);
            this.oresIdentifier = new Identifier(TechUtilities.MODID, getName());
            this.maxHeight = maxHeight;
            this.spawnRate = spawnRate;
            this.generateOre = generate;
            this.veinSize = veinSize;
        }

        public String getName() {
            return this.toString().toLowerCase();
        }

        public BlockResources getBlockOres() {
            return this.blockOres;
        }

        public Identifier getOresIdentifier() {
            return oresIdentifier;
        }

        public int getMaxHeight() {
            return maxHeight;
        }

        public int getSpawnRate() {
            return spawnRate;
        }

        public int getVeinSize() {
            return veinSize;
        }

        public boolean generateOre() {
            return generateOre;
        }
    }

    public enum EnumResourceBlocks {
        COPPER_BLOCK,
        TIN_BLOCK,
        LEAD_BLOCK,
        SILVER_BLOCK,
        NICKEL_BLOCK,
        PLATINUM_BLOCK,
        INVAR_BLOCK,
        ELECTRUM_BLOCK;

        private BlockResources blockOres;
        private Identifier oresIdentifier;

        EnumResourceBlocks() {
            this.blockOres = new BlockResources(2.0f);
            this.oresIdentifier = new Identifier(TechUtilities.MODID, getName());
        }

        public String getName() {
            return this.toString().toLowerCase();
        }

        public BlockResources getBlockOres() {
            return this.blockOres;
        }

        public Identifier getOresIdentifier() {
            return this.oresIdentifier;
        }
    }
}
