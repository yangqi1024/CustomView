package com.yq.customview.bean;

import android.support.annotation.NonNull;

/**
 * Description ...
 *
 * @author gsz
 * @create 2017/11/7
 * @since V1.0.1
 */
public class RectBean implements Comparable {
    private String title;
    private int count;

    public RectBean(String title, int count) {
        this.title = title;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return getCount() - ((RectBean) o).getCount();
    }
}
