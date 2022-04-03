import http from "@/service/http-common";

/**
 * Refreshes access token for further requests
 *
 * @returns access token
 */
export const refreshToken = () => {
    return http.post("/token", "", { withCredentials: true });
}

/**
 * Tries to login the given user
 *
 * @param user to login
 * @returns response from login
 */
export const login = (user) => {
    return http.post("/login", user, { withCredentials: true });
}

/**
 * Logs out the user
 */
export const logout = () => {
    return http.post("/logout", "", { withCredentials: true })
}

/**
 * Returns the currently loggen in user
 */
export const whoAmI = () => {
    return http.get("/me");
}