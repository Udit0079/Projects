package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.InventoryReportPojo;
import com.cbs.dto.report.InventoryTransferReceiptTable;
import com.cbs.dto.report.IssueChequeBookRegisterPojo;
import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName = "InventoryReportFacade")
public class InventoryReportFacade implements InventoryReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;

    public List<InventoryReportPojo> getInvtUsedDestroyReport(String type, String date, String brncode) throws ApplicationException {
        List<InventoryReportPojo> selectList = new ArrayList<InventoryReportPojo>();
        List reportList = null;
        try {

            if (brncode.equalsIgnoreCase("0A")) {
                if (type.equalsIgnoreCase("U")) {
                    reportList = em.createNativeQuery("select invnt_class,invnt_type,invnt_sr_no,dest_brncode,invnt_qty ,CREATED_BY_USER_ID, date_format(invnt_trn_dt,'%d/%m/%Y')"
                            + "  from inventory_transfer where ENTITY_CRE_FLG = 'ENT' and DEL_FLG ='N'"
                            + " and AUTHBY1<>'' and AUTHBY3<>'' and AUTH_ACT_FLG1='Y' and AUTH_ACT_FLG3='Y' and INVNT_TRN_DT<='" + date + "'").getResultList();
                } else {
                    reportList = em.createNativeQuery("select invnt_class,invnt_type,invnt_sr_no,dest_brncode,invnt_qty ,CREATED_BY_USER_ID, date_format(invnt_trn_dt,'%d/%m/%Y')"
                            + "  from inventory_transfer where ENTITY_CRE_FLG = 'DEL' and DEL_FLG ='Y'"
                            + " and AUTHBY1<>'' and AUTHBY3<>'' and AUTH_ACT_FLG1='N' and AUTH_ACT_FLG3='N' and INVNT_TRN_DT<='" + date + "'").getResultList();
                }
            } else {
                if (type.equalsIgnoreCase("U")) {
                    reportList = em.createNativeQuery("select invnt_class,invnt_type,invnt_sr_no,dest_brncode,invnt_qty ,CREATED_BY_USER_ID, date_format(invnt_trn_dt,'%d/%m/%Y')"
                            + "  from inventory_transfer where ORG_BRNCODE = '" + brncode + "' and ENTITY_CRE_FLG = 'ENT' and DEL_FLG ='N'"
                            + " and AUTHBY1<>'' and AUTHBY3<>'' and AUTH_ACT_FLG1='Y' and AUTH_ACT_FLG3='Y' and INVNT_TRN_DT<='" + date + "'").getResultList();
                } else {
                    reportList = em.createNativeQuery("select invnt_class,invnt_type,invnt_sr_no,dest_brncode,invnt_qty ,CREATED_BY_USER_ID, date_format(invnt_trn_dt,'%d/%m/%Y')"
                            + "  from inventory_transfer where ORG_BRNCODE = '" + brncode + "' and ENTITY_CRE_FLG = 'DEL' and DEL_FLG ='Y'"
                            + " and AUTHBY1<>'' and AUTHBY3<>'' and AUTH_ACT_FLG1='N' and AUTH_ACT_FLG3='N' and INVNT_TRN_DT<='" + date + "'").getResultList();
                }
            }
            if (reportList.size() > 0) {
                //InventoryReportPojo reportPojo = new InventoryReportPojo();
                for (int i = 0; i < reportList.size(); i++) {
                    Vector ele = (Vector) reportList.get(i);
                    InventoryReportPojo reportPojo = new InventoryReportPojo();
                    if (ele.get(0) != null) {
                        reportPojo.setInvntClass(ele.get(0).toString());
                    }
                    if (ele.get(1) != null) {
                        reportPojo.setInvntType(ele.get(1).toString());
                    }
                    if (ele.get(2) != null) {
                        reportPojo.setInvntSrNo(ele.get(2).toString());
                    }
                    if (ele.get(3) != null) {
                        int brncd = Integer.parseInt(ele.get(3).toString());
                        String branchName = "";
                        List branchList = em.createNativeQuery("select branchName from branchmaster where brncode=" + brncd).getResultList();
                        if (branchList.size() > 0) {
                            Vector vec = (Vector) branchList.get(0);
                            branchName = vec.get(0).toString();
                        }
                        reportPojo.setDestBranch(branchName);
                    }
                    if (ele.get(4) != null) {
                        reportPojo.setInvntQty(Long.parseLong(ele.get(4).toString()));
                    }
                    if (ele.get(5) != null) {
                        reportPojo.setEnterBy(ele.get(5).toString());
                    }
                    if (ele.get(6) != null) {
                        reportPojo.setTranDt(ele.get(6).toString());
                    }
                    selectList.add(reportPojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return selectList;
    }

    public List getAlphacode() throws ApplicationException {
        List brnCodeList = new ArrayList();
        try {
            brnCodeList = em.createNativeQuery("select distinct(brncode) , alphacode from branchmaster").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return brnCodeList;
    }

    public List<InventoryReportPojo> getInvtStockRegister(String type, String date, String brncode) throws ApplicationException {
        List<InventoryReportPojo> selectList = new ArrayList<InventoryReportPojo>();
        List reportList = null;
        try {
            if (brncode.equalsIgnoreCase("0A")) {
                if (type.equalsIgnoreCase("U")) {
                    reportList = em.createNativeQuery("select invt_class,invt_type,INVT_QUANTITY,chNoFrom,chNoTo,enterBy from chbookmaster_stock where status='U'"
                            + "  and '" + date + "'<=issuedt ").getResultList();
                } else {
                    reportList = em.createNativeQuery("select invt_class,invt_type,INVT_QUANTITY,chNoFrom,chNoTo,enterBy from chbookmaster_stock where status='F'"
                            + "  and Stockdt<='" + date + "' ").getResultList();
                }
            } else {
                if (type.equalsIgnoreCase("U")) {
                    reportList = em.createNativeQuery("select invt_class,invt_type,INVT_QUANTITY,chNoFrom,chNoTo,enterBy from chbookmaster_stock where status='U'"
                            + "  and '" + date + "'<=issuedt and brncode='" + brncode + "'").getResultList();
                } else {
                    reportList = em.createNativeQuery("select invt_class,invt_type,INVT_QUANTITY,chNoFrom,chNoTo,enterBy from chbookmaster_stock where status='F'"
                            + "  and Stockdt<='" + date + "' and brncode='" + brncode + "'").getResultList();
                }
            }
            if (reportList.size() > 0) {
                //InventoryReportPojo reportPojo = new InventoryReportPojo();
                for (int i = 0; i < reportList.size(); i++) {
                    InventoryReportPojo reportPojo = new InventoryReportPojo();
                    Vector ele = (Vector) reportList.get(i);
                    if (ele.get(0) != null) {
                        reportPojo.setInvntClass(ele.get(0).toString());
                    }
                    if (ele.get(1) != null) {
                        reportPojo.setInvntType(ele.get(1).toString());
                    }
                    if (ele.get(2) != null) {
                        reportPojo.setInvntQty(Long.parseLong(ele.get(2).toString()));
                    }
                    if (ele.get(3) != null) {
                        reportPojo.setChNoFrom(Integer.parseInt(ele.get(3).toString()));
                    }
                    if (ele.get(4) != null) {
                        reportPojo.setChNoTo(Integer.parseInt(ele.get(4).toString()));
                    }
                    if (ele.get(5) != null) {
                        reportPojo.setEnterBy(ele.get(5).toString());
                    }
                    selectList.add(reportPojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return selectList;
    }

    public List<InventoryTransferReceiptTable> getInventoryTransferData(String getOptions, String selectBoxData, String orgBrnCode, String currentDate) throws ApplicationException {
        try {
            List<InventoryTransferReceiptTable> inventoryTransferReceiptList = new ArrayList();
            List resultSet = new ArrayList();
            if (getOptions.equalsIgnoreCase("1")) {
                if (selectBoxData.equalsIgnoreCase("ALL")) {
                    resultSet = em.createNativeQuery("select  invnt_class, invnt_type, invnt_sr_no , to_invnt_loc_class ,invnt_tr_desc , date_format(invnt_trn_dt,'%d/%m/%Y') , created_by_user_id , authby1 ,dest_brncode  from inventory_transfer where ORG_BRNCODE = '" + orgBrnCode + "' "
                            + "and INVNT_TRN_DT<= '" + currentDate + "' and AUTHBY1<>'' and  AUTHBY3<>'' and AUTH_ACT_FLG1='Y' and AUTH_ACT_FLG3='Y'").getResultList();
                } else {
                    resultSet = em.createNativeQuery("select  invnt_class, invnt_type, invnt_sr_no , to_invnt_loc_class ,invnt_tr_desc , date_format(invnt_trn_dt,'%d/%m/%Y') , created_by_user_id , authby1 ,dest_brncode from inventory_transfer where ORG_BRNCODE = '" + orgBrnCode + "' and dest_brncode='" + selectBoxData + "' and "
                            + "INVNT_TRN_DT<= '" + currentDate + "' and AUTHBY1<>'' and  AUTHBY3<>'' and AUTH_ACT_FLG1='Y' and AUTH_ACT_FLG3='Y'").getResultList();
                }
            } else {
                if (selectBoxData.equalsIgnoreCase("ALL")) {
                    resultSet = em.createNativeQuery("select  invnt_class, invnt_type, invnt_sr_no , to_invnt_loc_class ,invnt_tr_desc , date_format(invnt_trn_dt,'%d/%m/%Y') , created_by_user_id , authby1 ,org_brncode  from inventory_transfer where ORG_BRNCODE not in ('" + orgBrnCode + "') "
                            + "and INVNT_TRN_DT<= '" + currentDate + "' and AUTHBY1<>'' and  AUTHBY3<>'' and AUTH_ACT_FLG1='Y' and AUTH_ACT_FLG3='Y'").getResultList();
                } else {
                    resultSet = em.createNativeQuery("select  invnt_class, invnt_type, invnt_sr_no , to_invnt_loc_class ,invnt_tr_desc , date_format((10),invnt_trn_dt,'%d/%m/%Y') , created_by_user_id , authby1 ,org_brncode from inventory_transfer where ORG_BRNCODE not in ('" + orgBrnCode + "') and dest_brncode='" + orgBrnCode + "' and "
                            + "INVNT_TRN_DT<= '" + currentDate + "' and AUTHBY1<>'' and  AUTHBY3<>'' and AUTH_ACT_FLG1='Y' and AUTH_ACT_FLG3='Y'").getResultList();
                }
            }
            if (!resultSet.isEmpty()) {
                for (int i = 0; i < resultSet.size(); i++) {
                    Vector vec = (Vector) resultSet.get(i);
                    InventoryTransferReceiptTable inventoryTransferReceiptTable = new InventoryTransferReceiptTable();
                    inventoryTransferReceiptTable.setInvtClass(vec.get(0).toString());
                    inventoryTransferReceiptTable.setInvntType(vec.get(1).toString());
                    inventoryTransferReceiptTable.setInvtSrNo(vec.get(2).toString());
                    inventoryTransferReceiptTable.setInvntLocClass(vec.get(3).toString());
                    inventoryTransferReceiptTable.setInvntDesc(vec.get(4).toString());
                    inventoryTransferReceiptTable.setTranDate(vec.get(5).toString());
                    inventoryTransferReceiptTable.setUserID(vec.get(6).toString());
                    inventoryTransferReceiptTable.setAuthBy(vec.get(7).toString());
                    inventoryTransferReceiptTable.setBranch(vec.get(8).toString());

                    inventoryTransferReceiptList.add(inventoryTransferReceiptTable);
                }
            }
            return inventoryTransferReceiptList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List<IssueChequeBookRegisterPojo> getChequeBookData(String repOption, String acNo, String issueDt, String chequeType) throws ApplicationException {
        List<IssueChequeBookRegisterPojo> dataList = new ArrayList<>();
        try {
            List result = new ArrayList();
            String chequeTypeCond = "";
            if (chequeType.equalsIgnoreCase("ALL")) {
                chequeTypeCond = "";
            } else {
                chequeTypeCond = "and StatusFlag = '" + chequeType + "'";
            }
            if (repOption.equalsIgnoreCase("A")) {
                result = em.createNativeQuery("select a.Acno,custname,cast(Chqno as unsigned),StatusFlag,date_format(IssueDt,'%d/%m/%Y') from chbook_ca a,accountmaster b where a.acno='" + acNo + "'  and a.acno = b.acno and IssueDt <='" + issueDt + "' " + chequeTypeCond + "\n"
                        + "union\n"
                        + "select a.Acno,custname,cast(Chqno as unsigned),StatusFlag,date_format(IssueDt,'%d/%m/%Y') from chbook_sb a,accountmaster b where a.acno='" + acNo + "'  and a.acno = b.acno and IssueDt <='" + issueDt + "' " + chequeTypeCond + "\n"
                        + "order by 4,1,5").getResultList();
            } else {
                result = em.createNativeQuery("select a.Acno,custname,cast(Chqno as unsigned),StatusFlag,date_format(IssueDt,'%d/%m/%Y') from chbook_ca a,accountmaster b where a.acno in"
                        + "(select Acno from customerid where custid = '" + acNo + "' and substring(acno,3,2) in"
                        + "(select acctcode from accounttypemaster where acctNature ='" + CbsConstant.CURRENT_AC + "')) and a.acno = b.acno and IssueDt <='" + issueDt + "' " + chequeTypeCond + "\n"
                        + "union\n"
                        + "select a.Acno,custname,cast(Chqno as unsigned),StatusFlag,date_format(IssueDt,'%d/%m/%Y') from chbook_sb a,accountmaster b where a.acno in"
                        + "(select Acno from customerid where custid = '" + acNo + "' and substring(acno,3,2) in"
                        + "(select acctcode from accounttypemaster where acctNature ='" + CbsConstant.SAVING_AC + "')) and a.acno = b.acno and IssueDt <='" + issueDt + "'" + chequeTypeCond + " \n"
                        + "order by 4,1,5").getResultList();
            }
            String cheqType = "";
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    IssueChequeBookRegisterPojo pojo = new IssueChequeBookRegisterPojo();
                    pojo.setAcNo(vtr.get(0).toString());
                    pojo.setCustName(vtr.get(1).toString());
                     pojo.setChBookNo(Integer.parseInt(vtr.get(2).toString()));
                    if (vtr.get(3).toString().equalsIgnoreCase("F")) {
                        cheqType = "Fresh";
                    } else if (vtr.get(3).toString().equalsIgnoreCase("U")) {
                        cheqType = "Used";
                    } else if (vtr.get(3).toString().equalsIgnoreCase("S")) {
                        cheqType = "Stop";
                    }
                    pojo.setBnkName(cheqType);
                    pojo.setIssueDt(vtr.get(4).toString());
                   
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }
}
