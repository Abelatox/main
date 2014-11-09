package ATX;

import io.netty.channel.ChannelHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Logger;

import ATX.block.AddedBlocks;
import ATX.core.event.DarkHeartDrops;
import ATX.core.event.DriveOrbDrops;
import ATX.core.event.EntityConstructEvent;
import ATX.core.event.EntityDamagedEvent;
import ATX.core.event.HPOrbDrops;
import ATX.core.event.HeartDrops;
import ATX.core.event.HurtEvent;
import ATX.core.event.KingdomHeartsDrops;
import ATX.core.event.LivingUpdateEevent;
import ATX.core.event.Munny10Drops;
import ATX.core.event.Munny1Drops;
import ATX.core.event.Munny20Drops;
import ATX.core.event.Munny3000Drops;
import ATX.core.event.Munny50Drops;
import ATX.core.event.Munny5Drops;
import ATX.core.event.OnCraftedEvent;
import ATX.core.event.OnHitEvent;
import ATX.core.event.OnPickUpEvent;
import ATX.core.event.PureHeartDrops;
import ATX.core.event.RecipeDrop;
import ATX.core.handlers.GiveMunny;
import ATX.core.handlers.GuiHandler;
import ATX.core.proxies.ClientProxy;
import ATX.core.proxies.CommonProxy;
import ATX.creativetab.ATXTAB;
import ATX.item.AddedItems;
import ATX.lib.ConfigBooleans;
import ATX.lib.Recipes;
import ATX.lib.Reference;
import ATX.lib.SynthesisRecipes;
import ATX.lib.ints;
import ATX.worldgen.WorldGen;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.MOD_VER, guiFactory = "wehavecookies56.kk.client.gui.KingdomKeysModGuiFactory") 
/*
 * TODO Clean up this file
 * TODO Add more config options
 * TODO Move some stuff to other files to reduce size
 */
public class KingdomKeys {
	public static Logger logger = FMLLog.getLogger();

	//World gen
	WorldGen worldGen = new WorldGen();

	public static Configuration config;

	@Mod.Instance(Reference.MOD_ID)
	public static KingdomKeys instance;

	//Proxies
	@SidedProxy(clientSide="wehavecookies56.kk.core.proxies.ClientProxy", serverSide="wehavecookies56.kk.core.proxies.CommonProxy")
	public static CommonProxy proxy;
	public static ClientProxy cproxy;

	//Creative tabs
	
	public static CreativeTabs ATXTAB = new ATXTAB(CreativeTabs.getNextID(), "ATXTAB");

	public static ChannelHandler network;

	//public static final Enchantment HarvestHearts = new EnchantHeartHarvest(ints.EnchantmentID, 1);

	//Mob
	//public static int getUniqueEntityID

	//private GuiHandlerSynth guiHandlerSynth = new GuiHandlerSynth();

	//Pre initialisation
//	@Mod.EventHandler
	/*public void modConstruct(FMLConstructionEvent event)
	{
		network = new ChannelHandler(Reference.MOD_ID, Reference.MOD_CHANNEL);
	}
*/
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
//		ModelPlayerAPI.register("kk", PlayerModel.class);
	//	RenderPlayerAPI.register("kk", PlayerRender.class);
		AddedItems.initKeyBlades();
		AddedItems.initHearts();
		AddedItems.initOthers();
		AddedItems.initRecipes();
		AddedItems.intiArmour();
		AddedItems.initLoot();
		AddedBlocks.preinit();

	/*	network.registerPacket(KnowledgePacket.class);
		network.registerPacket(SummonPacket.class);
		network.registerPacket(SynthesisPacket.class);
		network.registerPacket(MunnyPacket.class);
		network.registerPacket(AchievementPacket.class);
*/
		int modEntityID = 0;
		
