package gate.tags.formControls;

import gate.converter.Converter;
import gate.type.Attributes;
import gate.util.Toolkit;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.jsp.JspException;

public abstract class CheckableTag extends SelectorTag
{

	@Override
	public void doTag() throws JspException, IOException
	{
		super.doTag();

		if (options == null)
			if (Enum.class.isAssignableFrom(getType()))
				options = Arrays.asList(getType().getEnumConstants());
			else if (Boolean.class.isAssignableFrom(getType()))
				options = Arrays.asList(Boolean.FALSE, Boolean.TRUE);
			else
				throw new JspException("No option defined for property " + getProperty());

		if (sortby != null)
			Toolkit.collection(options)
				.stream()
				.sorted((a, b) -> (Integer) sortby.invoke(EL_CONTEXT, a, b))
				.collect(Collectors.toList());

		if (groups != null)
		{
			getJspContext().getOut().print("<ul>");

			for (Map.Entry<Object, List<Object>> group : Toolkit.collection(options).stream()
				.collect(Collectors.groupingBy(e -> groups.invoke(EL_CONTEXT, e), Collectors.toList())).entrySet())
			{
				getJspContext().getOut().print("<li>");
				getJspContext().getOut().print(Converter.toText(group.getKey()));
				print(group.getValue());
				getJspContext().getOut().print("</li>");
			}

			getJspContext().getOut().print("</ul>");
		} else
			print(options);
	}

	private void print(Iterable<?> options) throws IOException, JspException
	{
		getJspContext().getOut().print("<ul>");

		for (Object option : options)
		{
			Object value = option;
			if (values != null)
				value = values.invoke(EL_CONTEXT, option);

			Attributes attributes = new Attributes();
			attributes.put("type", getComponentType());
			attributes.put("name", getName());

			if (Toolkit.collection(getValue()).contains(value))
				attributes.put("checked", "checked");

			attributes.put("value", getConverter().toString(getType(), value));

			getJspContext().getOut().print("<li>");
			getJspContext().getOut().print("<label>");
			getJspContext().getOut().print(String.format("<input %s/>", attributes.toString()));

			if (getJspBody() != null)
			{
				getJspContext().setAttribute("option", option);
				getJspBody().invoke(null);
				getJspContext().removeAttribute("option");
			} else if (labels != null)
				getJspContext().getOut()
					.print(getConverter().toText(getType(),
						labels.invoke(EL_CONTEXT, option)));
			else
				getJspContext().getOut()
					.print(getConverter().toText(getType(), option));

			getJspContext().getOut().print("</label>");

			if (children != null)
				print(Toolkit.iterable(children.invoke(EL_CONTEXT, option)));

			getJspContext().getOut().print("</li>");
		}
		getJspContext().getOut().print("</ul>");
	}

	protected abstract String getComponentType();
}