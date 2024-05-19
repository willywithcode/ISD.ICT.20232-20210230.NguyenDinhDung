package Test.VNPaySubsystem;

import VNPaySubsystem.Request;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
public class RequestTest {
    @Test
    public void testBuildQueryURLWithValidInputs() {
        Request request = new Request();
        try {
            String queryUrl = request.buildQueryURL(10000, "payment for order", "VND", "vn", "billpayment");
            assertNotNull(queryUrl);
            assertTrue(queryUrl.contains("vnp_Amount=10000"));
            assertTrue(queryUrl.contains("vnp_CurrCode=VND"));
            assertTrue(queryUrl.contains("vnp_Locale=vn"));
            assertTrue(queryUrl.contains("vnp_Command=pay"));
        } catch (Exception e) {
            fail("UnsupportedEncodingException should not have been thrown");
        }
    }

    @Test
    public void testBuildQueryURLThrowsExceptionForInvalidEncodingInAmount() {
        Request request = new Request();
        assertThrows(IllegalArgumentException.class, () -> {
            request.buildQueryURL(-123, "payment for order", "VND", "vn", "asd");
        });
    }

    @Test
    public void testBuildQueryURLThrowsExceptionForInvalidEncoding() {
        Request request = new Request();
        assertThrows(IllegalArgumentException.class, () -> {
            request.buildQueryURL(10000, "payment for order", "VND", "vn", "\uD83D\uDCB0");
        });
    }

    @Test
    public void testBuildQueryURLThrowsExceptionForInvalidEncodingInContents() {
        Request request = new Request();
        assertThrows(IllegalArgumentException.class, () -> {
            request.buildQueryURL(10000, "payment for order\uD83D\uDCB0", "VND", "vn", "billpayment");
        });
    }

    @Test
    public void testBuildQueryURLThrowsExceptionForInvalidEncodingInLocale() {
        Request request = new Request();
        assertThrows(IllegalArgumentException.class, () -> {
            request.buildQueryURL(10000, "payment for order", "VND", "vn\uD83D\uDCB0", "billpayment");
        });
    }

    @Test
    public void testBuildQueryURLThrowsExceptionForInvalidEncodingInOrderType() {
        Request request = new Request();
        assertThrows(IllegalArgumentException.class, () -> {
            request.buildQueryURL(10000, "payment for order", "VND", "vn", "billpayment\uD83D\uDCB0");
        });
    }
}
