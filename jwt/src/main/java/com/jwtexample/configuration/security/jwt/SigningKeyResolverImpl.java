package com.jwtexample.configuration.security.jwt;

import com.jwtexample.util.FileUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolver;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class SigningKeyResolverImpl implements SigningKeyResolver  {

    // openssl genrsa -out private.key 2048

    private final String keyName;

    public SigningKeyResolverImpl(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public Key resolveSigningKey(JwsHeader header, Claims claims) {

        try {
            return new SecretKeySpec(FileUtils.getFileContent(keyName), SignatureAlgorithm.HS512.getJcaName());
        } catch (Exception exception) {
            throw new RuntimeException("Cannot extract public key", exception);
        }
    }

    @Override
    public Key resolveSigningKey(JwsHeader header, String plaintext) {
        try {
            return new SecretKeySpec(FileUtils.getFileContent(keyName), SignatureAlgorithm.HS512.getJcaName());
        } catch (Exception exception) {
            throw new RuntimeException("Cannot extract public key", exception);
        }
    }
}
