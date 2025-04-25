package org.sudokusolver.A_entities.objectsAndDataStructures;

import java.util.List;
import java.util.ArrayList;

public class TreeNode<T> {
    private final T value;
    private final List<TreeNode<T>> children = new ArrayList<>();

    public TreeNode(T value) { this.value = value; }

    public TreeNode<T> addChild(T childValue) {
        TreeNode<T> child = new TreeNode<>(childValue);
        children.add(child);
        return child;
    }

    public T getValue()                 { return value; }
    public List<TreeNode<T>> children() { return children; }
}

