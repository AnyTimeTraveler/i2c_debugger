package org.simonscode.i2c_controller;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Objects;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

import com.fazecast.jSerialComm.SerialPort;
import org.simonscode.i2c_controller.commands.ReadCommand;
import org.simonscode.i2c_controller.commands.WriteCommand;

public class Gui extends JFrame {

    public static final byte STX = 0x02;
    public static final byte ETX = 0x03;
    private SerialPort serialPort;
    private final JButton connectButton;
    private final JComboBox<String> portList;
    private final JTextArea logArea;
    private ResponseReader responseReader;
    private static final Gui instance;

    static {
        try {
            instance = new Gui();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> instance.setVisible(true));
    }

    public Gui() throws HeadlessException, ParseException {
        super("I2C Controller");
        enableEvents(AppendLogLine.EVENT_ID);
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create a drop-down box and connect button, then place them at the top of the window
        portList = new JComboBox<>();
        for (SerialPort portName : SerialPort.getCommPorts()) {
            portList.addItem(portName.getSystemPortName());
        }
        connectButton = new JButton("Connect");
        JPanel topPanel = new JPanel();
        topPanel.add(portList);
        topPanel.add(connectButton);
        add(topPanel, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

        JPanel paramsPanel = new JPanel();
        paramsPanel.add(new JLabel("ADDR:"));
        JFormattedTextField addressField = new JFormattedTextField(new MaskFormatter("HH"));
        addressField.setValue("01");
        paramsPanel.add(addressField);

        JComboBox<String> rwList = new JComboBox<>(new String[]{"Read", "Write"});
        rwList.setSelectedIndex(1);
        paramsPanel.add(rwList);

        paramsPanel.add(new JLabel("DATA:"));
        JTextField dataField = new JTextField(20);
        dataField.setText("10");
        paramsPanel.add(dataField);

        JButton sendButton = new JButton("Send");
        paramsPanel.add(sendButton);

        JButton scanButton = new JButton("Scan");
        paramsPanel.add(scanButton);
        add(paramsPanel, BorderLayout.SOUTH);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (connectButton.getText().equals("Disconnect")) {
                disconnect();
            }
        }));

        // configure the connect button and use another thread to listen for data
        connectButton.addActionListener(ignored -> {
            if (connectButton.getText().equals("Connect")) {
                connect();
            } else {
                disconnect();
            }
        });

        sendButton.addActionListener(ignored -> {
            OutputStream os = serialPort.getOutputStream();
            try {
                if ("Read".equals(rwList.getSelectedItem())) {
                    new ReadCommand(true, (byte) Integer.parseInt(addressField.getText(), 16), Integer.parseInt(dataField.getText())).send(os);
                } else {
                    byte[] data = new byte[dataField.getText().length() / 2];
                    for (int i = 0; i < data.length; i += 2) {
                        String hex = dataField.getText().substring(i);
                        if (hex.length() >= 2) {
                            String hexByte = hex.substring(0, 2);
                            data[i] = (byte) Integer.parseInt(hexByte, 16);
                        }
                    }
                    new WriteCommand(true, (byte) Integer.parseInt(addressField.getText(), 16), data).send(os);
                }
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        scanButton.addActionListener(ignored -> {
            Thread thread = new Thread(() -> {
                OutputStream os = serialPort.getOutputStream();
                for (int i = 1; i < 0x7F; i++) {
//            for (int i = 1; i < 10; i++) {
                    try {
                        new WriteCommand(true, (byte) i, new byte[0]).send(os);
                        Thread.sleep(50);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
            System.out.println("Scanning...");
        });


//        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEvent -> {
//            if (keyEvent.isControlDown() && keyEvent.getID() == KEY_RELEASED) {
//                switch (keyEvent.getKeyCode()) {
//                    case VK_SPACE:
//                        handleNext(null);
//                        return true;
//                    case VK_ENTER:
//                        handleStart(null);
//                        return true;
//                    case VK_ESCAPE:
//                        handleStop(null);
//                        return true;
//                    case VK_R:
//                        handleReset(null);
//                        return true;
//                }
//            }
//            return false;
//        });
    }

    protected void processEvent(AWTEvent event) {
        if (event.getID() == AppendLogLine.EVENT_ID) {
            AppendLogLine ev = (AppendLogLine) event;
            System.out.println(ev.getLine());
            logArea.append(ev.getLine() + '\n');
            logArea.setCaretPosition(logArea.getText().length());
        } else // other events go to the system default process event handler
        {
            super.processEvent(event);
        }
    }

    private void connect() {
        // attempt to connect to the serial port
        serialPort = SerialPort.getCommPort(Objects.requireNonNull(portList.getSelectedItem()).toString());
        serialPort.setBaudRate(115200);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        if (serialPort.openPort()) {
            connectButton.setText("Disconnect");
            portList.setEnabled(false);
        }

        if (responseReader != null) {
            try {
                responseReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        responseReader = new ResponseReader(serialPort.getInputStream());
        responseReader.start();
    }

    private void disconnect() {
        serialPort.closePort();
        portList.setEnabled(true);
        connectButton.setText("Connect");
    }

    public static Gui getInstance() {
        return instance;
    }
}