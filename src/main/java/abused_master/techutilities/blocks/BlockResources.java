package abused_master.techutilities.blocks;

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
        COPPER_ORE(1.0F),
        TIN_ORE(1.0F),
        LEAD_ORE(2.0F),
        SILVER_ORE(3.0F),
        NICKEL_ORE(2.0F),
        PLATINUM_ORE(3.0f);

        private BlockResources blockOres;
        private Identifier oresIdentifier;

        EnumResourceOres(float hardness) {
            this.blockOres = new BlockResources(hardness);
            this.oresIdentifier = new Identifier(TechUtilities.MODID, getName());
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
    }

    public enum EnumResoueceBlocks {
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

        EnumResoueceBlocks() {
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
