import java.util.LinkedList;
import java.util.Queue;

public class Arvore {
    private Node root;

    public Arvore() {
        root = new Node(15);
    }

    /**
     * @return Check if the arvore is empty.
     */
    public boolean isEmpty() {
        if (root == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param value Insert a node by the root, comparing the if the left and right child are null, if they are they set the respective child the "newNode" value.
     *              If they arent, "newNode" becomes the new root.
     */
    public void insert(int value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            root = newNode;
        } else {
            Node aux = this.root;
            while (true) {
                if (newNode.getValue() > aux.getValue()) {
                    if (aux.getRight() == null) {
                        aux.setRight(newNode);
                        return;
                    } else {
                        aux = aux.getRight();
                    }
                } else {
                    if (aux.getLeft() == null) {
                        aux.setLeft(newNode);
                        return;
                    } else {
                        aux = aux.getLeft();
                    }
                }
            }
        }
    }

    public void remove(int value) {
        Node aux = this.root;
        Node parentNode = null;

        while (aux != null) {
            // Faz a verificação do tamanho do valor, se for maior vai para a direita, se for menor
            // vai para a esquerda da arvore
            if (value > aux.getValue()) {
                parentNode = aux;
                aux = aux.getRight();
            } else if (value < aux.getValue()) {
                parentNode = aux;
                aux = aux.getLeft();
            } else {
                // Verifica se o nó possui dois filhos
                if (aux.getLeft() != null && aux.getRight() != null) {
                    // Caso 1: O nó tem dois filhos
                    removeNodeWithTwoChildren(aux, parentNode);
                } else if (aux.getLeft() != null || aux.getRight() != null) {
                    // Caso 2: O nó tem um filho
                    removeNodeWithOneChild(aux, parentNode);
                }
                // Caso o nó não tenha filhos (nó folha)
                else {
                    // Caso 3: O nó é uma folha
                    removeLeafNode(aux, parentNode);
                }
                return;
            }
        }
    }

    private void removeRootNode() {
        if (isEmpty()) { // Se a raiz for nula, para a execução
            return;
        }

        if (this.root.getLeft() == null && this.root.getRight() == null) { // Se os dois filhos forem nulos, remove a raiz
            this.root = null;
        } else if (this.root.getLeft() == null) { // Se a raiz tem um filho a direita, o filho a direita se torna a raiz
            this.root = this.root.getRight();
        } else if (this.root.getRight() == null) { // Se a raiz tem um filho a esquerda, o filho a esquerda se torna a raiz
            this.root = this.root.getLeft();
        } else {
            removeNodeWithTwoChildren(this.root, null); // Se tem dois filhos, chamamos o metodo para fazer as verificações
        }
    }

    private void removeLeafNode(Node node, Node parentNode) {
        if (parentNode.getLeft() == node) { // Se o nó a esquerda do "pai" for igual ao nó que queremos remover, apenas setamos como null
            parentNode.setLeft(null);
        } else {
            parentNode.setRight(null); // Fazemos a mesma coisa para caso o nó da direita do pai seja o nó que queremos remover.
        }
    }

    private void removeNodeWithOneChild(Node node, Node parent) {
        Node child;

        if (node.getLeft() != null) {
            child = node.getLeft(); // Caso o filho esteja a direita pegamos e "salvamos" ele
        } else {
            child = node.getRight(); // Faz a mesma coisa, mas para o filho à direita
        }

        if (parent.getLeft() == node) {
            parent.setLeft(child); // Se o nó removido é o filho a esquerda, passa o apontamento do filho do nó removido para o pai
        } else {
            parent.setRight(child); // Se o nó removido for o filho a direita, faz a mesma coisa
        }
    }

    private void removeNodeWithTwoChildren(Node node, Node parentNode) {
        Node sucessorParent = node; // O pai do sucessor começa sendo o próprio nó
        Node sucessor = node.getRight(); // O sucessor inicial é o nó a direita

        while (sucessor.getLeft() != null) { // Busca o menor dos maiores
            sucessorParent = sucessor; // Atualiza o pai do sucessor
            sucessor = sucessor.getLeft(); // "Desce" para o filho esquerdo
        }

        node.setValue(sucessor.getValue()); // Substituimos o valor do nó a ser removido pelo do nó sucessor

        if (sucessorParent.getLeft() == sucessor) {
            sucessorParent.setLeft(sucessor.getRight()); // Pega o apontamento a direita do sucessor
            // e substitui o proprio sucessor (Pai - filho a esquerda - sucessor a direita)
        } else {
            sucessorParent.setRight(sucessor.getRight()); // Faz a mesma coisa, mas agora com o filho a direita
            // (Pai - filho a direita - sucessor a direita)
        }
    }

    public void printLevelOrder() {
        if (root == null) {
            System.out.println("Árvore vazia.");
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.getValue() + " ");

            if (current.getLeft() != null) {
                queue.offer(current.getLeft());
            }

            if (current.getRight() != null) {
                queue.offer(current.getRight());
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Arvore arvore = new Arvore();

        System.out.println("Inserindo elementos:");
        arvore.insert(50);
        arvore.insert(30);
        arvore.insert(70);
        arvore.insert(20);
        arvore.insert(40);
        arvore.insert(60);
        arvore.insert(80);
        arvore.printLevelOrder();

        System.out.println("\nRemovendo o nó 20:");
        arvore.remove(20);
        arvore.printLevelOrder();

        System.out.println("\nRemovendo o nó 30:");
        arvore.remove(30);
        arvore.printLevelOrder();

        System.out.println("\nRemovendo o nó 50:");
        arvore.remove(50);
        arvore.printLevelOrder();
    }
}