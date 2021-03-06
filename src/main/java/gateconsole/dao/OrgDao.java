package gateconsole.dao;

import gate.base.Dao;
import gate.entity.Org;
import gate.error.AppException;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.select.Select;
import gate.sql.update.Update;
import gate.type.mime.MimeData;
import java.util.Optional;

public class OrgDao extends Dao
{

	public OrgDao(Link link)
	{
		super(link);
	}

	public Optional<Org> select()
	{
		return getLink()
			.from(Select.from(Org.class)
				.properties("orgID", "name", "description", "authenticators", "sun", "mon", "tue", "wed", "fri", "thu", "sat")
				.where(Condition.TRUE))
			.fetchEntity(Org.class);
	}

	public void insert(Org entity) throws AppException
	{
		getLink()
			.insert(Org.class)
			.properties("orgID", "name", "description", "authenticators", "sun", "mon", "tue", "wed", "fri", "thu", "sat", "icon")
			.execute(entity);
	}

	public boolean update(Org entity) throws AppException
	{
		return getLink()
			.prepare(Update
				.type(Org.class)
				.set("orgID", "name", "description", "authenticators", "sun", "mon", "tue", "wed", "fri", "thu", "sat")
				.where(Condition.TRUE))
			.value(entity).execute() > 0;
	}

	public boolean update(MimeData icon) throws AppException
	{
		return getLink()
			.prepare("update Org set icon = ?")
			.parameters(icon)
			.execute() > 0;
	}

	public Object dwload()
	{
		return getLink().from("select icon from Org")
			.constant().fetchObject(MimeData.class)
			.orElse(new MimeData("text", "plain", "Sem logo cadastrado.txt".getBytes()));
	}
}
