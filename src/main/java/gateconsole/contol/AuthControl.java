package gateconsole.contol;

import gate.annotation.DataSource;
import gate.base.Control;
import gate.constraint.Constraints;
import gate.entity.Auth;
import gate.error.AppException;
import gate.error.NotFoundException;
import gate.sql.Link;
import gate.sql.LinkSource;
import gate.type.ID;
import gateconsole.dao.AuthDao;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class AuthControl extends Control
{

	@Inject
	@DataSource("Gate")
	LinkSource linksource;

	public Collection<Auth> search(Auth auth)
	{
		try ( Link link = linksource.getLink();
			 AuthDao dao = new AuthDao(link))
		{
			return dao.search(auth);
		}
	}

	public Auth select(ID id) throws NotFoundException
	{
		try ( Link link = linksource.getLink();
			 AuthDao dao = new AuthDao(link))
		{
			return dao.select(id);
		}
	}

	public void insert(Auth model) throws AppException
	{
		Constraints.validate(model, "access", "scope", "module", "screen", "action");
		if (Stream.of(model.getRole().getId(),
			model.getUser().getId(),
			model.getFunc().getId())
			.filter(Objects::nonNull).count() != 1)
			throw new AppException("Selecione um usuário, perfil ou função para o acesso.");
		if (model.getUser().getId() != null
			&& model.getScope() == Auth.Scope.PUBLIC)
			throw new AppException("Acessos de usuário não podem ser públicos.");

		try ( Link link = linksource.getLink();
			 AuthDao dao = new AuthDao(link))
		{
			dao.insert(model);
		}
	}

	public void update(Auth model) throws AppException
	{
		try ( Link link = linksource.getLink();
			 AuthDao dao = new AuthDao(link))
		{
			Constraints.validate(model, "access", "scope", "module", "screen", "action");

			if (Stream.of(model.getRole().getId(),
				model.getUser().getId(),
				model.getFunc().getId())
				.filter(Objects::nonNull).count() != 1)
				throw new AppException("Selecione um usuário, perfil ou função para o acesso.");

			if (model.getUser().getId() != null
				&& model.getScope().equals(Auth.Scope.PUBLIC))
				throw new AppException("Acessos de usuário não podem ser públicos.");
			dao.update(model);
		}
	}

	public void delete(Auth model) throws AppException
	{

		try ( Link link = linksource.getLink();
			 AuthDao dao = new AuthDao(link))
		{
			dao.delete(model);
		}
	}
}
