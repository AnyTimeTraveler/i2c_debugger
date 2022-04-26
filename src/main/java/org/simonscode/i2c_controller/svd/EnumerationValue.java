package org.simonscode.i2c_controller.svd;

public class EnumerationValue {
    public String name;
    public String description;
    public String value;

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public void setValue(String value) {
        this.value = value.trim();
    }

    @Override
    public String toString() {
        return "\n\t\t\t\tEnumerationValue{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                "}";
    }
}
