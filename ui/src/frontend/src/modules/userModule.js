const userModule = {
    state: () => ({
        isUserAuth: false,
        user: '',
        credits: 0,
        ownedOffers: [],
        subscriptions: [],
    }),

    mutations: {
        setUser (state, user) {
            console.log("Setting user: " + user);
            state.user = user;
            state.credits = user.credits;
            state.isUserAuth = true;
        },

        setCatchedCredits(state, value){
            state.credits = value;
        },

        setOwned(state, owned){
            console.log("Setting owned: " + owned);
            state.ownedOffers = owned;
        },

        addOwned(state, owned){
            state.ownedOffers.push(owned);
        },

        setSubscribed(state, sub){
            console.log("Setting subscribed: " + sub);
            state.subscriptions = sub;
        },

        addSubscribed(state, sub){
            state.subscriptions.push(sub);
            console.log("Subs: " + state.subscriptions);
        },

        unsetUser(state) {
            state.user = '';
            state.isUserAuth = false;
            state.credits = 0;
            state.ownedOffers = [];
            state.subscriptions = [];
        }
    },

    getters: {
        getSubscribed(state) {
            return state.subscriptions;
        },

        getCatchedCredits(state) {
                console.log("Catched credits: " + state.user.credits);
                return state.credits;
        },
    }
}


export default userModule;