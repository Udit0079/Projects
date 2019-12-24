<%-- 
    Document   : fdinterestposting
    Created on : 17 Feb, 2018, 10:53:02 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
       <head>
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>FDR Interest Posting</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
         <a4j:form id="form1">
           <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
               <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{FdInterestPosting.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="FDR INTEREST POSTING"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{FdInterestPosting.userName}"/>
                        </h:panelGroup>
               </h:panelGrid>
               <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                   <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{FdInterestPosting.message}"/>
              </h:panelGrid>
              <h:panelGrid  id="repTypeGrid" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" styleClass="row1" width="100%">
                  <h:outputLabel id="lblRepType" styleClass="label" value="Security Type"/>
                  <h:selectOneListbox id="ddRepType" size="1" styleClass="ddlist" value="#{FdInterestPosting.repType}">
                        <f:selectItems value="#{FdInterestPosting.repTypeList}"/>  
                        <a4j:support action="#{FdInterestPosting.getControlNoList}" event="onblur" reRender="ctrlNoDD"/>   
                  </h:selectOneListbox>         
                  <h:outputLabel id="ctrlType" styleClass="label" value="Control No."/>
                  <h:selectOneListbox id="ctrlNoDD" styleClass="ddlist" size="1" style="width: 220px" value="#{FdInterestPosting.ctrlNoDd}"> 
                       <f:selectItems value="#{FdInterestPosting.ctrlNoList}"/>
                       <a4j:support action="#{FdInterestPosting.onCntlNoLostFocus}" event="onblur" reRender="frDt,toDt,mainPanel,message,debitAcnoGrid,creditAcnoGrid,accrAcnoGrid,popUpRepPanel,btnPost"/>    
                  </h:selectOneListbox>
              </h:panelGrid>          
              <h:panelGrid id="repTypeGrid1" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                   <h:outputLabel styleClass="label" value="From Date"/>
                   <h:inputText id="frDt" value="#{FdInterestPosting.frmDt}" maxlength="10" styleClass="input frDt" style="setMask();width:70px;" disabled="true"/>
                   <h:outputLabel value="To Date" styleClass="label"/>
                   <h:inputText id="toDt" value="#{FdInterestPosting.toDt}" maxlength="10"  style="width:70px;" styleClass="input toDt" />  
                   <a4j:support event="onblur" oncomplete="setMask()"/>
              </h:panelGrid>
              <h:panelGrid id="debitAcnoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblDebitAcno" styleClass="label" value="Debit A/c No."></h:outputLabel>
                        <%-- <h:outputText id="stxtAccrAcno" styleClass="output" value="#{FdInterestPosting.accrAcno}"/>--%>
                        <h:inputText id="stxtAccrAcno" styleClass="input" size="#{FdInterestPosting.accrAcno}"  value="#{FdInterestPosting.accrAcno}" disabled="#{FdInterestPosting.accacnovisibility}">
                          <a4j:support actionListener="#{FdInterestPosting.getNewDrAcNo}" event="onblur"  reRender="lblMsg,stxtAccrAcnoDesc,message"/>
                        </h:inputText>
                        <h:outputText id="stxtAccrAcnoDesc" styleClass="output" value="#{FdInterestPosting.accrHeadDesc}"  />
                        <h:outputLabel id="lblDebitAmt" styleClass="label" value="Total Debit Amount"></h:outputLabel>
                        <h:outputText id="stxtDebitAmt" styleClass="output" value="#{FdInterestPosting.drAmt}"/>
               </h:panelGrid>
               <h:panelGrid id="creditAcnoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblCreditAcno" styleClass="label" value="Credit A/c No."></h:outputLabel>
                        <h:outputText id="stxtCreditAcno" styleClass="output" value="#{FdInterestPosting.crAcno}"/>
                        <h:outputText id="stxtCreditAcnoDesc" styleClass="output" value="#{FdInterestPosting.crHeadDesc}"/>
                        <h:outputLabel id="lblCreditAmt" styleClass="label" value="Total Credit Amount (Bal Days)"></h:outputLabel>
                        <h:inputText id="stxtCreditAmt" value="#{FdInterestPosting.crAmt}" styleClass="input">
                           <a4j:support action="#{FdInterestPosting.onBlurValAction}" event="onblur" reRender="stxtMsg,stxtDebitAmt,popUpRepPanel"/> 
                        </h:inputText>                        
                </h:panelGrid>
                <h:panelGrid id="accrAcnoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAccrAcno" styleClass="label" value="Accured A/c No."></h:outputLabel>
                        <h:outputText id="stxtDebitAcno" styleClass="output" value="#{FdInterestPosting.drAcno}"/>
                        <h:outputText id="stxtDebitAcnoDesc" styleClass="output" value="#{FdInterestPosting.drHeadDesc}"/>
                        <h:outputLabel id="lblAccrAmt" styleClass="label" value="Total Credit Amount (Posted)"></h:outputLabel>
                        <h:outputText id="stxtAccrAmt" styleClass="output" value="#{FdInterestPosting.accrAmt}"/>
                 </h:panelGrid>
                 <h:panelGrid id="remarkIdGrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="lblRemark" styleClass="label" value="Remark"/>
                        <h:inputText id="stxtRemark" styleClass="output" style="width: 220px" value="#{FdInterestPosting.remark}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                 </h:panelGrid> 
                 <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnCal" action="#{FdInterestPosting.calTxn}" value="Calculate" oncomplete="if(#{FdInterestPosting.calcFlag==true}){
                                               #{rich:component('popUpRepPanel')}.show();
                                               }
                                               else if(#{FdInterestPosting.calcFlag==false})
                                               {
                                               #{rich:component('popUpRepPanel')}.hide();
                                               }" reRender="message,debitAcnoGrid,creditAcnoGrid,accrAcnoGrid,popUpRepPanel,btnPost,stxtAccrAcno"/>
                            <a4j:commandButton id="btnPost" action="#{FdInterestPosting.postTxn}"disabled="#{FdInterestPosting.visiblePost}"  value="Post"  reRender="message,debitAcnoGrid,creditAcnoGrid,accrAcnoGrid,popUpRepPanel,btnPost,mainPanel" />
                            <a4j:commandButton id="btnRefresh" action="#{FdInterestPosting.ClearAll}" value="Refresh" reRender="mainPanel,message,debitAcnoGrid,creditAcnoGrid,accrAcnoGrid,popUpRepPanel,btnPost,frDt,toDt"/>
                            <a4j:commandButton id="btnExit" action="#{FdInterestPosting.exitButton}" value="Exit"/>
                        </h:panelGroup>
                 </h:panelGrid>
               
           </h:panelGrid>
         </a4j:form>  
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="FDR Interest Posting" style="text-align:center;"/>
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
                                <a4j:include viewId="#{FdInterestPosting.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>   
        </body>
    </html>
</f:view>
