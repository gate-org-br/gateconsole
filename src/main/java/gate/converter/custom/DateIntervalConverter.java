package gate.converter.custom;

import gate.constraint.Constraint;
import gate.error.ConversionException;
import gate.constraint.Maxlength;
import gate.constraint.Pattern;
import gate.converter.Converter;
import gate.type.Date;
import gate.type.DateInterval;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DateIntervalConverter implements Converter
{

	private static final List<String> SUFIXES
		= Arrays.asList("date1", "date2");

	@Override
	public String getDescription()
	{
		return "Campos de intervalo de datas devem ser preenchidos no formato DD/MM/YYYY - DD/MM/YYYY";
	}

	@Override
	public String getMask()
	{
		return "##/##/#### - ##/##/####";
	}

	@Override
	public List<Constraint.Implementation<?>> getConstraints()
	{
		List<Constraint.Implementation<?>> constraints = new LinkedList<>();
		constraints.add(new Maxlength.Implementation(23));
		constraints.add(new Pattern.Implementation("^[0-9]{2}[/][0-9]{2}[/][0-9]{4}[ ][-][ ][0-9]{2}[/][0-9]{2}[/][0-9]{4}$"));
		return constraints;
	}

	@Override
	public Object ofString(Class<?> type, String string) throws ConversionException
	{
		if (string == null)
			return null;
		string = string.trim();
		if (string.isEmpty())
			return null;

		try
		{
			return DateInterval.of(string);
		} catch (ParseException ex)
		{
			throw new ConversionException(ex, String.format(getDescription()));
		}
	}

	@Override
	public String toText(Class<?> type, Object object)
	{
		return object != null ? object.toString() : "";
	}

	@Override
	public String toText(Class<?> type, Object object, String format)
	{
		return object != null ? ((DateInterval) object).format(format) : "";
	}

	@Override
	public String toString(Class<?> type, Object object)
	{
		return object != null ? ((DateInterval) object).format("dd/MM/yyyy") : "";
	}

	@Override
	public List<String> getSufixes()
	{
		return SUFIXES;
	}

	@Override
	public Object readFromResultSet(ResultSet rs, int fields, Class<?> type) throws SQLException, ConversionException
	{
		java.sql.Date value1 = rs.getDate(fields);
		if (rs.wasNull())
			return null;
		java.sql.Date value2 = rs.getDate(fields + 1);
		if (rs.wasNull())
			return null;
		return new DateInterval(Date.of(value1), Date.of(value2));
	}

	@Override
	public Object readFromResultSet(ResultSet rs, String fields, Class<?> type) throws SQLException
	{
		java.sql.Date value1 = rs.getDate(fields + ":" + SUFIXES.get(0));
		if (rs.wasNull())
			return null;
		java.sql.Date value2 = rs.getDate(fields + ":" + SUFIXES.get(1));
		if (rs.wasNull())
			return null;
		return new DateInterval(Date.of(value1), Date.of(value2));
	}

	@Override
	public int writeToPreparedStatement(PreparedStatement ps, int fields, Object value) throws SQLException
	{
		if (value != null)
		{
			ps.setDate(fields++, new java.sql.Date(((DateInterval) value).getMin().getValue()));
			ps.setDate(fields++, new java.sql.Date(((DateInterval) value).getMax().getValue()));
		} else
		{
			ps.setNull(fields++, Types.DATE);
			ps.setNull(fields++, Types.DATE);
		}
		return fields;
	}
}
