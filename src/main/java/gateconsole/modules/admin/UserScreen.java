package gateconsole.modules.admin;

import gate.annotation.Asynchronous;
import gate.annotation.Color;
import gate.annotation.Confirm;
import gate.annotation.CopyIcon;
import gate.annotation.Description;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.base.Screen;
import gate.command.Command;
import gate.constraint.Required;
import gate.entity.Func;
import gate.entity.User;
import gate.error.AppException;
import gate.error.ConversionException;
import gate.lang.property.Property;
import gate.report.Doc;
import gate.report.Form;
import gate.report.Grid;
import gate.report.Report;
import gate.type.DataFile;
import gate.type.mime.MimeData;
import gate.util.Backup;
import gateconsole.contol.FuncControl;
import gateconsole.contol.UserControl;
import java.time.LocalDateTime;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
@Name("Usuários")
@CopyIcon(User.class)
public class UserScreen extends gate.base.Screen
{

	private User form;

	@Required
	@Description("Entre com o arquivo a ser importado")
	private DataFile file;

	private Iterable<User> page;
	private final Backup BACKUP = new Backup("Usuários", User.class,
		"name", "username", "email", "phone", "cellPhone", "description");

	@Inject
	UserControl control;

	public String call()
	{

		if (isPOST())
		{
			page = paginate(ordenate(control.search(getForm())));
			return "/views/gateconsole/modules/admin/User/Page.html";
		} else
			return "/views/gateconsole/modules/admin/User/View.html";
	}

	@Name("Usuários")
	@Icon("gate.entity.User")
	public Object callImport()
	{
		page = control.search(getForm());
		return "/views/gateconsole/modules/admin/User/ViewImport.html";
	}

	public String callNoRole()
	{
		page = paginate(ordenate(control.getSubscriptions()));
		return "/views/gateconsole/modules/admin/User/ViewNoRole.html";
	}

	@Icon("select")
	@Name("Detalhes")
	public String callSelect() throws AppException
	{
		form = control.select(getForm().getId());
		return "/views/gateconsole/modules/admin/User/ViewSelect.html";
	}

	@Icon("insert")
	@Name("Novo usuário")
	public String callInsert() throws AppException
	{
		if (isPOST() && getMessages().isEmpty())
		{
			control.insert(getForm());
			return callSelect();
		} else
			getForm().setActive(true);
		return "/views/gateconsole/modules/admin/User/ViewInsert.html";
	}

	@Icon("update")
	@Name("Alterar usuário")
	public String callUpdate() throws AppException
	{
		if (isPOST() && getMessages().isEmpty())
		{
			control.update(getForm());
			return callSelect();
		} else if (isGET())
			form = control.select(getForm().getId());
		return "/views/gateconsole/modules/admin/User/ViewUpdate.html";
	}

	@Icon("passwd")
	@Name("Resetar senha")
	public Object callPasswd() throws AppException
	{
		control.password(getForm());
		return "Senha do usuário resetada para o login.";
	}

	@Icon("delete")
	@Name("Remover usuário")
	@Confirm("Tem certeza de que deseja remover este registro?")
	public void callDelete() throws AppException
	{
		control.delete(getForm());
	}

	@Icon("upload")
	@Name("Importar")
	@Description("Importar Usuários")
	public String callUpload()
	{
		return "/views/gateconsole/modules/admin/User/ViewUpload.html";
	}

	@Asynchronous
	public Object callCommit() throws ConversionException, AppException
	{
		control.insert(getForm().getRole(), BACKUP.load(file));
		return Command
			.redirect()
			.module(getModule())
			.screen(getScreen());
	}

	@Icon("report")
	@Name("Imprimir")
	@Description("Imprimir")
	public Object callReport(Doc.Type type)
	{
		Report report = new Report();

		report.addImage(getOrg().getIcon());
		report.addHeader(LocalDateTime.now());
		report.addHeader("Relatórios de Usuários");
		report.addHeader(getApp().getId() + " - " + getApp().getName());

		Form formulario = report.addForm(4);

		formulario.setCaption("Filtro");
		formulario.add("Ativo:", getForm().getActive());
		formulario.add("Login:", getForm().getUsername());
		formulario.add("E-Mail:", getForm().getEmail()).colspan(2);
		formulario.add("Nome", getForm().getName()).colspan(2);
		formulario.add("Perfil", getForm().getRole().getName()).colspan(2);

		report.addLineBreak();

		Grid<User> grid = report.addGrid(User.class, ordenate(control.search(getForm())));
		grid.add().head("Login").body(User::getUsername).style().width(10);
		grid.add().head("Perfil").body(e -> e.getRole().getName()).style().width(45);
		grid.add().head("Name").body(User::getName).style().width(45);

		return Doc.create(type, report);
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

	public Iterable<User> getPage()
	{
		return page;
	}

	public DataFile getFile()
	{
		return file;
	}

	public void setFile(DataFile file)
	{
		this.file = file;
	}

	public List<Property> getProperties()
	{
		return BACKUP.getProperties();
	}

	public MimeData getPhoto()
	{
		return control.getPhoto(getForm().getId());
	}

	@RequestScoped
	@Name("Funções")
	@CopyIcon(Func.class)
	public static class FuncScreen extends Screen
	{

		private Func func;
		private User user;
		private List<Func> page;

		@Inject
		FuncControl.UserControl control;

		public String call()
		{

			page = control.search(user);
			return "/views/gateconsole/modules/admin/User/Func/View.html";
		}

		@Icon("insert")
		@Name("Adicionar")
		public String callInsert() throws AppException
		{

			func = control.insert(func, user);
			return "/views/gateconsole/modules/admin/User/Func/ViewInsert.html";
		}

		@Icon("delete")
		@Name("Remover")
		@Color("#660000")
		public void callDelete() throws AppException
		{

			control.delete(func, user);
		}

		@Override
		public User getUser()
		{
			if (user == null)
				user = new User();
			return user;
		}

		public Func getFunc()
		{
			if (func == null)
				func = new Func();
			return func;
		}

		public List<Func> getPage()
		{
			return page;
		}
	}
}
