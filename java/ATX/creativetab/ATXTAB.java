package ATX.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ATXTAB extends CreativeTabs {

    public ATXTAB(int par1, String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem()
    {
    	return Items.boat;
    }
}