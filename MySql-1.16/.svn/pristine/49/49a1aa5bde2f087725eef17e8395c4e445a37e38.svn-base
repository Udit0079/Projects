<%-- 
    Document   : AtmCardMaster
    Created on : Sep 1, 2014, 6:53:48 PM
    Author     : sipl
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>ATM Kit/Card Mapping</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calIssDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="formAtm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ATMKitCardMapping.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="ATM Kit/Card Mapping"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ATMKitCardMapping.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                        <h:outputText id="message" styleClass="msg" value="#{ATMKitCardMapping.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="normalPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function" style="padding-left:70px;"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:80px;" value="#{ATMKitCardMapping.function}">
                                    <f:selectItems value="#{ATMKitCardMapping.functionList}"/>
                                    <a4j:support action="#{ATMKitCardMapping.chgFunction}" event="onblur" oncomplete="setMask();" focus="txtAcNo"
                                                 reRender="message,txtAcNo,txtCardNo,calIssDate1,txtMinLmt,ddStFlg,table,btnSave,newAcNo,txtCustName,txtKitNo"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblAcNo" styleClass="label" value="Account No"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:inputText id="txtAcNo" styleClass="input" maxlength="12" style="width:90px;" value="#{ATMKitCardMapping.acNo}" disabled="#{ATMKitCardMapping.acFlag}">
                                    <a4j:support action="#{ATMKitCardMapping.chenageOperation}" event="onblur" oncomplete="setMask();"
                                                 reRender="message,txtAcNo,txtCardNo,calIssDate1,txtMinLmt,ddStFlg,table,btnSave,newAcNo,txtCustName"/>
                                </h:inputText>
                                <h:outputText id="newAcNo" styleClass="output" value="#{ATMKitCardMapping.acNo}" style="color:blue"/>
                                </h:panelGroup>                  
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name" style="padding-left:70px;"/>
                                <h:outputText id="txtCustName" styleClass="output" value="#{ATMKitCardMapping.custName}" />
                                <h:outputLabel id="lblEmbossedName" styleClass="label" value="Embossed Name "/>
                                <h:inputText id="txtEmbossedName" styleClass="input" value="#{ATMKitCardMapping.embossedName}" maxlength="18" style="width:150px;" disabled="#{ATMKitCardMapping.embossedNameFlg}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblKitNo" styleClass="label" value="Kit No" style="padding-left:70px;"/>
                                <h:inputText id="txtKitNo" styleClass="input" value="#{ATMKitCardMapping.kitNo}" maxlength="10" disabled="#{ATMKitCardMapping.kitFlag}" style="width:90px;"/>
                                <h:outputLabel id="lblKitIssueDt" styleClass="label" value="Kit Issue. Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="calKitIssDate1" styleClass="input calIssDate" maxlength="10" style="width:80px;"  value="#{ATMKitCardMapping.kitIssueDt}" disabled="#{ATMKitCardMapping.kitDtFlag}">
                                    <f:convertDateTime pattern="dd/MM/yyyy"/>
                                </h:inputText>
                            </h:panelGrid>
                                <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row3" style="width:100%;text-align:left;display:#{ATMKitCardMapping.cardDisplay}" styleClass="row1">
                                <h:outputLabel id="lblCardNo" styleClass="label" value="Card No" style="padding-left:70px;"/>
                                <h:inputText id="txtCardNo" styleClass="input" value="#{ATMKitCardMapping.cardNo}" maxlength="20" disabled="#{ATMKitCardMapping.cardFlag}"/>
                                <h:outputLabel id="lblIssueDt" styleClass="label" value="Card Issue. Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="calIssDate1" styleClass="input calIssDate" maxlength="10" style="width:80px;"  value="#{ATMKitCardMapping.issueDt}" disabled="#{ATMKitCardMapping.dtFlag}">
                                    <f:convertDateTime pattern="dd/MM/yyyy"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblMinLmt" styleClass="label" value="Min Limiit" style="padding-left:70px;"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtMinLmt" styleClass="input" style="width:80px;" value="#{ATMKitCardMapping.minLmt}" disabled="#{ATMKitCardMapping.lmtFlag}"/>
                                <h:outputLabel id="lblStFlg" styleClass="label" value="Status"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddStFlg" styleClass="ddlist" size="1" style="width:80px;" value="#{ATMKitCardMapping.stFlg}" disabled="#{ATMKitCardMapping.statusFlag}">
                                    <f:selectItems value="#{ATMKitCardMapping.statusList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{ATMKitCardMapping.gridDetail}" var="dataItem"
                                                    rowClasses="row1,row2" id="taskList" rows="10" columnsWidth="100" 
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="10"><h:outputText value="Card Issue Details"/></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Account No"/></rich:column>
                                                <rich:column><h:outputText value="Kit No"/></rich:column>
                                                <rich:column><h:outputText value="Kit issue Date"/></rich:column>
                                                <rich:column><h:outputText value="Card No"/></rich:column>
                                                <rich:column><h:outputText value="Card Issue Date"/></rich:column>
                                                <rich:column><h:outputText value="Min Limit"/></rich:column>
                                                <rich:column><h:outputText value="Status"/></rich:column>
                                                <rich:column width="20"><h:outputText value="Select"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.acNo}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.kitNo}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.kitIssueDt}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.cardNo}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.issDt}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.minLmt}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.status}"/></rich:column>
                                        <rich:column style="text-align:center;width:40px">
                                            <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{ATMKitCardMapping.select}" oncomplete="setMask();" reRender="normalPanel,message">
                                                <h:graphicImage value="/resources/images/edit.gif" style="border:0"/>
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{ATMKitCardMapping.currentItem}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList" maxPages="20"/>
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnSave" value="#{ATMKitCardMapping.btnValue}" action="#{ATMKitCardMapping.saveMasterDetail}" oncomplete="setMask();" reRender="normalPanel,message"/>
                                    <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ATMKitCardMapping.refreshForm}" oncomplete="setMask();" reRender="normalPanel,message"/>
                                    <a4j:commandButton id="btnExit" value="Exit" action="#{ATMKitCardMapping.exitBtnAction}" reRender="normalPanel,message"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>