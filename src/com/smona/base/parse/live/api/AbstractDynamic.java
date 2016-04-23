package com.smona.base.parse.live.api;

import java.io.File;
import java.util.ArrayList;

import com.smona.base.parse.live.common.Constants;
import com.smona.base.parse.live.common.FileOperator;
import com.smona.base.parse.live.common.Logger;
import com.smona.base.parse.live.common.ZipFileAction;

public abstract class AbstractDynamic implements ApiDynamic {
    private ArrayList<String> mZipFiles = new ArrayList<String>();

    public void add(String path) {
        mZipFiles.add(path);
    }

    public void unzip(String path) {
        ZipFileAction action = new ZipFileAction();
        String targetPath = null;
        for (String file : mZipFiles) {
            targetPath = path
                    + Constants.PATH_TEMP
                    + "/"
                    + file.substring(0,
                            file.length() - Constants.ZIP_FLAG.length());
            try {
                action.unZip(path + Constants.PATH_SOURCE + "/" + file,
                        targetPath);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.printDetail(e.getMessage());
            }
        }
    }

    public void copyFile(String path) {
        String tempPath = null;
        for (String file : mZipFiles) {
            tempPath = path
                    + Constants.PATH_TEMP
                    + "/"
                    + file.substring(0,
                            file.length() - Constants.ZIP_FLAG.length());
            FileOperator.fileChannelCopy(new File(path + "/" + getAddFile()),
                    tempPath + getAddFile());
        }
    }

    public void modifyImage(String path) {
        if (noNeedModify()) {
            return;

        }
        String targetPath = null;
        for (String file : mZipFiles) {
            targetPath = path
                    + Constants.PATH_TEMP
                    + "/"
                    + file.substring(0,
                            file.length() - Constants.ZIP_FLAG.length());

            renameJpg(targetPath, Constants.BITMAP_FILE_NAME_1,
                    Constants.BITMAP_FILE_NAME_4);
            renameJpg(targetPath, Constants.BITMAP_FILE_NAME_2,
                    Constants.BITMAP_FILE_NAME_3);
        }
    }

    private void renameJpg(String dir, String jpgSource, String jpgTarget) {
        String result = searchFile(dir, jpgSource);
        if (result != null) {
            FileOperator.fileChannelCopy(new File(result),
                    result.substring(0, result.length() - 5) + jpgTarget);
            FileOperator.deleteFile(result);
        }
    }

    private String searchFile(String dirName, String suffix) {
        String result = null;
        File dir = new File(dirName);
        File[] files = dir.listFiles();
        System.out.println(dir.getAbsolutePath());
        System.out.println(files);
        if (files == null) {
            return result;
        }
        for (File file : files) {
            System.out.println(file.getName());
            if (file.isFile()) {
                if (file.getName().endsWith(suffix)) {
                    result = file.getAbsolutePath();
                    break;
                }
            } else {
                result = searchFile(file.getAbsolutePath(), suffix);
                if (result != null) {
                    break;
                }
            }
        }
        return result;
    }

    public void zip(String path) {
        ZipFileAction action = new ZipFileAction();
        String tempPath = null;
        String targetPath = null;
        for (String file : mZipFiles) {
            tempPath = path
                    + Constants.PATH_TEMP
                    + "/"
                    + file.substring(0,
                            file.length() - Constants.ZIP_FLAG.length());
            targetPath = path + Constants.PATH_TARGET + "/" + file;
            try {
                action.zip(tempPath, targetPath);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.printDetail(e.getMessage());
            }
        }
    }

    protected abstract String getAddFile();

    protected abstract boolean noNeedModify();
}
