package ATX.core.proxies;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import ATX.client.KeyBind;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;


public class ClientProxy extends CommonProxy {


	public static int sphereIdOutside;
	public static int sphereIdInside;
	
	@Override
	public void registerRenderers() {
		//3D Model renders
//		MinecraftForgeClient.registerItemRenderer(AddedItems.MetalChocobo, new ItemRenderMetalChocobo());
	//	MinecraftForgeClient.registerItemRenderer(AddedItems.WoodenKeyblade, new ItemRenderWoodenKeyblade());
		
		//Events
//		MinecraftForge.EVENT_BUS.register(new GuiCommands());

		//Entities
	/*	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKKChest.class, new TileEntityRendererKKChest());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlastBlox.class, new BlockRenderBlastBlox());
		RenderingRegistry.registerEntityRenderingHandler(EntityEternalFlamesProjectile.class, new EntityRenderEternalFlamesProjectile(AddedItems.EternalFlames));
		RenderingRegistry.registerEntityRenderingHandler(EntitySharpshooterBullet.class, new EntityRenderSharpShooterBullet());
		ResourceLocation villagerTexture = new ResourceLocation("kk", "textures/entities/mobs/knowledgeVillager.png");
		VillagerRegistry.instance().registerVillagerSkin(20, villagerTexture);
		VillagerRegistry.getVillagerSkin(10, villagerTexture);
	
*/
		Sphere sphere = new Sphere();

		sphere.setDrawStyle(GLU.GLU_FILL);
		sphere.setNormals(GLU.GLU_SMOOTH);

		sphere.setOrientation(GLU.GLU_OUTSIDE);

		sphereIdOutside = GL11.glGenLists(1);
		GL11.glNewList(sphereIdOutside, GL11.GL_COMPILE);
		ResourceLocation rL = new ResourceLocation("kk", "textures/entities/sphere.png");
		Minecraft.getMinecraft().getTextureManager().bindTexture(rL);
		sphere.draw(1.5F, 32, 32);
		GL11.glEndList();
		sphere.setOrientation(GLU.GLU_INSIDE);
		sphereIdInside = GL11.glGenLists(1);
		GL11.glNewList(sphereIdInside, GL11.GL_COMPILE);
		Minecraft.getMinecraft().getTextureManager().bindTexture(rL);
		sphere.draw(1.5F, 32, 32);
		GL11.glEndList();
	}

	@Override
	public int addArmor(String armor) {
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}

/*	@Override
	public void initCapes() {
		DevCapes.getInstance().registerConfig("https://www.dropbox.com/s/hb0wg5ky5wblz9g/Capes.json?raw=1");
	}*/


	public static KeyBind KeyBind = new KeyBind();

	@Override
	public void registerKeybinds() {
		FMLCommonHandler.instance().bus().register(this.KeyBind);
	}

}