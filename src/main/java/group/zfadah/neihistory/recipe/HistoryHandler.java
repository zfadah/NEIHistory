package group.zfadah.neihistory.recipe;

import codechicken.nei.*;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.guihook.IContainerInputHandler;
import codechicken.nei.recipe.*;
import group.zfadah.neihistory.HistoryInstance;
import group.zfadah.neihistory.Logger;
import group.zfadah.neihistory.history.HistoryPanel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import java.util.List;

public class HistoryHandler implements ICraftingHandler, IUsageHandler, IContainerInputHandler {

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
                HistoryInstance.historyPanel.addHistorys(res.copy());
            } catch (ClassCastException e) {
                Logger.warn(e.toString());
            }
        }
    }

    /*
    Implment of IContainerInputHandler
     */

    @Override
    public boolean keyTyped(GuiContainer gui, char keyChar, int keyCode) {
        return false;
    }

    @Override
    public void onKeyTyped(GuiContainer gui, char keyChar, int keyID) {}

    @Override
    public boolean lastKeyTyped(GuiContainer gui, char keyChar, int keyID) {
        return false;
    }

    @Override
    public boolean mouseClicked(GuiContainer gui, int mousex, int mousey, int button) {
        return false;
    }

    @Override
    public void onMouseClicked(GuiContainer gui, int mousex, int mousey, int button) {}

    @Override
    public void onMouseUp(GuiContainer gui, int mousex, int mousey, int button) {
        HistoryPanel his = HistoryInstance.historyPanel;

        ItemPanel.ItemPanelSlot slotMouseOver = his.getGrid().getSlotMouseOver(mousex, mousey);
        if (slotMouseOver != null && slotMouseOver.slotIndex == his.mouseDownSlot && his.draggedStack == null) {
            ItemStack item = slotMouseOver.item;
            if (NEIController.manager.window instanceof GuiRecipe || !NEIClientConfig.canCheatItem(item)) {
                if (button == 0) {
                    GuiCraftingRecipe.openRecipeGui("item", item);
                } else if (button == 1) {
                    GuiUsageRecipe.openRecipeGui("item", item);
                }
                his.mouseDownSlot = -1;
                return;
            }
            NEIClientUtils.cheatItem(his.getStack(slotMouseOver.slotIndex), button, -1);
        }
    }

    @Override
    public boolean mouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled) {
        return false;
    }

    @Override
    public void onMouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled) {}

    @Override
    public void onMouseDragged(GuiContainer gui, int mousex, int mousey, int button, long heldTime) {}

}
