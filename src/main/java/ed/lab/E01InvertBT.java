package ed.lab;
public class E01InvertBT {
    public TreeNode<Integer> invertTree(TreeNode<Integer> root) {

        if (root == null) return null; // Caso base: si el nodo es nulo, retornamos null

        // Intercambiamos los hijos izquierdo y derecho
        TreeNode<Integer> temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);
        return root; // Retornamos la nueva raíz invertida
    }
}
