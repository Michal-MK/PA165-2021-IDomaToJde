import Cookies from "js-cookie";

const apiModule = {
    state: () => ({
        tokenName: "token"
    }),

    mutations: {
    },

    getters: {

        async getAuthUser(state, getters){
            let token = getters.getAuthToken;

            if(getters.stringEmpty(token)) {
                return null;
            }

            return await getters.__fetchApiUser;
        },

        async getAllCategories(state, getters){
            return await getters.__apiGet("api/categories/all");
        },


        stringEmpty: () => (string) => {
            return string == null || string.length === 0;
        },

        getAuthToken(state){
            return Cookies.get(state.tokenName);
        },

        // private methods
        async __fetchApiUser(state, getters){
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
            } catch (e){
                return null;
            }
        },

        __apiGet: () => async (url) => {
            const response = await fetch(url);
            return await response.json();
        }
    },
}

export default apiModule;