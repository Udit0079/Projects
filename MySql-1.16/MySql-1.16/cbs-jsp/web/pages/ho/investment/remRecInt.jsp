<%-- 
    Document   : remRecInt
    Created on : Jul 19, 2012, 10:51:49 AM
    Author     : sipl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Remove Rec. Interest</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>            
        </head>
        <body>
            <a4j:form id="RemRecIntForm">
                <h:panelGrid id="RemRecIntPanelGrid" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="RemRecIntGrpPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="RemRecIntPanel" layout="block">
                            <h:outputLabel id="RemRecIntDtLvl" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="RemRecIntDtOp" styleClass="output" value=" #{RemRecInt.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="RemRecIntNmLvl" styleClass="headerLabel" value="Remove Rec. Interest"/>
                        <h:panelGroup id="RemRecIntPanel1" layout="block">
                            <h:outputLabel id="RemRecIntUsrLvl" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="RemRecIntUsrOp" styleClass="output" value="#{RemRecInt.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="RemRecInt2" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{RemRecInt.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="RemRecInt3" columns="6" columnClasses="col2,col18,col18,col17,col3,col21" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row1" width="100%">
                        <h:outputLabel id="ctrlNo" styleClass="label" value="Control No"/>
                        <h:selectOneListbox id="ctrlNoDD" styleClass="ddlist" size="1" style="width: 80px" value="#{RemRecInt.ctrNoDd}"> 
                            <f:selectItems value="#{RemRecInt.ctrNoList}"/> 
                            <a4j:support action="#{RemRecInt.ctrNoLostFocus}" event="onblur" 
                                         reRender="stxtMsg,fdrTxt,purDtTxt,matDtTxt,fValTxt,bValTxt,sellerTxt,roiTxt,intOptTxt,brTxt,totAmt,recIntTxt"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>                    
                    </h:panelGrid>
                    <h:panelGrid id="RemRecInt4" columns="6" columnClasses="col2,col18,col18,col17,col3,col21" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row2" width="100%">
                        <h:outputLabel id="fdrNo" styleClass="label" value="FDR No"/>
                        <h:outputText id="fdrTxt" styleClass="label" value="#{RemRecInt.fdrVal}"/>
                        <h:outputLabel id="purDt" styleClass="label" value="Purchase Date"/>
                        <h:outputText id="purDtTxt" styleClass="label" value="#{RemRecInt.purDtVal}"/>
                        <h:outputLabel id="matDt" styleClass="label" value="Maturity Date"/>
                        <h:outputText id="matDtTxt" styleClass="label" value="#{RemRecInt.matDtVal}"/>                   
                    </h:panelGrid>                    
                    <h:panelGrid id="RemRecInt5" columns="6" columnClasses="col2,col18,col18,col17,col3,col21" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row1" width="100%">
                        <h:outputLabel id="fVal" styleClass="label" value="Face Value"/>
                        <h:outputText id="fValTxt" styleClass="label" value="#{RemRecInt.faceVal}"/>
                        <h:outputLabel id="bVal" styleClass="label" value="Maturity Value"/>
                        <h:outputText id="bValTxt" styleClass="label" value="#{RemRecInt.bookVal}"/>
                        <h:outputLabel id="sellerNm" styleClass="label" value="Seller Name"/>
                        <h:outputText id="sellerTxt" styleClass="label" value="#{RemRecInt.sellerNmVal}"/>                
                    </h:panelGrid>                    
                    <h:panelGrid id="RemRecInt6" columns="6" columnClasses="col2,col18,col18,col17,col3,col21" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row2" width="100%">
                        <h:outputLabel id="roi" styleClass="label" value="ROI"/>
                        <h:outputText id="roiTxt" styleClass="label" value="#{RemRecInt.roiVal}"/>
                        <h:outputLabel id="intOpt" styleClass="label" value="Int. Opt"/>
                        <h:outputText id="intOptTxt" styleClass="label" value="#{RemRecInt.intOptVal}"/>
                        <h:outputLabel id="branch" styleClass="label" value="Branch"/>
                        <h:outputText id="brTxt" styleClass="label" value="#{RemRecInt.brVal}"/>
                    </h:panelGrid>                    
                    <h:panelGrid id="RemRecInt7" columns="6" columnClasses="col2,col18,col18,col17,col3,col21" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row1" width="100%">
                        <h:outputLabel id="totIntAmt" styleClass="label" value="Total Rec Int Amt"/>
                        <h:outputText id="totAmt" styleClass="label" value="#{RemRecInt.totAmtVal}"/>
                        <h:outputLabel id="recIntAmt" styleClass="label" value="Rec Int. Amt"/>
                        <h:outputText id="recIntTxt" styleClass="label" value="#{RemRecInt.recIntVal}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>                    
                    </h:panelGrid>
                    <h:panelGrid id="frmFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="frmBtnPanel">
                            <a4j:commandButton id="btnClrInt" action="#{RemRecInt.clrRecInt}" value="Clear Rec Int" reRender="stxtMsg"/>
                            <a4j:commandButton id="btnRefresh" action="#{RemRecInt.ClearAll}" value="Refresh" reRender="stxtMsg,fdrTxt,purDtTxt,matDtTxt,fValTxt,bValTxt,sellerTxt,roiTxt,intOptTxt,brTxt,totAmt,recIntTxt"/>
                            <a4j:commandButton id="btnExit" action="#{RemRecInt.exitButton}" value="Exit"/>                        
                        </h:panelGroup>
                    </h:panelGrid>            
                </h:panelGrid>
            </a4j:form>        
        </body>
    </html>    
</f:view>