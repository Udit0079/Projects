<%-- 
    Document   : memberRefMapping
    Created on : 27 Mar, 2018, 1:02:33 PM
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
            <title>Member Reference Mapping</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".toDate").mask("39/19/9999");
                }
            </script>   
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{MemberRefMapping.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Member Reference Mapping"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{MemberRefMapping.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="lblMsg" styleClass="error" value="#{MemberRefMapping.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col1,col1,col2,col1,col2" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function" style="display:"/>
                            <h:selectOneListbox id="ddFunction" styleClass="ddlist"  size="1" style="width:120px;" value="#{MemberRefMapping.function}" >
                                <f:selectItems value="#{MemberRefMapping.functionList}"/>
                                <a4j:support event="onblur" action="#{MemberRefMapping.referedByDetails}" reRender="lblMsg,ddSector,txtShareholder,stxtfolioShow,Row8,btnSave,btnPrint,Row12,btnPrintLabel,
                                             txtmemreferd,lblmemreferd,lblFrDt,frDate" oncomplete="setMask();" />
                            </h:selectOneListbox>
                            <h:panelGroup>
                                <h:outputLabel id="lblmemreferd" styleClass="label"  style="display:#{MemberRefMapping.memFieldShow}" value="Membership No. Refered:">
                                    <font class="required" style="color:red;">*</font>
                                </h:outputLabel>
                                <h:outputLabel id="lblFrDt" styleClass="label" value="From Date" style="display:#{MemberRefMapping.datefieldShow}"/>
                            </h:panelGroup>
                            <h:panelGroup>
                                <h:inputText id="txtmemreferd" styleClass="input" maxlength="6" value="#{MemberRefMapping.membershiprefNo}" style="width:90px;display:#{MemberRefMapping.memFieldShow}">
                                    <a4j:support event="onblur" action="#{MemberRefMapping.onblurMemReferedOperation}"
                                                 reRender="lblMsg,ddSector,ddSector1" oncomplete="setMask();"/>
                                </h:inputText>
                                <h:inputText id="frDate" size="10" styleClass="input toDate"  value="#{MemberRefMapping.dt}" 
                                             style="display:#{MemberRefMapping.datefieldShow}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col1,col1,col2" columns="4" id="Row12" style="width:100%;text-align:left;display:#{MemberRefMapping.panel3Display}" styleClass="row2">
                            <h:outputLabel id="lblReportIn" styleClass="label" value="Report In:"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox value="#{MemberRefMapping.reportIn}" id="ddReportIn" styleClass="ddlist"  size="1" style="width:120px">
                                <f:selectItems  value="#{MemberRefMapping.reportInList}"/>
                                <a4j:support event="onblur" />  
                            </h:selectOneListbox>
                            <h:outputLabel id="lblSearch" styleClass="label" value="Search By:"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox value="#{MemberRefMapping.searchBy}" id="ddSearchBy" styleClass="ddlist"  size="1" style="width:120px">
                                <f:selectItems  value="#{MemberRefMapping.searchByList}"/>
                                <a4j:support event="onblur" action="#{MemberRefMapping.searchByAction}" reRender="Row13,Row6,lblMsg" oncomplete="setMask();"/>  
                            </h:selectOneListbox> 
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col1,col1,col2" columns="4" id="Row6" style="width:100%;text-align:left;display:#{MemberRefMapping.panel5Display}" styleClass="row2">
                            <h:outputLabel id="lblsector" styleClass="label" value="Designation/Role"><font class="required" style="color:red;" title="Sector">*</font></h:outputLabel>
                            <h:selectOneListbox value="#{MemberRefMapping.designation}"id="ddSector" styleClass="ddlist"  size="1" style="width :120px ">
                                <f:selectItems  value="#{MemberRefMapping.designationOption}"/>
                                <a4j:support event="onblur" action="#{MemberRefMapping.personNameVisible}" 
                                             reRender="lblMsg,ddSector1" focus="ddSector1" oncomplete="setMask();"/>                  
                            </h:selectOneListbox>
                            <h:outputLabel id="lblsector1" styleClass="label" value="Designated Person:"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox value="#{MemberRefMapping.referBy}" id="ddSector1" styleClass="ddlist"  size="1" style="width:200px">
                                <f:selectItems  value="#{MemberRefMapping.referByList}"/>
                                <a4j:support event="onblur" />  
                            </h:selectOneListbox> 
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col1,col1,col2" columns="4" id="Row13" style="width:100%;text-align:left;display:#{MemberRefMapping.panel4Display}" styleClass="row2">
                            <h:panelGroup>
                                <h:outputLabel id="lblmemshipNo" styleClass="label" value="Membership No.:"><font class="required" style="color:red;">*</font></h:outputLabel> 
                                <h:inputText id="txtMemShipNo" styleClass="input" maxlength="6" value="#{MemberRefMapping.membershipNo}" style="width:90px;">
                                    <a4j:support event="onblur" action="#{MemberRefMapping.onblurMembershipNOOperation}"
                                                 reRender="lblMsg,txtnewMamShipNo,tableGrid,taskList" oncomplete="setMask();"/>  
                                </h:inputText>
                                <h:outputText id="txtnewMamShipNo" styleClass="output" value="#{MemberRefMapping.newMemShipNo}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col1,col1,col2" columns="4" id="Row8" style="width:100%;text-align:left;display:#{MemberRefMapping.folioFieldShow}" styleClass="row1">
                            <h:outputLabel id="lblFolioNo" styleClass="label" value="Membership No. Refered:">
                                <font class="required" style="color:red;">*</font>
                            </h:outputLabel>
                            <h:panelGroup layout="block">
                                <h:inputText id="txtShareholder" styleClass="input" maxlength="12" value="#{MemberRefMapping.folioNoShow}" style="width:90px;display:#{MemberRefMapping.folioFieldShow}">
                                    <a4j:support event="onblur" action="#{MemberRefMapping.onblurFolioHolderDetails}"
                                                 reRender="lblMsg,stxtfolioShow,stxtName" oncomplete="setMask();"/>
                                </h:inputText>
                                <h:outputText id="stxtfolioShow" styleClass="output" value="#{MemberRefMapping.folioNo}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblName" styleClass="label" value="Name"/>
                            <h:outputText id="stxtName" styleClass="output" value="#{MemberRefMapping.name}"/>
                        </h:panelGrid>
                        <a4j:region id="btnActionGrid">
                            <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="BtnPanel">
                                    <h:commandButton id="btnPrintLabel" value="Print Label" action="#{MemberRefMapping.printLabel}" disabled="#{MemberRefMapping.disableprintLabel}" styleClass="color:#044F93;text-decoration:none;">
                                    </h:commandButton>
                                    <a4j:commandButton id="btnSave" value="Save"  action="#{MemberRefMapping.saveBtnAction}" disabled="#{MemberRefMapping.disablesavebtn}" reRender="lblMsg,mainPanel,leftPanel" oncomplete="setMask();"/>
                                    <%--<a4j:commandButton id="btnPrint" value="Print Report" action="#{MemberRefMapping.printReportAction}" disabled="#{MemberRefMapping.disableprintbtn}" reRender="lblMsg,mainPanel"/>--%>
                                    <h:commandButton id="btnPrint" value="PDF Download" action="#{MemberRefMapping.printPDF}" disabled="#{MemberRefMapping.disableprintbtn}" styleClass="color:#044F93;text-decoration:none;">

                                    </h:commandButton>
                                    <a4j:commandButton  id="btnrefresh"  value="Refresh" action="#{MemberRefMapping.refresh}"  reRender="mainPanel,leftPanel" oncomplete="setMask();"/>
                                    <a4j:commandButton id="btnExit" value="Exit" action="#{MemberRefMapping.exitForm}" reRender="mainPanel,leftPanel" oncomplete="setMask();"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </a4j:region>
                        <h:panelGrid columnClasses="vtop" columns="1" id="tableGrid" style="height:auto;display:#{MemberRefMapping.gridDisplay}" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{MemberRefMapping.gridDetail}" var="dataItem"
                                                rowClasses="row1,row2" id="taskList" rows="10" columnsWidth="100" 
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="10"><h:outputText value="Member Details"/></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Membership No."/></rich:column>
                                            <rich:column><h:outputText value="Member Name"/></rich:column>                                                
                                            <rich:column><h:outputText value="Designation"/></rich:column>
                                            <rich:column><h:outputText value="Refer By"/></rich:column>
                                            <rich:column><h:outputText value="Enter Date"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.membershipNo}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.personName}"/></rich:column>                                        
                                    <rich:column><h:outputText value="#{dataItem.designation}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.referBy}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.date}"/></rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20"/>
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>	
                </h:panelGrid> 
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnActionGrid"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>
