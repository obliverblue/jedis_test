package connection;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Protocol;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.util.Optional;

/**
 *
 * @since 2019/4/29
 */
public interface JedisClientConfiguration
{

	boolean isUsingSsl();

	boolean isUsingPool();

	/**
	 * host
	 * @return
	 */
	default String getHost()
	{
		return Protocol.DEFAULT_HOST;
	}


	/**
	 * port
	 * @return
	 */
	default int getPort()
	{
		return Protocol.DEFAULT_PORT;
	}

	/**
	 * password
	 *
	 * @return
	 */
	default String getPassword()
	{
		return null;
	}


	GenericObjectPoolConfig getPoolConfig();


	int getConnectionTimeout();

	int getSoTimeout();

	default int getDatabase()
	{
		return Protocol.DEFAULT_DATABASE;
	}

	String getClientName();


	Optional<SSLSocketFactory> getSslSocketFactory();


	Optional<SSLParameters> getSslParameters();

	Optional<HostnameVerifier> getHostnameVerifier();

}
