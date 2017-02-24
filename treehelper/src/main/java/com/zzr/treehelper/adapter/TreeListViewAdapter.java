package com.zzr.treehelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.zzr.treehelper.model.Node;
import com.zzr.treehelper.utils.TreeHelper;

import java.util.List;

/**
 * Created by hasee on 2017/2/23.
 */

public abstract class TreeListViewAdapter<T> extends BaseAdapter {
    protected Context mContext;
    /**
     * 储存可见的Node
     */
    protected List<Node> mNodes;
    /**
     * 储存所有的Node
     */
    protected List<Node> mAllNodes;
    protected LayoutInflater mLayoutInflater;

    public TreeListViewAdapter() {
    }

    /**
     * 点击的回调接口
     */
    private OnTreeNodeClickListener onTreeNodeClickListener;

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    public interface OnTreeNodeClickListener {
        void onTreeNodeClick(Node node, int position);
    }

    /**
     * @param mTree
     * @param context
     * @param data
     * @param defaultLevel 默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public TreeListViewAdapter(ListView mTree, Context context, List<T> data,
                               int defaultLevel) throws IllegalArgumentException, IllegalAccessException {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(context);
        /**
         * 对所有的Node进行排序
         */
        mAllNodes = TreeHelper.getSortsNodes(data, defaultLevel);
        /**
         * 过滤可见的Node
         */
        mNodes = TreeHelper.filterVisibleNodes(mAllNodes);
        /**
         * 设置节点点击时，可以展开以及关闭；并且将ItemClick事件继续往外公布
         */
        mTree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                expandOrCollapse(i);
                if (onTreeNodeClickListener != null) {
                    onTreeNodeClickListener.onTreeNodeClick(mNodes.get(i), i);
                }
            }
        });
    }


    /**
     * 展开或关闭某节点
     *
     * @param i
     */
    private void expandOrCollapse(int i) {
        Node node = mNodes.get(i);
        if (node != null) {
            if (!node.isLeaf()) {
                node.setExpand(!node.isExpand());
                mNodes = TreeHelper.filterVisibleNodes(mAllNodes);
                notifyDataSetChanged();//刷新视图
            }
        }

    }

    @Override
    public int getCount() {
        return mNodes.size();
    }

    @Override
    public Object getItem(int i) {
        return mNodes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Node node = mNodes.get(i);
        view = getConvertView(node, i, view, viewGroup);
        view.setPadding(node.getLevel() * 30, 3, 3, 3);
        return view;
    }

    public abstract View getConvertView(Node node, int position, View convertView, ViewGroup viewGroup);

}
