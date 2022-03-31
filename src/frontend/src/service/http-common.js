import axios from "axios";
import jwtDecode from "jwt-decode";
import store from "@/store";

/**
 * This class sets up an instance of axios with a certain set of configurations that can be used around the
 * application
 *
 * @returns instance of axios with wanted config
 */
const fetchClient = () => {
    const options = {
        baseURL: "http://localhost:3000",
        headers: {
            "Content-type": "application/json",
        },
    };

    // Base instance
    let instance = axios.create(options);

    instance.interceptors.request.use(async (config) => {
        let token = store.state.accessToken;

        // If the token is expired, a new one is fetched
        if (!isTokenValidOrUndefined(token)) {
            try {
                let instance2 = axios.create(options);
                const response = await instance2.post("/token", "", {
                    withCredentials: true,
                });
                token = response.data.accessToken;
                store.commit("setAccessToken", token);
            } catch (err) {
                console.log(err);
            }
        }

        config.headers.Authorization = token ? `Bearer ${token}` : "";
        return config;
    });

    return instance;
};

/**
 * Checks whether the current access is still valid
 *
 * @returns true if the token is valid or undefined, otherwise false
 */
export const isTokenValidOrUndefined = (token) => {
    if (!token) {
        return true;
    }
    try {
        const decoded = jwtDecode(token);
        return Date.now() < decoded.exp * 1000;
    } catch {
        return false;
    }
};

export default fetchClient();