/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.pojo.ClaimPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakingClaim extends BaseBean {

    private String msg;
    private String acno;
    private String custname;
    private String roiVisible = "none";
    private String savingRoi;
    private boolean disablePost = true;
    private ClaimPojo currentItem;
    private List<ClaimPojo> tableDataList;
    private String year;
    private String seqNo;
    private String hide = "none";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private DDSManagementFacadeRemote ddsRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public MakingClaim() {
        try {
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            ddsRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            resetForm();
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void processAcNo() {
        this.setMsg("");
        this.disablePost = true;
        try {

            if (this.acno == null || this.acno.equals("") || this.acno.length() != 12) {
                this.setMsg("Please fill A/c Number.");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(this.acno);
            if (m.matches() != true) {
                this.setMsg("Please fill A/c Number.");
                return;
            }
            if (!getOrgnBrCode().equals(ftsRemote.getCurrentBrnCode(acno))) {
                this.setMsg("Please fill your branch A/c Number.");
                return;
            }
            String partyNature = ftsRemote.getAccountNature(this.acno);
            List list = new ArrayList();
            tableDataList = new ArrayList<ClaimPojo>();

            if (partyNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                this.hide = "";
                if (year == null || year.equalsIgnoreCase("")) {
                    this.setMsg("Please fill 4 digits Year !");
                    return;
                }
                if (seqNo == null || seqNo.equalsIgnoreCase("")) {
                    this.setMsg("Please fill Sequence No !");
                    return;
                }
                if (!year.equalsIgnoreCase("")) {
                    list = ddsRemote.getDeafAccountDetail(this.acno, this.year, this.seqNo);
                }
            } else {
                this.hide = "none";
                list = ddsRemote.getDeafAccountDetail(this.acno, this.year, this.seqNo);
            }

            if (list == null || list.isEmpty()) {
                this.setMsg("There is no data to claim.");
                return;
            }
            String name = ftsRemote.getCustName(this.acno);
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                ClaimPojo pojo = new ClaimPojo();

                pojo.setAcno(this.acno);
                pojo.setCustname(name);
                pojo.setAmount(new BigDecimal(ele.get(0).toString()));
                pojo.setDeafDt(dmy.format(ymd.parse(ele.get(1).toString())));
                pojo.setVoucherNo(Double.parseDouble(ele.get(2).toString()));
                if (partyNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    pojo.setInstcode(ele.get(4).toString());
                }
                tableDataList.add(pojo);
            }

            this.roiVisible = "none";
            this.savingRoi = "";

            if (partyNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || partyNature.equalsIgnoreCase(CbsConstant.MS_AC) 
                    || partyNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                this.roiVisible = "";
                this.setMsg("First fill Saving Roi and then select a row from table.");
            } else {
                this.setMsg("Please select a row from table.");
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void selectData() {
        this.setMsg("");
        this.disablePost = true;
        try {
            this.setCustname(currentItem.getCustname());
            this.disablePost = false;
            this.setMsg("Now you can post the seleted entry.");
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void processAction() {
        this.setMsg("");
        try {
            if (currentItem != null) {
                String accountNo = currentItem.getAcno();
                String partyNature = ftsRemote.getAccountNature(accountNo);
                if (partyNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || partyNature.equalsIgnoreCase(CbsConstant.MS_AC) 
                        || partyNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    String result = validateRoi(this.savingRoi);
                    if (!result.equals("true")) {
                        this.setMsg(result);
                        return;
                    }
                } else {
                    this.savingRoi = "0";
                }
                String date = ymd.format(dmy.parse(getTodayDate()));
                String markingPost = ddsRemote.claimDeafAccount(accountNo, this.getUserName(), date, getOrgnBrCode(),
                        currentItem.getAmount().doubleValue(), ymd.format(dmy.parse(currentItem.getDeafDt())),
                        currentItem.getVoucherNo(), Double.parseDouble(this.savingRoi), this.year, currentItem.getInstcode());
                if (!markingPost.equalsIgnoreCase("true")) {
                    this.setMsg("Problem in Marking claim! ");
                    return;
                }
                resetForm();
                this.setMsg("Marking claim post successfully!");
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public String validateRoi(String roi) {
        String result = "true";
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            Matcher m = p.matcher(roi);
            if (roi == null || roi.equals("")) {
                return result = "Saving roi can not be blank.";
            } else if (m.matches() != true) {
                return result = "Please fill proper Saving Roi.";
            } else if (roi.equalsIgnoreCase("0") || roi.equalsIgnoreCase("0.0")) {
                return result = "Saving roi can not be zero.";
            } else if (new BigDecimal(roi).compareTo(new BigDecimal("0")) == -1) {
                return result = "Saving roi can not be negative.";
            }
            if (roi.contains(".")) {
                if (roi.indexOf(".") != roi.lastIndexOf(".")) {
                    return result = "Invalid Saving Roi. Please fill the Saving Roi correctly.";
                } else if (roi.substring(roi.indexOf(".") + 1).length() > 2) {
                    return result = "Please fill the Saving Roi upto two decimal places.";
                }
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
        return result;
    }

    public void resetForm() {
        this.setMsg("");
        this.setAcno("");
        this.setCustname("");
        currentItem = null;
        tableDataList = new ArrayList<ClaimPojo>();
        this.setMsg("Please fill A/c Number.");
        this.roiVisible = "none";
        this.savingRoi = "";
        this.disablePost = true;
        this.hide = "none";
        this.year = "";
        this.seqNo = "";

    }

    public String exitBtnAction() {
        resetForm();
        return "case1";
    }

    /*Getter And Setter*/
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public ClaimPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ClaimPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<ClaimPojo> getTableDataList() {
        return tableDataList;
    }

    public void setTableDataList(List<ClaimPojo> tableDataList) {
        this.tableDataList = tableDataList;
    }

    public String getRoiVisible() {
        return roiVisible;
    }

    public void setRoiVisible(String roiVisible) {
        this.roiVisible = roiVisible;
    }

    public String getSavingRoi() {
        return savingRoi;
    }

    public void setSavingRoi(String savingRoi) {
        this.savingRoi = savingRoi;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getHide() {
        return hide;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }
}
