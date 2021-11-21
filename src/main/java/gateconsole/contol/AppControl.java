package gateconsole.contol;

import gate.base.Control;
import gate.entity.App;
import gate.error.NotFoundException;
import gate.sql.Link;
import gateconsole.dao.AppDao;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.sql.DataSource;

@Dependent
public class AppControl extends Control
{

	@Resource(lookup = "java:/comp/env/Gate")
	DataSource datasource;

	public List<App> search()
	{
		try ( Link link = new Link(datasource);
			 AppDao dao = new AppDao(link))
		{
			return dao.search();
		}
	}

	public App select(String id) throws NotFoundException
	{
		try ( Link link = new Link(datasource);
			 AppDao dao = new AppDao(link))
		{
			return dao.select(id);
		}
	}
}
