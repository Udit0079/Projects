<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<style type="text/css">
    .gridrow1 {
        background-color: #edebeb;
        height:20px;
    }
    .gridrow2 {
        background-color: #e8eef7;
        height:20px;
    }
    .row1 {
        background-color: #edebeb;
        height:30px;
    }
    .row2 {
        background-color: #e8eef7;
        height:30px;
    }
    .col1{
        width:15%;
    }
    .col2{
        width:16%;
    }
    .col3{
        width:17%;
    }
    .col4{
        width:18%;
    }

    .col5{
        width:50%;
    }

    .col6{
        width:70%;
    }
    .col7{
        width:25%;
    }
    .col8{
        width:26%;
    }
    .col9{
        width:24%;
    }
    .col{
        width:33%;
    }
    .col10{
        width:40%;
    }
    .col11{
        width:60%;
    }
    .vtop{
        vertical-align:top;
    }
    .dr-pnl-b{
        font-size:14px;
        padding:5px;
        color:#044F93;
        font-weight:bold;
    }

    .rich-calendar-input{
        width:100px;
        height:15px;
        font-size:10px;
        border:1px solid #BED6F8;
    }

    .rich-mpnl-body{
        padding:0px;
    }

    .rich-table-cell{
        padding:3px;
    }
    .input{
        font-size:11px;
        height:15px;
        border:1px solid #BED6F8;
    }

    .textarea{
        font-size:11px;
        height:30px;
        width:150px;
        border:1px solid #BED6F8;
    }

    .output{
        font-size:11px;
    }
    .headerLabel{
        font-size:12px;
    }

    .label{
        font-size:11px;
    }
    .error{
        font-size:12px;
        color:red;
    }
    .msg{
        font-size:12px;
        color:blue;
    }

    .ddlist{
        font-size:11px;
        height:20px;
        border:1px solid #BED6F8;
        background-color: #fff;
    }

    .required{
        color:red;
        font-weight:bold;
        font-size:12px;
    }

    .header{
        background:url(images/header_bg.png) repeat-x;
        height:35px;
        border:3px ridge #BED6F8;
        text-align:center;


    }
    .footer{
        background:url(images/header_bg.png) repeat-x;
        height:35px;
        border:3px ridge #BED6F8;
        text-align:center;
    }

