package android.zyf.ktx;

import androidx.databinding.InverseMethod;

public class Conv {
    @InverseMethod("toUnbox")
    public static int toUnbox(Integer boxed) {
        return boxed == null ? 0 : boxed;
    }

    @InverseMethod("toUnbox")
    public static long toUnbox(Long boxed) {
        return boxed == null ? 0L : boxed;
    }

    @InverseMethod("toUnbox")
    public static short toUnbox(Short boxed) {
        return boxed == null ? 0 : (short) boxed;
    }

    @InverseMethod("toUnbox")
    public static byte toUnbox(Byte boxed) {
        return boxed == null ? 0 : (byte) boxed;
    }

    @InverseMethod("toUnbox")
    public static char toUnbox(Character boxed) {
        return boxed == null ? '\u0000' : boxed;
    }

    @InverseMethod("toUnbox")
    public static double toUnbox(Double boxed) {
        return boxed == null ? 0.0 : boxed;
    }

    @InverseMethod("toUnbox")
    public static float toUnbox(Float boxed) {
        return boxed == null ? 0f : boxed;
    }

    @InverseMethod("toUnbox")
    public static boolean toUnbox(Boolean boxed) {
        return boxed != null && boxed;
    }

    @InverseMethod("toUnbox")
    public static String toUnbox(String boxed) {
        return boxed;
    }

    @InverseMethod("stringOf")
    public static String stringOf(Integer boxed) {
        return String.valueOf(boxed);
    }

    @InverseMethod("stringOf")
    public static String stringOf(Boolean boxed) {
        return String.valueOf(boxed);
    }

    @InverseMethod("stringOf")
    public static String stringOf(Byte boxed) {
        return String.valueOf(boxed);
    }

    @InverseMethod("stringOf")
    public static String stringOf(Long boxed) {
        return String.valueOf(boxed);
    }

    @InverseMethod("stringOf")
    public static String stringOf(Double boxed) {
        return String.valueOf(boxed);
    }

    @InverseMethod("stringOf")
    public static String stringOf(Float boxed) {
        return String.valueOf(boxed);
    }

    @InverseMethod("stringOf")
    public static String stringOf(String boxed) {
        return String.valueOf(boxed);
    }

    @InverseMethod("stringOf")
    public static String stringOf(Short boxed) {
        return String.valueOf(boxed);
    }

    @InverseMethod("stringOf")
    public static String stringOf(Object boxed) {
        return String.valueOf(boxed);
    }
}
