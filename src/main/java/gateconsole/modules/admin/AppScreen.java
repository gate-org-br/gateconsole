package gateconsole.modules.admin;

import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.annotation.Public;
import gate.command.Command;
import gate.entity.App;
import gate.error.NotFoundException;
import gateconsole.contol.AppControl;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@Public
@Icon("2044")
@RequestScoped
@Name("Aplicações")
public class AppScreen extends gate.base.Screen
{

	@Inject
	AppControl control;

	private App form;
	private String id;

	private List<App> page;

	public String call()
	{
		page = control.search();
		return "/views/gateconsole/modules/admin/App/View.html";
	}

	@Icon("search")
	@Name("Pesquisar")
	@Description("Pesquisar acessos")
	public String callSearch()
	{
		page = control.search();
		return "/views/gateconsole/modules/admin/App/ViewSearch.html";
	}

	@Icon("2199")
	@Name("Detalhes")
	public Object callSelect()
	{
		try
		{
			form = control.select(id);
			return "/views/gateconsole/modules/admin/App/ViewSelect.html";
		} catch (NotFoundException ex)
		{
			return Command.hide(ex.getMessage());
		}
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public App getForm()
	{
		return form;
	}

	public Collection<App> getPage()
	{
		return page;
	}
}
