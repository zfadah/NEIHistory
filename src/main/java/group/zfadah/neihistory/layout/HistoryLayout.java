package group.zfadah.neihistory.layout;

import codechicken.nei.Button;
import codechicken.nei.VisiblityData;
import codechicken.nei.api.LayoutStyle;
import group.zfadah.neihistory.HistoryInstance;
import net.minecraft.client.gui.inventory.GuiContainer;

public class HistoryLayout extends LayoutStyle {
    @Override
    public void init() {}

    @Override
    public void reset() {}

    @Override
    public void layout(GuiContainer gui, VisiblityData visibility) {
        HistoryInstance.historyPanel.resize(gui);
    }

    @Override
    public String getName() {
        return "NEIHistory";
    }

    @Override
    public void drawButton(Button button, int mousex, int mousey) {}

    @Override
    public void drawSubsetTag(String text, int x, int y, int w, int h, int state, boolean mouseover) {}
}
