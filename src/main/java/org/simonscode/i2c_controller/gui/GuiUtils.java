package org.simonscode.i2c_controller.gui;

import javax.swing.*;
import java.awt.*;

public class GuiUtils {
    public static Component leftJustify(Component panel )  {
        Box  b = Box.createHorizontalBox();
        b.add( panel );
        b.add( Box.createHorizontalGlue() );
        // (Note that you could throw a lot more components
        // and struts and glue in here.)
        return b;
    }
}
