/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.TDLienMarkingFacadeRemote;
import com.cbs.facade.txn.OtherAuthorizationManagementFacadeRemote;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author mayank
 */
public class SigMerged extends BaseBean {
    
    String oldAcno;
    String custName;
    String accOpenDate;
    String signScannedBy;
    String newAcno;
    String imageData;
    String acNo;
    String errorMsg;
    
    private final String jndiHomeName = "OtherAuthorizationManagementFacade";
    private OtherAuthorizationManagementFacadeRemote otherAuthRemote = null;
    private final String jndiHomeNameTd = "TDLienMarkingFacade";
    private TDLienMarkingFacadeRemote isr = null;
    
    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getAccOpenDate() {
        return accOpenDate;
    }

    public void setAccOpenDate(String accOpenDate) {
        this.accOpenDate = accOpenDate;
    }

    public String getSignScannedBy() {
        return signScannedBy;
    }

    public void setSignScannedBy(String signScannedBy) {
        this.signScannedBy = signScannedBy;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public SigMerged(){
        try {
            otherAuthRemote = (OtherAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            isr = (TDLienMarkingFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTd);
            } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getLocalizedMessage());
        }
    }
    
    public void createSignature(OutputStream out, Object data) throws IOException {
        if (null == data) {
            return;
        }
        if (imageData != null) {
            byte[] sigBytes = Base64.decode(imageData);
            out.write(sigBytes);
        }
    }
    
    public void getOldAccountValues() {
        try {
            this.imageData = null;
            acNo = this.getOldAcno();
            getSignatureDetail();
            List listForDetails = otherAuthRemote.getDetailsOfScannedPerson(this.getOldAcno());
            if (listForDetails.isEmpty()) {
                this.setErrorMsg("No Details for this account no.");
                this.setAccOpenDate("");
                this.setCustName("");
                this.setSignScannedBy("");
                return;
            } else {
                this.setErrorMsg("");
                Vector vecForDetails = (Vector) listForDetails.get(0);
                String openDate = vecForDetails.get(1).toString();
                openDate = openDate.substring(6) + "/" + openDate.substring(4, 6) + "/" + openDate.substring(0, 4);
                this.setAccOpenDate(openDate);
                this.setCustName(vecForDetails.get(0).toString());
                this.setSignScannedBy(vecForDetails.get(4).toString());
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getLocalizedMessage());
        }
    }
    
    public void getSignatureDetail() {
        try {
            String signature;
            signature = otherAuthRemote.getMergSignatureDetails(acNo);
            if (!signature.equalsIgnoreCase("Signature not found")) {
                String imageCode = signature.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(acNo);
                String filePath = directoryPath + encryptAcno + ".xml";
                imageData = CbsUtil.readImageFromXMLFile(new File(filePath));
            } else {
                imageData = null;
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }
    
    public void cmdSaveClick(){
        try{
            if (imageData == null) {
                this.setErrorMsg("Image Not Exist To Merge");
                return;
            }
            
            if (this.oldAcno.equalsIgnoreCase("") || this.oldAcno == null || this.oldAcno.equalsIgnoreCase("null")) {
                this.setErrorMsg("Please Enter 12 Digit Old Account Number.");
                return;
            }
            
            if (!this.oldAcno.equalsIgnoreCase("") && this.oldAcno.length() < 12) {
                this.setErrorMsg("Please Enter 12 Digit Old Account Number.");
                return;
            }
            
            if (this.getNewAcno().equalsIgnoreCase("") || this.getNewAcno() == null || this.getNewAcno().equalsIgnoreCase("null")) {
                this.setErrorMsg("Please Enter 12 Digit New Account Number.");
                return;
            }
            if (!this.newAcno.equalsIgnoreCase("") && this.newAcno.length() < 12) {
                this.setErrorMsg("Please Enter 12 Digit New Account Number.");
                return;
            }
            
            String valAcno = otherAuthRemote.validateBothAccountNo(this.getOldAcno(), this.getNewAcno());
            if(!valAcno.equalsIgnoreCase("TRUE")){
                this.setErrorMsg(valAcno);
                return;
            }            
            
            try {
                File dir = null;
                String osName = System.getProperty("os.name");
                if (osName.equals("Linux")) {
                    dir = new File("/install/images");
                } else {
                    dir = new File("C:/images");
                }
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File file1 = new File(dir.getCanonicalPath() + File.separatorChar + this.getNewAcno() + ".jpg");
                /***Writing zip file into filesystem***/
                byte[] b = Base64.decode(imageData);
                FileOutputStream fos = new FileOutputStream(file1);
                fos.write(b);
                fos.close();
                /***End here***/
                String enterBy = this.getUserName();
                String result = isr.saveSingleImg(file1, enterBy);
                if (result.equalsIgnoreCase("true")) {
                    this.setErrorMsg("Image has been saved successfully");
                    delete(dir);
                } else {
                    this.setErrorMsg("Image was not saved");
                    return;
                }
            } catch (ApplicationException e) {
                this.setErrorMsg(e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                this.setErrorMsg(ex.getMessage());
            }            
        }catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }
    
    public void refreshForm() {
        this.setAccOpenDate("");
        this.setCustName("");
        this.setErrorMsg("");
        this.setSignScannedBy("");
        this.setOldAcno("");
        this.setNewAcno("");
        this.imageData = null;
    }
    
    public String exitForm() {
        refreshForm();
        return "case1";
    }
    
    public static void delete(File file) throws IOException {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                file.delete();
            } else {
                String files[] = file.list();
                for (String temp : files) {
                    File fileDelete = new File(file, temp);
                    delete(fileDelete);
                }
                if (file.list().length == 0) {
                    file.delete();
                }
            }
        } else {
            file.delete();
        }
    }
}