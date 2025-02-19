package com.jambit.iam.controller.oauth;

import com.jambit.iam.controller.AbstractController;
import com.jambit.iam.controller.rest.model.request.OauthTokenRequest;
import com.jambit.iam.controller.rest.model.request.UserInfoDetails;
import com.jambit.iam.controller.rest.model.response.oauth.OauthTokenResponse;
import com.jambit.iam.domain.entity.user.User;
import com.jambit.iam.domain.model.common.exception.ErrorCode;
import com.jambit.iam.domain.model.common.exception.RecordConflictException;
import com.jambit.iam.controller.rest.model.request.CreateUserTokenRequest;
import com.jambit.iam.service.jwt.JwtService;
import com.jambit.iam.service.user.UserService;
import com.jambit.iam.service.validator.ModelValidator;
import com.jambit.iam.util.PasswordUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tigran Melkonyan
 * Date: 2/19/25
 * Time: 12:41 PM
 */
@RestController
@RequestMapping("iam/oauth/token")
public class OauthTokenController extends AbstractController {

    private final JwtService jwtService;
    private final UserService userService;
    private final ModelValidator modelValidator;


    public OauthTokenController(
            final JwtService jwtService,
            final UserService userService,
            final ModelValidator modelValidator) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.modelValidator = modelValidator;
    }

    @PostMapping
    @ApiOperation(value = "Grant Oauth Token", response = OauthTokenResponse.class)
    public ResponseEntity<OauthTokenResponse> grantToken(@RequestBody OauthTokenRequest request) {
        modelValidator.validate(request);
        User user = userService.getByUserName(request.getUserName());
        if (!PasswordUtils.isPasswordMatch(request.getPassword(), user.getPassword())) {
            throw new RecordConflictException("Invalid credentials", ErrorCode.INVALID_CREDENTIALS);
        }
        String token = jwtService.createJwt(new CreateUserTokenRequest(request.getUserName(), request.getPassword(),
                new UserInfoDetails(user.getId().toString(), user.getEmail(), user.getRole())));
        return respondOK(OauthTokenResponse.from(token));
    }

}
