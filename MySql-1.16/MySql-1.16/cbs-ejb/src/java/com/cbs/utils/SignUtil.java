package com.cbs.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import sun.security.pkcs11.SunPKCS11;
import sun.security.pkcs11.wrapper.PKCS11;
import sun.security.pkcs11.wrapper.PKCS11Exception;

public class SignUtil {

    static final Logger logger = LogManager.getLogger(SignUtil.class);
    //  private static final long TICKS_AT_EPOCH = 621355968000000000L;
    // private static final long TICKS_PER_MILLISECOND = 10000L;
    private String providerName = null;

    public SignUtil() {
        if (validate()) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private boolean validate() {
        return true;
    }

    public long getTicks(Date dt) {
        return dt.getTime() * 10000L + 621355968000000000L;
    }

    public void generateSignedFile(boolean fromToken, String tokenLib, String inFile, String outFile, String certFile, String password)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException, InvalidKeyException, NoSuchProviderException, SignatureException, OperatorCreationException, CMSException, ParserConfigurationException, TransformerException, PKCS11Exception {
        logger.debug("Reading input file: " + inFile);
        String xml = new String(Files.readAllBytes(Paths.get(inFile, new String[0])));
        String output = getSignedEnvelope(fromToken, tokenLib, xml, certFile, password);
        Files.write(Paths.get(outFile, new String[0]), output.getBytes(), new OpenOption[0]);
    }

    private PrivateKey getPrivateKey(KeyStore ks, String alias, String password)
            throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        logger.debug("Attempting to get private key for alias: " + alias);
        return (PrivateKey) ks.getKey(alias, password.toCharArray());
    }

    private X509Certificate getCertificate(KeyStore ks, String alias)
            throws KeyStoreException {
        logger.debug("Attempting to get the certificate for alias: " + alias);
        return (X509Certificate) ks.getCertificate(alias);
    }

    private String getSignData(boolean fromToken, String input, PrivateKey key, X509Certificate cert)
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException, CertificateEncodingException, OperatorCreationException, CMSException, IOException {
        PrivateKey privKey = key;
        List<X509Certificate> certList = new ArrayList();
        CMSTypedData msg = new CMSProcessableByteArray(input.getBytes());
        certList.add(cert);
        Store certs = new JcaCertStore(certList);
        CMSSignedDataGenerator gen = new CMSSignedDataGenerator();

        String prov = "BC";
        if (fromToken) {
            prov = this.providerName;
        }
        logger.debug("Building JcaContentSigner using provider: " + prov);
        ContentSigner sha256Signer = new JcaContentSignerBuilder(
                "SHA256WithRSA").setProvider(prov).build(privKey);
        gen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(
                new JcaDigestCalculatorProviderBuilder().setProvider("BC")
                .build()).build(sha256Signer, cert));
        gen.addCertificates(certs);
        logger.debug("Generating signature");
        CMSSignedData sigData = gen.generate(msg, false);

