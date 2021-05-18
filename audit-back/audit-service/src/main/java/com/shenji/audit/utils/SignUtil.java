package com.shenji.audit.utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.security.*;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.List;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/5/6 17:25
 */
@Component
@Slf4j
public class SignUtil {

    @Autowired
    private MinioUtil minioUtil;

    private KeyStore keyStore;
    private KeyPair keyPair;
    private Certificate certificate;
    private final String PFX_PASS = "catdogcola";
    private InputStream pfx;

    private void getKey() throws Exception {
        if(pfx == null) {
            pfx = minioUtil.getCer();
        }

        if(keyStore == null) {
            keyStore = KeyStore.getInstance("pkcs12", "BC");
            keyStore.load(pfx, PFX_PASS.toCharArray());
        }

        if(keyPair == null) {
            getKeyPair(pfx);
        }
    }

    public byte[] signPdf(InputStream inPdf, Long affairId, Long userId, String op, Date time, byte[] photo) throws Exception {

        log.info("==开始签名==");
        Security.addProvider(new BouncyCastleProvider());

        getKey();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        PdfReader reader = new PdfReader(inPdf);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);
        Rectangle _rect = PageSize.A4;
        Rectangle _cell = new Rectangle(_rect);

        ByteArrayOutputStream tmp_os = new ByteArrayOutputStream();
        createQr(affairId.toString(), 100, 100, tmp_os);
        Image QR_image = Image.getInstance(tmp_os.toByteArray());
        QR_image.setAbsolutePosition(_rect.getWidth() - 125, _rect.getHeight() - 125);
        PdfAnnotation pdfAnnotationQR = PdfAnnotation.createStamp(stamper.getWriter(), _cell, op, affairId.toString());
        PdfAppearance pdfAppearanceQR = PdfAppearance.createAppearance(stamper.getWriter(), _rect.getWidth(), _rect.getHeight());
        pdfAppearanceQR.addImage(QR_image, true);
        pdfAnnotationQR.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, pdfAppearanceQR);
        stamper.addAnnotation(pdfAnnotationQR, 1);

        if(photo != null) {
            Image sign_image = Image.getInstance(photo);
            sign_image.setAbsolutePosition(_rect.getWidth() - 150, 150);
            int pageNum = reader.getNumberOfPages();
            PdfAnnotation pdfAnnotation = PdfAnnotation.createStamp(stamper.getWriter(), _cell, op, userId.toString());
            PdfAppearance pdfAppearance = PdfAppearance.createAppearance(stamper.getWriter(), _rect.getWidth(), _rect.getHeight());
            pdfAppearance.addImage(sign_image, true);
            pdfAnnotation.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, pdfAppearance);
            stamper.addAnnotation(pdfAnnotation, pageNum);
        }

        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        appearance.setSignDate(calendar);
        appearance.setReason(op);

        ExternalSignature es = new PrivateKeySignature(privateKey, "SHA-256", "BC");
        ExternalDigest digest = new BouncyCastleDigest();

        MakeSignature.signDetached(appearance, digest, es, getChains(pfx), null, null,null, 0, MakeSignature.CryptoStandard.CMS);

        os.flush();
        os.close();
        log.info("==签名成功==");
        return os.toByteArray();
    }


    public Boolean verifySignatures(InputStream inPdf, InputStream cer) throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        if(certificate == null) {
            certificate = getPublicCertificate(cer);
        }
        ks.setCertificateEntry("cacert", certificate);
        PdfReader reader = new PdfReader(inPdf);
        AcroFields af = reader.getAcroFields();
        List<String> names = af.getSignatureNames();
        Boolean ok = true;
        for (String name : names) {
            PdfPKCS7 pk = af.verifySignature(name);
            Calendar cal = pk.getSignDate();
            Certificate[] pkc = pk.getCertificates();
            String reason = pk.getReason();
            List<VerificationException> errors = CertificateVerification.verifyCertificates(pkc, ks, null, cal);
        }
        return ok;
    }



    /**
     * 生成二维码
     *
     * @param content 二维码内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @param os 输出流
     * @throws Exception 异常
     */
    public void createQr(String content, int width, int height, OutputStream os) throws Exception {

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 1);
        BarcodeFormat format = BarcodeFormat.QR_CODE;
        BitMatrix matrix = new MultiFormatWriter().encode(content, format, width, height, hints);
        MatrixToImageConfig config = new MatrixToImageConfig(Color.black.getRGB(), Color.white.getRGB());
        BufferedImage qrcode = MatrixToImageWriter.toBufferedImage(matrix, config);
        ImageIO.write(qrcode, "png", os);
    }

    /**
     * 生成密钥对
     * @param pfx 密钥对
     * @throws Exception 异常
     */
    private void getKeyPair(InputStream pfx) throws Exception {
        if(keyStore == null) {
            keyStore = KeyStore.getInstance("pkcs12", "BC");
            keyStore.load(pfx, PFX_PASS.toCharArray());
        }
        String alias = (String)keyStore.aliases().nextElement();
        X509Certificate x509Certificate = (X509Certificate)keyStore.getCertificate(alias);
        PublicKey publicKey = x509Certificate.getPublicKey();
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, PFX_PASS.toCharArray());
        keyPair = new KeyPair(publicKey, privateKey);
    }

    private Certificate[] getChains(InputStream pfx) throws Exception {
        if(keyStore == null){
            keyStore = KeyStore.getInstance("pkcs12", "BC");
            keyStore.load(pfx, PFX_PASS.toCharArray());
        }
        String alias = (String)keyStore.aliases().nextElement();
        return keyStore.getCertificateChain(alias);
    }

    private Certificate getPublicCertificate(InputStream cer) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        return (X509Certificate)cf.generateCertificate(cer);
    }

    private String getContent(Long userId, String op, Date time) {
        return userId.toString() + "-|-" + Base64.getEncoder().encodeToString(op.getBytes(StandardCharsets.UTF_8)) + "-|-" + time.toString();
    }

    /**
     * sha1摘要加密
     * @param content 内容
     * @return 签名
     * @throws Exception 未知
     */
    private String getSha1Sign(String content) throws Exception {
        getKey();
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(keyPair.getPrivate());
        signature.update(contentBytes);
        byte[] signs = signature.sign();
        return Base64.getEncoder().encodeToString(signs);
    }

    /**
     * sha1摘要验证
     * @param content 内容
     * @param sign 签名
     * @return 是否有效
     * @throws Exception
     */
    private boolean verifyWhenSha1Sign(String content, String sign) throws Exception {
        getKey();
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(keyPair.getPublic());
        signature.update(contentBytes);
        return signature.verify(Base64.getDecoder().decode(sign));
    }
}
