package gateconsole.modules.admin;

import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.annotation.Public;
import javax.enterprise.context.RequestScoped;

@Public
@Icon("2009")
@RequestScoped
@Name("Ícones")
@Description("Biblioteca de ícones do Gate")
public class IconScreen extends gate.base.Screen
{

	public String call()
	{
		return "/WEB-INF/views/gateconsole/modules/admin/Icon/View.jsp";
	}
}
