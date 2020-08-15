/*******************************************************************************
 * Copyright (c) 2020, 2020 Alex.
 ******************************************************************************/
package com.alex.demo.multicast;

import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author Alex
 * @Created Dec 2020/5/9 18:41
 * @Description
 *              <p>
 *              测试组播发送与接收
 */
@Slf4j
public class MulticastTest {

	private static MulticastSender sender;

	private static MulticastReceiver receiver;

	private static String multicastIP = "239.0.0.1";

	private static int i = 0;

	private static MulticastSocket socket;

	public static void main(String[] args) throws Exception {
		socket = initMulticastSocket(multicastIP, 65501);
		sender = new MulticastSender();
		receiver = new MulticastReceiver();
		try {
			while (i < 10) {
				byte[] data = ("Multicast-Test" + (i++)).getBytes();
				sender.startSend(multicastIP, socket, data);
				receiver.startReceive(socket, multicastIP);
			}
			receiver.close();
			sender.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private static MulticastSocket initMulticastSocket(String ip, int port) throws Exception {
		// 设置组播组的地址
		InetAddress group = InetAddress.getByName(ip);
		// 创建组播套接字，与端口关联
		MulticastSocket socket = new MulticastSocket(port);
		socket.setTimeToLive(1); // 设置组播数据报的发送范围为本地网络
		socket.setSoTimeout(10000); // 设置套接字的接收数据报的最长时间，单位ms
		// 若有多个网卡，则要设置从哪个网卡接收数据
		NetworkInterface ni = NetworkInterface.getByName("eth1");
		socket.setNetworkInterface(ni);
		// 加入此组播组
		socket.joinGroup(group);
		return socket;
	}
}
