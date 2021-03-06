package gateconsole.dao;

import gate.base.Dao;
import gate.entity.Role;
import gate.entity.User;
import gate.sql.Command;
import gate.sql.Cursor;
import gate.sql.Link;
import gate.type.ID;
import java.util.ArrayList;
import java.util.List;

public class SearchDao extends Dao
{

	public SearchDao(Link link)
	{
		super(link);
	}

	public List<Object> search(String text)
	{
		try ( Command command = getLink()
			.from(getClass().getResource("SearchDao/search(String).sql"))
			.parameters(text,
				text,
				String.format("%%%s%%", text),
				text,
				text,
				String.format("%%%s%%", text))
			.createCommand())
		{
			List<Object> objects = new ArrayList<>();
			try ( Cursor cursor = command.getCursor())
			{
				while (cursor.next())
				{
					switch (cursor.getCurrentValue(Integer.class))
					{
						case 1:
							User user = new User();
							user.setId(cursor.getValue(ID.class, "id"));
							user.setUsername(cursor.getValue(String.class, "entityID"));
							user.setName(cursor.getValue(String.class, "name"));
							objects.add(user);
							break;
						case 2:
							Role role = new Role();
							role.setId(cursor.getValue(ID.class, "id"));
							role.setRolename(cursor.getValue(String.class, "entityID"));
							role.setName(cursor.getValue(String.class, "name"));
							objects.add(role);
							break;
					}
				}
			}
			return objects;
		}
	}
}
