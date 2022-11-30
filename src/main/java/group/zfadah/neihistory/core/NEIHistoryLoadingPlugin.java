package group.zfadah.neihistory.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import java.util.Map;

@IFMLLoadingPlugin.Name("NEIHistoryCore")
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions("group.zfadah.neihistory.core.transformer.ItemPanelTransformer"
        + ",group.zfadah.neihistory.core.transformer.LayoutStyleTransformer")
public class NEIHistoryLoadingPlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {
            "group.zfadah.neihistory.core.transformer.ItemPanelTransformer",
            "group.zfadah.neihistory.core.transformer.LayoutStyleTransformer",
            "group.zfadah.neihistory.core.transformer.LayoutManagerTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return "group.zfadah.neihistory.core.NEIHistoryContainer";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
