public class Arvore {
    private Node root;

    public Arvore() {
        root = new Node(15);
    }

    /**
     *
     * @return Check if the tree is empty.
     * */
    public boolean isEmpty() {
        if (root == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param value Insert a node by the root, comparing the if the left and right child are null, if they are they set the respective child the "newNode" value.
     *              If they arent, "newNode" becomes the new root.
     */
    public void insert(int value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            root = newNode;
        } else {
            Node aux = this.root;
            while(true) {
                if(newNode.getValue() > aux.getValue()) {
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

    public void removeD(int value) {
        Node aux = this.root;
        Node parentNode = null;

        while (aux != null) {
            if (value > aux.getValue()) {
                parentNode = aux;
                aux = aux.getRight();
            } else if (value < aux.getValue()) {
                parentNode = aux;
                aux = aux.getLeft();
            } else {
                // Encontrou o nó a ser removido
                if (aux.getLeft() != null && aux.getRight() != null) {
                    // Caso 3: Nó com dois filhos - procurar o sucessor (menor da subárvore direita)
                    Node successorParent = aux;
                    Node successor = aux.getRight();
                    while (successor.getLeft() != null) {
                        successorParent = successor;
                        successor = successor.getLeft();
                    }
                    // Substituir o valor do nó atual pelo valor do sucessor
                    aux.setValue(successor.getValue());
                    // Agora temos que remover o sucessor, que tem no máximo um filho (à direita)
                    if (successorParent.getLeft() == successor) {
                        successorParent.setLeft(successor.getRight());
                    } else {
                        successorParent.setRight(successor.getRight());
                    }
                } else if (aux.getLeft() != null || aux.getRight() != null) {
                    // Caso 2: Nó com um filho
                    Node child = (aux.getLeft() != null) ? aux.getLeft() : aux.getRight();
                    if (parentNode == null) {
                        this.root = child;
                    } else if (parentNode.getLeft() == aux) {
                        parentNode.setLeft(child);
                    } else {
                        parentNode.setRight(child);
                    }
                } else {
                    // Caso 1: Nó folha
                    if (parentNode == null) {
                        this.root = null;
                    } else if (parentNode.getLeft() == aux) {
                        parentNode.setLeft(null);
                    } else {
                        parentNode.setRight(null);
                    }
                }
                return;
            }
        }
    }

    /**
     *
     * @param value This method is to remove a certain node, making all comparisons and verifications.
     **/
    public void remove(int value) {
        if (isEmpty()) { // Apenas verificamos se a arvore está vazia.
            return;
        }

        Node parentNode = null; // Nó "pai" da raíz (sempre nulo). Vai servir para manipularmos apontamentos.
        Node aux = this.root; // Variavel auxiliar "guardando" a raiz.

        while (aux != null && aux.getValue() != value) { // Condição do loop de repetição verificando o valor do nó e se ele é diferente de nulo.
            parentNode = aux; // parentNode guarda o nó pai de aux
            if (aux.getValue() < value) { // Se o valor for menor, vamos para a esquerda, se for maior, vamos para a direita da arvore.
                aux = aux.getLeft();
            } else {
                aux = aux.getRight();
            }
        }

        if (aux == null) {
            return; // Caso o valor não exista, não removemos nada.
        }

        // Caso o nó tenha dois filhos.
        if (aux.getLeft() != null && aux.getRight() != null) {
            // Aqui vou usar o menor dos maiores.
            Node successorParentNode = aux; // O proprio nó começa como o pai do sucessor.
            Node successorNode = aux.getRight(); // Filho direito (menor dos maiores).

            // Descemos pela esquerda para buscar o menor dos maiores.
            while (successorNode.getLeft() != null) {
                successorParentNode = successorNode; // Atualizamos o nó "pai" do sucessor.
                successorNode = successorNode.getLeft(); // Vamos pela esquerda.
            }

            aux.setValue(successorNode.getValue()); // Substituimos o valor do nó que vai ser removido pelo valor do sucessor.

            // Vamos remover o nó sucessor.
            aux = successorNode;
            parentNode = successorParentNode;
        }

        Node childNode; // Definimos o filho do nó atual.

        if (aux.getLeft() != null) {
            childNode = aux.getLeft(); // Se tem um filho a esquerda, usamos ele.
        } else {
            childNode = aux.getRight(); // Se não, usamos o da direita (maior dos menores)
        }

        // Se o nó a ser removido for a raiz
        if (parentNode == null) {
            root = childNode; // Primeiramente atualizamos a raiz
        } else if (parentNode.getLeft() == aux) {
            parentNode.setLeft(childNode); // Se o no aux era o filho a esquerda, reconectamos eles.
        } else {
            parentNode.setRight(childNode); // Se o no aux era o filho a direita, reconectamos eles.
        }
    }
}