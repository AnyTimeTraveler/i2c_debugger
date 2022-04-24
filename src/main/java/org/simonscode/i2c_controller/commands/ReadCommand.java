package org.simonscode.i2c_controller.commands;

import java.io.IOException;
import java.io.OutputStream;

import static org.simonscode.i2c_controller.Gui.ETX;
import static org.simonscode.i2c_controller.Gui.STX;

public class ReadCommand {
    private final byte address;
    private final int length;

    public ReadCommand(byte address, int length) {
        this.address = address;
        this.length = length;
    }

    public void send(OutputStream os) throws IOException {
        os.write(STX);
        os.write('r');
        os.write(address);
        os.write(length);
        os.write(ETX);
    }
}
