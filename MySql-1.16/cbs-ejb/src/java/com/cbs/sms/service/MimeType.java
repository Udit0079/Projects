package com.cbs.sms.service;

/**
 * @author
 *
 */
public enum MimeType {

    HTML("HTML", "text/html"), TEXT("Text", "text/plain");
    private String key;
    private String value;

    MimeType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }
}
