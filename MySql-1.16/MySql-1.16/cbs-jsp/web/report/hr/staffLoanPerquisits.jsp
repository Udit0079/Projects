<%-- 
    Document   : staffLoanPerquisits
    Created on : 12 Jan, 2018, 2:52:46 PM
    Author     : root
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Staff Loan Perquisits</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="a1" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">

                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{StaffLoanPerquisits.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="User Maintenance"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{StaffLoanPerquisits.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="errorMsgReporting"   width="100%"   style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputText id="errorGrid" value="#{StaffLoanPerquisits.message}" style="color:red" styleClass="output"/>
                    </h:panelGrid>

                    <h:panelGrid id="groupPanel2" columns="6" columnClasses="col1,col2,col1,col2,col17,col2" style="height:30px;width:100%"  styleClass="row2" width="100%">
                        <h:outputLabel id="lblBranch" styleClass="label" value="Scheme Name :"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBranch" styleClass="ddlist" value="#{StaffLoanPerquisits.schemeName}" size="1" style="width:200px">
                            <f:selectItems value="#{StaffLoanPerquisits.schemeNameList}"/>
                        </h:selectOneListbox>

                        <h:outputLabel id="lblFromDate" styleClass="label" value="New Interest Rate :">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  id="input1" styleClass="input" value="#{StaffLoanPerquisits.interestRate}"  maxlength="5" style="width:80px"/>
                        <h:outputLabel id="dateFrom" styleClass="label"  value="Till Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDateFrom"  styleClass="input calInstDate" style="width:70px" value="#{StaffLoanPerquisits.fromDate}" > 

                            </h:inputText>
                            <font color="purple" >DD/MM/YYYY</font>
                        </h:panelGroup>
                    </h:panelGrid>

                    <%--h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel3"   style="height:30px;" styleClass="row2" width="100%" >

                        <h:outputLabel id="dateFrom" styleClass="label"  value="From Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDateFrom"  styleClass="input calInstDate" style="width:70px" value="#{StaffLoanPerquisits.fromDate}" > 
                                <f:convertDateTime pattern="dd/MM/yyyy" /> 
                            </h:inputText>
                            <font color="purple" >DD/MM/YYYY</font>
                        </h:panelGroup>
                        <h:outputLabel id="dateTo" styleClass="label"  value="To Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDateTo"   styleClass="input calInstDate" style="width:70px" value="#{StaffLoanPerquisits.toDate}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <font color="purple">DD/MM/YYYY</font>
                        </h:panelGroup>
                    </h:panelGrid--%>     

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer"> 
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton  id="btnView" value="View" action="#{StaffLoanPerquisits.viewReport}" reRender="errorGrid,groupPanel2"/>
                            <h:commandButton id="btnDownload" value="PDF Download" action="#{StaffLoanPerquisits.btnPdfAction}"/>
                            <a4j:commandButton  id="btnRefresh"  value="Refresh" action="#{StaffLoanPerquisits.refresh}" reRender="errorGrid,groupPanel2"/>
                            <a4j:commandButton  id="btnExit" value="Exit" action="#{StaffLoanPerquisits.exitBtnAction}" reRender="errorGrid,groupPanel2"/>
                        </h:panelGroup>
                    </h:panelGrid>            
                </h:panelGrid>                    

            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>

