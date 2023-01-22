package alps.java.api.util;

import java.util.List;

/**
 * Interface to the tree node class
 * @param <T>
 */
public interface ITreeNode<T> {
    /**
     * Sets the parent node
     * @param parent the parent node
     */
    void setParentNode(ITreeNode<T> parent);

    /**
     * Overrides the current child nodes with a list of new child nodes
     * @param childNodes the new child nodes
     */
    void setChildNodes(List<ITreeNode<T>> childNodes);


    /**
     * Adds a child to the list of child nodes
     * @param child the node of the child
     */
    void addChild(ITreeNode<T> child);

    /**
     * Returns the parent node
     * @return the parent node
     */
    ITreeNode<T> getParentNode();


    /**
     * Returns the child nodes
     * @return the child nodes
     */
    List<ITreeNode<T>> getChildNodes();

    ITreeNode<T> getChild(int index);

    /**
     * Sets the content of the node
     * @param content the content
     */
    void setContent(T content);

    /**
     * Returns the content of the node
     * @return the content of the node
     */
    T getContent();

    /**
     * Checks whether the node contains a given string as content
     * @param compare the String that will be checked as reference
     * @param node
     * @return true if the String equals the content, false if not
     */
    boolean containsContent(T compare, ITreeNode<T> node);

    /**
     * Checks whether the given node is sublcass of a specified node
     * @param parent the other node the given instance might be subclass of
     * @param direct if true is passed, this method only returns true if the current instance is a direct sublass of the parent class
     * @return true if it is a subclass of the specified parent, false if not
     */
    boolean isSubClassOf(ITreeNode<T> parent, boolean direct);

    /**
     * Returns the root node of the current tree node
     * @return the root node
     */
    ITreeNode<T> getRoot();

    /**
     * Returns the height of the longest path to a leaf starting from this node.
     * If this node is already a leaf, it returns 0
     * @return
     */
    int getHeigthToLastLeaf();
}
