package gateconsole.modules.admin;

import gate.annotation.CopyIcon;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.base.Screen;
import gate.entity.Func;
import gate.entity.Role;
import gate.entity.User;
import gate.error.AppException;
import gate.io.URL;
import gate.util.Page;
import gateconsole.contol.FuncControl;
import gateconsole.contol.RoleControl;
import gateconsole.contol.UserControl;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
@Name("Funções")
@CopyIcon(Func.class)
public class FuncScreen extends gate.base.Screen
{

	private Func form;
	private List<Func> page;

	@Inject
	private FuncControl control;

	public String call()
	{
		page = control.search(getForm());
		return "/WEB-INF/views/gateconsole/modules/admin/Func/View.jsp";
	}

	public String callIndex()
	{
		page = control.search(getForm());
		return "/WEB-INF/index.html";
	}

	@Icon("select")
	@Name("Detalhe")
	public String callSelect()
	{
		try
		{
			form = control.select(getForm().getId());
			return "/WEB-INF/views/gateconsole/modules/admin/Func/ViewSelect.jsp";
		} catch (AppException e)
		{
			setMessages(e.getMessages());
			return call();
		}
	}

	@Name("Nova")
	@Icon("insert")
	public Object callInsert()
	{
		if (isPOST() && getMessages().isEmpty())
		{
			try
			{
				control.insert(getForm());
				return new URL("Gate")
					.setModule(getModule())
					.setScreen(getScreen())
					.setAction("Select")
					.setParameter("form.id", getForm().getId());
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}

		return "/WEB-INF/views/gateconsole/modules/admin/Func/ViewInsert.jsp";
	}

	@Icon("update")
	@Name("Alterar")
	public Object callUpdate()
	{
		if (isGET())
		{
			try
			{
				form = control.select(getForm().getId());
			} catch (AppException ex)
			{
				return "/WEB-INF/views/gateconsole/modules/admin/Func/ViewResult.jsp";
			}
		} else if (getMessages().isEmpty())
		{
			try
			{
				control.update(getForm());
				return new URL("Gate")
					.setModule(getModule())
					.setScreen(getScreen())
					.setAction("Select")
					.setParameter("form.id", getForm().getId());
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}
		return "/WEB-INF/views/gateconsole/modules/admin/Func/ViewUpdate.jsp";
	}

	@Icon("delete")
	@Name("Remover")
	public Object callDelete()
	{
		try
		{
			control.delete(getForm());
			return "/WEB-INF/views/gateconsole/modules/admin/Func/ViewResult.jsp";
		} catch (AppException ex)
		{
			return new URL("Gate")
				.setModule(getModule())
				.setScreen(getScreen())
				.setAction("Select")
				.setMessages(ex.getMessages())
				.setParameter("form.id", getForm().getId());
		}
	}

	public Func getForm()
	{
		if (form == null)
			form = new Func();
		return form;
	}

	public List<Func> getPage()
	{
		return page;
	}

	@RequestScoped
	@Name("Usuários")
	@CopyIcon(User.class)
	public static class UserScreen extends Screen
	{

		private Func func;
		private User user;
		private Page<User> page;

		@Inject
		private UserControl userControl;

		@Inject
		private UserControl.FuncControl control;

		public String call()
		{

			page = paginate(ordenate(control.search(func)));
			return "/WEB-INF/views/gateconsole/modules/admin/Func/User/View.jsp";
		}

		@Icon("insert")
		@Name("Adcionar")
		public Object callInsert()
		{

			try
			{
				control.insert(user, func);
				return new URL("Gate")
					.setModule(getModule())
					.setScreen(getScreen())
					.setParameter("func.id", getFunc().getId());
			} catch (AppException ex)
			{
				return new URL("Gate")
					.setModule(getModule())
					.setScreen(getScreen())
					.setMessages(ex.getMessages())
					.setParameter("func.id", getFunc().getId());
			}
		}

		@Icon("delete")
		@Name("Remover")
		public Object callDelete()
		{
			try
			{
				control.delete(user, func);
				return new URL("Gate")
					.setModule(getModule())
					.setScreen(getScreen())
					.setParameter("func.id", getFunc().getId());
			} catch (AppException ex)
			{
				return new URL("Gate")
					.setModule(getModule())
					.setScreen(getScreen())
					.setMessages(ex.getMessages())
					.setParameter("func.id", getFunc().getId());
			}
		}

		@Override
		public User getUser()
		{
			if (user == null)
				user = new User();
			return user;
		}

		public Func getFunc()
		{
			if (func == null)
				func = new Func();
			return func;
		}

		public Page<User> getPage()
		{
			return page;
		}

		public List<User> getUsers()
		{
			return userControl.search();
		}
	}

	@RequestScoped
	@Name("Perfis")
	@CopyIcon(Role.class)
	public static class RoleScreen extends Screen
	{

		private Func func;
		private Role role;
		private Page<Role> page;

		@Inject
		private RoleControl roleControl;

		@Inject
		private RoleControl.FuncControl control;

		public String call()
		{

			page = paginate(ordenate(control.search(func)));
			return "/WEB-INF/views/gateconsole/modules/admin/Func/Role/View.jsp";
		}

		@Icon("insert")
		@Name("Adcionar")
		public URL callInsert()
		{

			try
			{
				control.insert(role, func);
				return new URL("Gate")
					.setModule(getModule())
					.setScreen(getScreen())
					.setParameter("func.id", getFunc().getId());
			} catch (AppException ex)
			{
				return new URL("Gate")
					.setModule(getModule())
					.setScreen(getScreen())
					.setMessages(ex.getMessages())
					.setParameter("func.id", getFunc().getId());
			}
		}

		@Icon("delete")
		@Name("Remover")
		public URL callDelete()
		{

			try
			{
				control.delete(role, func);
				return new URL("Gate")
					.setModule(getModule())
					.setScreen(getScreen())
					.setParameter("func.id", getFunc().getId());
			} catch (AppException ex)
			{
				return new URL("Gate")
					.setModule(getModule())
					.setScreen(getScreen())
					.setMessages(ex.getMessages())
					.setParameter("func.id", getFunc().getId());
			}
		}

		public Role getRole()
		{
			if (role == null)
				role = new Role();
			return role;
		}

		public Func getFunc()
		{
			if (func == null)
				func = new Func();
			return func;
		}

		public Page<Role> getPage()
		{
			return page;
		}

		public List<Role> getRoles()
			throws AppException
		{
			return roleControl.search();
		}
	}
}
