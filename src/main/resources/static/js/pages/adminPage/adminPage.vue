<template>
    <div>
        <div class="pos-f-t">
            <nav class="navbar navbar-dark bg-dark">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
            </nav>
            <div class="collapse" id="navbarToggleExternalContent">
                <div class="bg-dark p-4">
                    <div @click="selected = 'item'" class="w-100 text-muted">Item</div>
                    <div @click="selected = 'brand'" class="w-100 text-muted">Brand</div>
                    <div @click="selected = 'category'" class="w-100 text-muted">Category</div>
                </div>
            </div>
        </div>
        <div class="d-flex justify-content-center align-content-center flex-wrap">
            <news-list v-if="selected === 'item'" :task="task" :category="categories" :languages="languages" :brands="brands" @deleteTask="deleteTask"/>
            <brand-list v-if="selected === 'brand'" :task="brands" @deleteTask="deleteBrand"/>
            <category-list v-if="selected === 'category'" :task="categories" @deleteTask="deleteCat"/>
        </div>
    </div>
</template>

<script>
    import newsList from 'pages/adminPage/components/newsList.vue'
    import categoryList from 'pages/adminPage/components/categoryList.vue'
    import brandList from 'pages/adminPage/components/brandList.vue'
    export default {
        name: "adminPage",
        components: {
            newsList,
            brandList,
            categoryList
        },
        data() {
            return {
                task: [],
                selected: 'item',
                categories: [],
                brands: [],
                languages: [],
            }
        },
        methods: {
            deleteTask(i) {
                this.task = this.task.filter(x => x.id !== i.data.id)
            },
            deleteBrand(i) {
                this.brands = this.brands.filter(x => x.id !== i.data.id)
            },
            deleteCat(i) {
                this.categories = this.categories.filter(x => x.id !== i.data.id)
            },
            async loadNews() {
                let {data: data}= await this.$http.get("http://localhost:4000/item/api/Item/all");
                data.forEach(i => {
                    this.task.push(i);
                });
            },
            async loadLang() {
                let {data: data}= await this.$http.get("http://localhost:4000/item/api/language/all");
                data.forEach(i => {
                    this.languages.push(i);
                });
            },
            async loadBrands() {
                let {data: data}= await this.$http.get("http://localhost:4000/item/api/brand/all");
                data.forEach(i => {
                    this.brands.push(i);
                });
            },
            async loadCategories() {
                let {data: data}= await this.$http.get("http://localhost:4000/item/api/category/all");
                data.forEach(i => {
                    this.categories.push(i);
                });
            },
        },
        async created() {
            await this.loadNews();
            this.loadCategories();
            this.loadBrands();
            this.loadLang();
        }
    }
</script>

<style scoped>
    .rf {
        width: 8%;
        overflow: hidden;
    }
</style>