import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;





public class MongoTutorial {
	
	public static void main(String[] args) throws UnknownHostException {
		MongoUtil util = new MongoUtil("localhost");
		
		MongoClient mongoClient = util.getMongoClient();
		
		// Get the database
		DB myDb = mongoClient.getDB("myDb");
		
		// Get the collection
		DBCollection coll = myDb.getCollection("testCollection");
		
		// Drop existing table (temporary)
		coll.drop();
		
		//JOURNALED: Exceptions are raised for network issues, and server errors; 
		//the write operation waits for the server to group commit to the journal file on disk.
		mongoClient.setWriteConcern(WriteConcern.JOURNALED);
		
		//Create object
		BasicDBObject doc = new BasicDBObject("name", "MongoDB")
        .append("type", "database")
        .append("count", 1)
        .append("info", new BasicDBObject("x", 203).append("y", 102));
		
		//Insert object
		coll.insert(doc);
		
		// Get a document
		DBObject myDoc = coll.findOne();
		System.out.println(myDoc);
		
		// Insert 100 objects
		for (int i=0; i < 100; i++) {
		    coll.insert(new BasicDBObject("i", i));
		}
		
		// Get the count of objects
		System.out.println(coll.getCount());

		// Get all documents, and iterate through them
		DBCursor cursor = coll.find();
		try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}

		// Find object #71 and print it
		BasicDBObject query = new BasicDBObject("i", 71);
		cursor = coll.find(query);
		try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		
		// find all j != 3, and k > 10 (there are none)
		query = new BasicDBObject("j", new BasicDBObject("$ne", 3))
        .append("k", new BasicDBObject("$gt", 10));
		cursor = coll.find(query);
		try {
		    while(cursor.hasNext()) {
		        System.out.println(cursor.next());
		    }
		} finally {
		    cursor.close();
		}
		
		// find all i, > 20 and <= 30 and print
		query = new BasicDBObject("i", new BasicDBObject("$gt", 20).append("$lte", 30));
		cursor = coll.find(query);
		try {
		    while (cursor.hasNext()) {
		        System.out.println(cursor.next());
		    }
		} finally {
		    cursor.close();
		}

		// 1. Ordered bulk operation
		BulkWriteOperation builder = coll.initializeOrderedBulkOperation();
		
		builder.insert(new BasicDBObject("_id", 1));
		builder.insert(new BasicDBObject("_id", 2));
		builder.insert(new BasicDBObject("_id", 3));

		builder.find(new BasicDBObject("_id", 1)).updateOne(new BasicDBObject("$set", new BasicDBObject("x", 2)));
		builder.find(new BasicDBObject("_id", 2)).removeOne();
		builder.find(new BasicDBObject("_id", 3)).replaceOne(new BasicDBObject("_id", 3).append("x", 4));

		BulkWriteResult result = builder.execute();

		// 2. Unordered bulk operation - no guarantee of order of operation
		builder = coll.initializeUnorderedBulkOperation();
		builder.find(new BasicDBObject("_id", 1)).removeOne();
		builder.find(new BasicDBObject("_id", 2)).removeOne();
		
		result = builder.execute();
	}

}
