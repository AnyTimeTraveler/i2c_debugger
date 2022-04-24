package org.simonscode.i2c_controller;

import java.io.IOException;
import java.io.InputStream;

public class DataUtils {
    public static int readLength(InputStream is) throws IOException {
        int length = 0;
        int data;
        do {
            data = is.read();
            length += data;
        } while (data == 0xFF);
        return length;
    }
}
