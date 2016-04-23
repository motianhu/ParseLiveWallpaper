package com.smona.base.parse.live.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipRead {
    public static boolean isLive(String file, String flagLive) {
        try {
            return readZipFile(file, flagLive);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("resource")
    private static boolean readZipFile(String zipPath, String flagLive)
            throws Exception {
        if (zipPath == null || !zipPath.endsWith(".zip")) {
            return false;
        }
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zipPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (zipFile == null) {
            return false;
        }

        @SuppressWarnings("unchecked")
        Enumeration<ZipEntry> enumerations = (Enumeration<ZipEntry>) zipFile
                .entries();
        ZipEntry zipEntry = null;
        while (enumerations.hasMoreElements()) {
            zipEntry = enumerations.nextElement();
            String fileName = zipEntry.getName();
            if (!zipEntry.isDirectory() && fileName.startsWith(flagLive)) {
                return true;
            }
        }
        return false;
    }

    public static void addFileNoUnzip(String zipPath, String addFile) {
        if (zipPath == null || !zipPath.endsWith(".zip")) {
            return;
        }
        ZipOutputStream append = null;
        FileOutputStream fo = null;

        try {
            fo = new FileOutputStream(zipPath);
            append = new ZipOutputStream(fo);
            // now append some extra content
            ZipEntry e = new ZipEntry(addFile);
            append.putNextEntry(e);
            append.closeEntry();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (append != null) {
                try {
                    append.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fo != null) {
                try {
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static final byte[] BUFFER = new byte[4096 * 1024];

    /**
     * copy input to output stream - available in several StreamUtils or Streams
     * classes
     */
    public static void copy(InputStream input, OutputStream output)
            throws IOException {
        int bytesRead;
        while ((bytesRead = input.read(BUFFER)) != -1) {
            output.write(BUFFER, 0, bytesRead);
        }
    }
}
