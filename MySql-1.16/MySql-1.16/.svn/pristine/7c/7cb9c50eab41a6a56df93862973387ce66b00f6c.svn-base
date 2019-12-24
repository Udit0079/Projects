<%-- 
    Document   : EftCorrBranchDetail
    Created on : june 01, 2011, 10:46:43 AM
    Author     : jitendra Chaudhary
--%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Correspondent Branch Detail</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="panelGrid1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{EftCorrBranchDetail.currentDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Correspondent Branch Detail"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{EftCorrBranchDetail.loggedInUser}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{EftCorrBranchDetail.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid3" columns="4"  columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row1" width="100%">
                            <h:outputLabel id="branchcode" styleClass="label" value="Branch Code" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="branchcodetext" styleClass="input"  value="#{EftCorrBranchDetail.branchCode}" maxlength="6" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support reRender="bankcodetext,corrAcNoinput,corrAcNolabel,branchcodetext,btnUpdate,btnSave,stxtMsg,branchNameText,shortNameText,
                                             addressText,tradeFinanceFlagText,regionCodeText,zoneCodeText,ifsCodeText"  action="#{EftCorrBranchDetail.onBlurBranchCode}" event="onblur" focus="bankcodetext"/>
                            </h:inputText>
                            <h:outputLabel id="bankcode" styleClass="label" value="Bank Code" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="bankcodetext" styleClass="input" value="#{EftCorrBranchDetail.bankCode}"  maxlength="6" onkeyup="this.value=this.value.toUpperCase();">
                            
                            </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid6" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row2" width="100%">
                            <h:outputLabel id="branchname" styleClass="label" value="Branch Name"> <font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText   id="branchNameText" styleClass="input"  value="#{EftCorrBranchDetail.branchName}" maxlength="50" onkeyup="this.value=this.value.toUpperCase();">
                            </h:inputText>
                            <h:outputLabel id="shortName" styleClass="label" value="Short Name"/>
                            <h:inputText  id="shortNameText" styleClass="input"  value="#{EftCorrBranchDetail.shortName}" maxlength="20" onkeyup="this.value=this.value.toUpperCase();">
                            </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid9" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row1" width="100%">
                            <h:outputLabel id="address" styleClass="label" value="Address"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="addressText" styleClass="input"  value="#{EftCorrBranchDetail.address}" maxlength="60" onkeyup="this.value=this.value.toUpperCase();"/>
                            <h:outputLabel id="tradeFinanceFlag" styleClass="label" value="Trade Finance Flag"/>
                            <h:inputText  id="tradeFinanceFlagText" styleClass="input"  value="#{EftCorrBranchDetail.tradeFinanceFlag}"  maxlength="1" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid12" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px" styleClass="row2" width="100%">
                            <h:outputLabel id="regionCode" styleClass="label" value="Region Code"/>
                            <h:inputText id="regionCodeText" styleClass="input"  value="#{EftCorrBranchDetail.regionCode}" maxlength="6" onkeyup="this.value=this.value.toUpperCase();"/>
                            <h:outputLabel id="zoneCode" styleClass="label" value="Zone Code"/>
                            <h:inputText id="zoneCodeText" styleClass="input"  value="#{EftCorrBranchDetail.zoneCode}"  maxlength="3" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid14" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px" styleClass="row1" width="100%">
                            <h:outputLabel id="ifsCode" styleClass="label" value="IFS Code"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="ifsCodeText" styleClass="input"  value="#{EftCorrBranchDetail.ifsCode}"  maxlength="11" onkeyup="this.value=this.value.toUpperCase();"/>
                            <h:outputLabel id="corrAcNolabel" styleClass="label" value="Corr. A/c No"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="corrAcNoinput" styleClass="input"  value="#{EftCorrBranchDetail.corrAccNo}"  maxlength="16" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">

                            <a4j:commandButton disabled="#{EftCorrBranchDetail.add}" id="btnSave" value="Save"  action="#{EftCorrBranchDetail.saveButton}"  reRender="bankcodetext,corrAcNoinput,corrAcNolabel,btnUpdate,btnSave,stxtMsg,branchcodetext,
                                               branchNameText,shortNameText,addressText,tradeFinanceFlagText,regionCodeText,zoneCodeText,ifsCodeText">
                            </a4j:commandButton>
                            <a4j:commandButton disabled="#{EftCorrBranchDetail.update}" id="btnUpdate" value="Update" action="#{EftCorrBranchDetail.updateButton}" reRender="bankcodetext,corrAcNoinput,corrAcNolabel,btnUpdate,btnSave,stxtMsg,branchcodetext,
                                               branchNameText,shortNameText,addressText,tradeFinanceFlagText,regionCodeText,zoneCodeText,ifsCodeText">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{EftCorrBranchDetail.refresh}" reRender="bankcodetext,corrAcNoinput,corrAcNolabel,btnUpdate,btnSave,stxtMsg,branchcodetext,
                                               branchNameText,shortNameText,addressText,tradeFinanceFlagText,regionCodeText,zoneCodeText,ifsCodeText" oncomplete="{#{rich:element('branchcodetext')}.focus();}">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{EftCorrBranchDetail.exitButton}" reRender="bankcodetext,corrAcNoinput,corrAcNolabel,btnUpdate,btnSave,stxtMsg,branchcodetext,
                                               branchNameText,shortNameText,addressText,tradeFinanceFlagText,regionCodeText,zoneCodeText,ifsCodeText">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>

            </a4j:form>
        </body>
    </html>
</f:view>
