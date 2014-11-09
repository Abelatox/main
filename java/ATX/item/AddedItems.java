package ATX.item;

import org.apache.commons.codec.language.Nysiis;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.common.ChestGenHooks;
import ATX.KingdomKeys;
import ATX.lib.ConfigBooleans;
import ATX.lib.Reference;
import ATX.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

public class AddedItems {

	//TODO Rename all keyblades, chains and recipes
	//TODO Add chest generation of all recipes
	//TODO Fix music discs

	public static Item ValorFormOrb;
	public static ArmorMaterial OrganizationArmourMaterial;
	public static ToolMaterial AtoxMaterial;

	public static void initKeyBlades(){
		AtoxMaterial = EnumHelperClient.addToolMaterial(Strings.Atox, 3, -1, 6.0F, 9, 30); Foudre = new ItemAtox(FoudreMaterial).setFull3D().setUnlocalizedName(Strings.Foudre); GameRegistry.registerItem(Foudre, Strings.Foudre);
	}

	public static void intiArmour(){
		//Organization
		OrganizationArmourMaterial = EnumHelperClient.addArmorMaterial("ORGANIZATION", 68, new int[] { 4, 8, 6, 3 }, 30);
//		OrganizationHood = new OrganizationArmor(OrganizationArmourMaterial , KingdomKeys.proxy.addArmor(OrganizationArmourMaterial.name()), 0, "ORGANIZATION_1", Strings.OHood); GameRegistry.registerItem(OrganizationHood, Strings.OHood);
//		OrganizationCoat = new OrganizationArmor(OrganizationArmourMaterial, KingdomKeys.proxy.addArmor(OrganizationArmourMaterial.name()), 1, "ORGANIZATION_1", Strings.OCoat); GameRegistry.registerItem(OrganizationCoat, Strings.OCoat);
//		OrganizationLegs = new OrganizationArmor(OrganizationArmourMaterial, KingdomKeys.proxy.addArmor(OrganizationArmourMaterial.name()), 2, "ORGANIZATION_2", Strings.OLegs); GameRegistry.registerItem(OrganizationLegs, Strings.OLegs);
//		OrganizationBoots = new OrganizationArmor(OrganizationArmourMaterial, KingdomKeys.proxy.addArmor(OrganizationArmourMaterial.name()), 3, "ORGANIZATION_1", Strings.OBoots); GameRegistry.registerItem(OrganizationBoots, Strings.OBoots);
	
	//	OrganizationArmourMaterial.customCraftingMaterial = DarkLeather;
	}

	public static void initLoot()
	{
		//DISC 1 CHEST GENERATION
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(Disc1),0,1,5));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(Disc1),0,1,15));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(Disc1),0,1,15));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(Disc1),0,1,15));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(Disc1),0,1,5));
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(Disc1),0,1,5));
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(Disc1),0,1,15));
	}

	public static void initOthers()
	{	
		ValorFormOrb = new ItemValorFormOrb(); GameRegistry.registerItem(ValorFormOrb, Strings.ValorFormOrb);
	}

}