package com.qcz.qmplatform.common.bean;

import java.io.Serializable;
import java.util.List;

public class Tree implements Serializable {

    private String id;

    private String name;

    private String parentId;

    private List<? extends Tree> childes;

    private boolean hasParent;

    private boolean hasChild;

    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<? extends Tree> getChildes() {
        return childes;
    }

    public void setChildes(List<? extends Tree> childes) {
        this.childes = childes;
    }

    public boolean isHasParent() {
        return hasParent;
    }

    public void setHasParent(boolean hasParent) {
        this.hasParent = hasParent;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }
}
