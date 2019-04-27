package abused_master.techutilities.blocks;

import abused_master.abusedlib.blocks.BlockBase;
import abused_master.techutilities.TechUtilities;
import net.minecraft.block.Material;

public class BlockResources extends BlockBase {

    public BlockResources(String name, float hardness) {
        super(name, Material.STONE, hardness, TechUtilities.modItemGroup);
    }

    public enum EnumResourceOres {
        COPPER_ORE(1.0F, 3, 64, 40, TechUtilities.config.getBoolean("generateCopper")),
        TIN_ORE(1.0F, 4, 64, 40, TechUtilities.config.getBoolean("generateTin")),
        LEAD_ORE(2.0F, 3, 50, 40, TechUtilities.config.getBoolean("generateLead")),
        SILVER_ORE(3.0F, 3, 40, 35, TechUtilities.config.getBoolean("generateSilver")),
        NICKEL_ORE(2.0F, 4, 60, 40, TechUtilities.config.getBoolean("generateNickel"));

        private BlockResources blockOres;
        private int maxHeight;
        private int spawnRate;
        private boolean generateOre;
        private int veinSize;

        EnumResourceOres(float hardness, int veinSize, int maxHeight, int spawnRate, boolean generate) {
            this.blockOres = new BlockResources(getName(), hardness);
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
        NICKEL_BLOCK;

        private BlockResources blockOres;

        EnumResourceBlocks() {
            this.blockOres = new BlockResources(getName(), 2.0f);
        }

        public String getName() {
            return this.toString().toLowerCase();
        }

        public BlockResources getBlockOres() {
            return this.blockOres;
        }
    }
}