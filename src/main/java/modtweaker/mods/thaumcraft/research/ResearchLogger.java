package modtweaker.mods.thaumcraft.research;

import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;
import thaumcraft.api.research.ResearchCategories;

public class ResearchLogger implements ICommandFunction {

    @Override
    public void execute(String[] arguments, IPlayer player) {
        if(arguments == null || arguments.length <= 0 || arguments[0] == null){
            System.out.println("Research Categories:");
            MineTweakerAPI.logCommand("Research Categories:");
            for(String tab : ResearchCategories.researchCategories.keySet()){
                System.out.println(tab);
                MineTweakerAPI.logCommand(tab);
            }
            if (player != null) {
                player.sendChat(MineTweakerImplementationAPI.platform.getMessage("List generated; see minetweaker.log in your minecraft dir"));
            }
        }
        else if(arguments[0] != null && ResearchCategories.researchCategories.containsKey(arguments[0])){
            System.out.println("Research Keys for " + arguments[0] + ":");
            MineTweakerAPI.logCommand("Research Keys for " + arguments[0] + ":");
            for(String key : ResearchCategories.researchCategories.get(arguments[0]).research.keySet()){
                System.out.println(key);
                MineTweakerAPI.logCommand(key);
            }
            if (player != null) {
                player.sendChat(MineTweakerImplementationAPI.platform.getMessage("List generated; see minetweaker.log in your minecraft dir"));
            }
        }
        else if (arguments [0] != null && player != null) {
            player.sendChat(MineTweakerImplementationAPI.platform.getMessage("Cannot find research category " + arguments[0]));
        }
    }
}
