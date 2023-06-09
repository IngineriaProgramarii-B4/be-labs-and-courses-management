package com.example.controllers;

import com.example.models.User;
import com.example.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import static com.example.security.JWTGenerator.extractEmailFromTokenWithoutVerification;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Operation(summary = "Get a list of users based on 0 or more filters  passed as queries. The format is property_from_user_schema=value.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found users that match the requirements",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Haven't found users that match the requirements",
                    content = @Content
            )
    })
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsersByParams(@RequestParam Map<String, Object> params) {
        List<User> users = usersService.getUsersByParams(params);

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @Operation(summary = "Get from the frontend information about the logged user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Get information about logged user successfully.",
                    content = @Content)
    })
    @PostMapping(value = "/users/loggedUser")
    public ResponseEntity<User> getLoggedUser(@RequestBody String token) {
        String finalToken = token.substring(1, token.length() - 1);
        User user;
        try {
            String email = extractEmailFromTokenWithoutVerification(finalToken);
            user = usersService.getUsersByParams(Map.of("email", email)).get(0);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.out.println("An error occurred at object mapping");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}