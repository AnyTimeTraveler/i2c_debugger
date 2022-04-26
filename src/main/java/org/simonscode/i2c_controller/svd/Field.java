package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.ArrayList;
import java.util.List;

public class Field {
    public String name;
    public String description;
    public String bitRange;
    @JsonProperty("access")
    public AccessType accessOverride;
    @JacksonXmlElementWrapper(localName = "enumeratedValues")
    public List<EnumerationValue> enumeratedValues = new ArrayList<>();

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public void setBitRange(String bitRange) {
        this.bitRange = bitRange.trim();
    }

    @Override
    public String toString() {
        return "\n\t\t\tField{" +
                "name='" + name + '\'' +
                ", bitRange='" + bitRange + '\'' +
                ", accessOverride=" + accessOverride +
                ", enumeratedValues=" + enumeratedValues +
                "}";
    }
}
