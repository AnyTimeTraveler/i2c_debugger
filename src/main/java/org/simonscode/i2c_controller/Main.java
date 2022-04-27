package org.simonscode.i2c_controller;

import org.simonscode.i2c_controller.gui.GUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(GUI::start);
    }
}
