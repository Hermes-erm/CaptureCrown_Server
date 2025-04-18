package demo.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component // creates a bean and maintained by spring-IoC while component scanning
public interface UserRepo extends MongoRepository<UserModel, String> {
}
