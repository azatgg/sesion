<template>
    <div>
        <navbar />
        <div  class="d-flex justify-content-start align-content-center" style="height: 500px;">
            <div class="d-flex justify-content-start align-content-center flex-wrap mb-5" style="width: 80%;">
                <div class=" d-inline-flex flex-row">
                    <img :src="info.pictureUrl" alt="Card image" class="p-2 ml-4 w-50">
                    <div class="ml-4 d-inline-flex flex-column justify-content-start align-content-center mt-3" style="color: black;">
                        <div style="font-family: Roboto; font-style: normal; font-weight: normal; font-size: 36px; line-height: 42px; letter-spacing: 0.1em; margin-bottom: 30px;">{{info.name}}</div>
                        <label>Описание: </label><div style="font-family: Roboto; font-style: normal; font-weight: normal; font-size: 24px; line-height: 28px; letter-spacing: 0.05em; color: #000000;">{{info.description}}</div>
                        <label>Цена: </label><div style="font-family: Roboto; font-style: normal; font-weight: normal; font-size: 24px; line-height: 28px; letter-spacing: 0.05em; color: #000000;">{{info.price}}</div>
                        <label>Оценка: </label><div style="font-family: Roboto; font-style: normal; font-weight: normal; font-size: 24px; line-height: 28px; letter-spacing: 0.05em; color: #000000;">{{info.stars}}</div>
                        <button @click="addCart(info)" class="btn btn-warning">add to cart</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="d-flex justify-content-center align-content-center">
            <div class="d-flex justify-content-center align-content-center flex-row">
                <img src="https://i.picsum.photos/id/741/200/300.jpg?hmac=xaQ9kS4D9YUXp1ih8_9I1Bo0GlrWrUW2UxRv52xf8dU" style="width: 100px; height: 150px;">
                <div class="d-flex justify-content-center align-content-center flex-column">
                    <h4>test</h4>
                    <p>test</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import navbar from 'pages/defaultComponents/navbar.vue'
    export default {
        name: "infoPage",
        props: ["info"],
        components: {
            navbar,
        },
        created() {
            if (this.info === undefined) {
                this.$router.push({ name: 'main'})
            }
        },
        methods: {
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

</style>