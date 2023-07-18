package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.AppUser;
import com.bestbuy.ecommerce.domain.entity.Role;
import com.bestbuy.ecommerce.domain.repository.AppUserRepository;
import com.bestbuy.ecommerce.domain.repository.RoleRepository;
import com.bestbuy.ecommerce.dto.request.RoleRequest;
import com.bestbuy.ecommerce.dto.responses.AddPermissionRequest;
import com.bestbuy.ecommerce.dto.responses.RoleResponse;
import com.bestbuy.ecommerce.exceptions.AppUserNotFountException;
import com.bestbuy.ecommerce.exceptions.RolesNotFoundException;
import com.bestbuy.ecommerce.service.RoleService;
import com.bestbuy.ecommerce.utils.Permission;
import com.bestbuy.ecommerce.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private  final AppUserRepository appUserRepository;

    private  final   RoleRepository roleRepository;
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
}
