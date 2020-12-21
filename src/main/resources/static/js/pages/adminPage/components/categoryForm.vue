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
        name: "categoryForm",
        props: ["task"],
        components: {
            dropdown,
        },
        data() {
            return {
                message: '',
            }
        },
        methods: {
            close() {
                this.$emit('close');
            },
            async save() {
                console.log(this.task);
                let resp = await this.$http.post("http://localhost:4000/item/api/brand/save", this.task);
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

</style>