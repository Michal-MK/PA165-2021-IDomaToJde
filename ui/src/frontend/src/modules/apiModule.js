import Cookies from "js-cookie";

const apiModule = {
    state: () => ({
        tokenName: "token"
    }),

    mutations: {},

    getters: {

        async getAuthUser(state, getters) {
            let token = getters.getAuthToken;

            if (getters.stringEmpty(token)) {
                return null;
            }

            return await getters.__fetchApiUser;
        },

        async getAllCategories(state, getters) {
            return await getters.__apiGet("api/categories/all");
        },

        async registerUser() {

        },


        logIn: (state, getters) => async (username, pass) => {
            let requestOptions = {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "username": username,
                    "pass": pass
                }
            };

            let result = await getters.__apiPost("api/auth/login", requestOptions);

            if(result == null || !result.successful)
                return false;

            Cookies.set(state.tokenName, result.token);
            return true;
        },


        stringEmpty: () => (string) => {
            return string == null || string.length === 0;
        },

        getAuthToken(state) {
            return Cookies.get(state.tokenName);
        },

        // private methods
        async __fetchApiUser(state, getters) {
            try {
                const response = await fetch("api/auth/authenticate", {
                    method: 'POST',
                    headers: {
                        "token": getters.getAuthToken
                    }
                });

                if (!response.ok) {
                    return null;
                } else {

                    return await response.json();
                }
            } catch (e) {
                return null;
            }
        },

        __apiGet: () => async (url) => {
            try {
                const response = await fetch(url);
                return await response.json();
            } catch (e) {
                console.log("Error: " + e);
                return null;
            }
        },

        __apiPost: () => async (url, options) => {
            // try {
                let response = await fetch(url, options);

                if(!response.ok)
                    return null;

                return await response.json();
            // } catch (e) {
            //     console.log("Error: " + e);
            //     return null;
            // }
        }
    },
}

export default apiModule;