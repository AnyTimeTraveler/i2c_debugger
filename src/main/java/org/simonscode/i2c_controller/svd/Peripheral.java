package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Peripheral {
    public String name;
    public String version;
    public String description;
    public String groupName;
    public String baseAddress;
    public AccessType access;
    @JacksonXmlElementWrapper(localName = "registers")
    public List<Register> registers;

    @Override
    public String toString() {
        return "Peripheral{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", groupName='" + groupName + '\'' +
                ", baseAddress=" + baseAddress +
                ", access=" + access +
                ", registers=" + registers +
                "}\n";
    }
}
