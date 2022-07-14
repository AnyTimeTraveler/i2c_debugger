package org.simonscode.i2c_controller.gui;

import org.simonscode.i2c_controller.serial.commands.ReadCommand;
import org.simonscode.i2c_controller.serial.commands.WriteCommand;
import org.simonscode.i2c_controller.svd.Field;
import org.simonscode.i2c_controller.svd.Register;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;

import static org.simonscode.i2c_controller.gui.GuiUtils.leftJustify;

public class RegisterPanel extends JPanel {
    public RegisterPanel(Register register) {
        super();
        setMaximumSize(new Dimension(500, 10000));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("0x" + Integer.toHexString(register.addressOffsetInBytes).toUpperCase() + " : " + register.name));
        add(leftJustify(new JLabel(register.description)));
        JButton readButton = new JButton("Read");
        JButton writeButton = new JButton("Write");
        if (register.fields != null && !register.fields.isEmpty()) {
            JPanel fieldsPanel = new JPanel();
            fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
            for (Field field : register.fields) {
                fieldsPanel.add(new FieldPanel(field));
            }
            add(fieldsPanel);
            add(readButton);
        } else {
            int max = (int) (Math.pow(2, register.sizeInBits) - 1);
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, max, 1));
            add(spinner);
            JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
            JFormattedTextField tf = editor.getTextField();
            tf.setFormatterFactory(new MyFormatterFactory(String.valueOf(max).length()));
            add(readButton);
            writeButton.addActionListener(ignored -> {
                byte[] bytes = new byte[register.getSizeInBytes()];
                for (int i = 0; i < register.getSizeInBytes(); i++) {

                }
                new WriteCommand(true, register.getSizeInBytes(), bytes);
            });
            add(writeButton);
        }
        readButton.addActionListener(ignored -> {
            new ReadCommand(true, register.addressOffsetInBytes, register.getSizeInBytes());
        });
    }

    private static class MyFormatterFactory extends DefaultFormatterFactory {
        private final int length;

        public MyFormatterFactory(int length) {
            this.length = length;
        }

        public JFormattedTextField.AbstractFormatter getDefaultFormatter() {
            return new HexFormatter(length);
        }
    }

    private static class HexFormatter extends DefaultFormatter {
        private final int length;

        public HexFormatter(int length) {
            this.length = length;
        }

        public Object stringToValue(String text) throws ParseException {
            try {
                return Integer.valueOf(text.substring(2), 16);
            } catch (NumberFormatException nfe) {
                throw new ParseException(text, 0);
            }
        }

        public String valueToString(Object value) throws ParseException {
            StringBuilder sb = new StringBuilder();
            sb.append("0x");
            String tmp = Integer.toHexString((Integer) value).toUpperCase();
            sb.append("0".repeat(Math.max(0, length - tmp.length())));
            return sb.toString();
        }
    }
}
