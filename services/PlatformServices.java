package za.co.wirecard.channel.backoffice.services;

public interface PlatformServices {
    String decrypt(String key, String value);
    String encrypt(String key, String value);
}
