/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.facade.master.TermDepositMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.TableDetails;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
/**
 *
 * @author sipl
 */
public final class termDepositHis extends BaseBean{
    private TableDetails tableDt;
    private List<TableDetails> tableDtList;
    private List CurrentDate;
    private Iterable<TableDetails> griddata;
    TermDepositMasterFacadeRemote tdRemote;
    OtherMasterFacadeRemote otherMasterRemote;
    private String nature;
    private List<SelectItem> natureList;
    
    public List getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(List CurrentDate) {
        this.CurrentDate = CurrentDate;
    }

    public Iterable<TableDetails> getGriddata() {
        return griddata;
    }

    public void setGriddata(Iterable<TableDetails> griddata) {
        this.griddata = griddata;
    }

    public TableDetails getTableDt() {
        return tableDt;
    }

    public void setTableDt(TableDetails tableDt) {
        this.tableDt = tableDt;
    }

    public List<TableDetails> getTableDtList() {
        return tableDtList;
    }

    public void setTableDtList(List<TableDetails> tableDtList) {
        this.tableDtList = tableDtList;
    }   

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public List<SelectItem> getNatureList() {
        return natureList;
    }

    public void setNatureList(List<SelectItem> natureList) {
        this.natureList = natureList;
    }
    
    public termDepositHis() {
        try {
            tdRemote = (TermDepositMasterFacadeRemote) ServiceLocator.getInstance().lookup("TermDepositMasterFacade");
            otherMasterRemote = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
            tableDtList = new ArrayList<TableDetails>();
            getdata();            
        } catch (ApplicationException e) {
            e.getLocalizedMessage();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    } 
    
    public void getdata() {
        try {
            List acnoDataList = new ArrayList();
            acnoDataList = otherMasterRemote.getData();
            natureList = new ArrayList<SelectItem>();
            for (int i = 0; i < acnoDataList.size(); i++) {
                Vector ele = (Vector) acnoDataList.get(i);
                for (Object ee : ele) {
                    natureList.add(new SelectItem(ee.toString(), ee.toString()));
                }
            }            
        } catch (ApplicationException e) {
            e.getLocalizedMessage();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }
    
    public void changeFunction(){
        getTableHistory(this.getNature());
    }

    public void getTableHistory(String acNat) {
        try {
            tableDtList = new ArrayList();
            List list = new ArrayList();
            list = tdRemote.getTableHistry(acNat);
            if (list.isEmpty()) {
            } else {
                for (int j = 0; j < list.size(); j++) {
                    Vector element = (Vector) list.get(j);
                    tableDt = new TableDetails();
                    tableDt.setSeqNo(element.get(0).toString());
                    tableDt.setApplicableDate(element.get(1).toString());
                    tableDt.setIntRate((element.get(2).toString()));
                    tableDt.setFromPeriod(element.get(3).toString());
                    tableDt.setToPeriod(element.get(4).toString());
                    tableDt.setFromAmt(new BigDecimal(Float.parseFloat(element.get(5).toString())));
                    tableDt.setToAmt(new BigDecimal(Float.parseFloat(element.get(6).toString())));
                    tableDt.setAddIntSr((element.get(7).toString()));
                    tableDt.setAddIntSt((element.get(8).toString()));
                    tableDt.setAddIntOt((element.get(9).toString()));
                    tableDt.setAcNature((element.get(10).toString()));
                    tableDt.setAddIntMg((element.get(11).toString()));
                    tableDtList.add(tableDt);
                }
            }
        } catch (ApplicationException e) {
            e.getLocalizedMessage();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }
    
    public void refreshForm() {
        try {
            tableDtList = new ArrayList();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }   

    public String exitForm() {
        refreshForm();
        return "case1";
    }
    
}