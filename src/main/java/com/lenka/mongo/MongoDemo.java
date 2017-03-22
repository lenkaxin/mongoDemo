package com.lenka.mongo;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class MongoDemo {

	public static void main(String[] args) throws UnknownHostException
	{
		MongoClient client = new MongoClient("127.0.0.1", 27017);
		DBCollection collection = client.getDB("test").getCollection("user");
		save(collection);
		delete(collection);
		update(collection);
		findOne(collection);
		listUser(collection);
	}

	public static void save(DBCollection collection)
	{
		BasicDBObject user = new BasicDBObject();
		user.put("_id", "1");
		user.put("name", "lenka");
		user.put("status", 1);

		WriteResult result = collection.save(user);
		if (result != null && result.getN() > 0) {
			System.out.println("数据插入成功");
		}
	}

	public static void delete(DBCollection collection)
	{
		BasicDBObject user = new BasicDBObject();
		user.put("_id", "1");

		// 删除ID为1的用户
		WriteResult result = collection.remove(user);
		if (result != null && result.getN() > 0) {
			System.out.println("数据删除成功");
		}
	}

	public static void update(DBCollection collection)
	{
		// 查询条件
		BasicDBObject query = new BasicDBObject();
		query.put("_id", "1");

		// 要修改的内容
		BasicDBObject update = new BasicDBObject();
		update.put("name", "lenka");

		// 修改ID为1的用户的"name"字段值为"lenka".第三个参数设置为true表示如果没有该字段则插入该字段,第四个参数设置为true表示更新所有符合条件的记录,false表示只更新最先找到的一条
		WriteResult result = collection.update(query, update, true, false);
		if (result != null && result.getN() > 0) {
			System.out.println("数据修改成功");
		}
	}

	public static void findOne(DBCollection collection)
	{
		BasicDBObject query = new BasicDBObject();
		query.put("_id", "1");

		DBObject user = collection.findOne(query);
		System.out.println(user);
	}

	public static void listUser(DBCollection collection)
	{
		BasicDBObject query = new BasicDBObject();
		query.put("status", 1);

		// 返回游标，对游标进行遍历
		DBCursor cursor = collection.find(query, null, 0, 100);
		while (cursor.hasNext()) {
			DBObject user = cursor.next();
			System.out.println(user);
		}
	}
}
