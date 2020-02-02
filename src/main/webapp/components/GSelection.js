/* global */

class GSelection
{
	static getSelectedLink(nodes)
	{
		let parameters = URL.parse_query_string(window.location.href);
		let elements = Array.from(nodes)
			.filter(e => (e.href && e.href.includes('?'))
					|| (e.formaction && e.formaction.includes('?')));

		var q = elements.filter(e =>
		{
			let args = URL.parse_query_string(e.href || e.formaction);
			return args.MODULE === parameters.MODULE
				&& args.SCREEN === parameters.SCREEN
				&& args.ACTION === parameters.ACTION;
		});

		if (q.length === 0)
		{
			var q = elements.filter(e =>
			{
				var args = URL.parse_query_string(e.href || e.formaction);
				return args.MODULE === parameters.MODULE
					&& args.SCREEN === parameters.SCREEN;
			});

			if (q.length === 0)
			{
				q = elements.filter(e =>
				{
					var args = URL.parse_query_string(e.href || e.formaction);
					return args.MODULE === parameters.MODULE;
				});
			}
		}

		if (q.length !== 0)
			return q[0];
	}
}