import java.security.MessageDigest;

import org.springframework.security.crypto.codec.Utf8;

public class Test {

	public static void main(String[] args) {
		String password = "admin";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] digest = messageDigest.digest(Utf8.encode(password));
			System.out
					.println("|"
							+ Utf8.decode(org.springframework.security.crypto.codec.Base64
									.encode(digest)) + "|");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
