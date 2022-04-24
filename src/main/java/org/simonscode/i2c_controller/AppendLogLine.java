package org.simonscode.i2c_controller;

import javax.swing.*;
import java.awt.*;

public class AppendLogLine extends AWTEvent {
    public static final int EVENT_ID = RESERVED_ID_MAX + 1;
    private final String line;

    public AppendLogLine(String line) {
        super(Gui.getInstance(), EVENT_ID);
        this.line = line;
    }

    public String getLine() {
        return line;
    }
}
