package com.cbs.web.controller.master;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.web.pojo.master.AccountTypeMasterGrid;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public final class AccountTypeMaster extends BaseBean {

    private GeneralMasterFacadeRemote generalFacadeRemote;
    private String message;
    private String nature, accountType;
    private List<SelectItem> acctNatureOption;
    private String accType;
    private String description;
    private String acctGl1;
    private List<SelectItem> acctGlOption;
    private String intHead1;
    private String glHeadProvision1;
    private String minBalance;
    private String minbalChq;
    private String chqBookSeries;
    private String roi;
    private String roiStaff;
    private String glHeadIncuri;
    private String penalty;
    private String productCode;
    private String minBalCharges;
    private String tdsNature;
    private String oldAcctGl1;
    private String oldGlHeadProvision1;
    private String oldIntHead1;
    boolean update1;
    boolean save;
    ArrayList<AccountTypeMasterGrid> gridData = new ArrayList<AccountTypeMasterGrid>();
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    private AccountTypeMasterGrid grid;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getOldAcctGl1() {
        return oldAcctGl1;
    }

    public void setOldAcctGl1(String oldAcctGl1) {
        this.oldAcctGl1 = oldAcctGl1;
    }

    public String getOldGlHeadProvision1() {
        return oldGlHeadProvision1;
    }

    public void setOldGlHeadProvision1(String oldGlHeadProvision1) {
        this.oldGlHeadProvision1 = oldGlHeadProvision1;
    }

    public String getOldIntHead1() {
        return oldIntHead1;
    }

    public void setOldIntHead1(String oldIntHead1) {
        this.oldIntHead1 = oldIntHead1;
    }

    public boolean isUpdate1() {
        return update1;
    }

    public void setUpdate1(boolean update1) {
        this.update1 = update1;
    }

    public AccountTypeMasterGrid getGrid() {
        return grid;
    }

    public void setGrid(AccountTypeMasterGrid grid) {
        this.grid = grid;
    }

    public ArrayList<AccountTypeMasterGrid> getGridData() {
        return gridData;
    }

    public void setGridData(ArrayList<AccountTypeMasterGrid> gridData) {
        this.gridData = gridData;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public String getMinBalCharges() {
        return minBalCharges;
    }

    public void setMinBalCharges(String minBalCharges) {
        this.minBalCharges = minBalCharges;
    }

    public String getAcctGl1() {
        return acctGl1;
    }

    public void setAcctGl1(String acctGl1) {
        this.acctGl1 = acctGl1;
    }

    public String getGlHeadProvision1() {
        return glHeadProvision1;
    }

    public void setGlHeadProvision1(String glHeadProvision1) {
        this.glHeadProvision1 = glHeadProvision1;
    }

    public String getIntHead1() {
        return intHead1;
    }

    public void setIntHead1(String intHead1) {
        this.intHead1 = intHead1;
    }

    public String getTdsNature() {
        return tdsNature;
    }

    public void setTdsNature(String tdsNature) {
        this.tdsNature = tdsNature;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getGlHeadIncuri() {
        return glHeadIncuri;
    }

    public void setGlHeadIncuri(String glHeadIncuri) {
        this.glHeadIncuri = glHeadIncuri;
    }

    public String getChqBookSeries() {
        return chqBookSeries;
    }

    public void setChqBookSeries(String chqBookSeries) {
        this.chqBookSeries = chqBookSeries;
    }

    public String getMinbalChq() {
        return minbalChq;
    }

    public void setMinbalChq(String minbalChq) {
        this.minbalChq = minbalChq;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getRoiStaff() {
        return roiStaff;
    }

    public void setRoiStaff(String roiStaff) {
        this.roiStaff = roiStaff;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public List<SelectItem> getAcctNatureOption() {
        return acctNatureOption;
    }

    public void setAcctNatureOption(List<SelectItem> acctNatureOption) {
        this.acctNatureOption = acctNatureOption;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SelectItem> getAcctGlOption() {
        return acctGlOption;
    }

    public void setAcctGlOption(List<SelectItem> acctGlOption) {
        this.acctGlOption = acctGlOption;
    }

    public String getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(String minBalance) {
        this.minBalance = minBalance;
    }

    public AccountTypeMaster() {
        try {
            generalFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            nature();
            acctGlOption = new ArrayList<SelectItem>();
            acctGlOption.add(new SelectItem("GL", "GL"));
            clear();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void nature() {
        try {
            List resultList = new ArrayList();
            resultList = generalFacadeRemote.NatureAccountTypeMaster();
            acctNatureOption = new ArrayList<SelectItem>();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                acctNatureOption.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void save() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            setMessage("");
            if (this.nature.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select Account Nature");
                return;
            }
            if (this.accType.equalsIgnoreCase("") || accType == null || this.accType.length() == 0 || this.accType.length() > 2) {
                this.setMessage("Please Enter Account Code");
                return;
            }
            if (!this.accType.matches("[0-9]*")) {
                this.setMessage("Fill Account Code as Characters.Please Do Not Use Like(.,%#@!etc)");
                return;
            }
            if (this.accountType.equalsIgnoreCase("") || accountType == null || this.accountType.length() == 0 || this.accountType.length() > 2) {
                this.setMessage("Please Enter Account Type");
                return;
            }
            if (!this.accountType.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                this.setMessage("Fill Account Type as Characters.Please Do Not Use Like(.,%#@!etc)");
                return;
            }
            if (this.description.equalsIgnoreCase("") || description == null) {
                this.setMessage("Please Enter Description");
                return;
            }
            if (!this.description.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                this.setMessage("Fill Description Type as Characters.Please Do Not Use Like(.,%#@!etc)");
                return;
            }
            if (this.acctGl1.equalsIgnoreCase("") || acctGl1 == null || this.acctGl1.length() == 0 || this.acctGl1.length() > 8) {
                this.setMessage("Please Enter Valid G.L. Head For A/c Type");
                return;
            }
            if (this.intHead1.equalsIgnoreCase("") || intHead1 == null || this.intHead1.length() == 0 || this.intHead1.length() > 8) {
                this.setMessage("Please Enter  Valid  Int. Head (G.L)");
                return;
            }
            if (this.glHeadProvision1.equalsIgnoreCase("") || glHeadProvision1 == null || this.glHeadProvision1.length() == 0 || this.glHeadProvision1.length() > 8) {
                this.setMessage("Please Enter  Valid G.L. Head Provision For Interest");
                return;
            }
            if (this.minBalance.equalsIgnoreCase("") || this.minBalance.length() == 0) {
                this.setMessage("Please Enter Minimum Balance");
                return;
            }
            if (Float.parseFloat(minBalance) < 0) {
                this.setMessage("Negative G.L. Head Provision For Interest is not accepted.");
                return;
            }
            if (Integer.parseInt(minBalance) > 32767) {
                this.setMessage("Please Enter Valid Min. Bal. Charge");
                return;
            }
            if (this.minbalChq.equalsIgnoreCase("") || this.minbalChq.length() == 0) {
                this.setMessage("Please Enter Min Balance (Cheque)");
                return;
            }
            if (this.chqBookSeries.equalsIgnoreCase("") || this.chqBookSeries.length() == 0) {
                this.setMessage("Please Enter Cheque Book Series");
                return;
            }
            Pattern q = Pattern.compile("(((-|\\+)?[0-9]+(\\.[0-9]+)?)+)||[a-zA-z]+([ '-][a-zA-Z]+)*");
            Matcher chqBookSeriesCheck = q.matcher(chqBookSeries);
            if (!chqBookSeriesCheck.matches()) {
                this.setMessage("Cheque Book Series Should Be As Numerics Or Characters");
                return;
            }
            if (this.roi.equalsIgnoreCase("") || this.roi.length() == 0) {
                this.setMessage("Please Enter Rate Of Interest");
                return;
            }
            if (this.roiStaff.equalsIgnoreCase("") || this.roiStaff.length() == 0) {
                this.setMessage("Please Enter Staff (ROI)");
                return;
            }
            if (!this.glHeadIncuri.equalsIgnoreCase("") || this.glHeadIncuri.length() != 0) {

                Pattern q1 = Pattern.compile("(([a-zA-z]+([ '-][a-zA-Z]+)*)+(((-|\\+)?[0-9]+(\\.[0-9]+)?)+))||(([a-zA-z]+([ '-][a-zA-Z]+)*)||(((-|\\+)?[0-9]+(\\.[0-9]+)?)+))");
                Matcher glHeadIncuriCheck = q1.matcher(glHeadIncuri);
                if (!glHeadIncuriCheck.matches()) {
                    this.setMessage("G.L Head INCURI Should Be Character Or Numerics Or (First Character And Rest Numerics) ");
                    return;
                }
            }
            if (this.penalty.equalsIgnoreCase("") || penalty == null || this.penalty.length() == 0) {
                this.setMessage("Please Enter Penalty");
                return;
            }
            if (this.minBalCharges.equalsIgnoreCase("") || this.minBalCharges == null || this.minBalCharges.length() == 0) {
                this.setMessage("Please Enter Min. Bal. Charge");
                return;
            }
            if (this.productCode.equalsIgnoreCase("") || this.productCode.length() == 0) {
                this.setMessage("Please Enter Product Code");
                return;
            }
            Matcher productCodeCheck = q.matcher(productCode);
            if (!productCodeCheck.matches()) {
                this.setMessage("Product Code Should Be As Numerics Or Characters");
                return;
            }
            if (this.tdsNature.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select OF Nature Of T/D's");
                return;
            }
            Matcher acctGl1Check = p.matcher(acctGl1);
            if (!acctGl1Check.matches()) {
                this.setMessage("G.L. Head For A/c Type Should be Numeric.");
                this.setAcctGl1("");
                return;
            }
            if (Float.parseFloat(acctGl1) < 0) {
                this.setMessage("Negative G.L. Head For A/c Type is not accepted.");
                return;
            }
            Matcher intHead1Check = p.matcher(intHead1);
            if (!intHead1Check.matches()) {
                this.setMessage("Int. Head (G.L) Should be Numeric.");
                this.setIntHead1("");
                return;
            }
            if (Float.parseFloat(intHead1) < 0) {
                this.setMessage("Negative Int. Head (G.L) is not accepted.");
                return;
            }
            Matcher glHeadProvision1Check = p.matcher(glHeadProvision1);
            if (!glHeadProvision1Check.matches()) {
                this.setMessage("G.L. Head Provision For Interest Should be Numeric.");
                this.setGlHeadProvision1("");
                return;
            }
            if (Float.parseFloat(glHeadProvision1) < 0) {
                this.setMessage("Negative G.L. Head Provision For Interest is not accepted.");
                return;
            }
            Matcher minBalanceCheck = p.matcher(minBalance);
            if (!minBalanceCheck.matches()) {
                this.setMessage("Minimum Balance Should be In Integers.");
                this.setMinBalance("");
                return;
            }
            Matcher minbalChqCheck = p.matcher(minbalChq);
            if (!minbalChqCheck.matches()) {
                this.setMessage("Min Balance (Cheque) Should be Numeric.");
                this.setMinbalChq("");
                return;
            }
            if (Float.parseFloat(minbalChq) < 0) {
                this.setMessage("Negative Min Balance (Cheque) is not accepted.");
                return;
            }
            Matcher roiCheck = p.matcher(roi);
            if (!roiCheck.matches()) {
                this.setMessage("Rate Of Interest Should be Numeric.");
                this.setRoi("");
                return;
            }
            Matcher roiStaffCheck = p.matcher(roiStaff);
            if (!roiStaffCheck.matches()) {
                this.setMessage("Staff (ROI) Should be Numeric.");
                this.setRoiStaff("");
                return;
            }
            Matcher penaltyCheck = p.matcher(penalty);
            if (!penaltyCheck.matches()) {
                this.setMessage("Valid Penalty Should be Numeric.");
                this.setPenalty("");
                return;
            }
            Matcher minBalChargesCheck = p.matcher(minBalCharges);
            if (!minBalChargesCheck.matches()) {
                this.setMessage("Min. Bal. Charge Should be Numeric.");
                this.setMinBalCharges("");
                return;
            }
            String acctGl2 = acctGl1;
            String intHead2 = intHead1;
            String glHeadProvision2 = glHeadProvision1;
            String resultList = generalFacadeRemote.saveAccountTypeMaster(nature, accType, description, acctGl2, intHead2, glHeadProvision2, Integer.parseInt(minBalance), Float.parseFloat(minbalChq), chqBookSeries, Float.parseFloat(roi), Float.parseFloat(roiStaff), Float.parseFloat(penalty), Float.parseFloat(minBalCharges), productCode, tdsNature, getUserName(), glHeadIncuri, accountType);
            this.setMessage(resultList);
            clear1();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void clear() {
        try {
            gridData = new ArrayList<AccountTypeMasterGrid>();
            save = true;
            update1 = true;
            setAccountType("");
            setMessage("");
            setAcctGl1("");
            setGlHeadProvision1("");
            setIntHead1("");
            setTdsNature("");
            setMinBalCharges("");
            setProductCode("");
            setGlHeadIncuri("");
            setPenalty("");
            setRoiStaff("");
            setRoi("");
            setChqBookSeries("");
            setMinbalChq("");
            setAccType("");
            setDescription("");
            setNature("--Select--");
            setTdsNature("--Select--");
            setOldAcctGl1("");
            setOldGlHeadProvision1("");
            setOldIntHead1("");
            setMinBalance("");
            setAccountType("");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void clear1() {
        try {
            gridData = new ArrayList<AccountTypeMasterGrid>();
            save = true;
            update1 = true;
            setAccountType("");
            setAcctGl1("");
            setGlHeadProvision1("");
            setIntHead1("");
            setOldGlHeadProvision1("");
            setOldIntHead1("");
            setMinBalance("");
            setTdsNature("");
            setMinBalCharges("");
            setProductCode("");
            setGlHeadIncuri("");
            setPenalty("");
            setRoiStaff("");
            setRoi("");
            setChqBookSeries("");
            setMinbalChq("");
            setAccType("");
            setDescription("");
            setNature("--Select--");
            setTdsNature("--Select--");
            setMinBalance("");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void updateData() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Pattern q1 = Pattern.compile("(([a-zA-z]+([ '-][a-zA-Z]+)*)+(((-|\\+)?[0-9]+(\\.[0-9]+)?)+))||(([a-zA-z]+([ '-][a-zA-Z]+)*)||(((-|\\+)?[0-9]+(\\.[0-9]+)?)+))");
            Pattern q = Pattern.compile("(((-|\\+)?[0-9]+(\\.[0-9]+)?)+)||[a-zA-z]+([ '-][a-zA-Z]+)*");
            setMessage("");
            if (minBalance.equalsIgnoreCase("") || minBalance == null) {
                setMinBalance("0");
            }
            if (minbalChq.equalsIgnoreCase("") || minbalChq == null) {
                setMinbalChq("0");
            }
            if (chqBookSeries.equalsIgnoreCase("")) {
                setChqBookSeries("0");
            }
            if (minBalCharges.equalsIgnoreCase("")) {
                setMinBalCharges("0");
            }
            if (productCode.equalsIgnoreCase("")) {
                setProductCode("0");
            }
            if (glHeadIncuri.equalsIgnoreCase("")) {
                setGlHeadIncuri("0");
            }
            if (penalty.equalsIgnoreCase("")) {
                setPenalty("0");
            }
            if (roiStaff.equalsIgnoreCase("")) {
                setRoiStaff("0");
            }
            if (roi.equalsIgnoreCase("")) {
                setRoi("0");
            }
            if (tdsNature.equalsIgnoreCase("--Select--")) {
                setTdsNature("");
            }
            if (this.acctGl1.equalsIgnoreCase("") || acctGl1 == null) {
                acctGl1 = "0";
            }
            if (this.intHead1.equalsIgnoreCase("") || intHead1 == null) {
                intHead1 = "0";
            }
            if (this.glHeadProvision1.equalsIgnoreCase("") || glHeadProvision1 == null) {
                glHeadProvision1 = "0";
            }
            if (Integer.parseInt(minBalance) > 32767) {
                this.setMessage("Please Enter Valid Min. Bal. Charge");
                return;
            }
            Matcher productCodeCheck = q.matcher(productCode);
            if (!productCodeCheck.matches()) {
                this.setMessage("Product Code Should Be As Numerics Or Characters");
                return;
            }
            if (this.tdsNature.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select OF Nature Of T/D's");
                return;
            }
            Matcher acctGl1Check = p.matcher(acctGl1);
            if (!acctGl1Check.matches()) {
                this.setMessage("G.L. Head For A/c Type Should be Numeric.");
                this.setAcctGl1("");
                return;
            }
            if (Float.parseFloat(acctGl1) < 0) {
                this.setMessage("Negative G.L. Head For A/c Type is not accepted.");
                return;
            }
            Matcher intHead1Check = p.matcher(intHead1);
            if (!intHead1Check.matches()) {
                this.setMessage("Int. Head (G.L) Should be Numeric.");
                this.setIntHead1("");
                return;
            }
            if (Float.parseFloat(intHead1) < 0) {
                this.setMessage("Negative Int. Head (G.L) is not accepted.");
                return;
            }
            Matcher glHeadProvision1Check = p.matcher(glHeadProvision1);
            if (!glHeadProvision1Check.matches()) {
                this.setMessage("G.L. Head Provision For Interest Should be Numeric.");
                this.setGlHeadProvision1("");
                return;
            }
            if (Float.parseFloat(glHeadProvision1) < 0) {
                this.setMessage("Negative G.L. Head Provision For Interest is not accepted.");
                return;
            }
            Matcher minBalanceCheck = p.matcher(minBalance);
            if (!minBalanceCheck.matches()) {
                this.setMessage("Minimum Balance Should be In Integers.");
                this.setMinBalance("");
                return;
            }
            Matcher minbalChqCheck = p.matcher(minbalChq);
            if (!minbalChqCheck.matches()) {
                this.setMessage("Min Balance (Cheque) Should be Numeric.");
                this.setMinbalChq("");
                return;
            }
            if (Float.parseFloat(minbalChq) < 0) {
                this.setMessage("Negative Min Balance (Cheque) is not accepted.");
                return;
            }
            Matcher roiCheck = p.matcher(roi);
            if (!roiCheck.matches()) {
                this.setMessage("Rate Of Interest Should be Numeric.");
                this.setRoi("");
                return;
            }
            Matcher roiStaffCheck = p.matcher(roiStaff);
            if (!roiStaffCheck.matches()) {
                this.setMessage("Staff (ROI) Should be Numeric.");
                this.setRoiStaff("");
                return;
            }
            Matcher penaltyCheck = p.matcher(penalty);
            if (!penaltyCheck.matches()) {
                this.setMessage("Valid Penalty Should be Numeric.");
                this.setPenalty("");
                return;
            }
            Matcher minBalChargesCheck = p.matcher(minBalCharges);
            if (!minBalChargesCheck.matches()) {
                this.setMessage("Min. Bal. Charge Should be Numeric.");
                this.setMinBalCharges("");
                return;
            }
            Matcher glHeadIncuriCheck = q1.matcher(glHeadIncuri);
            if (!glHeadIncuriCheck.matches()) {
                this.setMessage("G.L Head INCURI Should Be Character Or Numerics Or (First Character And Rest Numerics) ");
                return;
            }
            String acctGl2 = acctGl1;
            String intHead2 = intHead1;
            String glHeadProvision2 = glHeadProvision1;
            String origindt1 = getTodayDate().substring(6, 10) + getTodayDate().substring(3, 5) + getTodayDate().substring(0, 2);
            String result = generalFacadeRemote.UpdateAccountTypeMaster(getDescription(), acctGl2, intHead2, glHeadProvision2, Integer.parseInt(getMinBalance()), Float.parseFloat(getMinbalChq()), getChqBookSeries(), Float.parseFloat(getRoi()), Float.parseFloat(getRoiStaff()), Float.parseFloat(getPenalty()), Float.parseFloat(getMinBalCharges()), getProductCode(), getTdsNature(), getUserName(), origindt1, getGlHeadIncuri(), getNature(), getAccType());
            setMessage(result);
            clear1();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onBlurAcctCode() {
        try {
            setMessage("");
            setAcctGl1("");
            setOldAcctGl1("");
            setOldGlHeadProvision1("");
            setOldIntHead1("");
            setGlHeadProvision1("");
            setIntHead1("");
            setTdsNature("");
            setMinBalCharges("");
            setProductCode("");
            setGlHeadIncuri("");
            setPenalty("");
            setRoiStaff("");
            setRoi("");
            setChqBookSeries("");
            setMinbalChq("");
            setGlHeadProvision1("");
            setDescription("");
            setTdsNature("--Select--");
            setMinBalance("");

            if (!this.accType.equalsIgnoreCase("")) {
                if (!this.accType.matches("[0-9]*")) {
                    this.setMessage("Please fill proper accountcode");
                    return;
                }
            }
            if (getAccType().toUpperCase().equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode()) || getAccType().toUpperCase().equals(CbsAcCodeConstant.OF_AC.getAcctCode())) {
                setMessage("Account Code Should Not Be 'GL' Or 'OF' Type");
                this.setAccType("");
                return;
            }
            this.setMessage("");
            List l1 = generalFacadeRemote.checkAcctCodeAccountTypeMaster(getNature(), getAccType());
            if (l1 == null || l1.isEmpty()) {
                save = false;
                update1 = true;
                List l2 = generalFacadeRemote.checkAcctCode1AccountTypeMaster(getAccType());
                if (!l2.isEmpty()) {
                    Vector v2 = (Vector) l2.get(0);
                    this.setMessage("Account Code Already Exist with " + v2.get(0).toString() + " A/C Nature");
                }
            } else {
                save = true;
                update1 = false;
                Vector v1 = (Vector) l1.get(0);
                if (v1.get(1) == null || v1.get(1).equals("")) {
                    setDescription("");
                } else {
                    setDescription(v1.get(1).toString());
                }
                if (v1.get(17) == null || v1.get(17).equals("")) {
                    setAcctGl1("");
                    setOldAcctGl1("");
                } else {
                    String str = v1.get(17).toString();
                    String str1 = str;
                    setOldAcctGl1(str1);
                    setAcctGl1(str1);
                }
                if (v1.get(18) == null || v1.get(18).equals("")) {
                    setIntHead1("");
                    setOldIntHead1("");
                } else {
                    String str = v1.get(18).toString();
                    String str1 = str;
                    setIntHead1(str1);
                    setOldIntHead1(str1);
                }
                if (v1.get(20) == null || v1.get(20).equals("")) {
                    setGlHeadProvision1("");
                    setOldGlHeadProvision1("");
                } else {
                    String str = v1.get(20).toString();
                    String str1 = str;
                    setGlHeadProvision1(str1);
                    setOldGlHeadProvision1(str1);
                }
                if (v1.get(3) == null || v1.get(3).equals("")) {
                    setMinBalance("");
                } else {
                    setMinBalance(v1.get(3).toString());
                }
                if (v1.get(4) == null || v1.get(4).equals("")) {
                    setMinbalChq("");
                } else {
                    setMinbalChq(v1.get(4).toString());
                }
                if (v1.get(14) == null || v1.get(14).equals("")) {
                    setChqBookSeries("");
                } else {
                    setChqBookSeries(v1.get(14).toString());
                }
                if (v1.get(5) == null || v1.get(5).equals("")) {
                    setRoi("");
                } else {
                    setRoi(v1.get(5).toString());
                }
                if (v1.get(6) == null || v1.get(6).equals("")) {
                    setRoiStaff("");
                } else {
                    setRoiStaff(v1.get(6).toString());
                }
                if (v1.get(34) == null || v1.get(34).equals("")) {
                    setGlHeadIncuri("");
                } else {
                    setGlHeadIncuri(v1.get(34).toString());
                }
                if (v1.get(13) == null || v1.get(13).equals("")) {
                    setPenalty("");
                } else {
                    setPenalty(v1.get(13).toString());
                }
                if (v1.get(29) == null || v1.get(29).equals("")) {
                    setMinBalCharges("");
                } else {
                    setMinBalCharges(v1.get(29).toString());
                }
                if (v1.get(12) == null || v1.get(12).equals("")) {
                    setProductCode("");
                }
                if (!(v1.get(12) == null || v1.get(1).equals(""))) {
                    setProductCode(v1.get(12).toString());
                }
                if (v1.get(19).toString().equals("N") || v1.get(19) == null || v1.get(1).equals("")) {
                    setTdsNature("No");
                } else {
                    setTdsNature("Yes");
                }
                System.out.println("v1.get(35)" + v1.get(35));
                if (!(v1.get(35) == null || v1.get(35).equals(""))) {
                    setAccountType(v1.get(35).toString());
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void Report() {
        try {
            gridData = new ArrayList<AccountTypeMasterGrid>();
            List l1 = generalFacadeRemote.ReportAccountTypeMaster();
            if (l1.isEmpty()) {
                this.setMessage("Data Does Not Exist");
            } else {
                for (int i = 0; i < l1.size(); i++) {
                    Vector v1 = (Vector) l1.get(i);
                    grid = new AccountTypeMasterGrid();
                    if (v1.get(0) == null) {
                        grid.setAcctcode("");
                    } else {
                        grid.setAcctcode(v1.get(0).toString());
                    }
                    if (v1.get(1) == null) {
                        grid.setAcctDesc("");
                    } else {
                        grid.setAcctDesc(v1.get(1).toString());
                    }
                    if (v1.get(16) == null) {
                        grid.setAcctnature("");
                    } else {
                        grid.setAcctnature(v1.get(16).toString());
                    }
                    if (v1.get(17) == null) {
                        grid.setGlHead("");
                    } else {
                        grid.setGlHead(v1.get(17).toString());
                    }
                    if (v1.get(18) == null) {
                        grid.setGlheadint("");
                    } else {
                        grid.setGlheadint(v1.get(18).toString());
                    }
                    if (v1.get(3) == null) {
                        grid.setMinbal("");
                    } else {
                        grid.setMinbal(v1.get(3).toString());
                    }
                    if (v1.get(5) == null) {
                        grid.setMinInt("");
                    } else {
                        grid.setMinInt(v1.get(5).toString());
                    }
                    if (v1.get(29) == null) {
                        grid.setMinbalcharge("");
                    } else {
                        grid.setMinbalcharge(v1.get(29).toString());
                    }
                    if (v1.get(14) == null) {
                        grid.setChqsrno("");
                    } else {
                        grid.setChqsrno(v1.get(14).toString());
                    }
                    if (v1.get(20) == null) {
                        grid.setGLheadProv("");
                    } else {
                        grid.setGLheadProv(v1.get(20).toString());
                    }
                    if (v1.get(21) == null) {
                        grid.setProductcode("");
                    } else {
                        grid.setProductcode(v1.get(21).toString());
                    }
                    if (v1.get(19) == null) {
                        grid.setOfAcctnature("");
                    } else {
                        grid.setOfAcctnature((v1.get(19).toString()));
                    }
                    if (v1.get(6) == null) {
                        grid.setStaffInt("");
                    } else {
                        grid.setStaffInt(v1.get(6).toString());
                    }
                    if (v1.get(4) == null) {
                        grid.setMinbal_chq("");
                    } else {
                        grid.setMinbal_chq(v1.get(4).toString());
                    }
                    gridData.add(grid);
                }
                Collections.sort(gridData);
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String exitBtnAction() {
        clear();
        return "case1";
    }

    public void getAccountDetailGLHeadAcType() {
        this.setMessage("");
        try {
            if (this.oldAcctGl1 == null || this.oldAcctGl1.equalsIgnoreCase("") || this.oldAcctGl1.length() == 0) {
                this.setMessage("Please Enter G.L. Head For A/c Type");
                return;
            }
            if (!oldAcctGl1.matches("[0-9a-zA-z]*")) {
                setMessage("Please Enter G.L. Head For A/c Type.");
                return;
            }
            if (oldAcctGl1.length() != 8) {
                setMessage("Please enter valid G.L. Head For A/c Type");
                return;
            }
            if ((oldAcctGl1.substring(0, 2).equalsIgnoreCase(CbsConstant.GL_AC))) {
                setAcctGl1(oldAcctGl1.replace(CbsConstant.GL_AC, CbsAcCodeConstant.GL_ACCNO.getAcctCode()));

            } else if (!oldAcctGl1.substring(0, 2).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                setMessage("Please enter correct G.L. Head For A/c Type!");
                return;

            } else {
                setAcctGl1(oldAcctGl1);
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void getAccountDetailIntGLHead() {
        this.setMessage("");
        try {
            if (this.oldIntHead1 == null || this.oldIntHead1.equalsIgnoreCase("") || this.oldIntHead1.length() == 0) {
                this.setMessage("Please Enter Int. Head");
                return;
            }
            if (!oldIntHead1.matches("[0-9a-zA-z]*")) {
                setMessage("Please Enter Int. Head.");
                return;
            }
            if (oldIntHead1.length() != 8) {
                setMessage("Int. Head is not valid.");
                return;
            }
            if ((oldIntHead1.substring(0, 2).equalsIgnoreCase(CbsConstant.GL_AC))) {
                setIntHead1(oldIntHead1.replace(CbsConstant.GL_AC, CbsAcCodeConstant.GL_ACCNO.getAcctCode()));

            } else if (!oldIntHead1.substring(0, 2).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                setMessage("Please enter correct Int. Head!");
                return;
            } else {
                setIntHead1(oldIntHead1);
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void getAccountDetailGlHeadProvision() {
        this.setMessage("");
        try {
            if (this.oldGlHeadProvision1 == null || this.oldGlHeadProvision1.equalsIgnoreCase("") || this.oldGlHeadProvision1.length() == 0) {
                this.setMessage("Please Enter G.L. Head Provision For Interest");
                return;
            }
            if (!oldGlHeadProvision1.matches("[0-9a-zA-z]*")) {
                setMessage("Please Enter correct G.L. Head Provision For Interest");
                return;
            }
            if (oldGlHeadProvision1.length() != 8) {
                setMessage("G.L. Head Provision For Interest is not valid.");
                return;
            }
            if ((oldGlHeadProvision1.substring(0, 2).equalsIgnoreCase(CbsConstant.GL_AC))) {
                setGlHeadProvision1(oldGlHeadProvision1.replace(CbsConstant.GL_AC, CbsAcCodeConstant.GL_ACCNO.getAcctCode()));

            } else if (!oldGlHeadProvision1.substring(0, 2).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                setMessage("Please enter correct G.L. Head Provision For Interest !");
                return;

            } else {
                setGlHeadProvision1(oldGlHeadProvision1);
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }
}
