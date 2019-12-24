<%-- 
    Document   : categoryform
    Created on : 25 Aug, 2011, 12:47:47 PM
    Author     : Zeeshan Waris
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Category Form</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="CategoryForm"/>
            <a4j:form id="CategoryForm">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CategoryForm.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Category Form"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CategoryForm.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="lblMsg" styleClass="error" value="#{CategoryForm.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col1,col1,col2,col1,col2" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFolioNo" styleClass="label" value="Folio No."><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup layout="block">
                                <h:inputText id="txtShareholder" styleClass="input" maxlength="12" value="#{CategoryForm.folioNoShow}" style="width:90px;" onkeyup="this.value = this.value.toUpperCase();">
                                    <a4j:support action="#{CategoryForm.onblurFolioDetails}" event="onblur"
                                                   reRender="stxtName,stxtFatherName,Row6,btnSave,lblMsg,stxtFolioShow" focus="txtArea"/>
                                </h:inputText>
                                <h:outputText id="stxtFolioShow" styleClass="output" value="#{CategoryForm.folioNo}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblName" styleClass="label" value="Name"/>
                            <h:outputText id="stxtName" styleClass="output" value="#{CategoryForm.name}"/>
                            <h:outputLabel id="lblFatherName" styleClass="label" value="Father Name"/>
                            <h:outputText id="stxtFatherName" styleClass="output" value="#{CategoryForm.fatherName}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col9,col1,col2" columns="4" id="Row6" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblAddress" styleClass="label" value="Address"/>
                            <h:outputText id="stxtAddress" styleClass="output" value="#{CategoryForm.address}"/>
                            <h:outputLabel id="lblAreaDet" styleClass="label" value="Category"/>
                            <h:outputText id="stxtAreaDet" styleClass="output" value="#{CategoryForm.stxtArea}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col5,col1,col1" columns="4" id="Row7" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblcategory" styleClass="label" value="Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddcategory" styleClass="ddlist" size="1" style="width:125px"  value="#{CategoryForm.category}" >
                                <f:selectItems   value="#{CategoryForm.categoryType}"/>
                            </h:selectOneListbox>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>    
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="#{CategoryForm.mode}"  action="#{CategoryForm.saveBtnAction}" reRender="mainPanel,ddcategory,lblMsg" focus="btnExit"/>
                            <a4j:commandButton  id="btnDelete"  value="Delete" action="#{CategoryForm.deleteBtnAction}"  reRender="mainPanel" focus="txtShareholder" />
                            <a4j:commandButton  id="btnrefresh"  value="Refresh" action="#{CategoryForm.btnRefresh}"  reRender="mainPanel" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{CategoryForm.btnExit}" reRender="leftPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>