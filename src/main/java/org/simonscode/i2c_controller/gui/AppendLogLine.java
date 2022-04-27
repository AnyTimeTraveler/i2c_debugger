package org.simonscode.i2c_controller.gui;

import org.simonscode.i2c_controller.serial.responses.Response;

import java.awt.*;

public class AppendLogLine extends AWTEvent {
    public static final int EVENT_ID = RESERVED_ID_MAX + 1;
    private final Response line;

    public AppendLogLine(Response line) {
        super(GUI.getInstance(), EVENT_ID);
        this.line = line;
    }

    public Response getLine() {
        return line;
    }
}
