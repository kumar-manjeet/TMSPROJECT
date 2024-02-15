package com.tms.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tms.models.Permission;
import com.tms.models.Role;
import com.tms.models.reponse.PermissionResponseDto;
import com.tms.models.reponse.RoleInheritResponseDto;
import com.tms.models.request.SaveRolePermissonRequestDto;
import com.tms.services.PermissionService;
import com.tms.vo.PermissionVo;
import com.tms.vo.RoleVo;

@RestController
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@GetMapping("/assignPermission")
	public String assignPermission(Model model) {
		List<RoleVo> roles = new ArrayList<>();
		RoleVo roleVo = new RoleVo();
		try {
			roles = permissionService.getAllRoles();
			model.addAttribute("roles", roles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("roleVo", roleVo);
		model.addAttribute("content", "assignPermission");
		return "home";
	}
	
	
	@PostMapping("/uriPermissionList")
	public String getPermissionList(@RequestParam Long roleId, Model model) {
		try {
			Map<String, ArrayList<PermissionResponseDto>> permissions = permissionService.getAllPermissionOnRole(roleId);
			List<RoleVo> roles = permissionService.getAllRoles();
			RoleVo roleVo = permissionService.getRoleById(roleId);
			model.addAttribute("roles", roles);
			model.addAttribute("roleVo", roleVo);
			model.addAttribute("permissions", permissions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("content", "assignPermission");
		return "home";
	}
	
	@PostMapping("/saveRolePermisson")
	public String saveRolePermisson(@ModelAttribute SaveRolePermissonRequestDto request, Model model) {
		try {
			Boolean saveRolePermisson = permissionService.saveRolePermisson(request);
			if (saveRolePermisson != null && saveRolePermisson == true) {
				model.addAttribute("msg", "Permission Assigned");
			} else {
				model.addAttribute("msg", "Permission Not Assigned, Something went wrong !!");
			}
			Map<String, ArrayList<PermissionResponseDto>> permissions = permissionService.getAllPermissionOnRole(request.getRoleId());
			List<RoleVo> roles = permissionService.getAllRoles();
			RoleVo roleVo = permissionService.getRoleById(request.getRoleId());
			model.addAttribute("roles", roles);
			model.addAttribute("roleVo", roleVo);
			model.addAttribute("permissions", permissions);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "Permission Not Assigned, Something went wrong !!");
		}
		model.addAttribute("content", "assignPermission");
		return "home";
	}

//	@PostMapping("/uriPermissionList")
//	public String getAllUris(@RequestParam Long roleId, Model model) {
//		try {
//			List<UriResponseDto> rolePermssionOnUriByRoleId = permissionService.getRolePermssionOnUriByRoleId(roleId);
//			RoleVo roleVo = permissionService.getRoleById(roleId);
//			List<RoleVo> roles = permissionService.getAllRoles();
//			model.addAttribute("roleVo", roleVo);
//			model.addAttribute("roles", roles);
//			model.addAttribute("rolePermssionOnUriByRoleId", rolePermssionOnUriByRoleId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		model.addAttribute("content", "assignPermission");
//		return "home";
//	}

//	@PostMapping("/saveRolePermisson")
//	public String saveRolePermisson(@ModelAttribute SaveRolePermissonRequestDto request, Model model) {
//		try {
//			Boolean saveRolePermisson = permissionService.saveRolePermisson(request);
//			if (saveRolePermisson != null && saveRolePermisson == true) {
//				model.addAttribute("msg", "Permission Assigned");
//			} else {
//				model.addAttribute("msg", "Permission Not Assigned, Something went wrong !!");
//			}
//			List<UriResponseDto> rolePermssionOnUriByRoleId = permissionService
//					.getRolePermssionOnUriByRoleId(request.getRoleId());
//			RoleVo roleVo = permissionService.getRoleById(request.getRoleId());
//			List<RoleVo> roles = permissionService.getAllRoles();
//			model.addAttribute("roleVo", roleVo);
//			model.addAttribute("roles", roles);
//			model.addAttribute("rolePermssionOnUriByRoleId", rolePermssionOnUriByRoleId);
//		} catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("msg", "Permission Not Assigned, Something went wrong !!");
//		}
//		model.addAttribute("content", "assignPermission");
//		return "home";
//	}
//
	@GetMapping("/getPermission")
	public String getPermission(Model model) {
		try {
			List<PermissionVo> allPermission = permissionService.getAllPermission();
			model.addAttribute("allPermission", allPermission);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("content", "uriDetails");
		return "home";
	}

	@PostMapping("/savePermission")
	public String saveUri(@RequestParam String module, @RequestParam String permission, Model model) {
		try {
			PermissionVo permissionVo = permissionService.getPermission(module, permission);
			if (permissionVo == null) {
				Permission saveUri = permissionService.savePermission(module, permission);
				if (saveUri.getId() != null) {
					model.addAttribute("msg", "Uri saved Successfully.");
				} else {
					model.addAttribute("msg", "Uri not saved, Something went wrong!!");
				}
			} else {
				model.addAttribute("msg", "Uri already present !!");
			}
			List<PermissionVo> allPermission = permissionService.getAllPermission();
			model.addAttribute("allPermission", allPermission);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "Something went wrong !!");
		}
		model.addAttribute("content", "uriDetails");
		return "home";
	}

	@GetMapping("/getRole")
	public String getRole(Model model) {
		try {
			List<RoleVo> allRoles = permissionService.getAllRoles();
			model.addAttribute("roles", allRoles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("content", "roleDetails");
		return "home";
	}

	@PostMapping("/saveRole")
	public String saveRole(@RequestParam String role, Model model) {
		try {
			RoleVo roleVo = permissionService.getRole(role);
			if (roleVo == null) {
				Role saveRole = permissionService.saveRole(role);
				if (saveRole.getId() != null) {
					model.addAttribute("msg", "Role saved Successfully.");
				} else {
					model.addAttribute("msg", "Role not saved, Something went wrong!!");
				}
			} else {
				model.addAttribute("msg", "Role already present !!");
			}
			List<RoleVo> allRoles = permissionService.getAllRoles();
			model.addAttribute("roles", allRoles);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "Something went wrong !!");
		}
		model.addAttribute("content", "roleDetails");
		return "home";
	}
	
	@GetMapping("/inheritRole")
	public String inheritRole(Model model) {
		RoleVo roleVo = new RoleVo();
		try {
			List<RoleVo> allRoles = permissionService.getAllRoles();
			model.addAttribute("roles", allRoles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("roleVo", roleVo);
		model.addAttribute("content", "inheritRole");
		return "home";
	}
	
//	@PostMapping("/assingRoleInherit")
//	public String assingRoleInherit(@RequestParam Long roleId, Model model) {
//		RoleVo roleVo = new RoleVo();
//		try {
//			roleVo = permissionService.getRoleById(roleId);
//			List<RoleInheritResponseDto> roleInherited = permissionService.getRoleInherited(roleId);
//			List<RoleVo> allRoles = permissionService.getAllRoles();
//			model.addAttribute("roles", allRoles);
//			model.addAttribute("roleInherited", roleInherited);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		model.addAttribute("roleVo", roleVo);
//		model.addAttribute("content", "inheritRole");
//		return "home";
//	}
	
	
	@GetMapping("/getResult")
    public ResponseEntity <List<Map<String, Object>>> getResult(@RequestParam Long userId){
		List<Map<String, Object>> permissions = permissionService.getPermissionsForUser(userId);
        if (permissions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(permissions);
	}

	

	@GetMapping("/getModules")
    public ResponseEntity <Map<String, ArrayList<PermissionResponseDto>>> getAllPermissionOnRole(@RequestParam Long roleId){
		Map<String, ArrayList<PermissionResponseDto>> permissions = permissionService.getAllPermissionOnRole(roleId);
        if (permissions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(permissions);
	}

}
