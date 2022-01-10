package gateconsole.contol;

import gate.annotation.DataSource;
import gate.base.Control;
import gate.entity.App;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.sql.LinkSource;
import gateconsole.dao.AppDao;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class AppControl extends Control
{

	@Inject
	@DataSource("Gate")
	LinkSource linksource;

	public List<App> search()
	{
		try ( Link link = linksource.getLink();
			 AppDao dao = new AppDao(link))
		{
			return dao.search();
		}
	}

	public App select(String id) throws NotFoundException
	{
		try ( Link link = linksource.getLink();
			 AppDao dao = new AppDao(link))
		{
			return dao.select(id);
		}
	}
}
