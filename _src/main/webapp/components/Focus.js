function autofocus(d)
{
	var elements = Array.from(d.querySelectorAll('[autofocus]'));
	if (elements.length !== 0)
		return elements[0].focus();

	var elements = Array.from(d.querySelectorAll('[tabindex]'))
		.filter(function (e)
		{
			return Number(e.getAttribute("tabindex")) > 0;
		});

	if (elements.length === 0)
		return -1;

	var element = elements.reverse().reduce(function (a, b)
	{
		return  Number(a.getAttribute("tabindex")) < Number(b.getAttribute("tabindex")) ? a : b;
	});

	if (element)
	{
		element.focus();
		if (element.onfocus)
			element.onfocus();
	}
}

window.addEventListener("load", function ()
{
	autofocus(document);
});