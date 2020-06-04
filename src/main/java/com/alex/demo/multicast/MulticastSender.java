/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.alex.demo.multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author Alex
 * @Created Dec 2020/5/9 18:37
 * @Description
 *              <p>
 *              组播发送
 */
@Slf4j
public class MulticastSender {

	private MulticastSocket socket;

	public void startSend(String groupUrl, MulticastSocket socket, byte[] data) throws Exception {
		this.socket = socket;
		InetAddress group = InetAddress.getByName(groupUrl);
		// 初始化DatagramPacket
		DatagramPacket packet = new DatagramPacket(data, data.length, group, socket.getLocalPort());
		// 通过MulticastSocket实例端口向组播组发送数据
		socket.send(packet);
		System.out.println("send data-- " + new String(data));
	}

	public void close() {
		if (socket != null) {
			socket.close();
		}
	}
}
