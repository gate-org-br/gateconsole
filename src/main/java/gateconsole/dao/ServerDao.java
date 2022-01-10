package gateconsole.dao;

import gate.base.Dao;
import gate.entity.Server;
import gate.error.AppException;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.delete.Delete;
import gate.sql.insert.Insert;
import gate.sql.select.Select;

public class ServerDao extends Dao
{

	public ServerDao(Link link)
	{
		super(link);
	}

	public Server select(Server.Type type)
	{
		return Select.expression("type")
			.expression("host")
			.expression("port")
			.expression("username")
			.expression("password")
			.from("Server")
			.where(Condition.of("type").eq(type))
			.build()
			.connect(getLink())
			.fetchEntity(Server.class)
			.orElseGet(() -> new Server(type));
	}

	public void insert(Server server) throws AppException
	{
		Insert.into("Server")
			.set("type", server.getType())
			.set("host", server.getHost())
			.set("port", server.getPort())
			.set("username", server.getUsername())
			.set("password", server.getPassword())
			.build()
			.connect(getLink())
			.execute();
	}

	public void delete(Server server) throws AppException
	{
		Delete.from("Server")
			.where(Condition.of("type").eq(server.getType()))
			.build()
			.connect(getLink())
			.execute();
	}
}
