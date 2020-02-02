/* global customElements, GOverflow, Proxy */

class GCommands extends GOverflow
{
	constructor()
	{
		super();
		this.more.innerHTML = "<i>&#X3018;</i>";
		this.container.style.flexDirection = "row-reverse";
	}
}

customElements.define('g-commands', GCommands);