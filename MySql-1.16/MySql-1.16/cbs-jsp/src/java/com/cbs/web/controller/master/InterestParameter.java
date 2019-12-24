package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.InterestMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public final class InterestParameter extends BaseBean {

    private InterestMasterFacadeRemote interestMasterRemote;
    List<SelectItem> acTypeList;
    List<SelectItem> monthTypeList;
    String acTypess;
    String month;
    List<SelectItem> selectOption;
    String option;
    boolean save;
    boolean checkBox;
    boolean checkBox2;
    boolean checkBox3;
    boolean checkBox4;
    boolean checkBox5;
    boolean checkBox6;
    private String inputText1;
    private String inputText2;
    private String inputText3;
    private String inputText4;
    private String inputText5;
    private String inputText6;
    private String inputText7;
    private String inputText8;
    private String inputText9;
    private String inputText10;
    private String inputText11;
    boolean valuefrchkOne;
    boolean valueFrchkTwo;
    boolean valuefrchkThree;
    boolean valueFrchkFour;
    boolean valuefrchkFive;
    boolean valueFrchkSix;
    String msgFrInterest;
    boolean saveButton;
    boolean inputDis1;
    boolean inputDis2;
    boolean inputDis3;
    boolean inputDis4;
    boolean inputDis5;
    boolean inputDis6;
    boolean inputDis7;
    boolean inputDis8;
    boolean inputDis9;
    boolean inputDis10;
    boolean inputDis11;
    String userName;
    String todayDt;

    public String getTodayDt() {
        return todayDt;
    }

    public void setTodayDt(String todayDt) {
        this.todayDt = todayDt;
    }

    public boolean isInputDis1() {
        return inputDis1;
    }

    public void setInputDis1(boolean inputDis1) {
        this.inputDis1 = inputDis1;
    }

    public boolean isInputDis10() {
        return inputDis10;
    }

    public void setInputDis10(boolean inputDis10) {
        this.inputDis10 = inputDis10;
    }

    public boolean isInputDis11() {
        return inputDis11;
    }

    public void setInputDis11(boolean inputDis11) {
        this.inputDis11 = inputDis11;
    }

    public boolean isInputDis2() {
        return inputDis2;
    }

    public void setInputDis2(boolean inputDis2) {
        this.inputDis2 = inputDis2;
    }

    public boolean isInputDis3() {
        return inputDis3;
    }

    public void setInputDis3(boolean inputDis3) {
        this.inputDis3 = inputDis3;
    }

    public boolean isInputDis4() {
        return inputDis4;
    }

    public void setInputDis4(boolean inputDis4) {
        this.inputDis4 = inputDis4;
    }

    public boolean isInputDis5() {
        return inputDis5;
    }

    public void setInputDis5(boolean inputDis5) {
        this.inputDis5 = inputDis5;
    }

    public boolean isInputDis6() {
        return inputDis6;
    }

    public void setInputDis6(boolean inputDis6) {
        this.inputDis6 = inputDis6;
    }

    public boolean isInputDis7() {
        return inputDis7;
    }

    public void setInputDis7(boolean inputDis7) {
        this.inputDis7 = inputDis7;
    }

    public boolean isInputDis8() {
        return inputDis8;
    }

    public void setInputDis8(boolean inputDis8) {
        this.inputDis8 = inputDis8;
    }

    public boolean isInputDis9() {
        return inputDis9;
    }

    public void setInputDis9(boolean inputDis9) {
        this.inputDis9 = inputDis9;
    }

    public boolean isSaveButton() {
        return saveButton;
    }

    public void setSaveButton(boolean saveButton) {
        this.saveButton = saveButton;
    }

    public String getMsgFrInterest() {
        return msgFrInterest;
    }

    public void setMsgFrInterest(String msgFrInterest) {
        this.msgFrInterest = msgFrInterest;
    }

    public boolean isValueFrchkFour() {
        return valueFrchkFour;
    }

    public void setValueFrchkFour(boolean valueFrchkFour) {
        this.valueFrchkFour = valueFrchkFour;
    }

    public boolean isValueFrchkSix() {
        return valueFrchkSix;
    }

    public void setValueFrchkSix(boolean valueFrchkSix) {
        this.valueFrchkSix = valueFrchkSix;
    }

    public boolean isValueFrchkTwo() {
        return valueFrchkTwo;
    }

    public void setValueFrchkTwo(boolean valueFrchkTwo) {
        this.valueFrchkTwo = valueFrchkTwo;
    }

    public boolean isValuefrchkFive() {
        return valuefrchkFive;
    }

    public void setValuefrchkFive(boolean valuefrchkFive) {
        this.valuefrchkFive = valuefrchkFive;
    }

    public boolean isValuefrchkThree() {
        return valuefrchkThree;
    }

    public void setValuefrchkThree(boolean valuefrchkThree) {
        this.valuefrchkThree = valuefrchkThree;
    }

    public String getInputText1() {
        return inputText1;
    }

    public void setInputText1(String inputText1) {
        this.inputText1 = inputText1;
    }

    public String getInputText10() {
        return inputText10;
    }

    public void setInputText10(String inputText10) {
        this.inputText10 = inputText10;
    }

    public String getInputText11() {
        return inputText11;
    }

    public void setInputText11(String inputText11) {
        this.inputText11 = inputText11;
    }

    public String getInputText2() {
        return inputText2;
    }

    public void setInputText2(String inputText2) {
        this.inputText2 = inputText2;
    }

    public String getInputText3() {
        return inputText3;
    }

    public void setInputText3(String inputText3) {
        this.inputText3 = inputText3;
    }

    public String getInputText4() {
        return inputText4;
    }

    public void setInputText4(String inputText4) {
        this.inputText4 = inputText4;
    }

    public String getInputText5() {
        return inputText5;
    }

    public void setInputText5(String inputText5) {
        this.inputText5 = inputText5;
    }

    public String getInputText6() {
        return inputText6;
    }

    public void setInputText6(String inputText6) {
        this.inputText6 = inputText6;
    }

    public String getInputText7() {
        return inputText7;
    }

    public void setInputText7(String inputText7) {
        this.inputText7 = inputText7;
    }

    public String getInputText8() {
        return inputText8;
    }

    public void setInputText8(String inputText8) {
        this.inputText8 = inputText8;
    }

    public String getInputText9() {
        return inputText9;
    }

    public void setInputText9(String inputText9) {
        this.inputText9 = inputText9;
    }

    public boolean isValuefrchkOne() {
        return valuefrchkOne;
    }

    public void setValuefrchkOne(boolean valuefrchkOne) {
        this.valuefrchkOne = valuefrchkOne;
    }

    public boolean isCheckBox2() {
        return checkBox2;
    }

    public void setCheckBox2(boolean checkBox2) {
        this.checkBox2 = checkBox2;
    }

    public boolean isCheckBox3() {
        return checkBox3;
    }

    public void setCheckBox3(boolean checkBox3) {
        this.checkBox3 = checkBox3;
    }

    public boolean isCheckBox4() {
        return checkBox4;
    }

    public void setCheckBox4(boolean checkBox4) {
        this.checkBox4 = checkBox4;
    }

    public boolean isCheckBox5() {
        return checkBox5;
    }

    public void setCheckBox5(boolean checkBox5) {
        this.checkBox5 = checkBox5;
    }

    public boolean isCheckBox6() {
        return checkBox6;
    }

    public void setCheckBox6(boolean checkBox6) {
        this.checkBox6 = checkBox6;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<SelectItem> getSelectOption() {
        return selectOption;
    }

    public void setSelectOption(List<SelectItem> selectOption) {
        this.selectOption = selectOption;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SelectItem> getMonthTypeList() {
        return monthTypeList;
    }

    public void setMonthTypeList(List<SelectItem> monthTypeList) {
        this.monthTypeList = monthTypeList;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getAcTypess() {
        return acTypess;
    }

    public void setAcTypess(String acTypess) {
        this.acTypess = acTypess;
    }

    public InterestParameter() {
        try {
            interestMasterRemote = (InterestMasterFacadeRemote) ServiceLocator.getInstance().lookup("InterestMasterFacade");
            setTodayDt(getTodayDate());
            save = true;
            saveButton = true;
            loadAcctCode();
            setAcTypess("--Select--");
            setOption("--Select--");
            setMonth("--Select--");
        } catch (ApplicationException e) {
            setMsgFrInterest(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsgFrInterest(e.getLocalizedMessage());
        }
    }

    public void loadAcctCode() {
        try {
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("--Select--"));
            List accountList = interestMasterRemote.acctCodeInterestParameter();
            for (int i = 0; i < accountList.size(); i++) {
                Vector ele = (Vector) accountList.get(i);
                for (Object o : ele) {
                    acTypeList.add(new SelectItem(o.toString()));
                }
            }
            monthTypeList = new ArrayList<SelectItem>();
            monthTypeList.add(new SelectItem("--Select--"));
            monthTypeList.add(new SelectItem("Janauary"));
            monthTypeList.add(new SelectItem("February"));
            monthTypeList.add(new SelectItem("March"));
            monthTypeList.add(new SelectItem("April"));
            monthTypeList.add(new SelectItem("May"));
            monthTypeList.add(new SelectItem("June"));
            monthTypeList.add(new SelectItem("July"));
            monthTypeList.add(new SelectItem("August"));
            monthTypeList.add(new SelectItem("September"));
            monthTypeList.add(new SelectItem("October "));
            monthTypeList.add(new SelectItem("November"));
            monthTypeList.add(new SelectItem("December"));
            selectOption = new ArrayList<SelectItem>();
            selectOption.add(new SelectItem("--Select--"));
            selectOption.add(new SelectItem("half Yearly"));
            selectOption.add(new SelectItem("Quartely"));
            selectOption.add(new SelectItem("monthly"));
            inputDis1 = true;
            inputDis2 = true;
            inputDis3 = true;
            inputDis4 = true;
            inputDis5 = true;
            inputDis6 = true;
            inputDis7 = true;
            inputDis8 = true;
            inputDis9 = true;
            inputDis10 = true;
            inputDis11 = true;
            checkBox = true;
            checkBox2 = true;
            checkBox3 = true;
            checkBox4 = true;
            checkBox5 = true;
            checkBox6 = true;
        } catch (ApplicationException e) {
            setMsgFrInterest(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsgFrInterest(e.getLocalizedMessage());
        }
    }

    public void InterestType() {
        try {
            String st1 = option;
            if (st1.equals("half Yearly")) {
                checkBox = false;
                checkBox2 = false;
                checkBox3 = true;
                checkBox4 = true;
                checkBox5 = true;
                checkBox6 = true;
                inputDis1 = false;
                inputDis2 = false;
                inputDis3 = false;
                inputDis4 = true;
                inputDis5 = true;
                inputDis6 = true;
                inputDis7 = true;
                inputDis8 = true;
                inputDis9 = true;
                inputDis10 = true;
                inputDis11 = true;
                setMonth("April");
                setInputText1("September");
                setInputText2("October");
                setInputText3("March");
                setInputText4("");
                setInputText5("");
                setInputText6("");
                setInputText7("");
                setInputText8("");
                setInputText9("");
                setInputText10("");
                setInputText11("");
            } else if (st1.equals("Quartely")) {
                checkBox = false;
                checkBox2 = false;
                checkBox3 = false;
                checkBox4 = false;
                checkBox5 = true;
                checkBox6 = true;
                inputDis1 = false;
                inputDis2 = false;
                inputDis3 = false;
                inputDis4 = false;
                inputDis5 = false;
                inputDis6 = false;
                inputDis7 = false;
                inputDis8 = true;
                inputDis9 = true;
                inputDis10 = true;
                inputDis11 = true;
                setMonth("April");
                setInputText1("June");
                setInputText2("July");
                setInputText3("September");
                setInputText4("October");
                setInputText5("December");
                setInputText6("Janaury");
                setInputText7("March");
                setInputText8("");
                setInputText9("");
                setInputText10("");
                setInputText11("");
            } else if (st1.equals("monthly")) {
                checkBox = false;
                checkBox2 = false;
                checkBox3 = false;
                checkBox4 = false;
                checkBox5 = false;
                checkBox6 = false;
                inputDis1 = false;
                inputDis2 = false;
                inputDis3 = false;
                inputDis4 = false;
                inputDis5 = false;
                inputDis6 = false;
                inputDis7 = false;
                inputDis8 = false;
                inputDis9 = false;
                inputDis10 = false;
                inputDis11 = false;
                setMonth("April");
                setInputText1("May");
                setInputText2("June");
                setInputText3("July");
                setInputText4("August");
                setInputText5("September");
                setInputText6("October");
                setInputText7("November");
                setInputText8("December");
                setInputText9("janauary");
                setInputText10("February");
                setInputText11("March");
            }
            saveButton = false;
        } catch (Exception e) {
            setMsgFrInterest(e.getLocalizedMessage());
        }
    }

    public void eventClicked() {
    }

    public void save() {
        try {
            if (getAcTypess().equals("--Select--")) {
                setMsgFrInterest("PLesae Select The Appropriate Option From The List");
                return;
            }
            if (getOption().equals("--Select--")) {
                setMsgFrInterest("PLesae Select The Appropriate Option From The List");
                return;
            }
            if (getMonth().equals("--Select--")) {
                setMsgFrInterest("PLesae Select The Appropriate Option From The List");
                return;
            }
            if (getInputText1().equals("")) {
                setMsgFrInterest("PLease fill The all Entries...");
                return;
            }
            if (getInputText2().equals("")) {
                setMsgFrInterest("PLease fill The all Entries...");
                return;
            }
            if (getInputText3().equals("")) {
                setMsgFrInterest("PLease fill The all Entries...");
                return;
            }
            if (getInputText4().equals("")) {
                setMsgFrInterest("PLease fill The all Entries...");
                return;
            }
            if (getInputText5().equals("")) {
                setMsgFrInterest("PLease fill The all Entries...");
                return;
            }
            if (getInputText6().equals("")) {
                setMsgFrInterest("PLease fill The all Entries...");
                return;
            }
            if (getInputText7().equals("")) {
                setMsgFrInterest("PLease fill The all Entries...");
                return;
            }
            if (getInputText8().equals("")) {
                setMsgFrInterest("PLease fill The all Entries...");
                return;
            }
            if (getInputText9().equals("")) {
                setMsgFrInterest("PLease fill The all Entries...");
                return;
            }
            if (getInputText10().equals("")) {
                setMsgFrInterest("PLease fill The all Entries...");
                return;
            }
            if (getInputText11().equals("")) {
                setMsgFrInterest("PLease fill The all Entries...");
                return;
            }
            String acNature = acTypess;
            String interestNature = option;
            if (interestNature.equals("half Yearly")) {
                boolean check1 = valuefrchkOne;
                boolean check2 = valueFrchkTwo;
                String st1 = interestMasterRemote.saveFrHalfInterestParameter(acNature, check1, check2);
                if (st1.equals("transaction successful")) {
                    setMsgFrInterest("Transaction Has Been Successful");
                } else {
                    setMsgFrInterest("Transaction Has Not Been Successful");
                }
            } else if (interestNature.equals("Quartely")) {
                String st1 = interestMasterRemote.saveFrQuartInterestParameter(acNature, valuefrchkOne, valueFrchkTwo, valuefrchkThree, valueFrchkFour);
                if (st1.equals("transaction successful")) {
                    setMsgFrInterest("Transaction Has Been Successful");
                } else {
                    setMsgFrInterest("Transaction Has Not Been Successful");
                }
            } else if (interestNature.equals("monthly")) {
                String st1 = interestMasterRemote.saveFrMonthlyInterestParameter(acNature, valuefrchkOne, valueFrchkTwo, valuefrchkThree, valueFrchkFour, valuefrchkFive, valueFrchkSix);
                if (st1.equals("transaction successful")) {
                    setMsgFrInterest("Transaction Has Been Successful");
                } else {
                    setMsgFrInterest("Transaction Has Not Been Successful");
                }
            }
        } catch (ApplicationException e) {
            setMsgFrInterest(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsgFrInterest(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        setAcTypess("--Select--");
        setOption("--Select--");
        setMonth("--Select--");
        setInputText1("");
        setInputText2("");
        setInputText3("");
        setInputText4("");
        setInputText5("");
        setInputText6("");
        setInputText7("");
        setInputText8("");
        setInputText9("");
        setInputText10("");
        setInputText11("");
    }

    public String exitForm() {
        refresh();
        return "case1";
    }
}