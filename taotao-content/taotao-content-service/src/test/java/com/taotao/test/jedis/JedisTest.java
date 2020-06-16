package com.taotao.test.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	//测试单机版
	@Test
	public void textJedis() {
		//1穿创建Jedis对象，需要指定链接的地址和端口
		Jedis jedis = new Jedis("47.92.48.144",6379);
		//2直接操作redis  set
		jedis.set("key123","calue");
		System.out.println(jedis.get("key123"));
		//3关闭Jedis
		jedis.close();
	}
	@Test
	public void teatJedisPool() {
		//1创建jedispool对象需要指定端口和地址
		JedisPool pool = new JedisPool("47.92.48.144",6379);
		//2获取redis的对象
		Jedis jedis = pool.getResource();
		//3直接操作redis
		jedis.set("keypool","keeypool");
		System.out.println(jedis.get("keypool"));
		//4关闭redis（释放资源到连接池）
		jedis.close();
		//5关闭连接池（应用西戎关闭的时候才关闭）
		pool.close();
	}
	
	//测试集群
	public void testjedisCluster() {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("47.92.48.144",7001));
		nodes.add(new HostAndPort("47.92.48.144",7002));
		nodes.add(new HostAndPort("47.92.48.144",7003));
		nodes.add(new HostAndPort("47.92.48.144",7004));
		nodes.add(new HostAndPort("47.92.48.144",7005));
		nodes.add(new HostAndPort("47.92.48.144",7006));
		//1创建jediscluster
		JedisCluster cluster = new JedisCluster(nodes);
		//2直接根据jediscluster对象操作redis集群
		cluster.set("keycluster","keycluster的calue");
		System.out.println(cluster.get("keycluster"));
		//3关闭jediscluster对象(是在应用系统关闭的时候关闭)封装了连接池
		cluster.close();
	}
}
