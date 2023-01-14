package group.zfadah.neihistory.history;

import codechicken.lib.vec.Rectangle4i;
import codechicken.nei.*;
import codechicken.nei.config.ConfigSet;
import group.zfadah.neihistory.HistoryInstance;
import java.util.ArrayList;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class HistoryPanel extends ItemPanel {

    public static ConfigSet world;

    private int size;

    protected static class HistoryPanelGrid extends ItemPanelGrid {
        public HistoryPanelGrid() {
            newItems = new ArrayList<>();
        }
    }

    public HistoryPanel() {
        grid = new HistoryPanelGrid();
    }

    @Override
    public void init() {
        more = null;
        less = null;
        quantity = null;
    }

    @Override
    public int getMarginLeft(GuiContainer gui) { // X
        return ItemPanels.itemPanel.getMarginLeft(gui);
    }

    @Override
    public int getMarginTop(GuiContainer gui) { // Y
        return ItemPanels.itemPanel.getMarginTop(gui) + ItemPanels.itemPanel.getHeight(gui) + PADDING;
    }

    @Override
    public int getWidth(GuiContainer gui) { // W
        return ItemPanels.itemPanel.getWidth(gui);
    }

    @Override
    public int getHeight(GuiContainer gui) { // H
        return ItemsGrid.SLOT_SIZE * 3;
    }

    @Override
    protected int resizeHeader(GuiContainer gui) {
        return 0;
    }

    @Override
    protected int resizeFooter(GuiContainer gui) {
        return 0;
    }

    @Override
    public void resize(GuiContainer gui) {
        final Rectangle4i margin =
                new Rectangle4i(getMarginLeft(gui), getMarginTop(gui), getWidth(gui), getHeight(gui));
        x = margin.x;
        y = margin.y;
        w = margin.w;
        h = margin.h;
        size = w / ItemsGrid.SLOT_SIZE * h / ItemsGrid.SLOT_SIZE;
        if (world != NEIClientConfig.world) {
            world = NEIClientConfig.world;
            if (((HistoryPanelGrid) (HistoryInstance.historyPanel.getGrid())).newItems != null)
                ((HistoryPanelGrid) (HistoryInstance.historyPanel.getGrid())).newItems.clear();
        }
        grid.setGridSize(x, y, w, h);
        grid.refresh(gui);
    }

    @Override
    public void setVisible() {
        super.setVisible();
    }

    public void addHistory(ItemStack item) {
        ArrayList<ItemStack> items1 = grid.getItems();
        if (items1.size() > 0) {
            for (int i = 0; i < items1.size(); i++) {
                if (items1.get(i).getDisplayName().equals(item.getDisplayName())) {
                    items1.remove(i);
                    break;
                }
            }
        }
        if (items1.size() == size) {
            items1.remove(size - 1);
        }
        item.stackSize = 1;
        items1.add(0, item.copy());
        ((HistoryPanelGrid) (HistoryInstance.historyPanel.getGrid())).setItems(items1);
    }

    @Override
    protected String getPositioningSettingName() {
        return null;
    }

    @Override
    public boolean handleClickExt(int mouseX, int mouseY, int button) {
        if (HistoryInstance.historyPanel.draggedStack != null) {
            return HistoryInstance.historyPanel.handleDraggedClick(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    protected boolean handleDraggedClick(int mouseX, int mouseY, int button) {
        if (draggedStack == null) {
            return false;
        }
        if (handleGUIContainerClick(draggedStack, mouseX, mouseY, button)) {

            if (draggedStack.stackSize == 0) {
                draggedStack = null;
            }

            return true;
        }

        final GuiContainer gui = NEIClientUtils.getGuiContainer();
        if (mouseX < gui.guiLeft
                || mouseY < gui.guiTop
                || mouseX >= gui.guiLeft + gui.xSize
                || mouseY >= gui.guiTop + gui.ySize) {
            draggedStack = null;
        }

        return true;
    }
}
