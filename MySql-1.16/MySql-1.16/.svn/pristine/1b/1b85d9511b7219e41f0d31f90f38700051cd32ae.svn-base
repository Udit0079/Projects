<%-- 
    Document   : ClearingInfo
    Created on : Aug 30, 2010, 5:43:37 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>Clearing Info</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="form2">
                <h:panelGrid id="mainPanel2" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPane48" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ClearingInfo.todayDate}"  />
                        </h:panelGroup>
                        <h:outputLabel id="OL17" styleClass="headerLabel" value="Clearing Info"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ClearingInfo.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errorMsg1"   width="100%"   style="height:30px;text-align:center" styleClass="row2">
                        <h:outputText id="errMsg1" value="#{ClearingInfo.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel491" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="OL16" styleClass="output" value="Circle Type"><font color="red">*</font></h:outputLabel>
			<h:selectOneListbox id="idcircltype" styleClass="ddlist" size="1" style="width:80px" value="#{ClearingInfo.item}">
                            <f:selectItems value="#{ClearingInfo.list1}"/>
				<a4j:support reRender="errMsg1,btnUpdate,btnSave,stxtuser2,stxtuser3,stxtuser4,stxtuser5,stxtuser14,stxtuser6,stxtuser7,stxtuser8,stxtuser9,stxtuser10,stxtuser11,stxtuser12" 
					event="onblur" action="#{ClearingInfo.loadCircleData}" oncomplete="if(#{ClearingInfo.flag=='true'}){#{rich:element('stxtuser2')}.focus();}"/>
			</h:selectOneListbox>
			<h:outputText id="errMsg51" value=""/>
                        <h:outputText id="errMsg52" value=""/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel501" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel id="OL12" styleClass="output" value="Circle Description "><font color="red">*</font></h:outputLabel>
                        <h:inputText id="stxtuser2" styleClass="input" value="#{ClearingInfo.circleDescription}" onkeyup="this.value = this.value.toUpperCase();" maxlength="30"/>
                        <h:outputLabel id="OL11" styleClass="output" value="Circle MICR "><font color="red">*</font></h:outputLabel>
                        <h:inputText id="stxtuser3" styleClass="input" value="#{ClearingInfo. circleMicr}" maxlength="5"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel521" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="OL10" styleClass="output" value="Bank MICR "><font color="red">*</font></h:outputLabel>
			<h:inputText id="stxtuser4" styleClass="input" value="#{ClearingInfo.bankMicr}" maxlength="5"/>
                        <h:outputLabel id="OL9" styleClass="output" value="Branch MICR "><font color="red">*</font></h:outputLabel>
			<h:inputText id="stxtuser5" styleClass="input" value="#{ClearingInfo.branchMicr}" maxlength="5"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel541" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel id="OL8" styleClass="output" value="GL O/W Clg Head "><font color="red">*</font></h:outputLabel>
                        <h:inputText id="stxtuser14" styleClass="input" value="#{ClearingInfo.owClgHead}" maxlength="6"/>
                        <h:outputLabel id="OL7" styleClass="output" value="GL O/W Clg Return Head "><font color="red">*</font></h:outputLabel>
			<h:inputText id="stxtuser6" styleClass="input" value="#{ClearingInfo.owClgReturnHead}" maxlength="6"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel561" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="OL6" styleClass="output" value="GL O/W Clg Return Charge Head"><font color="red">*</font></h:outputLabel>
			<h:inputText id="stxtuser7" styleClass="input" value="#{ClearingInfo.clgReturnCharge}" maxlength="6"/>
                        <h:outputLabel id="OL5" styleClass="output" value="O/W Return Charges "><font color="red">*</font></h:outputLabel>
			<h:inputText id="stxtuser8" styleClass="input" value="#{ClearingInfo.owReturnCharges}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel581" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel id="OL4" styleClass="output" value="GL I/W Clg Head"><font color="red">*</font></h:outputLabel>
			<h:inputText id="stxtuser9" styleClass="input" value="#{ClearingInfo.iwClgHead}" maxlength="6"/>
                        <h:outputLabel id="OL3" styleClass="output" value="GL I/W Clg Return Head "><font color="red">*</font></h:outputLabel>
			<h:inputText id="stxtuser10" styleClass="input" value="#{ClearingInfo.iwClgReturnHead}" maxlength="6"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel601" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="OL2" styleClass="output" value="GL I/W Clg Return Charge Head"><font color="red">*</font></h:outputLabel>
			<h:inputText id="stxtuser11" styleClass="input" value="#{ClearingInfo.iwClgReturnCharge}" maxlength="6"/>
                        <h:outputLabel id="OL1" styleClass="output" value=" I/W Return Charges "><font color="red">*</font></h:outputLabel>
			<h:inputText id="stxtuser12" styleClass="input" value="#{ClearingInfo.iwReturnCharges}"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel2" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel2">
                            <a4j:commandButton disabled="#{ClearingInfo.disableSave}" action="#{ClearingInfo.save}" id="btnSave" value="Save" 
                            			reRender="btnSave,btnUpdate,errMsg1,stxtuser2,stxtuser3,stxtuser4,stxtuser5,stxtuser14,stxtuser6,stxtuser7,stxtuser8,stxtuser9,stxtuser10,stxtuser11,stxtuser12,stxtuser14,idcircltype"/>
                            <a4j:commandButton disabled="#{ClearingInfo.disableUpdate}" action="#{ClearingInfo.update}" id="btnUpdate" value="Update" 
                            		reRender="btnSave,btnUpdate,errMsg1,stxtuser2,stxtuser3,stxtuser4,stxtuser5,stxtuser14,stxtuser6,stxtuser7,stxtuser8,stxtuser9,stxtuser10,stxtuser11,stxtuser12,stxtuser14,idcircltype"/>
                            <a4j:commandButton id="refresh" value="Refresh" action="#{ClearingInfo.refresh}" 
                            		reRender="btnSave,btnUpdate,errMsg1,btnUpdate,btnSave,stxtuser2,stxtuser3,stxtuser4,stxtuser5,stxtuser14,stxtuser6,stxtuser7,stxtuser8,stxtuser9,stxtuser10,stxtuser11,stxtuser12,stxtuser14,idcircltype"/>
                            <a4j:commandButton id="btnClose"  value="Exit" action="#{ClearingInfo.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>