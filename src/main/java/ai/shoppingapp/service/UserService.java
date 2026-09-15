package ai.shoppingapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.shoppingapp.model.Role;
import ai.shoppingapp.model.UserModel;
import ai.shoppingapp.repository.UserRepository;
import ai.shoppingapp.repository.entity.User;

@Service
public class UserService {
	private final UserRepository userRepo;

	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
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

	public int changeRole(String id, Role role) {
		return this.userRepo.updateRole(id, role.toString());
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
}
