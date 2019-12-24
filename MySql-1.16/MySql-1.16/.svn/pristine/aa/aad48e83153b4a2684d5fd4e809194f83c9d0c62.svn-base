package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.txn.OtherAuthorizationManagementFacadeRemote;
import com.cbs.web.pojo.txn.SignaturesAuthorizationGrid;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class SignaturesAuthorizationManaged extends BaseBean {

    SignaturesAuthorizationGrid signAuthGrid;
    List<SignaturesAuthorizationGrid> signAuthGridList;
    String errorMsg;
    int currentRow;
    String custName;
    String accOpenDate;
    String introAccNo;
    String signScannedBy;
    String accInstr;
    String imageData;
    String acNo;
    String custId;
    String displayCustId;
    //String displayDd;
    private String function;
    private String imgType;
    private List<SelectItem> functionList;
    private List<SelectItem> imgTypeList;

    private final String jndiHomeName = "OtherAuthorizationManagementFacade";
    private OtherAuthorizationManagementFacadeRemote otherAuthRemote = null;

    private final String jndiFtsName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsFacade = null;

    public SignaturesAuthorizationGrid getSignAuthGrid() {
        return signAuthGrid;
    }

    public void setSignAuthGrid(SignaturesAuthorizationGrid signAuthGrid) {
        this.signAuthGrid = signAuthGrid;
    }

    public List<SignaturesAuthorizationGrid> getSignAuthGridList() {
        return signAuthGridList;
    }

    public void setSignAuthGridList(List<SignaturesAuthorizationGrid> signAuthGridList) {
        this.signAuthGridList = signAuthGridList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getAccInstr() {
        return accInstr;
    }

    public void setAccInstr(String accInstr) {
        this.accInstr = accInstr;
    }

    public String getAccOpenDate() {
        return accOpenDate;
    }

    public void setAccOpenDate(String accOpenDate) {
        this.accOpenDate = accOpenDate;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIntroAccNo() {
        return introAccNo;
    }

    public void setIntroAccNo(String introAccNo) {
        this.introAccNo = introAccNo;
    }

    public String getSignScannedBy() {
        return signScannedBy;
    }

    public void setSignScannedBy(String signScannedBy) {
        this.signScannedBy = signScannedBy;
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

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public List<SelectItem> getImgTypeList() {
        return imgTypeList;
    }

    public void setImgTypeList(List<SelectItem> imgTypeList) {
        this.imgTypeList = imgTypeList;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getDisplayCustId() {
        return displayCustId;
    }

    public void setDisplayCustId(String displayCustId) {
        this.displayCustId = displayCustId;
    }

    
    public SignaturesAuthorizationManaged() {
        try {
            otherAuthRemote = (OtherAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsFacade = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiFtsName);
            functionList = new ArrayList<>();
            functionList.add(new SelectItem("", "--Select--"));
            functionList.add(new SelectItem("A", "Authorize"));
            functionList.add(new SelectItem("V", "View"));

            imgTypeList = new ArrayList<>();
            imgTypeList.add(new SelectItem("", "--Select--"));
            imgTypeList.add(new SelectItem("S", "Signature"));
            imgTypeList.add(new SelectItem("C", "CKYCR Image"));
            setFunction("");
            setImgType("");
            setDisplayCustId("none");
        } catch (ServiceLocatorException e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void displayFields() {
        if (function.equals("")) {
            setErrorMsg("Please select the Function");
        }
        if (function.equals("A")) {
            setDisplayCustId("none");
        } else {
            setDisplayCustId("");
        }
    }

    public void getCustomerImgDetails() {
        try {
            signAuthGridList = new ArrayList<>();
            if (function.equals("")) {
                throw new ApplicationException("Please select the Function");
            }
            if (imgType.equals("")) {
                throw new ApplicationException("Please select the Classification");
            }
            List imgList = otherAuthRemote.getCustImgeDetails(imgType, custId);
            if (imgList.isEmpty()) {
                throw new ApplicationException("CKYC Image / Signature was found for this customer.");
            }
            this.setErrorMsg("");
            
            SignaturesAuthorizationGrid signAuthItem = null;
            for (int i = 0; i < imgList.size(); i++) {
                signAuthItem = new SignaturesAuthorizationGrid();
                Vector vecForUnAuthAcc = (Vector) imgList.get(i);
                signAuthItem.setAccountNo(vecForUnAuthAcc.get(0).toString());
                signAuthItem.setSerialNo(vecForUnAuthAcc.get(1).toString());

                signAuthItem.setEnterBy(vecForUnAuthAcc.get(2).toString());
                signAuthItem.setImgCode(vecForUnAuthAcc.get(3).toString());
                signAuthItem.setImgName(vecForUnAuthAcc.get(4).toString());
                signAuthGridList.add(signAuthItem);
            }
        } catch (ApplicationException ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void getUnauthorizedAcctNoLoadGrid() {
        try {
            signAuthGridList = new ArrayList<>();
            if (function.equals("")) {
                throw new ApplicationException("Please Select Function");
            }
            if (imgType.equals("")) {
                throw new ApplicationException("Please Select Image Type");
            }
            if (function.equals("A")) {
                List listForUnauthAccNo = otherAuthRemote.getUnauthorizedAcctNo(getOrgnBrCode(), imgType);
                if (listForUnauthAccNo.isEmpty()) {
                    throw new ApplicationException("No unauthorized Signature/CKYC Image was found.");
                }
                this.setErrorMsg("");
                
                SignaturesAuthorizationGrid signAuthItem = null;
                for (int i = 0; i < listForUnauthAccNo.size(); i++) {
                    signAuthItem = new SignaturesAuthorizationGrid();
                    Vector vecForUnAuthAcc = (Vector) listForUnauthAccNo.get(i);
                    signAuthItem.setAccountNo(vecForUnAuthAcc.get(0).toString());
                    signAuthItem.setSerialNo(vecForUnAuthAcc.get(1).toString());

                    signAuthItem.setEnterBy(vecForUnAuthAcc.get(2).toString());
                    signAuthItem.setImgCode(vecForUnAuthAcc.get(3).toString());
                    signAuthItem.setImgName(vecForUnAuthAcc.get(4).toString());
                    signAuthGridList.add(signAuthItem);
                }
            }
        } catch (Exception e) {
            signAuthGridList = new ArrayList<>();
            this.setErrorMsg(e.getMessage());
        }
    }

    public void setRowValues() {
        try {
            switch (getImgType()) {
                case "S":
                    acNo = signAuthGrid.getAccountNo();
                    getSignatureDetail();
                    List listForDetails = otherAuthRemote.getDetailsOfScannedPerson(signAuthGrid.getAccountNo());
                    if (listForDetails.isEmpty()) {
                        throw new ApplicationException("Details not found for this account no.");
                    }
                    this.setErrorMsg("");
                    Vector vecForDetails = (Vector) listForDetails.get(0);
                    String openDate = vecForDetails.get(1).toString();
                    openDate = openDate.substring(6) + "/" + openDate.substring(4, 6) + "/" + openDate.substring(0, 4);
                    this.setAccInstr(vecForDetails.get(3).toString());

                    this.setAccOpenDate(openDate);
                    this.setCustName(vecForDetails.get(0).toString());
                    this.setIntroAccNo(vecForDetails.get(2).toString());
                    this.setSignScannedBy(vecForDetails.get(4).toString());
                    break;
                case "C":
                    acNo = signAuthGrid.getAccountNo() + signAuthGrid.getImgCode();
                    getCkycImgDetail();
                    listForDetails = otherAuthRemote.getCustomerDetails(signAuthGrid.getAccountNo(), signAuthGrid.getImgCode());
                    if (listForDetails.isEmpty()) {
                        throw new ApplicationException("Details not found for this account no.");
                    }
                    this.setErrorMsg("");
                    vecForDetails = (Vector) listForDetails.get(0);
                    this.setCustName(vecForDetails.get(0).toString());
                    this.setSignScannedBy(vecForDetails.get(1).toString());
                    this.setAccInstr("");
                    this.setAccOpenDate("");
                    this.setAccOpenDate("");
                    this.setIntroAccNo("");
                    break;
            }
        } catch (ApplicationException e) {
            this.setAccInstr("");
            this.setAccOpenDate("");
            this.setCustName("");
            this.setIntroAccNo("");
            this.setSignScannedBy("");
            this.setErrorMsg(e.getMessage());
        }
    }

    public void clickOnAuthorize() {
        try {
            if (signAuthGrid.getEnterBy().equalsIgnoreCase(getUserName())) {
                throw new ApplicationException("You are not authorized person to authorized this entry");
            }
            String rs = "";
            switch (getImgType()) {
                case "S":
                    if ((this.getAccInstr() == null || this.getAccInstr().equalsIgnoreCase("")) && (this.getCustName() == null || this.getCustName().equalsIgnoreCase(""))
                            && (this.accOpenDate == null || this.accOpenDate.equalsIgnoreCase("")) && (this.getIntroAccNo() == null || this.getIntroAccNo().equalsIgnoreCase(""))
                            && (this.getSignScannedBy() == null || this.getSignScannedBy().equalsIgnoreCase(""))) {
                        throw new ApplicationException("Invalid account for authorization.");
                    }
                    this.setErrorMsg("");

                    rs = otherAuthRemote.signAuthorization(signAuthGrid.getAccountNo(), signAuthGrid.getSerialNo(), getUserName());
                    break;
                case "C":
                    if (this.getCustName() == null || this.getCustName().equalsIgnoreCase("")) {
                        throw new ApplicationException("Invalid account for authorization.");
                    }
                    this.setErrorMsg("");
                    rs = otherAuthRemote.ckycImgAuthorization(signAuthGrid.getAccountNo(), signAuthGrid.getSerialNo(), signAuthGrid.getImgCode(), getUserName());
                    break;
            }
            if (rs.equalsIgnoreCase("true")) {
                this.setAccInstr("");
                this.setCustName("");
                this.setAccOpenDate("");
                this.setIntroAccNo("");
                this.setSignScannedBy("");
                this.setCustId("");
                this.setAcNo("");
                this.imageData = null;
                getUnauthorizedAcctNoLoadGrid();
                if (getImgType().equals("S")) {
                    this.setErrorMsg("Signature successfully authorized");
                } else {
                    this.setErrorMsg("Ckyc Image successfully authorized");
                }
            } else {
                this.setErrorMsg(rs);
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
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

    public void getCkycImgDetail() {
        try {
            String filePath = "/" + ftsFacade.getBankCode() + "/CI/" + signAuthGrid.getAccountNo() + "/" + signAuthGrid.getImgCode() + ".xml";
            imageData = CbsUtil.readImageFromXMLFile(new File(filePath));
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void getSignatureDetail() {
        try {
            String signature = otherAuthRemote.getSignatureDetails(signAuthGrid.getAccountNo());
            if (!signature.equalsIgnoreCase("Signature not found")) {
                //imageData = signature;
                String imageCode = signature.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(signAuthGrid.getAccountNo());
                String filePath = directoryPath + encryptAcno + ".xml";
                imageData = CbsUtil.readImageFromXMLFile(new File(filePath));
            } else {
                imageData = null;
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setAccInstr("");
        this.setAccOpenDate("");
        this.setCustName("");
        this.setErrorMsg("");
        this.setIntroAccNo("");
        this.setSignScannedBy("");
        setCustId("");
        this.setAcNo("");
        this.imageData = null;
        setFunction("");
        setImgType("");
        signAuthGridList = new ArrayList<>();
        signAuthGrid = null;
        setDisplayCustId("none");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    public void deleteUnauthorizedSignature() {
        try {
            if (function.equals("V")) {
                throw new ApplicationException("In case of View function you can not delete the image");
            }
            String deleteResult = "";
            switch (getImgType()) {
                case "S":
                    deleteResult = otherAuthRemote.deleteUnauthSign(signAuthGrid.getAccountNo(), getUserName());
                    break;
                case "C":
                    deleteResult = otherAuthRemote.deleteUnauthCkycImg(signAuthGrid.getAccountNo(), getUserName(), signAuthGrid.getImgCode());
                    break;
            }
            if (!deleteResult.equalsIgnoreCase("true")) {
                this.setErrorMsg(deleteResult);
                return;
            } else {
                getUnauthorizedAcctNoLoadGrid();
                switch (getImgType()) {
                    case "S":
                        this.setErrorMsg("Signature has been deleted successfully");
                        break;
                    case "C":
                        this.setErrorMsg("Ckyc Image has been deleted successfully");
                        break;
                }
                this.setAccInstr("");
                this.setCustName("");
                this.setAccOpenDate("");
                this.setIntroAccNo("");
                this.setSignScannedBy("");
                this.setAcNo("");
                this.imageData = null;
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        }
    }
}
