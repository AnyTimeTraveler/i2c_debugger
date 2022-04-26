package org.simonscode.i2c_controller.commands;

import java.io.IOException;
import java.io.OutputStream;

import static org.simonscode.i2c_controller.Flags.FLAGS_SEND_STOP;
import static org.simonscode.i2c_controller.Gui.ETX;
import static org.simonscode.i2c_controller.Gui.STX;

public record WriteCommand(boolean sendStop, byte address, byte[] data) {

    public void send(OutputStream os) throws IOException {
        int flags = 0;
        if (sendStop) {
            flags |= FLAGS_SEND_STOP;
        }
        os.write(STX);
        os.write('w');
        os.write(flags);
        os.write(address);
        os.write(data.length);
        for (int i = 0; i < data.length; i += 2) {
            os.write(data[i]);
        }
        os.write(ETX);
    }
}
