package com.zzr.treehelper.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/2/23.
 */

public class Node {
    private int id;
    /**
     * 根节点pid为0
     */
    private int pid = 0;
    private String name;
    /**
     * 父Node
     */
    private Node parent;
    /**
     * 下一级的子Node
     */
    private List<Node> children = new ArrayList<>();
    /**
     * 是否展开
     */
    private boolean isExpand = false;
    /**
     * 当前的级别
     */
    private int level;
    private int icon;

    public Node() {

    }

    public Node(int id, int pid, String name) {
        super();
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    //是否根节点
    public boolean isRoot() {
        return parent == null;
    }

    //是否叶子节点
    public boolean isLeaf() {
        return children.size() == 0;
    }

    //父节点是否展开
    public boolean isParentExpand() {
        if(parent == null){
            return false;
        }
        return parent.isExpand;
    }

    //设置展开
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            for (Node node : children) {
                node.setExpand(isExpand);
            }
        }
    }

    //获取等级
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

}
