package group.zfadah.neihistory;

import codechicken.nei.api.API;
import codechicken.nei.guihook.GuiContainerManager;
import cpw.mods.fml.common.event.*;

public class ClientProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {
        API.registerUsageHandler(HistoryInstance.historyHandler);
        API.registerRecipeHandler(HistoryInstance.historyHandler);
        GuiContainerManager.addInputHandler(HistoryInstance.historyHandler);
    }
}
