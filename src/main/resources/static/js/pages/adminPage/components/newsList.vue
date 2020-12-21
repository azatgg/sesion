<template>
    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 d-flex flex-column">
        <div class="d-flex justify-content-between m-3">
            <h1>All Items</h1>
            <button type="button" class="btn btn-success mr-3 rf" @click="showModal({name: '', description: '', price: '', stars: '', amount: '', pictureUrl: '', brand: {name: 'select'}, category: {name: 'select'}, language: {name: 'select'}})" data-toggle="modal" data-target="#staticBackdrop">save</button>
        </div>
        <ul class="list-group ml-5">
            <li class="list-group-item">
                <div class="d-inline-flex rf">id</div>
                <div class="d-inline-flex rf">description</div>
                <div class="d-inline-flex rf">name</div>
                <div class="d-inline-flex rf">price</div>
                <div class="d-inline-flex rf">stars</div>
                <div class="d-inline-flex rf">amount</div>
            </li>
            <li class="list-group-item" v-for="i in task">
                <div class="d-inline-flex rf">{{i.id}}</div>
                <div class="d-inline-flex rf">{{i.description}}</div>
                <div class="d-inline-flex rf">{{i.name}}</div>
                <div class="d-inline-flex rf">{{i.price}}</div>
                <div class="d-inline-flex rf">{{i.stars}}</div>
                <button type="button" class="btn btn-secondary mr-3 rf" @click="showModal(i)" data-toggle="modal" data-target="#staticBackdrop">Edit</button>
                <b-button class="rf" variant="danger" @click="deleteNews(i)">X</b-button>
            </li>
        </ul>
        <save-form v-show="isModalVisible" @close="closeModal" :task="selectedTask" @updateTask="updateTask" :categories="category" :languages="languages" :brands="brands"/>
        <b-modal ref="my-modal1" title="Admin panel">
            <p>{{message}}</p>
        </b-modal>
    </div>
</template>

<script>
    import saveForm from 'pages/adminPage/components/saveForm.vue'
    export default {
        name: "newsList",
        props: ["task", "category", "languages", "brands"],
        components: {
            saveForm
        },
        data() {
            return {
                selectedTask: {name: '', description: '', price: '', stars: '', amount: '', pictureUrl: '', brand: {name: 'select'}, category: {name: 'select'}, language: {name: 'select'}},
                isModalVisible: false,
                message: '',
            }
        },
        methods: {
            updateTask(i) {
                if (this.task.find(x => x.id === i.id) === undefined)
                    this.task.push(i);
            },
            getDateTemplate (DateTime, Teamplate) {
                const regex = /(\w+)(\W+)?/g;
                const date = new Date(Date.parse(DateTime));
                let match;
                let result = '';

                while((match = regex.exec(Teamplate)) !== null){
                    if(match.index === regex.lastIndex) regex.lastIndex++;
                    let tmp = (match[2] || '');

                    switch(match[1]){
                        case 'd': result += date.getDate() + tmp; break;
                        case 'm': result += (date.getMonth() + 1) + tmp; break;
                        case 'y': result += date.getFullYear().toString().substr(3,2) + tmp; break;
                        case 'D': result += date.getDate().toString().padStart(2,'0') + tmp; break;
                        case 'M': result += (date.getMonth() + 1).toString().padStart(2,'0') + tmp; break;
                        case 'Mm': result += (date.toLocaleString('ru', {month:'long'})) + tmp; break;
                        case 'MM':
                            var t = (date.toLocaleString('ru', {month:'long'}));
                            result += (t[0].toUpperCase() + t.slice(1)) + tmp;
                            break;
                        case 'Y': result += date.getFullYear() + tmp; break;
                        case 'h': result += date.getHours() + tmp; break;
                        case 'i': result += date.getMinutes() + tmp; break;
                        case 's': result += date.getSeconds() + tmp; break;
                        case 'v': case 'vv': result += date.getMilliseconds() + tmp; break;
                        case 'H': result += date.getHours().toString().padStart(2,'0') + tmp; break;
                        case 'I': result += date.getMinutes().toString().padStart(2,'0') + tmp; break;
                        case 'S': result += date.getSeconds().toString().padStart(2,'0') + tmp; break;
                    }
                }

                return result;
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
                this.$http.post("http://localhost:4000/item/api/Item/delete", i).then(response => {
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