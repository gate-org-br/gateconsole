package gateconsole.dao;

import gate.base.Dao;
import gate.entity.App;
import gate.error.NotFoundException;
import gate.sql.Link;
import java.util.List;

public class AppDao extends Dao
{

	public AppDao(Link link)
	{
		super(link);
	}

	public List<App> search()
	{
		return getLink()
			.from("SELECT json FROM gate.App")
			.constant()
			.fetchObjectList(App.class);
	}

	public App select(String id) throws NotFoundException
	{
		return getLink()
			.from("SELECT json FROM gate.App WHERE id = ?")
			.parameters(id)
			.fetchObject(App.class)
			.orElseThrow(NotFoundException::new);
	}

}
