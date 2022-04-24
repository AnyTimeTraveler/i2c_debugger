package org.simonscode.i2c_controller.responses;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.DataFormatException;

import static org.simonscode.i2c_controller.DataUtils.readLength;
import static org.simonscode.i2c_controller.Gui.ETX;
import static org.simonscode.i2c_controller.responses.ResponseType.*;

public class Response {

    private final ResponseType type;
    private final int address;
    private final String status;
    private final byte[] data;

    public Response(InputStream is) throws DataFormatException, IOException {
        int read = is.read();
        type = switch (read) {
            case 'w' -> WRITE;
            case 'r' -> READ;
            case 'd' -> DEBUG;
            case 'e' -> ERROR;
            default -> throw new DataFormatException("Unexpected value for type: " + read);
        };

        if (type == ERROR) {
            address = 0;
            status = "error";
        } else if (type == DEBUG) {
            address = 0;
            status = "debug";
        } else {
            address = is.read();

            read = is.read();
            status = switch (read) {
                case 0 -> "success";
                case 1 -> "data too long to fit in transmit buffer";
                case 2 -> "received NACK on transmit of address";
                case 3 -> "received NACK on transmit of data";
                case 4 -> "other error";
                case 5 -> "timeout";
                default -> throw new DataFormatException("Unexpected value for status: " + read);
            };
        }

        this.data = new byte[readLength(is)];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) is.read();
        }

        if (is.read() != ETX) {
            throw new DataFormatException("Expected ETX at last byte");
        }
    }

    public int getAddress() {
        return address;
    }

    public byte[] getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return new String(data, StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Response{");
        sb.append("type=");
        sb.append(type);
        if (type == READ || type == WRITE) {
            sb.append(", address=");
            sb.append(address);
            sb.append(", status='");
            sb.append(status);
            sb.append('\'');
            sb.append(", data=");
            sb.append(Arrays.toString(data));
        } else {
            sb.append(", message='");
            sb.append(getMessage());
            sb.append("'");
        }
        sb.append('}');
        return sb.toString();
    }
}
