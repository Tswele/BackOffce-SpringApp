package za.co.wirecard.channel.backoffice.config;

public enum ApplicationConstants {

    // Url's

    // Errors
    S3_BUCKET_IMAGE_OBJECT_ERROR("Image was not sent correctly"),

    // BASE URL

    // Statics
    ADUMO_S3_BUCKET_NAME("wirecard-static");

    private final String value;

    ApplicationConstants(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
