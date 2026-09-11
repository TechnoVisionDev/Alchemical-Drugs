package com.technovision.alchemicaldrugs.api.item;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.function.Consumer;
public class ItemWithTooltip extends Item {
    private final String chemicalFormula;
    public ItemWithTooltip(Properties properties, String chemicalFormula) {
        super(properties);
        this.chemicalFormula = chemicalFormula;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        if (chemicalFormula != null) tooltip.accept(Component.literal(chemicalFormula).withStyle(ChatFormatting.DARK_AQUA));
    }
}