        return new String(Base64.encode(sigData.getEncoded()));
    }

    private String getPublicCert(X509Certificate cert)
            throws CertificateEncodingException {
        return new String(Base64.encode(cert.getEncoded()));
    }

    public String getEncodedData(String input) {
        byte[] bytes = input.getBytes();
        return new String(Base64.encode(bytes));
    }

    public String getSignedEnvelope(boolean fromToken, String tokenLib, String input, String certFile, String password)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException, InvalidKeyException, NoSuchProviderException, SignatureException, OperatorCreationException, CMSException, ParserConfigurationException, TransformerException, PKCS11Exception {
        if (!validate()) {
            return "";
        }
        if (fromToken) {
            logger.info("Loading provider using token library: " + tokenLib);
            loadProvider(tokenLib);
        }
        KeyStore ks = getKeyStore(fromToken, certFile, password);
        String alias = (String) ks.aliases().nextElement();

        PrivateKey pk = getPrivateKey(ks, alias, password);
        X509Certificate cert = getCertificate(ks, alias);
        String signData = getSignData(fromToken, input, pk, cert);
        String encContent = getEncodedData(input);
        String encCert = getPublicCert(cert);
        if (fromToken) {
            logger.debug("Unloading token provider");
            unloadProvider();
        }
        logger.debug("Building XMLDocument");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();

        Element root = document.createElement("Envelope");
        Element org = document.createElement("OrgContent");
        org.appendChild(document.createTextNode(encContent));
        Element sign = document.createElement("Signature");
        sign.appendChild(document.createTextNode(signData));
        Element cer = document.createElement("Certificate");
        cer.appendChild(document.createTextNode(encCert));
        root.appendChild(org);
        root.appendChild(sign);
        root.appendChild(cer);
        document.appendChild(root);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty("indent", "yes");
        StringWriter writer = new StringWriter();
        transformer
                .transform(new DOMSource(document), new StreamResult(writer));

        logger.info("Finished generating file data");
        return writer.getBuffer().toString();
    }

    private boolean verifySign(String orgContent, String signature, String certificate, boolean parseRaw)
            throws CertificateException, CMSException, OperatorCreationException, IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        logger.debug("Attemting to verify signature");

        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

        X509Certificate cert = null;
        cert = (X509Certificate) certFactory
                .generateCertificate(new ByteArrayInputStream(
                Base64.decode(certificate)));

        logger.debug("Certificate parsed: "
                + cert.getSubjectX500Principal().getName());
        if (parseRaw) {
            logger.debug("Parsing the signature block as raw RSA object");
            Signature sg = Signature.getInstance("SHA256withRSA");
            sg.initVerify(cert.getPublicKey());
            sg.update(Base64.decode(orgContent));
            if (sg.verify(Base64.decode(signature))) {
                logger.info("Signature verified");
                return true;
            }
        } else {
            logger.debug("Parsing the signature block as ASN.1 encoded object");
            CMSProcessableByteArray contentArray = new CMSProcessableByteArray(
                    Base64.decode(orgContent));
            byte[] signArray = Base64.decode(signature);
            logger.debug("Creating signed data");
            CMSSignedData signedData = new CMSSignedData(contentArray,
                    signArray);

            SignerInformationStore sis = signedData.getSignerInfos();
            Collection<?> c = sis.getSigners();
            Iterator<?> it = c.iterator();
            if (it.hasNext()) {
                SignerInformation signer = (SignerInformation) it.next();
                if (signer.verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(cert))) {
                    logger.info("Signature verified");
                    return true;
                }
            }
        }
        logger.info("Signature verification failed");
        return false;
    }

    public boolean parseSignedXml(String xmlString, boolean decodeContent, String decodedFileName, boolean parseRaw)
            throws ParserConfigurationException, SAXException, IOException, CMSException, CertificateException, OperatorCreationException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        if (!validate()) {
            return false;
        }
        logger.debug("Parsing signed XML");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new ByteArrayInputStream(xmlString
                .getBytes()));

        String orgContent = document.getElementsByTagName("OrgContent").item(0)
                .getTextContent();
        String signature = document.getElementsByTagName("Signature").item(0)
                .getTextContent();
        String certificate = document.getElementsByTagName("Certificate")
                .item(0).getTextContent();

        boolean verificationResult = verifySign(orgContent, signature,
                certificate, parseRaw);
        if ((decodeContent) && (verificationResult)) {
            logger.info("Writing decoded file");
            Files.write(Paths.get(decodedFileName, new String[0]), Base64.decode(orgContent), new OpenOption[0]);
        }
        return verificationResult;
    }

    public boolean parseSignedFile(String inFile, boolean decodeContent, String outFile, boolean parseRaw)
            throws IOException, CertificateException, OperatorCreationException, ParserConfigurationException, SAXException, CMSException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        logger.info("Attemting to verify signature for file: " + inFile);
        String xml = new String(Files.readAllBytes(Paths.get(inFile, new String[0])));
        return parseSignedXml(xml, decodeContent, outFile, parseRaw);
    }

    private KeyStore getKeyStore(boolean fromToken, String certFile, String password)
            throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        char[] pin = password.toCharArray();
        KeyStore tokenKeyStore = null;
        if (fromToken) {
            logger.debug("Attemting to load keystore from PKCS#11(token)");
            tokenKeyStore = KeyStore.getInstance("PKCS11");
            tokenKeyStore.load(null, pin);
        } else {
            logger.debug("Attemting to load keystore from pfx file");
            FileInputStream fis = new FileInputStream(certFile);
            tokenKeyStore = KeyStore.getInstance("pkcs12");
            tokenKeyStore.load(fis, pin);
        }
        return tokenKeyStore;
    }

    private X509Certificate getCertificate(KeyStore keyStore)
            throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
        X509Certificate cert = null;
        String alias = (String) keyStore.aliases().nextElement();
        logger.debug("Alias found in keystore: " + alias);
        cert = (X509Certificate) keyStore.getCertificate(alias);
        return cert;
    }

    public Provider loadProvider(String libraryName)
            throws PKCS11Exception, IOException {
        PKCS11 p11 = PKCS11.getInstance(libraryName, "C_GetFunctionList", null, false);
        long[] slots = p11.C_GetSlotList(true);
        if ((slots == null) || (slots.length <= 0)) {
            throw new IOException(
                    "No token inserted, or token not correctly detected");
        }
        long slot = slots[0];
        String pkcs11config = "name = eToken\nlibrary = " + libraryName
                + "\nslot = " + slot;
        logger.debug("PKCS#11 configuration in use: " + pkcs11config);

        byte[] pkcs11configBytes = pkcs11config.getBytes();
        ByteArrayInputStream configStream = new ByteArrayInputStream(
                pkcs11configBytes);
        Provider pkcs11Provider = new SunPKCS11(
                configStream);
        this.providerName = pkcs11Provider.getName();
        Security.addProvider(pkcs11Provider);
        String provProps = "";
        for (Enumeration<?> e = pkcs11Provider.keys(); e.hasMoreElements();) {
            provProps = provProps + "\t" + e.nextElement();
        }
        logger.debug("Listing properties for provider : " + this.providerName);
        logger.debug(provProps);

        return pkcs11Provider;
    }

    public void unloadProvider() {
        Security.removeProvider(this.providerName);
    }

    public boolean testToken(String tokenLib, String password)
            throws Exception {
        try {
            logger.info("Testing token accessibility for library: " + tokenLib);
            loadProvider(tokenLib);
            KeyStore ks = getKeyStore(true, null, password);
            getCertificate(ks);
            unloadProvider();
        } catch (Exception ex) {
            logger.error("Token error: " + ex.getMessage());
            logger.debug("Exception Details: ", ex);
            return false;
        }
        return true;
    }
}
