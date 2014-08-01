package hacker;

import java.util.ArrayList;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;

public class LoggerList {
    public static final ArrayList<LoggerList.StringClass> clazzes = new ArrayList();

    public static class StringClass {
        public String string;
        public Class clazz;

        public StringClass(Class clazz, String string) {
            this.clazz = clazz;
            this.string = string;
        }
    }

    public static void add(Class clazz) {
        LoggerList.add(clazz, "logger");
    }

    public static void add(Class clazz, String string) {
        clazzes.add(new StringClass(clazz, string));
    }

    static {
        add(TextureMap.class);
        add(TileEntity.class);
    }
}
