import Cookies from "js-cookie";

const apiModule = {
    state: () => ({
        tokenName: "token",
    }),

    getters: {

        async getAuthUser(state, getters) {
            let token = getters.getAuthToken;

            if (getters.stringEmpty(token)) {
                return null;
            }

            return await getters.__fetchApiUser;
        },

        addSubscriptions: (state, getters) => async (userId, offerId) => {
            await fetch("api/offers/subscribe?userId=" + userId + "&offerId=" + offerId, {
                method: 'POST',
                headers: {
                    "token": getters.getAuthToken
                }
            });
        },

         getSubscriptions: (state, getters) => async (userId) => {
            return await getters.__apiGet("api/offers/subscribedBy/" + userId);
        },

        async getAllCategories(state, getters) {
            return await getters.__apiGet("api/categories/all");
        },

        getOwnedOffers: (state, getters) => async (userId) => {
            return await getters.__apiGet("api/offers/ofUser/" + userId);
        },

        getSubscribedOffers: (state, getters) => async (userId) => {
            return await getters.__apiGet("api/offers/subscribedBy/" + userId);
        },

        getSubscribersOfOffer: (state, getters) => async (offerId) => {
            return await getters.__apiGet("api/offers/subscribersOf/" + offerId);
        },

        getCurrentTimetable: (state, getters) => async () => {
            let requestOptions = {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    "token": getters.getAuthToken
                }
            };

            return await getters.__apiPost("api/timetables/forWeek/current", requestOptions);
        },

        getCredits: (state, getters) => async (userId) => {
            let requestForCredits = {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    "token": getters.getAuthToken
                }
            };

            let current = await getters.__apiPost("api/users/credits/" + userId, requestForCredits);

            if (current == null)
                return 0

            return current.credits;
        },

        addCredits: (state, getters) => async (userId, credits) => {
            let requestSetCredits = {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "token": getters.getAuthToken
                }
            };

            let current = await getters.getCredits(userId);
            let newCredits = current + credits;
            let setResponse = await getters.__apiPost("api/users/setCredits/" + userId + "/" + newCredits, requestSetCredits);

            return setResponse != null;
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

            if (result == null || !result.successful)
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

        isAuthenticated(state, getters) {
            return !getters.stringEmpty(getters.getAuthToken);
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
            console.log("DEBUG: Request [Get] to " + url);
            try {
                const response = await fetch(url);
                return await response.json();
            } catch (e) {
                console.log("Error: " + e);
                return null;
            }
        },

        __apiPost: () => async (url, options) => {
            try {
                console.log("DEBUG: Request [Post] to " + url);
                let response = await fetch(url, options);

                if (!response.ok) {
                    console.log("DEBUG: Result [Post] [" + url + "] not ok");
                    return null;
                }

                return await response.json();
            } catch (e) {
                console.log("ERROR: " + e);
                return null;
            }
        }
    },
}

export default apiModule;