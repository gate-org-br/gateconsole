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
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
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
	public Object callSelect() throws AppException
	{
		form = control.select(getForm().getId());
		return "/views/gateconsole/modules/admin/Func/ViewSelect.html";
	}

	@Icon("insert")
	@Name("Nova função")
	public Object callInsert() throws AppException
	{
		if (isPOST())
		{
			control.insert(getForm());
			return Command.redirect()
				.module(getModule())
				.screen(getScreen())
				.action("Select")
				.parameter("form.id", getForm().getId());
		} else
			return "/views/gateconsole/modules/admin/Func/ViewInsert.html";
	}

	@Icon("update")
	@Name("Alterar função")
	public Object callUpdate() throws AppException
	{
		if (isPOST())
		{
			control.update(getForm());
			return callSelect();
		} else
			form = control.select(getForm().getId());

		return "/views/gateconsole/modules/admin/Func/ViewUpdate.html";
	}

	@Icon("delete")
	@Name("Remover função")
	@Confirm("Tem certeza de que deseja remover este registro?")
	public void callDelete() throws AppException
	{
		control.delete(getForm());
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

	@Dependent
	@Name("Usuários")
	@CopyIcon(User.class)
	public static class UserScreen extends Screen
	{

		private Func func;
		private User user;
		private List<User> page;

		@Inject
		UserControl.FuncControl control;

		public String call()
		{

			page = control.search(func);
			return "/views/gateconsole/modules/admin/Func/User/View.html";
		}

		@Icon("insert")
		@Name("Adcionar usuário")
		public String callInsert() throws AppException
		{
			user = control.insert(user, func);
			return "/views/gateconsole/modules/admin/Func/User/ViewInsert.html";
		}

		@Icon("delete")
		@Name("Remover")
		@Color("#660000")
		public void callDelete() throws AppException
		{
			control.delete(user, func);
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

		public List<User> getPage()
		{
			if (page == null)
				page = new ArrayList<>();
			return page;
		}
	}

	@Dependent
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
		@Name("Adcionar perfil")
		public String callInsert() throws AppException
		{

			role = control.insert(role, func);
			return "/views/gateconsole/modules/admin/Func/Role/ViewInsert.html";
		}

		@Icon("delete")
		@Name("Remover")
		public void callDelete() throws AppException
		{

			control.delete(role, func);
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
