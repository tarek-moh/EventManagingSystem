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
        root = insert(root, value);
        root = splay(search(value));
        nodeCount++;
    }

    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }

        if (value.compareTo(node.data) < 0) {
            node.left = insert(node.left, value);
            node.left.parent = node;
        } else if (value.compareTo(node.data) > 0) {
            node.right = insert(node.right, value);
            node.right.parent = node;
        }

        return node;
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
        while (node != null && node.parent != null) {
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

    private void rotateLeft(Node<T> node) {
        Node<T> rightChild = node.right;
        if (rightChild != null) {
            node.right = rightChild.left;
            if (rightChild.left != null) {
                rightChild.left.parent = node;
            }
            rightChild.parent = node.parent;
        }

        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        if (rightChild != null) {
            rightChild.left = node;
        }
        node.parent = rightChild;
    }

    private void rotateRight(Node<T> node) {
        Node<T> leftChild = node.left;
        if (leftChild != null) {
            node.left = leftChild.right;
            if (leftChild.right != null) {
                leftChild.right.parent = node;
            }
            leftChild.parent = node.parent;
        }

        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }

        if (leftChild != null) {
            leftChild.right = node;
        }
        node.parent = leftChild;
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