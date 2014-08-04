package naruto1310.extendedWorkbench;

import java.io.File;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class EWFMLPlugin implements IFMLLoadingPlugin
{
	public static boolean FMLPLuginLoaded = false;
	public static Logger log;

	public static boolean useCoremod = true;
	public static boolean NEI = true;
	public static int biggerTools = 2;
	
	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] {"naruto1310.extendedWorkbench.EWTransformer"};
	}

	@Override
	public String getModContainerClass()
	{
		return null;
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		log = LogManager.getLogger("ExtendedWorkbench");
		EWConfig.init(new File((File)data.get("mcLocation"), "config/ExtendedWorkbench.cfg"));
		FMLPLuginLoaded = true;
	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}
