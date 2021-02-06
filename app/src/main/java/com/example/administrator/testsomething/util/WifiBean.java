package com.example.administrator.testsomething.util;

public class WifiBean {
    public int ImageId; //图片
    public String Top;  //第一行的数据
    public String Bottom;  //第二行的数据

    public WifiBean(int imageId, String top, String bottom) {  //构造方法
        ImageId = imageId;
        Top = top;
        Bottom = bottom;
    }
}