		config = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
	}


	//TODO Put this in a different file to make it look better
	public static void syncConfig(){
		FMLCommonHandler.instance().bus().register(instance);

		//TODO Make this work
		ints.EnchantmentID = config.get(config.CATEGORY_GENERAL, "Enchantment ID", ints.ENCHANTMENTID_DEFAULT).getInt(ints.ENCHANTMENTID_DEFAULT);
		ConfigBooleans.enableUpdateCheck = config.get(config.CATEGORY_GENERAL, ConfigBooleans.enableUpdateCheck_name, ConfigBooleans.enableUpdateCheck_default).getBoolean(ConfigBooleans.enableUpdateCheck_default);

		final String GENERATE = config.CATEGORY_GENERAL + config.CATEGORY_SPLITTER + "World Generation";
		ConfigBooleans.enableGenerate = config.get(GENERATE, ConfigBooleans.enableGenerate_name, ConfigBooleans.enableGenerate_default).getBoolean(ConfigBooleans.enableGenerate_default);
		ConfigBooleans.enableOverworld = config.get(GENERATE, ConfigBooleans.enableOverworld_name, ConfigBooleans.enableOverworld_default).getBoolean(ConfigBooleans.enableOverworld_default);
		//ConfigBooleans.discSpawn = config.get(GENERATE, "Enable the disc spawning in chests", ConfigBooleans.discSpawn_default).getBoolean(ConfigBooleans.discSpawn_default);

		final String OVERWORLD = GENERATE + config.CATEGORY_SPLITTER + "Overworld Spawn Chances";
		config.addCustomCategoryComment(OVERWORLD, "Higher number = Higher spawn chance");
		ints.LucidOreChance = config.get(OVERWORLD, "Lucid Ore Spawn Chance", ints.LUCIDORECHANCE_DEFAULT).getInt(ints.LUCIDORECHANCE_DEFAULT);
		ints.SerenityOreChance = config.get(OVERWORLD, "Serenity Ore Spawn Chance", ints.SERENITYORECHANCE_DEFAULT).getInt(ints.SERENITYORECHANCE_DEFAULT);
		ints.TranquilOreChance = config.get(OVERWORLD, "Tranquil Ore Spawn Chance", ints.TRANQUILORECHANCE_DEFAULT).getInt(ints.TRANQUILORECHANCE_DEFAULT);
		ints.BrightOreChance = config.get(OVERWORLD, "Bright Ore Spawn Chance", ints.BRIGHTORECHANCE_DEFAULT).getInt(ints.BRIGHTORECHANCE_DEFAULT);
		ints.DarkOreChance = config.get(OVERWORLD, "Dark Ore Spawn Chance", ints.DARKORECHANCE_DEFAULT).getInt(ints.DARKORECHANCE_DEFAULT);
		ints.DenseOreChance = config.get(OVERWORLD, "Dense Ore Spawn Chance", ints.DENSEORECHANCE_DEFAULT).getInt(ints.DENSEORECHANCE_DEFAULT);
		ints.LightningOreChance = config.get(OVERWORLD, "Lightning Ore Spawn Chance", ints.LIGHTNINGORECHANCE_DEFAULT).getInt(ints.LIGHTNINGORECHANCE_DEFAULT);
		ints.BlazingOreChance = config.get(OVERWORLD, "Blazing Ore Spawn Chance", ints.BLAZINGORECHANCE_DEFAULT).getInt(ints.BLAZINGORECHANCE_DEFAULT);
		ints.PrizeBloxChance = config.get(OVERWORLD, "Prize Blox Spawn Chance", ints.PRIZEBLOXCHANCE_DEFAULT).getInt(ints.PRIZEBLOXCHANCE_DEFAULT);
		ints.RarePrizeBloxChance = config.get(OVERWORLD, "Rare Prize Blox Spawn Chance", ints.RAREPRIZEBLOXCHANCE_DEFAULT).getInt(ints.RAREPRIZEBLOXCHANCE_DEFAULT);
		ints.FrostOreChance = config.get(OVERWORLD, "Frost Ore Spawn Chance", ints.FROSTORECHANCE_DEFAULT).getInt(ints.FROSTORECHANCE_DEFAULT);
		ints.EnergyOreChance = config.get(OVERWORLD, "Energy Ore Spawn Chance", ints.ENERGYORECHANCE_DEFAULT).getInt(ints.ENERGYORECHANCE_DEFAULT);
		ints.RemembranceOreChance = config.get(OVERWORLD, "Remembrance Ore Spawn Chance", ints.REMEMBRANCEORECHANCE_DEFAULT).getInt(ints.REMEMBRANCEORECHANCE_DEFAULT);
		ints.TwilightOreChance = config.get(OVERWORLD, "Twilight Ore Spawn Chance", ints.TWILIGHTORECHANCE_DEFAULT).getInt(ints.TWILIGHTORECHANCE_DEFAULT);
		ints.NormalBlox = config.get(OVERWORLD, "Normal Blox Spawn Chance", ints.NORMALBLOX_DEFAULT).getInt(ints.NORMALBLOX_DEFAULT);
		ints.HardBlox = config.get(OVERWORLD, "Hard Blox Spawn Chance", ints.HARDBLOX_DEFAULT).getInt(ints.HARDBLOX_DEFAULT);
		ints.MetalBlox = config.get(OVERWORLD, "Metal Blox Spawn Chance", ints.METALBLOX_DEFAULT).getInt(ints.METALBLOX_DEFAULT);
		ints.DangerBlox = config.get(OVERWORLD, "Danger Blox Spawn Chance", ints.DANGERBLOX_DEFAULT).getInt(ints.DANGERBLOX_DEFAULT);
		ints.RarePrizeBlox = config.get(OVERWORLD, "Rare Prize Blox Spawn Chance", ints.RAREPRIZEBLOX_DEFAULT).getInt(ints.RAREPRIZEBLOX_DEFAULT);
		ints.PrizeBlox = config.get(OVERWORLD, "Prize Blox Spawn Chance", ints.PRIZEBLOX_DEFAULT).getInt(ints.PRIZEBLOX_DEFAULT);

		final String END = GENERATE + config.CATEGORY_SPLITTER + "End Spawn Chances";
		config.addCustomCategoryComment(END, "Higher number = Higher spawn chance");
		ints.PowerOreEChance = config.get(END, "Power Ore Spawn Chance", ints.POWEROREECHANCE_DEFAULT).getInt(ints.POWEROREECHANCE_DEFAULT);
		ints.DarkOreEChance = config.get(END, "Dark Ore Spawn Chance", ints.DARKOREECHANCE_DEFAULT).getInt(ints.DARKOREECHANCE_DEFAULT);
		ints.NormalBloxE = config.get(END, "Normal Blox Spawn Chance", ints.NORMALBLOXE_DEFAULT).getInt(ints.NORMALBLOXE_DEFAULT);
		ints.HardBloxE = config.get(END, "Hard Blox Spawn Chance", ints.HARDBLOXE_DEFAULT).getInt(ints.HARDBLOXE_DEFAULT);
		ints.MetalBloxE = config.get(END, "Metal Blox Spawn Chance", ints.METALBLOXE_DEFAULT).getInt(ints.METALBLOXE_DEFAULT);
		ints.DangerBloxE = config.get(END, "Danger Blox Spawn Chance", ints.DANGERBLOXE_DEFAULT).getInt(ints.DANGERBLOXE_DEFAULT);
		ints.RarePrizeBloxE = config.get(END, "Rare Prize Blox Spawn Chance", ints.RAREPRIZEBLOXE_DEFAULT).getInt(ints.RAREPRIZEBLOXE_DEFAULT);

		//TODO Make these work
		final String KEYBLADE = config.CATEGORY_GENERAL + config.CATEGORY_SPLITTER + "Keyblade Config";
		ConfigBooleans.enable3D = config.get(KEYBLADE, ConfigBooleans.enable3D_name, ConfigBooleans.enable3D_default).getBoolean(ConfigBooleans.enable3D_default);
		ConfigBooleans.altWaywardWind = config.get(KEYBLADE, ConfigBooleans.altWaywardWind_name, ConfigBooleans.altWaywardWind_default).getBoolean(ConfigBooleans.altWaywardWind_default);
		ConfigBooleans.enableShine = config.get(KEYBLADE, ConfigBooleans.enableShine_name, ConfigBooleans.enableShine_default).getBoolean(ConfigBooleans.enableShine_default);

		//TODO Fix these too
		final String RECIPE = config.CATEGORY_GENERAL + config.CATEGORY_SPLITTER + "Recipe Config";
		ConfigBooleans.heartRecipe = config.get(RECIPE, ConfigBooleans.heartRecipe_name, ConfigBooleans.heartRecipe_default).getBoolean(ConfigBooleans.heartRecipe_default);
		ConfigBooleans.bloxRecipe = config.get(RECIPE, ConfigBooleans.bloxRecipe_name, ConfigBooleans.bloxRecipe_default).getBoolean(ConfigBooleans.bloxRecipe_default);
		ConfigBooleans.munnyDrops = config.get(RECIPE, ConfigBooleans.munnyDrops_name, ConfigBooleans.munnyDrops_default).getBoolean(ConfigBooleans.munnyDrops_default);       
		ConfigBooleans.expensiveDarkMatter = config.get(RECIPE, ConfigBooleans.expensiveDarkMatter_name, ConfigBooleans.expensiveDarkMatter_default).getBoolean(ConfigBooleans.expensiveDarkMatter_default);
		if(config.hasChanged()){
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.modID.equals("kk")){
			syncConfig();
		}
	}

	//Initialisation
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
	//	network.initialise();
		SynthesisRecipes.initSynthesisRecipes();
		//logger.info(Reference.MOD_NAME + ": Registered " + RecipeHandler.getTotalRegistered() + " Synthesis recipes");
		//RecipeHandler.iterateRecipes();
		//AddedAchievments.initAchievements();
		logger.info(Reference.MOD_NAME + ": Registered Added Achievements");
		logger.info(Reference.MOD_NAME + ": Registering Events");
		MinecraftForge.EVENT_BUS.register(new HeartDrops());
		MinecraftForge.EVENT_BUS.register(new PureHeartDrops());
		MinecraftForge.EVENT_BUS.register(new DarkHeartDrops());
		MinecraftForge.EVENT_BUS.register(new KingdomHeartsDrops());
		MinecraftForge.EVENT_BUS.register(new Munny1Drops());
		MinecraftForge.EVENT_BUS.register(new Munny5Drops());
		MinecraftForge.EVENT_BUS.register(new Munny10Drops());
		MinecraftForge.EVENT_BUS.register(new Munny20Drops());
		MinecraftForge.EVENT_BUS.register(new Munny50Drops());
		MinecraftForge.EVENT_BUS.register(new Munny3000Drops());
		MinecraftForge.EVENT_BUS.register(new HPOrbDrops());
		MinecraftForge.EVENT_BUS.register(new RecipeDrop());
		MinecraftForge.EVENT_BUS.register(new EntityConstructEvent());
		MinecraftForge.EVENT_BUS.register(new HurtEvent());
		MinecraftForge.EVENT_BUS.register(new DriveOrbDrops());
		MinecraftForge.EVENT_BUS.register(new OnHitEvent());
		MinecraftForge.EVENT_BUS.register(new EntityDamagedEvent());
		MinecraftForge.EVENT_BUS.register(new LivingUpdateEevent());
		FMLCommonHandler.instance().bus().register(instance);
		FMLCommonHandler.instance().bus().register(new OnCraftedEvent());
		FMLCommonHandler.instance().bus().register(new OnPickUpEvent());
	//	FMLCommonHandler.instance().bus().register(new Update());
		logger.info(Reference.MOD_NAME + ": Registered Events");
		new GuiHandler();
		Recipes.initShapedRecipes();
		Recipes.initShapelessRecipes();
//		GameRegistry.registerWorldGenerator(worldGen, 1);
		proxy.registerRenderers();
		proxy.initCapes();
		proxy.registerKeybinds();
		if(ConfigBooleans.enableUpdateCheck)
		{
			final Side side = FMLCommonHandler.instance().getEffectiveSide();
		}
	}

	@Mod.EventHandler
	private void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new GiveMunny());

	}

	EntityPlayer entityplayer;

	//Post Initialisation
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//network.postInitialise();
	}

	public static void addVillagePiece(Class c, String s){
		try
		{
			MapGenStructureIO.func_143031_a(c, s);
		}
		catch(Exception e)
		{

		}
	}
}
