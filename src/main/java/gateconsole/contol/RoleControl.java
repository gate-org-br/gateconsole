package gateconsole.contol;

import gate.annotation.DataSource;
import gate.base.Control;
import gate.constraint.Constraints;
import gate.entity.Auth;
import gate.entity.Bond;
import gate.entity.Func;
import gate.entity.Role;
import gate.entity.User;
import gate.error.AppException;
import gate.sql.Link;
import gate.sql.LinkSource;
import gate.type.ID;
import gateconsole.dao.AuthDao;
import gateconsole.dao.BondDao;
import gateconsole.dao.RoleDao;
import gateconsole.dao.UserDao;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class RoleControl extends Control
{

	@Inject
	@DataSource("Gate")
	LinkSource linksource;

	public List<Role> search() throws AppException
	{
		try (Link link = linksource.getLink();
			RoleDao roleDao = new RoleDao(link);
			UserDao userDao = new UserDao(link);
			BondDao bondDao = new BondDao(link);
			AuthDao authDao = new AuthDao(link))
		{
			List<Role> roles = roleDao.search();
			List<User> users = userDao.search();
			List<Auth> auths = authDao.search();
			List<Bond> bonds = bondDao.search();

			roles.forEach(role -> role.setAuths(auths.stream().filter(auth -> role.equals(auth.getRole())).collect(Collectors.toList())));
			users.forEach(user -> user.setAuths(auths.stream().filter(auth -> user.equals(auth.getUser())).collect(Collectors.toList())));
			bonds.stream().map(Bond::getFunc).forEach(func -> func.setAuths(auths.stream().filter(auth -> func.equals(auth.getFunc())).collect(Collectors.toList())));

			roles.forEach(role -> role.setFuncs(bonds.stream().filter(bond -> role.equals(bond.getRole())).map(Bond::getFunc).collect(Collectors.toList())));
			users.forEach(user -> user.setFuncs(bonds.stream().filter(bond -> user.equals(bond.getUser())).map(Bond::getFunc).collect(Collectors.toList())));

			roles.forEach(role -> role.setUsers(users.stream().filter(user -> role.equals(user.getRole())).collect(Collectors.toList())));

			roles.removeIf(e -> e.getRole().getId() != null);
			return roles;
		}
	}

	public List<Role> search(Role role)
	{
		try (Link link = linksource.getLink();
			RoleDao dao = new RoleDao(link))
		{
			return dao.search(role);
		}
	}

	public Role select(ID id) throws AppException
	{
		try (Link link = linksource.getLink();
			RoleDao dao = new RoleDao(link))
		{
			return dao.select(id);
		}
	}

	public void insert(Role role) throws AppException
	{
		Constraints.validate(role, "master", "active", "name", "email", "description", "rolename");
		try (Link link = linksource.getLink();
			RoleDao dao = new RoleDao(link))
		{
			dao.insert(role);
		}
	}

	public void update(Role role) throws AppException
	{
		Constraints.validate(role, "master", "active",
			"name", "email", "description", "rolename");

		try (Link link = linksource.getLink();
			RoleDao dao = new RoleDao(link))
		{
			dao.beginTran();

			dao.update(role);

			List<Role> roles = dao.search();
			if (roles.stream().anyMatch(e
				-> Boolean.TRUE.equals(e.getMaster())
				&& e.getParent().getId() != null
				&& !Boolean.TRUE.equals(e.getParent().getMaster())))
				throw new AppException("Tentativa de inserir um perfil master dentro de um perfil não master.");

			dao.commit();
		}
	}

	public void delete(Role role) throws AppException
	{
		try (Link link = linksource.getLink();
			RoleDao dao = new RoleDao(link))
		{
			dao.delete(role);
		}
	}

	@Dependent
	public static class FuncControl extends Control
	{

		@Inject
		@DataSource("Gate")
		LinkSource linksource;

		public List<Role> search(Func func)
		{
			try (Link link = linksource.getLink();
				RoleDao.FuncDao dao = new RoleDao.FuncDao(link))
			{
				return dao.search(func);
			}
		}

		public void insert(Role role, Func func) throws AppException
		{
			try (Link link = linksource.getLink();
				RoleDao.FuncDao dao = new RoleDao.FuncDao(link))
			{
				dao.insert(role, func);
			}

		}

		public void delete(Role role, Func func) throws AppException
		{
			try (Link link = linksource.getLink();
				RoleDao.FuncDao dao = new RoleDao.FuncDao(link))
			{
				dao.delete(role, func);
			}
		}
	}
}
