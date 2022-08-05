package com.technovision.alchemicaldrugs.api.item;

import com.technovision.alchemicaldrugs.AlchemicalDrugs;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemWithTooltip extends Item {

    String chemicalFormula;

    public ItemWithTooltip(String chemicalFormula) {
        super(new FabricItemSettings().group(AlchemicalDrugs.TAB));
        this.chemicalFormula = chemicalFormula;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (chemicalFormula != null) tooltip.add(Text.literal(chemicalFormula).formatted(Formatting.DARK_AQUA));
    }
}
