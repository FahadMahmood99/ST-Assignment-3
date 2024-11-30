import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginAppTest {

    @Test
    public void testValidLogin() {
        LoginApp loginApp = new LoginApp();

        // Check if the valid login returns the correct username
        String result = loginApp.authenticateUser("validEmail@example.com", "correctPassword");
        assertEquals("UserName", result, "Expected valid login to return 'UserName'.");

        // Additional check for case sensitivity in username
        assertNotEquals("username", result, "Login should be case-sensitive.");
    }

    @Test
    public void testInvalidPassword() {
        LoginApp loginApp = new LoginApp();

        // Check if an invalid password returns null
        String result = loginApp.authenticateUser("validEmail@example.com", "wrongPassword");
        assertNull(result, "Expected login to fail with an incorrect password.");

        // Additional check: Verify that an incorrect password does not allow access
        assertNotEquals("UserName", result, "Invalid password should not return a valid username.");
    }

    @Test
    public void testInvalidEmail() {
        LoginApp loginApp = new LoginApp();

        // Check if an invalid email returns null
        String result = loginApp.authenticateUser("invalidEmail@example.com", "somePassword");
        assertNull(result, "Expected login to fail with an invalid email.");

        // Additional check for null or empty username handling
        assertNotEquals("UserName", result, "Invalid email should not return a valid username.");
    }

    @Test
    public void testEmptyEmailAndPassword() {
        LoginApp loginApp = new LoginApp();

        // Check if empty email and password return null
        String result = loginApp.authenticateUser("", "");
        assertNull(result, "Expected login to fail with empty email and password.");

        // Additional check for null or empty values in general
        result = loginApp.authenticateUser(null, null);
        assertNull(result, "Expected login to fail with null values for email and password.");
    }

    @Test
    public void testSqlInjectionAttempt() {
        LoginApp loginApp = new LoginApp();

        // Check if SQL injection attempt returns null
        String result = loginApp.authenticateUser("email@example.com' OR '1'='1", "anyPassword");
        assertNull(result, "Expected login to fail with SQL injection attempt in email.");

        // Additional SQL injection scenarios
        result = loginApp.authenticateUser("validEmail@example.com", "' OR '1'='1");
        assertNull(result, "Expected login to fail with SQL injection attempt in password.");
    }

    @Test
    public void testUsernameValidation() {
        LoginApp loginApp = new LoginApp();

        // Test case with valid username format
        String result = loginApp.authenticateUser("user@domain.com", "validPassword");
        assertTrue(result != null && result.matches("^[a-zA-Z0-9_]+$"), "Username should only contain alphanumeric characters and underscores.");

        // Test case with special characters in username
        result = loginApp.authenticateUser("special@domain.com", "validPassword");
        assertNull(result, "Login should fail with special characters in the username.");
    }
    

}
