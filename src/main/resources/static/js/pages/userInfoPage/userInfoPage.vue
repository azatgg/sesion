<template>
    <div>
        <navbar />
        <div class="container-fluid">
            <div class="row">
                <div class="col-3 mt-4">
                    <div class="col-12 h-40">
                        <div class="bg-green">{{ $t('categories') }}</div>
                        <div v-for="i in categories">
                            <p style="font-size: 0.9em; font-weight: bold;">{{i.name}}</p>
                        </div>
                    </div>
                    <div class="col-12 h-40 mt-4">
                        <div class="bg-green">{{ $t('brand') }}</div>
                        <div v-for="i in brands">
                            <p style="font-size: 0.9em; font-weight: bold;">{{i.name}}</p>
                        </div>
                    </div>
                </div>

                <div class="col-7 mt-4 mr-auto ml-auto">
                        <div class="form-group d-flex align-content-center justify-content-center">
                            <img :src="user.photo" alt="" class="w-50" align="center">
                        </div>
                        <div class="form-group">
                            <label>Photo Url</label>
                            <input type="text" class="form-control" v-model="user.photo" />
                        </div>
                        <div class="form-group">
                            <label>Full name</label>
                            <p class="form-control">{{user.lname}} {{user.fname}}</p>
                        </div>
                        <div class="form-group">
                            <label>Username</label>
                            <input class="form-control" type="text" v-model="user.username">
                        </div>
                        <div class="d-flex align-content-center justify-content-end">
                            <button @click="editUser" class="btn btn-primary">Update</button>
                        </div>
                </div>
            </div>
        </div>
        <footer />
    </div>
</template>

<script>
    import navbar from 'pages/defaultComponents/navbar.vue'
    import dropdown from 'pages/defaultComponents/dropdown.vue'
    import Footer from "pages/defaultComponents/footer.vue";
    export default {
        name: "userInfoPage",
        components: {
            Footer,
            navbar,
            dropdown
        },
        data() {
            return{
                token: null,
                user: {},
                categories: [],
                brands: [],
            }
        },
        async created() {
            let {data: token} = await this.$http.get("http://localhost:4000/token");
            this.token = token;
            let {data: username} = await this.$http.get("http://localhost:4000/sso/auth/user/me ", {headers: {'Authorization': 'bearer ' + this.token}});
            let {data: user} = await this.$http.get("http://localhost:4000/sso/auth/user/get?name=" + username.name, {headers: {'Authorization': 'bearer ' + this.token}});
            this.user = user;
            console.log(user);
            this.loadCategories();
            this.loadBrands();
        },
        methods: {
            async loadCategories() {
                let {data: data}= await this.$http.get("http://localhost:4000/item/api/category/all");
                data.forEach(i => {
                    this.categories.push(i);
                });
            },
            async loadBrands() {
                let {data: data}= await this.$http.get("http://localhost:4000/item/api/brand/all");
                data.forEach(i => {
                    this.brands.push(i);
                });
            },
            async editUser() {
                await this.$http.post("http://localhost:4000/sso/auth/save", this.user, {headers: {'Authorization': 'bearer ' + this.token}});
            }
        }
    }
</script>

<style scoped>
    .bg-green {
        background-color: #0f6b27 !important;
        padding-left: 5%;
        color: #FFFFFF;
        font-size: 1.2em;
    }
    .h-40 > div {
        text-align: center;
        height: 40px;
        border: 1px solid #636b6f;
        color: black;
    }
</style>