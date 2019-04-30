import connection.JedisConnectionFactory;

import java.util.Set;

/**
 * Created by Administrator on 2019/4/29.
 */
public class ConnectionTest
{
	public static void main(String[] args)
	{
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.init();
		Set<byte[]> ret = factory.getConnection().keyCommands().keys("*".getBytes());
		for(byte[] r : ret)
		{
			System.out.println(r);
		}
	}
}
