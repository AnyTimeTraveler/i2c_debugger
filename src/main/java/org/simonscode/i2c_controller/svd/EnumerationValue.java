package org.simonscode.i2c_controller.svd;

public class EnumerationValue {
    public String name;
    public String description;
    public String value;

    @Override
    public String toString() {
        return "EnumerationValue{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                "}\n";
    }
}
