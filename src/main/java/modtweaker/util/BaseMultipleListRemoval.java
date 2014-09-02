package modtweaker.util;

import java.util.ArrayList;
import java.util.List;

import minetweaker.IUndoableAction;

public class BaseMultipleListRemoval implements IUndoableAction {
    public static enum Position {
        ALL, FIRST, LAST;
    }
    
    protected final Position pos;
    protected final String description;
    protected final List list;
    protected final Object search;
    protected ArrayList<Object> recipes;
    
    public BaseMultipleListRemoval(String description, List list, Object search, Position pos) {
        this.pos = pos;
        this.description = description;
        this.list = list;
        this.search = search;
    }

    @Override
    public void apply() {
        //Create a new list
        recipes = new ArrayList();
        for(Object o: list) {
            if(isEqual(o, search)) {
                //If we want the last position, reset the array
                if(pos == Position.LAST) {
                    recipes = new ArrayList();
                }
                
                recipes.add(o);
                
                //If we want the first position, exit the loop
                if(pos == Position.FIRST) {
                    break;
                }
            }
        }
        
        //Remove all the recipes that were considered valid for removal
        for(Object o: recipes) {
            list.remove(o);
        }
    }

    //Return whether the items are equal or not
    protected boolean isEqual(Object recipe, Object search) {
        return false;
    }

    @Override
    public boolean canUndo() {
        return list != null;
    }

    @Override
    public void undo() {
        for(Object o: recipes) {
            list.add(o);
        }
    }
    
    public String getRecipeInfo() {
        return "Invalid Item";
    }

    @Override
    public String describe() {
        return "Removing " + description + " Recipe for :" + getRecipeInfo();
    }

    @Override
    public String describeUndo() {
        return "Restoring " + description + " Recipe for :" + getRecipeInfo();
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}
