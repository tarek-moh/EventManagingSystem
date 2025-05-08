package org.example.eventmanagingsystem.util;

import java.util.ArrayList;

/**
 * Generic Splay Tree implementation in Java
 * @param <T> Type of data stored in nodes, must implement Comparable
 * @author Tarek
 */
public class SplayTree<T extends Comparable<T>> {

    private static class Node<T> {
        T data;
        Node<T> left, right, parent;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private Node<T> root;
    private int nodeCount;

    public SplayTree() {
        this.root = null;
        this.nodeCount = 0;
    }

    /**
     * Inserts a value into the tree and splays the new node to root
     * @param value The value to insert
     */
    public void insert(T value) {
        // Handle memory allocation (Java doesn't need explicit memory checks)
        Node<T> newNode = new Node<>(value);

        // Special case: empty tree
        if (root == null) {
            root = newNode;
            nodeCount++;
            return;
        }

        // Iterative BST insertion
        Node<T> current = root;
        Node<T> parent = null;

        while (current != null) {
            parent = current;
            int cmp = value.compareTo(current.data);

            if (cmp == 0) {
                // Duplicate found - splay this node to root
                root = splay(current);
                return;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        // Insert the new node
        newNode.parent = parent;
        if (value.compareTo(parent.data) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        // Splay the new node to root
        root = splay(newNode);
        nodeCount++;
    }

    /**
     * Removes a value from the tree if it exists
     * @param value The value to remove
     * @return true if the value was found and removed
     */
    public boolean erase(T value) {
        Node<T> node = search(value);
        if (node == null) {
            return false;
        }

        root = splay(node);

        if (root.left == null) {
            root = root.right;
        } else {
            Node<T> rightSubtree = root.right;
            root = splay(findMax(root.left));
            root.right = rightSubtree;
        }

        nodeCount--;
        return true;
    }

    private Node<T> findMax(Node<T> node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * Searches for a value and splays the found node to root
     * @param value The value to search for
     * @return Node containing the value or null if not found
     */
    public Node<T> search(T value) {
        Node<T> node = searchNoSplay(value);
        if (node != null) {
            root = splay(node);
        }
        return node;
    }

    private Node<T> searchNoSplay(T value) {
        Node<T> current = root;
        while (current != null) {
            int cmp = value.compareTo(current.data);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
    }

    /**
     * @return The value at the root of the tree
     * @throws IllegalStateException if tree is empty
     */
    public T getRootValue() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return root.data;
    }

    /**
     * @return true if the tree is empty
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return Number of nodes in the tree
     */
    public int size() {
        return nodeCount;
    }

    // Splay tree specific operations
    private Node<T> splay(Node<T> node) {
        if (node == null) return null;

        while (node.parent != null) {
            Node<T> parent = node.parent;
            Node<T> grandparent = parent.parent;

            if (grandparent == null) {
                // Zig step
                if (node == parent.left) {
                    rotateRight(parent);
                } else {
                    rotateLeft(parent);
                }
            } else {
                if (parent == grandparent.left) {
                    if (node == parent.left) {
                        // Zig-zig right
                        rotateRight(grandparent);
                        rotateRight(parent);
                    } else {
                        // Zig-zag left-right
                        rotateLeft(parent);
                        rotateRight(grandparent);
                    }
                } else {
                    if (node == parent.right) {
                        // Zig-zig left
                        rotateLeft(grandparent);
                        rotateLeft(parent);
                    } else {
                        // Zig-zag right-left
                        rotateRight(parent);
                        rotateLeft(grandparent);
                    }
                }
            }
        }
        return node;
    }

    private void rotateLeft(Node<T> x) {
        Node<T> y = x.right;
        if (y == null) return;

        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }

        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node<T> x) {
        Node<T> y = x.left;
        if (y == null) return;

        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }

        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }

        y.right = x;
        x.parent = y;
    }
    /**
     * @return List of all values in the tree in in-order traversal
     */
    public ArrayList<T> inOrderTraversal() {
        ArrayList<T> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    private void inOrderTraversal(Node<T> node, ArrayList<T> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.data);
            inOrderTraversal(node.right, result);
        }
    }
}