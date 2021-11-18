package one.microstream.communication.binarydynamic.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import one.microstream.communication.binarydynamic.ComBinaryDynamic;
import one.microstream.communication.tls.ComTLSConnectionHandler;
import one.microstream.communication.tls.SecureRandomProvider;
import one.microstream.communication.tls.TLSKeyManagerProvider;
import one.microstream.communication.tls.TLSParametersProvider;
import one.microstream.communication.tls.TLSTrustManagerProvider;
import one.microstream.communication.types.ComChannel;
import one.microstream.communication.types.ComClient;
import one.microstream.meta.XDebug;

public class MainTestComTLSClientDynamic
{
	public static void main(final String[] args)
	{		
		Path clientKeyStore = Paths.get(args[0]);
		Path clientTrustStore = Paths.get(args[1]);
				
		final ComClient<?> client = ComBinaryDynamic.Foundation()
			.setConnectionHandler(ComTLSConnectionHandler.New(
				new TLSKeyManagerProvider.PKCS12(
					clientKeyStore,
					args[2].toCharArray()),
				new TLSTrustManagerProvider.PKCS12(
					clientTrustStore,
					args[2].toCharArray()),
				new TLSParametersProvider.Default(),
				new SecureRandomProvider.Default()
			))
			.setClientConnectTimeout(10000)
			.createClient();
								
		
		// create a channel by connecting the client
		final ComChannel channel = client.connect(5, Duration.ofMillis(1000));
		
		final Object o = channel.receive();
		
		if(o != null)
		{
			XDebug.println("received:\n" + o.toString());
		}
		else
		{
			XDebug.println("received: null\n");
		}
		
		final Object o2 = channel.receive();
		
		if(o2 != null)
		{
			XDebug.println("received:\n" + o2.toString());
		}
		else
		{
			XDebug.println("received: null\n");
		}
		
		
		channel.send("exit");
		channel.close();
	}
}
