<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="600px"
    >
        <div slot="title" class="comm-toast-title-one">{{id==''?'新增':'编辑'}}角色</div>

        <el-card shadow="never">
            <el-form :model="info" :rules="rules" ref="ruleForm" label-width="80px"  size="mini" :show-message="false">

                    <el-form-item label="角色编码" prop="roleCode">
                        <el-input maxlength="32"  v-model="info.roleCode" placeholder="请输入角色编码"></el-input>
                    </el-form-item>
                <el-form-item label="角色名称" prop="roleName">
                    <el-input maxlength="32"  v-model="info.roleName" placeholder="请输入角色名称"></el-input>
                </el-form-item>
                <el-form-item label="角色描述" prop="roleDesc">
                <el-input
                type="textarea"
                maxlength="256"
                :rows="5"
                show-word-limit
                placeholder="请输入角色描述"
                v-model="info.roleDesc">
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
        name: "role-manage",
        props: {
            visible: Boolean,
            id: String
        },
        data() {
            return {
                list: [],
                info: {
                    roleCode: '',
                    roleName: '',
                    roleDesc:''
                },
                rules: {
                    roleCode: [{required: true, message: '', trigger: 'blur'}],
                    roleName: [{required: true, message: '', trigger: 'blur'}]
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
                this.$axios.get('/role/findById/'+this.id).then(res=>{
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
                this.$axios.post('/role/save',{
                    roleCode: info.roleCode,
                    roleName:  info.roleName,
                    roleDesc: info.roleDesc
                }).then(res=>{
                    this.$message.success('新增角色成功')
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            editSubmit(){
                var info=this.info;
                this.$axios.post('/role/update',{
                    "id":this.id,
                    roleCode: info.roleCode,
                    roleName:  info.roleName,
                    roleDesc: info.roleDesc
                }).then(res=>{
                    this.$message.success('编辑角色成功')
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