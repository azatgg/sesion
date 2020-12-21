<template>
    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 d-flex flex-column">
        <div class="d-flex justify-content-between m-3">
            <h1>All Items</h1>
            <button type="button" class="btn btn-success mr-3 rf" @click="showModal({name: ''})" data-toggle="modal" data-target="#staticBackdrop">save</button>
        </div>
        <ul class="list-group ml-5">
            <li class="list-group-item">
                <div class="d-inline-flex rf">id</div>
                <div class="d-inline-flex rf">name</div>
            </li>
            <li class="list-group-item" v-for="i in task">
                <div class="d-inline-flex rf">{{i.id}}</div>
                <div class="d-inline-flex rf">{{i.name}}</div>
                <button type="button" class="btn btn-secondary mr-3 rf" @click="showModal(i)" data-toggle="modal" data-target="#staticBackdrop">Edit</button>
                <b-button class="rf" variant="danger" @click="deleteNews(i)">X</b-button>
            </li>
        </ul>
        <category-form v-show="isModalVisible" @close="closeModal" :task="selectedTask" @updateTask="updateTask"/>
        <b-modal ref="my-modal1" title="Admin panel">
            <p>{{message}}</p>
        </b-modal>
    </div>
</template>

<script>
    import categoryForm from 'pages/adminPage/components/categoryForm.vue'
    export default {
        name: "categoryList",
        props: ["task"],
        components: {categoryForm},
        data() {
            return {
                selectedTask: {name: ''},
                isModalVisible: false,
                message: '',
            }
        },
        methods: {
            updateTask(i) {
                if (this.task.find(x => x.id === i.id) === undefined)
                    this.task.push(i);
            },
            showModal(item) {
                console.log(item);
                this.selectedTask = item;
                this.isModalVisible = true;
            },
            closeModal() {
                this.selectedTask = {name: '', description: '', deadlineDate: new Date(), completed: ''};
                this.isModalVisible = false;
            },
            deleteNews(i) {
                this.$http.post("http://localhost:4000/item/api/brand/delete", i).then(response => {
                    if (response['status'] === 200) {
                        this.message = 'Success';
                        this.$refs['my-modal1'].show();
                        this.$emit('deleteTask', response);
                        this.$emit('close');

                    } else
                        this.message = 'Error'
                })
            },
        }
    }
</script>

<style scoped>
    .rf {
        width: 8%;
        overflow: hidden;
    }
</style>