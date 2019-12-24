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
            <title>Revalidation Of Remittance(PO)</title>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{RevalidationOfRemittance.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Revalidation Of Remittance(PO)"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{RevalidationOfRemittance.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="message" styleClass="msg" value="#{RevalidationOfRemittance.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="a7" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblRemittancetype" styleClass="label" value="Remittance Type :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddRemittancetype" tabindex="1"  styleClass="ddlist" size="1" style="width: 100px" value="#{RevalidationOfRemittance.remType}">
                            <f:selectItems value="#{RevalidationOfRemittance.remittanceType}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblInstNo" styleClass="label" value="Inst. No. :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInstNo" maxlength="10" value="#{RevalidationOfRemittance.instNo}" tabindex="2" disabled="" size="12" styleClass="input">
                            <a4j:support action="#{RevalidationOfRemittance.instDetail}" event="onblur" reRender="message,a11,a13,a14,a15,a16,a17,a18,a19,a20,a21" limitToList="true" focus="btnUpdate"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="a11" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblSeqNo" styleClass="label" value="Seq. No. :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtSeqNo"  disabled="true" style="color:blue" styleClass="input" value="#{RevalidationOfRemittance.seqNo}" size="8"/>
                        <h:outputLabel id="lblInstNo1" styleClass="label" value="Inst. No. :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInstNo1"  disabled="true" style="color:blue" styleClass="input" value="#{RevalidationOfRemittance.instNo1}" size="12"/>
                        <h:outputLabel id="lblRemType" styleClass="label" value="Remittance Type :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRemType" disabled="true" size="12" style="color:blue" styleClass="input" value="#{RevalidationOfRemittance.remtType}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="a13" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblTimeLimit" styleClass="label" value="Time Limit(Days) :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtTimeLimit"  disabled="true" size="8" style="color:blue" styleClass="input" value="#{RevalidationOfRemittance.timeLimit}"/>
                        <h:outputLabel id="lblCustname" styleClass="label" value="Customer Name :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtCustName" disabled="true" size="22" style="color:blue" styleClass="input" value="#{RevalidationOfRemittance.custName}"/>
                        <h:outputLabel id="lblPayableAt" styleClass="label" value="Payable At :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtPayableAt"  disabled="true" size="20" style="color:blue" styleClass="input" value="#{RevalidationOfRemittance.payAt}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="a14" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAmount" styleClass="label" value="Amount :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtAmount" disabled="true" size="8" style="color:blue" styleClass="input" value="#{RevalidationOfRemittance.amount}"/>
                        <h:outputLabel id="lblOriginalDt" styleClass="label" value="Original Date :"><font class="required" color="red">*</font></h:outputLabel>
                        <rich:calendar datePattern="dd/MM/yyyy" id="calOriginalDt" value="#{RevalidationOfRemittance.originalDt}" disabled="true" inputSize="8"/>
                        <h:outputLabel id="lblInFavOf" styleClass="label" value="In Favour Of :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInFavOf"  disabled="true" size="22" style="color:blue" styleClass="input" value="#{RevalidationOfRemittance.inFavOf}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="a15" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblNewRemDt" styleClass="label" value="New Remittance Date :"><font class="required" color="red">*</font></h:outputLabel>
                        <rich:calendar datePattern="dd/MM/yyyy" tabindex="3" id="calNewRemDt" value="#{RevalidationOfRemittance.newRemDate}" inputSize="8"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnUpdate" tabindex="4" value="Update" action="#{RevalidationOfRemittance.saveRevalidationInfo}" reRender="a1" focus="ddRemittancetype">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" tabindex="4" value="Refresh" action="#{RevalidationOfRemittance.resetForm}" reRender="a1" focus="ddRemittancetype">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" tabindex="5" value="Exit" action="#{RevalidationOfRemittance.exitFrm}" reRender="message">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>