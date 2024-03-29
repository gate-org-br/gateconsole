package gateconsole.modules.admin;

import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Required;
import gate.error.AppException;
import gateconsole.contol.SearchControl;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@Icon("2008")
@Name("Home")
public class HomeScreen extends gate.base.Screen
{

	@Required
	@Description("O campo de busca deve conter pelo menos 3 caracteres.")
	private String form;
	private List<Object> page;

	@Inject
	SearchControl control;

	public Object call() throws AppException
	{
		if (isPOST())
		{
			setPage(control.search(getForm()));
			return "/views/gateconsole/modules/admin/Home/Page.html";
		}

		return "/views/gateconsole/modules/admin/Home/View.html";
	}

	public String getForm()
	{
		return form;
	}

	public void setForm(String form)
	{
		this.form = form;
	}

	public List<Object> getPage()
	{
		if (page == null)
			page = new ArrayList<>();
		return page;
	}

	public void setPage(List<Object> page)
	{
		this.page = page;
	}
}
