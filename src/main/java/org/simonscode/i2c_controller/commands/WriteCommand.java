package org.simonscode.i2c_controller.commands;

import java.io.IOException;
import java.io.OutputStream;

import static org.simonscode.i2c_controller.Gui.ETX;
import static org.simonscode.i2c_controller.Gui.STX;

public class WriteCommand {
    private final byte address;
    private final byte[] data;

    public WriteCommand(byte address, byte[] data) {
        this.address = address;
        this.data = data;
    }

    public void send(OutputStream os) throws IOException {
        os.write(STX);
        os.write('w');
        os.write(address);
        os.write(data.length);
        for (int i = 0; i < data.length; i += 2) {
            os.write(data[i]);
        }
        os.write(ETX);
    }
}
