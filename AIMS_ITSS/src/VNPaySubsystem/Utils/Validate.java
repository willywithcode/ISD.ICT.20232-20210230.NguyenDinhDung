package VNPaySubsystem.Utils;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Validate {

    public static  void validateParameters(int amount, String contents, String currCode, String locale, String orderType) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        if (!isAsciiString(contents)) {
            throw new IllegalArgumentException("Contents must be an ASCII string.");
        }

        if (!isAsciiString(currCode)) {
            throw new IllegalArgumentException("Currency code must be an ASCII string.");
        }

        if (!isAsciiString(locale)) {
            throw new IllegalArgumentException("Locale must be an ASCII string.");
        }

        if (!isAsciiString(orderType)) {
            throw new IllegalArgumentException("Order type must be an ASCII string.");
        }
    }

    public static boolean isAsciiString(String str) {
        return str != null && StandardCharsets.US_ASCII.newEncoder().canEncode(str);
    }

    public static boolean isValidURL(String urlString) {
        try {
            new URL(urlString);
            return true;
        } catch ( MalformedURLException e) {
            return false;
        }
    }
}

