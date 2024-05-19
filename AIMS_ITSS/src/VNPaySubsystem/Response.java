package VNPaySubsystem;

import VNPaySubsystem.Utils.Validate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Response {

	private VNPaySubsystem.ResultDisplay resultDisplay;

	public VNPaySubsystem.PaymentTransaction buildTransaction(String returnUrl) throws URISyntaxException {
		PaymentTransaction transaction = new PaymentTransaction();
		if(!Validate.isValidURL(returnUrl)) throw new URISyntaxException("","");
		URI uri = new URI(returnUrl);
		String query = uri.getQuery();
		Map<String, String> parameters = new HashMap<>();

		if (query != null) {
			String[] pairs = query.split("&");
			for (String pair : pairs) {
				String[] keyValue = pair.split("=");
				if (keyValue.length == 2) {
					String key = keyValue[0];
					String value = keyValue[1];
					parameters.put(key, value);
				}
			}
		}

		transaction.setTransactionId(parameters.get("transactionId"));
		transaction.setTransactionTime(parameters.get("transactionTime"));
		transaction.setCustomerId(parameters.get("customerId"));
		transaction.setAmount(Integer.parseInt(parameters.get("amount")));
		transaction.setErrorMessage(parameters.get("errorMessage"));
		transaction.setContents(parameters.get("contents"));

        return transaction;
	}

	public String handleErrorCode(String errorCode) {
		String errorMessage;

		switch (errorCode) {
			case "00":
				errorMessage = "Giao dịch thành công";
				break;
			case "07":
				errorMessage = "Trừ tiền thành công. Giao dịch bị nghi ngờ (liên quan tới lừa đảo, giao dịch bất thường).";
				break;
			case "09":
				errorMessage = "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng chưa đăng ký dịch vụ InternetBanking tại ngân hàng.";
				break;
			case "10":
				errorMessage = "Giao dịch không thành công do: Khách hàng xác thực thông tin thẻ/tài khoản không đúng quá 3 lần";
				break;
			case "11":
				errorMessage = "Giao dịch không thành công do: Đã hết hạn chờ thanh toán. Xin quý khách vui lòng thực hiện lại giao dịch.";
				break;
			case "12":
				errorMessage = "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng bị khóa.";
				break;
			case "13":
				errorMessage = "Giao dịch không thành công do Quý khách nhập sai mật khẩu xác thực giao dịch (OTP). Xin quý khách vui lòng thực hiện lại giao dịch.";
				break;
			case "24":
				errorMessage = "Giao dịch không thành công do: Khách hàng hủy giao dịch";
				break;
			case "51":
				errorMessage = "Giao dịch không thành công do: Tài khoản của quý khách không đủ số dư để thực hiện giao dịch.";
				break;
			case "65":
				errorMessage = "Giao dịch không thành công do: Tài khoản của Quý khách đã vượt quá hạn mức giao dịch trong ngày.";
				break;
			case "75":
				errorMessage = "Ngân hàng thanh toán đang bảo trì.";
				break;
			case "79":
				errorMessage = "Giao dịch không thành công do: KH nhập sai mật khẩu thanh toán quá số lần quy định. Xin quý khách vui lòng thực hiện lại giao dịch.";
				break;
			case "99":
				errorMessage = "Các lỗi khác (lỗi còn lại, không có trong danh sách mã lỗi đã liệt kê)";
				break;
			default:
				errorMessage = "Mã lỗi không hợp lệ.";
				break;
		}
		return errorMessage;
	}

	public VNPaySubsystem.PaymentTransaction getTransaction(String returnUrl) throws URISyntaxException {
		URI uri = new URI(returnUrl);
		String query = uri.getQuery();
		Map<String, String> parameters = new HashMap<>();

		if (query != null) {
			String[] pairs = query.split("&");
			for (String pair : pairs) {
				String[] keyValue = pair.split("=");
				if (keyValue.length == 2) {
					String key = keyValue[0];
					String value = keyValue[1];
					parameters.put(key, value);
				}
			}
		}
		String responseCode = parameters.get("vnp_ResponseCode");
		String errorMsg = handleErrorCode(responseCode);
		PaymentTransaction transaction = buildTransaction(returnUrl);
		transaction.setErrorMessage(errorMsg);
		return transaction;
	}

}
