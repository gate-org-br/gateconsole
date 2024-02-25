package gateconsole.modules.admin;

import gate.annotation.Authorization;
import gate.annotation.Secure;
import gate.lang.json.JsonElement;
import gate.lang.json.JsonString;
import gateconsole.contol.UserControl;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("users")
@Authorization(module = "gate.modulos.admin", screen = "User")
public class UserService
{

	@Inject
	private UserControl control;

	@GET
	@Secure
	@Produces(MediaType.APPLICATION_JSON)
	public JsonElement search(@QueryParam("criteria") String criteria)
	{
		return criteria != null && criteria.length() >= 3
			? control.search(criteria)
			: JsonString.of("\"Entre com  no mínimo 3 caracteres\"");
	}

}
