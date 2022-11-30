package group.zfadah.neihistory.core;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import java.util.Collections;

public class NEIHistoryContainer extends DummyModContainer {
    public NEIHistoryContainer() {
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "neihistorycore";
        meta.name = "NEIHistoryCore";
        meta.version = "1.0.0";
        meta.authorList = Collections.singletonList("zfadah");
        meta.description = "Coremod for NEIHistory";
        meta.url = "";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }
}
