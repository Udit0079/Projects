package com.cbs.web.controller.bill;

import com.cbs.dto.master.RemittanceTypeMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.bill.BillCommissionFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

public class RemittanceAmountSlabMaster extends BaseBean {

    private BillCommissionFacadeRemote billFacade;
    private ArrayList<SelectItem> instCode;
    private String insmntCode;
    private int slabcode;
    private String mssg;
    private String flag = "F";
    private String from;
    private String to;
    private Date asOnDate = new Date();
    private ArrayList<RemittanceTypeMaster> list;
    private int currentRow;
    private boolean fromDisable;
    private boolean fromAmountDisable;
    private boolean btnDisable;
    private List<SelectItem> instrumentCodeOption;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formater = new DecimalFormat("#.##");

    public List<SelectItem> getInstrumentCodeOption() {
        return instrumentCodeOption;
    }

    public void setInstrumentCodeOption(List<SelectItem> instrumentCodeOption) {
        this.instrumentCodeOption = instrumentCodeOption;
    }

    public boolean isBtnDisable() {
        return btnDisable;
    }

    public void setBtnDisable(boolean btnDisable) {
        this.btnDisable = btnDisable;
    }

    public boolean isFromAmountDisable() {
        return fromAmountDisable;
    }

    public void setFromAmountDisable(boolean fromAmountDisable) {
        this.fromAmountDisable = fromAmountDisable;
    }

    public boolean isFromDisable() {
        return fromDisable;
    }

    public void setFromDisable(boolean fromDisable) {
        this.fromDisable = fromDisable;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMssg() {
        return mssg;
    }

    public void setMssg(String mssg) {
        this.mssg = mssg;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    RemittanceTypeMaster currentItem = new RemittanceTypeMaster();

    public RemittanceTypeMaster getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(RemittanceTypeMaster currentItem) {
        this.currentItem = currentItem;
    }

    public ArrayList<RemittanceTypeMaster> getList() {
        return list;
    }

    public void setList(ArrayList<RemittanceTypeMaster> list) {
        this.list = list;
    }

    public String getInsmntCode() {
        return insmntCode;
    }

    public void setInsmntCode(String insmntCode) {
        this.insmntCode = insmntCode;
    }

    public Date getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(Date asOnDate) {
        this.asOnDate = asOnDate;
    }

    public int getSlabcode() {
        return slabcode;
    }

    public void setSlabcode(int slabcode) {
        this.slabcode = slabcode;
    }

    public ArrayList<SelectItem> getInstCode() {
        return instCode;
    }

    public void setInstCode(ArrayList<SelectItem> instCode) {
        this.instCode = instCode;
    }

    public RemittanceAmountSlabMaster() {
        try {
            billFacade = (BillCommissionFacadeRemote) ServiceLocator.getInstance().lookup("BillCommissionFacade");
            getInstrumentCode();
//            fromAmountDisable = true;
            list = new ArrayList<RemittanceTypeMaster>();
            this.setFrom("0");
            this.setTo("0");
            this.setBtnDisable(true);
        } catch (Exception e) {
            this.setMssg(e.getMessage());
        }
    }

    public void getInstrumentCode() {
        try {
            instrumentCodeOption = new ArrayList<SelectItem>();
            instrumentCodeOption.add(new SelectItem("--Select--"));
            List resultList = billFacade.getInstrumentCodeData();
            if (!resultList.isEmpty()) {
                instrumentCodeOption = new ArrayList<SelectItem>();
                instrumentCodeOption.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        instrumentCodeOption.add(new SelectItem(ee.toString()));
                    }
                }
            } else {
                this.setMssg("Please fill data in billtypemaster.");
            }
        } catch (Exception ex) {
            this.setMssg(ex.getMessage());
        }
    }

