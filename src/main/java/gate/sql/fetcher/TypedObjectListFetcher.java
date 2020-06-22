package gate.sql.fetcher;

import gate.sql.Cursor;
import java.util.ArrayList;
import java.util.List;

public class TypedObjectListFetcher<T> implements Fetcher<List<T>>
{

	private final Class<T> type;

	public TypedObjectListFetcher(Class<T> type)
	{
		this.type = type;
	}

	@Override
	public List<T> fetch(Cursor rs)
	{
		List<T> result = new ArrayList<>();
		while (rs.next())
			result.add(rs.getValue(type, 1));
		return result;
	}
}
