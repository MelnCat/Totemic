package pokefenn.totemic.configuration;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.Config.RangeDouble;

public class ConfigGeneral
{
    @Comment("Set to false to prevent Skeletons from shooting Buffalos")
    @RequiresMcRestart
    public boolean skeletonsShouldAttackBuffalos = true;

    @Comment("Enables the generation of Tipis in villages")
    @RequiresMcRestart
    public boolean enableVillageTipi = true;

    @Comment("Enables the generation of Medicine Wheels in villages")
    @RequiresMcRestart
    public boolean enableVillageMedicineWheel = true;

    @Comment("The modifier for the ceremony time limit. Higher values will will make ceremonies easier, and lower values will make them harder.")
    @RangeDouble(min = 0, max = 2)
    public double ceremonyTimeModifier = 1d;

}