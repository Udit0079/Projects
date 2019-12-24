<%-- 
    Document   : InstrumentSearch
    Created on : Sep 7, 2010, 11:33:18 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Instrument Search</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript"></script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="output" value="#{InstrumentSearch.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Instrument Search"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InstrumentSearch.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="d22" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtmessage" styleClass="error" value="#{InstrumentSearch.message}"  style="width:100%;text-align:center;"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="typeId" width="100%" styleClass="row1">
                        <h:outputLabel id="lblRepType" styleClass="headerLabel"  value="Report Option" style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddRepType" styleClass="ddlist" size="1" value="#{InstrumentSearch.repTp}">
                            <f:selectItems value="#{InstrumentSearch.repTypeOption}"/>
                            <a4j:support  action="#{InstrumentSearch.setBranchOptions}" event="onblur" oncomplete="
                                          if(#{InstrumentSearch.repTp=='C'}){#{rich:element('txtchqno')}.focus();}else{#{rich:element('txtAcNo')}.focus();}" 
                                          reRender="stxtmessage,a9,txtAcNo,datatablepanelGrid"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblacNo" styleClass="headerLabel" value="Account No" style="width:100%;text-align:left;"></h:outputLabel>
                        <h:panelGroup layout="block" style="width:100%;text-align:left;">
                            <h:inputText id="txtAcNo" styleClass="input" size="#{InstrumentSearch.acNoMaxLen}" value="#{InstrumentSearch.oldAcNo}" maxlength="#{InstrumentSearch.acNoMaxLen}" disabled="#{InstrumentSearch.acNFlag}">
                                <a4j:support event="onblur" action="#{InstrumentSearch.onBlurGetNewAccount}" reRender="stxtAccNo"/> 
                            </h:inputText>
                            <h:outputText id="stxtAccNo" styleClass="output" value="#{InstrumentSearch.acNo}"/>
                        </h:panelGroup>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="a9" width="100%" styleClass="row2">
                        <h:outputLabel id="lblchqno" styleClass="headerLabel"  value="Cheque No. :" style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtchqno" styleClass="input"  size="12"  value="#{InstrumentSearch.chqno}" disabled="#{InstrumentSearch.chqFlg}" >
                            <a4j:support actionListener="#{InstrumentSearch.onblurChequeNo}" event="onblur" oncomplete="#{rich:element('ddactype1')}.focus();" reRender="stxtmessage,stxtaccno,stxtname,stxtpstatus,stxtcseries"/>
                        </h:inputText>
                        <h:outputLabel id="lblactype" styleClass="headerLabel" value="Account Type :" style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddactype1"  styleClass="ddlist" size="1" style="width: 80px" value="#{InstrumentSearch.acctype}" disabled="#{InstrumentSearch.acTFlag}">
                            <f:selectItems value="#{InstrumentSearch.acctTypeOption}"/>
                        </h:selectOneListbox>                        
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="2" columnClasses="col9,col9" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblaccno" styleClass="headerLabel"  value="Account No. :" style="width:100%;padding-left:350px;" />
                        <h:outputText id="stxtaccno" styleClass="output" value="#{InstrumentSearch.accno}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="2" columnClasses="col9,col9" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblname" styleClass="headerLabel"  value="Name :" style="width:100%;padding-left:350px;" />
                        <h:outputText id="stxtname" styleClass="output" value="#{InstrumentSearch.name}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" columns="2" columnClasses="col9,col9" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblpstatus" styleClass="headerLabel"  value="Present Status :" style="width:100%;padding-left:350px;" />
                        <h:outputText id="stxtpstatus" styleClass="output" value="#{InstrumentSearch.pstatus}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel6" columns="2" columnClasses="col9,col9" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblcseries" styleClass="headerLabel"  value="Cheque Series :" style="width:100%;padding-left:350px;" />
                        <h:outputText id="stxtcseries" styleClass="output" value="#{InstrumentSearch.cseries}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid id="datatablepanelGrid" style="width:100%"> 
                        <rich:dataTable id="grid" value="#{InstrumentSearch.chqList}" var="dataItem" rows="10" width="100%" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" rowClasses="gridrow1,gridrow2">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column width=""><h:outputText value="S.No." /></rich:column>
                                    <rich:column><h:outputText value="Cheque Series." /></rich:column>
                                    <rich:column><h:outputText value="Cheque No." /></rich:column>
                                    <rich:column><h:outputText value="Status"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.status}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.chqBookNo}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.chqNo}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.favoring}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller for="grid" maxPages="50" style="text-align:left"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton value="Search" id="btn31" reRender="stxtaccno,stxtname,stxtpstatus,stxtmessage,stxtcseries,datatablepanelGrid" action="#{InstrumentSearch.searchInfo}" focus="refreshBtn"/>
                            <a4j:commandButton value="Refresh" id="refreshBtn" action="#{InstrumentSearch.reset}" reRender="stxtmessage,txtchqno,stxtaccno,stxtname,stxtpstatus,stxtcseries,datatablepanelGrid,ddRepType,txtAcNo" focus="repTp"/>
                            <a4j:commandButton value="Exit" id="btnclose" action="#{InstrumentSearch.exitFrm}" reRender="stxtmessage,txtchqno,stxtaccno,stxtname,stxtpstatus,stxtcseries,datatablepanelGrid,ddRepType,txtAcNo"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
