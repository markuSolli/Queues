import http from "@/service/http-common";

/**
 * Refreshes access token for further requests
 *
 * @returns access token
 */
export const refreshToken = () => {
    return http.post("/token", "", { withCredentials: true });
}