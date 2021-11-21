package gateconsole.dao;

import gate.base.Dao;
import gate.entity.Auth;
import gate.error.AppException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.delete.Delete;
import gate.sql.insert.Insert;
import gate.sql.select.Select;
import gate.sql.update.Update;
import gate.type.ID;
import java.util.Collection;
import java.util.List;

public class AuthDao extends Dao
{

	public AuthDao()
	{
		super("Gate");
	}

	public AuthDao(Link link)
	{
		super(link);
	}

	public List<Auth> search()
	{
		return getLink()
			.from(getClass().getResource("AuthDao/search().sql"))
			.constant()
			.fetchEntityList(Auth.class);
	}

	public Collection<Auth> search(Auth filter)
	{
		return Select.of(getClass().getResource("AuthDao/search(Auth).sql"))
			.where(Condition.TRUE
				.and("Role$id").eq(filter.getRole().getId())
				.and("Uzer$id").eq(filter.getUser().getId())
				.and("Func$id").eq(filter.getFunc().getId()))
			.orderBy("access").and("scope").and("module").and("screen").and("action")
			.build()
			.connect(getLink())
			.fetchEntityList(Auth.class);
	}

	public Auth select(ID id) throws NotFoundException
	{
		return getLink()
			.from(getClass().getResource("AuthDao/select(ID).sql"))
			.parameters(id)
			.fetchEntity(Auth.class)
			.orElseThrow(NotFoundException::new);
	}

	public void insert(Auth value) throws AppException
	{
		Insert.into("Auth")
			.set("Role$id", value.getRole().getId())
			.set("Uzer$id", value.getUser().getId())
			.set("Func$id", value.getFunc().getId())
			.set("access", value.getAccess())
			.set("scope", value.getScope())
			.set("module", value.getModule())
			.set("screen", value.getScreen())
			.set("action", value.getAction())
			.build()
			.connect(getLink())
			.fetchGeneratedKey(ID.class)
			.ifPresent(value::setId);
	}

	public void update(Auth value) throws AppException
	{
		if (Update.table("Auth")
			.set("access", value.getAccess())
			.set("scope", value.getScope())
			.set("module", value.getModule())
			.set("screen", value.getScreen())
			.set("action", value.getAction())
			.where(Condition.of("id").eq(value.getId()))
			.build()
			.connect(getLink())
			.execute() == 0)
			throw new NotFoundException();
	}

	public void delete(Auth value) throws AppException
	{
		if (Delete.from("Auth")
			.where(Condition.of("id").eq(value.getId()))
			.build()
			.connect(getLink())
			.execute() == 0)
			throw new NotFoundException();

	}
}
