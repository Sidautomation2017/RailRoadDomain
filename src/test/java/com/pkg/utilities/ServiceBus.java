	package com.pkg.utilities;
	import java.util.concurrent.ExecutionException;

	import com.microsoft.azure.servicebus.management.*;
	import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;

public class ServiceBus{
					
		public static long getActiveCount() throws InterruptedException, ExecutionException{
			
		String connction="Endpoint=sb://cm-kcsr-mmcs-qa-sbus.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=slcOsQp40inlT/6K8tO9vP4vy9jhC5G5r+nXChbgxps=";

		ManagementClientAsync mc=new ManagementClientAsync(new ConnectionStringBuilder(connction));
		QueueRuntimeInfo qr=mc.getQueueRuntimeInfoAsync("mmcsrwbib").get();
		Thread.sleep(5000);
		long activecount=qr.getMessageCountDetails().getActiveMessageCount();
		System.out.println("Queue Active Count is:"+activecount);
		//System.out.println(qr.getMessageCountDetails().getDeadLetterMessageCount());
		return activecount;
		
					
		}
		
	}


