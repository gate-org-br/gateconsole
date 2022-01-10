package gateconsole.contol;

import gate.annotation.DataSource;
import gate.base.Control;
import gate.entity.Server;
import gate.error.AppException;
import gate.sql.Link;
import gate.sql.LinkSource;
import gateconsole.dao.ServerDao;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ServerControl extends Control
{

	@Inject
	@DataSource("Gate")
	LinkSource linksource;

	public Server select(Server.Type type)
	{
		try ( Link link = linksource.getLink();
			 ServerDao dao = new ServerDao(link))
		{
			return dao.select(type);
		}
	}

	public void update(Server server) throws AppException
	{
		if (server.getType() == null)
			throw new AppException("Especifique o tipo deo servidor");
		try ( Link link = linksource.getLink();
			 ServerDao dao = new ServerDao(link))
		{
			link.beginTran();
			dao.delete(server);
			dao.insert(server);
			link.commit();
		}
	}

}
