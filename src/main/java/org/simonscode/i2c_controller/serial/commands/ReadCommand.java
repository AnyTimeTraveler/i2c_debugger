package org.simonscode.i2c_controller.serial.commands;

import java.io.IOException;
import java.io.OutputStream;

import static org.simonscode.i2c_controller.serial.Flags.FLAGS_SEND_STOP;
import static org.simonscode.i2c_controller.gui.GUI.ETX;
import static org.simonscode.i2c_controller.gui.GUI.STX;

public record ReadCommand(boolean sendStop, byte address, int length) {

    public void send(OutputStream os) throws IOException {
        int flags = 0;
        if (sendStop) {
            flags |= FLAGS_SEND_STOP;
        }
        os.write(STX);
        os.write('r');
        os.write(flags);
        os.write(address);
        os.write(length);
        os.write(ETX);
    }
}
