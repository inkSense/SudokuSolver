package org.sudokusolver.A_entities.objectsAndDataStructures;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Tree<T> {
    private final TreeNode<T> root;

    public Tree(T rootValue) { this.root = new TreeNode<>(rootValue); }
    public TreeNode<T> root() { return root; }

    /** Tiefensuche mit Callback */
    public void dfs(Consumer<T> visit) {
        dfsRecursive(root, visit);
    }
    private void dfsRecursive(TreeNode<T> node, Consumer<T> visit) {
        visit.accept(node.getValue());
        for (TreeNode<T> c : node.children()) dfsRecursive(c, visit);
    }

    /** Zählt alle Blätter */
    public long leafCount() {
        return Stream.iterate(List.of(root), l -> !l.isEmpty(),
                        l -> l.stream().flatMap(n -> n.children().stream()).toList())
                .flatMap(List::stream)
                .filter(n -> n.children().isEmpty())
                .count();
    }
}

