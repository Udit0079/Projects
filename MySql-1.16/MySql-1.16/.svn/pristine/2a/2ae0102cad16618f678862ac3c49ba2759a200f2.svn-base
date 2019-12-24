<%-- 
    Document   : npaTransaction
    Created on : Feb 14, 2014, 11:15:34 AM
    Author     : Athar Reza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>NPA Transaction</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".issueDt").mask("99/99/9999");
                }
                function validate(e) {e = e || window.event; var bad = /[^\sa-z\d]/i, key = String.fromCharCode( e.keyCode || e.which ); if ( e.which !== 0 && e.charCode !== 0 && bad.test(key) ){e.returnValue = false;if ( e.preventDefault ) {e.preventDefault();}} } 
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{NPATransactions.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="NPA Transaction"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{NPATransactions.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{NPATransactions.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="acnoPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAccNo" styleClass="label" value="A/c No."></h:outputLabel>
                        <h:panelGroup>
                            <h:inputText id="txtaccNo" styleClass="input" maxlength="#{NPATransactions.acNoMaxLen}" value="#{NPATransactions.oldAcno}" style="width:100px;" onkeypress="validate(event)">
                                <a4j:support event="onblur" action="#{NPATransactions.onBlurAcno}" reRender="stxtMessage,stxtAccNo,stxtName,stxtAcName,stxtCustid,stxtJtName,listTransaction,listType,listBy,listRelatedTo,txtDetails" focus="listTransaction"oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText id="stxtAccNo" styleClass="output" value="#{NPATransactions.acNo}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblName" styleClass="label" value="Date of Opening"/>
                        <h:outputText id="stxtName" styleClass="output" value="#{NPATransactions.opDt}"/>  
                        <h:outputLabel id="lblAcName" styleClass="label" value="Account. Name"/>
                        <h:outputText id="stxtAcName" styleClass="output" value="#{NPATransactions.acName}"/>     
                    </h:panelGrid>

                    <h:panelGrid id="jtNamePanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">   
                        <h:outputLabel id="lblJtName" styleClass="label" value="Joint. Name"/>
                        <h:outputText id="stxtJtName" styleClass="output" value="#{NPATransactions.joiName}"/>  
                        <h:outputLabel id="lblCustid" styleClass="label" value="Customer Id"/>
                        <h:outputText id="stxtCustid" styleClass="output" value="#{NPATransactions.custid}"/>   
                        <h:outputLabel id="lblTransaction" value="Transaction" styleClass="label"/>
                        <h:selectOneListbox id="listTransaction" style="width:80px" styleClass="ddlist" size="1" value="#{NPATransactions.trantype}">
                            <f:selectItems value="#{NPATransactions.trantypeList}"/>                                    
                        </h:selectOneListbox>    
                    </h:panelGrid>

                    <h:panelGrid id="idtype" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">     
                        <h:outputLabel id="lblType" value="Type" styleClass="label"/>
                        <h:selectOneListbox id="listType" style="width:80px" styleClass="ddlist" size="1" value="#{NPATransactions.type}" >
                            <f:selectItems value="#{NPATransactions.typeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblBy" value="By" styleClass="output"/>
                        <h:selectOneListbox id="listBy" style="width:80px" styleClass="ddlist" size="1" value="#{NPATransactions.tranby}" >
                            <f:selectItems value="#{NPATransactions.tranbyList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblRelatedTo" value="Related To" styleClass="output"/>
                        <h:selectOneListbox id="listRelatedTo" style="width:80px" styleClass="ddlist" size="1" value="#{NPATransactions.relatedto}">
                            <f:selectItems value="#{NPATransactions.relatedtoList}"/>
                        </h:selectOneListbox>    
                    </h:panelGrid>

                    <h:panelGrid id="iddate" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">    
                        <h:outputLabel id="lbdt" value="Date" styleClass="label"/>     
                        <h:inputText id="asOnDt" styleClass="input issueDt" style="width:70px;"  value="#{NPATransactions.date}">
                            <a4j:support event="onblur" focus="txtAmount"oncomplete="setMask();"/>
                        </h:inputText>   
                        <h:outputLabel id="lblAmount" value="Amount" styleClass="label"/>
                        <h:inputText id="txtAmount" styleClass="input" value="#{NPATransactions.amount}" style="width:70px"><f:convertNumber maxFractionDigits="2" minFractionDigits="2" />
                            <%--a4j:support event="onblur" action="#{NPATransactions.onBlurAmt}" reRender="stxtMessage,stxtAccNo,stxtName,stxtJtName" oncomplete="setMask();"/--%>
                        </h:inputText>                
                        <h:outputLabel id="lblDetails" value="Details" styleClass="label"/>
                        <h:inputText id="txtDetails" styleClass="input" value="#{NPATransactions.details}" onkeypress="validate(event)">
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" disabled="#{NPATransactions.disableSave}" value="Save" oncomplete="#{rich:component('savePanel')}.show();" reRender="stxtMessage,savePanel"/>
                            <a4j:commandButton id="btnHtml2" disabled="#{NPATransactions.disableVerify}" value="Verify" action="#{NPATransactions.verified}" oncomplete="setMask();" reRender="stxtMessage,mainPanel,taskList,tablePanel,savePanel"/>
                            <a4j:commandButton id="btnRefresh" action="#{NPATransactions.btnRefreshAction}" value="Refresh" reRender="mainPanel,stxtMessage" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" action="#{NPATransactions.btnExitAction}" value="Exit" reRender="mainPanel" oncomplete="setMask();"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable id="taskList" value="#{NPATransactions.tableDataList}" var="dataItem"
                                            rowClasses="row1,row2" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="11"><h:outputText value="NPA Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No" /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Transaction" /></rich:column>
                                        <rich:column><h:outputText value="Type" /></rich:column>
                                        <rich:column><h:outputText value=" Txn Date" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Rec No" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="Details" /></rich:column>        
                                        <rich:column><h:outputText value="Auth Status" /></rich:column>                 
                                        <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.transactionType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.type}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.txnDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.recNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.detail}" /></rich:column>
                                <%--rich:column><h:outputText value="#{dataItem.auth}" /></rich:column--%>
                                
                             <rich:column style="text-align:center;cursor:pointer;"> 
                                <h:outputText  value="#{dataItem.auth}" style="text-align:center"/>
                                <a4j:support action="#{NPATransactions.changeStatus}" ajaxSingle="true" event="ondblclick" reRender="datalist,stxtMessage,mainPanel">
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{NPATransactions.currentItem}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{NPATransactions.currentRow}"/>
                                </a4j:support>
                            </rich:column>
                                   
                                 <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="taskList,stxtMessage,mainPanel">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{NPATransactions.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{NPATransactions.currentRow}" />
                                    </a4j:commandLink>                                    
                                </rich:column>
                                
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20"/>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnSaveYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to save this transaction ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnSaveYes" action="#{NPATransactions.saveNPATransaction}" oncomplete="#{rich:component('savePanel')}.hide();" reRender="mainPanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnSaveNo" onclick="#{rich:component('savePanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYesDel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete This Record?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true"  action="#{NPATransactions.deleteUnAuth}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="mainPanel" focus=""/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoDel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>    
        </body>
    </html>
</f:view>