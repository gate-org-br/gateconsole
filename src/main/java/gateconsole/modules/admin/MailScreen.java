package gateconsole.modules.admin;

import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.constraint.Required;
import gate.messaging.Message;
import gate.messaging.MessageException;
import gate.messaging.Messenger;
import gate.type.mime.MimeMail;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@Icon("2034")
@RequestScoped
@Name("Mensagens")
public class MailScreen extends gate.base.Screen
{

	private List<Message> page
		= new ArrayList<>();

	@Inject
	private Messenger messenger;

	@Required
	@Name("Destino")
	@Description("Endereço de destino para o email de teste")
	private String destination;

	public String call()
	{
		try
		{
			page = messenger.search();
		} catch (MessageException ex)
		{
			setMessages(ex.getMessages());
		}
		return "/WEB-INF/views/gateconsole/modules/admin/Mail/View.jsp";
	}

	@Icon("2034")
	@Name("Enviar email de teste")
	public String callPost()
	{
		try
		{
			messenger.post(destination, destination,
				MimeMail.of("EMail de teste", "Favor desconsiderar"));
			destination = null;
		} catch (MessageException ex)
		{
			setMessages(ex.getMessages());
		}
		return call();
	}

	public String getDestination()
	{
		return destination;
	}

	public void setDestination(String destination)
	{
		this.destination = destination;
	}

	public List<Message> getPage()
	{
		return page;
	}
}
