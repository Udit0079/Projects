/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author sipl
 */
public class IP2BranchCode {
    static class IPBranch {
        long mask;
        String branch;
        public IPBranch(long m, String br) {
            mask = m;
            branch = br;
        }
    }
    public IP2BranchCode(){

    };

    public IP2BranchCode(String propFlname) throws IOException {
        IPBrList = new ArrayList<IPBranch>();
        // read from property file and poplutate; Read prop file from
        Properties props = new Properties();
        InputStream incoming = null;
        try{
            incoming = new FileInputStream(propFlname);
            props.load(incoming);
        }
        catch (IOException e){
            e.printStackTrace();
        }finally{
            try {
                incoming.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }

      Enumeration propKeys = props.keys();
      while (propKeys.hasMoreElements()){
          String key = (String) propKeys.nextElement();
          InetAddress mask = InetAddress.getByName(key);
          byte[] maskArr = mask.getAddress();
          long longMask = toLong(maskArr);
          String brcode=props.getProperty(key).trim();
          IPBranch ip=new IPBranch(longMask, brcode);
          IPBrList.add(ip);
      }
    }
    // null ret when branch not found
    public String getBranch(InetAddress remoteIP) {
        long remote = toLong(remoteIP.getAddress());
        String brcode = null;
        for(int i=0; i< IPBrList.size(); i++){
            IPBranch ipBranch = IPBrList.get(i);
            //if(remote == (remote & ipBranch.mask) ){
            if(remote == ipBranch.mask ){
                brcode = ipBranch.branch;
                break;
            }
        }
        return brcode;
    }
    private long toLong(byte[] ip){
        long ret = 0L;
        for(int i=0; i<ip.length; i++){
            ret <<= 8;
            ret += ((long) (ip[i] & 255));
        }
        return ret;
    }
    private  ArrayList<IPBranch> IPBrList = null;
}
