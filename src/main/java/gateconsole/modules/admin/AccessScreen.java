package gateconsole.modules.admin;

import gate.annotation.Color;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.entity.Role;
import gate.entity.User;
import gate.error.AppException;
import gate.messaging.Messenger;
import gate.type.mime.MimeMail;
import gateconsole.contol.RoleControl;
import gateconsole.contol.UserControl;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@Icon("1007")
@RequestScoped
@Color("#660000")
@Name("Pendências")
public class AccessScreen extends gate.base.Screen
{

	private User form;

	@Inject
	Messenger messenger;

	@Inject
	UserControl control;

	public String call()
	{
		return "/views/gateconsole/modules/admin/Access/View.html";
	}

	public String callSelect()
	{
		try
		{
			form = control.select(getForm().getId());
			return "/views/gateconsole/modules/admin/Access/ViewSelect.html";
		} catch (AppException e)
		{
			setMessages(e.getMessages());
			return call();
		}
	}

	public String callUpdate()
	{
		try
		{
			control.accept(getForm(), getForm().getRole());
			if (getForm().getEmail() != null)
				messenger.post(getUser().getEmail(), getForm().getEmail(), MimeMail.of("Cadastro acatado",
					"Seu pedido de cadastro foi acatado."));
			return "/views/gateconsole/modules/admin/Access/ViewResult.html";
		} catch (AppException e)
		{
			setMessages(e.getMessages());
			return callSelect();
		}
	}

	public String callDelete()
	{
		try
		{
			form = control.select(getForm().getId());
			if (getForm().getEmail() != null)
				messenger.post(getUser().getEmail(), getForm().getEmail(), MimeMail.of("Cadastro recusado",
					"Seu pedido de cadastro foi recusado."));
			control.delete(getForm());
			return "/views/gateconsole/modules/admin/Access/ViewResult.html";
		} catch (AppException e)
		{
			setMessages(e.getMessages());
			return callSelect();
		}
	}

	public User getForm()
	{
		if (form == null)
			form = new User();
		return form;
	}

	public void setForm(User form)
	{
		this.form = form;
	}

	public Collection<Role> getRoles() throws AppException
	{
		return new RoleControl().search();
	}
}
