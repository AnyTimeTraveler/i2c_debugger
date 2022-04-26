package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "peripherals")
public class Chip {
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Peripheral> peripheral;

    @Override
    public String toString() {
        return "Chip{" +
                "peripheral=" + peripheral +
                '}';
    }
}
