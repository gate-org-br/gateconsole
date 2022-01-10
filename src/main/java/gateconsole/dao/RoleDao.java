package gateconsole.dao;

import gate.base.Dao;
import gate.entity.Func;
import gate.entity.Role;
import gate.error.AppException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.delete.Delete;
import gate.sql.insert.Insert;
import gate.sql.update.Update;
import gate.type.Hierarchy;
import gate.type.ID;
import java.util.List;

public class RoleDao extends Dao
{

	public RoleDao()
	{
		super("Gate");
	}

	public RoleDao(Link link)
	{
		super(link);
	}

	public List<Role> search() throws AppException
	{
		List<Role> roles = getLink()
			.from(getClass().getResource("RoleDao/search().sql"))
			.constant()
			.fetchEntityList(Role.class);
		Hierarchy.setup(roles);
		return roles;
	}

	public List<Role> search(Role role)
	{
		return getLink()
			.from(getClass().getResource("RoleDao/search(Role).sql"))
			.parameters(role.getId())
			.fetchEntityList(Role.class);
	}

	public Role select(ID id) throws NotFoundException
	{
		return getLink()
			.from(getClass().getResource("RoleDao/select(ID).sql"))
			.parameters(id)
			.fetchEntity(Role.class)
			.orElseThrow(NotFoundException::new);
	}

	public void insert(Role value) throws AppException
	{
		Insert.into("gate.Role")
			.set("active", value.getActive())
			.set("master", value.getMaster())
			.set("Role$id", value.getRole().getId())
			.set("rolename", value.getRolename())
			.set("name", value.getName())
			.set("email", value.getEmail())
			.set("description", value.getDescription())
			.set("Manager$id", value.getManager().getId())
			.build()
			.connect(getLink())
			.fetchGeneratedKey(ID.class)
			.ifPresent(value::setId);
	}

	public void update(Role value) throws AppException
	{
		if (Update.table("gate.Role")
			.set("active", value.getActive())
			.set("master", value.getMaster())
			.set("Role$id", value.getRole().getId())
			.set("rolename", value.getRolename())
			.set("name", value.getName())
			.set("email", value.getEmail())
			.set("description", value.getDescription())
			.set("Manager$id", value.getManager().getId())
			.where(Condition.of("id").eq(value.getId()))
			.build()
			.connect(getLink())
			.execute() == 0)
			throw new NotFoundException();
	}

	public void delete(Role value) throws AppException
	{
		if (Delete.from("gate.Role")
			.where(Condition.of("id").eq(value.getId()))
			.build()
			.connect(getLink())
			.execute() == 0)
			throw new NotFoundException();
	}

	public static class FuncDao extends Dao
	{

		public FuncDao(Link link)
		{
			super(link);
		}

		public List<Role> search(Func func)
		{
			return getLink()
				.from(getClass().getResource("RoleDao/FuncDao/search(Func).sql"))
				.parameters(func.getId())
				.fetchEntityList(Role.class);
		}

		public void insert(Role user, Func func) throws AppException
		{
			Insert.into("RoleFunc")
				.set("Role$id", user.getId())
				.set("Func$id", func.getId())
				.build().connect(getLink())
				.execute();
		}

		public void delete(Role user, Func func) throws AppException
		{
			Delete.from("RoleFunc")
				.where(Condition.of("Role$id")
					.eq(ID.class, user.getId())
					.and("Func$id").eq(ID.class, func.getId()))
				.build().connect(getLink())
				.execute();
		}
	}
}
