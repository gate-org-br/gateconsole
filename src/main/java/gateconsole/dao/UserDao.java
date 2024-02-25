package gateconsole.dao;

import gate.base.Dao;
import gate.entity.Func;
import gate.entity.Role;
import gate.entity.User;
import gate.error.AppException;
import gate.error.ConstraintViolationException;
import gate.error.NotFoundException;
import gate.lang.json.JsonArray;
import gate.sql.Link;
import gate.sql.condition.Condition;
import gate.sql.delete.Delete;
import gate.sql.insert.Insert;
import gate.sql.select.Select;
import gate.sql.update.Update;
import gate.type.ID;
import gate.type.MD5;
import gate.type.mime.MimeData;
import java.time.LocalDateTime;
import java.util.List;

public class UserDao extends Dao
{

	public UserDao(Link link)
	{
		super(link);
	}

	public List<User> getSubscriptions()
	{
		return getLink()
			.from(getClass().getResource("UserDao/getSubscriptions().sql"))
			.parameters()
			.fetchEntityList(User.class);
	}

	public List<User> search()
	{
		return getLink().from(getClass().getResource("UserDao/search().sql"))
			.constant()
			.fetchEntityList(User.class);
	}

	public List<User> search(User filter)
	{
		return Select.of(getClass().getResource("UserDao/search(User).sql"))
			.where(Condition.TRUE
				.and("active").eq(filter.getActive())
				.and("name").lk(filter.getName())
				.and("username").eq(filter.getUsername())
				.and("email").eq(filter.getEmail())
				.and("CPF").eq(filter.getCPF())
				.and("Role$id").eq(filter.getRole().getId()))
			.build()
			.connect(getLink())
			.fetchEntityList(User.class);
	}

	public JsonArray search(String criteria)
	{
		return Select.of(getClass().getResource("UserDao/search(String).sql"))
			.where(Condition.FALSE
				.or("Uzer.username").eq(criteria)
				.or("Uzer.email").eq(criteria)
				.or("Role.rolename").eq(criteria)
				.or("Uzer.name").lk(criteria)
				.or("Role.name").lk(criteria))
			.build()
			.connect(getLink())
			.fetchJsonArray();
	}

	public List<User> search(Role role)
	{
		return getLink()
			.from("SELECT id, name FROM gate.Uzer WHERE Role$id = ?")
			.parameters(role.getId())
			.fetchEntityList(User.class);
	}

	public User select(ID id) throws AppException
	{
		return getLink().from(getClass().getResource("UserDao/select(ID).sql"))
			.parameters(id)
			.fetchEntity(User.class)
			.orElseThrow(NotFoundException::new);
	}

	public void insert(User value) throws AppException
	{

		getLink()
			.prepare(Insert.into("gate.Uzer")
				.set("active", value.getActive())
				.set("Role$id", value.getRole().getId())
				.set("name", value.getName())
				.set("username", value.getUsername())
				.set("password", MD5.digest(value.getPassword()))
				.set("sex", value.getSex())
				.set("CPF", value.getCPF())
				.set("birthdate", value.getBirthdate())
				.set("phone", value.getPhone())
				.set("cellPhone", value.getCellPhone())
				.set("email", value.getEmail())
				.set("description", value.getDescription())
				.set("registration", LocalDateTime.now())
				.set("code", value.getCode())
				.set("photo", value.getPhoto()))
			.fetchGeneratedKeys(ID.class)
			.forEach(value::setId);
	}

	public void update(User value) throws AppException
	{
		getLink().prepare(Update.table("Uzer")
			.set("active", value.getActive())
			.set("Role$id", value.getRole().getId())
			.set("name", value.getName())
			.set("username", value.getUsername())
			.set("email", value.getEmail())
			.set("description", value.getDescription())
			.set("phone", value.getPhone())
			.set("cellPhone", value.getCellPhone())
			.set("CPF", value.getCPF())
			.set("code", value.getCode())
			.set("birthdate", value.getBirthdate())
			.set("sex", value.getSex())
			.when(value.getPhoto() != null).set("photo", value.getPhoto())
			.where(Condition.of("id").eq(ID.class, value.getId())))
			.execute();
	}

	public MimeData getPhoto(ID id)
	{
		return getLink()
			.from("select photo from gate.Uzer where id = ?")
			.parameters(id)
			.fetchObject(MimeData.class)
			.orElse(null);
	}

	public void accept(User user, Role role) throws ConstraintViolationException
	{
		getLink()
			.prepare("update Uzer set Role$id = ? where id = ?")
			.parameters(role.getId(), user.getId())
			.execute();
	}

	public boolean delete(User... values) throws ConstraintViolationException
	{
		return getLink()
			.delete(User.class)
			.execute(values) >= 0;
	}

	public boolean setPasswd(User entity) throws ConstraintViolationException
	{
		return getLink()
			.prepare("update Uzer set password = MD5(username) where id = ?")
			.parameters(entity.getId())
			.execute() > 0;
	}

	public static class FuncDao extends Dao
	{

		public FuncDao(Link link)
		{
			super(link);
		}

		public List<User> search(Func func)
		{
			return getLink()
				.from(getClass().getResource("UserDao/FuncDao/search(Func).sql"))
				.parameters(func.getId())
				.fetchEntityList(User.class);
		}

		public void insert(User user, Func func) throws AppException
		{
			Insert.into("UzerFunc")
				.set("Uzer$id", user.getId())
				.set("Func$id", func.getId())
				.build().connect(getLink())
				.execute();
		}

		public void delete(User user, Func func) throws AppException
		{
			Delete.from("UzerFunc")
				.where(Condition.of("Uzer$id")
					.eq(ID.class, user.getId())
					.and("Func$id").eq(ID.class, func.getId()))
				.build().connect(getLink())
				.execute();
		}
	}
}
