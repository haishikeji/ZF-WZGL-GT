<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="600px"
    >
        <div slot="title" class="comm-toast-title-one">领料单位详细资料</div>

        <el-card shadow="never">
            <div class="info-area" >
                <div class="text">领料单位编码</div>
                <div class="num">{{info.pickCompanyNo}}</div>
                <div class="text">领料单位名称</div>
                <div class="num">{{info.pickCompanyName}}</div>
                <div class="text">描述</div>
                <div class="num">{{info.remarks}}</div>
            </div>
        </el-card>
    </el-dialog>
</template>

<script>
    export default {
        name: "pick-unit-detail",
        props: {
            visible: Boolean,
            id:String
        },
        data() {
            return {
                info:{}
            }
        },
        watch:{
            visible(newval,oldval){
                if(newval){
                    this.getData();
                }
            }
        },
        methods: {
            getData(){
                this.$axios.get('/pickCompany/findById/'+this.id).then(res=>{
                    console.log(res.data);
                    this.info=res.data;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            resetForm() {
                this.$emit('update:visible', false);
            }
        }
    }
</script>

<style scoped lang="scss">
    .info-area {
        display: grid;
        width: 100%;
        grid-row-gap: 30px;
        grid-template-columns: 25% 75%;
        font-size: 14px;
        .text {
            text-align: right;
            margin-right: 20px;
        }
        .num {
            text-align: left;
            padding:0 10px;
            background: #f5f7fa;
            color: #c6c6c6;
            margin-right: 8%;
        }
    }
</style>