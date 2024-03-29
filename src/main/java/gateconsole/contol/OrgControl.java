package gateconsole.contol;

import gate.annotation.DataSource;
import gate.base.Control;
import gate.constraint.Constraints;
import gate.entity.Org;
import gate.error.AppException;
import gate.sql.Link;
import gate.sql.LinkSource;
import gateconsole.dao.OrgDao;
import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class OrgControl extends Control
{

	@Inject
	@DataSource("Gate")
	LinkSource linksource;

	public Optional<Org> select()
	{
		try (Link link = linksource.getLink();
			OrgDao dao = new OrgDao(link))
		{
			return dao.select();
		}
	}

	public void update(Org model) throws AppException
	{
		Constraints.validate(model, "orgID", "name", "description", "authenticators", "sun", "mon", "tue", "wed", "fri", "thu", "sat");
		try (Link link = linksource.getLink();
			OrgDao dao = new OrgDao(link))
		{
			dao.getLink().beginTran();
			if (!dao.update(model))
				dao.insert(model);

			if (model.getIcon() != null)
				dao.update(model.getIcon());

			dao.getLink().commit();
		}
	}

	public Object dwload()
	{
		try (Link link = linksource.getLink();
			OrgDao dao = new OrgDao(link))
		{
			return dao.dwload();
		}
	}
}
