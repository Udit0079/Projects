<%-- 
    Document   : depositesMpr
    Created on : Apr 24, 2013, 1:08:17 PM
    Author     : Athar Reza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Deposite Mpr</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DepositeMpr.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Deposite Mpr"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DepositeMpr.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{DepositeMpr.message}"/>
                    </h:panelGrid> 
                    <%--h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel2"   style="height:30px;" styleClass="row1" width="100%" --%>
               <h:panelGroup>
                   <h:panelGrid columns="10" columnClasses="col17,col17,col17,col17,col17,col17,col17,col17,col17,col17" id="gridpanel11"   style="height:30px;" styleClass="row1" width="100%" >
                        
                        <h:outputLabel id="lblAcType1" styleClass="headerLabel"  value="Report Type"  />
                        <h:selectOneListbox id="selectListBox1" value="#{DepositeMpr.selectRepType}" styleClass="ddlist" size="1"  style="width:120px" >
                            <f:selectItems id="selectAcTypeList1" value="#{DepositeMpr.selectRepTypeList}" />
                        </h:selectOneListbox>
                         <h:outputText  value="No.of A/c'S" styleClass="label"/>
                         <h:selectBooleanCheckbox value="#{DepositeMpr.noOfAcsBox}"/>
                         <h:outputText  value="Target A/c'S" styleClass="label" />
                         <h:selectBooleanCheckbox value="#{DepositeMpr.tarAcsBox}"/>
                         <h:outputText  value="Target Amount" styleClass="label" />
                         <h:selectBooleanCheckbox value="#{DepositeMpr.tarAmtBox}"/>
                         <h:outputText  value="O/s Amount" styleClass="label" />
                         <h:selectBooleanCheckbox value="#{DepositeMpr.osAmtBox}"/>
                        <%--      
                        <h:outputLabel id="lblChk12" styleClass="headerLabel"  value="O/s Amount" />
                        <h:selectBooleanCheckbox id="chkBoxTime12"  value="#{DepositeMpr.chkBoxValue}" >
                            <a4j:support action="#{DepositeMpr.showOsAmountBox}" event="onclick" reRender="selectListBox1" />
                        </h:selectBooleanCheckbox>
                          --%>
                            
                   </h:panelGrid>  
                        <h:panelGrid columns="10" columnClasses="col17,col17,col17,col17,col17,col17,col17,col17,col17,col17" id="gridpane222"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblAcStatus2" styleClass="headerLabel"  value="Col1"  />
                        <h:selectOneListbox id="selectAcListBox2" value="#{DepositeMpr.selectCol1}" styleClass="ddlist" size="1" style="width:120px">
                            <f:selectItems id="selectAcStatusList2" value="#{DepositeMpr.selectCol1List}" />
                        </h:selectOneListbox>    
                        <h:outputText  value="Yes/No" styleClass="label" />
                        <h:selectBooleanCheckbox value="#{DepositeMpr.yes1Box}"/>   
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>     
                        <h:outputText  value="Yes/No" styleClass="label" />
                        <h:selectBooleanCheckbox value="#{DepositeMpr.yes2Box}"/>
                        </h:panelGrid>
                   <h:panelGrid columns="10" columnClasses="col17,col17,col17,col17,col17,col17,col17,col17,col17,col17" id="gridpane333"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblAcStatus3" styleClass="headerLabel"  value="Col2"  />
                        <h:selectOneListbox id="selectAcListBox3" value="#{DepositeMpr.selectCol2}" styleClass="ddlist" size="1" style="width:120px">
                            <f:selectItems id="selectAcStatusList3" value="#{DepositeMpr.selectCol2List}" />
                        </h:selectOneListbox>
                        <h:outputText  value="Yes/No" styleClass="label" />
                        <h:selectBooleanCheckbox value="#{DepositeMpr.yes3Box}"/> 
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText  value="Yes/No" styleClass="label" />
                        <h:selectBooleanCheckbox value="#{DepositeMpr.yes4Box}"/>   
                    </h:panelGrid>
                    <h:panelGrid columns="10" columnClasses="col17,col17,col17,col17,col17,col17,col17,col17,col17,col17" id="gridpane444"   style="height:30px;" styleClass="row1" width="100%" > 
                        <h:outputLabel id="lblAcStatus4" styleClass="headerLabel"  value="Col3"  />
                        <h:selectOneListbox id="selectAcListBox4" value="#{DepositeMpr.selectCol3}" styleClass="ddlist" size="1" style="width:120px">
                            <f:selectItems id="selectAcStatusList4" value="#{DepositeMpr.selectCol3List}" />
                        </h:selectOneListbox> 
                        <h:outputText  value="Yes/No" styleClass="label" />
                        <h:selectBooleanCheckbox value="#{DepositeMpr.yes5Box}"/>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText  value="Yes/No" styleClass="label" />
                        <h:selectBooleanCheckbox value="#{DepositeMpr.yes6Box}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="10" columnClasses="col17,col17,col17,col17,col17,col17,col17,col17,col17,col17" id="gridpane555"   style="height:30px;" styleClass="row1" width="100%" > 
                        <h:outputLabel id="lblAcStatus5" styleClass="headerLabel"  value="Col4"  />
                        <h:selectOneListbox id="selectAcListBox5" value="#{DepositeMpr.selectCol4}" styleClass="ddlist" size="1" style="width:120px">
                            <f:selectItems id="selectAcStatusList5" value="#{DepositeMpr.selectCol4List}" />
                        </h:selectOneListbox> 
                        <h:outputText  value="Yes/No" styleClass="label" />
                        <h:selectBooleanCheckbox value="#{DepositeMpr.yes7Box}"/>
                         <h:outputText/>
                         <h:outputText/>
                         <h:outputText/>
                         <h:outputText/>
                         <h:outputText  value="Yes/No" styleClass="label" />
                         <h:selectBooleanCheckbox value="#{DepositeMpr.yes8Box}"/>
                    </h:panelGrid>
                        <%--h:panelGrid columnClasses="col2,col6" columns="2" id="gridpanel1" style="width:100%;text-align:left;" styleClass="row1"--%>
                  <h:panelGrid columns="10" columnClasses="col17,col17,col17,col17,col17,col17,col17,col17,col17,col17" id="gridpanel1"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblAcStatus6" styleClass="headerLabel"  value="Inc/Dec With"  />
                        <h:selectOneListbox id="selectAcListBox6" value="#{DepositeMpr.selectIncDec}" styleClass="ddlist" size="1" style="width:120px">
                            <f:selectItems id="selectAcStatusList6" value="#{DepositeMpr.selectIncDecList}" />
                        </h:selectOneListbox> 
                            <h:outputText/>
                            <h:outputText/>
                            <h:outputText/>
                            <h:outputText/>
                            <h:outputText/>
                            <h:outputText/>
                            <h:outputText/>
                            <h:outputText/>    
                  </h:panelGrid>   
                  </h:panelGroup>     
                    <h:panelGroup>
                        <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel2"   style="height:30px;" styleClass="row2" width="100%" >    
                       <h:outputLabel id="lblAcStatus7" styleClass="headerLabel"  value="Monthly/Quaterly"  />
                        <h:selectOneListbox id="selectAcListBox7" value="#{DepositeMpr.modOfMonthQly}" styleClass="ddlist" size="1" style="width:120px">
                            <f:selectItems id="selectAcStatusList7" value="#{DepositeMpr.modOfMonthQlyList}" />
                            <a4j:support action="#{DepositeMpr.getMonthQuater}" event="onblur" reRender="stxtError,selectAcListBox8" />
                        </h:selectOneListbox> 
                         <h:outputLabel id="lblAcStatus8" styleClass="headerLabel"  value="For The Month/Quater "  />
                        <h:selectOneListbox id="selectAcListBox8" value="#{DepositeMpr.month}" styleClass="ddlist" size="1" style="width:120px">
                            <f:selectItems id="selectAcStatusList8" value="#{DepositeMpr.monthList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel id="lblAcStatus9" styleClass="headerLabel"  value="Year"  />
                        <h:selectOneListbox id="selectAcListBox9" value="#{DepositeMpr.year}" styleClass="ddlist" size="1" style="width:120px">
                            <f:selectItems id="selectAcStatusList9" value="#{DepositeMpr.yearList}" />
                        </h:selectOneListbox>  
                        </h:panelGrid>
                     <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <%--a4j:commandButton  id="btnView" value="View Report"   action="#{DepositeMpr.viewReport}" reRender="stxtError,mainPanelGrid,txtDate" oncomplete="#{rich:element('txtDate')}.style=setMask()"--%>
                           <a4j:commandButton  id="btnView" value="View Report"   action="#{DepositeMpr.viewReport}" reRender="stxtError">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{DepositeMpr.refreshForm}"  reRender="mainPanelGrid" oncomplete="setMask()">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{DepositeMpr.exitAction}" reRender="mainPanelGrid" >
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                       </h:panelGroup>   
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
