import Vue from 'vue'
import VueRouter from 'vue-router'
import mainPage from 'pages/mainPage/mainPage.vue'
import adminPage from 'pages/adminPage/adminPage.vue'
import userInfoPage from 'pages/userInfoPage/userInfoPage.vue'
import cartPage from 'pages/cartPage/cartPage.vue'
import infoPage from 'pages/infoPage/infoPage.vue'
Vue.use(VueRouter);
const routes = [
    { path: '/', name: "mainPage", component: mainPage },
    { path: '/admin', name: "auth", component: adminPage},
    { path: '/profile', name: "userInfoPage", component: userInfoPage, props: true},
    { path: '/cart', name: "cartPage", component: cartPage, props: true},
    { path: '/info', name: "infoPage", component: infoPage, props: true},
];


export default new VueRouter({
    mode: 'history',
    routes
})