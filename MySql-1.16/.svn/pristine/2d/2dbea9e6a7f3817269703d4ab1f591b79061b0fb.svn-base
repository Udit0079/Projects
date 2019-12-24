/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.dto.GlHeadSearchPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.AcctEnqueryFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author Athar Reza
 */
public class GlHeadSearch extends BaseBean {

    private String message;
    private String searchOption;
    private List<SelectItem> searchOptionList;
    private String glCodeName;
     private String newGlHead = "";
    private List<GlHeadSearchPojo> gridDetail;
    private AcctEnqueryFacadeRemote remoteObject;
    private FtsPostingMgmtFacadeRemote ftsRemote;

    public List<GlHeadSearchPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<GlHeadSearchPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getGlCodeName() {
        return glCodeName;
    }

    public void setGlCodeName(String glCodeName) {
        this.glCodeName = glCodeName;
    }

    public String getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(String searchOption) {
        this.searchOption = searchOption;
    }

    public List<SelectItem> getSearchOptionList() {
        return searchOptionList;
    }

    public void setSearchOptionList(List<SelectItem> searchOptionList) {
        this.searchOptionList = searchOptionList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GlHeadSearch() {
        try {
            remoteObject = (AcctEnqueryFacadeRemote)ServiceLocator.getInstance().lookup("AcctEnqueryFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            searchOptionList = new ArrayList<SelectItem>();
            searchOptionList.add(new SelectItem("0", "--Select--"));
            searchOptionList.add(new SelectItem("1", "GL Code"));
            searchOptionList.add(new SelectItem("2", "GL Name"));

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
//    public void searchAction() {
//    }
    public void getGlDetails() {
        setMessage("");
        try{
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            
            this.newGlHead="";
            if(searchOption.equalsIgnoreCase("0")){
                setMessage("Please select search option !!!");
                return;
            }
            if(glCodeName.equalsIgnoreCase("")){
                setMessage("Please fill Gl Code /Name !!");
                return;
            }
            if(searchOption.equalsIgnoreCase("1")){
                Matcher glCodeCheck = p.matcher(this.glCodeName.toString());
                if (!glCodeCheck.matches()) {
                    this.setMessage("Invalid Entry.");
                    return;
                }
                if(this.glCodeName.length() > 6){
                    setMessage("Please fill Only 6 Digit Gl Code!!");
                    return;
                }
                newGlHead = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + ftsRemote.lPading(this.glCodeName, 6, "0");
            }
                           
            gridDetail = new ArrayList<GlHeadSearchPojo>();
            gridDetail = remoteObject.getGlDetails(searchOption,newGlHead ,glCodeName);
             
        }catch(Exception e){
            setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("");
        this.setSearchOption("0");
        this.setGlCodeName("");
          gridDetail = new ArrayList<GlHeadSearchPojo>();
    }

    public String exitBtnAction() {
        try {
            refreshForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}