/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.share;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.web.pojo.ho.ShareTransferLeftGrid;
import com.cbs.web.pojo.ho.ShareTransferRightGrid;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zeeshan Waris
 */
public class ShareTransfer extends BaseBean{

    ShareTransferFacadeRemote remoteObject;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    private String message;
    private String folioNoLeft;
    private String folioNoRight;
    private List<ShareTransferLeftGrid> shareTransferLeft;
    private ShareTransferLeftGrid currentItem = new ShareTransferLeftGrid();
    private List<SelectItem> transfereeNoType;
    private List<SelectItem> transferorNoType;
    private List<ShareTransferRightGrid> shareTransferRight;
    private ShareTransferRightGrid currentItem1 = new ShareTransferRightGrid();
    private String fromCertificate;
    private String sharesToBeTransferred;
    private String shareNoFrom;
    private String shareNoTo;
    private Date transferDate = new Date();
    private String transfereeCertNo;
    private String transfereeCertNoTxt;
    private String transferorCertNo;
    private String transferorCertNoTxt;
    private String remarks;
    private String folioRightName;
    private String folioLeftName;
    private String fullFolioNotransferor;
    private String fullFolioNoTransferee;
    private boolean transfereeCertNoTxtDisable;
    private boolean transferorCertNoDisable;
    private boolean transferorCertNoTxtDisable;
    private boolean saveDisable;
    private boolean fromDisable;
    private boolean toDisable;
    private String checkAuth;
    private String checkCertificate;
    private String folioNoLeftShow, folioNoRightShow;
    List resultListCheck = new ArrayList();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public String getFolioNoRightShow() {
        return folioNoRightShow;
    }

    public void setFolioNoRightShow(String folioNoRightShow) {
        this.folioNoRightShow = folioNoRightShow;
    }

    public String getFolioNoLeftShow() {
        return folioNoLeftShow;
    }

    public void setFolioNoLeftShow(String folioNoLeftShow) {
        this.folioNoLeftShow = folioNoLeftShow;
    }

    public boolean isFromDisable() {
        return fromDisable;
    }

    public void setFromDisable(boolean fromDisable) {
        this.fromDisable = fromDisable;
    }

    public boolean isToDisable() {
        return toDisable;
    }

    public void setToDisable(boolean toDisable) {
        this.toDisable = toDisable;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public boolean isTransfereeCertNoTxtDisable() {
        return transfereeCertNoTxtDisable;
    }

    public void setTransfereeCertNoTxtDisable(boolean transfereeCertNoTxtDisable) {
        this.transfereeCertNoTxtDisable = transfereeCertNoTxtDisable;
    }

    public boolean isTransferorCertNoDisable() {
        return transferorCertNoDisable;
    }

    public void setTransferorCertNoDisable(boolean transferorCertNoDisable) {
        this.transferorCertNoDisable = transferorCertNoDisable;
    }

    public boolean isTransferorCertNoTxtDisable() {
        return transferorCertNoTxtDisable;
    }

    public void setTransferorCertNoTxtDisable(boolean transferorCertNoTxtDisable) {
        this.transferorCertNoTxtDisable = transferorCertNoTxtDisable;
    }

    public List<SelectItem> getTransferorNoType() {
        return transferorNoType;
    }

    public void setTransferorNoType(List<SelectItem> transferorNoType) {
        this.transferorNoType = transferorNoType;
    }

    public String getFolioLeftName() {
        return folioLeftName;
    }

    public void setFolioLeftName(String folioLeftName) {
        this.folioLeftName = folioLeftName;
    }

    public String getFolioRightName() {
        return folioRightName;
    }

    public void setFolioRightName(String folioRightName) {
        this.folioRightName = folioRightName;
    }

    public String getFolioNoLeft() {
        return folioNoLeft;
    }

    public void setFolioNoLeft(String folioNoLeft) {
        this.folioNoLeft = folioNoLeft;
    }

    public String getFolioNoRight() {
        return folioNoRight;
    }

    public void setFolioNoRight(String folioNoRight) {
        this.folioNoRight = folioNoRight;
    }

    public ShareTransferLeftGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ShareTransferLeftGrid currentItem) {
        this.currentItem = currentItem;
    }

