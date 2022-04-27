package org.simonscode.i2c_controller.gui;

import org.simonscode.i2c_controller.svd.AccessType;
import org.simonscode.i2c_controller.svd.EnumerationValue;
import org.simonscode.i2c_controller.svd.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import static org.simonscode.i2c_controller.gui.GuiUtils.leftJustify;

public class FieldPanel extends JPanel {
    public FieldPanel(Field field) {
        setBorder(BorderFactory.createTitledBorder(field.name));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(leftJustify(new JLabel(field.description)));
        JComboBox<EnumerationValue> values = new JComboBox<>(new Vector<>(field.enumeratedValues));
        if (field.getAccess() == AccessType.READ_ONLY) {
            values.setEnabled(false);
        }
        add(values);
        JButton updateButton = new JButton("Write new Value");
        updateButton.setEnabled(false);
        values.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Object item = event.getItem();
                updateButton.setEnabled(true);
            }
        });
        add(updateButton);
    }
}
