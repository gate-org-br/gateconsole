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

	public String call() throws AppException
	{
		page = control.search();
		return "/views/gateconsole/modules/admin/Role/View.html";
	}

	@Name("Sub Perfis")
	@CopyIcon(Role.class)
	public String callImport() throws AppException
	{
		page = control.search(getForm().getRole());
		return "/views/gateconsole/modules/admin/Role/ViewImport.html";
	}

	@Icon("search")
	@Name("Pesquisar")
	public String callSearch() throws AppException
	{
		page = control.search();
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
	public Object callInsert() throws AppException
	{
		if (isPOST())
		{
			control.insert(getForm());
			return Command
				.redirect()
				.module(getModule())
				.screen(getScreen())
				.action("Select")
				.parameter("form.id", getForm().getId());
		}

		return "/views/gateconsole/modules/admin/Role/ViewInsert.html";
	}

	@Icon("update")
	@Name("Alterar perfil")
	public Object callUpdate() throws AppException
	{
		if (isPOST())
		{
			control.update(getForm());
			return Command.redirect()
				.module(getModule())
				.screen(getScreen())
				.action("Select")
				.parameter("form.id", getForm().getId());
		}

		form = control.select(getForm().getId());
		return "/views/gateconsole/modules/admin/Role/ViewUpdate.html";

	}

	@Icon("delete")
	@Name("Remover perfil")
	public void callDelete() throws AppException
	{
		control.delete(getForm());
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
		public String callInsert() throws AppException
		{
			func = control.insert(func, role);
			return "/views/gateconsole/modules/admin/Role/Func/ViewInsert.html";
		}

		@Icon("delete")
		@Name("Remover função")
		public void callDelete() throws AppException
		{
			control.delete(func, role);
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
