//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.28 at 01:31:33 PM IST 
//


package com.cbs.ibl.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RespCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RespDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TrxnHistory">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Transaction" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BankReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ValueDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="TransactionDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="TrxnType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="PaymentNarration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Debit" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                             &lt;element name="Credit" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                             &lt;element name="AvailableBal" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "respCode",
    "respDesc",
    "trxnHistory"
})
@XmlRootElement(name = "CustomerStmtEnqResp")
public class CustomerStmtEnqResp {

    @XmlElement(name = "RespCode", required = true)
    protected String respCode;
    @XmlElement(name = "RespDesc", required = true)
    protected String respDesc;
    @XmlElement(name = "TrxnHistory", required = true)
    protected CustomerStmtEnqResp.TrxnHistory trxnHistory;

    /**
     * Gets the value of the respCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespCode() {
        return respCode;
    }

    /**
     * Sets the value of the respCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespCode(String value) {
        this.respCode = value;
    }

    /**
     * Gets the value of the respDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespDesc() {
        return respDesc;
    }

    /**
     * Sets the value of the respDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespDesc(String value) {
        this.respDesc = value;
    }

    /**
     * Gets the value of the trxnHistory property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerStmtEnqResp.TrxnHistory }
     *     
     */
    public CustomerStmtEnqResp.TrxnHistory getTrxnHistory() {
        return trxnHistory;
    }

    /**
     * Sets the value of the trxnHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerStmtEnqResp.TrxnHistory }
     *     
     */
    public void setTrxnHistory(CustomerStmtEnqResp.TrxnHistory value) {
        this.trxnHistory = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Transaction" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BankReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ValueDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="TransactionDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="TrxnType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="PaymentNarration" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Debit" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                   &lt;element name="Credit" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                   &lt;element name="AvailableBal" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "transaction"
    })
    public static class TrxnHistory {

        @XmlElement(name = "Transaction")
        protected List<CustomerStmtEnqResp.TrxnHistory.Transaction> transaction;

        /**
         * Gets the value of the transaction property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the transaction property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTransaction().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CustomerStmtEnqResp.TrxnHistory.Transaction }
         * 
         * 
         */
        public List<CustomerStmtEnqResp.TrxnHistory.Transaction> getTransaction() {
            if (transaction == null) {
                transaction = new ArrayList<CustomerStmtEnqResp.TrxnHistory.Transaction>();
            }
            return this.transaction;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="BankReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ValueDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="TransactionDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="TrxnType" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="PaymentNarration" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Debit" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *         &lt;element name="Credit" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *         &lt;element name="AvailableBal" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "bankReference",
            "valueDate",
            "transactionDate",
            "trxnType",
            "paymentNarration",
            "debit",
            "credit",
            "availableBal"
        })
        public static class Transaction {

            @XmlElement(name = "BankReference", required = true)
            protected String bankReference;
            @XmlElement(name = "ValueDate", required = true)
            protected String valueDate;
            @XmlElement(name = "TransactionDate", required = true)
            protected String transactionDate;
            @XmlElement(name = "TrxnType", required = true)
            protected String trxnType;
            @XmlElement(name = "PaymentNarration", required = true)
            protected String paymentNarration;
            @XmlElement(name = "Debit")
            protected float debit;
            @XmlElement(name = "Credit")
            protected float credit;
            @XmlElement(name = "AvailableBal")
            protected float availableBal;

            /**
             * Gets the value of the bankReference property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBankReference() {
                return bankReference;
            }

            /**
             * Sets the value of the bankReference property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBankReference(String value) {
                this.bankReference = value;
            }

            /**
             * Gets the value of the valueDate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValueDate() {
                return valueDate;
            }

            /**
             * Sets the value of the valueDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValueDate(String value) {
                this.valueDate = value;
            }

            /**
             * Gets the value of the transactionDate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTransactionDate() {
                return transactionDate;
            }

            /**
             * Sets the value of the transactionDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTransactionDate(String value) {
                this.transactionDate = value;
            }

            /**
             * Gets the value of the trxnType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTrxnType() {
                return trxnType;
            }

            /**
             * Sets the value of the trxnType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTrxnType(String value) {
                this.trxnType = value;
            }

            /**
             * Gets the value of the paymentNarration property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPaymentNarration() {
                return paymentNarration;
            }

            /**
             * Sets the value of the paymentNarration property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPaymentNarration(String value) {
                this.paymentNarration = value;
            }

            /**
             * Gets the value of the debit property.
             * 
             */
            public float getDebit() {
                return debit;
            }

            /**
             * Sets the value of the debit property.
             * 
             */
            public void setDebit(float value) {
                this.debit = value;
            }

            /**
             * Gets the value of the credit property.
             * 
             */
            public float getCredit() {
                return credit;
            }

            /**
             * Sets the value of the credit property.
             * 
             */
            public void setCredit(float value) {
                this.credit = value;
            }

            /**
             * Gets the value of the availableBal property.
             * 
             */
            public float getAvailableBal() {
                return availableBal;
            }

            /**
             * Sets the value of the availableBal property.
             * 
             */
            public void setAvailableBal(float value) {
                this.availableBal = value;
            }

        }

    }

}
