package com.yan.zhihu.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.yan.common.mongodb.MongoDBConfig;
import com.yan.common.util.SchameDocumentUtil;
import com.yan.zhihu.model.ZhiHuPeopleCollection;

public class ZhiHuPeopleCollectionMongoDaoUtil {

	private MongoDBConfig dataSource;
	
	public MongoDBConfig getDataSource() {
		return dataSource;
	}

	public void setDataSource(MongoDBConfig dataSource) {
		this.dataSource = dataSource;
	}
	
	public String insertZhiHuPeopleCollection(ZhiHuPeopleCollection zhiHuPeopleCollection){

		//To connect to a single MongoDB instance:
	    //You can explicitly specify the hostname and the port:
		MongoCredential credential = MongoCredential.createCredential(dataSource.getUser(), dataSource.getDbUserDefined(), dataSource.getPassword().toCharArray());
		MongoClient mongoClient = new MongoClient(new ServerAddress(dataSource.getIp(), dataSource.getPort()),
		                                         Arrays.asList(credential));
		//Access a Database
		MongoDatabase database = mongoClient.getDatabase(dataSource.getDatabase());
		
		//Access a Collection
		MongoCollection<Document> collection = database.getCollection("ZhiHuPeopleCollection");
		
		//Create a Document
		Document doc = SchameDocumentUtil.schameToDocument(zhiHuPeopleCollection, ZhiHuPeopleCollection.class);

		//Insert a Document
		collection.insertOne(doc);
		
		//System.out.println("id:" + doc.get("_id"));
		String id = null;
		if(doc.get("_id") != null){
			id = doc.get("_id").toString();
		}
		mongoClient.close();
		return id;
	}
	
	public ZhiHuPeopleCollection findZhiHuPeopleCollectionById(String id) {
		ZhiHuPeopleCollection zhiHuPeopleCollection = null;
		if(id!= null && !"".equals(id.trim())) {
			//To connect to a single MongoDB instance:
			//You can explicitly specify the hostname and the port:
			MongoCredential credential = MongoCredential.createCredential(dataSource.getUser(), dataSource.getDbUserDefined(), dataSource.getPassword().toCharArray());
			MongoClient mongoClient = new MongoClient(new ServerAddress(dataSource.getIp(), dataSource.getPort()),
			                                         Arrays.asList(credential));
			//Access a Database
			MongoDatabase database = mongoClient.getDatabase(dataSource.getDatabase());
			
			//Access a Collection
			MongoCollection<Document> collection = database.getCollection("ZhiHuPeopleCollection");
			
			List<Document> docs = collection.find(Filters.eq("_id", new ObjectId(id))).into(new ArrayList<Document>());
			if(docs != null && docs.size() > 0) {
				zhiHuPeopleCollection = (ZhiHuPeopleCollection)SchameDocumentUtil.documentToSchame(docs.get(0), ZhiHuPeopleCollection.class);
			}
			mongoClient.close();
		}
		
		return zhiHuPeopleCollection;
	}
	
	public ZhiHuPeopleCollection findZhiHuPeopleCollectionByUserIdAndCollectionId(String userId, String collectionId) {
		ZhiHuPeopleCollection zhiHuPeopleCollection = null;
		if(userId!= null && !"".equals(userId.trim())) {
			//To connect to a single MongoDB instance:
			//You can explicitly specify the hostname and the port:
			MongoCredential credential = MongoCredential.createCredential(dataSource.getUser(), dataSource.getDbUserDefined(), dataSource.getPassword().toCharArray());
			MongoClient mongoClient = new MongoClient(new ServerAddress(dataSource.getIp(), dataSource.getPort()),
			                                         Arrays.asList(credential));
			//Access a Database
			MongoDatabase database = mongoClient.getDatabase(dataSource.getDatabase());
			
			//Access a Collection
			MongoCollection<Document> collection = database.getCollection("ZhiHuPeopleCollection");
			
			List<Bson> bsons = new ArrayList<Bson>(0);
			bsons.add(Filters.eq("userId", userId));
			bsons.add(Filters.eq("collectionId", collectionId));
			
			List<Document> docs = collection.find(Filters.and(bsons)).into(new ArrayList<Document>());
			if(docs != null && docs.size() > 0) {
				zhiHuPeopleCollection = (ZhiHuPeopleCollection)SchameDocumentUtil.documentToSchame(docs.get(0), ZhiHuPeopleCollection.class);
			}
			mongoClient.close();
		}
		
		return zhiHuPeopleCollection;
	}
	
	public void updateZhiHuPeopleCollection(ZhiHuPeopleCollection zhiHuPeopleCollection){
		//To connect to a single MongoDB instance:
	    //You can explicitly specify the hostname and the port:
		MongoCredential credential = MongoCredential.createCredential(dataSource.getUser(), dataSource.getDbUserDefined(), dataSource.getPassword().toCharArray());
		MongoClient mongoClient = new MongoClient(new ServerAddress(dataSource.getIp(), dataSource.getPort()),
		                                         Arrays.asList(credential));
		//Access a Database
		MongoDatabase database = mongoClient.getDatabase(dataSource.getDatabase());
		
		//Access a Collection
		MongoCollection<Document> collection = database.getCollection("ZhiHuPeopleCollection");
		
		
		//Create a Document
		 Document doc = SchameDocumentUtil.schameToDocument(zhiHuPeopleCollection, ZhiHuPeopleCollection.class);
		 
		 //Update a Document
		 collection.updateOne(Filters.eq("_id", doc.get("_id")), new Document("$set", doc));
		 mongoClient.close();
	}
	
}
