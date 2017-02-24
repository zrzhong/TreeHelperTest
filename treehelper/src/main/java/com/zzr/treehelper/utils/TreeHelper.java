package com.zzr.treehelper.utils;

import com.zzr.treehelper.R;
import com.zzr.treehelper.annotation.NodeId;
import com.zzr.treehelper.annotation.NodeName;
import com.zzr.treehelper.annotation.NodePid;
import com.zzr.treehelper.model.Node;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/2/23.
 */

public class TreeHelper {
    /**
     * 传入普通的bean，转化为Node
     *
     * @param data
     * @param defaultLevel
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static <T> List<Node> getSortsNodes(List<T> data, int defaultLevel)
            throws IllegalAccessException, IllegalArgumentException {
        List<Node> result = new ArrayList<>();

        //将用户数据转化为List<Node>以及设置Node间关系
        List<Node> nodes = convertData2Nodes(data);

        //拿到跟节点
        List<Node> rootNode = getRootNodes(nodes);

        //排序
        for (Node n : rootNode) {
            addNode(result, n, defaultLevel, 1);
        }
        return result;
    }

    //将一个节点上的数据都挂上去
    private static void addNode(List<Node> nodes, Node n, int defaultLevel, int currentLevel) {
        nodes.add(n);
        if (defaultLevel > currentLevel) {
            n.setExpand(true);
        }
        if (n.isLeaf()) {
            return;
        }

        for (Node node : n.getChildren()) {
            addNode(nodes, node, defaultLevel, currentLevel + 1);
        }
    }

    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> result = new ArrayList<>();
        for (Node n : nodes) {
            if (n.isRoot()) {
                result.add(n);
            }
        }
        return result;
    }

    private static <T> List<Node> convertData2Nodes(List<T> data) throws
            IllegalAccessException, IllegalArgumentException {
        //通过反射和注解转化为Node
        List<Node> result = new ArrayList<>();
        Node node = null;
        for (T t : data) {
            int id = -1;
            int pid = -1;
            String name = null;
            Class<? extends Object> cla = t.getClass();
            Field[] fields = cla.getDeclaredFields();
            for (Field f : fields) {
                if (f.getAnnotation(NodeId.class) != null) {
                    f.setAccessible(true);
                    id = f.getInt(t);
                }
                if (f.getAnnotation(NodePid.class) != null) {
                    f.setAccessible(true);
                    pid = f.getInt(t);
                }
                if (f.getAnnotation(NodeName.class) != null) {
                    f.setAccessible(true);
                    name = (String) f.get(t);
                }
                if (id != -1 && pid != -1 && name != null) {
                    break;
                }
            }
            node = new Node(id, pid, name);
            result.add(node);
        }
        /**
         * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
         */
        for (int i = 0; i < result.size(); i++) {
            Node m = result.get(i);
            for (int j = i + 1; j < result.size(); j++) {
                Node n = result.get(j);
                if (m.getId() == n.getPid()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                } else if (n.getId() == m.getPid()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                }
            }

        }
        //设置图片
        for (Node n : result) {
            setNodeIcon(n);
        }
        return result;
    }

    /**
     * 过滤出所有可见的Node
     *
     * @param data
     * @return
     */
    public static List<Node> filterVisibleNodes(List<Node> data) {
        List<Node> result = new ArrayList<>();
        for (Node n : data) {
            //如果是跟节点，或者父节点是展开状态
            if (n.isRoot() || n.isParentExpand()) {
                setNodeIcon(n);
                result.add(n);
            }
        }
        return result;
    }

    /**
     * 设置节点图标
     *
     * @param node
     */
    public static void setNodeIcon(Node node) {
        if (node.getChildren().size() > 0 && node.isExpand()) {
            node.setIcon(R.drawable.tree_ex);
        } else if (node.getChildren().size() > 0 && !node.isExpand()) {
            node.setIcon(R.drawable.tree_ec);
        } else {
            node.setIcon(-1);
        }
    }
}
