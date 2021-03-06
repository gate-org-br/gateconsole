package gateconsole.contol;

import gate.annotation.CopyInfo;
import gate.annotation.DataSource;
import gate.base.Control;
import gate.constraint.Constraints;
import gate.entity.Func;
import gate.entity.Role;
import gate.entity.User;
import gate.error.AppException;
import gate.sql.Link;
import gate.sql.LinkSource;
import gate.type.ID;
import gateconsole.dao.FuncDao;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@CopyInfo(User.class)
public class FuncControl extends Control
{

	@Inject
	@DataSource("Gate")
	LinkSource linksource;

	public List<Func> search()
	{
		try ( Link link = linksource.getLink();
			 FuncDao dao = new FuncDao(link))
		{
			return dao.search();
		}
	}

	public List<Func> search(Func filter)
	{
		try ( Link link = linksource.getLink();
			 FuncDao dao = new FuncDao(link))
		{
			return dao.search(filter);
		}
	}

	public Func select(ID id) throws AppException
	{
		try ( Link link = linksource.getLink();
			 FuncDao dao = new FuncDao(link))
		{
			return dao.select(id);
		}
	}

	public void insert(Func func) throws AppException
	{
		Constraints.validate(func, "name");

		try ( Link link = linksource.getLink();
			 FuncDao dao = new FuncDao(link))
		{
			dao.insert(func);
		}
	}

	public void update(Func func) throws AppException
	{
		Constraints.validate(func, "name");

		try ( Link link = linksource.getLink();
			 FuncDao dao = new FuncDao(link))
		{
			dao.update(func);
		}
	}

	public void delete(Func user) throws AppException
	{
		try ( Link link = linksource.getLink();
			 FuncDao dao = new FuncDao(link))
		{
			dao.delete(user);
		}
	}

	@Dependent
	public static class UserControl extends Control
	{

		@Inject
		@DataSource("Gate")
		LinkSource linksource;

		public List<Func> search(User user)
		{
			try ( Link link = linksource.getLink();
				 FuncDao.UserDao dao = new FuncDao.UserDao(link))
			{
				return dao.search(user);
			}
		}

		public void insert(Func func, User user) throws AppException
		{
			try ( Link link = linksource.getLink();
				 FuncDao.UserDao dao = new FuncDao.UserDao(link))
			{
				dao.insert(func, user);
			}
		}

		public void delete(Func func, User user) throws AppException
		{
			try ( Link link = linksource.getLink();
				 FuncDao.UserDao dao = new FuncDao.UserDao(link))
			{
				dao.delete(func, user);
			}
		}
	}

	@Dependent
	public static class RoleControl extends Control
	{

		@Inject
		@DataSource("Gate")
		LinkSource linksource;

		public List<Func> search(Role role)
		{
			try ( Link link = linksource.getLink();
				 FuncDao.RoleDao dao = new FuncDao.RoleDao(link))
			{
				return dao.search(role);
			}
		}

		public void insert(Func func, Role role) throws AppException
		{
			try ( Link link = linksource.getLink();
				 FuncDao.RoleDao dao = new FuncDao.RoleDao(link))
			{
				dao.insert(func, role);
			}
		}

		public void delete(Func func, Role role) throws AppException
		{
			try ( Link link = linksource.getLink();
				 FuncDao.RoleDao dao = new FuncDao.RoleDao(link))
			{
				dao.delete(func, role);
			}
		}
	}

}
