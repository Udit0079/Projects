package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public final class PassBookParam extends BaseBean {

    List<SelectItem> list2;
    List<SelectItem> list3;
    List<SelectItem> list4;
    List<SelectItem> list5;
    List<SelectItem> list6;
    List<SelectItem> list7;
    String item1;
    String item2;
    String item3;
    String item4;
    String item5;
    String item6;
    private GeneralMasterFacadeRemote generalFacadeRemote;
    String passline;
    String halfline;
    String skipline;
    String beginlines;
    String inputText1;
    String inputText2;
    String inputText3;
    String inputText4;
    String inputText5;
    String inputText6;
    String inputText7;
    String inputText8;
    String inputText9;
    String inputText10;
    String inputText11;
    String inputText12;
    String message;
    boolean saveButton;

    public String getBeginlines() {
        return beginlines;
    }

    public void setBeginlines(String beginlines) {
        this.beginlines = beginlines;
    }

    public String getHalfline() {
        return halfline;
    }

    public void setHalfline(String halfline) {
        this.halfline = halfline;
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

    public String getInputText12() {
        return inputText12;
    }

    public void setInputText12(String inputText12) {
        this.inputText12 = inputText12;
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

    public String getPassline() {
        return passline;
    }

    public void setPassline(String passline) {
        this.passline = passline;
    }

    public String getSkipline() {
        return skipline;
    }

    public void setSkipline(String skipline) {
        this.skipline = skipline;
    }

    public boolean isSaveButton() {
        return saveButton;
    }

    public void setSaveButton(boolean saveButton) {
        this.saveButton = saveButton;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getItem5() {
        return item5;
    }

    public void setItem5(String item5) {
        this.item5 = item5;
    }

    public String getItem6() {
        return item6;
    }

    public void setItem6(String item6) {
        this.item6 = item6;
    }

    public List<SelectItem> getList3() {
        return list3;
    }

    public void setList3(List<SelectItem> list3) {
        this.list3 = list3;
    }

    public List<SelectItem> getList4() {
        return list4;
    }

    public void setList4(List<SelectItem> list4) {
        this.list4 = list4;
    }

    public List<SelectItem> getList5() {
        return list5;
    }

    public void setList5(List<SelectItem> list5) {
        this.list5 = list5;
    }

    public List<SelectItem> getList6() {
        return list6;
    }

    public void setList6(List<SelectItem> list6) {
        this.list6 = list6;
    }

    public List<SelectItem> getList7() {
        return list7;
    }

    public void setList7(List<SelectItem> list7) {
        this.list7 = list7;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public List<SelectItem> getList2() {
        return list2;
    }

    public void setList2(List<SelectItem> list2) {
        this.list2 = list2;
    }

    public PassBookParam() {
        try {
            generalFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            saveButton = true;
            onLoad();
            onLoad2();
            setDetail();
            saveButton = false;
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setDetail() {
        try {
            List listD = new ArrayList();
            listD = generalFacadeRemote.dataPassBookDisplay();
            for (int k = 0; k < listD.size(); k++) {
                Vector ele1 = (Vector) listD.get(k);
                if (ele1.get(1).toString().equalsIgnoreCase("1")) {
                    setItem1(ele1.get(0).toString());
                    setInputText1(ele1.get(2).toString());
                    setInputText2(ele1.get(3).toString());
                } else if (ele1.get(1).toString().equalsIgnoreCase("2")) {
                    setItem2(ele1.get(0).toString());
                    setInputText3(ele1.get(2).toString());
                    setInputText4(ele1.get(3).toString());
                } else if (ele1.get(1).toString().equalsIgnoreCase("3")) {
                    setItem3(ele1.get(0).toString());
                    setInputText5(ele1.get(2).toString());
                    setInputText6(ele1.get(3).toString());
                } else if (ele1.get(1).toString().equalsIgnoreCase("4")) {
                    setItem4(ele1.get(0).toString());
                    setInputText7(ele1.get(2).toString());
                    setInputText8(ele1.get(3).toString());
                } else if (ele1.get(1).toString().equalsIgnoreCase("5")) {
                    setItem5(ele1.get(0).toString());
                    setInputText9(ele1.get(2).toString());
                    setInputText10(ele1.get(3).toString());
                } else if (ele1.get(1).toString().equalsIgnoreCase("6")) {
                    setItem6(ele1.get(0).toString());
                    setInputText11(ele1.get(2).toString());
                    setInputText12(ele1.get(3).toString());
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onLoad() {
        try {
            list2 = new ArrayList<SelectItem>();
            list2.add(new SelectItem("--Select--"));
            List dataset = new ArrayList();
            dataset = generalFacadeRemote.dataloadPassBook();
            for (int i = 0; i < dataset.size(); i++) {
                Vector ele = (Vector) dataset.get(i);
                for (Object o : ele) {
                    list2.add(new SelectItem(o.toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onLoad2() {
        try {
            List dataset = new ArrayList();
            dataset = generalFacadeRemote.dataload2PassBook();
            for (int k = 0; k < dataset.size(); k++) {
                Vector ele1 = (Vector) dataset.get(k);
                {
                    if ((ele1.get(0).toString()).equals("PassLine")) {
                        setPassline(ele1.get(1).toString());
                    } else if ((ele1.get(0).toString()).equals("HalfLine")) {
                        setHalfline(ele1.get(1).toString());
                    } else if ((ele1.get(0).toString()).equals("SkipLine")) {
                        setSkipline(ele1.get(1).toString());
                    } else if ((ele1.get(0).toString()).equals("BeginLines")) {
                        setBeginlines(ele1.get(1).toString());
                    }
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void cmdOk() {
        try {
            String st1 = this.item1;
            String st2 = this.item2;
            String st3 = this.item3;
            String st4 = this.item4;
            String st5 = this.item5;
            String st6 = this.item6;
            if (st1.equals("--Select--") || st2.equals("--Select--") || st3.equals("--Select--") || st4.equals("--Select--") || st5.equals("--Select--") || st6.equals("--Select--")) {
                this.setMessage("Please Check All The Values In Every ListBox..");
                return;
            }
            if (getInputText1().equals("") || getInputText2().equals("") || getInputText3().equals("") || getInputText4().equals("") || getInputText5().equals("") || getInputText6().equals("") || getInputText7().equals("") || getInputText8().equals("") || getInputText9().equals("") || getInputText10().equals("") || getInputText11().equals("") || getInputText12().equals("") || getHalfline().equals("") || getSkipline().equals("") || getBeginlines().equals("") || getPassline().equals("")) {
                this.setMessage("PLease Check The all Text Fields, Text Field Cannot Be Empty..");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getInputText1());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setInputText1("");
                return;
            }
            billNoCheck = p.matcher(this.getInputText2());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setInputText2("");
                return;
            }
            billNoCheck = p.matcher(this.getInputText3());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setInputText3("");
                return;
            }
            billNoCheck = p.matcher(this.getInputText4());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setInputText4("");
                return;
            }
            billNoCheck = p.matcher(this.getInputText5());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setInputText5("");
                return;
            }
            billNoCheck = p.matcher(this.getInputText6());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setInputText6("");
                return;
            }
            billNoCheck = p.matcher(this.getInputText7());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setInputText7("");
                return;
            }
            billNoCheck = p.matcher(this.getInputText8());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setInputText8("");
                return;
            }
            billNoCheck = p.matcher(this.getInputText9());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setInputText9("");
                return;
            }
            billNoCheck = p.matcher(this.getInputText10());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setInputText10("");
                return;
            }
            billNoCheck = p.matcher(this.getInputText11());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setInputText11("");
                return;
            }
            billNoCheck = p.matcher(this.getInputText12());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setInputText12("");
                return;
            }
            billNoCheck = p.matcher(this.getPassline());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setPassline("");
                return;
            }
            billNoCheck = p.matcher(this.getSkipline());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setSkipline("");
                return;
            }
            billNoCheck = p.matcher(this.getHalfline());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setHalfline("");
                return;
            }
            billNoCheck = p.matcher(this.getBeginlines());
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Valid Numeric Value For All Text Field");
                this.setBeginlines("");
                return;
            }
            if (Integer.parseInt(getInputText1()) < 0 || Integer.parseInt(getInputText2()) < 0 || Integer.parseInt(getInputText3()) < 0 || Integer.parseInt(getInputText4()) < 0 || Integer.parseInt(getInputText5()) < 0 || Integer.parseInt(getInputText6()) < 0 || Integer.parseInt(getInputText7()) < 0 || Integer.parseInt(getInputText8()) < 0 || Integer.parseInt(getInputText9()) < 0 || Integer.parseInt(getInputText10()) < 0 || Integer.parseInt(getInputText11()) < 0 || Integer.parseInt(getInputText12()) < 0 || Integer.parseInt(getHalfline()) < 0 || Integer.parseInt(getSkipline()) < 0 || Integer.parseInt(getBeginlines()) < 0 || Integer.parseInt(getPassline()) < 0) {
                this.setMessage("PLease Check The all Text Fields, Text Field Cannot Contain Negative Value..");
                return;
            }
            int value1 = Integer.parseInt(this.getPassline());
            int value2 = Integer.parseInt(this.getHalfline());
            int value3 = Integer.parseInt(this.getSkipline());
            int value4 = Integer.parseInt(this.getBeginlines());
            int inputvalue1 = Integer.parseInt(this.getInputText1());
            int inputvalue2 = Integer.parseInt(this.getInputText2());
            int inputvalue3 = Integer.parseInt(this.getInputText3());
            int inputvalue4 = Integer.parseInt(this.getInputText4());
            int inputvalue5 = Integer.parseInt(this.getInputText5());
            int inputvalue6 = Integer.parseInt(this.getInputText6());
            int inputvalue7 = Integer.parseInt(this.getInputText7());
            int inputvalue8 = Integer.parseInt(this.getInputText8());
            int inputvalue9 = Integer.parseInt(this.getInputText9());
            int inputvalue10 = Integer.parseInt(this.getInputText10());
            int inputvalue11 = Integer.parseInt(this.getInputText11());
            int inputvalue12 = Integer.parseInt(this.getInputText12());
            String string2 = entryMatch(st1, st2, st3, st4, st5, st6);
            if (string2.equals("CANNOT ENTER DUPLICATE VALUE FOR LIST")) {
                this.setMessage("Not Allow Duplicate Entry in ListBox..Each Selected Option Should Be Distinct..");
            } else {
                String String1 = generalFacadeRemote.okButtonPassBook(value1, value2, value3, value4, st1, st2, st3, st4, st5, st6, inputvalue1, inputvalue2, inputvalue3, inputvalue4, inputvalue5, inputvalue6, inputvalue7, inputvalue8, inputvalue9, inputvalue10, inputvalue11, inputvalue12);
                if (String1.equals("transaction successful")) {
                    this.setMessage("Data Successfully Saved");
                    this.setItem1("--Select--");
                    this.setItem2("--Select--");
                    this.setItem3("--Select--");
                    this.setItem4("--Select--");
                    this.setItem5("--Select--");
                    this.setItem6("--Select--");
                    this.setInputText1("");
                    this.setInputText2("");
                    this.setInputText3("");
                    this.setInputText4("");
                    this.setInputText5("");
                    this.setInputText6("");
                    this.setInputText7("");
                    this.setInputText8("");
                    this.setInputText9("");
                    this.setInputText10("");
                    this.setInputText11("");
                    this.setInputText12("");
                    setDetail();
                } else {
                    this.setMessage("Database Not Saved Successfully..");
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String entryMatch(String st1, String st2, String st3, String st4, String st5, String st6) {
        try {
            if (st1.equals(st2)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st1.equals(st3)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st1.equals(st4)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st1.equals(st5)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st1.equals(st6)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st2.equals(st3)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st2.equals(st4)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st2.equals(st5)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st2.equals(st6)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st3.equals(st2)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st3.equals(st4)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st3.equals(st5)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st3.equals(st6)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st4.equals(st5)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st4.equals(st6)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else if (st5.equals(st6)) {
                return ("CANNOT ENTER DUPLICATE VALUE FOR LIST");
            } else {
                return ("not any dupliacte entry");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return (null);
        }
    }

    public void refresh() {
        this.setMessage("");
        this.setItem1("--Select--");
        this.setItem2("--Select--");
        this.setItem3("--Select--");
        this.setItem4("--Select--");
        this.setItem5("--Select--");
        this.setItem6("--Select--");
        this.setInputText1("");
        this.setInputText2("");
        this.setInputText3("");
        this.setInputText4("");
        this.setInputText5("");
        this.setInputText6("");
        this.setInputText7("");
        this.setInputText8("");
        this.setInputText9("");
        this.setInputText10("");
        this.setInputText11("");
        this.setInputText12("");
        onLoad();
        onLoad2();
        setDetail();
    }

    public String exitForm() {
        refresh();
        return "case1";
    }
}
