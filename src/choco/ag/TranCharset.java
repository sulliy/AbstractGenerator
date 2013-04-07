package choco.ag;

public class TranCharset {
    private static final String PRE_FIX_UTF = "&#x";
    private static final String POS_FIX_UTF = ";";

    public TranCharset() {
    }

    /**
     * Translate   charset   encoding   to   unicode
     *
     * @param sTemp charset   encoding   is   gb2312
     * @return charset   encoding   is   unicode
     */
    public static String XmlFormalize(String sTemp) {
        StringBuffer sb = new StringBuffer();

        if (sTemp == null || sTemp.equals("")) {
            return "";
        }
        String s = TranCharset.TranEncodeToGB(sTemp);
        for (int i = 0; i < s.length(); i++) {
            char cChar = s.charAt(i);
            if (TranCharset.isGB2312(cChar)) {
                sb.append(PRE_FIX_UTF);
                sb.append(Integer.toHexString(cChar));
                sb.append(POS_FIX_UTF);
            } else {
                switch ((int) cChar) {
                    case 32:
                        sb.append("&#32;");
                        break;
                    case 34:
                        sb.append("&quot;");
                        break;
                    case 38:
                        sb.append("&amp;");
                        break;
                    case 60:
                        sb.append("&lt;");
                        break;
                    case 62:
                        sb.append("&gt;");
                        break;
                    default:
                        sb.append(cChar);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将字符串编码格式转成GB2312
     *
     * @param str
     * @return
     */
    public static String TranEncodeToGB(String str) {
        try {
        	CodeType type = TranCharset.getEncoding(str);
        	String strEncode = "";
        	switch(type)
        	{
        	case GB2312:
        		strEncode = "GB2312";
        		break;
        	case ISO_8859_1:
        		strEncode = "ISO-8859-1";
        		break;
        	case UTF_8:
        		strEncode = "UTF-8";
        		break;
        	case GBK:
        		strEncode = "GBK";
        		break;
        	default:
        		break;
        	}           
            String temp = new String(str.getBytes(strEncode), "GB2312");
            return temp;
        } catch (java.io.IOException ex) {
            return null;
        }
    }
    
    /**
     * 将字符串编码格式转成UTF-8
     *
     * @param str
     * @return
     */
    public static String TranEncodeToUTF8(String str) {
        try {
        	CodeType type = TranCharset.getEncoding(str);
        	String strEncode = "";
        	switch(type)
        	{
        	case GB2312:
        		strEncode = "GB2312";
        		break;
        	case ISO_8859_1:
        		strEncode = "ISO-8859-1";
        		break;
        	case UTF_8:
        		strEncode = "UTF-8";
        		break;
        	case GBK:
        		strEncode = "GBK";
        		break;
        	default:
        		break;
        	}           
            String temp = new String(str.getBytes(strEncode), "UTF-8");
            return temp;
        } catch (java.io.IOException ex) {
            return null;
        }
    }

    /**
     * 判断输入字符是否为gb2312的编码格式
     *
     * @param c 输入字符
     * @return 如果是gb2312返回真，否则返回假
     */
    public static boolean isGB2312(char c) {
        Character ch = new Character(c);
        String sCh = ch.toString();
        try {
            byte[]   bb = sCh.getBytes("gb2312");
            if (bb.length > 1) {
                return true;
            }
        } catch (java.io.UnsupportedEncodingException ex) {
            return false;
        }
        return false;
    }

    /**
     * 判断字符串的编码
     *
     * @param str
     * @return
     */
    public static CodeType getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return CodeType.GB2312;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return CodeType.ISO_8859_1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return CodeType.UTF_8;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return CodeType.GBK;
            }
        } catch (Exception exception3) {
        }
        return CodeType.ASCII;
    }
    
    public enum CodeType
    {
    	ASCII,
    	GB2312,
    	/*
    	 * ISO-8859-1编码是单字节编码，向下兼容ASCII，其编码范围是0x00-0xFF，
    	 * 0x00-0x7F之间完全和ASCII一致，0x80-0x9F之间是控制字符，0xA0-0xFF之间是文字符号。
    	 */
    	ISO_8859_1,
    	/*
    	 * ASCII字符的UNICODE字符，将会编码成1个字节，并且UTF-8表示与ASCII字符表示是一样的。
    	 * 所有其他的UNICODE字符转化成UTF-8将需要至少2个字节。
    	 */
    	UTF_8,
    	GBK
    }
}


