<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="800px"
    >
        <div slot="title" class="comm-toast-title-one">用户详细资料</div>

        <el-card shadow="never">
            <div class="info-area" >
                <div class="text">用户名</div>
                <div class="num">{{info.loginName}}</div>
                <div class="text">手机号</div>
                <div class="num">{{info.phone}}</div>
                <div class="text">真实姓名</div>
                <div class="num">{{info.userName}}</div>
                <div class="text">角色名称</div>
                <div class="num">{{info.roleName}}</div>
                <div class="text">岗位</div>
                <div class="num" >{{info.postName}}</div>
                <div class="text"></div>
                <div class="text"></div>
                <div class="text ">所属项目</div>
                <div class="project num">{{info.projectName}}</div>
            </div>
        </el-card>
    </el-dialog>
</template>

<script>
    export default {
        name: "user-detail",
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
                this.$axios.get('/user/findByUserId/'+this.id).then(res=>{
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
        grid-template-columns: repeat(2, 12% 38%);
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
        }
        .project{
            grid-column-start: 2;
            grid-column-end:5;
        }
    }
</style>