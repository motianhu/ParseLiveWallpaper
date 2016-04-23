package com.smona.base.parse.live;

import com.smona.base.parse.live.common.Logger;

public class Main {

    public static void main(String[] args) {
        String encode = System.getProperty("file.encoding");
        println(encode);
        Logger.init();
        String path = System.getProperty("user.dir");
        println(path);
        action(path);
    }

    private static void action(String path) {
        Dynamic action = new Dynamic(path);
        action.filterZip(path);
        action.generalStandardLive(path);
    }

    private static void println(String msg) {
        Logger.printDetail(msg);
    }

}
