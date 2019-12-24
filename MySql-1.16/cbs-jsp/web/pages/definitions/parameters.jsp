<%-- 
    Document   : parameters
    Created on : 19 july 2011, 5:57:08 PM
    Author     : Zeeshan Waris
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
            <title>Parameters</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            
            <a4j:form id="Parameters">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{Parameters.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="#{Parameters.paramType}"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{Parameters.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="lblMsg" styleClass="error" value="#{Parameters.message}"/>
                        </h:panelGrid>
                         <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblareaOfExpertise" styleClass="label" value="Parameter Name"/>
                            <h:selectOneListbox id="ddbiltype" styleClass="ddlist" size="1" style="width: 134px" value="#{Parameters.parameterName}" disabled="#{Parameters.parameterNameDisable}">
                                <f:selectItems value="#{Parameters.parameterList}"/>
                                 <a4j:support action="#{Parameters.getDescriptionTableDetails}" event="onchange" ajaxSingle="true"
                                         reRender="label2,gridPanel103"  />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblemail" styleClass="label" value="Description"/>
                            <h:inputText id="txtemail" styleClass="input"  value="#{Parameters.description}"  maxlength="50" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblDefault" styleClass="label" />
                            <h:outputLabel id="lblDefault1" styleClass="label" />
                        </h:panelGrid>
                         <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:168px;">
                                <rich:contextMenu attached="false" id="menuCustomerDetails" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                    <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                                   actionListener="#{Parameters.fetchCurrentRow}">
                                        <a4j:actionparam name="Description" value="{Description}"/>
                                        <a4j:actionparam name="row" value="{currentRow}" />
                                    </rich:menuItem>
                                </rich:contextMenu>
                                <a4j:region>
                                    <rich:dataTable value="#{Parameters.instrReg}" var="item"
                                                    rowClasses="row1, row2" id="ListCustomerDetails" rows="5" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="5">
                                                    <h:outputText value="Description Table"/>
                                                </rich:column>
                                                <rich:column breakBefore="true" width="80%"><h:outputText value="Description"/></rich:column>
                                                <rich:column width="10%"><h:outputText value="Delete"/></rich:column>
                                                <rich:column width="10%"><h:outputText value="Update"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{item.description}"/></rich:column>
                                     
                                     <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" reRender="subbodyPanel" oncomplete="#{rich:component('deletePanel')}.show()">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{row}" target="#{Parameters.currentRow}" />
                                        <f:setPropertyActionListener value="#{item}" target="#{Parameters.currentItem}"/>
                                    </a4j:commandLink>
                                    <rich:toolTip for="deletelink" value="Delete" />
                                    </rich:column>
                                        <rich:column>
                                            <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{Parameters.selectParameterCurrentRow}" reRender="ddbiltype,leftPanel,btnupdate,btnSave,txtCode" focus="btnSave">
                                                <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{item}" target="#{Parameters.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{Parameters.currentRow}" />
                                            </a4j:commandLink>
                                            <rich:toolTip for="selectlink" value="Update" />
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller id="scroller20" align="left" for="ListCustomerDetails" maxPages="5" />
                                </a4j:region>
                            </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save" action="#{Parameters.saveParameterAction}" disabled="#{Parameters.saveDisable}"  reRender="lblMsg,leftPanel,gridPanel103"/>
                            <a4j:commandButton id="btnupdate" value="Update" action="#{Parameters.updateParameterAction}" disabled="#{Parameters.updateNameDisable}" reRender="lblMsg,leftPanel,gridPanel103"/>
                            <a4j:commandButton  id="btnrefresh" type="reset" value="Refresh" action="#{Parameters.refreshButtonAction}"  reRender="leftPanel,btnupdate,btnSave"/>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{Parameters.btnExit}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('Yes')}.focus()">
                    <f:facet name="header">
                        <h:outputText value="Confirmation DialogBox" />
                    </f:facet>

                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are You Sure To Delete ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:region id="yesBtn">
                                            <a4j:commandButton id="Yes" value="Yes" ajaxSingle="true" action="#{Parameters.deleteParamAction}"
                                                               onclick="#{rich:component('deletePanel')}.hide();" reRender="lblMsg,leftPanel,gridPanel103" />
                                        </a4j:region>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton id="No" value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
               </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>
