package gateconsole.modules.admin;

import gate.annotation.CopyInfo;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.entity.Org;
import gate.error.AppException;
import gate.error.NotFoundException;
import gateconsole.contol.OrgControl;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@Name("Organização")
@CopyInfo(Org.class)
public class OrgScreen extends gate.base.Screen
{

	private Org form;

	@Inject
	OrgControl control;

	public Object call()
	{
		try
		{
			form = control.select().orElseThrow(NotFoundException::new);
			return "/views/gateconsole/modules/admin/Org/View.html";
		} catch (NotFoundException ex)
		{
			return "/views/gateconsole/modules/admin/Org/ViewUpdate.html";
		}
	}

	@Icon("2057")
	@Name("Alterar")
	public String callUpdate()
	{
		form = control.select().orElse(new Org());
		return "/views/gateconsole/modules/admin/Org/ViewUpdate.html";
	}

	@Icon("commit")
	@Name("Concluir")
	public Object callCommit() throws AppException
	{
		control.update(getForm());
		getRequest().getSession().getServletContext()
			.setAttribute("org", control.select().orElseThrow(NotFoundException::new));
		return call();

	}

	@Icon("dwload")
	public Object callDwload()
	{
		return control.dwload();
	}

	public Org getForm()
	{
		if (form == null)
			form = new Org();
		return form;
	}
}
