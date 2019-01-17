package gate.sql.delete;

import gate.Contact;
import gate.Mock;
import gate.Person;
import gate.error.ConstraintViolationException;
import gate.sql.Link;
import gate.sql.TestDataSource;
import gate.sql.condition.Condition;
import gate.sql.select.Select;
import gate.type.ID;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DeleteTest
{

	@BeforeClass
	public static void setUp()
	{
		TestDataSource.getInstance().setUp();
	}

	@Test
	public void test1()
	{
		String expected = "delete from Uzer where id = ? and name like ?";

		String result = Delete.from("Uzer")
			.where(Condition.of("id").eq()
				.and("name").lk())
			.build()
			.toString();

		Assert.assertEquals(expected, result);
	}

	@Test
	public void test2()
	{
		String expected = "delete from Uzer where id = ? and name like ?";

		String result = Delete.from("Uzer")
			.where(Condition.of("id").eq(1)
				.and("name").lk("Paulo"))
			.build()
			.toString();

		Assert.assertEquals(expected, result);
	}

	@Test
	public void test3()
	{
		String expected = "delete from Mock where id = ?";
		String result = Delete.from(Mock.class).build().toString();

		Assert.assertEquals(expected, result);
	}

	@Test
	public void test4()
	{
		try (Link link = TestDataSource.getInstance().getLink())
		{
			try
			{
				link
					.prepare(Delete.from(Person.class))
					.value(new Person().setId(1))
					.execute();

				Assert.assertTrue(Select.expression("count(*)").from("Person")
					.where(Condition.of("id").isEq(new ID(1).toString())).build()
					.connect(link).fetchObject(Integer.class).get().equals(0));
			} catch (NullPointerException e)
			{
				Assert.fail();
			}
		} catch (SQLException | ConstraintViolationException ex)
		{
			Logger.getLogger(DeleteTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Test
	public void test5()
	{
		String expected = "delete from Contact where 0 = 0 and Person$id = ?";
		String result = Delete.of(Contact.class, "=person.id").toString();

		Assert.assertEquals(expected, result);
	}

	@AfterClass
	public static void clean()
	{
		TestDataSource.getInstance().clean();
	}
}
