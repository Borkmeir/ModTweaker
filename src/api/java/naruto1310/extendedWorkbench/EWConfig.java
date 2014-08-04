package naruto1310.extendedWorkbench;

import static naruto1310.extendedWorkbench.EWFMLPlugin.NEI;
import static naruto1310.extendedWorkbench.EWFMLPlugin.biggerTools;
import static naruto1310.extendedWorkbench.EWFMLPlugin.log;
import static naruto1310.extendedWorkbench.EWFMLPlugin.useCoremod;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class EWConfig
{
    private static Map<String, String> values;
    private static boolean changed = false;

    public static void load(File file)
    {
        values = new TreeMap<String, String>();

        try
        {
            Scanner scan = new Scanner(file);

            while (scan.hasNext())
            {
                String s = scan.nextLine();

                if(s.startsWith("#") || s.isEmpty())
                    continue;

                if(s.contains("="))
                {
                	s = s.replace(" ", "");
                    values.put(s.substring(0, s.indexOf("=")), s.substring(s.indexOf("=") + 1, s.length()));
                    continue;
                }
                
                log.info("Extended Workbench config found unexpected line: " + s);
            }
            
            scan.close();
        }
        catch (FileNotFoundException e)
        {
        	log.info("Extended Workbench could not find config. Generating one.");
        }
    }

    public static void save(File file)
    {
    	if(!changed)
    		return;

        try
        {
            BufferedWriter output = new BufferedWriter(new FileWriter(file));

            output.write("# Extended Workbench configuration file");
            output.newLine();
            output.write("#           mod by Naruto1310          ");
            output.newLine();

            for(int i = 0; i < values.size(); i++)
            {
            	String value = values.values().toArray()[i].toString();
            	if(value.contains(":"))
            	{
            		output.newLine();
            		output.write("# " + value.substring(value.indexOf(":") + 1));
            		output.newLine();
            		value = value.substring(0, value.indexOf(":"));
            	}
                output.write(values.keySet().toArray()[i] + " = " + value);
                output.newLine();
            }
            
            output.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String getOrCreateValue(String key, Object defaultValue, String comment)
    {
    	if(!values.containsKey(key))
    	{
    		values.put(key, defaultValue.toString());
    		changed = true;
    	}

        if(comment != null)
        {
        	String value = values.get(key);
        	if(!value.contentEquals(defaultValue.toString()))
        		changed = true;
        	values.put(key, value + ":" + comment);
        	return value;
        }
        
        return values.get(key);
    }

    public static String getOrCreateValue(String key, String defaultValue)
    {
    	return getOrCreateValue(key, defaultValue, null);
    }
    
    public static void init(File f)
    {
		EWConfig.load(f);
		try
		{
			useCoremod = Boolean.valueOf(EWConfig.getOrCreateValue("useCoremod", new Boolean(useCoremod), "Use FML coremod features to extend tool reach.")).booleanValue();

			NEI = Boolean.valueOf(EWConfig.getOrCreateValue("enableNEI", new Boolean(NEI), "(Client only) Enable NEI recipe and usage handler. (no effect if NEI isn't installed)")).booleanValue();
			biggerTools = Integer.valueOf(EWConfig.getOrCreateValue("biggerTools", new Integer(biggerTools), "(Client only) Render tools bigger: 0 off, 1 swords only, 2 all tools.")).intValue();
		}
		catch(ClassCastException e)
		{
			e.printStackTrace();
		}
		EWConfig.save(f);
    }
}
