package org.simonscode.i2c_controller.svd;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.swing.tree.TreeNode;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@JacksonXmlRootElement(localName = "peripherals")
public class Chip implements TreeNode {

    public void init() {
        for (Peripheral p : peripheral) {
            p.parent = this;
            p.init();
        }
    }

    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Peripheral> peripheral;

    public String filename;

    @Override
    public String toString() {
        return filename;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return peripheral.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return peripheral.size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return peripheral.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return peripheral.isEmpty();
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return Collections.enumeration(peripheral);
    }
}
