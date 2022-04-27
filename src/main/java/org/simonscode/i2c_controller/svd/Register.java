package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static org.simonscode.i2c_controller.svd.ParseUtils.parseNumberLiteral;

public class Register implements TreeNode {
    public String name;
    public String description;
    @JsonProperty("addressOffset")
    public int addressOffsetInBytes;
    @JsonProperty("size")
    public int sizeInBits;
    public AccessType access;
    public String resetValue;
    @JacksonXmlElementWrapper(localName = "fields")
    public List<Field> fields = new ArrayList<>();
    public Peripheral parent;

    public void init() {
        for (Field f : fields) {
            f.parent = this;
            f.init();
        }
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public void setAddressOffsetInBytes(String addressOffsetInBytes) {
        this.addressOffsetInBytes = parseNumberLiteral(addressOffsetInBytes);
    }

    public void setSizeInBits(String sizeInBits) {
        this.sizeInBits = parseNumberLiteral(sizeInBits);
    }

    public void setResetValue(String resetValue) {
        this.resetValue = resetValue.trim();
    }

    @Override
    public String toString() {
        // TODO: Align this to same leading zeros
        return Integer.toHexString(addressOffsetInBytes).toUpperCase() + " : " + name;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return fields.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return fields.size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return fields.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return fields.isEmpty();
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return Collections.enumeration(fields);
    }
}
