package com.ts.server.safe.security;

import com.ts.server.safe.BaseException;

import java.util.Optional;

public class CredentialContextUtils {

    private static final ThreadLocal<Credential> threadLocal = new ThreadLocal<>();

    public static void setCredential(Credential credential){
        threadLocal.set(credential);
    }

    public static Optional<Credential> getCredentialOpt(){
        return Optional.ofNullable(threadLocal.get());
    }

    public static Credential getCredential(){
        Credential t = threadLocal.get();
        if(t == null){
            throw new BaseException(143, "认证失败");
        }
        return t;
    }

    public static boolean hasCredential(){
        return getCredentialOpt().isPresent();
    }
}
