import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;


public class MongoUtil {

	MongoClient mongoClient;
	
//	MongoCredential credential = MongoCredential.createCredential(userName, database, password);
//	MongoClient mongoClient = new MongoClient(new ServerAddress(), Arrays.asList(credential));

	
	//MongoClient mongoClient = new MongoClient();
	public MongoUtil() throws UnknownHostException {
		this.mongoClient = new MongoClient();
	}
	
	//MongoClient mongoClient = new MongoClient( "localhost" );
	public MongoUtil(String hostname) throws UnknownHostException {
		this.mongoClient = new MongoClient(hostname);
	}
	
	//	MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	public MongoUtil(String hostname, int port) throws UnknownHostException {
		this.mongoClient = new MongoClient(hostname, port);
	}
	
	//	MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
	//    new ServerAddress("localhost", 27018),
	//    new ServerAddress("localhost", 27019)));
	public MongoUtil(List serverAddresses) throws UnknownHostException {
		this.mongoClient = new MongoClient(serverAddresses);
	}
	
	public MongoClient getMongoClient() {
		return this.mongoClient;
	}
}
