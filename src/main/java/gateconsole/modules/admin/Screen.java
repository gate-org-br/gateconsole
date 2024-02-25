package gateconsole.modules.admin;

import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.entity.User;
import gateconsole.contol.UserControl;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@Icon("2059")
@RequestScoped
@Name("Gerência")
@Description("Módulo de gerência")
public class Screen extends gate.base.Screen
{

	public Object call()
	{
		return "/views/gateconsole/modules/admin/View.html";
	}

	public List<User> getSubscriptions()
	{
		return new UserControl().getSubscriptions();
	}
}
