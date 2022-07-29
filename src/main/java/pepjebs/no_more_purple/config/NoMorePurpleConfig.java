package pepjebs.no_more_purple.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "no_more_purple")
public class NoMorePurpleConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip()
    @Comment("Set the color of your client's glint.")
    public String GLINT_COLOR = "RED";
}