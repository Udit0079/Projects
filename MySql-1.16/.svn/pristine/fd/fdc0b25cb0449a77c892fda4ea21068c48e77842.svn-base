/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.DepositeMprPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OtherLoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Athar Reza
 */
public class DepositeMpr extends BaseBean {
    
    private String message;
    private String selectRepType;
    private List <SelectItem> selectRepTypeList;
    private String selectCol1;
    private List <SelectItem> selectCol1List;
    private String selectCol2;
    private List <SelectItem> selectCol2List;
    private String selectCol3;
    private List <SelectItem> selectCol3List;
    private String selectCol4;
    private List <SelectItem> selectCol4List;
    private String selectIncDec;
    private List <SelectItem> selectIncDecList;
    private boolean chkBoxValue;
    private String modOfMonthQly;
    private List <SelectItem> modOfMonthQlyList;
    private String month;
    private List <SelectItem> monthList;
    private Integer year;
    private List <SelectItem> yearList;
    private HttpServletRequest req;
            boolean flag;
    private boolean yes1Box;
    private boolean yes2Box;
    private boolean yes3Box;
    private boolean yes4Box;
    private boolean yes5Box;
    private boolean yes6Box;
    private boolean yes7Box;
    private boolean yes8Box;
    
    private boolean noOfAcsBox;
    private boolean tarAcsBox;
    private boolean tarAmtBox;
    private boolean osAmtBox;
    
    private CommonReportMethodsRemote comman;
    private OtherLoanReportFacadeRemote mprFacade;
    List <DepositeMprPojo> depositeMprList = new ArrayList<DepositeMprPojo>();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public boolean isYes2Box() {
        return yes2Box;
    }

    public void setYes2Box(boolean yes2Box) {
        this.yes2Box = yes2Box;
    }
    

    public boolean isNoOfAcsBox() {
        return noOfAcsBox;
    }

    public void setNoOfAcsBox(boolean noOfAcsBox) {
        this.noOfAcsBox = noOfAcsBox;
    }

    public boolean isOsAmtBox() {
        return osAmtBox;
    }

    public void setOsAmtBox(boolean osAmtBox) {
        this.osAmtBox = osAmtBox;
    }

    public boolean isTarAcsBox() {
        return tarAcsBox;
    }

    public void setTarAcsBox(boolean tarAcsBox) {
        this.tarAcsBox = tarAcsBox;
    }

    public boolean isTarAmtBox() {
        return tarAmtBox;
    }

    public void setTarAmtBox(boolean tarAmtBox) {
        this.tarAmtBox = tarAmtBox;
    }

    public boolean isYes1Box() {
        return yes1Box;
    }

    public void setYes1Box(boolean yes1Box) {
        this.yes1Box = yes1Box;
    }

    public boolean isYes3Box() {
        return yes3Box;
    }

    public void setYes3Box(boolean yes3Box) {
        this.yes3Box = yes3Box;
    }

    public boolean isYes4Box() {
        return yes4Box;
    }

    public void setYes4Box(boolean yes4Box) {
        this.yes4Box = yes4Box;
    }

    public boolean isYes5Box() {
        return yes5Box;
    }

    public void setYes5Box(boolean yes5Box) {
        this.yes5Box = yes5Box;
    }

    public boolean isYes6Box() {
        return yes6Box;
    }

    public void setYes6Box(boolean yes6Box) {
        this.yes6Box = yes6Box;
    }

    public boolean isYes7Box() {
        return yes7Box;
    }

    public void setYes7Box(boolean yes7Box) {
        this.yes7Box = yes7Box;
    }

    public boolean isYes8Box() {
        return yes8Box;
    }

