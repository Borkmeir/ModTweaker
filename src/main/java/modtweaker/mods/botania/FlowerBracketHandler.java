package modtweaker.mods.botania;

import java.util.List;

import minetweaker.IBracketHandler;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.BracketHandler;
import minetweaker.api.item.IIngredient;
import minetweaker.mc172.item.MCItemStack;
import stanhebben.zenscript.expression.ExpressionJavaCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

@BracketHandler
public class FlowerBracketHandler implements IBracketHandler {
    public static IBotaniaSubTile getFlower(String name) {
        return new BotaniaSubTile(name);
    }

    @Override
    public IZenSymbol resolve(List<Token> tokens) {
        if (tokens.size() > 2) {
            if (tokens.get(0).getValue().equals("flower") && tokens.get(1).getValue().equals(":")) {
                return find(tokens, 2, tokens.size());
            }
        }

        return null;
    }

    private IZenSymbol find(List<Token> tokens, int startIndex, int endIndex) {
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            Token token = tokens.get(i);
            valueBuilder.append(token.getValue());
        }

        return new FlowerReferenceSymbol(valueBuilder.toString());
    }

    private class FlowerReferenceSymbol implements IZenSymbol {
        private final String name;

        public FlowerReferenceSymbol(String name) {
            this.name = name;
        }

        @Override
        public IPartialExpression instance(ZenPosition position) {
            JavaMethod method = MineTweakerAPI.getJavaMethod(FlowerBracketHandler.class, "getFlower", String.class);
            return new ExpressionJavaCallStatic(position, method, new ExpressionString(position, name));
        }
    }

    public static interface IBotaniaSubTile extends IIngredient {}

    public static class BotaniaSubTile extends MCItemStack implements IBotaniaSubTile {
        public BotaniaSubTile(String subtile) {
            super(ItemBlockSpecialFlower.ofType(subtile));
        }
    }
}
