/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.ibl.util;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 *
 * @author root
 */
public class IBLNamespaceMapper extends NamespacePrefixMapper {

    private static final String IBL_PREFIX = "tem";
    private static final String IBL_URI = "http://tempuri.org/";

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if (namespaceUri.equals(IBL_URI)) {
            return IBL_PREFIX;
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[]{IBL_URI};
    }

}
