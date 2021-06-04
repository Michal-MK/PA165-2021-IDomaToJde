import Cookies from "js-cookie";

const apiModule = {
    state: () => ({
        tokenName: "token"
    }),

    mutations: {
    },

    getters: {

        async isAuth(state, getters){
            // let token = getters.getAuthToken;
            //
            // if(getters.stringEmpty(token)) {
            //     return null;
            // }

            return await getters.fetchApiUser;
        },


        async fetchApiUser(state, getters){
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

        stringEmpty: () => (string) => {
            return string == null || string.length === 0;
        },

        getAuthToken(state){
            return Cookies.get(state.tokenName);
        },

        // private methods
    },
}

export default apiModule;