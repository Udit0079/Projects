<%-- 
    Document   : CategoryAdd
    Created on : 25 Aug, 2011, 10:49:39 AM
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
            <title>Category Addition</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="CategoryAdd"/>
            <a4j:form id="CategoryAdd">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CategoryAdd.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Category Addition"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CategoryAdd.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="panel" columns="2" columnClasses="col9,col9" style="width:100%;">
                        <h:panelGrid id="leftPanel" style="width:100%;height:220px;" styleClass="row2">
                            <h:panelGrid columnClasses="col1,col9,col1,col2" columns="4" id="Row6" style="width:100%;text-align:left;">
                                <h:outputLabel id="lblArea" styleClass="label" value="Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtArea" styleClass="input" maxlength="40" value="#{CategoryAdd.category}" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();">
                                    </h:inputText>
                                    <h:outputLabel styleClass="label"/>
                                    <h:outputLabel styleClass="label"/>
                                </h:panelGrid>
                                <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;">
                                    <h:outputText id="lblMsg" styleClass="error" value="#{CategoryAdd.message}"/>
                                </h:panelGrid>   
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:168px;">
                                <a4j:region>
                                    <rich:dataTable value="#{CategoryAdd.maintenance}" var="item"
                                                    rowClasses="row1, row2" id="ListCustomerDetails" rows="5" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="6"><h:outputText value="Category Details"/></rich:column>
                                                <rich:column breakBefore="true" width="90%"><h:outputText value="Category"/></rich:column>
                                                <rich:column width="10%"><h:outputText value="Delete"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{item.category}"/></rich:column>
                                        <rich:column style="text-align:center;">
                                            <a4j:commandLink ajaxSingle="true" id="deletelink" reRender="subbodyPanel" oncomplete="#{rich:component('deletePanel')}.show()">
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{item}" target="#{CategoryAdd.currentItem}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller id="scroller20" align="left" for="ListCustomerDetails" maxPages="5" />
                                </a4j:region>
                            </h:panelGrid>
                        </h:panelGrid>        
                        <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="BtnPanel">
                                <a4j:commandButton id="btnSave" value="Save" action="#{CategoryAdd.saveBtnAction}"  reRender="lblMsg,mainPanel,gridPanel103"  focus="btnExit"/>
                                <a4j:commandButton  id="btnrefresh" type="reset" value="Refresh" action="#{CategoryAdd.btnRefresh}"  reRender="mainPanel,btnupdate,btnSave"/>
                                <a4j:commandButton id="btnExit" value="Exit"  action="#{CategoryAdd.btnExit}" reRender="mainPanel"/>
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
                                        <h:outputText value="Are You Sure To Delete This Category ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:region id="yesBtn">
                                            <a4j:commandButton id="Yes" value="Yes" ajaxSingle="true" action="#{CategoryAdd.deleteBtnAction}"
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


