package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

public class Register {
    public String name;
    public String description;
    @JsonProperty("addressOffset")
    public String addressOffsetInBytes;
    @JsonProperty("size")
    public String sizeInBits;
    public AccessType access;
    public String resetValue;
    @JacksonXmlElementWrapper(localName = "fields")
    public List<Field> fields;

    @Override
    public String toString() {
        return "\tRegister{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", addressOffsetInBytes=" + addressOffsetInBytes +
                ", sizeInBits=" + sizeInBits +
                ", access=" + access +
                ", resetValue=" + resetValue +
                ", fields=" + fields +
                "}\n";
    }
}
