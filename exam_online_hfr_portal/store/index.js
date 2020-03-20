export const state = () => ({
    isLogin: false,
    user: {
        userId: "",
        userRealname: ""
    }
})

export const mutations = {
    setUser(state, newUser) {
        state.user = newUser;
    },
    setIsLogin(state, newIsLogin){
        state.isLogin = newIsLogin;
    }
}