</style>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Account Opening Register</title>
        </head>
        <body>
            <a4j:form id="enrollmentForm"> 
                <h:panelGrid columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8" cellspacing="0" cellpadding="0">

                    <%--<rich:panel styleClass="header">--%>
                        <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;"  styleClass="header">
                            <h:panelGroup layout="block">
                                <h:outputLabel for="todayDate" styleClass="headerLabel" value="Date: "/><h:outputText styleClass="output" id="todayDate" value="#{FinInclusionAcctOpening.todayDate}"/>
                            </h:panelGroup>

                            <h:outputLabel styleClass="headerLabel" value="Customer Enrollment"/>
                            <h:panelGroup layout="block">
                                <h:outputLabel for="userName" styleClass="headerLabel" value="User: "/><h:outputText styleClass="output" id="userName" value="#{FinInclusionAcctOpening.userName}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                   <%-- </rich:panel>--%>
                    <h:panelGrid id="bodyPanel" width="100%" cellspacing="0" cellpadding="0">
                        <h:panelGroup layout="block" id="imgDiv" style="height:200px; overflow:auto;display:none" styleClass="row2">
                            <h:panelGrid id="imgGrid" columns="3" width="100%;" columnClasses="col10,col10,col10">
                                <h:graphicImage id="custImg" value="#{FinInclusionAcctOpening.custImgUri}" style="border:0" />
                                <h:graphicImage id="addImg" value="#{FinInclusionAcctOpening.addProofImgUri}" style="border:0" />
                                <h:graphicImage id="idImg" value="#{FinInclusionAcctOpening.idProofImgUri}" style="border:0" />
                            </h:panelGrid>
                        </h:panelGroup>

                        <h:panelGrid id="custInfo" width="100%" columns="1">
                            <h:panelGrid columns="6" width="100%" styleClass="row2" columnClasses="col1,col1,col4,col4,col3,col3">
                                <h:outputLabel styleClass="label" for="newAcctCode" value="New A/C Type:"><font class="required">*</font></h:outputLabel>
                                <h:selectOneListbox id="newAcctCode" value="#{FinInclusionAcctOpening.newAcctCode}" styleClass="ddlist" style="width:75px" size="1">
                                    <f:selectItems value="#{FinInclusionAcctOpening.acctTypeOption}" />
                                    <a4j:support event="onchange" ajaxSingle="true" reRender="dsDetail" focus="agentCode"/> 
                                </h:selectOneListbox>
                                <h:outputLabel styleClass="label" for="agentCode" value="Agent Code:"><font class="required">*</font></h:outputLabel>
                                <h:selectOneListbox id="agentCode" value="#{FinInclusionAcctOpening.agentCode}" styleClass="ddlist" style="width:120px" size="1">
                                    <f:selectItems value="#{FinInclusionAcctOpening.agcodeOption}" />
                                </h:selectOneListbox>
                                <h:outputLabel styleClass="label" value="Date of Birth:"><font class="required">*</font></h:outputLabel>
                                <rich:calendar id="dob" datePattern="dd/MM/yyyy" value="#{FinInclusionAcctOpening.dob}" disabled="#{FinInclusionAcctOpening.flagDisable}"/>
                            </h:panelGrid>

                            <h:panelGrid columns="6" width="100%" styleClass="row1" columnClasses="col1,col1,col4,col4,col3,col3">
                                <h:outputLabel styleClass="label" for="nameTitle" value="Name:"><font class="required">*</font></h:outputLabel>
                                <h:panelGroup id ="titleName">
                                    <h:selectOneListbox styleClass="ddlist" value="#{FinInclusionAcctOpening.nameTitle}" id="nameTitle" style="width:50px" size="1" disabled="#{FinInclusionAcctOpening.flagDisable}">
                                        <f:selectItems value="#{FinInclusionAcctOpening.nameTitleOption}"/>
                                    </h:selectOneListbox>
                                    <h:inputText id="custName" styleClass="input" style="width:120px;" value="#{FinInclusionAcctOpening.customerName}" disabled="#{FinInclusionAcctOpening.flagDisable}"/>

                                    <h:outputText styleClass="output"/>
                                </h:panelGroup>
                                <h:outputLabel styleClass="label" value="Father/Husband Name:" for="fatherHusbandName"/>
                                <h:inputText id="fatherHusbandName" styleClass="input" style="width:120px;" value="#{FinInclusionAcctOpening.fatherHusbandName}" onkeyup="this.value = this.value.toUpperCase();"/>
                                <h:outputLabel styleClass="label" value="" id="fatherHusbandName1"/>
                                <h:outputLabel styleClass="label" value="" id="fatherHusbandName2"/>
                            </h:panelGrid>


                            <h:panelGrid columns="6" width="100%" styleClass="row2" columnClasses="col1,col1,col4,col4,col3,col3">
                                <h:outputLabel styleClass="label" value="Permanent Address:"><font class="required">*</font></h:outputLabel>
                                <h:inputTextarea id="corresAdd" styleClass="textarea" value="#{FinInclusionAcctOpening.permtAddress}" disabled="#{FinInclusionAcctOpening.flagDisable}"/>

                                <h:outputLabel styleClass="label" value="Correspondence Address:"><font class="required">*</font></h:outputLabel>
                                <h:inputTextarea id="permAdd" styleClass="textarea" value="#{FinInclusionAcctOpening.corresAddress}" disabled="#{FinInclusionAcctOpening.flagDisable}"/>

                                <h:outputLabel styleClass="label" value="Pan/Gir Number:"/>
                                <h:inputText styleClass="input" style="width:120px" value="#{FinInclusionAcctOpening.panNo}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();"/>
                            </h:panelGrid>
                        </h:panelGrid>

                        <h:panelGrid columns="2" width="100%" columnClasses="col10,col11" cellpadding="0" cellspacing="0">
                            <h:panelGrid columns="1" width="100%">
                                <h:panelGrid columns="2" width="100%" styleClass="row1" columnClasses="col10,col11">
                                    <h:outputLabel styleClass="label" value="Document:"/>
                                    <h:selectOneListbox id="document" styleClass="ddlist"  value="#{FinInclusionAcctOpening.document}" style="width:120px" size="1">
                                        <f:selectItems value="#{FinInclusionAcctOpening.documentListOption}" />
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columns="2" width="100%" styleClass="row2" columnClasses="col10,col11">
                                    <h:outputLabel styleClass="label" value="Document Details:"/>
                                    <h:inputTextarea id="docDetail" styleClass="textarea"  value="#{FinInclusionAcctOpening.documentDetails}" onkeyup="this.value = this.value.toUpperCase();"/>
                                </h:panelGrid>
                                <h:panelGrid columns="1" id="dsDetail" width="100%">
                                    <h:panelGrid columns="2" width="100%" styleClass="row1" columnClasses="col10,col11" cellpadding="0" cellspacing="0">
                                        <h:outputLabel id="lblInstAmt" styleClass="label" value="Inst. Amount:" rendered="#{FinInclusionAcctOpening.newAcctCode == 'DS'}">
                                            <font class="required">*</font>
                                        </h:outputLabel>
                                        <h:inputText id="instAmt" styleClass="input" style="width:120px;" value="#{FinInclusionAcctOpening.instAmount}" rendered="#{FinInclusionAcctOpening.newAcctCode == 'DS'}"/>
                                    </h:panelGrid>

                                    <h:panelGrid columns="2" width="100%" styleClass="row2" columnClasses="col10,col11" style="height:25px;">
                                        <h:outputLabel id="lblPd" styleClass="label" value="Peroid(Months):" rendered="#{FinInclusionAcctOpening.newAcctCode == 'DS'}">
                                            <font class="required">*</font>
                                        </h:outputLabel>
                                        <h:inputText id="pdInMonths" styleClass="input" style="width:120px;"  value="#{FinInclusionAcctOpening.months}" rendered="#{FinInclusionAcctOpening.newAcctCode == 'DS'}">
                                            <a4j:support actionListener="#{FinInclusionAcctOpening.getRateOfInterest}" event="onblur"
                                                         reRender="roi,message"/>
                                        </h:inputText>
                                    </h:panelGrid>

                                    <h:panelGrid columns="2" width="100%" styleClass="row1" columnClasses="col10,col11" style="height:25px;">
                                        <h:outputLabel id="lblRoi" styleClass="label" value="ROI:" rendered="#{FinInclusionAcctOpening.newAcctCode == 'DS'}">
                                            <font class="required">*</font>
                                        </h:outputLabel>
                                        <h:inputText id="roi" styleClass="input" style="width:120px;" value="#{FinInclusionAcctOpening.roi}" rendered="#{FinInclusionAcctOpening.newAcctCode == 'DS'}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="1" width="100%" styleClass="row2" style="height:25px;">
                                        <h:outputText id="message" styleClass="msg" value="#{FinInclusionAcctOpening.message}"/>
                                    </h:panelGrid>
                                </h:panelGrid>

                            </h:panelGrid>

                            <h:panelGrid id="dataGrid" columns="1" width="100%" columnClasses="vtop" style="height:200px;background-color:#e8eef7">
                                <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                    <rich:menuItem value="Select Record" ajaxSingle="true" actionListener="#{FinInclusionAcctOpening.fetchCurrentRow}">
                                        <a4j:actionparam name="custId" value="{custId}" />
                                        <a4j:actionparam name="row" value="{currentRow}" />
                                    </rich:menuItem>
                                </rich:contextMenu>
                                <a4j:region>
                                    <rich:dataTable value="#{FinInclusionAcctOpening.dataList}" var="dataItem"
                                                    rowClasses="gridrow1, gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                    onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;
                                                    #{rich:component('menu')}.show(event,{custId:'#{dataItem.custId}', currentRow:'#{row}'});return false;">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="5"><h:outputText value="Enrolled Customers"/></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Customer Id"/></rich:column>
                                                <rich:column><h:outputText value="Customer Name"/></rich:column>
                                                <rich:column><h:outputText value="Date of Birth"/></rich:column>
                                                <rich:column><h:outputText value="Location"/></rich:column>
                                                <rich:column><h:outputText value="Action"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column style="width:100px"><h:outputText value="#{dataItem.custId}"/></rich:column>
                                        <rich:column>
                                            <h:outputText value="#{dataItem.name}"/>
                                            <a4j:support actionListener="#{FinInclusionAcctOpening.changeValue}" ajaxSingle="true" event="ondblclick" reRender="dataGrid">
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{FinInclusionAcctOpening.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{FinInclusionAcctOpening.currentRow}"/>
                                            </a4j:support>
                                        </rich:column>
                                        <rich:column style="width:80px"><h:outputText value="#{dataItem.dob}"/></rich:column>
                                        <rich:column>
                                            <h:outputText value="#{dataItem.location}"/>
                                            <a4j:support actionListener="#{FinInclusionAcctOpening.changeLocationValue}" ajaxSingle="true" event="ondblclick" reRender="dataGrid">
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{FinInclusionAcctOpening.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{FinInclusionAcctOpening.currentRow}"/>
                                            </a4j:support>
                                        </rich:column>
                                        <rich:column style="text-align:center;width:40px"> 
                                            <a4j:commandLink ajaxSingle="true" id="selectlink" actionListener="#{FinInclusionAcctOpening.setRowValues}"
                                                             reRender="bodyPanel,btnSave" focus="custType"
                                                             oncomplete="#{rich:element('imgDiv')}.style.display='block'">
                                                <h:graphicImage value="/resources/images/edit.gif" style="height:14px;width:14px;border:0;"/>
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{FinInclusionAcctOpening.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{FinInclusionAcctOpening.currentRow}"/>
                                            </a4j:commandLink>
                                            <rich:toolTip for="selectlink" value="Select this row."/>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList" maxPages="20"/>
                                </a4j:region>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <rich:panel styleClass="footer">
                        <a4j:commandButton action="#{FinInclusionAcctOpening.saveAccDetail}" id="btnSave" value="Save" reRender="bodyPanel" disabled="#{FinInclusionAcctOpening.saveDisable}" >
                            <rich:toolTip for="btnSave" value="Click To Save Account Details"></rich:toolTip>
                        </a4j:commandButton>
                        <a4j:commandButton id="btnCancel" value="Cancel" action="#{FinInclusionAcctOpening.cancelAccDetail}" reRender="bodyPanel">
                            <rich:toolTip for="btnCancel" value="Click To Cancel Account Detail"></rich:toolTip>
                        </a4j:commandButton>
                        <a4j:commandButton id="btnExit" value="Exit" action="#{FinInclusionAcctOpening.close}"  reRender="bodyPanel">
                        </a4j:commandButton>
                    </rich:panel>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:outputText value="Processing" />
                </f:facet>
                <h:outputText value="Wait Please..." />
            </rich:modalPanel>
            <rich:messages></rich:messages>
        </body>
    </html>
</f:view>