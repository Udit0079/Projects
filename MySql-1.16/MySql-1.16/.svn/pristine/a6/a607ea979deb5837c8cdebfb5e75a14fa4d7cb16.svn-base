<%-- 
    Document   : rdMaster
    Created on : 11 Jul, 2017, 11:14:57 AM
    Author     : root
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
            <title>Rd Master</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calFromDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="kycCateg">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{rdMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Rd Master"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{rdMaster.userName}"/>
                           </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{rdMaster.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gpBranchCode" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%"  styleClass="row2" width="100%">
                        <h:outputText value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchCode" size="1" value="#{rdMaster.branchCode}" styleClass="ddlist" style="width:100px;">
                            <f:selectItems value="#{rdMaster.branchCodeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblacType" styleClass="label"  value="Account Type"/>
                        <h:selectOneListbox id="ddacType" styleClass="ddlist" size="1" style="width: 80px" value="#{rdMaster.acType}" >
                            <f:selectItems value="#{rdMaster.acTypeList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputText id="stxtbranchCode" value="Account No" styleClass="label"/>
                            <h:inputText id="toDate" styleClass="input toDate" style="width:100px;"  value="#{rdMaster.accountNo}" >
                                <a4j:support action="#{rdMaster.getAccountDetails}" event="onblur" reRender="newpasswwordretype4,panelNameFatherName,panelDateOfBirthValue,errPanel" focus="newpasswwordretype4"/>
                            </h:inputText>    
                        <h:outputLabel id="retypenewpassword4" styleClass="label" value=" Receipt No" />
                        <h:inputText id="newpasswwordretype4"  disabled="#{rdMaster.disableReceiptNo}" styleClass="input"  value="#{rdMaster.receiptNO}" maxlength="14" style="width:80px" >
                            <a4j:support action="#{rdMaster.getReceiptDetails}" event="onblur" reRender="panelNameFatherName,panelDateOfBirthValue,errPanel"/>
                        </h:inputText>   
                    </h:panelGrid>
                     <h:panelGrid id="panelNameFatherName" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="sellarl9" styleClass="label" value="Old/New Account No"/>
                        <h:outputText id="sellarName"  styleClass="output" value="#{rdMaster.oldOrNewAccountNo}" />
                        <h:outputLabel id="manDtl9" styleClass="label" value="Customer Name"/>
                        <h:outputText id="stxtMandt"  styleClass="output" value="#{rdMaster.customerName}" />
                    </h:panelGrid>
                    <h:panelGrid id="panelDateOfBirthValue" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="sellarBookl9" styleClass="label" value="Father Name"/>
                        <h:outputText id="bookValue"  styleClass="output" value="#{rdMaster.fatherName}" />
                        <h:outputLabel id="dateofBirth9" styleClass="label" value="Date Of Birth"/>
                        <h:outputText id="valueDateOfBirth"  styleClass="output" value="#{rdMaster.dob}" />
                        </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                           <a4j:commandButton action="#{rdMaster.btnRefreshAction}" id="btnReset" value="Refresh" reRender="mainPanel,message"/>
                            <a4j:commandButton action="#{rdMaster.btnExitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msgPanel" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputText id="stxtInstruction1" styleClass="output" value="6 Digit For Old And 12 Digit For New " style="color:blue;"/>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>

