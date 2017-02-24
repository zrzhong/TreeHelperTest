package com.zzr.treehelpertest.bean;

import com.zzr.treehelper.annotation.NodeId;
import com.zzr.treehelper.annotation.NodeName;
import com.zzr.treehelper.annotation.NodePid;

/**
 * Created by hasee on 2017/2/23.
 */

public class FileBean {
    @NodeId
    private int id;
    @NodePid
    private int pid;
    @NodeName
    private String label;

    public FileBean(int id, int pid, String label) {
        this.id = id;
        this.pid = pid;
        this.label = label;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
