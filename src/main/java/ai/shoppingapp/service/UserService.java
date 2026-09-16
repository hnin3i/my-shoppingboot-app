package ai.shoppingapp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ai.shoppingapp.model.Role;
import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.model.usermanagement.LoginModel;
import ai.shoppingapp.model.usermanagement.RegisterModel;
import ai.shoppingapp.repository.UserRepository;
import ai.shoppingapp.repository.entity.User;

@Service
public class UserService {
	private final UserRepository userRepo;
	private final PasswordService passwordService;

	
	public UserService(UserRepository userRepo, PasswordService passwordService) {
		this.userRepo = userRepo;
		this.passwordService = passwordService;
	}

	public List<UserModel> findAll() {
		List<User> entities = this.userRepo.findAll();
		return entities.stream().map(this::toModel).toList();
	}

	public UserModel findById(String id) {
		User entity = this.userRepo.findById(id);
		if (entity == null) return null;
		return toModel(entity);
	}
	
	public int register(RegisterModel model) {
		if (userRepo.findByEmail(model.getEmail()) != null) {
            return 0; // email already exists
        }
		User entity=toEntity(model);
		return this.userRepo.save(entity);
	}
	
	public UserModel login(LoginModel model){
		User user=this.userRepo.findByEmail(model.getEmail());
		
		if (user != null && passwordService.matches(model.getPassword(), user.getPassword())){
													//rawPass				encodePass
			UserModel loginUser=toModel(user);
			return loginUser;
		}
		
		return null;
	}
	
	
	private UserModel toModel(User entity) {
		UserModel model = new UserModel();
		model.setId(entity.getId());
		model.setName(entity.getName());
		model.setEmail(entity.getEmail());
		model.setPhone(entity.getPhone());
		String role = entity.getRole();
		if (role != null) model.setRole(Role.valueOf(role));
		model.setAddress(entity.getAddress());
		model.setProfile(entity.getProfile());
		model.setCreated_at(entity.getCreated_at());
		model.setUpdated_at(entity.getUpdated_at());
		return model;
	}
	private  User toEntity(RegisterModel model) {
		User entity=new User();
		entity.setName(model.getName());
		entity.setEmail(model.getEmail());
		entity.setPhone(model.getPhone());	
		String encodePass=passwordService.encode(model.getPassword());
		entity.setPassword(encodePass);
		entity.setId(UUID.randomUUID().toString());
		entity.setAddress(model.getAddress());
		entity.setRole(Role.CUSTOMER.toString());
		return entity;
	}
}
