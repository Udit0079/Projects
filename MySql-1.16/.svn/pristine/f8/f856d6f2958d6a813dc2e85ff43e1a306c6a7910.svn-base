/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.npci.h2h;

import java.util.regex.Pattern;
import org.apache.commons.vfs2.FileFilter;
import org.apache.commons.vfs2.FileSelectInfo;

public class NpciFileFilter implements FileFilter {

    public NpciFileFilter() {
    }

    @Override
    public boolean accept(FileSelectInfo fileInfo) {
        String filePattern = ".*" + "." + ".*";
        if (Pattern.matches(filePattern, fileInfo.getFile().getName().toString().toLowerCase())) {
            return true;
        }
        return false;
    }
}
