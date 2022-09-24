package com.makerspace.database;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import com.makerspace.types.Project;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

public class Database {
	
	private static Database INSTANCE = new Database();
	public static Database getInstance() {
		return INSTANCE;
	}
	
	private MongoClient client;
	private MongoDatabase database;
	
	private MongoCollection<Project> projectCollection;
	
	private ReplaceOptions replaceUpsertOption = new ReplaceOptions().upsert(true);
	
	private Map<ObjectId, Project> projects = new HashMap<>();
	
	private Database() {
		Config config = Config.getInstance();
		
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder()
						.automatic(true)
						.build()));
		
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(config.getDatabaseUrl()))
				.codecRegistry(codecRegistry)
				.build();
		
		client = MongoClients.create(settings);
		database = client.getDatabase(config.getDatabaseName());
	}
	
	public Collection<Project> listProjects() {
		return projects.values();
	}
	
	public Project getProject(ObjectId id) {
		return projects.get(id);
	}
	
	public void addProject(Project project) {
		projects.put(project.id, project);
	}
	
	private void updateProject(Project project) {
		projectCollection.replaceOne(Filters.eq("_id", project.id), project, replaceUpsertOption);
	}
	
	public void load() {
		projectCollection = database.getCollection("projects", Project.class);
		projectCollection.find().forEach((Project project) -> { projects.put(project.id, project); });
	}
	
	public void save() {
		projects.values().forEach(this::updateProject);
	}
	
}
