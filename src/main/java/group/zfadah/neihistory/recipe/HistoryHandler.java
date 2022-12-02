package group.zfadah.neihistory.recipe;

import codechicken.nei.*;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.recipe.*;
import group.zfadah.neihistory.HistoryInstance;
import group.zfadah.neihistory.Logger;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class HistoryHandler implements ICraftingHandler, IUsageHandler {

    @Override
    public ICraftingHandler getRecipeHandler(String outputId, Object... results) {
        addHistory(results);
        return this;
    }

    @Override
    public String getRecipeName() {
        return null;
    }

    @Override
    public int numRecipes() {
        return 0;
    }

    @Override
    public void drawBackground(int recipe) {}

    @Override
    public void drawForeground(int recipe) {}

    @Override
    public List<PositionedStack> getIngredientStacks(int recipe) {
        return null;
    }

    @Override
    public List<PositionedStack> getOtherStacks(int recipetype) {
        return null;
    }

    @Override
    public PositionedStack getResultStack(int recipe) {
        return null;
    }

    @Override
    public void onUpdate() {}

    @Override
    public boolean hasOverlay(GuiContainer gui, Container container, int recipe) {
        return false;
    }

    @Override
    public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer gui, int recipe) {
        return null;
    }

    @Override
    public IOverlayHandler getOverlayHandler(GuiContainer gui, int recipe) {
        return null;
    }

    @Override
    public int recipiesPerPage() {
        return 0;
    }

    @Override
    public List<String> handleTooltip(GuiRecipe<?> gui, List<String> currenttip, int recipe) {
        return null;
    }

    @Override
    public List<String> handleItemTooltip(GuiRecipe<?> gui, ItemStack stack, List<String> currenttip, int recipe) {
        return null;
    }

    @Override
    public boolean keyTyped(GuiRecipe<?> gui, char keyChar, int keyCode, int recipe) {
        return false;
    }

    @Override
    public boolean mouseClicked(GuiRecipe<?> gui, int button, int recipe) {
        return false;
    }

    @Override
    public IUsageHandler getUsageHandler(String inputId, Object... ingredients) {
        addHistory(ingredients);
        return this;
    }

    private void addHistory(Object... obj) {
        if (obj.length != 0) {
            try {
                ItemStack res = ((ItemStack) obj[0]);
                res.stackSize = 1;
                HistoryInstance.historyPanel.addHistory(res.copy());
            } catch (ClassCastException e) {
                Logger.warn(e.toString());
            }
        }
    }
}
