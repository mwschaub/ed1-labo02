package ed.lab;

import java.util.Comparator;

public class E03AVLTree<T> {
    private final Comparator<T> comparator;
    private TreeNode<T> root;
    private int size;

    public E03AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    public void insert(T value) {
        root = insertRec(root, value);
        size++;
    }

    private TreeNode<T> insertRec(TreeNode<T> node, T value) {
        if (node == null) {
            return new TreeNode<>(value);
        }

        if (comparator.compare(value, node.value) < 0) {
            node.left = insertRec(node.left, value);
        } else if (comparator.compare(value, node.value) > 0) {
            node.right = insertRec(node.right, value);
        } else {
            // Value already exists
            return node;
        }

        return balance(node);
    }

    public void delete(T value) {
        if (search(value) != null) {
            root = deleteRec(root, value);
            size--;
        }
    }
    private TreeNode<T> deleteRec(TreeNode<T> node, T value) {
        if (node == null) {
            return null;
        }

        if (comparator.compare(value, node.value) < 0) {
            node.left = deleteRec(node.left, value);
        } else if (comparator.compare(value, node.value) > 0) {
            node.right = deleteRec(node.right, value);
        } else {
            if (node.left == null || node.right == null) {
                // Node with one child or no child
                node = (node.left != null) ? node.left : node.right;
            } else {
                // Node with two children: Get the inorder successor (smallest in the right subtree)
                TreeNode<T> temp = minValueNode(node.right);
                node.value = temp.value; // Copy the inorder successor's content to this node
                node.right = deleteRec(node.right, temp.value); // Delete the inorder successor
            }
        }

        if (node == null) {
            return null;
        }

        return balance(node);
    }

    private TreeNode<T> minValueNode(TreeNode<T> node) {
        TreeNode<T> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public T search(T value) {
        return searchRec(root, value);
    }

    private T searchRec(TreeNode<T> node, T value) {
        if (node == null) {
            return null;
        }
        if (comparator.compare(value, node.value) < 0) {
            return searchRec(node.left, value);
        } else if (comparator.compare(value, node.value) > 0) {
            return searchRec(node.right, value);
        } else {
            return node.value; // Value found
        }
    }

    public int height() {
        return heightRec(root);
    }

    private int heightRec(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(heightRec(node.left), heightRec(node.right));
    }

    public int size() {
        return size;
    }

    private TreeNode<T> balance(TreeNode<T> node) {
        int balanceFactor = getBalance(node);

        // Left Left Case
        if (balanceFactor > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }

        // Right Right Case
        if (balanceFactor < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }

        // Left Right Case
        if (balanceFactor > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right Left Case
        if (balanceFactor < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node; // Return the node if already balanced
    }

    private int getBalance(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        return heightRec(node.left) - heightRec(node.right);
    }

    private TreeNode<T> rotateRight(TreeNode<T> y) {
        TreeNode<T> x = y.left;
        TreeNode<T> T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        return x;
    }

    private TreeNode<T> rotateLeft(TreeNode<T> x) {
        TreeNode<T> y = x.right;
        TreeNode<T> T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        return y;
    }
}