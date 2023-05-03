package gateconsole.modules.admin;

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
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
@Name("Perfis")
@CopyIcon(Role.class)
public class RoleScreen extends gate.base.Screen
{

	@Inject
	RoleControl control;

	@Inject
	UserControl userControl;

	private Role form;

	private Collection<Role> page;

	public String call()
	{
		try
		{
			page = control.search();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/views/gateconsole/modules/admin/Role/View.html";
	}

	@Name("Sub Perfis")
	@CopyIcon(Role.class)
	public String callImport()
	{
		page = control.search(getForm().getRole());
		return "/views/gateconsole/modules/admin/Role/ViewImport.html";
	}

	@Icon("search")
	@Name("Pesquisar")
	public String callSearch()
	{
		try
		{
			page = control.search();
		} catch (AppException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/views/gateconsole/modules/admin/Role/ViewSearch.html";
	}

	@Icon("select")
	@Name("Detalhe")
	public Object callSelect()
	{
		try
		{
			form = control.select(getForm().getId());
			return "/views/gateconsole/modules/admin/Role/ViewSelect.html";
		} catch (AppException ex)
		{
			return Command.hide(ex.getMessages());
		}
	}

	@Icon("insert")
	@Name("Novo perfil")
	public Object callInsert()
	{
		if (isPOST() && getMessages().isEmpty())
		{
			try
			{
				control.insert(getForm());
				return Command
					.redirect()
					.module(getModule())
					.screen(getScreen())
					.action("Select")
					.parameter("form.id", getForm().getId());
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
		}

		return "/views/gateconsole/modules/admin/Role/ViewInsert.html";
	}

	@Icon("update")
	@Name("Alterar perfil")
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

		return "/views/gateconsole/modules/admin/Role/ViewUpdate.html";
	}

	@Icon("delete")
	@Name("Remover perfil")
	public Object callDelete()
	{
		try
		{
			control.delete(getForm());
			return Command.hide("O perfil foi removido com sucesso.");
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

	public List<User> getUsers()
	{
		return userControl.search(getForm());
	}

	public Role getForm()
	{
		if (form == null)
			form = new Role();
		return form;
	}

	public Collection<Role> getPage()
	{
		return page;
	}

	@RequestScoped
	@Name("Funções")
	@CopyIcon(Func.class)
	public static class FuncScreen extends Screen
	{

		private Func func;
		private Role role;
		private Page<Func> page;

		@Inject
		FuncControl.RoleControl control;

		public String call()
		{

			page = paginate(ordenate(control.search(role)));
			return "/views/gateconsole/modules/admin/Role/Func/View.html";
		}

		@Icon("insert")
		@Name("Adcionar função")
		public String callInsert()
		{

			try
			{
				control.insert(func, role);
				func = null;
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
			return call();
		}

		@Icon("delete")
		@Name("Remover função")
		public String callDelete()
		{

			try
			{
				control.delete(func, role);
				func = null;
			} catch (AppException ex)
			{
				setMessages(ex.getMessages());
			}
			return call();
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

		public Page<Func> getPage()
		{
			return page;
		}
	}
}
