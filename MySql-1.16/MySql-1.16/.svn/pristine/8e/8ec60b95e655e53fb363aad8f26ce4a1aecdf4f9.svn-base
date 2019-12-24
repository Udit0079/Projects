<%-- 
    Document   : InterestParameter
    Created on : Sep 16, 2010, 2:19:19 PM
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
            <title>Interest parameter</title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="form3">
                <h:panelGrid id="mainPanel5" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="headerpanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{InterestParameter.todayDt}"  />
                        </h:panelGroup>
                        <h:outputLabel id="outputLabel1" styleClass="Label0" value="Interest Parameter"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InterestParameter.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col9" columns="2" id="IpPanel1" style="width:100%;text-align:center;" styleClass="row1">
                        <h:panelGroup>
                            <h:outputLabel id="accountLabel" styleClass="Label0" value="Account Code"/>
                            <h:selectOneListbox id="acCode" styleClass="output" size="1" style="width:80px" value="#{InterestParameter.acTypess}">
                                <f:selectItems  value="#{InterestParameter.acTypeList}"/>
                                <rich:toolTip for="acCode" value="Please Select the type of account"></rich:toolTip>
                            </h:selectOneListbox>
                        </h:panelGroup>

                        <h:panelGroup>
                            <h:outputLabel id="selectMonth" styleClass="Labelnew" value="Select Months"/>
                            <h:selectOneListbox id="monthList2" styleClass="output" size="1" style="width:80px" value="#{InterestParameter.option}">
                                <f:selectItems  value="#{InterestParameter.selectOption}"/>
                                <a4j:support  reRender="errMsg10,monthList,btnSave,checkBox5,checkBox6,checkBox7,checkBox8,checkBox9,checkBox10,inputText1,inputText2,inputText3,inputText4,inputText5,inputText6,inputText7,inputText8,inputText9,inputText10,inputText11" event="onchange" action="#{InterestParameter.InterestType}"/>
                                <rich:toolTip for="monthList2" value="Please Select month gap"></rich:toolTip>
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="IpPanel3" style="width:100%;text-align:center;">
                        <h:panelGrid columns="4" columnClasses="col7" id="IpPanel4" style="width:100%;text-align:center;" styleClass="row2">
                            <h:selectOneListbox  id="monthList" styleClass="output" size="1" style="width:60px" value="#{InterestParameter.month}">
                                <f:selectItems value="#{InterestParameter.monthTypeList}"/>
                                <rich:toolTip for="monthList" value="Please Select the month"></rich:toolTip>
                            </h:selectOneListbox>
                            <h:outputLabel id="gridLabel4" styleClass="Label0" value="To"/>
                            <h:inputText disabled="#{InterestParameter.inputDis1} "   id="inputText1" styleClass="input" value="#{InterestParameter.inputText1}" />
                            <h:panelGroup>
                                <h:selectBooleanCheckbox  value="#{InterestParameter.valuefrchkOne}"  id="checkBox5" disabled="#{InterestParameter.checkBox}">
                                </h:selectBooleanCheckbox>
                                <h:outputLabel id="gridLabel5" styleClass="Label0" value="Int Paid"/>
                            </h:panelGroup>
                        </h:panelGrid>

                        <h:panelGrid  columns="4" columnClasses="col7" id="IpPanel5" style="width:100%;text-align:center;" styleClass="row1">
                            <h:inputText  disabled="#{InterestParameter.inputDis2}" id="inputText2" styleClass="input"  value="#{InterestParameter.inputText2}"/>
                            <h:outputLabel id="gridLabel6" styleClass="Label0" value="To"/>
                            <h:inputText disabled="#{InterestParameter.inputDis3}"  id="inputText3" styleClass="input"  value="#{InterestParameter.inputText3}"/>
                            <h:panelGroup>
                                <h:selectBooleanCheckbox   value="#{InterestParameter.valueFrchkTwo}"    id="checkBox6"  disabled="#{InterestParameter.checkBox2}"/>
                                <h:outputLabel id="gridLabel7" styleClass="Label0" value="Int Paid"/>
                            </h:panelGroup>
                        </h:panelGrid>

                        <h:panelGrid     columns="4" columnClasses="col7" id="IpPanel6" style="width:100%;text-align:center;" styleClass="row2">
                            <h:inputText disabled="#{InterestParameter.inputDis4}"    id="inputText4" styleClass="input"  value="#{InterestParameter.inputText4}"/>
                            <h:outputLabel  id="gridLabel8" styleClass="Label0" value="To"/>
                            <h:inputText disabled="#{InterestParameter.inputDis5}"  id="inputText5" styleClass="input"  value="#{InterestParameter.inputText5}"/>
                            <h:panelGroup>
                                <h:selectBooleanCheckbox  value="#{InterestParameter.valuefrchkThree}" id="checkBox7"   disabled="#{InterestParameter.checkBox3}"/>
                                <h:outputLabel id="gridLabel9" styleClass="Label0" value="Int Paid"/>
                            </h:panelGroup>
                        </h:panelGrid>

                        <h:panelGrid  columns="4" columnClasses="col7" id="IpPanel7" style="width:100%;text-align:center;" styleClass="row1">

                            <h:inputText disabled="#{InterestParameter.inputDis6}"  id="inputText6" styleClass="input"  value="#{InterestParameter.inputText6}"/>
                            <h:outputLabel id="gridLabel10" styleClass="Label0" value="To"/>
                            <h:inputText disabled="#{InterestParameter.inputDis7}"  id="inputText7" styleClass="input"  value="#{InterestParameter.inputText7}"/>
                            <h:panelGroup>
                                <h:selectBooleanCheckbox  value="#{InterestParameter.valueFrchkFour}" id="checkBox8" disabled="#{InterestParameter.checkBox4}"/>
                                <h:outputLabel id="gridLabel11" styleClass="Label0" value="Int Paid"/>
                            </h:panelGroup>
                        </h:panelGrid>

                        <h:panelGrid   columns="4" columnClasses="col7" id="IpPanel8" style="width:100%;text-align:center;" styleClass="row2">

                            <h:inputText disabled="#{InterestParameter.inputDis8}"  id="inputText8" styleClass="input"  value="#{InterestParameter.inputText8}"/>
                            <h:outputLabel id="gridLabel12" styleClass="Label0" value="To"/>
                            <h:inputText disabled="#{InterestParameter.inputDis9}"  id="inputText9" styleClass="input" value="#{InterestParameter.inputText9}"/>
                            <h:panelGroup>
                                <h:selectBooleanCheckbox  value="#{InterestParameter.valuefrchkFive}"  id="checkBox9" disabled="#{InterestParameter.checkBox5}"/>
                                <h:outputLabel id="gridLabel13" styleClass="Label0" value="Int Paid"/>
                            </h:panelGroup>
                        </h:panelGrid>


                        <h:panelGrid columns="4" columnClasses="col7" id="IpPanel9" style="width:100%;text-align:center;" styleClass="row1">

                            <h:inputText disabled="#{InterestParameter.inputDis10}"  id="inputText10" styleClass="input" value="#{InterestParameter.inputText10}" />
                            <h:outputLabel id="gridLabel14" styleClass="Label0" value="To"/>
                            <h:inputText disabled="#{InterestParameter.inputDis11}"  id="inputText11" styleClass="input"  value="#{InterestParameter.inputText11}"/>
                            <h:panelGroup>
                                <h:selectBooleanCheckbox   value="#{InterestParameter.valueFrchkSix}" id="checkBox10"  disabled="#{InterestParameter.checkBox6}"/>
                                <h:outputLabel id="gridLabel15" styleClass="Label0" value="Int Paid"/>
                            </h:panelGroup>
                        </h:panelGrid>

                    </h:panelGrid>




                    <h:panelGrid  id="errorMsg10"   width="100%"   style="height:50px;" styleClass="error">
                        <h:outputText id="errMsg10" value="#{InterestParameter.msgFrInterest}"/>
                    </h:panelGrid>


                    <h:panelGrid id="footerpane5" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="buttonPanel">
                            <a4j:commandButton disabled="#{InterestParameter.saveButton}"  reRender="errMsg10" action="#{InterestParameter.save}"  id="btnSave" value="Save">
                                <rich:toolTip for="btnSave" value="Click To save the Data"></rich:toolTip>
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh"  value="Refresh" action="#{InterestParameter.refresh}" reRender="monthList,monthList2,acCode,errMsg10,monthList,btnSave,checkBox5,checkBox6,checkBox7,checkBox8,checkBox9,checkBox10,inputText1,inputText2,inputText3,inputText4,inputText5,inputText6,inputText7,inputText8,inputText9,inputText10,inputText11">
                                <rich:toolTip for="btnRefresh" value="Click To Refresh "></rich:toolTip>
                            </a4j:commandButton>
                            <a4j:commandButton id="btnClose"  value="Exit" action="#{InterestParameter.exitForm}">
                                <rich:toolTip for="btnClose" value="Click To Exit "></rich:toolTip>
                            </a4j:commandButton>

                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>

