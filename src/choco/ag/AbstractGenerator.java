package choco.ag;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;

import choco.data.Article;
import choco.data.ArticleSet;

public class AbstractGenerator {
	public String GetAbstract(String article) throws IOException
	{
		String abs = Abstract.GetInstance().GetAbbreviation(Abstract.SIMPLE_ABBREVIATION, article);
		System.out.println(abs + "...");
		return null;
	}
	
	public static void main(String[] args){
		AbstractGenerator gen = new AbstractGenerator();
		try {
			ArticleSet as = gen.GetArticles();
			for(Article article : as.getSets())
			{
				gen.GetAbstract(article.getContent());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArticleSet GetArticles() throws FileNotFoundException, IOException, SAXException
	{
		Digester digester = new Digester();  
        digester.setValidating(false);
        digester.addObjectCreate("sets", ArticleSet.class); 
        digester.addObjectCreate("sets/article", Article.class); 
        digester.addBeanPropertySetter("sets/article/title", "title");
        digester.addBeanPropertySetter("sets/article/content", "content");
        digester.addSetNext("sets/article", "AddArticle");
        ArticleSet as = (ArticleSet)digester.parse(new FileInputStream("D:\\eclipse_workspace\\AbstractGenerator\\test\\article.xml"));
        return as;
	}
}
