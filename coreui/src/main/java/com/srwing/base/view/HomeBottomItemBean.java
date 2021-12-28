package com.srwing.base.view;


public class HomeBottomItemBean {

    public HomeBottomItemBean(String reTopIcon, String normalIcon, String selectIcon, String text, String textColor) {
        this.reTopIcon = reTopIcon;
        this.normalIcon = normalIcon;
        this.selectIcon = selectIcon;
        this.text = text;
        this.textColor = textColor;
    }

    private boolean isReTop = false;

    private String reTopIcon;

    private String normalIcon;

    private String selectIcon;

    private String text;

    private String textColor;

    public String getNormalIcon() {
        return normalIcon;
    }

    public void setNormalIcon(String normalIcon) {
        this.normalIcon = normalIcon;
    }

    public String getSelectIcon() {
        return selectIcon;
    }

    public void setSelectIcon(String selectIcon) {
        this.selectIcon = selectIcon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isReTop() {
        return isReTop;
    }

    public void setReTop(boolean reTop) {
        isReTop = reTop;
    }

    public String getReTopIcon() {
        return reTopIcon;
    }

    public void setReTopIcon(String reTopIcon) {
        this.reTopIcon = reTopIcon;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
