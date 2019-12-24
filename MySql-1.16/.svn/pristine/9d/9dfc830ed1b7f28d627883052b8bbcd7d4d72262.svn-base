/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.pojo.OtherUnclaimedDepositPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class OtherUnclaimedDetail extends BaseBean {

    private String msg;
    private String acNature;
    private List<SelectItem> acNatureList;
    private String acType;
    private List<SelectItem> acTypeList;
    private String glAcno;
    private String month;
    private List<SelectItem> monthList;
    private String year;
    private Date date = new Date();
    private String custname;
    private boolean disablePost = true;
    private OtherUnclaimedDepositPojo currentItem;
    private List<OtherUnclaimedDepositPojo> tableDataList;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private DDSManagementFacadeRemote ddsRemote = null;
    private CommonReportMethodsRemote common = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    String intCalcDt = "";
    String finalDeafEffDt = "";
    private DDSReportFacadeRemote ddsReportRemote;

    public OtherUnclaimedDepositPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(OtherUnclaimedDepositPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<OtherUnclaimedDepositPojo> getTableDataList() {
        return tableDataList;
    }

    public void setTableDataList(List<OtherUnclaimedDepositPojo> tableDataList) {
        this.tableDataList = tableDataList;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public OtherUnclaimedDetail() {
        try {
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            ddsRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsReportRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");

            acNatureList = new ArrayList<SelectItem>();
            acTypeList = new ArrayList<SelectItem>();
            monthList = new ArrayList<SelectItem>();

            //Retrieve Nature
            acNatureList.add(new SelectItem("0", "--Select--"));
            List list = ddsRemote.getGlAccounNature();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    acNatureList.add(new SelectItem(vec.get(0)));
                }
            }
            //Set all months
            monthList.add(new SelectItem("0", "--Select--"));
            Map<Integer, String> monthMap = CbsUtil.getAllMonths();
            System.out.println("Month List Size-->" + monthMap.size());
            Set<Map.Entry<Integer, String>> mapSet = monthMap.entrySet();
            Iterator<Map.Entry<Integer, String>> mapIt = mapSet.iterator();
            while (mapIt.hasNext()) {
                Map.Entry<Integer, String> mapEntry = mapIt.next();
                monthList.add(new SelectItem(mapEntry.getKey(), mapEntry.getValue()));
            }
            resetForm();
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void getGlCode() {
        try {
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("0", "--Select--"));
//            acTypeList.add(new SelectItem("ALL", "ALL"));
            List actCodeList = ddsRemote.getGlCodeByGlNature(this.acNature);
            if (!actCodeList.isEmpty()) {
                for (int i = 0; i < actCodeList.size(); i++) {
                    Vector vec = (Vector) actCodeList.get(i);
                    acTypeList.add(new SelectItem(vec.get(0), vec.get(1).toString()));
                }
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void getGlAccount() {
        setMsg("");
        try {
            this.glAcno = getOrgnBrCode() + ddsRemote.getGlAcnoByGlCodeBGlNature(acNature, acType) + "01";
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void gridLoad() {
        setMsg("");
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            intCalcDt = "";
            finalDeafEffDt = "";

            //Get Month Deaf Effective Date
            String deafEffDate = ddsReportRemote.getDeafEfectiveDate();
            if (deafEffDate.equals("") || deafEffDate.equals("")) {
                this.setMsg("Please define Deaf Effective Date");
                return;
            }
            //Get Final Interest Calculation Date
//            intCalcDt = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month),
//                    Integer.parseInt(this.year)));

            intCalcDt = dmy.format(ymd.parse(CbsUtil.dateAdd(ymd.format(CbsUtil.
                    calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year))), -1)));

            String prevMonthDt = CbsUtil.monthAdd(ymd.format(dmy.parse(intCalcDt)), -1);

            //Final Deaf Effective Date
            finalDeafEffDt = prevMonthDt.substring(0, 6) + deafEffDate;

            tableDataList = new ArrayList<OtherUnclaimedDepositPojo>();
            List list = ddsRemote.getOtherUnclaimedAccountDetail(getOrgnBrCode(), acNature, acType, glAcno,
                    finalDeafEffDt, getTodayDate());
            if (list == null || list.isEmpty()) {
                this.setMsg("There is no data to Deaf Mark.");
                return;
            }
            // String ppdd = common.getPoddType(glAcno.substring(2, 10));

            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                OtherUnclaimedDepositPojo pojo = new OtherUnclaimedDepositPojo();
                pojo.setfYear(ele.get(0).toString());
                pojo.setGlAcno(ele.get(1).toString());
                pojo.setSeqNo(ele.get(2).toString());
                pojo.setInstNo(ele.get(6).toString());
                BigDecimal amount = new BigDecimal(ele.get(3).toString());
                if ((acNature.equalsIgnoreCase("SUN") || acNature.equalsIgnoreCase("SUP"))) {
                    String amt = common.getSuspenceBalance(ele.get(1).toString(), ele.get(0).toString(),
                            ele.get(2).toString());
                    pojo.setAmount(amount.subtract(new BigDecimal(amt)));
                } else {
                    pojo.setAmount(amount);
                }
                pojo.setStatus(ele.get(4).toString());
                pojo.setOriginDt(ele.get(5).toString());
                tableDataList.add(pojo);
            }
            this.disablePost = false;
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void processAction() {
        setMsg("");
        try {
            if (ftsRemote.existInParameterInfoReport("UNCLAIMED-POSTING")) {
                if (!tableDataList.isEmpty()) {
                    String otherMarkingPost = ddsRemote.otherMarkUnClaimed(getOrgnBrCode(), acNature,
                            acType, tableDataList, finalDeafEffDt, ymd.format(dmy.parse(intCalcDt)),
                            ymd.format(dmy.parse(getTodayDate())), getUserName());
                    if (!otherMarkingPost.equalsIgnoreCase("true")) {
                        this.setMsg("Problem in Other Marking claim! ");
                        return;
                    }
                    resetForm();
                    this.setMsg("Posting has been done successfully!");
                    tableDataList = new ArrayList<OtherUnclaimedDepositPojo>();
                }
            } else {
                this.setMsg("Posting is not allowed.");
                return;
            }
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void resetForm() {
        this.setMsg("");
        this.setGlAcno("");
        this.setAcNature("0");
        this.setAcType("0");
        this.setMonth("0");
        this.setYear("");

        tableDataList = new ArrayList<OtherUnclaimedDepositPojo>();
        //this.setMsg("Please fill A/c Number.");
        this.disablePost = true;
    }

    public String exitBtnAction() {
        resetForm();
        return "case1";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGlAcno() {
        return glAcno;
    }

    public void setGlAcno(String glAcno) {
        this.glAcno = glAcno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
