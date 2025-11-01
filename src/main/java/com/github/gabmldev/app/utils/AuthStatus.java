package com.github.gabmldev.app.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthStatus {
    private boolean isAdmin;
    private boolean isProfileOwner;
    private boolean isAuthenticate;
    private boolean isAnonymous;
}
