package Test.VNPaySubsystem;

import VNPaySubsystem.PaymentTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import VNPaySubsystem.Response;

import java.net.URISyntaxException;

public class ResponseTest {

    @Test
    public void testBuildTransaction_URISyntaxException() {
        String returnUrl = "htt:/234/example.com/returnUrl?transactionId=123456&transactionTime=2024-05-19&customerId=ABC123&amount=100&errorMessage=Payment+failed&contents=Some+contents";

        Assertions.assertThrows(URISyntaxException.class, () -> {
            Response response = new Response();
            PaymentTransaction transaction = response.buildTransaction(returnUrl);
        });
    }

    @Test
    public void testBuildTransaction_NumberException() {
        String returnUrl = "https://example.com/returnUrl?transactionId=123456&transactionTime=2024-05-19&customerId=ABC123&amount=invalid&errorMessage=Payment+failed&contents=Some+contents";

        Assertions.assertThrows(NumberFormatException.class, () -> {
            Response response = new Response();
            PaymentTransaction transaction = response.buildTransaction(returnUrl);
        });
    }

    @Test
    public void testBuildTransaction() throws URISyntaxException {
        String returnUrl = "https://example.com/returnUrl?transactionId=123456&transactionTime=2024-05-19&customerId=ABC123&amount=100&errorMessage=Payment+failed&contents=Some+contents";
        Response response = new Response();
        PaymentTransaction transaction = response.buildTransaction(returnUrl);

        Assertions.assertEquals("123456", transaction.getTransactionId());
        Assertions.assertEquals("2024-05-19", transaction.getTransactionTime());
        Assertions.assertEquals("ABC123", transaction.getCustomerId());
        Assertions.assertEquals(100, transaction.getAmount());
        Assertions.assertEquals("Payment+failed", transaction.getErrorMessage());
        Assertions.assertEquals("Some+contents", transaction.getContents());
    }

    @Test
    public void testHandleErrorCode_Success() {
        // Arrange
        Response response = new Response();
        String errorCode = "00";
        String expectedErrorMessage = "Giao dịch thành công";

        // Act
        String actualErrorMessage = response.handleErrorCode(errorCode);

        // Assert
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testHandleErrorCode_TrustIssue() {
        // Arrange
        Response response = new Response();
        String errorCode = "07";
        String expectedErrorMessage = "Trừ tiền thành công. Giao dịch bị nghi ngờ (liên quan tới lừa đảo, giao dịch bất thường).";

        // Act
        String actualErrorMessage = response.handleErrorCode(errorCode);

        // Assert
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testHandleErrorCode_UnregisteredAccount() {
        // Arrange
        Response response = new Response();
        String errorCode = "09";
        String expectedErrorMessage = "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng chưa đăng ký dịch vụ InternetBanking tại ngân hàng.";

        // Act
        String actualErrorMessage = response.handleErrorCode(errorCode);

        // Assert
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testHandleErrorCode_MaxAuthAttemptsExceeded() {
        // Arrange
        Response response = new Response();
        String errorCode = "10";
        String expectedErrorMessage = "Giao dịch không thành công do: Khách hàng xác thực thông tin thẻ/tài khoản không đúng quá 3 lần";

        // Act
        String actualErrorMessage = response.handleErrorCode(errorCode);

        // Assert
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testHandleErrorCode_PaymentExpired() {
        // Arrange
        Response response = new Response();
        String errorCode = "11";
        String expectedErrorMessage = "Giao dịch không thành công do: Đã hết hạn chờ thanh toán. Xin quý khách vui lòng thực hiện lại giao dịch.";

        // Act
        String actualErrorMessage = response.handleErrorCode(errorCode);

        // Assert
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testHandleErrorCode_AccountBlocked() {
        // Arrange
        Response response = new Response();
        String errorCode = "12";
        String expectedErrorMessage = "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng bị khóa.";

        // Act
        String actualErrorMessage = response.handleErrorCode(errorCode);

        // Assert
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testGetErrorMessageWithInvalidErrorCode() {
        Response response = new Response();
        String errorCode = "99";
        String expectedErrorMessage = "Các lỗi khác (lỗi còn lại, không có trong danh sách mã lỗi đã liệt kê)";
        String actualErrorMessage = response.handleErrorCode(errorCode);
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testHandleErrorCode_InvalidOTP() {
        // Arrange
        Response response = new Response();
        String errorCode = "13";
        String expectedErrorMessage = "Giao dịch không thành công do Quý khách nhập sai mật khẩu xác thực giao dịch (OTP). Xin quý khách vui lòng thực hiện lại giao dịch.";

        // Act
        String actualErrorMessage = response.handleErrorCode(errorCode);

        // Assert
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testHandleErrorCode_CancelledTransaction() {
        // Arrange
        Response response = new Response();
        String errorCode = "24";
        String expectedErrorMessage = "Giao dịch không thành công do: Khách hàng hủy giao dịch";

        // Act
        String actualErrorMessage = response.handleErrorCode(errorCode);

        // Assert
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testHandleErrorCode_InsufficientBalance() {
        // Arrange
        Response response = new Response();
        String errorCode = "51";
        String expectedErrorMessage = "Giao dịch không thành công do: Tài khoản của quý khách không đủ số dư để thực hiện giao dịch.";

        // Act
        String actualErrorMessage = response.handleErrorCode(errorCode);

        // Assert
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testHandleErrorCode_ExceededDailyLimit() {
        // Arrange
        Response response = new Response();
        String errorCode = "65";
        String expectedErrorMessage = "Giao dịch không thành công do: Tài khoản của Quý khách đã vượt quá hạn mức giao dịch trong ngày.";

        // Act
        String actualErrorMessage = response.handleErrorCode(errorCode);

        // Assert
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testHandleErrorCode_PaymentMaintenance() {
        // Arrange
        Response response = new Response();
        String errorCode = "75";
        String expectedErrorMessage = "Ngân hàng thanh toán đang bảo trì.";

        // Act
        String actualErrorMessage = response.handleErrorCode(errorCode);

        // Assert
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testHandleErrorCode_ExceededPaymentAttempts() {
        // Arrange
        Response response = new Response();
        String errorCode = "79";
        String expectedErrorMessage = "Giao dịch không thành công do: KH nhập sai mật khẩu thanh toán quá số lần quy định. Xin quý khách vui lòng thực hiện lại giao dịch.";

        // Act
        String actualErrorMessage = response.handleErrorCode(errorCode);

        // Assert
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    public void testGetErrorMessageWithUnknownErrorCode() {
        Response response = new Response();
        String errorCode = "123";
        String expectedErrorMessage = "Mã lỗi không hợp lệ.";
        String actualErrorMessage = response.handleErrorCode(errorCode);

        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }
}
