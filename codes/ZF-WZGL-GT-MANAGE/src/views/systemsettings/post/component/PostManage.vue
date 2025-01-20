<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="600px"
    >
        <div slot="title" class="comm-toast-title-one">{{id==''?'新增':'编辑'}}岗位</div>

        <el-card shadow="never">
            <el-form :model="info" :rules="rules" ref="ruleForm" label-width="80px"  size="mini" :show-message="false">

                    <el-form-item label="岗位编码" prop="postCode">
                        <el-input maxlength="32"  v-model="info.postCode" placeholder="请输入岗位编码"></el-input>
                    </el-form-item>
                <el-form-item label="岗位名称" prop="postName">
                    <el-input maxlength="32"  v-model="info.postName" placeholder="请输入岗位名称"></el-input>
                </el-form-item>
                <el-form-item label="岗位描述" prop="postDesc">
                <el-input
                type="textarea"
                maxlength="256"
                :rows="5"
                show-word-limit
                placeholder="请输入岗位描述"
                v-model="info.postDesc">
                </el-input>
                </el-form-item>
            </el-form>

        </el-card>
        <div slot="footer" style="margin-top: -20px">
            <el-button size="medium" @click="resetForm">取 消</el-button>
            <el-button size="medium" type="primary" class="ml-10" @click="submit('ruleForm')">保 存</el-button>

        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "post-manage",
        props: {
            visible: Boolean,
            id: String
        },
        data() {
            return {
                list: [],
                info: {
                    postCode: '',
                    postName: '',
                    postDesc:''
                },
                rules: {
                    postName: [{required: true, message: '', trigger: 'blur'}],
                    postCode: [{required: true, message: '', trigger: 'blur'}]
                },
            }
        },
        watch: {
            visible(newval, oldval) {
                if (newval) {
                    this.getData();
                }
            }
        },
        methods: {
            getData(){
                this.$axios.get('/post/findById/'+this.id).then(res=>{
                    console.log(res.data);
                    this.info=res.data;
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            submit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.id == '') {
                            this.addSubmit();
                        } else {
                            this.editSubmit();
                        }
                    } else {
                        this.$message.error('表单填写有误');
                        return false;
                    }
                });
            },
            addSubmit(){
                var info=this.info;
                this.$axios.post('/post/save',{
                    "postCode": info.postCode,
                    "postName": info.postName,
                    "postDesc": info.postDesc
                }).then(res=>{
                    this.$message.success('新增岗位成功')
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            editSubmit(){
                var info=this.info;
                this.$axios.post('/post/update',{
                    "id":this.id,
                    "postCode": info.postCode,
                    "postName": info.postName,
                    "postDesc": info.postDesc
                }).then(res=>{
                    this.$message.success('编辑岗位成功')
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            resetForm() {
                this.$emit('refreshList');
                this.$refs.ruleForm.resetFields();
                this.$emit('update:visible', false);
            }
        }
    }
</script>

<style scoped>

</style>