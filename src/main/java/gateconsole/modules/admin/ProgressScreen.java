package gateconsole.modules.admin;

import gate.Progress;
import gate.annotation.Asynchronous;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.control.ActivateRequestContext;

@Dependent
public class ProgressScreen extends gate.base.Screen
{

	private String nome = "AFE";

	public String call() throws InterruptedException
	{
		return "/views/gateconsole/modules/admin/Progress/View.html";
	}

	@Asynchronous
	public String callRedirect() throws InterruptedException
	{
		Progress.startup(10, "Loading");
		for (int i = 0; i < 10; i++)
		{
			Thread.sleep(100);
			Progress.update("Loading: " + (i + 1));
		}
		Progress.commit("Done");
		return "http://www.google.com.br";
	}

	@Asynchronous
	@ActivateRequestContext
	public String callInsert() throws InterruptedException
	{
		Progress.startup(10, "Loading");
		for (int i = 0; i < 10; i++)
		{
			Thread.sleep(100);
			Progress.update("Loading: " + (i + 1));
		}
		Progress.commit("Done");
		return "/views/gateconsole/modules/admin/Progress/ViewInsert.html";
	}

	public String getNome()
	{
		return nome;
	}
}
