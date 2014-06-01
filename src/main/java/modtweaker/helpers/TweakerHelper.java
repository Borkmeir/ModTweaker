package modtweaker.helpers;

import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.value.TweakerArray;
import stanhebben.minetweaker.api.value.TweakerItem;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class TweakerHelper {
	public static TweakerValue notNull(TweakerValue value, int arg) {
		return TweakerValue.notNull(value, "argument " + arg + " must not be null");
	}
	
	public static TweakerItem getItem(int arg, TweakerValue... values) {
		return notNull(values[arg], arg).toItem( "argument " + arg + " must be an item");
	}
	
	public static TweakerLiquidStack getFluid(int arg, TweakerValue... values) {
		return notNull(values[arg], arg).toFluidStack("argument " + arg + " must be a valid fluid");
	}
	
	public static boolean getBoolean(int arg, TweakerValue... values) {
		return notNull(values[arg], arg).toBool("argument " + arg + " must be true or false").get();
	}
	
	public static int getInt(int arg, TweakerValue... values) {
		return notNull(values[arg], arg).toInt("argument " + arg + " must be an integer").get();
	}
	
	public static float getFloat(int arg, TweakerValue... values) {
		return notNull(values[arg], arg).toFloat("argument " + arg + " must be a float").get();
	}
	
	public static TweakerArray getArray(int arg, TweakerValue... values) {
		return notNull(values[arg], arg).toArray("argument " + arg + " must be an array of items");
	}
	
	public static TweakerItem getItemNull(int arg, TweakerValue...values) {
		return values[arg].asItem();
	}
	
	public static TweakerLiquidStack getFluidNull(int arg, TweakerValue...values) {
		return values[arg].asFluidStack();
	}
	
	public static int getDimension(int arg, TweakerValue... values) {
		String dim = getString(arg, values);
		if(dim.equalsIgnoreCase("any")) return Short.MAX_VALUE;
		if(dim.equalsIgnoreCase("nether")) return -1;
		if(dim.equalsIgnoreCase("end")) return 1;
		if(dim.equalsIgnoreCase("overworld")) return 0;
		return Short.MAX_VALUE;
	}
	
	public static String getString(int arg, TweakerValue... values) {
		return notNull(values[arg], arg).toBasicString();
	}
	
	public static void throwException(String label, int args) {
		throw new TweakerExecuteException(label + " requires " + args + " arguments");
	}
}
