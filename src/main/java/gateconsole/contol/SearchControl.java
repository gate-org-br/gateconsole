package gateconsole.contol;

import gate.annotation.DataSource;
import gate.base.Control;
import gate.error.AppException;
import gate.sql.Link;
import gate.sql.LinkSource;
import gateconsole.dao.SearchDao;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class SearchControl extends Control
{

	@Inject
	@DataSource("Gate")
	LinkSource linksource;

	public List<Object> search(String text) throws AppException
	{
		if (text == null || text.length() < 3)
			throw new AppException("O texto de busca deve conter pelo menos 3 caracteres.");
		try ( Link link = linksource.getLink();
			 SearchDao dao = new SearchDao(link))
		{
			return dao.search(text);
		}
	}
}
