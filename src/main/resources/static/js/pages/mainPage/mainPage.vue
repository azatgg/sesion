<template>
    <div>
        <navbar :categories="categories" :brands="brands"/>
        <div class="container-fluid">
            <div class="row">
                <div class="col-3 mt-4">
                    <div class="col-12 h-40">
                        <div class="bg-green">{{ $t('categories') }}</div>
                        <div v-for="i in categories">
                            <p @click="filter(i, null, null)" style="font-size: 0.9em; font-weight: bold;">{{i.name}}</p>
                        </div>
                    </div>
                    <div class="col-12 h-40 mt-4">
                        <div class="bg-green">{{ $t('brand') }}</div>
                        <div v-for="i in brands">
                            <p @click="filter(null, i, null)" style="font-size: 0.9em; font-weight: bold;">{{i.name}}</p>
                        </div>
                    </div>
                </div>
                <div class="col-9 mt-4">
                    <div class="form-group col-12 d-flex justify-content-between">
                        <input v-model="name" type="text" class="form-control col-2 mr-1" placeholder="search">
                        <dropdown :options="brands" :selected="brand" @updateOption="update"/>
                        <input v-model="priceFrom" type="number" class="form-control col-2 mr-1" placeholder="search">
                        <input v-model="priceTo" type="number" class="form-control col-2 mr-1" placeholder="priceTo">
                        <button type="submit" @click="filter(null, null, null)" class="col-2 ml-3 btn btn-primary mb-2">search</button>
                    </div>
                    <div v-if="!isFiltered" class="col-12">
                        <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img src="https://techno.24tv.ua/resources/photos/news/1200x675_DIR/202010/1444047.jpg?202010170319" class="d-block w-100 img-400" alt="...">
                                </div>
                                <div class="carousel-item">
                                    <img src="https://techno.24tv.ua/resources/photos/news/1200x675_DIR/202010/1444047.jpg?202010170319" class="d-block w-100 img-400" alt="...">
                                </div>
                                <div class="carousel-item">
                                    <img src="https://techno.24tv.ua/resources/photos/news/1200x675_DIR/202010/1444047.jpg?202010170319" class="d-block w-100 img-400" alt="...">
                                </div>
                            </div>
                            <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>
                    </div>
                    <div v-if="isFiltered" class="form-group col-12 d-flex justify-content-between mt-4">
                        <h3>Search Result: </h3>
                        <div class="d-flex justify-content-between align-content-center">
                            <p style="margin-bottom: auto; margin-top: auto;">Order Pricing:</p>
                            <div class="btn-group ml-4" role="group" aria-label="Basic example">
                                <button type="button" @click="filter(null, null, true)" class="btn btn-secondary">Left</button>
                                <button type="button" @click="filter(null, null, false)" class="btn btn-secondary">Right</button>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 d-flex flex-wrap justify-content-around mt-4 mb-5">
                        <div class="card mb-4" style="width: 18rem;" v-for="i in items">
                            <img :src="i.pictureUrl" class="card-img-top" alt="...">
                            <div class="card-body">
                                <h5 class="card-title">{{i.name}}</h5>
                                <p class="card-text">{{i.description}}</p>
                                <p class="card-text">{{i.price}}</p>
                                <button class="btn btn-warning" @click="addCart(i)">Add to Cart</button>
                                <button class="btn btn-warning" @click="goInfo(i)">More Info</button>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">{{i.stars}} in 5</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import navbar from 'pages/defaultComponents/navbar.vue'
    import dropdown from 'pages/defaultComponents/dropdown.vue'
    export default {
        name: "mainPage",
        components: {
            navbar,
            dropdown,
        },
        data() {
            return {
                items: [],
                languages: [],
                categories: [],
                brands: [],
                name: "",
                cat: null,
                brand: {name: 'Выберите Брэнд'},
                isFiltered: false,
                priceTo: null,
                priceFrom: null,
            }
        },
        created() {
            this.loadItems();
            this.loadCategories();
            this.loadBrands();
            this.loadLang();
            localStorage.setItem("cart", JSON.stringify({item: []}));
        },
        methods: {
            update(i) {
                this.brand = i;
            },
            async filter(cat, brand, ismore) {
                if (cat !== null) this.cat = cat;
                if (brand !== null) this.brand = brand;

                this.isFiltered = true;
                let data = [];
                if (ismore === null)
                    data = await this.$http.post(`http://localhost:4000/item/api/Item/filter?name=${this.name}&priceFrom=${this.priceFrom}&priceTo=${this.priceTo}`, {category: this.cat, brand: this.brand});
                else
                    data = await this.$http.post(`http://localhost:4000/item/api/Item/filter?name=${this.name}&isMore=${ismore}&priceFrom=${this.priceFrom}&priceTo=${this.priceTo}`, {category: this.cat, brand: this.brand});
                this.items.splice(0,this.items.length);
                data.data.forEach(i => {
                    this.items.push(i);
                });
            },
            async loadItems() {
                let {data: data}= await this.$http.get("http://localhost:4000/item/api/Item/all");
                data.forEach(i => {
                    this.items.push(i);
                });
            },
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
            async loadLang() {
                let {data: data}= await this.$http.get("http://localhost:4000/item/api/language/all");
                data.forEach(i => {
                    this.languages.push(i);
                });
            },
            goInfo(item) {
                this.$router.push({ name: 'infoPage', params: { info: item }})
            },
            addCart(item) {
                let loc = JSON.parse(localStorage.getItem("cart"));
                item.amount = 1;
                loc.item.push(item);
                localStorage.setItem("cart", JSON.stringify(loc));
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

    .img-20 {
        width: 20px;
        height: 20px;
    }

    .img-400 {
        height: 400px;
    }
    .btn-group1 {
        margin: -7px 1px;
    }
</style>
