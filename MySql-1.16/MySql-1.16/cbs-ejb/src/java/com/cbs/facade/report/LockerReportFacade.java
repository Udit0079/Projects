package com.cbs.facade.report;

import com.cbs.dto.report.LockerInventoryReportPojo;
import com.cbs.dto.report.LockerRentReportPojo;
import com.cbs.dto.report.LockerStatementReportPojo;
import com.cbs.dto.report.LockerSurrenderReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName = "LockerReportFacade")
public class LockerReportFacade implements LockerReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

    @Override
    public List getLockerCabinetNumbers(String brnCode) throws ApplicationException {
        List list = new ArrayList();
        try {
            if (brnCode.equalsIgnoreCase("0A")) {
                list = em.createNativeQuery("Select Distinct cabno From lockermaster order by cabno").getResultList();
            } else {
                list = em.createNativeQuery("Select Distinct cabno From lockermaster WHERE BRNCODE='" + brnCode + "' order by cabno").getResultList();
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    @Override
    public List getLockerCabinetNumbersByLockerType(String lockerType, String brnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select Distinct cabno From lockermaster WHERE lockertype ='" + lockerType + "' and BRNCODE='" + brnCode + "' order by cabno").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List<LockerStatementReportPojo> getLockerStatementReport(String lockerType, double cabNo, double lockerNo, String brCode) throws ApplicationException {
        List<LockerStatementReportPojo> returnList = new ArrayList<LockerStatementReportPojo>();
        List tempList = null;
        try {

            tempList = em.createNativeQuery("select l.acNo,l.rentDueDt,l.rent,lm.keyNo,am.custName,l.issueDt,l.status ,r.adOperator1,r.adOperator2,"
                    + "r.adOperator3,r.adOperator4 From lockermaster lm,lockerrent l,accountmaster am ,lockeracmaster r Where am.acno=l.acno and "
                    + "lm.cabNo=l.cabNo and l.lockerType= '" + lockerType + "' and lm.brncode = '" + brCode + "'  and am.curbrcode ='" + brCode
                    + "' and lm.LockerType=l.LockerType and lm.LockerNo=l.LockerNo AND lm.LockerNo=r.LockerNo AND lm.cabNo=r.cabNo and l.cabno= "
                    + cabNo + " and l.lockerno= " + lockerNo + " and am.acno=r.acno").getResultList();

            for (int i = 0; i < tempList.size(); i++) {
                Vector ele = (Vector) tempList.get(i);
                LockerStatementReportPojo pojo = new LockerStatementReportPojo();
                pojo.setCabNo(cabNo);
                pojo.setLockerNo(lockerNo);
                pojo.setLockerType(lockerType);
                if (ele.get(0) != null) {
                    pojo.setAcNo(ele.get(0).toString());
                }
                if (ele.get(1) != null) {
                    pojo.setRentDueDt((Date) ele.get(1));
                }
                if (ele.get(2) != null || ele.get(2).toString().equalsIgnoreCase("")) {
                    pojo.setRent(Double.parseDouble(ele.get(2).toString()));
                }
                if (ele.get(3) != null || ele.get(3).toString().equalsIgnoreCase("")) {
                    pojo.setKeyNo(Double.parseDouble(ele.get(3).toString()));
                }
                if (ele.get(4) != null) {
                    pojo.setCustName(ele.get(4).toString());
                }
                if (ele.get(5) != null) {
                    pojo.setIssueDt((Date) ele.get(5));
                }
                if (ele.get(6) != null) {
                    pojo.setStatus(ele.get(6).toString());
                }
                if (ele.get(7) != null) {
                    pojo.setAdOperator1(ele.get(7).toString());
                }
                if (ele.get(8) != null) {
                    pojo.setAdOperator2(ele.get(8).toString());
                }
                if (ele.get(9) != null) {
                    pojo.setAdOperator3(ele.get(9).toString());
                }
                if (ele.get(10) != null) {
                    pojo.setAdOperator4(ele.get(10).toString());
                }
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    @Override
    public List<LockerSurrenderReportPojo> getLockerSurrenderReport(String option, String cabNo, String brCode, String frDt, String toDt) throws ApplicationException {
        List<LockerSurrenderReportPojo> returnList = new ArrayList<LockerSurrenderReportPojo>();
        List tempList = null;
        try {
            String brCode1 = "";

            if (brCode.equalsIgnoreCase("0A")) {
                brCode1 = "";
            } else {
                brCode1 = "AND ls.brncode ='" + brCode + "'";
            }

            if (cabNo.equalsIgnoreCase("All")) {
                tempList = em.createNativeQuery("select ls.cabNo,ls.lockerType,ls.lockerNo, ls.acno, ls.rentDueDt, ls.rent,ls.surrenderDt from "
                        + "lockersurrender ls, accountmaster am where ls.acno=am.acno " + brCode1 + " and ls.surrenderDt between '" + frDt + "' and '" + toDt + "' order by  ls.cabno asc").getResultList();
            } else {
                tempList = em.createNativeQuery("select ls.cabNo,ls.lockerType,ls.lockerNo, ls.acno, ls.rentDueDt, ls.rent,ls.surrenderDt from "
                        + "lockersurrender ls, accountmaster am where  ls.acno=am.acno " + brCode1 + " and ls.CabNo= " + cabNo
                        + " and ls.surrenderDt between '" + frDt + "' and '" + toDt + "' order by  ls.cabno asc").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                LockerSurrenderReportPojo pojo = new LockerSurrenderReportPojo();
                Vector ele = (Vector) tempList.get(i);
                if (ele.get(0) != null) {
                    pojo.setCabNo(ele.get(0).toString());
                }
                if (ele.get(1) != null) {
                    pojo.setLockerType(ele.get(1).toString());
                }
                if (ele.get(2) != null) {
                    pojo.setLockerNo(ele.get(2).toString());
                }
                if (ele.get(3) != null) {
                    pojo.setAcNo(ele.get(3).toString());
                }
                if (ele.get(4) != null) {
                    pojo.setRentDueDt(dmyFormat.format((Date) ele.get(4)));
                }
                if (ele.get(5) != null || !ele.get(5).toString().equalsIgnoreCase("")) {
                    pojo.setRent(Double.parseDouble(ele.get(5).toString()));
                }
                if (ele.get(6) != null) {
                    pojo.setSurrenderDt(dmyFormat.format((Date) ele.get(6)));
                }
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    @Override
    public List<LockerRentReportPojo> getLockerRentReport(String option, String brCode, String cabNo, int ordBy, String status, String frDt, String toDt) throws ApplicationException {
        List<LockerRentReportPojo> returnList = new ArrayList<LockerRentReportPojo>();
        List tempList = null;
        try {

//            String orderBy = null;
//            switch (ordBy) {
//                case 1:
//                    orderBy = "lr.cabno";
//                    break;
//                case 2:
//                    orderBy = "lr.lockerType";
//                    break;
//                case 3:
//                    orderBy = "lr.lockerNo";
//                    break;
//                case 4:
//                    orderBy = "am.CustName";
//                    break;
//                case 5:
//                    orderBy = "lr.rentDueDt";
//                    break;
//                default:
//                    orderBy = "lr.issuedt";
//            }
            Double rent = 0.0;
            String rentDueDt = "", issueDt = "", keyNo = "", custName = "", lockerNo = "", lockerType = "", acNo = "", custFullName = "", mailAddressLine1 = "", mailAddressLine2 = "", mailDistrict = "", mailPostalCode = "", mailVillage = "", mailStateCode = "", mailBlock = "";
            String brCode1 = "", brCode2 = "", bankNameReq = "", bankAddressReq = "", city = "", pinCode = "", statusQuery = "";
            if (brCode.equalsIgnoreCase("0A")) {
                brCode1 = "";
                brCode2 = "";
            } else {
                brCode1 = "and lm.brncode= '" + brCode + "'";
                brCode2 = "and  lr.brncode = '" + brCode + "'";
            }

            if (status.equalsIgnoreCase("P")) {
                statusQuery = "date_format(lr.TxnDate,'%Y%m%d')";
            } else {
                statusQuery = "lr.rentDueDt";
            }

            if (cabNo.equalsIgnoreCase("ALL")) {
                tempList = em.createNativeQuery("Select lr.CabNo,lr.lockerType,lr.lockerNo,lr.acno,am.CustName,lm.keyNo,lr.issuedt,lr.rent,lr.rentDueDt,lr.status "
                        + "From lockerrent lr,accountmaster am, lockermaster lm Where am.acno=lr.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType "
                        + "and lm.LockerNo=lr.LockerNo and lr.status='" + status + "' and lm.brncode=lr.brncode  " + brCode1 + " " + brCode2 + " "
                        + "and " + statusQuery + " between '" + frDt + "' and '" + toDt + "'"
                        + "union All "
                        + "Select lr.CabNo,lr.lockerType,lr.lockerNo,lr.acno,am.acname,lm.keyNo,lr.issuedt,lr.rent,lr.rentDueDt,lr.status "
                        + "From lockerrent lr,gltable am, lockermaster lm Where am.acno=lr.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType "
                        + "and lm.LockerNo=lr.LockerNo and lr.status='" + status + "' and lm.brncode=lr.brncode " + brCode1 + " " + brCode2 + ""
                        + "and " + statusQuery + " between '" + frDt + "' and '" + toDt + "' order by " + ordBy + "").getResultList();
            } else {
                tempList = em.createNativeQuery("Select lr.CabNo,lr.lockerType,lr.lockerNo,lr.acno,am.CustName,lm.keyNo,lr.issuedt,lr.rent,lr.rentDueDt,lr.status "
                        + "From lockerrent lr,accountmaster am, lockermaster lm Where am.acno=lr.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType "
                        + "and lm.LockerNo=lr.LockerNo and lr.status='" + status + "' and lm.brncode=lr.brncode " + brCode1 + " " + brCode2 + " and lr.cabno= "
                        + cabNo + " and " + statusQuery + " between '" + frDt + "' and '" + toDt + "' "
                        + "union All "
                        + "Select lr.CabNo,lr.lockerType,lr.lockerNo,lr.acno,am.acname,lm.keyNo,lr.issuedt,lr.rent,lr.rentDueDt,lr.status "
                        + "From lockerrent lr,gltable am, lockermaster lm Where am.acno=lr.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType "
                        + "and lm.LockerNo=lr.LockerNo and lr.status='" + status + "' and lm.brncode=lr.brncode " + brCode1 + " " + brCode2 + " and lr.cabno= " + cabNo + ""
                        + "and " + statusQuery + " between '" + frDt + "' and '" + toDt + "' order by " + ordBy + "").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                Vector ele = (Vector) tempList.get(i);
                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                    cabNo = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    lockerType = ele.get(1).toString();
                }
                if (ele.get(2) != null) {
                    lockerNo = (ele.get(2).toString());
                }
                if (ele.get(3) != null) {
                    acNo = ele.get(3).toString();
                }
                if (ele.get(4) != null) {
                    custName = (ele.get(4).toString());
                }
                if (ele.get(5) != null) {
                    keyNo = (ele.get(5).toString());
                }
                if (ele.get(6) != null || !ele.get(6).toString().equalsIgnoreCase("")) {
                    issueDt = (dmyFormat.format((Date) ele.get(6)));
                }
                if (ele.get(7) != null || !ele.get(7).toString().equalsIgnoreCase("")) {
                    rent = (Double.parseDouble(ele.get(7).toString()));
                }
                if (ele.get(8) != null || !ele.get(8).toString().equalsIgnoreCase("")) {
                    rentDueDt = (dmyFormat.format((Date) ele.get(8)));
                }
                if (ele.get(9).toString().equalsIgnoreCase("P")) {
                    status = ("Paid");
                } else {
                    status = ("Unpaid");
                }
                int yr = 0;
                if (ele.get(9).toString().equalsIgnoreCase("U")) {
                    List lm = em.createNativeQuery(" select c.brncode, b.bankname, b.bankaddress, c.keyno, a.City, a.Pincode from lockeracmaster c , bnkadd b, branchmaster a where c.lockerno = '" + lockerNo + "'"
                            + "and c.brncode = b.branchcode and b.alphacode = a.AlphaCode and c.keyno = '" + keyNo + "' and c.acno='" + acNo + "'").getResultList();
                    if (!lm.isEmpty()) {
                        Vector vec1 = (Vector) lm.get(0);
                        bankNameReq = vec1.get(1).toString();
                        bankAddressReq = vec1.get(2).toString();
                        city = vec1.get(4).toString();
                        pinCode = vec1.get(5).toString();
                    }

                    List l = em.createNativeQuery("select  trim(ccmd.CustFullName), concat(trim(ccmd.MailAddressLine1),' ',ifnull(trim(MailAddressLine2),'')) as custAdd,"
                            + "ifnull(trim(ccmd.MailDistrict),'') as dist, ifnull(trim(ccmd.MailPostalCode),'') pin, ifnull(trim(ccmd.MailVillage),'') village,re.REF_DESC stateCode,"
                            + "ifnull(trim(ccmd.mailblock),'')block from cbs_customer_master_detail ccmd,cbs_ref_rec_type re "
                            + "where ccmd.customerid =(select custId from customerid where acno ='" + acNo + "') and re.REF_REC_NO = '002' and re.REF_CODE = ccmd.MailStateCode").getResultList();
                    if (!l.isEmpty()) {
                        Vector vector1 = (Vector) l.get(0);
                        custFullName = vector1.get(0).toString();
                        mailAddressLine1 = vector1.get(1).toString();
                        mailDistrict = vector1.get(2).toString();
                        mailPostalCode = vector1.get(3).toString();
                        mailVillage = vector1.get(4).toString();
                        mailStateCode = vector1.get(5).toString();
                        mailBlock = vector1.get(6).toString();
                    }

                    List yrMonDayList = CbsUtil.getYrMonDayDiff(ymdFormat.format(dmyFormat.parse(rentDueDt)), toDt);
                    yr = Integer.parseInt(yrMonDayList.get(0).toString());

                    String mon = yrMonDayList.get(1).toString();
                    String day = yrMonDayList.get(2).toString();
                    String monDay = mon + "." + day;
                    double monDayDiff = Double.parseDouble(monDay);

                    if (yr == 0) {
                        if (monDayDiff > 0) {
                            yr = yr + 1;
                        }
                    } else {
                        if (monDayDiff > 0) {
                            yr = yr + 1;
                        }
                    }
                }
                LockerRentReportPojo pojo = new LockerRentReportPojo();
                pojo.setCabNo(cabNo);
                pojo.setLockerType(lockerType);
                pojo.setLockerNo(lockerNo);
                pojo.setAcNo(acNo);
                pojo.setCustName(custName);
                pojo.setKeyNo(keyNo);
                pojo.setIssueDt(issueDt);
                if (ele.get(9).toString().equalsIgnoreCase("U")) {
                    pojo.setRent(rent * yr);
                } else {
                    pojo.setRent(rent);
                }
                pojo.setRentDueDt(rentDueDt);
                pojo.setStatus(status);
                pojo.setCity(city.toUpperCase());
                pojo.setPinCode(pinCode);
                if (custName.contains("SUNDRY")) {
                    pojo.setCustFullName(custName.toUpperCase());
                    pojo.setBankNameReq(bankNameReq.toUpperCase());
                    pojo.setBankaddressReq(bankAddressReq.toUpperCase());
                    pojo.setMailAddressLine1("");
                    pojo.setMailDistrict("");
                    pojo.setMailPostalCode("");
                    pojo.setMailStateCode("");
                    pojo.setMailVillage("");
                } else {
                    pojo.setCustFullName(custFullName.toUpperCase());
                    pojo.setMailAddressLine1(mailAddressLine1.toUpperCase());
                    pojo.setMailDistrict(mailDistrict.toUpperCase());
                    pojo.setMailPostalCode(mailPostalCode.toUpperCase());
                    pojo.setMailStateCode(mailStateCode.toUpperCase());
                    pojo.setMailVillage(mailVillage.toUpperCase());
                    pojo.setBankNameReq(bankNameReq.toUpperCase());
                    pojo.setBankaddressReq(bankAddressReq.toUpperCase());
                }
                returnList.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return returnList;
    }

    @Override
    public List<LockerInventoryReportPojo> getLockerInventoryReport(String option, String cabNo, String brCode, String frDt, String toDt, String status) throws ApplicationException {
        List<LockerInventoryReportPojo> returnList = new ArrayList<LockerInventoryReportPojo>();
        List tempList = null;
        int countVacentLocker = 0;
        int countOccupiedLocker = 0;
        try {

            String brCode1 = "", brCode2 = "", date = "", date1 = "", ocflag = "", ocflag1 = "", surrenderQuery1 = "", surrenderQuery2 = "";
            if (brCode.equalsIgnoreCase("0A")) {
                brCode1 = "";
                brCode2 = "";
            } else {
                brCode1 = "and lm.brncode= '" + brCode + "'";
                brCode2 = "and  lr.brncode = '" + brCode + "'";
            }

            if (status.equalsIgnoreCase("I")) {
                surrenderQuery2 = "";
                if (cabNo.equalsIgnoreCase("ALL")) {
                    surrenderQuery1 = "union"
                            + " Select lm.cabno,lm.lockerType,lm.lockerno,lm.keyno,lm.ocFlag,lr.issueDt,am.acno,am.Custname From lockermaster lm,"
                            + "accountmaster am, lockersurrender lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType and lm.LockerNo=lr.LockerNo "
                            + "and lm.brncode = lr.brncode " + brCode2 + "   and lm.ocFlag = 'N' and surrenderdt > '" + toDt + "'";
                } else {
                    surrenderQuery1 = "union"
                            + " Select lm.cabno,lm.lockerType,lm.lockerno,lm.keyno,lm.ocFlag,lr.issueDt,am.acno,am.Custname From lockermaster lm,"
                            + "accountmaster am, lockersurrender lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType and lm.LockerNo=lr.LockerNo "
                            + "and lm.brncode = lr.brncode and lm.CabNo= " + cabNo + " " + brCode2 + "   and lm.ocFlag = 'N' and surrenderdt > '" + toDt + "'";
                }

            } else if (status.equalsIgnoreCase("N")) {
                if (cabNo.equalsIgnoreCase("ALL")) {
                    surrenderQuery2 = "and lm.LockerNo not in(select lockerno from lockersurrender lr  where surrenderdt>'" + frDt + "' " + brCode2 + ")";
                } else {
                    surrenderQuery2 = "and lm.LockerNo not in(select lockerno from lockersurrender lr  where cabno = " + cabNo + " " + brCode2 + " and surrenderdt>'" + frDt + "')";
                }
                surrenderQuery1 = "";
            } else {
                if (cabNo.equalsIgnoreCase("ALL")) {
                    surrenderQuery1 = "union "
                            + "select a.cabno,a.lockerType,a.lockerno,a.keyno,a.ocFlag ,a.issueDt,a.acno,a.Custname,b.PerAddressLine1 from "
                            + "(Select lm.cabno,lm.lockerType,lm.lockerno,lm.keyno,lm.ocFlag,lr.issueDt,am.acno,am.Custname From lockermaster lm, "
                            + "accountmaster am, lockersurrender lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType and lm.LockerNo=lr.LockerNo "
                            + "and lm.brncode = lr.brncode " + brCode2 + " and lm.ocFlag = 'N' and surrenderdt > '" + frDt + "')a, "
                            + "(Select c.acno ,cm.customerid,cm.PerAddressLine1 from customerid c ,cbs_customer_master_detail cm  "
                            + "where c.custid = cast(cm.customerid as unsigned))b where a.acno = b.acno ";
                } else {
                    surrenderQuery1 = "union "
                            + "select a.cabno,a.lockerType,a.lockerno,a.keyno,a.ocFlag ,a.issueDt,a.acno,a.Custname,b.PerAddressLine1 from "
                            + "(Select lm.cabno,lm.lockerType,lm.lockerno,lm.keyno,lm.ocFlag,lr.issueDt,am.acno,am.Custname From lockermaster lm, "
                            + "accountmaster am, lockersurrender lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType and lm.LockerNo=lr.LockerNo "
                            + "and lm.brncode = lr.brncode and lm.CabNo= " + cabNo + " " + brCode2 + " and lm.ocFlag = 'N' and surrenderdt > '" + frDt + "')a, "
                            + "(Select c.acno ,cm.customerid,cm.PerAddressLine1 from customerid c ,cbs_customer_master_detail cm  "
                            + "where c.custid = cast(cm.customerid as unsigned))b where a.acno = b.acno ";
                }
            }

            if (status.equalsIgnoreCase("I")) {
                date = "and lr.issueDt between '" + frDt + "' and '" + toDt + "'";
                date1 = "and lm.entrydate between '" + frDt + "' and '" + toDt + "'";
                status = "N";
                ocflag = "";
            } else {
                date = "and lr.issueDt <= '" + frDt + "'";
                date1 = "and lm.entrydate <= '" + frDt + "'";
                ocflag = "and lm.ocflag='" + status + "'";
            }

            if (status.equalsIgnoreCase("Y")) {
                if (cabNo.equalsIgnoreCase("All")) {
                    tempList = em.createNativeQuery("select a.cabno,a.lockerType,a.lockerno,a.keyno,a.ocFlag ,a.issueDt,a.acno,a.Custname,b.PerAddressLine1 "
                            + "from (Select distinct lm.cabno,lm.lockerType,lm.lockerno,lm.keyno,lm.ocFlag ,lr.issueDt,am.acno,am.Custname From lockermaster lm,"
                            + "accountmaster am, lockerrent lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType "
                            + "and lm.LockerNo=lr.LockerNo and lm.brncode = lr.brncode " + brCode2 + " " + ocflag + " " + date + ") a,"
                            + "(Select c.acno ,cm.customerid,cm.PerAddressLine1 from customerid c ,cbs_customer_master_detail cm "
                            + "where c.custid = cast(cm.customerid as unsigned)) b where a.acno = b.acno  "
                            + "union all "
                            + "select a.cabno,a.lockerType,a.lockerno,a.keyno,a.ocFlag ,a.issueDt,a.acno,Custname,''PerAddressLine1 "
                            + "from (Select distinct lm.cabno,lm.lockerType,lm.lockerno,lm.keyno,lm.ocFlag ,lr.issueDt,am.acno,am.AcName Custname From lockermaster lm,"
                            + "gltable am, lockerrent lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType "
                            + "and lm.LockerNo=lr.LockerNo and lm.brncode = lr.brncode " + brCode2 + " " + ocflag + " " + date + ") a " + surrenderQuery1 + "order by 1,5").getResultList();
                } else {
                    tempList = em.createNativeQuery("select a.cabno,a.lockerType,a.lockerno,a.keyno,a.ocFlag ,a.issueDt,a.acno,a.Custname,b.PerAddressLine1 "
                            + "from (Select distinct lm.cabno,lm.lockerType,lm.lockerno,lm.keyno,lm.ocFlag ,lr.issueDt,am.acno,am.Custname From lockermaster lm,"
                            + "accountmaster am, lockerrent lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType "
                            + "and lm.LockerNo=lr.LockerNo and lm.brncode = lr.brncode and lm.CabNo= " + cabNo + " " + brCode2 + " " + ocflag + " " + date + ") a,"
                            + "(Select c.acno ,cm.customerid,cm.PerAddressLine1 from customerid c ,cbs_customer_master_detail cm "
                            + "where c.custid = cast(cm.customerid as unsigned)) b where a.acno = b.acno "
                            + "union All "
                            + "select a.cabno,a.lockerType,a.lockerno,a.keyno,a.ocFlag ,a.issueDt,a.acno,Custname,''PerAddressLine1 "
                            + "from (Select distinct lm.cabno,lm.lockerType,lm.lockerno,lm.keyno,lm.ocFlag ,lr.issueDt,am.acno,am.AcName Custname From lockermaster lm,"
                            + "gltable am, lockerrent lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType "
                            + "and lm.LockerNo=lr.LockerNo and lm.brncode = lr.brncode and lm.CabNo= " + cabNo + " " + brCode2 + " " + ocflag + " " + date + ") a " + surrenderQuery1 + " order by 1,5").getResultList();
                }
            } else {
                if (cabNo.equalsIgnoreCase("All")) {
                    tempList = em.createNativeQuery("select a.cabno,a.lockerType,a.lockerno,a.keyno,a.ocFlag,a.issueDt,a.acno,a.Custname from "
                            + "(Select lm.cabno,lm.lockerType,lm.lockerno,lm.keyno,lm.ocFlag,lr.issueDt,am.acno,am.Custname From lockermaster lm,"
                            + "accountmaster am, lockerrent lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType and lm.LockerNo=lr.LockerNo "
                            + "and lm.brncode = lr.brncode " + brCode2 + " " + ocflag + " " + date + " " + surrenderQuery1 + " "
                            + "Union "
                            + "Select lm.CabNo,lm.LockerType,lm.LockerNo, lm.KeyNo,lm.Ocflag,null issueDt ,null acno ,null Custname  from "
                            + "lockermaster lm Where lm.ocflag='" + status + "' " + brCode1 + " " + date1 + " " + surrenderQuery2 + ")a "
                            + "Union all "
                            + "Select lm.cabno,lm.lockerType,lm.lockerno,lm.keyno,lm.ocFlag,lr.issueDt,am.acno,am.AcName custName From lockermaster lm,"
                            + "gltable am, lockerrent lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType and lm.LockerNo=lr.LockerNo "
                            + "and lm.brncode = lr.brncode " + brCode2 + " " + ocflag + " " + date + " order by 1,5").getResultList();
                } else {
                    tempList = em.createNativeQuery("select a.cabno,a.lockerType,a.lockerno,a.keyno,a.ocFlag,a.issueDt,a.acno,a.Custname from "
                            + "(Select lm.cabno,lm.lockerType,lm.lockerno,lm.keyno,lm.ocFlag,lr.issueDt,am.acno,am.Custname From lockermaster lm,"
                            + "accountmaster am, lockerrent lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType and lm.LockerNo=lr.LockerNo "
                            + "and lm.brncode = lr.brncode and lm.CabNo= " + cabNo + " " + brCode2 + " " + ocflag + " " + date + " " + surrenderQuery1 + " "
                            + "Union "
                            + "Select lm.CabNo,lm.LockerType,lm.LockerNo, lm.KeyNo,lm.Ocflag,null issueDt ,null acno ,null Custname  from "
                            + "lockermaster lm Where lm.CabNo= " + cabNo + " and lm.ocflag='" + status + "' " + brCode1 + " " + date1 + " " + surrenderQuery2 + ")a  "
                            + "Union all "
                            + "Select lm.cabno,lm.lockerType,lm.lockerno,lm.keyno,lm.ocFlag,lr.issueDt,am.acno,am.AcName custName  From lockermaster lm,"
                            + "gltable am, lockerrent lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and lm.LockerType=lr.LockerType and lm.LockerNo=lr.LockerNo "
                            + "and lm.brncode = lr.brncode and lm.CabNo= " + cabNo + " " + brCode2 + " " + ocflag + " " + date + " order by 1,5").getResultList();
                }
            }

            for (int i = 0; i < tempList.size(); i++) {
                LockerInventoryReportPojo pojo = new LockerInventoryReportPojo();
                Vector ele = (Vector) tempList.get(i);
                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                    pojo.setCabNo(new Double(ele.get(0).toString()).intValue());
                }
                if (ele.get(1) != null) {
                    pojo.setLockerType(ele.get(1).toString());
                }
                if (ele.get(2) != null || !ele.get(2).toString().equalsIgnoreCase("")) {
                    pojo.setLockerNo(new Double(ele.get(2).toString()).intValue());
                }
                if (ele.get(3) != null || !ele.get(3).toString().equalsIgnoreCase("")) {
                    pojo.setKeyNo(new Double(ele.get(3).toString()).intValue());
                }
                String occupied = ele.get(4) == null ? "" : ele.get(4).toString();

                if (status.equalsIgnoreCase("Y")) {
                    if (occupied.equalsIgnoreCase("N")) {
                        occupied = "Y";
                    }
                }
                if (!occupied.equalsIgnoreCase("")) {
                    if (occupied.equalsIgnoreCase("N")) {
                        ++countVacentLocker;
                    } else {
                        ++countOccupiedLocker;
                    }
                    pojo.setOccupied(occupied);
                }
                if (ele.get(5) != null) {
                    pojo.setIssueDate(dmyFormat.format((Date) ele.get(5)));
                }
                if (ele.get(6) != null) {
                    pojo.setDebitAcNo(ele.get(6).toString());
                }
                if (ele.get(7) != null) {
                    pojo.setDebitAcName(ele.get(7).toString());
                }
                if (status.equalsIgnoreCase("Y")) {
                    pojo.setAddress(ele.get(8).toString());
                }
                pojo.setOccupiedLocker(countOccupiedLocker);
                pojo.setVacentLocker(countVacentLocker);
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }
    
        @Override
    public List getLockerNoByLockerTypeAndCabNo(String lockerType, String cabNo, String brCode) throws ApplicationException {

        try {
            List list = em.createNativeQuery("Select cast(lockerno as unsigned)  From locker_operation WHERE date_format(opdate,'%Y%m%d') = '" + ymdFormat.format(new Date()) + "' and lockertype ='" + lockerType + "'and cabno = " + cabNo + " and BRNCODE='" + brCode + "' order by lockerno").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
        
}
