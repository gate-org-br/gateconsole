package gateconsole.contol;

import gate.Progress;
import gate.annotation.DataSource;
import gate.base.Control;
import gate.constraint.Constraints;
import gate.entity.Func;
import gate.entity.Role;
import gate.entity.User;
import gate.error.AppException;
import gate.error.ConstraintViolationException;
import gate.lang.json.JsonArray;
import gate.sql.Link;
import gate.sql.LinkSource;
import gate.type.ID;
import gate.type.mime.MimeData;
import gateconsole.dao.UserDao;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
public class UserControl extends Control
{

	@Inject
	@DataSource("Gate")
	LinkSource linksource;

	private static final int MAX_PHOTO_SIZE = 65535;

	public List<User> getSubscriptions()
	{
		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			return dao.getSubscriptions();
		}
	}

	@Produces
	@RequestScoped
	@Named("users")
	public List<User> search()
	{
		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			return dao.search();
		}
	}

	public List<User> search(User filter)
	{
		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			return dao.search(filter);
		}
	}

	public JsonArray search(String criteria)
	{
		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			return dao.search(criteria);
		}
	}

	public List<User> search(Role role)
	{
		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			return dao.search(role);
		}
	}

	public User select(ID id) throws AppException
	{
		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			return dao.select(id);
		}
	}

	public void insert(User value) throws AppException
	{
		Constraints.validate(value, "active", "username", "name", "email", "description", "phone", "cellPhone", "CPF", "code");

		if (value.getPhoto() != null && value.getPhoto().getSize() > MAX_PHOTO_SIZE)
			throw new AppException(String.format("Fotos devem possuir no máximo %d bytes", MAX_PHOTO_SIZE));
		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			if (value.getPassword() == null)
				value.setPassword(value.getUsername()).toString();
			dao.insert(value);
		}
	}

	public void insert(Role role, List<User> values) throws AppException
	{
		Progress.startup(values.size(), "Inserindo usuários");

		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			dao.getLink().beginTran();
			for (User value : values)
			{
				value.setRole(role);
				value.setActive(true);

				Constraints.validate(value, "username", "name", "email", "description", "phone", "cellPhone", "CPF", "code");

				if (value.getPhoto() != null && value.getPhoto().getSize() > MAX_PHOTO_SIZE)
					throw new AppException(String.format("Fotos devem possuir no máximo %d bytes", MAX_PHOTO_SIZE));

				if (value.getPassword() == null)
					value.setPassword(value.getUsername());

				try
				{
					dao.insert(value);
				} catch (ConstraintViolationException ex)
				{
					if (value.getEmail() == null)
						throw new AppException(String.format("%s: %s", ex.getMessage(), value.getUsername()), ex);
					else
						throw new AppException(String.format("%s: %s (%s)", ex.getMessage(), value.getUsername(), value.getEmail()), ex);
				}

				Progress.update(value.getName() + " inserido com sucesso");
			}
			dao.getLink().commit();
			Progress.commit("Todos os usuários foram inseridos com sucesso");
		}
	}

	public MimeData getPhoto(ID id)
	{
		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			return dao.getPhoto(id);
		}
	}

	public void update(User model) throws AppException
	{
		Constraints.validate(model, "active", "role.id",
			"username", "name", "email", "description", "phone",
			"cellPhone", "photo", "CPF");

		if (model.getPhoto() != null && model.getPhoto().getSize() > 65535)
			throw new AppException(String.format("Fotos devem possuir no máximo %d bytes", MAX_PHOTO_SIZE));
		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			dao.update(model);
		}
	}

	public void accept(User model, Role role) throws AppException
	{
		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			dao.accept(model, role);
		}
	}

	public void delete(User user) throws AppException
	{
		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			if (!dao.delete(user))
				throw new AppException("Tentativa de remover um USUÁRIO inexistente.");
		}
	}

	public void password(User user) throws AppException
	{
		try (Link link = linksource.getLink();
			UserDao dao = new UserDao(link))
		{
			if (!dao.setPasswd(user))
				throw new AppException("Tentativa de resetar a senha de um USUÁRIO inexistente.");
		}
	}

	@Dependent
	public static class FuncControl extends Control
	{

		@Inject
		@DataSource("Gate")
		LinkSource linksource;

		public List<User> search(Func func)
		{
			try (Link link = linksource.getLink();
				UserDao.FuncDao dao = new UserDao.FuncDao(link))
			{
				return dao.search(func);
			}
		}

		public User insert(User user, Func func) throws AppException
		{
			try (Link link = linksource.getLink();
				UserDao userDao = new UserDao(link);
				UserDao.FuncDao userFuncDao = new UserDao.FuncDao(link))
			{
				user = userDao.select(user.getId());
				userFuncDao.insert(user, func);
				return user;
			}

		}

		public void delete(User user, Func func) throws AppException
		{
			try (Link link = linksource.getLink();
				UserDao.FuncDao dao = new UserDao.FuncDao(link))
			{
				dao.delete(user, func);
			}
		}
	}
}
