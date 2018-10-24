package com.opopto.deploy.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    public static List<File> getFile(File f) {
        List<File> list = new ArrayList<File>();
        if (f != null) {
            if (f.isDirectory()) {
                Arrays.stream(f.listFiles()).forEach(file -> {
                    list.add(file);
                });
            } else {
                list.add(f);
            }
        }
        return list;
    }

}