    public void setYes8Box(boolean yes8Box) {
        this.yes8Box = yes8Box;
    }


    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }
    
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }
    
    public boolean isChkBoxValue() {
        return chkBoxValue;
    }

    public void setChkBoxValue(boolean chkBoxValue) {
        this.chkBoxValue = chkBoxValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModOfMonthQly() {
        return modOfMonthQly;
    }

    public void setModOfMonthQly(String modOfMonthQly) {
        this.modOfMonthQly = modOfMonthQly;
    }

    public List<SelectItem> getModOfMonthQlyList() {
        return modOfMonthQlyList;
    }

    public void setModOfMonthQlyList(List<SelectItem> modOfMonthQlyList) {
        this.modOfMonthQlyList = modOfMonthQlyList;
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

    public String getSelectCol1() {
        return selectCol1;
    }

    public void setSelectCol1(String selectCol1) {
        this.selectCol1 = selectCol1;
    }

    public List<SelectItem> getSelectCol1List() {
        return selectCol1List;
    }

    public void setSelectCol1List(List<SelectItem> selectCol1List) {
        this.selectCol1List = selectCol1List;
    }

    public String getSelectCol2() {
        return selectCol2;
    }

    public void setSelectCol2(String selectCol2) {
        this.selectCol2 = selectCol2;
    }

    public List<SelectItem> getSelectCol2List() {
        return selectCol2List;
    }

    public void setSelectCol2List(List<SelectItem> selectCol2List) {
        this.selectCol2List = selectCol2List;
    }

    public String getSelectCol3() {
        return selectCol3;
    }

    public void setSelectCol3(String selectCol3) {
        this.selectCol3 = selectCol3;
    }

    public List<SelectItem> getSelectCol3List() {
        return selectCol3List;
    }

    public void setSelectCol3List(List<SelectItem> selectCol3List) {
        this.selectCol3List = selectCol3List;
    }

    public String getSelectCol4() {
        return selectCol4;
    }

    public void setSelectCol4(String selectCol4) {
        this.selectCol4 = selectCol4;
    }

    public List<SelectItem> getSelectCol4List() {
        return selectCol4List;
    }

    public void setSelectCol4List(List<SelectItem> selectCol4List) {
        this.selectCol4List = selectCol4List;
    }

    public String getSelectIncDec() {
        return selectIncDec;
    }

    public void setSelectIncDec(String selectIncDec) {
        this.selectIncDec = selectIncDec;
    }

    public List<SelectItem> getSelectIncDecList() {
        return selectIncDecList;
    }

    public void setSelectIncDecList(List<SelectItem> selectIncDecList) {
        this.selectIncDecList = selectIncDecList;
    }

    public String getSelectRepType() {
        return selectRepType;
    }
    
    public void setSelectRepType(String selectRepType) {
        this.selectRepType = selectRepType;
    }

    public List<SelectItem> getSelectRepTypeList() {
        return selectRepTypeList;
    }

    public void setSelectRepTypeList(List<SelectItem> selectRepTypeList) {
        this.selectRepTypeList = selectRepTypeList;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<SelectItem> getYearList() {
        return yearList;
    }

    public void setYearList(List<SelectItem> yearList) {
        this.yearList = yearList;
    }
    
    public DepositeMpr() {
        req = getRequest();
        try {
            
        comman = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
        mprFacade = (OtherLoanReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherLoanReportFacade");
        this.setTodayDate(getTodayDate());
        getMonthlyList();
        getyearlyList();
        getQuaterlyList();
        selectMonthlyQuaterly();
       
        selectRepTypeList = new ArrayList<SelectItem>();
        selectRepTypeList.add(new SelectItem("--Select--","--Select--"));
        selectRepTypeList.add(new SelectItem("A/c Type Wise","A/c Type Wise"));
        selectRepTypeList.add(new SelectItem("A/c Nature Wise","A/c Nature Wise"));
        
        selectCol1List = new ArrayList<SelectItem>();
        selectCol1List.add(new SelectItem("Select2","--Select--"));
        selectCol1List.add(new SelectItem("O/S As On 01.04."));
        selectCol1List.add(new SelectItem("Begining Of The Year"));
        selectCol1List.add(new SelectItem("N/A"));
        
        selectCol2List = new ArrayList<SelectItem>();
        selectCol2List.add(new SelectItem("Select3","--Select--"));
        selectCol2List.add(new SelectItem("O/S At The End Of Prev. Mon."));
        selectCol2List.add(new SelectItem("Last Month"));
        selectCol2List.add(new SelectItem("N/A"));
        
        selectCol3List = new ArrayList<SelectItem>();
        selectCol3List.add(new SelectItem("Select4","--Select--"));
        selectCol3List.add(new SelectItem("O/S At The End Of Curr. Mon."));
        selectCol3List.add(new SelectItem("Current Month"));
        selectCol3List.add(new SelectItem("N/A"));
        
        selectCol4List = new ArrayList<SelectItem>();
        selectCol4List.add(new SelectItem("Select5","--Select--"));
        selectCol4List.add(new SelectItem("Current Month"));
        selectCol4List.add(new SelectItem("Increment/Decrement"));
        selectCol4List.add(new SelectItem("N/A"));
        
        selectIncDecList = new ArrayList<SelectItem>();
        selectIncDecList.add(new SelectItem("Select6","--Select--"));
        selectIncDecList.add(new SelectItem("Last Month"));
        selectIncDecList.add(new SelectItem("Last Year"));
        selectIncDecList.add(new SelectItem("N/A")); 
        
        }catch(Exception e){
            this.setMessage(e.getMessage());
        } 
    }
    
    public void selectMonthlyQuaterly() {
        
        modOfMonthQlyList = new ArrayList<SelectItem> ();
        modOfMonthQlyList.add(new SelectItem("0","--Select--"));
        modOfMonthQlyList.add(new SelectItem("M","Monthly"));
        modOfMonthQlyList.add(new SelectItem("Q","Quaterly"));
        
    }
    public void getMonthlyList() {
       monthList = new ArrayList<SelectItem>();
       monthList.add(new SelectItem("0","--Select--"));
       monthList.add(new SelectItem("1","January"));
       monthList.add(new SelectItem("2","February"));
       monthList.add(new SelectItem("3","March"));
       monthList.add(new SelectItem("4","April"));
       monthList.add(new SelectItem("5","May"));
       monthList.add(new SelectItem("6","June"));
       monthList.add(new SelectItem("7","July"));
       monthList.add(new SelectItem("8","August"));
       monthList.add(new SelectItem("9","September"));
       monthList.add(new SelectItem("10","October"));
       monthList.add(new SelectItem("11","November"));
       monthList.add(new SelectItem("12","December"));  
    }
    public void getQuaterlyList(){
 
       monthList = new ArrayList<SelectItem>();
       monthList.add(new SelectItem("0","--Select--"));
       monthList.add(new SelectItem("3","March"));
       monthList.add(new SelectItem("6","June"));
       monthList.add(new SelectItem("9","September"));
       monthList.add(new SelectItem("12","December")); 
     
    }
    
    public void getMonthQuater() {
        
        if(this.modOfMonthQly.equals("M")){
            this.getMonthlyList();
            this.flag=true;
        }else {
            if(this.modOfMonthQly.equals("Q")){
                this.getQuaterlyList();
                this.flag=true;
            }else{
                this.flag=false;}
        }      
    }  
   public void getyearlyList() {
   yearList = new ArrayList<SelectItem>();   
    Calendar po = Calendar.getInstance(); 
    for(int i =-10; i<=0; i++){
        int year=po.get(Calendar.YEAR)+i;
        yearList.add(new SelectItem(String.valueOf(year)));
    } 
 }
       
   public void viewReport() {
       
       String branchName = "";
       String address = "";
       String report = "Deposite Mpr Report";
       String dCo1 ="";
       String dCo2 ="";
       String dCo3 ="";
       String dCo4 ="";
       String tarAcno="";
       String tarAmt="";
       String tarYear="";
       String fromDt;
       String toDt;
        Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH,Integer.parseInt(month)-1);
            cal.set(Calendar.YEAR, year);
          int fd = cal.getActualMinimum(Calendar.DATE); 
                cal.set(Calendar.DATE, fd);
        fromDt = ymd.format(cal.getTime()); 
        int ld = cal.getActualMaximum(Calendar.DATE);
        cal.set(Calendar.DATE, ld);
        toDt = ymd.format(cal.getTime());
        String rpdt = toDt;
//        String finDt;
//        String lastDt;
//        lastDt = (year + 1) + "0331";
        
        String yyyy = String.valueOf(year);
        String yy =  yyyy.substring(2, 4);
         
       try {
       List brnAdd = new ArrayList();
       brnAdd = comman.getBranchNameandAddress(getOrgnBrCode());
                if(brnAdd.size() > 0) {
                  branchName = (String) brnAdd.get(0);
                  address= (String) brnAdd.get(1);
                }
                
//           if(noOfAcsBox == false && tarAcsBox == false && tarAmtBox == false && osAmtBox == false ) {
//               setMessage("Please Select At Least One Fields");
//               return;
//           }
              if(selectCol1.equalsIgnoreCase("N/A")&& selectCol2.equalsIgnoreCase("N/A")&& selectCol3.equalsIgnoreCase("N/A")&& selectCol4.equalsIgnoreCase("N/A")) {
                  setMessage("Please Select At Least One Column Field");
                  //this.flag=false; 
                  return;
              }
              
              if(selectCol1.equalsIgnoreCase("Select2")|| yes1Box==false && yes2Box==false){
                  setMessage("Please Select At Least One Field of Column One");
                  //this.flag=false;
                  return;
              }
              if(selectCol2.equalsIgnoreCase("Select3") || yes3Box==false && yes4Box==false){
                  setMessage("Please Select At Least One Field of Column Two");
                  //this.flag=false;
                   return;
              }
              if(selectCol3.equalsIgnoreCase("Select4") || yes5Box==false && yes6Box==false){
                  setMessage("Please Select At Least One Field of Column Three");
                  //this.flag=false;
                  return;
              }
              if(selectCol4.equalsIgnoreCase("Select5")|| yes7Box==false && yes8Box==false){
                  setMessage("Please Select At Least One Field of Column Four");
                 // this.flag=false;
                  return;
              }
//              if(selectCol3==selectCol4){
//                  setMessage("Please Select Distinct Column Field");
//                  //this.flag=false;
//                  return;
//              }
              if(selectRepType.equalsIgnoreCase("--Select--")){
                  setMessage("Please Select Report Type!");
                  return;
              }
                  
        if(this.modOfMonthQly.equalsIgnoreCase("0")) {
            setMessage("Please select Monthly/Quaterly!");
            return;
        }
        if(this.month.equalsIgnoreCase("0")) {
            setMessage("Please select For the month!");
            return;
        }else if(this.month.equalsIgnoreCase("0")){
           setMessage("Please select For the Quater month!");
            return; 
        }
        
         Map fillMap = new HashMap();
         fillMap.put("pPrintedBy", this.getUserName());
         fillMap.put("pBranchName", branchName);
         fillMap.put("pAddress", address);
         fillMap.put("pReportName", report);
         fillMap.put("pReportDt",dmy.format(ymd.parse(rpdt)) );
         fillMap.put("pPrintDt", this.getTodayDate());
         fillMap.put("pSelectRepType",this.selectRepType);
        // fillMap.put("pTarAcno", tarAcno);
        // fillMap.put("pTarAmt", tarAmt);
         
         String tar[]= {"Target Of Year","Tar.A/c","Tar.Amt"};
            if(tarAcsBox == true && tarAmtBox == true){
                    tarYear = tar[0];
               fillMap.put("pSelectTarYear", tarYear);
                    tarAcno = tar[1];
               fillMap.put("pTarAcno", tarAcno); 
                    tarAmt = tar[2];
               fillMap.put("pTarAmt", tarAmt);
            }
            
           String incdic[] = {"Current Month","Increment/Decrement","No.A/c","Amount"};
           if(selectCol4.equalsIgnoreCase("Current Month")){
                dCo4 = incdic[0]; 
            fillMap.put("pSelectCol4", dCo4);
                   dCo4 = incdic[2];
          fillMap.put("pNoAcno",dCo1);      
                  dCo4 = incdic[3];
          fillMap.put("pAmount", dCo1);
               
           }else{
                   
                dCo4 = incdic[1];
            fillMap.put("pSelectCol4", dCo4);
           }
               
    
         String yyy[]= {"O/S As On 01.04.","Begining Of The Year","No.A/c","Amount"};
          if(selectCol1.equalsIgnoreCase("O/S As On 01.04.")){
                 dCo1 = yyy[0] + yy;
          fillMap.put("pSelectCol1", dCo1);
                  dCo1 = yyy[2];
          fillMap.put("pNoAcno",dCo1);      
                  dCo1 = yyy[3];
          fillMap.put("pAmount", dCo1);
                  
          }else if(selectCol1.equalsIgnoreCase("Begining Of The Year")) {
                 dCo1 = yyy[1];
               fillMap.put("pSelectCol1",dCo1);
                        dCo1 = yyy[2];
                fillMap.put("pNoAcno",dCo1);      
                        dCo1 = yyy[3];
                 fillMap.put("pAmount", dCo1);
               
            }
          //col2
         String yyy1[]= {"O/S At The End Of Prev. Mon.","Last Month","No.A/c","Amount"};
                if(selectCol2.equalsIgnoreCase("O/S At The End Of Prev. Mon.")) {
                        dCo2 = yyy1[0];
                fillMap.put("pSelectCol2", dCo2);
                        dCo2 = yyy1[2];
                fillMap.put("pNoAcno1",dCo2);
                        dCo2 = yyy1[3];
                fillMap.put("pAmount1", dCo2);             
                }else if(selectCol2.equalsIgnoreCase("Last Month")){
                    dCo2 = yyy1[1];
                    fillMap.put("pSelectCol2",dCo2);
                                dCo2 = yyy1[2];
                    fillMap.put("pNoAcno1",dCo2);
                                dCo2 = yyy1[3];
                    fillMap.put("pAmount1",dCo2);
                    
                }
                
          String yyy2[]= {"O/S At The End Of Curr. Mon.","Current Month","No.A/c","Amount"};
                    if(selectCol3.equalsIgnoreCase("O/S At The End Of Curr. Mon.")){
                                dCo3 = yyy2[0];
                fillMap.put("pSelectCol3", dCo3);
                        dCo3 = yyy2[2];
                fillMap.put("pNoAcno2",dCo3);
                        dCo3 = yyy2[3];
                fillMap.put("pAmount2", dCo3);             
                }else if(selectCol3.equalsIgnoreCase("Current Month")){
                    dCo3 = yyy2[1];
                    fillMap.put("pSelectCol3", dCo3);
                                dCo3 = yyy2[2];
                    fillMap.put("pNoAcno2",dCo3);
                                dCo3 = yyy2[3];
                    fillMap.put("pAmount2",dCo3);    
                    }
               
         depositeMprList = mprFacade.getDepositeMpr(this.selectRepType,this.selectCol1,this.selectCol2,this.selectCol3,this.selectCol4,this.selectIncDec,this.modOfMonthQly,fromDt,toDt,this.getOrgnBrCode(),noOfAcsBox,tarAcsBox,tarAmtBox,osAmtBox);
         if(depositeMprList.isEmpty()) {
             message = "No data found in this date from database";
             return;
         }
             new ReportBean().jasperReport("DepositeMprReport", "text/html",
                    new JRBeanCollectionDataSource(depositeMprList), fillMap, "Deposite Mpr Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        
       }catch (Exception e){
           setMessage(e.getMessage());
       }   
   }
    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
    public void refreshForm() {
        
        this.setMessage("");
        this.setMonth("S");
        this.setModOfMonthQly("0");
        this.setSelectCol1("Select2");
        this.setYear(0);
        this.setSelectRepType("Select1");
        this.setSelectIncDec("Select6");
        this.setSelectCol4("Select5");
        this.setSelectCol3("Select4");
        this.setSelectCol2("Select3");
        yes1Box = false;
        yes2Box = false;
        yes3Box = false;
        yes4Box = false;
        yes5Box = false;
        yes6Box = false;
        yes6Box = false;
        yes7Box = false;
        yes8Box = false;
        noOfAcsBox = false;
        tarAcsBox = false;
        tarAmtBox = false;
        osAmtBox = false;
        
    }
    public String exitAction() {
        
       return "case1"; 
    }
        
}