    public void getInstSlabDetails() throws NamingException {
//        fromAmountDisable = true;
        this.setMssg("");
        try {
            if (insmntCode == null) {
                this.setMssg("Please select the Instrument Code");
                return;
            }
            if (insmntCode.equalsIgnoreCase("--Select--")) {
                this.setMssg("Please select the Instrument Code");
                return;
            }
            list = new ArrayList<RemittanceTypeMaster>();
            List gridLt = billFacade.amountSlabTable(insmntCode);
            if (!gridLt.isEmpty()) {
                for (int i = 0; i < gridLt.size(); i++) {
                    Vector ele = (Vector) gridLt.get(i);
                    RemittanceTypeMaster rd = new RemittanceTypeMaster();

                    rd.setsNo(Integer.parseInt(ele.get(0).toString()));
                    rd.setInstCode(ele.get(1).toString());
                    rd.setSlabCode(Integer.parseInt(ele.get(2).toString()));

                    rd.setAmtFrom(new BigDecimal(formater.format(ele.get(3))));
                    rd.setAmtTo(new BigDecimal(formater.format(ele.get(4))));
                    rd.setEffDt((String) ele.get(5));
                    rd.setFlag((String) ele.get(6));

                    list.add(rd);
                }
                RemittanceTypeMaster obj = list.get(list.size() - 1);
                setSlabcode(obj.getSlabCode() + 1);
            } else {
                //this.setFrom("0");
                this.setSlabcode(1);
                this.setMssg("There is no data for this instrument code.");
            }
        } catch (Exception ex) {
            this.setMssg(ex.getMessage());
        }
    }

    public void delete() throws NamingException {
        try {
            this.setFlag("D");
            String result = billFacade.amountSalbDeleteRemaittance(insmntCode, currentItem.getSlabCode(), currentItem.getsNo());
            if (result.equalsIgnoreCase("True")) {
                this.setMssg("Instrument Code Deleted Successfully");
                list.remove(currentItem);
                this.setFlag("F");
            }
        } catch (ApplicationException ex) {
            this.setMssg(ex.getMessage());
        }

    }

    public void saveClick() throws NamingException {
        try {
            if (onblurChecking().equalsIgnoreCase("false")) {
                return;
            }
            RemittanceTypeMaster rtm = new RemittanceTypeMaster();
            if (Float.parseFloat(to) <= Float.parseFloat(from)) {
                this.setMssg("Amount To must be greater than From Amount ");
                rtm.setAmtTo(new BigDecimal(to));
                return;
            }
            rtm.setInstCode(insmntCode);
            rtm.setSlabCode(slabcode);
            rtm.setAmtFrom(new BigDecimal(from));
            rtm.setAmtTo(new BigDecimal(to));
            rtm.setEffDt(dmy.format(asOnDate));
            rtm.setFlag("F");
            String tdate = ymd.format(new Date());
            String result = billFacade.saveSlabMasterRemaittance(rtm, getUserName(), tdate);
            this.setMssg(result);
            getInstSlabDetails();
            setFrom("0.0");
            setTo("0");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String onblurChecking() {
        this.setMssg("");
        try {
            if (asOnDate == null) {
                this.setMssg("Please fill the AS ON #");
                return "false";
            }
            if (from == null || from.equalsIgnoreCase("")) {
                this.setMssg("Please enter numeric value in From");
                return "false";
            } else {
                if (!from.matches("[0-9.]*")) {
                    this.setMssg("Please enter numeric value in From");
                    return "false";
                }
            }
            if (to == null || to.equalsIgnoreCase("")) {
                this.setMssg("Please enter numeric value in To");
                return "false";
            } else {
                if (!to.matches("[0-9.]*")) {
                    this.setMssg("Please enter numeric value in To");
                    return "false";
                }
            }
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "true";
    }

    public void refreshBtn() {
        try {
            this.setMssg("");
            list = new ArrayList<RemittanceTypeMaster>();
            setInsmntCode("--Select--");
            setFrom("");
            setTo("0");
            btnDisable = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String exitForm() {
        refreshBtn();
        return "case1";
    }
}
