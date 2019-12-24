<%-- 
    Document   : loanMpr
    Created on : Jun 10, 2013, 11:00:33 AM
    Author     : Athar Reza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Loan MPR Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calFromDate").mask("99/99/9999");
                    jQuery(".calToDate").mask("99/99/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="mpr">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanMpr.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Loan MPR"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{LoanMpr.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{LoanMpr.message}" styleClass="error"/>
                    </h:panelGrid>
                    
                    <h:panelGroup>
                    <h:panelGrid columnClasses="col3,col3,col3,col3" columns="4" id="gridPanel3"  styleClass="row2"> 
                        <h:outputLabel id="lbreportType" styleClass="label"  value="ReportType"/>
                        <h:selectOneListbox id="ddreportTypeList" styleClass="ddlist" size="1" style="width: 100px" value="#{LoanMpr.cmbReptype}" >
                        <f:selectItems value="#{LoanMpr.reptypeList}"/>
                        <a4j:support event="onblur" action="#{LoanMpr.selMnthQtr}" reRender="errmsg,ddmonthList" />                        
                        </h:selectOneListbox>
                        <h:outputLabel id="lboptions" styleClass="label"  value="Select Options"/>
                        <h:selectOneListbox id="ddoptionsList" styleClass="ddlist" size="1" style="width: 100px" value="#{LoanMpr.cmbOption}" >
                        <f:selectItems value="#{LoanMpr.optionList}"/>
                        <a4j:support event="onblur" action="#{LoanMpr.options}" reRender="errmsg,ddoptionsList" /> 
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3" columns="4" id="gridPanel4"  styleClass="row2"> 
                        <h:outputLabel id="lbmonth" styleClass="label"  value="For The Month/Quarter"/>
                        <h:selectOneListbox id="ddmonthList" styleClass="ddlist" size="1" style="width: 100px" value="#{LoanMpr.cmbMon}" >
                        <f:selectItems value="#{LoanMpr.monList}"/>   
                        </h:selectOneListbox>
                        <h:outputLabel id="lbyear" styleClass="label"  value="Year"/>
                        <h:selectOneListbox id="ddyearList" styleClass="ddlist" size="1" style="width: 100px" value="#{LoanMpr.cmbYear}" >
                        <f:selectItems value="#{LoanMpr.yearList}"/>
                       
                        </h:selectOneListbox>
                    </h:panelGrid>
                    </h:panelGroup>
                   
                    <h:panelGrid columnClasses="col3,col3" columns="2"  id="gridPanel5" style="height:30px;" styleClass="row1" width="100%"> 
                    <h:panelGroup>
                        <h:selectBooleanCheckbox id="chkBoxTime"  value="#{LoanMpr.chkOs1}" >
                        <%--a4j:support action="#{LoanMpr.chkBoxValueA}" event="onclick" reRender="showPanelgrid" /--%>
                        </h:selectBooleanCheckbox>
                        <h:outputLabel id="lblChk" styleClass="headerLabel"  value="O/S As On 01 / 04 /" style="font-weight: bolder;" />   
                    </h:panelGroup>
                    </h:panelGrid>
                   
                    <h:panelGroup>
                    <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" columns="8" id="gridPanel6" style="height:30px;" styleClass="row2" width="100%">                     
                         <h:selectBooleanCheckbox id="chkBoxTime2"  value="#{LoanMpr.chkTarac}" >                       
                         </h:selectBooleanCheckbox>
                         <h:outputLabel id="lblChk2" styleClass="headerLabel"  value="Target A/c's" />
                         <h:selectBooleanCheckbox id="chkBoxTime7"  value="#{LoanMpr.chkOsbal1}" >                        
                         </h:selectBooleanCheckbox>
                         <h:outputLabel id="lblChk7" styleClass="headerLabel"  value="O/S Amount" />
                         <h:selectBooleanCheckbox id="chkBoxTime4"  value="#{LoanMpr.chkOverdue1}" >                        
                         </h:selectBooleanCheckbox>
                         <h:outputLabel id="lblChk4" styleClass="headerLabel"  value="OverDue" />
                         <h:selectBooleanCheckbox id="chkBoxTime5"  value="#{LoanMpr.chkDisbAmt1}" >                         
                         </h:selectBooleanCheckbox>
                         <h:outputLabel id="lblChk5" styleClass="headerLabel"  value="Disburse Amt." /> 
                     
                     </h:panelGrid>
                     <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" columns="8" id="gridPanel7" style="height:30px;" styleClass="row2" width="100%">                      
                         <h:selectBooleanCheckbox id="chkBoxTime3"  value="#{LoanMpr.chkAc1}" >                         
                         </h:selectBooleanCheckbox>
                         <h:outputLabel id="lblChk3" styleClass="headerLabel"  value="No. Of A/c's" />    
                         <h:selectBooleanCheckbox id="chkBoxTime6"  value="#{LoanMpr.chkTaramt}" >                         
                         </h:selectBooleanCheckbox>
                         <h:outputLabel id="lblChk6" styleClass="headerLabel"  value="Target Amount" />                             
                         <h:selectBooleanCheckbox id="chkBoxTime8"  value="#{LoanMpr.chkRec1}" >                        
                         </h:selectBooleanCheckbox>
                         <h:outputLabel id="lblChk8" styleClass="headerLabel"  value="Recovery" />  
                         <h:outputText/>  
                         <h:outputText/>
                     </h:panelGrid> 
                     </h:panelGroup>
                          
                    <h:panelGrid columnClasses="col3,col3" columns="2" id="gridPanel8" style="height:30px;" styleClass="row1" width="100%"> 
                    <h:panelGroup>
                        <h:selectBooleanCheckbox id="chkBoxTime1"  value="#{LoanMpr.chkOs2}" >
                        <%--a4j:support action="#{LoanMpr.chkBoxValueB}" event="onclick" reRender="showPanelgrid" /--%>
                        </h:selectBooleanCheckbox>
                        <h:outputLabel id="lblChk1" styleClass="headerLabel"  value="O/S Of Current Year"  style="font-weight: bolder;"/> 
                    </h:panelGroup> 
                    </h:panelGrid>      
                                     
                    <h:panelGroup>                        
                     <h:panelGrid columnClasses="col3,col3" columns="2" id="gridPanel9" style="height:30px;" styleClass="row2" width="100%"> 
                         <h:panelGroup>
                          <h:selectBooleanCheckbox id="chkBoxTime13"  value="#{LoanMpr.chkLastMon}" >                          
                          </h:selectBooleanCheckbox>                           
                        <h:outputLabel id="lblChk13" styleClass="headerLabel"  value="Last Month/Quarter" style="font-weight: bolder;" />
                        </h:panelGroup> 
                    </h:panelGrid>
                    
                    <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" columns="8" id="gridPanel10" style="height:30px;" styleClass="row1" width="100%">
                        <h:selectBooleanCheckbox id="chkBoxTime10"  value="#{LoanMpr.chkAc2}" >                        
                         </h:selectBooleanCheckbox>
                         <h:outputLabel id="lblChk10" styleClass="headerLabel"  value="No. Of A/c's" />    
                         <h:selectBooleanCheckbox id="chkBoxTime11"  value="#{LoanMpr.chkOverDue2}" >                        
                         </h:selectBooleanCheckbox>
                         <h:outputLabel id="lblChk11" styleClass="headerLabel"  value="OverDue" />   
                         <h:selectBooleanCheckbox id="chkBoxTime12"  value="#{LoanMpr.chkRec2}" >                                
                         </h:selectBooleanCheckbox>
                         <h:outputLabel id="lblChk12" styleClass="headerLabel"  value="Recovery" />
                         <h:outputLabel/>
                         <h:outputLabel/>
                    </h:panelGrid>  
                         
                    <h:panelGrid columnClasses="col3,col3" columns="2" id="gridPanel11" style="height:30px;" styleClass="row2" width="100%"> 
                         <h:panelGroup>
                         <h:selectBooleanCheckbox id="chkBoxTime9"  value="#{LoanMpr.chkCurMon}" >                        
                         </h:selectBooleanCheckbox>
                        <h:outputLabel id="lblChk9" styleClass="headerLabel" value="Current Month/Quarter"  style="font-weight: bolder;"/> 
                         </h:panelGroup>
                    </h:panelGrid>   
                        
                    <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" columns="8" id="gridPanel12" style="height:30px;" styleClass="row1" width="100%">
                        <h:selectBooleanCheckbox id="chkBoxTime14"  value="#{LoanMpr.chkOsbal2}" >                          
                        </h:selectBooleanCheckbox>
                        <h:outputLabel id="lblChk14" styleClass="headerLabel"  value="O/S Amount" />  
                        <h:selectBooleanCheckbox id="chkBoxTime15"  value="#{LoanMpr.chkDisb2}" >                           
                        </h:selectBooleanCheckbox>
                        <h:outputLabel id="lblChk15" styleClass="headerLabel"  value="Disburse Amt." /> 
                        <h:selectBooleanCheckbox id="chkBoxTime16"  value="#{LoanMpr.chkIncrDcr}" >                               
                        </h:selectBooleanCheckbox>
                        <h:outputLabel id="lblChk16" styleClass="headerLabel"  value="%Increment/Decrement" /> 
                        <h:selectOneListbox id="ddreportList1" styleClass="ddlist" size="1" style="width: 100px" value="#{LoanMpr.incrdcr}" >
                        <f:selectItems value="#{LoanMpr.incrdcrlist}"/>     
                        </h:selectOneListbox>
                        <h:outputLabel/>
                    </h:panelGrid>  
                    </h:panelGroup>               
                    
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" action="#{LoanMpr.print}" type="submit" value="Print" reRender="errmsg">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" action="#{LoanMpr.refresh}" value="Refresh"  reRender="PanelGridMain" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnExit" action="#{LoanMpr.exit}" value="Exit" reRender="errmsg">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>    
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
