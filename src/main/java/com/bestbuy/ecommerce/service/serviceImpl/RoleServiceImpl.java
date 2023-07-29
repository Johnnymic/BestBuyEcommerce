package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.Admin;
import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.Role;
import com.bestbuy.ecommerce.domain.repository.AdminRepository;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.RoleRepository;
import com.bestbuy.ecommerce.dto.request.AdminRequest;
import com.bestbuy.ecommerce.dto.request.RoleRequest;
import com.bestbuy.ecommerce.dto.responses.AddPermissionRequest;
import com.bestbuy.ecommerce.dto.responses.AdminResponse;
import com.bestbuy.ecommerce.dto.responses.RoleResponse;
import com.bestbuy.ecommerce.enums.Gender;
import com.bestbuy.ecommerce.enums.Roles;
import com.bestbuy.ecommerce.exceptions.AlreadyExistException;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.RolesNotFoundException;
import com.bestbuy.ecommerce.service.RoleService;
import com.bestbuy.ecommerce.utils.Permission;
import com.bestbuy.ecommerce.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private  final AppUserRepository appUserRepository;

    private  final   RoleRepository roleRepository;

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public RoleResponse addRoles(RoleRequest roleRequest)  {
        if(getRoleForUser()){
            Role roles = Role.builder()
                    .roleId(roleRequest.getId())
                    .roleName(roleRequest.getFirstName().toUpperCase())
                    .permissions(new ArrayList<>())
                    .build();
            var saveRole = roleRepository.save(roles);
            return RoleResponse.builder()
                    .role(saveRole)
                    .build();
        }
         throw  new RolesNotFoundException("Authority not granted");
    }



    private boolean getRoleForUser() {
        AppUser loginUser = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFountException("user has not login"));
        Role roles = loginUser.getRole();
        return roles.getRoleName().equalsIgnoreCase("ADMIN");

    }





  @Override
    public RoleResponse addRolesPermission(AddPermissionRequest roleRequest, Long roleId) {
        if(getRoleForUser()){
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(()-> new RolesNotFoundException("role not found exception"));
             Permission permission = new Permission();
             permission.setPermission(roleRequest.getPermission().toUpperCase());
             role.getPermissions().add(String.valueOf(permission));
             Role newRole = roleRepository.save(role);
             return RoleResponse.builder()
                     .role(newRole)
                     .build();

        }
        throw new RolesNotFoundException("Authority not granted ");
    }

    @Override
    public AdminResponse registerAdmin(AdminRequest adminRequest) {
        Role role = roleRepository.findByRoleName("ADMIN");
        boolean emailAlreadyExist  = appUserRepository.existsByEmail(adminRequest.getEmail());
         if(emailAlreadyExist){
             throw new AlreadyExistException("email already exist");
         }
        Admin admin = new Admin();
        String password = UUID.randomUUID().toString();
        AppUser appUser = AppUser.builder()
                .firstName(adminRequest.getFirstName())
                .lastName(adminRequest.getLastName())
                .date_of_birth(adminRequest.getDateOfBirth())
                .phone(adminRequest.getPhoneNumber())
                .password(passwordEncoder.encode(password))
                .role(role)
                .email(adminRequest.getEmail())
                .build();
        admin.setAppUser(appUser);
        adminRepository.save(admin);
        roleRepository.save(role);
        appUserRepository.save(appUser);

        AdminResponse adminResponse = new AdminResponse();
        BeanUtils.copyProperties(appUser,adminResponse);
        return adminResponse;
    }

    @Override
    public String deActiveUser(Long userId) {
        if(getRoleForUser()){
            AppUser appUser = appUserRepository.findById(userId)
                    .orElseThrow(()-> new AppUserNotFountException("user not found "));

            boolean isActive = appUser.getIsEnabled();
            appUser.setIsEnabled(!isActive);
            appUserRepository.save(appUser);
        }
        throw new RolesNotFoundException("Authority not granted ");
    }
}
