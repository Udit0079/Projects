package com.cbs.email.service;

import com.cbs.sms.service.MimeType;

/**
 * @author 
 *
 */
public class MailTemplate {

	private static final long serialVersionUID = -6737966297718932298L;

	private String fromAddr;
	private String fromName;
	private MimeType mimeType;
	private String subject;
	private String body;
	private EmailType emailType;
	private String name;
	private String ccAddress;

	/**
	 * @param subject
	 * @param body
	 * @param emailType
	 * @param fromAddr
	 * @param fromName
	 * @param mimeType
	 * @param ccAddress
	 */
	public MailTemplate(String subject, String body, EmailType emailType, String fromAddr, String fromName,
		MimeType mimeType, String ccAddress) {
		this.subject = subject;
		this.body = body;
		this.emailType = emailType;
		this.fromAddr = fromAddr;
		this.fromName = fromName;
		this.mimeType = mimeType;
		this.ccAddress = ccAddress;
	}

	public MimeType getMimeType() {
		return mimeType;
	}

	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFromAddr() {
		return fromAddr;
	}

	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public EmailType getEmailType() {
		return emailType;
	}

	public void setEmailType(EmailType emailType) {
		this.emailType = emailType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCcAddress() {
		return ccAddress;
	}

	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}
}
