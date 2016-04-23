package com.smona.base.parse.live;

import java.io.File;

import com.smona.base.parse.live.api.ApiDynamic;
import com.smona.base.parse.live.common.Constants;
import com.smona.base.parse.live.common.FileOperator;
import com.smona.base.parse.live.common.ZipRead;
import com.smona.base.parse.live.mx.MXDynamic;
import com.smona.base.parse.live.vlife.VLifeDynamic;

public class Dynamic {
    private ApiDynamic mVLife;
    private ApiDynamic mMX;

    public Dynamic(String path) {
        initLive();
        initEvn(path);
    }

    private void initLive() {
        mVLife = new VLifeDynamic();
        mMX = new MXDynamic();
    }

    private void initEvn(String path) {
        String tempPath = path + Constants.PATH_TEMP;
        String targetPath = path + Constants.PATH_TARGET;
        deleteFolder(tempPath);
        deleteFolder(targetPath);

        mkdir(tempPath);
        mkdir(targetPath);
    }

    private static void deleteFolder(String path) {
        File dirFile = new File(path);
        FileOperator.deleteDirectory(dirFile);
    }

    protected File mkdir(String target) {
        File tempDir = new File(target);
        tempDir.mkdir();
        return tempDir;
    }

    public void filterZip(String path) {
        String fileSource = path + Constants.PATH_SOURCE;
        File dir = new File(fileSource);
        String[] files = dir.list();
        if (files == null) {
            return;
        }
        for (String file : files) {
            if (!file.endsWith(Constants.ZIP_FLAG)) {
                continue;
            }
            boolean isVLife = ZipRead.isLive(fileSource + "/" + file,
                    Constants.VLIFE_FLAG);
            if (isVLife) {
                mVLife.add(file);
            } else {
                mMX.add(file);
            }
        }
    }

    public void generalStandardLive(String path) {
        processLive(mVLife, path);
        processLive(mMX, path);
    }

    private void processLive(ApiDynamic live, String path) {
        live.unzip(path);
        live.copyFile(path);
        live.modifyImage(path);
        live.zip(path);
    }
}
