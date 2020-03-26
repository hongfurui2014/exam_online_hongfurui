export default ({ app }) => {
    app.router.beforeEach((to, from, next) => {

        // const c = getCookie("HFR_Q_TOKEN");
        // console.log(c);

        // console.log(to)
        // console.log(from)
        next()
    })
}