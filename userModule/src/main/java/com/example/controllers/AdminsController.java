package com.example.controllers;

import com.example.models.Admin;
import com.example.models.Student;
import com.example.services.AdminsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class AdminsController {
    private final AdminsService adminsService;

    @Autowired
    public AdminsController(AdminsService adminsService) {
        this.adminsService = adminsService;
    }

    @Operation(summary = "Get a list of admins based on 0 or more filters passed as queries. The format is property_from_admin_schema=value.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found admins that match the requirements",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Admin.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Haven't found admins that match the requirements",
                    content = @Content
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping(value = "/admins")
    public List<Admin> getAdminsByParams(@RequestParam Map<String, Object> params) {
        List<Admin> admins = adminsService.getAdminsByParams(params);

        if (admins.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return admins;
    }

    @Operation(summary = "Receive necessary data in order to update information about an admin in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource updated successfully",
                    content = @Content)
    })
    @PutMapping(value = "/admin")
    public void updateAdmin(@RequestBody Admin admin) {
        adminsService.saveAdmin(admin);
    }
}
