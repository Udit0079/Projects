<%-- 
    Document   : NPAChargesMaster
    Created on : 31 Jul, 2017, 1:10:29 PM
    Author     : Manish kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>NPA Charges Master</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".effFromDt").mask("99/99/9999");
                    jQuery(".effToDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="npaChargesMaster" enctype="multipart/form-data">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{nPAChargesMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="NPA CHARGES MASTER"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{nPAChargesMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errormsg" style="height:25px;text-align:center" styleClass="#{nPAChargesMaster.msgStyle}" width="100%">
                        <h:outputText id="errmsg" value="#{nPAChargesMaster.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel1" width="100%" styleClass="row2">
                        <h:outputLabel value="Acount Nature" id="acountNature" styleClass="label"/>
                        <h:selectOneListbox id="acountNatureId" value="#{nPAChargesMaster.acNature}" disabled="#{nPAChargesMaster.disableAcNature}" styleClass="ddlist" size="1"  style="width:100px" >
                            <f:selectItems value="#{nPAChargesMaster.acNatureOption}" />  
                            <a4j:support event="onblur" action="#{nPAChargesMaster.initData}"  reRender="acountTypeId,chargesId,errmsg" />
                        </h:selectOneListbox>

                        <h:outputLabel value="Acount Type" id="acountType" styleClass="label"/>
                        <h:selectOneListbox id="acountTypeId" value="#{nPAChargesMaster.acType}" disabled="#{nPAChargesMaster.disableAcType}" styleClass="ddlist" size="1"  style="width:100px" >
                            <f:selectItems value="#{nPAChargesMaster.acTypeOption}" /> 
                            <a4j:support event="onblur" action="#{nPAChargesMaster.clear}"  reRender="errmsg" />
                        </h:selectOneListbox>

                        <h:outputLabel value="Charges" id="charges" styleClass="label"/>
                        <h:selectOneListbox id="chargesId" value="#{nPAChargesMaster.charge}" disabled="#{nPAChargesMaster.disableAcNature}" styleClass="ddlist" size="1"  style="width:130px" >
                            <f:selectItems value="#{nPAChargesMaster.chargesOption}" />     
                            <a4j:support event="onblur" action="#{nPAChargesMaster.clear}"  reRender="errmsg" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel2" width="100%" styleClass="row1">
                        <h:outputLabel value="Effective From Date" styleClass="label "/>
                        <h:inputText id="effectiveFromDt" styleClass="input effFromDt" maxlength="10"   value="#{nPAChargesMaster.effectiveFromDt}" style="width:100px;setMask();" >
                            <a4j:support event="onblur" action="#{nPAChargesMaster.clear}"  reRender="errmsg" />
                        </h:inputText>

                        <h:outputLabel value="Effective To Date" styleClass="label"/>
                        <h:inputText id="effectiveToDt" styleClass="input effToDt" maxlength="10"  value="#{nPAChargesMaster.effectiveToDt}" style="width:100px;setMask();" >
                            <a4j:support event="onblur" action="#{nPAChargesMaster.clear}"  reRender="errmsg" />
                        </h:inputText>
                        <h:outputLabel value="Amount" styleClass="label"/>
                        <h:inputText  value="#{nPAChargesMaster.amount}" id="amoutnId" maxlength="27" styleClass="input" style="width:130px">
                            <a4j:support event="onblur" action="#{nPAChargesMaster.clear}"  reRender="errmsg" />
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel3" width="100%" styleClass="row2">
                        <h:outputLabel value="GL Head" styleClass="label"/>
                        <h:inputText  value="#{nPAChargesMaster.glHead}" id="glHeadId" maxlength="08" styleClass="input" style="width:100px">
                            <a4j:support event="onblur" action="#{nPAChargesMaster.initGlName}"  reRender="glHeadLabel,errmsg,saveUpdate,glHeadId" />
                            <%-- it will use for button disable
                            oncomplete="if(#{nPAChargesMaster.glHead != ''} && #{nPAChargesMaster.glHeadName != ''}
                                         && #{nPAChargesMaster.acNature != '--Select--'} && #{nPAChargesMaster.acType != '--Select--'} && #{nPAChargesMaster.charge != '--Select--'}
                                         && #{nPAChargesMaster.effectiveFromDt != ''} && #{nPAChargesMaster.effectiveToDt != ''} && #{nPAChargesMaster.amount != ''}){
                                                  #{rich:element('saveUpdate')}.disabled=false;
                                                    #{rich:element('saveUpdate')}.focus(); 
                                               }
                                               "
                            --%>
                        </h:inputText>
                        <h:outputLabel value="#{nPAChargesMaster.glHeadName}" id="glHeadLabel" styleClass="label"/>
                        <h:outputLabel value="" styleClass="label"/>
                        <h:outputLabel value="" styleClass="label"/>
                        <h:outputLabel value="" styleClass="label"/>
                    </h:panelGrid>

                    <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="saveUpdate" value="#{nPAChargesMaster.buttonLabel}" action="#{nPAChargesMaster.saveOrUpdate}" disabled="#{nPAChargesMaster.disableButton}"/>
                            <h:commandButton id="btnRefresh" value="Refresh" action="#{nPAChargesMaster.refreshForm}"/>
                            <h:commandButton id="btnExit" value="Exit" action="#{nPAChargesMaster.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>

                <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                    <rich:dataTable  value="#{nPAChargesMaster.npaList}" var="item" 
                                     rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                     width="100%">
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column colspan="11"><h:outputText value="NPA CHARGES LIST"/></rich:column>
                                <rich:column breakBefore="true" width="5"><h:outputText value="SNo."/></rich:column>
                                <rich:column width="200"><h:outputText value="Charges" /></rich:column>
                                <rich:column width="90"><h:outputText value="Effective From" /></rich:column>
                                <rich:column width="90"><h:outputText value="Effective To" /></rich:column>
                                <rich:column width="110"><h:outputText value="Amount" /></rich:column>
                                <rich:column width="200"><h:outputText value="GL Head" /></rich:column>
                                <rich:column width="20"><h:outputText value="Select" /></rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:column width="5"><h:outputText value="#{item.sno}"/></rich:column> <%--value="#{item.sno}" --%>
                        <rich:column><h:outputText value="#{item.chargeDesc}"/></rich:column>
                        <rich:column style="text-align:center;"><h:outputText value="#{item.effFromDt}"/></rich:column>
                        <rich:column style="text-align:center;"><h:outputText value="#{item.effToDt}"/></rich:column>
                        <rich:column style="text-align:right;"><h:outputText value="#{item.amount}"/></rich:column>
                        <rich:column><h:outputText value="#{item.glHeadDesc}"/></rich:column>
                        <rich:column style="text-align:center;">
                            <a4j:commandLink ajaxSingle="true" id="selectlink"  action="#{nPAChargesMaster.setNPA}" focus="" reRender="errmsg,saveUpdate,gridPanel1,gridPanel2,gridPanel3">
                                <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                <f:setPropertyActionListener value="#{item.sno}" target="#{nPAChargesMaster.currentIndex}"/>
                                <rich:toolTip for="selectlink" value="Select" />
                            </a4j:commandLink>
                        </rich:column>
                    </rich:dataTable>
                    <rich:datascroller id="deafDataScroll"align="left" for="txnList" maxPages="20"/>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
