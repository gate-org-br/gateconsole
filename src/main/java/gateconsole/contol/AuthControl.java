package gateconsole.contol;

import gate.base.Control;
import gate.constraint.Constraints;
import gate.entity.Auth;
import gate.error.AppException;
import gate.error.NotFoundException;
import gate.type.ID;
import gateconsole.dao.AuthDao;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

public class AuthControl extends Control
{

	public Collection<Auth> search(Auth auth)
	{
		try ( AuthDao dao = new AuthDao())
		{
			return dao.search(auth);
		}
	}

	public Auth select(ID id) throws NotFoundException
	{
		try ( AuthDao dao = new AuthDao())
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

		try ( AuthDao dao = new AuthDao())
		{
			dao.insert(model);
		}
	}

	public void update(Auth model) throws AppException
	{
		try ( AuthDao dao = new AuthDao())
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

		try ( AuthDao dao = new AuthDao())
		{
			dao.delete(model);
		}
	}
}
