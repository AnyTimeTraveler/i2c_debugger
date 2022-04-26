package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class Loader {
    public static void main(String[] args) throws IOException {
        XmlMapper mapper = new XmlMapper();
        Chip value = mapper.readValue(new File("/home/simon/projects/i2c_controller/src/main/resources/svd/ST25DV16K.svd"), Chip.class);

        System.out.println(value);
    }
}
