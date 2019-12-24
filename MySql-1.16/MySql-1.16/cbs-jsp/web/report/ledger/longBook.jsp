<%-- 
    Document   : longbook
    Created on : Dec 14, 2011, 11:17:59 AM
    Author     : Ankit Verma
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
            <title>Long Book</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{LongBook.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Long Book"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LongBook.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{LongBook.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanel1"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblAcType" styleClass="headerLabel"  value="Account Type"  />
                        <h:selectOneListbox id="selectListBox" value="#{LongBook.selectAcType}" styleClass="ddlist" size="1"  style="width:120px" >
                            <f:selectItems id="selectAcTypeList" value="#{LongBook.selectAcTypeList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel id="lblAcStatus" styleClass="headerLabel"  value="Account Status"  />
                        <h:selectOneListbox id="selectAcListBox" value="#{LongBook.selectAcStatus}" styleClass="ddlist" size="1" style="width:120px">
                            <f:selectItems id="selectAcStatusList" value="#{LongBook.selectAcStatusList}" />
                        </h:selectOneListbox>  
                        <%--<h:outputLabel id="lblToDate" styleClass="headerLabel"  value="To Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDate"   styleClass="input calInstDate" style="width:70px"  value="#{LongBook.date}"/>
                            <font color="purple">DD/MM/YYYY</font>
                        </h:panelGroup>--%>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanel1235"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblToDate" styleClass="headerLabel"  value="To Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDate"   styleClass="input calInstDate" style="width:70px"  value="#{LongBook.date}">
                                <a4j:support action="#{LongBook.dateValidate}" event="onblur" reRender="ddOrderBy" focus="#{LongBook.focusId}"/>
                            </h:inputText>
                            <font color="purple">DD/MM/YYYY</font>
                        </h:panelGroup>
                        <h:outputLabel id="orderLbl" value="Order By :" styleClass="output">
                                <font class="required" style="color:red">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox id="ddOrderBy" styleClass="ddlist" value="#{LongBook.orderBy}" size="1" style="width:120px" disabled="#{LongBook.disabled}">
                            <f:selectItems value="#{LongBook.orderByList}"/>
                        </h:selectOneListbox>  
                        
                    </h:panelGrid>
                            
                    <h:panelGrid columns="3" columnClasses="col2,col6" id="gridpanel129"   style="height:30px;" styleClass="row2" width="100%" >
                        <h:panelGroup >
                            <h:selectBooleanCheckbox id="chkBoxTime"  value="#{LongBook.chkBoxValue}" >
                                <a4j:support action="#{LongBook.showTimeBox}" event="onclick" reRender="showPanelgrid" />
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblChk" styleClass="headerLabel"  value="Time Based Report" />
                        </h:panelGroup>
                        <h:panelGrid id="showPanelgrid" style="#{LongBook.flag}" width="100%" columns="4" columnClasses="col2,col7,col2,col7"> 
                            <h:outputLabel id="lblFrom" styleClass="headerLabel"  value="From"  />
                            <h:panelGroup id="pgTime1" >
                                <h:outputLabel id="lblHH1" styleClass="headerLabel"  value="HH"  />
                                <h:selectOneListbox id="selectHH1" value="#{LongBook.selectHH1}" styleClass="ddlist" size="1" >
                                    <f:selectItems id="selectHH1List" value="#{LongBook.selectHH1List}" />
                                </h:selectOneListbox>  
                                <h:outputLabel id="lblMM1" styleClass="headerLabel"  value="MM"  />
                                <h:selectOneListbox id="selectMM1" value="#{LongBook.selectMM1}" styleClass="ddlist" size="1">
                                    <f:selectItems id="selectMM1List" value="#{LongBook.selectMM1List}" />
                                </h:selectOneListbox>  
                                <h:selectOneListbox id="selectSS1" value="#{LongBook.selectSS1}" styleClass="ddlist" size="1">
                                    <f:selectItems id="selectSS1List" value="#{LongBook.selectSS1List}" />
                                </h:selectOneListbox>  
                            </h:panelGroup>
                            <h:outputLabel id="lblTo" styleClass="headerLabel"  value="To"  />        
                            <h:panelGroup id="pgTime2" >
                                <h:outputLabel id="lblHH2" styleClass="headerLabel"  value="HH"  />
                                <h:selectOneListbox id="selectHH2" value="#{LongBook.selectHH2}" styleClass="ddlist" size="1">
                                    <f:selectItems id="selectHH2List" value="#{LongBook.selectHH2List}" />
                                </h:selectOneListbox>  
                                <h:outputLabel id="lblMM2" styleClass="headerLabel"  value="MM"  />
                                <h:selectOneListbox id="selectMM2" value="#{LongBook.selectMM2}" styleClass="ddlist" size="1">
                                    <f:selectItems id="selectMM2List" value="#{LongBook.selectMM2List}" />
                                </h:selectOneListbox>  
                                <h:selectOneListbox id="selectSS2" value="#{LongBook.selectSS2}" styleClass="ddlist" size="1">
                                    <f:selectItems id="selectSS2List" value="#{LongBook.selectSS2List}" />
                                </h:selectOneListbox>  
                            </h:panelGroup>
                        </h:panelGrid> 
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnView" value="View Report"   action="#{LongBook.viewReport}" reRender="stxtError,mainPanelGrid,txtDate" oncomplete="setMask()">
                            </a4j:commandButton>
                             <h:commandButton id="btnDownload"  value="PDF Download" action="#{LongBook.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{LongBook.refreshForm}"  reRender="mainPanelGrid,txtDate" oncomplete="setMask()">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{LongBook.exitAction}" reRender="mainPanelGrid" >
                            </a4j:commandButton>

                            
                        </h:panelGroup>
                    </h:panelGrid>
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
