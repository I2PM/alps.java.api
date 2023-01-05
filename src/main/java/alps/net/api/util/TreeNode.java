package alps.net.api.util;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> implements ITreeNode<T> {
    private ITreeNode<T> parentNode;
    private final List<ITreeNode<T>> childNodes = new ArrayList<>();
    private T content;

    public TreeNode() {}

    public TreeNode(T content) {
        setContent(content);
    }

    public TreeNode(T content, ITreeNode<T> parent, List<ITreeNode<T>> childNodes) {
        setContent(content);
        setParentNode(parent);
        setChildNodes(childNodes);
    }

    public TreeNode(List<ITreeNode<T>> childNodes) {
        setChildNodes(childNodes);
    }

    public void setParentNode(ITreeNode<T> parent) {
        parentNode = parent;
    }

    public void setChildNodes(List<ITreeNode<T>> childNodes) {
        if (childNodes == null) {
            this.childNodes.clear();
        } else {
            this.childNodes.addAll(childNodes);
        }
    }

    public void addChild(ITreeNode<T> child) {
        if (child != null) {
            childNodes.add(child);
            child.setParentNode(this);
        }
    }

    public ITreeNode<T> getParentNode() {
        return parentNode;
    }

    public List<ITreeNode<T>> getChildNodes() {
        return childNodes;
    }

    public T getContent() {
        return content;
    }

    @Override
    public String toString() {
        String result = "";

        if (childNodes != null) {
            for (ITreeNode<T> i : childNodes) {
                result += i.getContent();
                System.out.println(i.getContent());
                i.toString();
            }
        }

        return result;
    }

    public boolean containsContent(T content, ITreeNode<T> node) {
        boolean test = false;
        node = null;

        for (ITreeNode<T> t : childNodes) {
            if (t.getContent().equals(content)) {
                test = true;
                node = this;
                break;
            } else {
                test = t.containsContent(content, node);
                if (test) break;
            }
        }

        return test;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public boolean isSubClassOf(ITreeNode<T> parent, boolean direct) {
        if (parentNode == null) return false;
        if (parentNode.equals(parent)) return true;
        if (!direct) return parentNode.isSubClassOf(parent, false);
        return false;
    }
    public ITreeNode<T> getChild(int index) {
        if (index < 0 || index > (childNodes.size() - 1)) return null;
        return childNodes.get(index);
    }

    public ITreeNode<T> getRoot() {
        ITreeNode<T> currentNode = this;
        ITreeNode<T> parent = null;
        while ((parent = currentNode.getParentNode()) != null) currentNode = parent;
        return currentNode;
    }

    public int getHeigthToLastLeaf() {
        if (getChildNodes().size() == 0)
            return 0;
        int height = 0;
        for (ITreeNode<T> child : getChildNodes()) {
            height = Math.max(height, child.getHeigthToLastLeaf());
        }
        return height + 1;
    }

}
