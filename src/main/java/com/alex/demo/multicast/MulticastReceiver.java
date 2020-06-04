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
 * @Created Dec 2020/5/9 14:11
 * @Description
 *              <p>
 *              组播接收
 */
@Slf4j
public class MulticastReceiver {

	private MulticastSocket socket;

	public void startReceive(MulticastSocket socket, String multicastIP) throws Exception {
		this.socket = socket;
		InetAddress group = InetAddress.getByName(multicastIP);
		byte[] buffer = new byte[512];// 接收内容的大小是自定义的
		// 建立一个指定缓冲区大小的数据包
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, socket.getLocalPort());
		socket.receive(packet); // 通过MulticastSocket实例端口从组播组接收数据

		byte[] data = packet.getData();// 接收到的数据
		System.out.println("received data-- " + new String(data));
	}

	public void close() {
		if (socket != null) {
			socket.close();
		}
	}

}
