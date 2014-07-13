package modtweaker.mods.mekanism.gas;

import java.util.List;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import minetweaker.IBracketHandler;
import minetweaker.annotations.BracketHandler;
import minetweaker.runtime.GlobalRegistry;
import stanhebben.zenscript.expression.ExpressionJavaCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

@BracketHandler
public class GasBracketHandler implements IBracketHandler {
    public static IGasStack getGas(String name) {
        Gas gas = GasRegistry.getGas(name);
        if (gas != null) {
            return new MCGasStack(new GasStack(gas, 1));
        } else {
            return null;
        }
    }

    @Override
    public IZenSymbol resolve(List<Token> tokens) {
        if (tokens.size() > 2) {
            if (tokens.get(0).getValue().equals("gas") && tokens.get(1).getValue().equals(":")) {
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

        Gas gas = GasRegistry.getGas(valueBuilder.toString());
        if (gas != null) {
            return new GasReferenceSymbol(valueBuilder.toString());
        }

        return null;
    }

    private class GasReferenceSymbol implements IZenSymbol {
        private final String name;

        public GasReferenceSymbol(String name) {
            this.name = name;
        }

        @Override
        public IPartialExpression instance(ZenPosition position) {
            JavaMethod method = JavaMethod.get(GlobalRegistry.getTypeRegistry(), GasBracketHandler.class, "getGas", String.class);
            return new ExpressionJavaCallStatic(position, method, new ExpressionString(position, name));
        }
    }
}