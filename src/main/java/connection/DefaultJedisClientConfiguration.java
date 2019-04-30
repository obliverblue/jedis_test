package connection;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Protocol;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.util.Optional;

/**
 * @since 2019/4/29
 */
public class DefaultJedisClientConfiguration implements JedisClientConfiguration
{

	private final boolean usingSsl;
	private final boolean usingPool;
	private final GenericObjectPoolConfig poolConfig;
	private final String clientName;
	private final int readTimeout;
	private final int connectTimeout;
	private final Optional<SSLSocketFactory> sslSocketFactory;
	private final Optional<SSLParameters> sslParameters;
	private final Optional<HostnameVerifier> hostnameVerifier;


	public DefaultJedisClientConfiguration(GenericObjectPoolConfig poolConfig)
	{
		this(false, true, poolConfig, null, Protocol.DEFAULT_TIMEOUT, Protocol.DEFAULT_TIMEOUT, null, null, null);
	}

	public DefaultJedisClientConfiguration(boolean usingSsl, boolean usingPool, GenericObjectPoolConfig poolConfig, String clientName, int readTimeout, int connectTimeout, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier)
	{
		this.usingSsl = usingSsl;
		this.usingPool = usingPool;
		this.poolConfig = poolConfig;
		this.clientName = clientName;
		this.readTimeout = readTimeout;
		this.connectTimeout = connectTimeout;
		this.sslSocketFactory = Optional.ofNullable(sslSocketFactory);
		this.sslParameters = Optional.ofNullable(sslParameters);
		this.hostnameVerifier = Optional.ofNullable(hostnameVerifier);
	}

	@Override
	public boolean isUsingSsl()
	{
		return usingSsl;
	}

	@Override
	public boolean isUsingPool()
	{
		return usingPool;
	}

	@Override
	public GenericObjectPoolConfig getPoolConfig()
	{
		return poolConfig;
	}


	@Override
	public int getConnectionTimeout()
	{
		return connectTimeout;
	}

	@Override
	public int getSoTimeout()
	{
		return readTimeout;
	}

	@Override
	public String getClientName()
	{
		return clientName;
	}

	@Override
	public Optional<SSLSocketFactory> getSslSocketFactory()
	{
		return sslSocketFactory;
	}

	@Override
	public Optional<SSLParameters> getSslParameters()
	{
		return sslParameters;
	}

	@Override
	public Optional<HostnameVerifier> getHostnameVerifier()
	{
		return hostnameVerifier;
	}


}
