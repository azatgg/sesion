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
                <div class="col-7">
                    <div class="w-100 d-flex justify-content-start align-content-center mb-4 ml-2 mr-auto mt-4" style="height: 70px;background-color: whitesmoke;">
                        <h2 class="d-flex">Basket</h2>
                    </div>
                    <div class=" w-100 d-flex justify-content-center align-content-center">
                            <div class="font-weight-bold w-100 d-flex flex-row align-content-between justify-content-between"><div>Item</div><div>price</div><div>Amount</div><div style="opacity: 0;"><button class="btn btn-success">+</button><button class="btn btn-danger">-</button></div></div>
                    </div>
                    <div class="w-100 d-flex justify-content-center align-content-center flex-column">
                        <div v-for="(i, count) in cart.item" :class="count % 2 === 0 ? 'font-weight-bold w-100 d-flex flex-row align-content-between justify-content-between bg-light': 'font-weight-bold w-100 d-flex flex-row align-content-between justify-content-between'"><div>{{i.name}}</div><div>{{i.price}}</div><div>{{i.amount}}</div><div><button class="btn btn-success" @click="add(i)">+</button><button @click="minus(i)" class="btn btn-danger">-</button></div></div>
                        <div class="w-100 d-flex justify-content-end align-content-center mt-4">
                            <div class="w-100 d-flex justify-content-end align-content-center">
                                <div class="w-100 d-flex justify-content-between align-content-center">
                                    <div>Total: {{sum}}</div>
                                    <div>
                                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">Check In</button>

                                        <!-- Modal -->
                                        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                            <div class="modal-dialog" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="form-group">
                                                            <label >Item Address</label>
                                                            <input v-model="cart.addressName" type="text" class="form-control" placeholder="Enter Address">
                                                        </div>
                                                        <div class="form-group">
                                                            <label >Enter cart number</label>
                                                            <input v-model="cart.cardNumber" type="text" class="form-control" placeholder="Enter cart number">
                                                        </div>
                                                        <div class="form-group">
                                                            <label >Enter cart number</label>
                                                            <input v-model="cart.cardNumber" type="text" class="form-control" placeholder="Enter cart number">
                                                        </div>
                                                        <div class="form-group">
                                                            <label >Enter cart number</label>
                                                            <input v-model="cart.cardDate" type="text" class="form-control" placeholder="Enter cart number">
                                                        </div>
                                                        <div class="form-group">
                                                            <label >Enter cart number</label>
                                                            <input v-model="cart.cvc" type="password" class="form-control" placeholder="Enter cart number">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                        <button @click="saveCheck" type="button" class="btn btn-primary">Save changes</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <button @click="clear" class="btn btn-danger">Clear</button>
                                    </div>
                                </div>
                            </div>
                        </div>
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
        name: "cartPage",
        components: {
            Footer,
            navbar,
            dropdown
        },
        watch: {
            'cart.item': function (newvalue, oldvalue) {
                let sum = 0;
                // newvalue.forEach(i => {
                //     if (i.amount <=0) this.cart = this.cart.item.filter(x => x.id !== i.id);
                //     if (i.amount > 0) sum += (i.price * i.amount);
                // });
                // this.sum = sum;
            }
        },
        data() {
            return {
                cart: JSON.parse(localStorage.getItem("cart")),
                categories: [],
                brands: [],
                sum: 0,
            }
        },
        created() {
            this.loadCategories();
            this.loadBrands();
            let sum = 0;
            this.cart.item.forEach(i => {
                if (i.amount <=0) this.cart = this.cart.item.filter(x => x.id !== i.id);
                if (i.amount > 0) sum += (i.price * i.amount);
            });
            this.sum = sum;
        },
        methods: {
            add(i) {
                i.amount += 1;
                let sum = 0;
                this.cart.item.forEach(i => {
                    if (i.amount <=0) this.cart = this.cart.item.filter(x => x.id !== i.id);
                    if (i.amount > 0) sum += (i.price * i.amount);
                });
                this.sum = sum;
            },
            minus(i) {
                i.amount -= 1;
                let sum = 0;
                this.cart.item.forEach(i => {
                    console.log(this.cart.item.filter(x => x.id !== i.id))
                    if (i.amount <= 0) this.cart.item = this.cart.item.filter(x => x.id !== i.id);
                    if (i.amount > 0) sum += (i.price * i.amount);
                });
                this.sum = sum;
            },
            clear() {
                this.cart = {item: []};
                localStorage.setItem("cart", JSON.stringify(this.cart));
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
            async saveCheck() {
                let resp = await this.$http.post("http://localhost:4000/item/api/cart/save", this.cart);
                if (resp.status === 200) {
                    $('#exampleModal').modal('hide');
                }
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