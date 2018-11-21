package com.bondif.clothesshop.core;

public final class Tools {
    public static String getExtension(String path) {
        if(path.lastIndexOf(".") != -1 && path.lastIndexOf(".") != 0)
            return path.substring(path.lastIndexOf(".")+1);

        return null;
    }
}
