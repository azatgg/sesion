<template>
    <div>
        <div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">Modal title</h5>
                        <button type="button" class="btn-closeN" @click="close" aria-label="Close modal">x</button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label >Item Name</label>
                            <input v-model="task.name" type="text" class="form-control" placeholder="Enter name">
                        </div>
                        <div class="form-group">
                            <label >Item description</label>
                            <input v-model="task.description" type="text" class="form-control" placeholder="Enter description">
                        </div>
                        <div class="form-group">
                            <label >Item price</label>
                            <input v-model="task.price" type="number" class="form-control" placeholder="Enter price">
                        </div>
                        <div class="form-group">
                            <label >Item stars</label>
                            <input v-model="task.stars" type="number" class="form-control" placeholder="Enter stars">
                        </div>
                        <div class="form-group">
                            <label >Item pictureUrl</label>
                            <input v-model="task.pictureUrl" type="text" class="form-control" placeholder="Enter pictureUrl">
                        </div>
                        <div class="form-group">
                            <label >Item amount</label>
                            <input v-model="task.amount" type="number" class="form-control" placeholder="Enter amount">
                        </div>
                        <div class="form-group">
                            <label >Item category</label>
                            <dropdown v-model="task.category" placeholder="Enter category" :options="categories" @updateOption="updateCat" :selected="task.category? task.category: {name: 'select'}"/>
                        </div>
                        <div class="form-group">
                            <label >Item brand</label>
                            <dropdown v-model="task.brand" placeholder="Enter brand" :options="brands" @updateOption="updateBrand" :selected="task.brand? task.brand: {name: 'select'}"/>
                        </div>
                        <div class="form-group">
                            <label >Item languages</label>
                            <dropdown v-model="task.language" placeholder="Enter language" :options="languages" @updateOption="updateLang" :selected="task.language? task.language: {name: 'select'}"/>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal" @click="close">Close</button>
                            <b-button @click="save" variant="outline-primary" >Save</b-button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Admin modal</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        {{message}}
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">Ok</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import dropdown from 'pages/defaultComponents/dropdown.vue'
    export default {
        name: "saveForm",
        props: ["task", "brands", "languages", "categories"],
        components: {
            dropdown,
        },
        data() {
            return {
                message: '',
            }
        },
        methods: {
            updateCat(i) {
                this.task.category = i;
            },
            updateLang(i) {
                this.task.category = i;
            },
            updateBrand(i) {
                this.task.category = i;
            },
            close() {
                this.$emit('close');
            },
            async save() {
                console.log(this.task);
                let resp = await this.$http.post("http://localhost:8082/api/Item/save", this.task);
                if (resp.status === 200) {
                    this.message = 'Success';
                    this.$emit('updateTask', resp.data);
                    $('#staticBackdrop').modal('hide');
                    $('#exampleModal').modal('show');
                } else
                    this.message = 'Error'
            },
        }
    }
</script>

<style scoped>
    .modal-backdropEQ {
        position: fixed;
        z-index: 1;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background-color: rgba(0, 0, 0, 0.3);
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .modalN {
        background: #FFFFFF;
        box-shadow: 2px 2px 20px 1px;
        overflow-x: auto;
        display: flex;
        flex-direction: column;
    }
    .modal-headerN,
    .modal-footerN {
        padding: 15px;
        display: flex;
    }
    .modal-headerN {
        border-bottom: 1px solid #eeeeee;
        color: #4AAE9B;
        justify-content: space-between;
    }
    .modal-footerN {
        border-top: 1px solid #eeeeee;
        justify-content: flex-end;
    }
    .modal-bodyN {
        position: relative;
        padding: 20px 10px;
    }
    .btn-closeN {
        border: none;
        font-size: 20px;
        padding: 20px;
        cursor: pointer;
        font-weight: bold;
        color: #4AAE9B;
        background: transparent;
    }
    .btn-greenN {
        color: white;
        background: #4AAE9B;
        border: 1px solid #4AAE9B;
        border-radius: 2px;
    }

    .text {
        font-family: Roboto;
        font-style: normal;
        font-weight: normal;
        font-size: 9px;
        line-height: 11px;
    }

    label {
        border-color: black;
    }
</style>