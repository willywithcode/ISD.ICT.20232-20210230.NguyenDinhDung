package VNPaySubsystem;

import VNPaySubsystem.Utils.Validate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class Request {

	public String buildQueryURL(int amount, String contents, String currCode, String locale, String orderType) throws IllegalArgumentException, UnsupportedEncodingException {
		Validate.validateParameters(amount, contents, currCode, locale, orderType);
		String vnp_Version = Config.vnp_ver;
		String vnp_Command = "pay";
		String vnp_TxnRef = Config.getRandNum(8);
		String vnp_IpAddr = Config.getIp();
		String vnp_TmnCode = Config.vnp_Tmn;

		Map vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", currCode);
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_Locale", locale);
		vnp_Params.put("order_Type", orderType);
		vnp_Params.put("contents", contents);

		vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());

		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		//Build data to hash and querystring
		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				//Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				//Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();

        return queryUrl;
    }

}
