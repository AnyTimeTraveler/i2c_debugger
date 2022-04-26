package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.xml.stream.XMLInputFactory;
import java.io.FileInputStream;
import java.io.IOException;

public class Loader {
    public static void main(String[] args) throws IOException {
        XMLInputFactory f = XMLInputFactory.newFactory();

        XmlMapper mapper = new XmlMapper();
        Chip value = mapper.readValue(new FileInputStream("/home/simon/projects/i2c_controller/src/main/resources/svd/ST25DV16K.svd"), Chip.class);

        System.out.println(value);
    }
}
