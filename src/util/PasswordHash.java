package util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHash {
	private static final String ALGORITHM = "SHA-256";
	private static final int SALT_LENGTH = 16;

	/**
	 * Hash a password with a random salt
	 * 
	 * @param password the plain text password
	 * @return salt + hash combined and encoded in Base64
	 */
	public static String hashPassword(String password) {
		try {
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[SALT_LENGTH];
			random.nextBytes(salt);

			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			md.update(salt);
			byte[] hashedPassword = md.digest(password.getBytes());

			// Combine salt and hash
			byte[] saltAndHash = new byte[salt.length + hashedPassword.length];
			System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
			System.arraycopy(hashedPassword, 0, saltAndHash, salt.length, hashedPassword.length);

			// Encode to Base64 for storage
			return Base64.getEncoder().encodeToString(saltAndHash);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Verify a password against a stored hash
	 * 
	 * @param password   the plain text password to verify
	 * @param storedHash the hash stored in the database
	 * @return true if password matches, false otherwise
	 */
	public static boolean verifyPassword(String password, String storedHash) {
		try {
			byte[] saltAndHash = Base64.getDecoder().decode(storedHash);

			// Extract salt
			byte[] salt = new byte[SALT_LENGTH];
			System.arraycopy(saltAndHash, 0, salt, 0, SALT_LENGTH);

			// Hash the provided password with the extracted salt
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			md.update(salt);
			byte[] hashedPassword = md.digest(password.getBytes());

			// Compare the hashes
			for (int i = 0; i < hashedPassword.length; i++) {
				if (hashedPassword[i] != saltAndHash[SALT_LENGTH + i]) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
