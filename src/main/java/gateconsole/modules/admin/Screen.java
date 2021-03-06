package gateconsole.modules.admin;

import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.entity.User;
import gate.io.URL;
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
		return new URL("Gate?MODULE=gateconsole.modules.admin&SCREEN=Home");
	}

	public List<User> getSubscriptions()
	{
		return new UserControl().getSubscriptions();
	}
}
