package gateconsole.modules.admin;

import gate.Progress;
import gate.annotation.Asynchronous;
import gate.annotation.Description;
import gate.annotation.Handler;
import gate.annotation.Icon;
import gate.annotation.Name;
import gate.annotation.Public;
import gate.entity.User;
import gate.error.AppException;
import gate.error.ConversionException;
import gate.handler.JsonTextHandler;
import gate.handler.OptionHandler;
import gate.io.URL;
import gate.report.Doc;
import gate.report.Report;
import gate.type.ID;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.enterprise.context.RequestScoped;

@Public
@Icon("2072")
@Name("Demo")
@RequestScoped
@Description("Demonstração")
public class DemoScreen extends gate.base.Screen
{

	private String text;
	private Doc.Type type;
	private List<Doc.Type> types = new ArrayList<>();

	private static final List<User> USERS
		= List.of(new User().setId(new ID(1)).setName("Amanda Rodrigues").setUsername("amandarodrigues").setUsername("amandarodrigues@gate.org"),
			new User().setId(new ID(2)).setName("Amanda Borges").setUsername("amandaborges").setUsername("amandaborges@gate.org"),
			new User().setId(new ID(2)).setName("Anderson Borges").setUsername("andersonborges").setUsername("andersonborges@gate.org"),
			new User().setId(new ID(2)).setName("Anderson Lima").setUsername("andersonlima").setUsername("andersonlima@gate.org"),
			new User().setId(new ID(3)).setName("Barbara Gomes").setUsername("barbaragomes").setUsername("bgomes@gate.org"),
			new User().setId(new ID(4)).setName("Barbie Rodrigues").setUsername("barbierodrigues").setUsername("brodrigues@gate.org"),
			new User().setId(new ID(5)).setName("Barbie Sampaio").setUsername("barbiesampaio").setUsername("barbiesampaio@gate.org"),
			new User().setId(new ID(6)).setName("Bartira Souza").setUsername("bartirasouza").setUsername("bartirasouza@gate.org"),
			new User().setId(new ID(7)).setName("Bartira Nunes").setUsername("bartiranunes").setUsername("bartiranunes@gate.org"),
			new User().setId(new ID(8)).setName("Bartolomeu Borges").setUsername("bartolomeuborges").setUsername("bartolomeuborges@gate.org"),
			new User().setId(new ID(9)).setName("Bruno Silva").setUsername("brunosilva").setUsername("brunosilva@gate.org"),
			new User().setId(new ID(10)).setName("Carla Sampaio").setUsername("carlasampaio").setUsername("carlasampaio@gate.org"),
			new User().setId(new ID(11)).setName("Carlos Gomes").setUsername("carlosgomes").setUsername("carlosgomes@gate.org"),
			new User().setId(new ID(12)).setName("Carlene Souza").setUsername("carlenesouza").setUsername("carlenesouza@gate.org"));

	public String call()
	{
		return callTabBar();
	}

	@Name("Tab Bar")
	public String callTabBar()
	{
		return "/views/gateconsole/modules/admin/Demo/ViewTabBar.html";
	}

	@Name("Pickers")
	public String callPickers()
	{
		return "/views/gateconsole/modules/admin/Demo/ViewPickers.html";
	}

	@Name("Tooltip")
	public String callTooltip()
	{
		return "/views/gateconsole/modules/admin/Demo/ViewTooltip.html";
	}

	@Name("Mensagens")
	public String callMessage()
	{
		return "/views/gateconsole/modules/admin/Demo/ViewMessage.html";
	}

	@Name("Chart")
	public String callChart()
	{
		return "/views/gateconsole/modules/admin/Demo/ViewChart.html";
	}

	@Name("Context Menu")
	public String callContextMenu()
	{
		return "/views/gateconsole/modules/admin/Demo/ViewContextMenu.html";
	}

	@Name("Select")
	public String callSelect()
	{
		return "/views/gateconsole/modules/admin/Demo/ViewSelect.html";
	}

	@Name("Text Editor")
	public String callTextEditor()
	{
		return "/views/gateconsole/modules/admin/Demo/TextEditor.html";
	}

	@Name("Other")
	public String callOther()
	{
		return "/views/gateconsole/modules/admin/Demo/ViewOther.html";
	}

	@Name("Chat")
	public String callChat()
	{
		return "/views/gateconsole/modules/admin/Demo/ViewChat.html";
	}

	@Icon("report")
	@Name("Imprimir")
	public Doc callReport()
	{
		Report report = new Report();
		report.addGrid(String.class, IntStream
			.iterate(1, e -> e + 1).limit(100000)
			.mapToObj(e -> String.format("Item %06d", e))
			.collect(Collectors.toList()))
			.add().body(e -> e).head("Item");
		return Doc.create(type, report);
	}

	@Icon("2264")
	@Asynchronous
	@Name("Progress")
	public URL callProgress()
	{
		Progress.startup(100, "Starting");
		for (int i = 1; i <= 100; i++)
		{
			try
			{
				Progress.update("Processing " + i);
				Thread.sleep(50);
			} catch (InterruptedException ex)
			{
				Progress.cancel(ex.getMessage());
			}

		}
		Progress.commit("Success");
		return new URL("Gate")
			.setModule(getModule())
			.setScreen("Icon");
	}

	@Icon("1001")
	@Name("Block")
	public String callBlock()
	{
		try
		{
			Thread.sleep(4000);
			return call();
		} catch (InterruptedException ex)
		{
			return ex.getMessage();
		}
	}

	@Handler(JsonTextHandler.class)
	public List<List<Object>> callData() throws ConversionException
	{

		int size = getRequest().getParameter(Integer.class, "size");
		return TestData.INSTANCE.stream().skip(size).limit(10).collect(Collectors.toList());
	}

	@Handler(OptionHandler.class)
	public List<User> callSelectOptions()
	{
		return USERS;
	}

	@Handler(OptionHandler.class)
	public List<User> callSearchOptions() throws AppException
	{
		String body = getRequest().getBody().trim().toUpperCase();
		return body.length() >= 3
			? USERS.stream().filter(e -> e.getName().toUpperCase().contains(body)).collect(Collectors.toList())
			: List.of();
	}

	public void setType(Doc.Type type)
	{
		this.type = type;
	}

	public List<Doc.Type> getTypes()
	{
		return types;
	}

	public void setTypes(List<Doc.Type> types)
	{
		this.types = types;
	}

	public List<Doc.Type> getOptions()
	{
		return List.of(Doc.Type.PDF, Doc.Type.DOC, Doc.Type.XLS, Doc.Type.CSV);
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	private static class TestData extends ArrayList<List<Object>>
	{

		private static final TestData INSTANCE = new TestData();

		private TestData()
		{
			int index = 0;

			List<String> nomes = List.of("Paulo", "Pedro", "Carlos", "Silvio", "José", "Sandro", "Camila", "Davi", "Patrícia", "Renata");
			List<String> sobrenomes = List.of("Carvalho", "Nunes", "Silva", "Gusmão", "Cunha", "Ferreira", "Barbosa", "Barnabé", "Santos", "Azevedo");

			for (String nome : nomes)
				for (String sobrenome : sobrenomes)
					add(List.of(index++, nome + " " + sobrenome, nome.toLowerCase() + sobrenome.toLowerCase() + "@br.com", true));

		}
	}
}
