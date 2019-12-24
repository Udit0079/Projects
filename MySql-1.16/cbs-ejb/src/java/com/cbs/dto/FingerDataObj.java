/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

/**
 *
 * @author root
 */
public class FingerDataObj {

    int ErrorCode;

    String ErrorDescription;

    String BitmapData = null;

    int Quality = 0;

    int Nfiq = 0;

   // String RawData = null;

    byte[] IsoTemplate = null;

    //String IsoImage = null;

   // String AnsiTemplate = null;

   // String WsqImage = null;

    public String getBitmapData() {
        return BitmapData;
    }

    public void setBitmapData(String BitmapData) {
        this.BitmapData = BitmapData;
    }

    public int getQuality() {
        return Quality;
    }

    public void setQuality(int Quality) {
        this.Quality = Quality;
    }

    public int getNfiq() {
        return Nfiq;
    }

    public void setNfiq(int Nfiq) {
        this.Nfiq = Nfiq;
    }

    public byte[] getIsoTemplate() {
        return IsoTemplate;
    }

    public void setIsoTemplate(byte[] IsoTemplate) {
        this.IsoTemplate = IsoTemplate;
    }

    
//    public String getRawData() {
//        return RawData;
//    }
//
//    public void setRawData(String RawData) {
//        this.RawData = RawData;
//    }
//
//    
//
//    public String getIsoImage() {
//        return IsoImage;
//    }
//
//    public void setIsoImage(String IsoImage) {
//        this.IsoImage = IsoImage;
//    }
//
//    public String getAnsiTemplate() {
//        return AnsiTemplate;
//    }
//
//    public void setAnsiTemplate(String AnsiTemplate) {
//        this.AnsiTemplate = AnsiTemplate;
//    }
//
//    public String getWsqImage() {
//        return WsqImage;
//    }
//
//    public void setWsqImage(String WsqImage) {
//        this.WsqImage = WsqImage;
//    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getErrorDescription() {
        return ErrorDescription;
    }

    public void setErrorDescription(String ErrorDescription) {
        this.ErrorDescription = ErrorDescription;
    }
}