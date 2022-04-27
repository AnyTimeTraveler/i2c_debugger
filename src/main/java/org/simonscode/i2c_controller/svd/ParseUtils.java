package org.simonscode.i2c_controller.svd;

public class ParseUtils {
    public static int parseNumberLiteral(String literal) {
        String trimmedLiteral = literal.trim();
        if (trimmedLiteral.startsWith("0x")) {
            return Integer.parseInt(trimmedLiteral.substring(2), 16);
        } else if (trimmedLiteral.startsWith("0b")) {
            return Integer.parseInt(trimmedLiteral.substring(2), 2);
        } else if (trimmedLiteral.startsWith("#")) {
            return Integer.parseInt(trimmedLiteral.substring(1), 2);
        } else {
            return Integer.parseInt(trimmedLiteral);
        }
    }
}
