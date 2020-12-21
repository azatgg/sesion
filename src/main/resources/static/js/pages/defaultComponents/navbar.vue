<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-green">
        <a class="navbar-brand" href="/">{{ $t('nav') }}</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto mr-4">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        {{ $t('Lang') }}
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="#" @click="setLocale(lang)" v-for="(lang, i) in $i18n.availableLocales" :key="`Lang${i}`" :value="lang">{{ lang }}</a>
                    </div>
                </li>
                <li class="nav-item">
                <li class="nav-item">
                    <router-link class="nav-link" :to="{ name: 'userInfoPage' }">Profile</router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" :to="{ name: 'cartPage' }">Cart</router-link>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/login">{{ $t('login') }}</a>
                </li>
            </ul>
        </div>
    </nav>
</template>

<script>
    export default {
        name: "navbar",
        data() {
            return {
                langs: ["en", "ru", "kz"]
            }
        },
        async created() {
        },
        methods: {
            setLocale(locale) {
                this.$i18n.locale = locale;
            },
        },
    }
</script>

<style scoped>
    .bg-green {
        background-color: #0f6b27 !important;
    }
</style>