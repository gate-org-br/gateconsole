package gate.io;

import gate.lang.csv.CSVParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.function.Consumer;

public class CSVProcessor implements Processor<List<String>>
{

	private final String charset;
	private final Consumer<List<String>> consumer;

	public CSVProcessor(String charset, Consumer<List<String>> consumer)
	{
		this.charset = charset;
		this.consumer = consumer;
	}

	public CSVProcessor(Consumer<List<String>> consumer)
	{
		this(Charset.defaultCharset().name(), consumer);
	}

	@Override
	public void process(InputStream is) throws IOException
	{
		new CSVParser(new BufferedReader(new InputStreamReader(is, getCharset())))
			.forEach(consumer);
	}

	@Override
	public String getCharset()
	{
		return charset;
	}

}
