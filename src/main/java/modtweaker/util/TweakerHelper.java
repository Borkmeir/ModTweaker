package modtweaker.util;

import net.minecraft.item.ItemStack;
import stanhebben.minetweaker.api.TweakerExecuteException;
import stanhebben.minetweaker.api.value.TweakerArray;
import stanhebben.minetweaker.api.value.TweakerItemStack;
import stanhebben.minetweaker.api.value.TweakerLiquidStack;
import stanhebben.minetweaker.api.value.TweakerValue;

public class TweakerHelper {
	
	
	public static TweakerItemStack GetItemOld(int arg, TweakerValue... values) {
		return notNull(values[arg], arg).toItemStack( "argument " + arg + " must be an item");
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
	
	public static TweakerItemStack getItemNull(int arg, TweakerValue...values) {
		return values[arg].asItemStack();
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
	
	public static int getHex(int arg, TweakerValue... values) {
		return Integer.parseInt(getString(arg, values), 16);
	}
	
	public static void throwException(String label, int args) {
		throw new TweakerExecuteException(label + " requires " + args + " arguments");
	}
	
	
	/** NEW TWEAKER HELPER, ^^^ OLD IS ABOVE **/
	public static TweakerValue[] arguments;
	public static int index;
	public static boolean canContinue(int size, TweakerValue... args) {
		if(args.length == size) {
			arguments = args;
			index = 0;
			return true;
		} else return false;
	}
	
	public static boolean canContinue(int[] size, TweakerValue... args) {
		for(int i: size) {
			if(canContinue(i, args)) {
				return true;
			}
		}
		
		return false;
	}
	
	/* Returns an integer */
	public static int getInt() {
		int i = notNull(arguments[index], index).toInt("argument " + index + " must be an integer").get();
		index++;
		return i;
	}
	
	/* Returns an itemstack, if it's not null then it will increase the index */
	public static ItemStack getItem() {
		ItemStack stack = notNull(arguments[index], index).toItemStack( "argument " + index + " must be an item").get();
		if(stack != null) {
			index++;
		}
		
		return stack;
	}
	
	/* return a list of itemstacks **/
	public static ItemStack[] getItems() {
		TweakerArray array = notNull(arguments[index], index).toArray("argument " + index + " must be an array of items");
		if(array != null) {
			ItemStack[] input = new ItemStack[array.size()];
			for (int i = 0; i < array.size(); i++) {
				input[i] = array.get(0).asItemStack().get();
			}
			
			index++;
			
			return input;
		} else return null;
	}
	
	/* Throws an exception if the item is null */
	public static TweakerValue notNull(TweakerValue value, int arg) {
		return TweakerValue.notNull(value, "argument " + arg + " must not be null");
	}
	
	/* Throws an exception complaining about incorrect argument length **/
	public static void throwException(TweakerBaseFunction label, int args) {
		throw new TweakerExecuteException(label.toString() + " requires " + args + " arguments");
	}
	
	public static void throwException(TweakerBaseFunction label, int[] args) {
		String nums = "";
		for(int i: args) {
			nums = nums + i + " ";
			if(i < args.length) {
				nums = nums + "or ";
			}
		}
		
		throw new TweakerExecuteException(label.toString() + " requires " + args.toString() + "arguments");
	}
}
