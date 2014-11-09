package wehavecookies56.kk.core.packet;


import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class SummonPacket implements IPacket {
	
	ItemStack itemToSummon;
	ItemStack itemToRemove;

	public SummonPacket() { }

    public SummonPacket(ItemStack itemToSummon, ItemStack itemToRemove) {
    	this.itemToSummon = itemToSummon;
    	this.itemToRemove = itemToRemove;
    	
    }
	
	@Override
	public void readBytes(ByteBuf bytes) {
		this.itemToSummon = ByteBufUtils.readItemStack(bytes);
		this.itemToRemove = ByteBufUtils.readItemStack(bytes);
		
	}
	
	@Override
	public void writeBytes(ByteBuf bytes) {
		ByteBufUtils.writeItemStack(bytes, this.itemToSummon);
		ByteBufUtils.writeItemStack(bytes, this.itemToRemove);

	}

	@Override
	public void handleClientSide(NetHandlerPlayClient nhClient) {
		
	}

	@Override
	public void handleServerSide(NetHandlerPlayServer nhServer) {
		EntityPlayer player = nhServer.playerEntity;
		if(player.getHeldItem() != null){
			if(player.getHeldItem().getItem() == itemToRemove.getItem()){
				player.inventory.setInventorySlotContents(player.inventory.currentItem, itemToSummon);
			}
		}
	}

}