import java.util.Stack;
import java.io.*;

class Node
{
    int iDate;
    double fDate;
    Node leftChild;
    Node rightChild;

    public void displayNode()
    {

    }
}

class Tree 
{
    private Node root;

    public Node find(int key)
    {
        Node current = root;
        while(current.iDate != key)
        {
            if(key < current.iDate)
                current = current.leftChild;
            else
                current = current.rightChild;

            if(current == null)
                return null;
        }
        return current;
    }

    public Node getRoot()
    {
        return root;
    }

    public void insert(int d, double dd)
    {
        Node newNode = new Node();
        newNode.iDate = d;
        newNode.fDate = dd;

        if(root == null)
            root = newNode;
        else
        {
            Node current = root;
            Node parent;

            while(true)
            {
                parent = current;

                if(d < current.iDate)
                {
                    current = current.leftChild;
                    if(current == null)
                    {
                        parent.leftChild = newNode;
                        return;
                    }
                }
                else
                {
                    current = current.rightChild;
                    if(current == null)
                    {
                        parent.rightChild = newNode;
                        return;
                    }
                }

            }


        }
    }

    public void preOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            System.out.print(localRoot.iDate + " " );
            inOrder(localRoot.leftChild);
            inOrder(localRoot.rightChild);
        }
    }

    public void inOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.iDate + " " );
            inOrder(localRoot.rightChild);
        }
    }

    public void postOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            inOrder(localRoot.leftChild);
            inOrder(localRoot.rightChild);
            System.out.print(localRoot.iDate + " " );
        }
    }

    public Node minimum()
    {
        Node current, last = null;
        current = root;
        while(current != null)
        {
            last = current;
            current = current.leftChild;
        }

        return last;
    }

    public Node maximum()
    {
        Node current, last = null;
        current = root;

        while(current != null)
        {
            last = current;
            current = current.rightChild;
        }

        return last;
    }

    public Node getSuccessor(Node delNode)
    {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;

        while(current != null)
        {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }

        if(successor != delNode.rightChild)
        {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }

        return successor;
    }



    public boolean delete(int id)
    {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        while(current.iDate != id)
        {
            parent = current;
            if(id < current.iDate)
            {
                isLeftChild = true;
                current = current.leftChild;
            }
            else
            {
                isLeftChild = false;
                current = current.rightChild;
            }
            if(current == null)
            {
                return false;
            }
        }

        if(isLeaf(current))
        {
            if(current == root)
            {
                root = null;
            }
            else if(isLeftChild)
                parent.leftChild = null;
            else 
                parent.rightChild = null;
        }
        else if(hasOneChild(current))
        {
            if(current.rightChild == null)
            {
                if(current == root)
                    root = current.leftChild;
                else if(isLeftChild)
                    parent.leftChild = current.leftChild;
                else 
                    parent.rightChild = current.leftChild;
            }
            else
            {
                if(current == root)
                    root = current.rightChild;
                else if(isLeftChild)
                    parent.leftChild = current.rightChild;
                else   
                    parent.rightChild = current.rightChild;
            }
        }
        else  
        {
            Node successor = getSuccessor(current);

            if(current == root)
            {
                root = successor;
            }
            else if(isLeftChild)
                parent.leftChild = successor;
            else 
                parent.rightChild = successor;

            successor.leftChild = current.leftChild;
        }

        return true;
    }

    public boolean isLeaf(Node temp){
        boolean isLeaf = 
            (temp.leftChild == null) &&
            (temp.rightChild == null);
        
        return isLeaf;
    }

    public boolean hasOneChild(Node temp)
    {
        boolean hasOne = 
            ((temp.leftChild != null)  ||
            (temp.rightChild == null)) ||
            ((temp.leftChild == null)  ||
            (temp.rightChild != null));

        return hasOne;
    }

    public void traverse(int traverseType)
    {
        switch(traverseType)
        {
            case 1: System.out.print("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2: System.out.print("\nInorder traversal: ");
                inOrder(root);
                break;
            case 3: System.out.print("\nPostorder traversal");
                postOrder(root);
                break;
        }
        System.out.println();
    }

    public void displayTree()
    {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;

        System.out.println("..................");

        while( isRowEmpty == false)
        {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for(int j=0; j<nBlanks; j++)
                System.out.print(' ');
            
            while(globalStack.isEmpty() == false)
            {
                Node temp = (Node)globalStack.pop();
                if(temp != null)
                {
                    System.out.print(temp.iDate);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);

                    if(temp.leftChild != null || temp.rightChild != null)
                        isRowEmpty = false;
                }
                else
                {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }

                for(int j=0; j<nBlanks*2-2; j++)
                {
                    System.out.print(' ');
                }
            }
            System.out.println();
            nBlanks /= 2;
            while(localStack.isEmpty() == false)
                globalStack.push(localStack.pop());
        }
        System.out.println(".............................");
    }
}

class TreeApp
{
    public static void main(String[] args)
    {
        Tree theTree = new Tree();

        theTree.insert(50, 1.5);
        theTree.insert(25, 1.7);
        theTree.insert(75, 1.9);
        theTree.insert(90, 2.1);
        theTree.insert(105, 2.3);

        Node found = theTree.find(25);
        theTree.inOrder(theTree.getRoot());

        if( found != null )
        {
            System.out.println("Found the node with key 25");
        }
        else
        {
            System.out.println("Could not find node with key 25");
        }

        System.out.println("Now we begin to delete nodes");
        theTree.delete(90);
        theTree.inOrder(theTree.getRoot());

        theTree.displayTree();

        

    }
}