package com.jambit.iam.controller.oauth;

import com.jambit.iam.controller.AbstractResponseController;
import com.jambit.iam.controller.mapper.CreateUserTokenMapper;
import com.jambit.iam.controller.mapper.OauthTokenMapper;
import com.jambit.iam.controller.rest.model.request.OauthTokenRequest;
import com.jambit.iam.controller.rest.model.response.oauth.OauthTokenResponse;
import com.jambit.iam.service.jwt.JwtMediator;
import com.jambit.iam.service.validator.ModelValidator;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class OauthTokenController extends AbstractResponseController {

    private final JwtMediator jwtMediator;
    private final ModelValidator modelValidator;
    private final OauthTokenMapper oauthTokenMapper;
    private final CreateUserTokenMapper createUserTokenMapper;


    @PostMapping
    @ApiOperation(value = "Grant Oauth Token", response = OauthTokenResponse.class)
    public ResponseEntity<OauthTokenResponse> grantToken(@RequestBody OauthTokenRequest request) {
        modelValidator.validate(request);
        String token = jwtMediator.grantToken(createUserTokenMapper.toDto(request));
        return respondOK(oauthTokenMapper.toResponse(token));
    }

}
