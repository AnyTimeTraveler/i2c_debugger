package org.simonscode.i2c_controller;

import org.simonscode.i2c_controller.responses.Response;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;

import static org.simonscode.i2c_controller.Gui.STX;

public class ResponseReader extends Thread implements AutoCloseable {
    private final InputStream is;
    private boolean closed = false;

    public ResponseReader(InputStream is) {
        super("ResponseReader");
        this.is = is;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (!closed) {
            try {
                int stx = is.read();
                if (stx != STX) {
                    System.err.printf("ResponseReader: Skipping unexpected byte: %02X\n", stx);
                    continue;
                }
                Response response = new Response(is);
                EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
                eventQueue.postEvent(new AppendLogLine(response.toString()));
            } catch (IOException e) {
                closed = true;
            } catch (DataFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws Exception {
        closed = true;
        while (isAlive())
            ;
    }
}
