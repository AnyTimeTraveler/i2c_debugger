package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.ArrayList;
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
    public List<Field> fields = new ArrayList<>();

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public void setAddressOffsetInBytes(String addressOffsetInBytes) {
        this.addressOffsetInBytes = addressOffsetInBytes.trim();
    }

    public void setSizeInBits(String sizeInBits) {
        this.sizeInBits = sizeInBits.trim();
    }

    public void setResetValue(String resetValue) {
        this.resetValue = resetValue.trim();
    }

    @Override
    public String toString() {
        return "\n\t\tRegister{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", addressOffsetInBytes=" + addressOffsetInBytes +
                ", sizeInBits=" + sizeInBits +
                ", access=" + access +
                ", resetValue=" + resetValue +
                ", fields=" + fields +
                "}";
    }
}
