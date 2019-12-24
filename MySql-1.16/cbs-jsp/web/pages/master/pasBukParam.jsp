<%--
    Document   : pasBukParam
    Created on : Aug 31, 2010, 4:24:38 PM
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
            <title>Pass Book Parameter</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="form5">
                <h:panelGrid id="mainPanel5" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">

                    <h:panelGrid columns="3" id="gridPane71" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{pasBukParam.todayDate}"  />
                        </h:panelGroup>
                        <h:outputLabel id="OL71" styleClass="output" value="Pass Book Parameter"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{pasBukParam.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="errorMsg3"   width="100%"   style="height:5px;text-align:center" styleClass="error">
                        <h:outputText id="errMsg3" value="#{pasBukParam.message}"/>
                    </h:panelGrid>

                    <h:panelGrid  columns="1" id="gridPanel72" style="text-align:left;" width="100%" styleClass="header">
                        <h:panelGrid columnClasses="col22,col22,col19" columns="3" id="gridPanel73" style="text-align:left;" width="100%" styleClass="row1">
                            <h:outputLabel id="OL72" styleClass="output" value="ORDER1"/>
                            <h:outputLabel id="OL73" styleClass="output" value="ORDER2"/>
                            <h:outputLabel id="OL74" styleClass="output" value="ORDER3"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col22,col22,col19" columns="3" id="gridPanel74" style="text-align:left;" width="100%" styleClass="row2">
                            <h:selectOneListbox id="ddAcNo35" styleClass="output" size="1" style="width:80px"  value="#{pasBukParam.item1}">
                                <f:selectItems value="#{pasBukParam.list2}"/>
                                <a4j:support event="onchange"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="ddAcNo36" styleClass="output" size="1" style="width:80px"  value="#{pasBukParam.item2}">
                                <f:selectItems value="#{pasBukParam.list2}"/>
                                <a4j:support event="onchange"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="ddAcNo37" styleClass="output" size="1" style="width:80px"  value="#{pasBukParam.item3}">
                                <f:selectItems value="#{pasBukParam.list2}"/>
                                <a4j:support event="onchange"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col22,col22,col19" columns="3" id="gridPanel75" style="text-align:left;" width="100%" styleClass="row1">
                            <h:panelGrid  columnClasses="col9,col9" columns="2" id="gridPanel76" style="text-align:left;" width="20%" styleClass="row1">
                                <h:outputLabel id="OL75" styleClass="output" value="Align"/>
                                <h:outputLabel id="OL76" styleClass="output" value="Width"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel77" style="text-align:left;" width="20%" styleClass="row1">
                                <h:outputLabel id="OL77" styleClass="output" value="Align"/>
                                <h:outputLabel id="OL78" styleClass="output" value="Width"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel78" style="text-align:left;" width="30%" styleClass="row1">
                                <h:outputLabel id="OL79" styleClass="output" value="Align"/>
                                <h:outputLabel id="OL80" styleClass="output" value="Width"/>
                            </h:panelGrid>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col22,col22,col19" columns="3" id="gridPanel79" style="text-align:left;" width="100%" styleClass="row2">
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel80" style="text-align:left;" width="30%" styleClass="row2">
                                <h:inputText  style="width:60px;" id="stxtuser12" styleClass="input" value="#{pasBukParam.inputText1}"/>
                                <h:inputText style="width:60px;" id="stxtuser13" styleClass="input"value="#{pasBukParam.inputText2}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel81" style="text-align:left;" width="30%" styleClass="row2">
                                <h:inputText style="width:60px;" id="stxtuser14" styleClass="input" value="#{pasBukParam.inputText3}"  />
                                <h:inputText style="width:60px;" id="stxtuser15" styleClass="input" value="#{pasBukParam.inputText4}"  />
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel82" style="text-align:left;" width="30%" styleClass="row2">
                                <h:inputText style="width:60px;" id="stxtuser16" styleClass="input" value="#{pasBukParam.inputText5}"  />
                                <h:inputText  style="width:60px;"id="stxtuser116" styleClass="input" value="#{pasBukParam.inputText6}"  />
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gridPanel45" style="text-align:left;" width="100%" styleClass="header">
                        <h:panelGrid columnClasses="col22,col22,col19" columns="3" id="gridPanel83" style="text-align:left;" width="100%" styleClass="row1">
                            <h:outputLabel id="OL81" styleClass="output" value="ORDER4"/>
                            <h:outputLabel id="OL82" styleClass="output" value="ORDER5"/>
                            <h:outputLabel id="OL83" styleClass="output" value="ORDER6"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col22,col22,col19" columns="3" id="gridPanel84" style="text-align:left;" width="100%" styleClass="row1">
                            <h:selectOneListbox id="ddAcNo38" styleClass="output" size="1" style="width:80px" value="#{pasBukParam.item4}">
                                <f:selectItems value="#{pasBukParam.list2}"/>
                                <a4j:support event="onchange"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="ddAcNo39" styleClass="output" size="1" style="width:80px" value="#{pasBukParam.item5}">
                                <f:selectItems value="#{pasBukParam.list2}"/>
                                <a4j:support event="onchange"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="ddAcNo40" styleClass="output" size="1" style="width:80px" value="#{pasBukParam.item6}">
                                <f:selectItems value="#{pasBukParam.list2}"/>
                                <a4j:support event="onchange"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col22,col22,col19" columns="3" id="gridPanel85" style="text-align:left;" width="100%" styleClass="row2">
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel50" style="text-align:left;" width="30%" styleClass="row2">
                                <h:outputLabel id="OL84" styleClass="output" value="Align"/>
                                <h:outputLabel id="OL85" styleClass="output" value="Width"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel86" style="text-align:left;" width="30%" styleClass="row2">
                                <h:outputLabel id="OL86" styleClass="output" value="Align"/>
                                <h:outputLabel id="OL87" styleClass="output" value="Width"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel87" style="text-align:left;" width="30%" styleClass="row2">
                                <h:outputLabel id="OL88" styleClass="output" value="Align"/>
                                <h:outputLabel id="OL89" styleClass="output" value="Width"/>
                            </h:panelGrid>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col22,col22,col19" columns="3" id="gridPanel89" style="text-align:left;" width="100%" styleClass="row1">
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel90" style="text-align:left;" width="30%" styleClass="row1">
                                <h:inputText style="width:60px;" id="stxtuser18" styleClass="input" value="#{pasBukParam.inputText7}"/>
                                <h:inputText style="width:60px;" id="stxtuser19" styleClass="input" value="#{pasBukParam.inputText8}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel91" style="text-align:left;" width="30%" styleClass="row1">
                                <h:inputText style="width:60px;" id="stxtuser20" styleClass="input" value="#{pasBukParam.inputText9}"/>
                                <h:inputText style="width:60px;" id="stxtuser21" styleClass="input" value="#{pasBukParam.inputText10}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel92" style="text-align:left;" width="30%" styleClass="row1">
                                <h:inputText style="width:60px;" id="stxtuser22" styleClass="input" value="#{pasBukParam.inputText11}"/>
                                <h:inputText style="width:60px;" id="stxtuser23" styleClass="input" value="#{pasBukParam.inputText12}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid  columns="1" id="gridPanel93" style="text-align:left;" width="100%" styleClass="header">
                        <h:panelGrid  columnClasses="col2,col2,col2,col2" columns="4" id="gridPanel94" style="text-align:left;" width="100%" styleClass="row2">
                            <h:panelGrid   columns="1" id="gridPanel95" style="text-align:left;" width="25%" styleClass="row2">
                                <h:outputLabel  id="OL90" styleClass="output" value="PASSLINE"/>
                            </h:panelGrid>
                            <h:panelGrid   columns="1" id="gridPanel96" style="text-align:left;" width="25%" styleClass="row2">
                                <h:outputLabel id="OL91" styleClass="output" value="HALFLINE"/>
                            </h:panelGrid>
                            <h:panelGrid   columns="1" id="gridPanel97" style="text-align:left;" width="25%" styleClass="row2">
                                <h:outputLabel id="OL92" styleClass="output" value="SKIPLINE"/>
                            </h:panelGrid>
                            <h:panelGrid   columns="1" id="gridPanel98" style="text-align:left;" width="25%" styleClass="row2">
                                <h:outputLabel id="OL93" styleClass="output" value="BEGINLINES"/>
                            </h:panelGrid>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col2,col2,col2,col2" columns="4" id="gridPanel99" style="text-align:left;" width="100%" styleClass="row1">
                            <h:inputText style="width:60px;" id="stxtuser24" styleClass="input" value="#{pasBukParam.passline}"/>
                            <h:inputText style="width:60px;" id="stxtuser25" styleClass="input" value="#{pasBukParam.halfline}"/>
                            <h:inputText style="width:60px;" id="stxtuser26" styleClass="input" value="#{pasBukParam.skipline}"/>
                            <h:inputText style="width:60px;" id="stxtuser28" styleClass="input" value="#{pasBukParam.beginlines}"/>
                        </h:panelGrid>

                        <h:panelGrid  columns="1" id="gridPanel100" style="text-align:left;" width="100%" styleClass="row1">
                            <h:outputLabel id="OL94" styleClass="output" value="For Vertical passbook printing passline and halfline are same"/>
                            <h:outputLabel id="OL95" styleClass="output" value="BEGINLINES- the lines we have to print blank on each new page"/>
                            <h:outputLabel id="OL96" styleClass="output" value="SKIPLINE - In horizontal printing; it stands for blank lines between two pages"/>
                            <h:outputLabel id="OL97" styleClass="output" value="HALFLINE - In horizontal printing; It stands for number of lines of a single page "/>
                            <h:outputLabel id="OL98" styleClass="output" value="PASSLINE -  It stands for number of lines available  on the passbook or total number of lines that could be printed"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel2" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel2">
                            <a4j:commandButton reRender="errMsg3,ddAcNo35,ddAcNo36,ddAcNo37,ddAcNo38,ddAcNo39,ddAcNo40,stxtuser12,stxtuser13,stxtuser14,stxtuser15,stxtuser16,stxtuser116,stxtuser18,stxtuser19,stxtuser20,stxtuser21,stxtuser22,stxtuser23,stxtuser24,stxtuser25,stxtuser26,stxtuser28" disabled="#{pasBukParam.saveButton}" action="#{pasBukParam.cmdOk}"   id="btnSave" value="Save">
                            </a4j:commandButton>                            
                            <a4j:commandButton   id="btnRefresh" value="Refresh" action="#{pasBukParam.refresh}" reRender="errMsg3,ddAcNo35,ddAcNo36,ddAcNo37,ddAcNo38,ddAcNo39,ddAcNo40,stxtuser12,stxtuser13,stxtuser14,stxtuser15,stxtuser16,stxtuser116,stxtuser18,stxtuser19,stxtuser20,stxtuser21,stxtuser22,stxtuser23,stxtuser24,stxtuser25,stxtuser26,stxtuser28">
                            </a4j:commandButton>
                            <a4j:commandButton   id="btnCancel" value="Exit" action="#{pasBukParam.exitForm}">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
