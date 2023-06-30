package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.repository.RoleRepository;
import com.bestbuy.ecommerce.dto.request.RoleRequest;
import com.bestbuy.ecommerce.dto.responses.RoleResponse;
import com.bestbuy.ecommerce.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private  final RoleRepository roleRepository;
    @Override
    public RoleResponse addRoles(RoleRequest roleRequest) {
        return null;
    }
}
