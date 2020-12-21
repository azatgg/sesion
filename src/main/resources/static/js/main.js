import Vue from 'vue'
import main from 'main.vue'
import router from "router/router"
import axios from 'axios'
import VueCookie from 'vue-cookies'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VueI18n from 'vue-i18n'

Vue.use(BootstrapVue);
Vue.use(VueCookie);
Vue.use(VueI18n);
Vue.prototype.$http = axios;
// Vue.config.devtools = false;
// Vue.config.debug = false;
// Vue.config.silent = true;

function loadMessages() {
    const context = require.context("./locales", true, /[a-z0-9-_]+\.json$/i);

    const messages = context
        .keys()
        .map((key) => ({ key, locale: key.match(/[a-z0-9-_]+/i)[0] }))
        .reduce(
            (messages, { key, locale }) => ({
                ...messages,
                [locale]: context(key),
            }),
            {}
        );

    return { context, messages };
}

const { context, messages } = loadMessages();

const i18n = new VueI18n({
    locale: 'en',
    messages
});


new Vue({
    el: '#app',
    i18n,
    router,
    render: a => a(main)
});