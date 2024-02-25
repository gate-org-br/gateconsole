package gateconsole.modules.admin;

import gate.annotation.CopyIcon;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.entity.Auth;
import gate.error.AppException;
import gateconsole.contol.AuthControl;
import java.util.ArrayList;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
@Name("Acessos")
@CopyIcon(Auth.class)
public class AuthScreen extends gate.base.Screen
{

	private Auth form;
	private Collection<Auth> page;

	@Inject
	AuthControl control;

	@Name("Acessos")
	@CopyIcon(Auth.class)
	public String call() throws AppException
	{
		setPage(control.search(getForm()));
		return "/views/gateconsole/modules/admin/Auth/View.html";
	}

	@Icon("1002")
	@Name("Incluir acesso")
	public String callInsert() throws AppException
	{
		control.insert(getForm());
		return "/views/gateconsole/modules/admin/Auth/ViewInsert.html";
	}

	@Icon("2026")
	@Name("Excluir acesso")
	public void callDelete() throws AppException
	{
		control.delete(getForm());
	}

	public Auth getForm()
	{
		if (form == null)
			form = new Auth();
		return form;
	}

	public void setForm(Auth form)
	{
		this.form = form;
	}

	public Collection<Auth> getPage()
	{
		if (page == null)
			page = new ArrayList<>();
		return page;
	}

	public void setPage(Collection<Auth> page)
	{
		this.page = page;
	}
}
