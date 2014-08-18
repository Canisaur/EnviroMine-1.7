package enviromine.client.gui.menu;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import enviromine.core.EnviroMine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.logging.log4j.Level;

@SideOnly(Side.CLIENT)
public class UI_Settings {

	public static String enviroSettingsFile = "em_options.txt";

	public static boolean ShowGuiIcons;
	public static float guiScale;
	public static boolean sweatParticals;
	public static boolean insaneParticals;
	public static boolean useFarenheit;
	public static String heatBarPos;
	public static String waterBarPos;
	public static String sanityBarPos;
	public static String oxygenBarPos;
	public static boolean ShowText;
	public static boolean ShowDebug;
	public static boolean breathSound;
	public static int breathPause;
	public static float breathVolume;
	public static boolean minimalHud;

	@SideOnly(Side.CLIENT)
	public static void loadSettings() {

		File f = new File(enviroSettingsFile);
		if (!f.exists()) {
			// Check Propeties will Write new file
			checkProperties(new Properties());
		}

		readSettings();
	}

	@SideOnly(Side.CLIENT)
	public static void saveSettings() {
		Properties prop = new Properties();

		prop.setProperty("ShowGuiIcons", "" + ShowGuiIcons);
		prop.setProperty("guiScale", "" + guiScale);
		prop.setProperty("sweatParticals", "" + sweatParticals);
		prop.setProperty("insaneParticals", "" + insaneParticals);
		prop.setProperty("useFarenheit", "" + useFarenheit);
		prop.setProperty("heatBarPos", "" + heatBarPos);
		prop.setProperty("waterBarPos", "" + waterBarPos);
		prop.setProperty("sanityBarPos", "" + sanityBarPos);
		prop.setProperty("oxygenBarPos", "" + oxygenBarPos);
		prop.setProperty("ShowText", "" + ShowText);
		prop.setProperty("ShowDebug", "" + ShowDebug);
		prop.setProperty("breathSound", "" + breathSound);
		prop.setProperty("breathPause", "" + breathPause);
		prop.setProperty("breathVolume", "" + breathVolume);
		prop.setProperty("minimalHud", "" + minimalHud);

		WriteSettings(prop);
	}

	@SideOnly(Side.CLIENT)
	public static Properties checkProperties(Properties prop) {

		if (!prop.containsKey("ShowGuiIcons"))
			prop.setProperty("ShowGuiIcons", "true");
		if (!prop.containsKey("guiScale"))
			prop.setProperty("guiScale", "1.0");
		if (!prop.containsKey("sweatParticals"))
			prop.setProperty("sweatParticals", "true");
		if (!prop.containsKey("insaneParticals"))
			prop.setProperty("insaneParticals", "true");
		if (!prop.containsKey("useFarenheit"))
			prop.setProperty("useFarenheit", "true");
		if (!prop.containsKey("heatBarPos"))
			prop.setProperty("heatBarPos", "custom_0,97");
		if (!prop.containsKey("waterBarPos"))
			prop.setProperty("waterBarPos", "custom_0,93");
		if (!prop.containsKey("sanityBarPos"))
			prop.setProperty("sanityBarPos", "custom_82,93");
		if (!prop.containsKey("oxygenBarPos"))
			prop.setProperty("oxygenBarPos", "custom_82,97");
		if (!prop.containsKey("ShowText"))
			prop.setProperty("ShowText", "true");
		if (!prop.containsKey("ShowDebug"))
			prop.setProperty("ShowDebug", "false");
		if (!prop.containsKey("breathSound"))
			prop.setProperty("breathSound", "true");
		if (!prop.containsKey("breathPause"))
			prop.setProperty("breathPause", "300");
		if (!prop.containsKey("breathVolume"))
			prop.setProperty("breathVolume", "0.75");
		if (!prop.containsKey("minimalHud"))
			prop.setProperty("minimalHud", "false");

		//TODO TEMP FIX FOR TESTING
		if (prop.containsKey("heatBarPos"))
		{
			prop.setProperty("heatBarPos", "custom_0,97");
		}
			
		if (prop.containsKey("waterBarPos"))
		{
			prop.setProperty("waterBarPos", "custom_0,93");
		}
		if (prop.containsKey("sanityBarPos"))
		{
			prop.setProperty("sanityBarPos", "custom_82,93");
		}
		if (prop.containsKey("oxygenBarPos"))
		{
			prop.setProperty("oxygenBarPos", "custom_82,97");
		}

		
		WriteSettings(prop);
		return prop;
	}

	@SideOnly(Side.CLIENT)
	public static void WriteSettings(Properties prop) {

		OutputStream output = null;

		try {

			try {
				output = new FileOutputStream(enviroSettingsFile);

				EnviroMine.logger.log(Level.INFO, "Saving Options File: "
						+ enviroSettingsFile);

			} catch (NullPointerException e) {
				e.printStackTrace();
				EnviroMine.logger.log(Level.WARN,
						"FAILED TO LOAD Options File: " + enviroSettingsFile
								+ "\nNEW SETTINGS WILL BE IGNORED!");
				return;
			} catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
				EnviroMine.logger.log(Level.WARN,
						"FAILED TO LOAD Options File: " + enviroSettingsFile
								+ "\nNEW SETTINGS WILL BE IGNORED!");
				return;
			}

			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	@SideOnly(Side.CLIENT)
	public static void readSettings() {

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(enviroSettingsFile);

			// load a properties file
			prop.load(input);

			// Check all Proptires before reading
			prop = checkProperties(prop);

			ShowGuiIcons = getBoolean(prop.getProperty("ShowGuiIcons"));
			sweatParticals = getBoolean(prop.getProperty("sweatParticals"));
			insaneParticals = getBoolean(prop.getProperty("insaneParticals"));
			useFarenheit = getBoolean(prop.getProperty("useFarenheit"));
			ShowText = getBoolean(prop.getProperty("ShowText"));
			ShowDebug = getBoolean(prop.getProperty("ShowDebug"));
			guiScale = getFloat(prop.getProperty("guiScale"));
			breathSound = getBoolean(prop.getProperty("breathSound"));
			breathPause = getInterger(prop.getProperty("breathPause"));
			breathVolume = getFloat(prop.getProperty("breathVolume"));
			minimalHud = getBoolean(prop.getProperty("minimalHud"));
			heatBarPos = prop.getProperty("heatBarPos");
			waterBarPos = prop.getProperty("waterBarPos");
			sanityBarPos = prop.getProperty("sanityBarPos");
			oxygenBarPos = prop.getProperty("oxygenBarPos");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static boolean getBoolean(String n) {
		boolean m = Boolean.valueOf(n.toLowerCase().trim());
		;
		return m;
	}

	private static int getInterger(String n) {
		int m = Integer.valueOf(n.trim());
		return m;
	}

	private static float getFloat(String n) {
		float m = Float.valueOf(n.trim());
		return m;
	}

	
}
