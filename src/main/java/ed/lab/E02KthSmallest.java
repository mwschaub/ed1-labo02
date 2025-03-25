package ed.lab;

public class E02KthSmallest {

    private int count = 0; // Contador de nodos visitados
    private int result = 0; // Resultado a devolver

    public int kthSmallest(TreeNode<Integer> root, int k) {
        count = 0; // Reiniciar el contador y resultado
        result = 0;
        inOrder(root, k);
        return result;
    }


    private void inOrder(TreeNode<Integer> node, int k) {
        if (node == null) return;
        inOrder(node.left, k);
        count++;

        if (count == k) { // Si es el k-ésimo elemento, lo almacenamos
            result = node.value;
            return;
        }
        inOrder(node.right, k);
    }
}