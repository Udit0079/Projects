/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.clg.h2h;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.vfs2.FileFilter;
import org.apache.commons.vfs2.FileSelectInfo;

public class ClgCtsFileFilter implements FileFilter {

    private List<String> micrList = new ArrayList<>();

    public ClgCtsFileFilter(List<String> micrs) {
        this.micrList = micrs;
    }

    @Override
    public boolean accept(FileSelectInfo fileInfo) {
        for (String micr : micrList) {
            String filePattern = ".*" + micr + ".*" + ".zip";
            if (Pattern.matches(filePattern, fileInfo.getFile().getName().toString().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
