package com.zzr.treehelpertest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zzr.treehelper.adapter.TreeListViewAdapter;
import com.zzr.treehelper.model.Node;
import com.zzr.treehelpertest.bean.FileBean;

import java.util.List;

/**
 * Created by hasee on 2017/2/23.
 */

public class MyTreeAdapter extends TreeListViewAdapter<FileBean> {
    public MyTreeAdapter(ListView mTree, Context context, List<FileBean> data, int defaultLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(mTree, context, data, defaultLevel);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.tree_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (node.getIcon() == -1) {
            viewHolder.imageView.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.imageView.setImageResource(node.getIcon());
        }
        viewHolder.textView.setText(node.getName());
        return convertView;
    }

    private final class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
