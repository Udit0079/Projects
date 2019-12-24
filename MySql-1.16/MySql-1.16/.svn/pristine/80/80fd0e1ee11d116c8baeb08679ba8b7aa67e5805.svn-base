<%-- 
    Document   : RdEnquiry
    Created on : Aug 18, 2010, 6:48:12 PM
    Author     : Admin
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
            <title>Recurring Deposit Enquiry</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{RdEnquiry.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Recurring Deposit Enquiry"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{RdEnquiry.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError" value="#{RdEnquiry.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" columnClasses="col9" id="gridpanel1"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblamount" styleClass="headerLabel"  value="Amount" style="padding-left:250px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtamount"  size="15" styleClass="input" value="#{RdEnquiry.amount}" maxlength="10" >
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columns="2" columnClasses="col9" id="gridpanel2" style="height:30px;" styleClass="row2" width="100%" >
                        <h:outputLabel id="lblperiod" styleClass="headerLabel"  value="Period(in month)" style="padding-left:250px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtperiod"   size="15" styleClass="input" value="#{RdEnquiry.period}" maxlength="4" >
                            <a4j:support reRender="stxtError,txtamount,txtperiod,txtpercentage,stxttamt,btncal" event="onblur" action="#{RdEnquiry.getInputData}" focus="txtpercentage"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columns="2" columnClasses="col9" id="gridpanel3" style="height:30px;"  styleClass="row1" width="100%"  >
                        <h:outputLabel id="lblpercentage" styleClass="headerLabel"  value="ROI(%)" style="padding-left:250px;"><font class="required" color="red">*</font></h:outputLabel>
                        <%--<h:outputText id="txtpercentage" styleClass="output" value="#{RdEnquiry.percentage}"/>--%>
                        <h:inputText id="txtpercentage"  size="15" styleClass="input" value="#{RdEnquiry.percentage}" maxlength="5" >
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columns="1"  id="grdpane4" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxttamt" styleClass="output" value="#{RdEnquiry.atotAmt}" style="color:purple"/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btncal" value="Calculate" reRender="stxtError,txtamount,txtperiod,txtpercentage,stxttamt,btncal" action="#{RdEnquiry.calbtnData}" focus="refresh">
                            </a4j:commandButton>
                            <a4j:commandButton  id="refresh" value="Refresh"  action="#{RdEnquiry.reSet}" reRender="stxtError,txtamount,txtperiod,txtpercentage,stxttamt,btncal" focus="txtamount">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{RdEnquiry.exitFrm}" reRender="stxtError,txtamount,txtperiod,txtpercentage,stxttamt,btncal">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
