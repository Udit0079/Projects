<%-- 
    Document   : EnableServices
    Created on : 1 Jul, 2017, 5:35:02 PM
    Author     : root
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
            <title>Enable Service</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{enableServices.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Enable Service"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{enableServices.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{enableServices.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col4,col1,col4,col1,col4" columns="6" id="gridpanel1"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblFunction" styleClass="headerLabel"  value="Function"  />
                        <h:selectOneListbox id="ddFunction" value="#{enableServices.function}" styleClass="ddlist" size="1"  style="width:120px">
                            <f:selectItems value="#{enableServices.functionList}" />
                            <a4j:support event="onblur"  action="#{enableServices.functionAction}"  focus="ddService" reRender="btnHtml1,stxtError,vertaskList,tableVerfypanel,tblRegion,
                                         lblService,ddService,lblCustId,txtDate,gridpanel2,gridpanel3,tablePanel" />
                        </h:selectOneListbox> 
                        <h:outputLabel id="lblService" styleClass="headerLabel"  value="Service Type" style="display:#{enableServices.hideServiceType}" />
                        <h:selectOneListbox id="ddService" value="#{enableServices.serviceType}" styleClass="ddlist" size="1"  style="width:120px;display:#{enableServices.hideServiceType}">
                            <f:selectItems value="#{enableServices.serviceTypeList}" />

                        </h:selectOneListbox> 
                        <h:outputLabel id="lblCustId" styleClass="headerLabel"  value="Customer Id" style="display:#{enableServices.hideCustID}"/>
                        <h:inputText id="txtDate"   styleClass="input" style="setMask();display:#{enableServices.hideCustID}"  value="#{enableServices.customerId}" size="10">
                            <a4j:support event="onblur" action="#{enableServices.onblurCustId()}" reRender="mainPanelGrid" oncomplete="setMask();" focus="ddFreq"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col4,col1,col4,col1,col4" columns="6" id="gridpanel2"   style="height:30px;display:#{enableServices.hidePanel2}" styleClass="row2" width="100%" >
                        <h:outputLabel id="lblFreq" styleClass="headerLabel"  value="Frequency"  />
                        <h:selectOneListbox id="ddFreq" value="#{enableServices.frequency}" styleClass="ddlist" size="1"  style="width:120px">
                            <f:selectItems value="#{enableServices.frequencyList}" />

                        </h:selectOneListbox> 
                        <h:outputLabel id="lblstrDate" styleClass="headerLabel"  value="Strat Date"/>
                        <h:inputText id="txtstrDate"   styleClass="input calInstDate" style="setMask();"  value="#{enableServices.startDate}" size="10">

                        </h:inputText>
                        <h:outputLabel id="lblstrIndctr" styleClass="headerLabel"  value="Start Indicator"  />
                        <h:selectOneListbox id="ddstrIndctr" value="#{enableServices.startIndct}" styleClass="ddlist" size="1"  style="width:120px">
                            <f:selectItems value="#{enableServices.startIndctList}" />
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col4,col1,col4,col1,col4" columns="6" id="gridpanel3"   style="height:30px;display:#{enableServices.hidepanel3}" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblEmail" styleClass="headerLabel"  value="Email Id"/>
                        <h:inputText id="txtEmail"   styleClass="input"  value="#{enableServices.email}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel />
                        <h:outputLabel />
                        <h:outputLabel />
                        <h:outputLabel />
                    </h:panelGrid>

                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{enableServices.hideTablePanel}" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{enableServices.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="Account Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="A/C Holder Name" /></rich:column>
                                        <rich:column><h:outputText value="Account Type" /></rich:column>
                                        <rich:column>  
                                            <h:selectBooleanCheckbox  id="c1"  value="#{enableServices.checkAllBox}">
                                                <a4j:support actionListener="#{enableServices.checkAll()}" event="onclick" reRender="tablePanel"/> 
                                            </h:selectBooleanCheckbox>
                                        </rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.accType}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <h:selectBooleanCheckbox  id="c2"  value="#{dataItem.checkBox}">
                                        <a4j:support actionListener="#{enableServices.checkUnCheck()}" event="onclick" reRender="tablePanel"/> 
                                    </h:selectBooleanCheckbox>
                                </rich:column> 
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="tableVerfypanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{enableServices.hideTablePanel1}" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion1">
                            <rich:dataTable value="#{enableServices.verifyDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="vertaskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="Account Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="CustId" /></rich:column>
                                        <rich:column><h:outputText value="Account Holder Name" /></rich:column>
                                        <rich:column><h:outputText value="Verify" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="verifyLink" oncomplete="if(#{enableServices.verifyPanelViewFlag == true})
                                                     {#{rich:component('verifyViewPanel')}.show();}else{#{rich:component('verifyViewPanel')}.hide();} " 
                                                     action="#{enableServices.getviewDetails}"
                                                     reRender=" verifyViewPanel,closelink,verifyViewPanel1,stxtAc1,stxtName1,stxtserviceType,stxtcustid1,
                                                     stxtemail1,stxtfrequency1,stxtissueDt1,stxtstartFlag1,vrifyViewbtn,vrifyViewClose">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{enableServices.verifyCurrentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>

                            </rich:dataTable>
                            <rich:datascroller align="left" for="vertaskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <%-- <a4j:commandButton id="btnDownload"  value="SAVE" action="#{enableServices.createConfirmTxt()}"  reRender="mainPanelGrid,txtDate,processPanel" styleClass="color:#044F93;text-decoration:none;"/>--%>
                            <a4j:commandButton id="btnHtml1" value="#{enableServices.btnLbl}" action="#{enableServices.processAction()}" disabled = "#{enableServices.btndisbl}" 
                                               reRender="mainPanelGrid,processPanel,tablePanel"  />
                            <%-- <a4j:commandButton id="btnHtml" value="SAVE" action="#{enableServices.createConfirmTxt}" 
                                                oncomplete="#{rich:component('processPanel')}.show();setMask();" 
                                        reRender="mainPanelGrid,processPanel,tablePanel"  />--%>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{enableServices.btnRefreshAction()}"  reRender="mainPanelGrid,txtDate,tablePanel" oncomplete="#{rich:element('txtDate')}.style=setMask()"/>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{enableServices.btnExitAction()}" reRender="mainPanelGrid" />
                        </h:panelGroup>
                    </h:panelGrid>  
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="#{enableServices.confirmText}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{enableServices.processAction()}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();#{rich:element('ddFunction')}.focus();setMask();" 
                                                       reRender="mainPanel,tablePanel,tablePanelDownload"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();" oncomplete="setMask();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="verifyPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to verify the entry"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{enableServices.verification()}" 
                                                       oncomplete="#{rich:component('verifyPanel')}.hide();#{rich:component('verifyViewPanel')}.hide();" 
                                                       reRender="stxtError,mainPanelGrid,tableVerfypanel,vertaskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('verifyPanel')}.hide();" oncomplete="setMask();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="verifyViewPanel" height="200" width="900">
                <f:facet name="header">
                    <h:outputText value="Verification detail"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                        <rich:componentControl for="verifyViewPanel" attachTo="closelink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="verifyViewPanel1" width="100%">
                        <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow1" style="height:30px;"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lblAc1" styleClass="label"  value="Ac No: " />
                            <h:outputText id="stxtAc1" styleClass="output" value="#{enableServices.acno1}"/>
                            <h:outputLabel id="lblName1" styleClass="label"  value="Customer Name:" />
                            <h:outputText id="stxtName1" styleClass="output" value="#{enableServices.name1}"/>
                            <h:outputLabel id="lblserviceType" styleClass="label"  value="Service Type:" />
                            <h:outputText id="stxtserviceType" styleClass="output" value="#{enableServices.serviceType1}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow2" style="height:30px;"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lblcustid1" styleClass="label"  value="Customer Id: " />
                            <h:outputText id="stxtcustid1" styleClass="output" value="#{enableServices.custid1}"/>
                            <h:outputLabel id="lblstartFlag1" styleClass="label"  value="Start Indicator:" />
                            <h:outputText id="stxtstartFlag1" styleClass="output" value="#{enableServices.startFlag1}"/>
                            <h:outputLabel id="lblfrequency1" styleClass="label"  value="Frequency Type:" />
                            <h:outputText id="stxtfrequency1" styleClass="output" value="#{enableServices.frequency1}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow3" style="height:30px;"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lblissueDt1" styleClass="label"  value="Enter Date: " />
                            <h:outputText id="stxtissueDt1" styleClass="output" value="#{enableServices.date1}"/>
                            <h:outputLabel id="lblemail1" styleClass="label"  value="Email Id:" />
                            <h:outputText id="stxtemail1" styleClass="output" value="#{enableServices.email1}"/>
                            <h:outputLabel>
                            </h:outputLabel>
                            <h:outputText>
                            </h:outputText>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="verifyViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="vrifyViewBtnPanel">
                            <a4j:commandButton id="vrifyViewbtn" value="Verify" onclick="#{rich:component('verifyPanel')}.show();"/>
                            <a4j:commandButton id="vrifyViewClose" value="Close" onclick="#{rich:component('verifyViewPanel')}.hide(); return false;"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
