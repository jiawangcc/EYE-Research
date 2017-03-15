package edu.uw.jiawang.util;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Test;

public class PasswordUtilTest {

	@Test
	public void test() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String originalPassword = "password";
        String generatedSecuredPasswordHash = PasswordUtil.generateStrongPasswordHash(originalPassword);
        System.out.println(generatedSecuredPasswordHash);
         
        boolean matched = PasswordUtil.validatePassword("password", generatedSecuredPasswordHash);
        assertTrue(matched);
         
        matched = PasswordUtil.validatePassword("password1", generatedSecuredPasswordHash);
        assertFalse(matched);
	}

}
