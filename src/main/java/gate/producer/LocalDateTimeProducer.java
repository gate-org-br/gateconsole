package gate.producer;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 * @author davins
 *
 * Produces LocalDateTime objects with current Date and Time
 */
@Dependent
public class LocalDateTimeProducer implements Serializable
{

	@Produces
	@Named("dateTime")
	public LocalDateTime dateTime()
	{
		return LocalDateTime.now();
	}
}
