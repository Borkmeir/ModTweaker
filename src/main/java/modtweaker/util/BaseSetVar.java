package modtweaker.util;

import minetweaker.IUndoableAction;

public abstract class BaseSetVar implements IUndoableAction {
	protected final String description;
	protected final int original;
	protected final int newValue;
	public BaseSetVar(String description, int original, int newValue) {
		this.description = description;
		this.original = original;
		this.newValue = newValue;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public String describe() {
		return "Setting " + description + " to " + newValue;
	}

	@Override
	public String describeUndo() {
		return "Setting " + description + " to the default value of " + original;
	}

}
