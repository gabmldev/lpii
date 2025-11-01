package com.github.gabmldev.app.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileData {
    private UserClaims user;
    private AuthStatus _authStatus;
}
