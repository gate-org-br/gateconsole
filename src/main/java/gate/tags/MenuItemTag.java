package gate.tags;

import gate.annotation.Color;
import gate.annotation.Description;
import gate.annotation.Name;
import gate.type.Attributes;
import gate.util.Toolkit;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class MenuItemTag extends AnchorTag
{

	private boolean fixed = false;

	public void setFixed(boolean fixed)
	{
		this.fixed = fixed;
	}

	@Override
	public void doTag() throws JspException, IOException
	{
		super.doTag();
		if (!getCondition())
			return;

		PageContext pageContext = (PageContext) getJspContext();

		if (Toolkit.isEmpty(getModule())
			&& Toolkit.isEmpty(getScreen())
			&& Toolkit.isEmpty(getAction()))
		{
			pageContext.getOut().print("<li " + getAttributes() + ">");

			Attributes atrributes = new Attributes();
			if (getTarget() != null)
				atrributes.put("target", getTarget());
			if (getTabindex() != null)
				atrributes.put("tabindex", getTabindex());

			atrributes.put("href", "Gate");
			pageContext.getOut().print("<a " + atrributes + ">");
			pageContext.getOut().print("Sair do sistema<i>&#X2007;</i>");
			pageContext.getOut().print("</a>");
			pageContext.getOut().print("</li>");
		} else
		{

			if (!getAttributes().containsKey("title") && getJavaMethod().isAnnotationPresent(Description.class))
				getAttributes().put("title", getJavaMethod().getAnnotation(Description.class).value());

			if (!getAttributes().containsKey("title") && getJavaMethod().isAnnotationPresent(Name.class))
				getAttributes().put("title", getJavaMethod().getAnnotation(Name.class).value());

			if (!getAttributes().containsKey("style") && getJavaMethod().isAnnotationPresent(Color.class))
				getAttributes().put("style", String.format("color: %s", getJavaMethod().getAnnotation(Color.class).value()));

			if (checkAccess())
			{
				pageContext.getOut().print("<li " + getAttributes() + ">");
				if ("POST".equalsIgnoreCase(getMethod()))
				{
					Attributes atrributes = new Attributes();
					atrributes.put("formaction", getURL());
					if (getTabindex() != null)
						atrributes.put("tabindex", getTabindex());
					if (getTarget() != null)
						atrributes.put("formtarget", getTarget());

					pageContext.getOut().print("<button " + atrributes + ">");

					if (getJspBody() != null)
						getJspBody().invoke(null);
					else
						pageContext.getOut().print(String.format("%s<i>&#X%s;</i>", getName(), getIcon().getCode()));

					pageContext.getOut().print("</button>");
				} else
				{
					Attributes atrributes = new Attributes();
					atrributes.put("href", getURL());
					if (getTabindex() != null)
						atrributes.put("tabindex", getTabindex());
					if (getTarget() != null)
						atrributes.put("target", getTarget());

					pageContext.getOut().print("<a " + atrributes + ">");

					if (getJspBody() != null)
						getJspBody().invoke(null);
					else
						pageContext.getOut().print(String.format("%s<i>&#X%s;</i>", getName(), getIcon().getCode()));

					pageContext.getOut().print("</a>");
				}
				pageContext.getOut().print("</li>");
			} else if (fixed)
			{
				pageContext.getOut().print("<li " + getAttributes() + ">");

				Attributes atrributes = new Attributes();
				atrributes.put("href", "#");
				atrributes.put("data-disabled", "true");
				if (getTabindex() != null)
					atrributes.put("tabindex", getTabindex());

				pageContext.getOut().print("<a " + atrributes + ">");
				if (getJspBody() != null)
					getJspBody().invoke(null);
				else
					pageContext.getOut().print(String.format("%s<i>&#X%s;</i>", getName(), getIcon().getCode()));

				pageContext.getOut().print("</a>");
				pageContext.getOut().print("</li>");
			}
		}
	}
}
