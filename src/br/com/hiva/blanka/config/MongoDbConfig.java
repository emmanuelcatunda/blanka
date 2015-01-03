package br.com.hiva.blanka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import br.com.hiva.blanka.model.Note;
import br.com.hiva.blanka.repository.NotesRepository;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableMongoRepositories({"br.com.hiva.blanka.repository"})
public class MongoDbConfig extends AbstractMongoConfiguration {

	@Autowired
	Environment enviroment;
	
	@Override
	protected String getDatabaseName() {
		return enviroment.getProperty("mongoDb.dbName");
	}
   
	 @Override
	    protected String getMappingBasePackage() {
	        return "br.com.hiva.blanka.repository";
	    }
	
	@Bean
	public Mongo mongo() throws Exception {
		Mongo db =    new MongoClient(enviroment.getProperty("mongoDb.host") ,Integer.parseInt(enviroment.getProperty("mongoDb.port")));
		return db;
	}
	
	@Bean
	public MongoOperations mongoOperations() throws Exception{
		MongoOperations mongoOperations = new MongoTemplate(mongo(), enviroment.getProperty("mongoDb.dbName"));
		return mongoOperations;
	}
	
	@Bean
	public LocalValidatorFactoryBean validator() {
	    return new LocalValidatorFactoryBean();
	}
	
	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener() {
	    return new ValidatingMongoEventListener(validator());
	}
	

	public MongoRepository<Note,String> noteRepository() throws Exception{
		MongoRepository noteRepository = new MongoRepositoryFactory(mongoOperations()).getRepository(NotesRepository.class);
		return noteRepository;
	}
	
}
