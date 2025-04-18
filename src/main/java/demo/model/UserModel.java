package demo.model;

// import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data // @Getter, @Setter, Etc..
@NoArgsConstructor
@Document(collection = "User")
public class UserModel {

    @Id
    private String id;
    private int age;
    private String name;

    public UserModel(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Bean
    public UserModel UserModel() { // creates an instance of this class (bean) and store it in spring container.. then you can inject this bean into another component by @Autowired..
        return new UserModel();
    }
}