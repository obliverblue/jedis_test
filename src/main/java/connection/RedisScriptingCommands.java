package connection;

import support.ReturnType;

import java.util.List;

/**
 * 执行脚本相关的命令（主要是lua脚本）
 * @author obliverblue
 * @since 2019/4/25
 * @see <a href="https://redis.io/commands#scripting">script commands</a>
 */
public interface RedisScriptingCommands
{


	/**
	 * 执行lua脚本
	 *  EVAL script numkeys key [key ...] arg [arg ...]
	 *
	 *  Example :
	 *      eval "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}" 2 key1 key2 first second
	 * @param scripts a Lua 5.1 script.
	 * @param returnType 返回值类型
	 * @param numkeys   用于指定键名参数的个数
	 * @param keysAndArgs 表示在脚本中所用到的那些 Redis键(key) 和  附加参数，在 Lua 中通过全局变量 ARGV 数组访问
	 * @param <T>
	 * @return
	 */
	<T> T eval(byte[] scripts, ReturnType returnType, int numkeys, byte[]... keysAndArgs);

	/**
	 *  根据给定的 sha1 校验码，执行缓存在服务器中的脚本
	 *  Evaluates a script cached on the server side by its SHA1 digest
	 *
	 *  EVALSHA sha1 numkeys key [key ...] arg [arg ...]
	 *
	 * @param scriptSha
	 * @param returnType
	 * @param numkeys
	 * @param keysAndArgs
	 * @param <T>
	 * @return
	 */
	<T> T evalSha(String scriptSha, ReturnType returnType, int numkeys, byte[]... keysAndArgs);


	/**
	 * 从脚本缓存中移除所有脚本
	 *
	 */
	void scriptFlush();

	/**
	 * kill当前正在运行的 Lua 脚本
	 *
	 */
	void scriptKill();

	/**
	 * 将脚本 script 添加到脚本缓存中，但并不立即执行这个脚本
	 * @param script
	 * @return
	 */
	String scriptLoad(byte[] script);

	/**
	 * 查看指定的脚本是否已经被保存在缓存当中
	 *
	 * @param scriptShas
	 * @return
	 */
	List<Boolean> scriptExists(String... scriptShas);
}
