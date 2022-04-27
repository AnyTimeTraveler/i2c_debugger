package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class Field implements TreeNode {
    public String name;
    public String description;
    public String bitRange;
    @JsonProperty("access")
    public AccessType accessOverride;
    @JacksonXmlElementWrapper(localName = "enumeratedValues")
    public List<EnumerationValue> enumeratedValues = new ArrayList<>();

    public Register parent;

    public void init() {
        for (EnumerationValue e : enumeratedValues) {
            e.parent = this;
            e.init();
        }
    }

    public AccessType getAccess() {
        if (accessOverride != null) {
            return accessOverride;
        } else {
            return parent.access;
        }
    }

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
        return name;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return enumeratedValues.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return enumeratedValues.size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return enumeratedValues.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return enumeratedValues.isEmpty();
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return Collections.enumeration(enumeratedValues);
    }
}
