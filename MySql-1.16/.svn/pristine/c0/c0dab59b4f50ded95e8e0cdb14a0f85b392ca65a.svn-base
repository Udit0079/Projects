<%--
    Document   : dividendcalculation
    Created on : Aug 27, 2011, 11:15:53 AM
    Author     : Navneet Goyal
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Dividend Calculation</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="DividendCalculation"/>
            <a4j:form>
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DividendCalculation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Share Dividend Calculation"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="Username: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DividendCalculation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="errPanel" style="width:100%;text-align:center;" styleClass="row2">
                        <h:outputText value="#{DividendCalculation.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid id="panel1" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" style="height:30px;width:100%" styleClass="row2" width="100%">
                        <h:outputLabel styleClass="label" value="Alpha Code"/>
                        <h:selectOneListbox id="ddAlphaCOdeList" disabled="#{DividendCalculation.readOnlyAlphaCode}" styleClass="ddlist" size="1" value="#{DividendCalculation.alphaCode}" style="width:80px">
                            <f:selectItems value="#{DividendCalculation.alphaCodeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Share Amount" />
                        <h:inputText id="txtShareAmount" readonly="true" styleClass="input" value="#{DividendCalculation.shareAmount}" style="width:80px"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:inputText>
                        <h:outputLabel styleClass="label" value="Dividend (in %)"/>
                        <h:inputText id="txtDividend" styleClass="input" readonly="#{DividendCalculation.readOnlyDividend}" value="#{DividendCalculation.dividend}" style="width:80px"/>
                    </h:panelGrid>

                    <h:panelGrid id="panel4" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" style="height:30px;width:100%" styleClass="row2" width="100%">
                        <h:outputLabel styleClass="label" value="Customer Type"/>
                        <h:selectOneListbox id="ddCustTypeList" disabled="#{DividendCalculation.readOnlyFinYear}" styleClass="ddlist" size="1" value="#{DividendCalculation.custType}" style="width:80px">
                            <f:selectItems value="#{DividendCalculation.custTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="For the Financial Year"  />
                        <h:selectOneListbox id="txtFinancialYear" disabled="#{DividendCalculation.readOnlyFinYear}" styleClass="ddlist" size="1" value="#{DividendCalculation.financialYear}" style="width:80px">
                            <f:selectItems value="#{DividendCalculation.finYearList}"/>
                            <a4j:support event="onblur" focus="btnCalculate" action="#{DividendCalculation.onBlurFinancialYear}" reRender="btnCalculate,errPanel"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Post Date"/>
                        <h:inputText id="calDateofbirth" readonly="true" value="#{DividendCalculation.postDate}" styleClass="input calInstDate" style="width:80px;setMask()" maxlength="10"></h:inputText>
                    </h:panelGrid>

                    <h:panelGrid id="panel6" columnClasses="vtop" columns="1" style="display:none" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable id="dividendTable" value="#{DividendCalculation.dividendTable}" var="dataItem" rowClasses="gridrow1,gridrow2" columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" rows="8" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="Folio No." style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Cert. No." style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Issue Date" style="text-align:left"/></rich:column>
                                        <rich:column><h:outputText value="Tot Shares" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Amount" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Dividend" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Status" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Po/Slip Branch Name" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Related Acno" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Share Holder Name" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Purpose" style="text-align:left" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.folioNo}"></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.certNo}"></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.issueDt}"></h:outputText></rich:column>
                                <rich:column style="text-align:right"><h:outputText value="#{dataItem.nos}"></h:outputText></rich:column>
                                <rich:column style="text-align:right"><h:outputText value="#{dataItem.amt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText></rich:column>
                                <rich:column style="text-align:right"><h:outputText value="#{dataItem.divamt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.status}"></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.brcode}"></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.beneAcno}"></h:outputText></rich:column>
                                <rich:column style="text-align:left"><h:outputText value="#{dataItem.name}"></h:outputText></rich:column>
                                <rich:column style="text-align:left"><h:outputText value="#{dataItem.purpose}"></h:outputText></rich:column>
                                <f:facet name="footer">
                                    <h:outputText id="stxtRows" value="#{DividendCalculation.totalRows} rows generated !!"/>
                                </f:facet>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="dividendTable" maxPages="10" />

                        </a4j:region>

                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="btnRegion">
                                <a4j:commandButton id="btnCalculate" value="Calculate" action="#{DividendCalculation.calculateAction}" disabled="#{DividendCalculation.disableBtnCalculate}" reRender="mainPanel,popUpRepPanel" focus="btnPost"
                                                   oncomplete="if(#{DividendCalculation.visibleDividendTable==true})
                                                   {
                                                   #{rich:element('panel6')}.style.display='';
                                                   #{rich:component('popUpRepPanel')}.show();
                                                   }
                                                   if(#{DividendCalculation.visibleDividendTable==false})
                                                   {
                                                   #{rich:element('panel6')}.style.display=none;
                                                   #{rich:component('popUpRepPanel')}.hide();
                                                   }
                                                   
                                                   "/>
                                <a4j:commandButton id="btnPost" value="Post" oncomplete="#{rich:component('confirmationPanel')}.show()" disabled="#{DividendCalculation.disableBtnPost}"/>
                                <a4j:commandButton id="btnCancel" value="Cancel" type="reset" action="#{DividendCalculation.cancelAction}" reRender="mainPanel"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{DividendCalculation.exitAction}" reRender="mainPanel"/>
                                <rich:modalPanel id="confirmationPanel" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                                    <f:facet name="header">
                                        <h:outputText value="Confirmation DialogBox" />
                                    </f:facet>
                                    <h:form>
                                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                            <tbody>
                                                <tr style="height:40px">
                                                    <td colspan="2">
                                                        <h:outputText value="Do You Want To Post Dividend?"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="center" width="50%">

                                                            <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{DividendCalculation.postAction}"
                                                                               onclick="#{rich:component('confirmationPanel')}.hide();" reRender="mainPanel" />

                                                    </td>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton id="No" value="No" onclick="#{rich:component('confirmationPanel')}.hide();return false;"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </h:form>
                                </rich:modalPanel>
                            </a4j:region>

                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnRegion"/>
                <rich:modalPanel id="wait" autosized="true" width="140" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
             <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Dividend Calculation" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{DividendCalculation.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
