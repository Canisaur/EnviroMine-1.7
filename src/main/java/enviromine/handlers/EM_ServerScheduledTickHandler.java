package enviromine.handlers;


import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import enviromine.client.gui.SaveController;
import enviromine.client.hud.HUDRegistry;
import enviromine.core.EM_Settings;
import enviromine.gases.GasBuffer;
import enviromine.world.Earthquake;

public class EM_ServerScheduledTickHandler
{
	@SubscribeEvent
	public void tickEnd(TickEvent.WorldTickEvent tick)
	{
		
		if(tick.side.isServer())
		{
			GasBuffer.update();
			
			if(EM_Settings.enablePhysics)
			{
				EM_PhysManager.updateSchedule();
			}
			
			TorchReplaceHandler.UpdatePass();
			
			Earthquake.updateEarthquakes();
			
			if(EM_Settings.enableQuakes && tick.world.getTotalWorldTime()%24000 < 100 && MathHelper.floor_double(tick.world.getTotalWorldTime()/24000L) != Earthquake.lastTickDay && tick.world.provider.dimensionId == 0)
			{
				Earthquake.lastTickDay = MathHelper.floor_double(tick.world.getTotalWorldTime()/24000L);
				Earthquake.TickDay(tick.world);
			}
		}
	
	}
	
    private boolean ticked = false;
    private boolean firstload = true;

    @SubscribeEvent
	@SideOnly(Side.CLIENT)
    public void RenderTickEvent(RenderTickEvent event) 
    {
        if ((event.type == Type.RENDER || event.type == Type.CLIENT) && event.phase == Phase.END) 
        {
            Minecraft mc = Minecraft.getMinecraft();
            if (firstload && mc != null) 
            {
            	System.out.println("First Load...");
                if (!SaveController.loadConfig(SaveController.UISettingsData))
                {
                	System.out.println("No Config Create one...");
                    HUDRegistry.checkForResize();
                    HUDRegistry.resetAllDefaults();
                    SaveController.saveConfig(SaveController.UISettingsData);
                }
                firstload = false;
            }
        }
    }
}
