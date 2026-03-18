package org.example.spring26.filters;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private static final String VALID_API_KEY = "secret_key";

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String presentedKey = (String) authentication.getPrincipal();
        if (VALID_API_KEY.equals(presentedKey)) {
            return new ApiKeyAuthenticationToken(
                    presentedKey,
                    AuthorityUtils.createAuthorityList("ROLE_API")
            );
        }
        throw new BadCredentialsException("Invalid api key");
    }

    @Override
    public boolean supports(@NonNull Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
