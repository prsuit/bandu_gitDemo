package com.DFHT.db.local.localenum;

/**
 * Created by jm on 2015/10/27.
 * 缓存到本地的类型
 * 文件,图片,实体类型
 */
public enum SaveType {
    IMAGE("image-cache"),FILE("file-cache"),ENTITY("data-cache");
    private String type;

    SaveType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
