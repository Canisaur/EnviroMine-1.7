package enviromine.client.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import enviromine.client.gui.UI_Settings;
import enviromine.core.EnviroMine;

public class SaveController {
    protected static final String dirName = Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "enviromine";
    
    protected static File dir = new File(dirName);
    
    public static String UISettingsData = "UI_Settings"; 
    
    
    public static boolean loadConfig(String name) {
        return loadConfig(name, null);
    }

    public static boolean loadConfig(String name, String dirName) {
        if (dirName != null) {

        	dir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + dirName);
        }

        String fileName = name + ".dat";
        File file = new File(dir, fileName);

        if (!file.exists()) 
        {
            EnviroMine.logger.warn("Config load canceled, file ("+ file.getAbsolutePath()  +")does not exist. This is normal for first run.");
            return false;
        } else {
            EnviroMine.logger.info("Config load successful.");
        }
        try {
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(new FileInputStream(file));

            UI_Settings.readFromNBT(nbt.getCompoundTag(UISettingsData));
            
            // New HUD Settings will be here
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void saveConfig(String name) {
        saveConfig(name, null);
    }

    public static void saveConfig(String name, String dirName) {
        EnviroMine.logger.info("Saving...");

        if (dirName != null) {
            dir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + dirName);
        }

        if (!dir.exists() && !dir.mkdirs())
            throw new ReportedException(new CrashReport("Unable to create the configuration directories", new Throwable()));

        String fileName = name + ".dat";
        File file = new File(dir, fileName);

        try {
            NBTTagCompound nbt = new NBTTagCompound();
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            NBTTagCompound globalNBT = new NBTTagCompound();
            	UI_Settings.writeToNBT(globalNBT);
            	nbt.setTag(UISettingsData, globalNBT);

           // New HUD Settings will be here
            
            
            CompressedStreamTools.writeCompressed(nbt, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new ReportedException(new CrashReport("An error occured while saving", new Throwable()));
        }
    }

    public static File[] getConfigs() {
        return dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".dat");
            }
        });
    }

}