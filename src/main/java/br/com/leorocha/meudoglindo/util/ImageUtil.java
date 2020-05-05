package br.com.leorocha.meudoglindo.util;

import java.util.Base64;

public class ImageUtil {
	public static byte[] stringToB64(String base64) {
		if(base64.contains(",")) {
			String[] split = base64.split(",");
			byte[] image = Base64.getDecoder().decode(split[1]);
			return image;
		} else {
			byte[] image = Base64.getDecoder().decode(base64);
			return image;
		}
    }
	public static String b64ToString(byte[] base64) {
		String text = Base64.getEncoder().encodeToString(base64);
		return text;
    }
}