    public ShareTransferRightGrid getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(ShareTransferRightGrid currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ShareTransferLeftGrid> getShareTransferLeft() {
        return shareTransferLeft;
    }

    public void setShareTransferLeft(List<ShareTransferLeftGrid> shareTransferLeft) {
        this.shareTransferLeft = shareTransferLeft;
    }

    public List<ShareTransferRightGrid> getShareTransferRight() {
        return shareTransferRight;
    }

    public void setShareTransferRight(List<ShareTransferRightGrid> shareTransferRight) {
        this.shareTransferRight = shareTransferRight;
    }

    public List<SelectItem> getTransfereeNoType() {
        return transfereeNoType;
    }

    public void setTransfereeNoType(List<SelectItem> transfereeNoType) {
        this.transfereeNoType = transfereeNoType;
    }

    public String getFromCertificate() {
        return fromCertificate;
    }

    public void setFromCertificate(String fromCertificate) {
        this.fromCertificate = fromCertificate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getShareNoFrom() {
        return shareNoFrom;
    }

    public void setShareNoFrom(String shareNoFrom) {
        this.shareNoFrom = shareNoFrom;
    }

    public String getShareNoTo() {
        return shareNoTo;
    }

    public void setShareNoTo(String shareNoTo) {
        this.shareNoTo = shareNoTo;
    }

    public String getSharesToBeTransferred() {
        return sharesToBeTransferred;
    }

    public void setSharesToBeTransferred(String sharesToBeTransferred) {
        this.sharesToBeTransferred = sharesToBeTransferred;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransfereeCertNo() {
        return transfereeCertNo;
    }

    public void setTransfereeCertNo(String transfereeCertNo) {
        this.transfereeCertNo = transfereeCertNo;
    }

    public String getTransfereeCertNoTxt() {
        return transfereeCertNoTxt;
    }

    public void setTransfereeCertNoTxt(String transfereeCertNoTxt) {
        this.transfereeCertNoTxt = transfereeCertNoTxt;
    }

    public String getTransferorCertNo() {
        return transferorCertNo;
    }

    public void setTransferorCertNo(String transferorCertNo) {
        this.transferorCertNo = transferorCertNo;
    }

    public String getTransferorCertNoTxt() {
        return transferorCertNoTxt;
    }

    public void setTransferorCertNoTxt(String transferorCertNoTxt) {
        this.transferorCertNoTxt = transferorCertNoTxt;
    }
    Date date = new Date();

    public ShareTransfer() {
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            transfereeNoType = new ArrayList<SelectItem>();
            transfereeNoType.add(new SelectItem("New Certificate", "New Certificate"));

            transferorNoType = new ArrayList<SelectItem>();
            transferorNoType.add(new SelectItem("New Certificate", "New Certificate"));
            setTransferorCertNoDisable(true);
            saveDisable = true;
            this.setMessage("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void transferorTableDetail(String folioNum) {
        try {
            String comp = "";
            int j = 0, shNo = 0;
            shareTransferLeft = new ArrayList<ShareTransferLeftGrid>();
            List resultList = new ArrayList();
            resultList = remoteObject.getTableData(folioNum);
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    if (!ele.get(0).toString().equalsIgnoreCase(comp) || (shNo != Integer.parseInt(ele.get(1).toString()))) {
                        comp = ele.get(0).toString();
                        shNo = Integer.parseInt(ele.get(1).toString());
                        if (j != 0) {
                            ShareTransferLeftGrid rd = new ShareTransferLeftGrid();
                            Vector ele1 = (Vector) resultList.get(i - 1);
                            rd.setCertNo(ele1.get(0).toString());
                            rd.setNoOfshares(j + "");
                            int a = Integer.parseInt(ele1.get(1).toString()) - j + 1;
                            rd.setNoFrom(a + "");
                            rd.setNoTo(ele1.get(1).toString());
                            Date test =  ymd.parse(ele1.get(2).toString());
                            String dateLeave = sdf.format(test);
                            rd.setIssueDt(dateLeave);
                            j = 0;
                            shareTransferLeft.add(rd);
                        }
                    }
                    if (ele.get(0).toString().equalsIgnoreCase(comp)) {
                        j++;
                        shNo = Integer.parseInt(ele.get(1).toString());
                        shNo++;
                    }
                    if (i == resultList.size() - 1) {
                        ShareTransferLeftGrid rd = new ShareTransferLeftGrid();
                        rd.setCertNo(ele.get(0).toString());
                        rd.setNoOfshares(j + "");
                        int a = Integer.parseInt(ele.get(1).toString()) - j + 1;
                        rd.setNoFrom(a + "");
                        rd.setNoTo(ele.get(1).toString());
                        Date test = ymd.parse(ele.get(2).toString());
                        String dateLeave = sdf.format(test);
                        rd.setIssueDt(dateLeave);
                        shareTransferLeft.add(rd);
                    }
                }
            } else {
                setMessage("No Shares present");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void transfereeTableDetail(String folioNum) {
        try {
            String comp = "";
            int j = 0, shNo = 0;

            shareTransferRight = new ArrayList<ShareTransferRightGrid>();
            List resultList = new ArrayList();
            resultList = remoteObject.getTableData(folioNum);
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    if (!ele.get(0).toString().equalsIgnoreCase(comp) || (shNo != Integer.parseInt(ele.get(1).toString()))) {
                        comp = ele.get(0).toString();
                        shNo = Integer.parseInt(ele.get(1).toString());
                        if (j != 0) {
                            ShareTransferRightGrid rd = new ShareTransferRightGrid();
                            Vector ele1 = (Vector) resultList.get(i - 1);
                            rd.setCertNo(ele1.get(0).toString());
                            rd.setNoOfshares(j + "");
                            int a = Integer.parseInt(ele1.get(1).toString()) - j + 1;
                            rd.setNoFrom(a + "");
                            rd.setNoTo(ele1.get(1).toString());
                            Date test = (Date) ele1.get(2);
                            String dateLeave = sdf.format(test);
                            rd.setIssueDt(dateLeave);
                            j = 0;
                            shareTransferRight.add(rd);
                        }
                    }
                    if (ele.get(0).toString().equalsIgnoreCase(comp)) {
                        j++;

                        shNo = Integer.parseInt(ele.get(1).toString());
                        shNo++;
                    }
                    if (i == resultList.size() - 1) {
                        ShareTransferRightGrid rd = new ShareTransferRightGrid();
                        rd.setCertNo(ele.get(0).toString());
                        rd.setNoOfshares(j + "");
                        int a = Integer.parseInt(ele.get(1).toString()) - j + 1;
                        rd.setNoFrom(a + "");
                        rd.setNoTo(ele.get(1).toString());
                        Date test = (Date) ele.get(2);
                        String dateLeave = sdf.format(test);
                        rd.setIssueDt(dateLeave);
                        shareTransferRight.add(rd);
                    }
                }
            } else {
                setMessage("No Shares present");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void transferorNameDetail() {
        setMessage("");
        if (folioNoLeftShow == null || folioNoLeftShow.equalsIgnoreCase("")) {
            setMessage("Please fill Folio No.");
            return;
        }
        if (folioNoLeftShow.length() < 12) {
            setMessage("Please fill 12 digit Folio No.");
            return;
        }
        try {
            folioNoLeft = ftsPostRemote.getNewAccountNumber(folioNoLeftShow);
            transferorNameDetailShow();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void transferorNameDetailShow() {
        setMessage("");
        try {
            if (folioNoLeft == null || folioNoLeft.equalsIgnoreCase("")) {
                setMessage("Please fill Transferor Folio No");
                return;
            }
            if (folioNoLeft.length() < 12) {
                this.setMessage("Please enter 12 digit Folio No");
                return;
            }
            fullFolioNotransferor = folioNoLeft;
            transferorTableDetail(folioNoLeft);
            List resultList = new ArrayList();
            resultList = remoteObject.transferorShareHolderName(fullFolioNotransferor);
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        setFolioLeftName(ele.get(0).toString());
                    }
                }
                if (message.equalsIgnoreCase("No Shares present")) {
                    setMessage("No Shares present");
                }
            } else {
                setMessage("Check The Transferor Folio NO");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void transfereeNameDetail() {
        setMessage("");
        if (folioNoRightShow == null || folioNoRightShow.equalsIgnoreCase("")) {
            setMessage("Please fill Folio No.");
            return;
        }
        if (folioNoRightShow.length() < 12) {
            setMessage("Please fill 12 digit Folio No.");
            return;
        }
        try {
            folioNoRight = ftsPostRemote.getNewAccountNumber(folioNoRightShow);
            transfereeNameDetailShow();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void transfereeNameDetailShow() {
        setMessage("");
        try {
            if (folioNoRight == null || folioNoRight.equalsIgnoreCase("")) {
                setMessage("Please fill Transferee Folio No");
                return;
            }
            if (folioNoRight.length() < 12) {
                this.setMessage("Please enter 12 digit Transferee Folio No");
                return;
            }
            fullFolioNoTransferee = folioNoRight;
            if (!fullFolioNotransferor.equals(fullFolioNoTransferee)) {
                transfereeTableDetail(fullFolioNoTransferee);
                List resultList = new ArrayList();
                resultList = remoteObject.transferorShareHolderName(fullFolioNoTransferee);
                if (resultList.size() > 0) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        if (ele.get(0) != null) {
                            setFolioRightName(ele.get(0).toString());
                        }
                    }
                    if (message.equalsIgnoreCase("No Shares present")) {
                        setMessage("No Shares present");
                    }
                } else {
                    setMessage("Check The Transferee Folio NO");
                }
            } else {
                shareTransferLeft = new ArrayList<ShareTransferLeftGrid>();
                setMessage("Check The Transferee Folio Number");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void transfereeCertificateNum() {
        try {
            if (transfereeCertNo.equalsIgnoreCase("New Certificate")) {
                List resultList = new ArrayList();
                resultList = remoteObject.gettransfereeCertNo();
                if (resultList.size() > 0) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        if (ele.get(0) != null) {
                            setTransfereeCertNoTxt(ele.get(0).toString());
                        }
                    }
                    transfereeCertNoTxtDisable = true;
                    transferorCertNoDisable = false;
                }
            } else {
                transfereeCertNoTxtDisable = false;
                transferorCertNoDisable = true;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void transferorCertificateNum() {
        try {
            if (transferorCertNo.equalsIgnoreCase("New Certificate")) {
                if (transfereeCertNo.equalsIgnoreCase("New Certificate")) {
                    List resultList = new ArrayList();
                    resultList = remoteObject.gettransfereeCertNo();
                    if (resultList.size() > 0) {
                        for (int i = 0; i < resultList.size(); i++) {
                            Vector ele = (Vector) resultList.get(i);
                            if (ele.get(0) != null) {
                                String no = ele.get(0).toString();
                                int num = Integer.parseInt(no) + 1;
                                setTransferorCertNoTxt(num + "");
                            }
                        }

                    }
                } else {
                    List resultList = new ArrayList();
                    resultList = remoteObject.gettransfereeCertNo();
                    if (resultList.size() > 0) {
                        for (int i = 0; i < resultList.size(); i++) {
                            Vector ele = (Vector) resultList.get(i);
                            if (ele.get(0) != null) {
                                setTransferorCertNoTxt(ele.get(0).toString());
                            }
                        }
                    }

                }
                transferorCertNoTxtDisable = true;
                saveDisable = false;

            } else {
                transferorCertNoTxtDisable = false;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void checkCertificateNoDetail() {
        setMessage("");
        try {
            if (folioNoLeft == null || folioNoLeft.equalsIgnoreCase("")) {
                setMessage("Please Fill Transferor Folio No");
                return;
            }
            if (folioNoLeft.length() < 12) {
                this.setMessage("Please enter 12 digit Transferor Folio No");
                return;
            }

            if (fromCertificate == null || fromCertificate.equalsIgnoreCase("")) {
                setMessage("Please Fill From Certificate");
                return;
            } else {
                if (!fromCertificate.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in From Certificate");
                    return;
                }
            }
            resultListCheck = remoteObject.getShareNum(fullFolioNotransferor, Double.parseDouble(fromCertificate));
            if (resultListCheck.size() > 0) {
                if (onblurChecking().equalsIgnoreCase("true")) {
                    setSharesToBeTransferred("");
                    setShareNoFrom("");
                    setShareNoTo("");
                    saveDisable = true;
                    this.setMessage("This certifate not authorized");
                    return;
                }
                for (int i = 0; i < resultListCheck.size(); i++) {
                    Vector ele = (Vector) resultListCheck.get(i);
                    if (ele.get(0) != null) {
                        checkCertificate = ele.get(0).toString();
                    }
                }
            } else {
                setMessage("Please Check From Certificate No Entered");
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String onblurChecking() {
        try {
            List resultList = new ArrayList();

            resultList = remoteObject.checkAuthorization(Double.parseDouble(fromCertificate));
            if (resultList.size() > 0) {
                checkAuth = "true";
            } else {
                checkAuth = "false";
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return checkAuth;
    }

    public void checkShareNo() {
        setMessage("");
        try {
            if (fromCertificate == null || fromCertificate.equalsIgnoreCase("")) {
                setMessage("Please Fill From Certificate");
                return;
            } else {
                if (!fromCertificate.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in From Certificate");
                    return;
                }
            }
            if (sharesToBeTransferred == null || sharesToBeTransferred.equalsIgnoreCase("")) {
                setMessage("Please Fill Shares To Be Transferred");
                return;
            } else {
                if (!sharesToBeTransferred.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Shares To Be Transferred");
                    return;
                }
            }
            if (resultListCheck.size() < Integer.parseInt(sharesToBeTransferred)) {
                this.setMessage("Shares To Be Transferred exceeds present in the Transferor Details table");
                fromDisable = false;
                toDisable = true;
                saveDisable = true;
                return;
            } else if (resultListCheck.size() == Integer.parseInt(sharesToBeTransferred)) {
                for (int i = 0; i < shareTransferLeft.size(); i++) {
                    if (shareTransferLeft.get(i).getCertNo().toString().equalsIgnoreCase(fromCertificate) && Integer.parseInt(shareTransferLeft.get(i).getNoOfshares().toString()) >= Integer.parseInt(sharesToBeTransferred)) {
                        setShareNoFrom(shareTransferLeft.get(i).getNoFrom().toString());
                        setShareNoTo(shareTransferLeft.get(i).getNoTo().toString());
                        fromDisable = true;
                        toDisable = true;
                        saveDisable = false;
                        this.setMessage("");
                        break;
                    } else {
                        this.setMessage("Enter the valid no of Shares To Be Transferred");
                    }
                }
            } else {
                fromDisable = false;
                toDisable = true;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void checkShareNoFrom() {
        setMessage("");
        try {
            if (shareNoFrom == null || fromCertificate.equalsIgnoreCase("")) {
                setMessage("Please Fill Share No From");
                return;
            } else {
                if (!shareNoFrom.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Share No From");
                    return;
                }
            }
            for (int i = 0; i < shareTransferLeft.size(); i++) {
                if (shareTransferLeft.get(i).getCertNo().toString().equalsIgnoreCase(fromCertificate)) {
                    if (shareNoFrom == null || shareNoFrom.equalsIgnoreCase("")) {
                        this.setMessage("Please Fill Share No From");
                        return;
                    } else {
                        if (!shareNoFrom.matches("[0-9]*")) {
                            this.setMessage("Please Enter Numeric Value in Share No From");
                            return;
                        }
                    }
                    if (!(Integer.parseInt(shareTransferLeft.get(i).getNoFrom()) <= Integer.parseInt(shareNoFrom) && Integer.parseInt(shareNoFrom) <= Integer.parseInt(shareTransferLeft.get(i).getNoTo()))) {
                        this.setMessage("Please enter valid  Share No From");
                        return;
                    }
                    if (Integer.parseInt(shareTransferLeft.get(i).getNoFrom().toString()) <= Integer.parseInt(shareNoFrom) && Integer.parseInt(shareTransferLeft.get(i).getNoTo().toString()) >= Integer.parseInt(shareNoFrom)) {
                        int sum = Integer.parseInt(shareNoFrom) + Integer.parseInt(sharesToBeTransferred) - 1;
                        if (Integer.parseInt(shareNoFrom) < Integer.parseInt(shareTransferLeft.get(i).getNoFrom().toString()) || Integer.parseInt(shareNoFrom) > Integer.parseInt(shareTransferLeft.get(i).getNoTo().toString()) || !(sum <= Integer.parseInt(shareTransferLeft.get(i).getNoTo().toString()))) {
                            setMessage("Please Check The Share No From Entered");
                            saveDisable = true;
                            return;
                        } else {
                            setShareNoTo(sum + "");
                            toDisable = true;
                            break;
                        }
                    }



                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void saveButtonAction() {
        try {
            if (validation().equalsIgnoreCase("false")) {
                return;
            }
            String check = "";
            if (resultListCheck.size() == Integer.parseInt(sharesToBeTransferred)) {
                check = "false";
            } else {
                check = "true";
            }
            String resultList = "";
            resultList = remoteObject.shareTransferSaveUpdation(fullFolioNotransferor, Integer.parseInt(fromCertificate), Integer.parseInt(shareNoFrom),
                    Integer.parseInt(shareNoTo), fullFolioNoTransferee, Integer.parseInt(transfereeCertNoTxt), Integer.parseInt(transferorCertNoTxt),
                    ymd.format(transferDate), getUserName(), ymd.format(date),
                    transferorCertNo, folioLeftName, folioRightName, Integer.parseInt(sharesToBeTransferred), remarks, check);
            setMessage(resultList);
            clear();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void clear() {
        setFolioNoLeft("");
        setFolioNoRight("");
        setFromCertificate("");
        setSharesToBeTransferred("");
        setShareNoFrom("");
        setShareNoTo("");
        setTransfereeCertNoTxt("");
        setTransferorCertNoTxt("");
        setRemarks("");
        setFolioRightName("");
        setFolioLeftName("");
        shareTransferLeft = new ArrayList<ShareTransferLeftGrid>();
        shareTransferRight = new ArrayList<ShareTransferRightGrid>();

    }

    public void refreshButtonAction() {
        setMessage("");
        setTransferorCertNoDisable(true);
        saveDisable = true;
        shareTransferLeft = new ArrayList<ShareTransferLeftGrid>();
        shareTransferRight = new ArrayList<ShareTransferRightGrid>();
        setFolioNoLeft("");
        setFolioNoRight("");
        setFromCertificate("");
        setSharesToBeTransferred("");
        setShareNoFrom("");
        setShareNoTo("");
        setTransfereeCertNoTxt("");
        setTransferorCertNoTxt("");
        setRemarks("");
        setFolioRightName("");
        setFolioLeftName("");
    }

    public String validation() {
        try {
            if (folioNoRight == null || folioNoRight.equalsIgnoreCase("")) {
                setMessage("Please fill Folio No");
                return "false";
            }
            if (folioNoRight.length() < 12) {
                this.setMessage("Please enter 12 digit Folio No");
                return "false";
            }

            if (folioNoLeft == null || folioNoLeft.equalsIgnoreCase("")) {
                setMessage("Please fill Folio No");
                return "false";
            }
            if (folioNoLeft.length() < 12) {
                this.setMessage("Please enter 12 digit Folio No");
                return "false";
            }


            if (fromCertificate == null || fromCertificate.equalsIgnoreCase("")) {
                this.setMessage("Please Fill From Certificate");
                return "false";
            } else {
                if (!fromCertificate.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in From Certificate");
                    return "false";
                }
            }
            if (sharesToBeTransferred == null || sharesToBeTransferred.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Shares To Be Transferred");
                return "false";
            } else {
                if (!sharesToBeTransferred.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Shares To Be Transferred");
                    return "false";
                }
            }
            if (shareNoFrom == null || shareNoFrom.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Share No From");
                return "false";
            } else {
                if (!shareNoFrom.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Share No From");
                    return "false";
                }
            }

            if (shareNoTo == null || shareNoTo.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Share No To");
                return "false";
            } else {
                if (!shareNoTo.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Share No To");
                    return "false";
                }
            }

            if (transfereeCertNoTxt == null || transfereeCertNoTxt.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Transferee Cert No");
                return "false";
            } else {
                if (!transfereeCertNoTxt.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Transferee Cert No");
                    return "false";
                }
            }
            if (transferorCertNoTxt == null || transferorCertNoTxt.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Transferor Cert No");
                return "false";
            } else {
                if (!transferorCertNoTxt.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Transferor Cert No");
                    return "false";
                }
            }
            if (transferDate == null) {
                this.setMessage("Please Fill Transfer Date");
                return "false";
            }
            return "true";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "true";
    }

    public String btnExit() {
        return "case1";
    }
}
