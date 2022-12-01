package group.zfadah.neihistory;

import codechicken.nei.api.API;
import cpw.mods.fml.common.event.*;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event) {
        API.registerUsageHandler(HistoryInstance.historyHandler);
        API.registerRecipeHandler(HistoryInstance.historyHandler);
    }

    public void serverAboutToStart(FMLServerAboutToStartEvent event) {}

    // register server commands in this event handler
    public void serverStarting(FMLServerStartingEvent event) {}

    public void serverStarted(FMLServerStartedEvent event) {}

    public void serverStopping(FMLServerStoppingEvent event) {}

    public void serverStopped(FMLServerStoppedEvent event) {}
}
