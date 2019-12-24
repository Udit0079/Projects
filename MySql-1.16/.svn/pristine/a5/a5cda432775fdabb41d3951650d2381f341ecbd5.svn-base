/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.dto.FidilityTablePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author sipl
 */
public class FidelityPremiumSlab extends BaseBean {
    private String message;
    private String desgCd;
    private List<SelectItem> desgCdList;
    private String bondAmt;
    private String prAmt;
    private String prd;
    private Date effDt;
    private String crHead;
    private String drHead;
    FidilityTablePojo currentItem;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private boolean effFlg;
    private boolean dsgFlg;
    private GeneralMasterFacadeRemote genFacadeRemote;
    private List<FidilityTablePojo> fiDir;
    private boolean saveDisable;
    private boolean updateDisable;

    public String getBondAmt() {
        return bondAmt;
    }

    public void setBondAmt(String bondAmt) {
        this.bondAmt = bondAmt;
    }

    public String getCrHead() {
        return crHead;
    }

    public void setCrHead(String crHead) {
        this.crHead = crHead;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDesgCd() {
        return desgCd;
    }

    public void setDesgCd(String desgCd) {
        this.desgCd = desgCd;
    }

    public String getDrHead() {
        return drHead;
    }

    public void setDrHead(String drHead) {
        this.drHead = drHead;
    }

    public boolean isDsgFlg() {
        return dsgFlg;
    }

    public void setDsgFlg(boolean dsgFlg) {
        this.dsgFlg = dsgFlg;
    }

    public Date getEffDt() {
        return effDt;
    }

    public void setEffDt(Date effDt) {
        this.effDt = effDt;
    }

    public boolean isEffFlg() {
        return effFlg;
    }

    public void setEffFlg(boolean effFlg) {
        this.effFlg = effFlg;
    }

    public List<FidilityTablePojo> getFiDir() {
        return fiDir;
    }

    public void setFiDir(List<FidilityTablePojo> fiDir) {
        this.fiDir = fiDir;
    }    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrAmt() {
        return prAmt;
    }

    public void setPrAmt(String prAmt) {
        this.prAmt = prAmt;
    }

    public String getPrd() {
        return prd;
    }

    public void setPrd(String prd) {
        this.prd = prd;
    }

    public FidilityTablePojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(FidilityTablePojo currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public boolean isUpdateDisable() {
        return updateDisable;
    }

    public void setUpdateDisable(boolean updateDisable) {
        this.updateDisable = updateDisable;
    }

    public List<SelectItem> getDesgCdList() {
        return desgCdList;
    }

    public void setDesgCdList(List<SelectItem> desgCdList) {
        this.desgCdList = desgCdList;
    }
    
    public FidelityPremiumSlab() {
        try {
            genFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            onLoad();
            fiDir = new ArrayList<FidilityTablePojo>();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void onLoad() {
        try {
            desgCdList = new ArrayList<SelectItem>();
            desgCdList.add(new SelectItem("0", "--Select--"));
            List glList = genFacadeRemote.getRefCodeAndDescByNo("020");
            if (glList.isEmpty()) {
                this.setMessage("Please fill data in CBS REF REC TYPE for 020");
                return;
            } else {
                for (int i = 0; i < glList.size(); i++) {
                    Vector v1 = (Vector) glList.get(i);
                    desgCdList.add(new SelectItem(v1.get(0).toString()));
                }                
            }
            this.setDsgFlg(false);
            this.setEffFlg(false);
            this.setSaveDisable(false);
            this.setUpdateDisable(true);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void getData(){
        this.setMessage("");
        fiDir = new ArrayList<FidilityTablePojo>();
        try {
            List<FidilityTablePojo> resultList = genFacadeRemote.getFidilityDetailsList(this.desgCd);
            if (!resultList.isEmpty()) {
                for (FidilityTablePojo entity : resultList) {
                    FidilityTablePojo pojo = new FidilityTablePojo();
                    pojo.setDsgCode(entity.getDsgCode());
                    pojo.setDsgDesc(entity.getDsgDesc());
                    pojo.setBondAmount(entity.getBondAmount());
                    pojo.setPrAmount(entity.getPrAmount());
                    pojo.setPeriod(entity.getPeriod());
                    pojo.setCrGl(entity.getCrGl());
                    pojo.setDrGl(entity.getDrGl());
                    pojo.setEffDt(entity.getEffDt());                    
                    fiDir.add(pojo);
                }                
            } else {
                this.setMessage("There is no data.");
            }            
        }catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public void save() {
        if (validation().equalsIgnoreCase("False")) {
            return;
        }
        String msg;
        try {
            msg = genFacadeRemote.saveFidilityData("save",this.getDesgCd(),Double.parseDouble(this.getBondAmt()), Double.parseDouble(this.getPrAmt()),
                    Integer.parseInt(this.getPrd()),this.getCrHead(),this.getDrHead(),ymd.format(this.getEffDt()),this.getUserName());
            if (msg.equalsIgnoreCase("Data saved successfully")) {
                this.setSaveDisable(false);
                this.setUpdateDisable(true);
                this.setBondAmt("");
                this.setPrAmt("");
                this.setPrd("");
                this.setCrHead("");
                this.setDrHead("");
                this.setEffDt(date);
                setMessage(msg);
                getData();
            } else {
                setMessage(msg);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void update() {
        try {
            String msg;
            if (validation().equalsIgnoreCase("False")) {
                return;
            }
            msg = genFacadeRemote.saveFidilityData("update",this.getDesgCd(),Double.parseDouble(this.getBondAmt()), Double.parseDouble(this.getPrAmt()),
                    Integer.parseInt(this.getPrd()),this.getCrHead(),this.getDrHead(),ymd.format(this.getEffDt()),this.getUserName());
            if (msg.equalsIgnoreCase("Data Update Successfully.")) {
                this.setSaveDisable(true);
                this.setUpdateDisable(false);
                this.setBondAmt("");
                this.setPrAmt("");
                this.setPrd("");
                this.setCrHead("");
                this.setDrHead("");
                this.setEffDt(date);
                setMessage(msg);
                getData();
            } else {
                setMessage(msg);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void select() {
        this.setMessage("");
        try {
            this.setDesgCd(currentItem.getDsgCode());
            this.setBondAmt(currentItem.getBondAmount());
            this.setPrAmt(currentItem.getPrAmount());
            this.setPrd(currentItem.getPeriod());
            this.setCrHead(currentItem.getCrGl());
            this.setDrHead(currentItem.getDrGl());
            this.setEffDt(sdf.parse(currentItem.getEffDt()));
            this.setDsgFlg(true);
            this.setEffFlg(true);
            setSaveDisable(true);
            setUpdateDisable(false);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public String validation() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.desgCd.equalsIgnoreCase("0")) {
                this.setMessage("Please Select Designation Code.");
                return "false";
            }
            
            if (this.bondAmt == null || this.bondAmt.toString().equalsIgnoreCase("")) {
                this.setMessage("Please Fill Bond Amount Field.");
                return "false";
            } else if (this.bondAmt.toString().equalsIgnoreCase("0") || this.bondAmt.toString().equalsIgnoreCase("0.0")) {
                this.setMessage("Please Fill Bond Amount Field.");
                return "false";
            } else {
                Matcher bondAmtTxnCheck = p.matcher(this.bondAmt.toString());
                if (!bondAmtTxnCheck.matches()) {
                    this.setMessage("Invalid Entry.");
                    return "false";
                }
            }
            if (validateBondAmt() != true) {
                return "false";
            }
            
            if (this.prAmt == null || this.prAmt.toString().equalsIgnoreCase("")) {
                this.setMessage("Please Fill Premium Amount Field.");
                return "false";
            } else if (this.prAmt.toString().equalsIgnoreCase("0") || this.prAmt.toString().equalsIgnoreCase("0.0")) {
                this.setMessage("Please Fill Prewmium Amount Field.");
                return "false";
            } else {
                Matcher bondAmtTxnCheck = p.matcher(this.prAmt.toString());
                if (!bondAmtTxnCheck.matches()) {
                    this.setMessage("Invalid Entry.");
                    return "false";
                }
            }
            if (validatePrAmt() != true) {
                return "false";
            }
            
            if (prd.equalsIgnoreCase("")) {
                setMessage("Please Enter Period");
                return "false";
            } else {
                Matcher prdCheck = p.matcher(prd);
                if (!prdCheck.matches()) {
                    setMessage("Please enter numeric value for Period");
                    return "false";
                }                
            }
            
            if (crHead.equalsIgnoreCase("")) {
                setMessage("Please Enter Period");
                return "false";
            } else {
                Matcher crGlCheck = p.matcher(crHead);
                if (!crGlCheck.matches()) {
                    setMessage("Please enter proper value for Credit GL Head");
                    return "false";
                }                
            }
            
            if (drHead.equalsIgnoreCase("")) {
                setMessage("Please Enter Period");
                return "false";
            } else {
                Matcher drGlCheck = p.matcher(drHead);
                if (!drGlCheck.matches()) {
                    setMessage("Please enter proper value for Debit GL Head");
                    return "false";
                }                
            }
            return "true";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return "false";
        }
    }
    
    public boolean validateBondAmt() {
        if (this.bondAmt.toString().contains(".")) {
            if (this.bondAmt.toString().indexOf(".") != this.bondAmt.toString().lastIndexOf(".")) {
                this.setMessage("Invalid Amount.Please Fill The Bond Amount Correctly.");
                return false;
            } else if (this.bondAmt.toString().substring(bondAmt.toString().indexOf(".") + 1).length() > 2) {
                this.setMessage("Please Fill The Bond Amount Upto Two Decimal Places.");
                return false;
            } else {
                this.setMessage("");
                return true;
            }
        } else {
            this.setMessage("");
            return true;
        }
    }
    
    public boolean validatePrAmt() {
        if (this.prAmt.toString().contains(".")) {
            if (this.prAmt.toString().indexOf(".") != this.prAmt.toString().lastIndexOf(".")) {
                this.setMessage("Invalid Amount.Please Fill The Premium Amount Correctly.");
                return false;
            } else if (this.prAmt.toString().substring(prAmt.toString().indexOf(".") + 1).length() > 2) {
                this.setMessage("Please Fill The Premium Amount Upto Two Decimal Places.");
                return false;
            } else {
                this.setMessage("");
                return true;
            }
        } else {
            this.setMessage("");
            return true;
        }
    }
    
    public void refresh() {
        setMessage("");
        this.setDesgCd("0");
        this.setBondAmt("");
        this.setPrAmt("");
        this.setPrd("");
        this.setCrHead("");
        this.setDrHead("");
        this.setEffDt(date);
        this.setDsgFlg(false);
        this.setEffFlg(false);
        setSaveDisable(false);
        setUpdateDisable(true);
        fiDir = new ArrayList<FidilityTablePojo>();
    }

    public String exitBtnAction() {
        refresh();
        return "case1";
    }
    
}
