package demo.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
class Config {

    @Autowired
    private ApplicationContext applicationContext;

//    Config(ApplicationContext applicationContext) { // add final before ApplicationContext to ensure immutable and threadsafe bean..!
//        this.applicationContext = applicationContext;
//    }

    @PostConstruct
    public void init() {
        MappingMongoConverter mappingMongoConverter = applicationContext.getBean(MappingMongoConverter.class);
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }
}