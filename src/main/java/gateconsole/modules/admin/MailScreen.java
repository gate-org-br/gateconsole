package gateconsole.modules.admin;

import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Required;
import gate.entity.Mail;
import gate.entity.Server;
import gate.error.AppException;
import gate.messaging.MessageException;
import gate.messaging.Messenger;
import gate.type.mime.MimeMail;
import gateconsole.contol.ServerControl;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@Icon("2034")
@RequestScoped
@Name("Mensagens")
public class MailScreen extends gate.base.Screen
{

	private List<Mail> page
		= new ArrayList<>();

	@Inject
	Messenger messenger;

	@Inject
	ServerControl control;

	@Required
	@Name("Destino")
	@Description("Endereço de destino para o email de teste")
	private String destination;

	private Server form;

	public String call() throws MessageException
	{
		page = messenger.search();
		form = control.select(Server.Type.SMTP);
		return "/views/gateconsole/modules/admin/Mail/View.html";
	}

	@Icon("commit")
	@Name("Aplicar")
	public String callUpdate() throws AppException
	{
		getForm().setType(Server.Type.SMTP);
		control.update(form);
		return "Configurações aplicadas com sucesso";
	}

	@Icon("2034")
	@Name("Postar")
	public String callPost() throws MessageException
	{
		messenger.post(destination, MimeMail.of("EMail de teste", "Favor desconsiderar"));
		destination = null;
		return "/views/gateconsole/modules/admin/Mail/ViewInsert.html";
	}

	public String getDestination()
	{
		return destination;
	}

	public void setDestination(String destination)
	{
		this.destination = destination;
	}

	public Server getForm()
	{
		return form;
	}

	public List<Mail> getPage()
	{
		return page;
	}
}
