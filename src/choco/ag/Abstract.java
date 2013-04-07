package choco.ag;

import java.io.IOException;
import java.io.StringReader;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import choco.ag.TranCharset.CodeType;

public class Abstract {
	public static final int SIMPLE_ABBREVIATION = 1;
	public static final int GEN_ABBREVIATION = 2;
	public static final int KWYWOR_ABBREVIATION = 3;
	
	private static final int MAX_ABBREV_LENGTH = 50;
	
	private static Abstract abs;
	
	public static synchronized Abstract GetInstance()
	{
		if(null == abs)
		{
			abs = new Abstract();
		}
		return abs;
	}
	
	public String GetAbbreviation(int type, String content) throws IOException
	{
		String ret = "";
		switch(type)
		{
		case Abstract.SIMPLE_ABBREVIATION:
			int truncateLen = Abstract.MAX_ABBREV_LENGTH;
			if(CodeType.ASCII != TranCharset.getEncoding(content))
			{
				truncateLen *= 2;
			}
			
			if(content.length() > Abstract.MAX_ABBREV_LENGTH)
			{
				content.replaceAll("\\s", "");
				ret = content.substring(0, truncateLen);
			}
			break;
		case Abstract.KWYWOR_ABBREVIATION:
			StringReader sr = new StringReader(content);
			IKSegmenter ik = new IKSegmenter(sr, true);
			Lexeme lex = null;
			while((lex = ik.next()) != null){
				if(lex.getLexemeType() == Lexeme.TYPE_CNWORD 
						|| lex.getLexemeType() == Lexeme.TYPE_CQUAN)
				{
					System.out.print(lex.getLexemeText() + "|");
				}
			}
			break;
		case Abstract.GEN_ABBREVIATION:
			break;
		default:
			break;	
		}		
		return ret;
	}
}
