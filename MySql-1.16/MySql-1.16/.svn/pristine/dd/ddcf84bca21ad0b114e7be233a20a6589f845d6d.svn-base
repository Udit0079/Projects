/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.neftrtgs;

import java.util.regex.Pattern;
import org.apache.commons.vfs2.FileFilter;
import org.apache.commons.vfs2.FileSelectInfo;

public class NeftFileFilter implements FileFilter {

    private String incomingValue;

    public NeftFileFilter(String value) {
        this.incomingValue = value;
    }

    @Override
    public boolean accept(FileSelectInfo fileInfo) {
        //(*.*),(Basti*.*),(*.csv)
        String filePattern = "";
        char starChar = '*';
        int starCount = 0;
        incomingValue = incomingValue.toLowerCase();
        for (int i = 0; i < incomingValue.length(); i++) {
            if (incomingValue.charAt(i) == starChar) {
                starCount++;
            }
        }
        System.out.println("STAR Count-->" + starCount);
        if (starCount == 2) {
            if (incomingValue.indexOf("*") != 0) {
                filePattern = incomingValue.substring(0, incomingValue.indexOf("*")) + ".*" + "." + ".*";
            } else {
                filePattern = ".*" + "." + ".*";
            }
        } else if (starCount == 1) {
            filePattern = ".*" + "." + incomingValue.substring(incomingValue.indexOf(".") + 1);
        }
        System.out.println("File Pattern Is-->" + filePattern);

        String fileName = fileInfo.getFile().getName().toString().toLowerCase();
        if (Pattern.matches(filePattern, fileName.substring(fileName.lastIndexOf("/") + 1))) {
            return true;
        }
        return false;
    }
}
