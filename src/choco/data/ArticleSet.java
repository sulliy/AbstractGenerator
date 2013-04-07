package choco.data;

import java.util.ArrayList;
import java.util.List;

public class ArticleSet {
	private List<Article> sets = new ArrayList<Article>();
	
	public void AddArticle(Article article)
	{
		sets.add(article);
	}

	public List<Article> getSets() {
		return sets;
	}

	public void setSets(List<Article> sets) {
		this.sets = sets;
	}
	
	
}
