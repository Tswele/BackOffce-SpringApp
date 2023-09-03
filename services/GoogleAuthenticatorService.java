package za.co.wirecard.channel.backoffice.services;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.config.VaultConfig;
import za.co.wirecard.channel.backoffice.controllers.AuthenticationController;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserPasswordHistoryRepository;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Scanner;

@Service
public class GoogleAuthenticatorService {

    private static final Logger logger = LogManager.getLogger(GoogleAuthenticatorService.class);
    private final BackOfficeUserRepository backOfficeUserRepository;
    private final BackOfficeUserPasswordHistoryRepository backOfficeUserPasswordHistoryRepository;
    private final PlatformServices platformServices;
    private final VaultConfig vaultConfig;

    public GoogleAuthenticatorService(BackOfficeUserRepository backOfficeUserRepository, BackOfficeUserPasswordHistoryRepository backOfficeUserPasswordHistoryRepository, PlatformServices platformServices, VaultConfig vaultConfig) {
        this.backOfficeUserRepository = backOfficeUserRepository;
        this.backOfficeUserPasswordHistoryRepository = backOfficeUserPasswordHistoryRepository;
        this.platformServices = platformServices;
        this.vaultConfig = vaultConfig;
    }

    public String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    public String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    public String getGoogleAuthenticatorBarCode(String account, String secretKey) {
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode("AdumoOnline" + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode("AdumoOnline", "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public void createQRCode(String barCodeData, String filePath, int height, int width)
            throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
                width, height);
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);
        }
    }

    public void twoFactorAuth(String twoFaCode, long userId) {
        Scanner scanner = new Scanner(twoFaCode);
        String code = scanner.nextLine();
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne(userId);
        if (backOfficeUserEntity == null) {
            throw new GenericException("No user found with id | " + userId, HttpStatus.NOT_FOUND, "Unable to find user with that id");
        }
        logger.info("code | " + getTOTPCode(backOfficeUserEntity.getSecretKey()));
        //if (!code.equals(getTOTPCode(platformServices.decrypt(vaultConfig.getOtpSecretKey(), backOfficeUserEntity.getSecretKey())))) {
        if (!code.equals(getTOTPCode(backOfficeUserEntity.getSecretKey()))) {
            throw new GenericException("Invalid 2FA Code", HttpStatus.UNAUTHORIZED, "Invalid 2FA Code");
        }
    }

}
