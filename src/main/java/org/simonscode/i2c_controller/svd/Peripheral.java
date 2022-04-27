package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.swing.tree.TreeNode;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class Peripheral implements TreeNode {
    public String name;
    public String version;
    public String description;
    public String groupName;
    public String baseAddress;
    public AccessType access;
    @JacksonXmlElementWrapper(localName = "registers")
    public List<Register> registers;
    public Chip parent;

    public void init() {
        for (Register r : registers) {
            r.parent = this;
            r.init();
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return registers.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return registers.size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return registers.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return Collections.enumeration(registers);
    }
}
