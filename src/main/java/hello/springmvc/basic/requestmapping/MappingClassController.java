package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {
	
	@GetMapping
	public String user() {
		return "get users";
	}
	
	@PostMapping
	public String addUser() {
		return "add user";
	}
	
	@GetMapping("/{userId}")
	public String findUser() {
		return "find User";
	}
	
	@PatchMapping("/{userId}")
	public String updateUser() {
		return "update user";
	}
	
	@DeleteMapping("/{userId}")
	public String deleteUser() {
		return "delete user";
	}
}
