package com.whotel.aliyun;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;

public class ComsumerDemo {
	public static void main(String[] args) {
		CloudAccount account = new CloudAccount("LTAIHAAcdeqQwBK1", "pgHtruHOEj8xApITwqzrsV2jPzvZjq",
				"http://35664445.mns.cn-shenzhen.aliyuncs.com/");
		MNSClient client = account.getMNSClient();
		
		try {
			CloudQueue queue = client.getQueueRef("eyt2wx");
			for (int i = 0; i < 8474; i++) {
				
				Message popMsg = queue.popMessage();
				if (popMsg != null) {
					System.out.println("message handle: " + popMsg.getReceiptHandle());
					System.out.println("message body: " + new String(popMsg.getMessageBodyAsRawBytes()));
					System.out.println("message id: " + popMsg.getMessageId());
					System.out.println("message dequeue count:" + popMsg.getDequeueCount());
					queue.deleteMessage(popMsg.getReceiptHandle());
					System.out.println("delete message successfully.\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		client.close();
	}
}
