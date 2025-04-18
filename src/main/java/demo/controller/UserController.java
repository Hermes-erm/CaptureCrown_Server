package demo.controller;

import demo.model.UserModel;
import demo.model.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
class APIresponse {
    public String message;
    public Object data;

}

@RestController
@NoArgsConstructor
public class UserController {

    @Autowired // fetch or get this bean/instance of UserRepo from spring container
    private UserRepo userRepo;

    @Autowired
    private MongoTemplate mt;

    @GetMapping("/get-users")
    public String getUsers() {
        return "welcome";
    }

    @PostMapping("/add-user")
    public ResponseEntity<APIresponse> addUser(@RequestBody UserModel userData) { // @RequestBody JsonNode userData
        userRepo.save(userData); // predefined class mapping.. with that class instance (with some specific types of data struct)..
//        mt.save(userData);
        APIresponse apIresponse = new APIresponse("Success", userData);
        return ResponseEntity.ok(apIresponse);
    }
}
