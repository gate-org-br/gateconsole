package gateconsole.modules.admin;

import gate.annotation.Color;
import gate.annotation.Confirm;
import gate.annotation.CopyIcon;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.base.Screen;
import gate.command.Command;
import gate.entity.Func;
import gate.entity.Role;
import gate.entity.User;
import gate.error.AppException;
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
	FuncControl control;

	public String call()
	{
		page = control.search(getForm());
		return "/views/gateconsole/modules/admin/Func/View.html";
	}

	@Icon("select")
	@Name("Detalhe")
	public Object callSelect()
	{
		try
		{
			form = control.select(getForm().getId());
			return "/views/gateconsole/modules/admin/Func/ViewSelect.html";
		} catch (AppException ex)
		{
			return Command.hide(ex.getMessages());
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
				return Command.redirect()
					.module(getModule())
					.screen(getScreen())
					.action("Select")
					.parameter("form.id", getForm().getId());
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}

		return "/views/gateconsole/modules/admin/Func/ViewInsert.html";
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
				return Command.hide(ex.getMessages());
			}
		} else if (getMessages().isEmpty())
		{
			try
			{
				control.update(getForm());
				return Command.redirect()
					.module(getModule())
					.screen(getScreen())
					.action("Select")
					.parameter("form.id", getForm().getId());
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}
		return "/views/gateconsole/modules/admin/Func/ViewUpdate.html";
	}

	@Icon("delete")
	@Name("Remover")
	@Color("#660000")
	@Confirm("Tem certeza de que deseja remover este registro?")
	public Object callDelete()
	{
		try
		{
			control.delete(getForm());
			return Command.hide();
		} catch (AppException ex)
		{
			return Command.redirect()
				.module(getModule())
				.screen(getScreen())
				.action("Select")
				.messages(ex.getMessages())
				.parameter("form.id", getForm().getId());
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
		UserControl.FuncControl control;

		public String call()
		{

			page = paginate(ordenate(control.search(func)));
			return "/views/gateconsole/modules/admin/Func/User/View.html";
		}

		@Icon("insert")
		@Name("Adcionar")
		public Command callInsert()
		{

			try
			{
				control.insert(user, func);
				return Command.redirect()
					.module(getModule())
					.screen(getScreen())
					.parameter("func.id", getFunc().getId());
			} catch (AppException ex)
			{
				return Command.redirect()
					.module(getModule())
					.screen(getScreen())
					.messages(ex.getMessages())
					.parameter("func.id", getFunc().getId());
			}
		}

		@Icon("delete")
		@Name("Remover")
		public Command callDelete()
		{
			try
			{
				control.delete(user, func);
				return Command.redirect()
					.module(getModule())
					.screen(getScreen())
					.parameter("func.id", getFunc().getId());
			} catch (AppException ex)
			{
				return Command.redirect()
					.module(getModule())
					.screen(getScreen())
					.messages(ex.getMessages())
					.parameter("func.id", getFunc().getId());
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
		RoleControl.FuncControl control;

		public String call()
		{

			page = paginate(ordenate(control.search(func)));
			return "/views/gateconsole/modules/admin/Func/Role/View.html";
		}

		@Icon("insert")
		@Name("Adcionar")
		public Command callInsert()
		{

			try
			{
				control.insert(role, func);
				return Command.redirect()
					.module(getModule())
					.screen(getScreen())
					.parameter("func.id", getFunc().getId());
			} catch (AppException ex)
			{
				return Command.redirect()
					.module(getModule())
					.screen(getScreen())
					.messages(ex.getMessages())
					.parameter("func.id", getFunc().getId());
			}
		}

		@Icon("delete")
		@Name("Remover")
		public Command callDelete()
		{

			try
			{
				control.delete(role, func);
				return Command.redirect()
					.module(getModule())
					.screen(getScreen())
					.parameter("func.id", getFunc().getId());
			} catch (AppException ex)
			{
				return Command.redirect()
					.module(getModule())
					.screen(getScreen())
					.messages(ex.getMessages())
					.parameter("func.id", getFunc().getId());
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
	}
}
