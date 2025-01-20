<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="600px"
    >
        <div slot="title" class="comm-toast-title-one">角色详细资料</div>

        <el-card shadow="never">
            <div class="info-area" >
                <div class="text">角色编码</div>
                <div class="num">{{info.roleCode}}</div>
                <div class="text">角色名称</div>
                <div class="num">{{info.roleName}}</div>
                <div class="text">角色描述</div>
                <div class="num">{{info.roleDesc}}</div>
                <div class="text">创建时间</div>
                <div class="num">{{info.createTime}}</div>
                <div class="text">创建人</div>
                <div class="num">{{info.createUserName}}</div>
            </div>
        </el-card>
    </el-dialog>
</template>

<script>
    export default {
        name: "role-detail",
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
                this.$axios.get('/role/findById/'+this.id).then(res=>{
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
        grid-template-columns: 20% 80%;
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