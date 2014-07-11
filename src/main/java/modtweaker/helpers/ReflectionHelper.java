package modtweaker.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionHelper {
    public static <T> T getPrivateFinalObject(Object o, String... fieldName) {
        Class cls = o.getClass();
        for (String field : fieldName) {
            try {
                Field result = cls.getDeclaredField(field);
                result.setAccessible(true);
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(result, result.getModifiers() & ~Modifier.FINAL);
                return (T) result.get(o);
            } catch (Exception ex) {}
        }

        return null;
    }

    public static <T> T getPrivateStaticObject(Class cls, String... fieldName) {
        for (String field : fieldName) {
            try {
                Field result = cls.getDeclaredField(field);
                result.setAccessible(true);
                return (T) result.get(null);
            } catch (Exception e) {}
        }

        return null;
    }

    public static void setPrivateValue(Class cls, String field, int var) {
        cpw.mods.fml.relauncher.ReflectionHelper.setPrivateValue(cls, null, var, field);
    }

}
