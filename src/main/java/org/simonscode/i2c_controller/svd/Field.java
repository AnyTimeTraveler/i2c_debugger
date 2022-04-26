package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

public class Field {
    public String name;
    public String description;
    public String bitRange;
    @JsonProperty("access")
    public AccessType accessOverride;
    @JacksonXmlElementWrapper(localName = "enumeratedValues")
    public List<EnumerationValue> enumeratedValues;

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", bitRange='" + bitRange + '\'' +
                ", accessOverride=" + accessOverride +
                ", enumeratedValues=" + enumeratedValues +
                "}\n";
    }
}
