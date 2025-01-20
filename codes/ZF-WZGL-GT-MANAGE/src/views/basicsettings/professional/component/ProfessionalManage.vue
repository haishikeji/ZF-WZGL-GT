<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="600px"
    >
        <div slot="title" class="comm-toast-title-one">{{id==''?'新增':'编辑'}}专业</div>

        <el-card shadow="never">
            <el-form :model="info" :rules="rules" ref="ruleForm" label-width="80px"  size="mini" :show-message="false">

                    <el-form-item label="专业编码" prop="majorCode">
                        <el-input maxlength="32"  v-model.number="info.majorCode" placeholder="请输入专业编码"></el-input>
                    </el-form-item>
                <el-form-item label="专业名称" prop="majorName">
                    <el-input maxlength="32"  v-model="info.majorName" placeholder="请输入专业名称"></el-input>
                </el-form-item>
                <el-form-item label="描述" prop="remarks">
                <el-input
                type="textarea"
                maxlength="256"
                :rows="5"
                show-word-limit
                placeholder="请输入备注"
                v-model="info.remarks">
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
        name: "professional-manage",
        props: {
            visible: Boolean,
            id: String
        },
        data() {
            return {
                list: [],
                info: {
                    majorCode: '',
                    majorName: '',
                    remarks:''
                },
                rules: {
                    majorCode: [{required: true, message: '', trigger: 'blur'}],
                    majorName: [{required: true, message: '', trigger: 'blur'}]
                },
            }
        },
        watch: {
            visible(newval, oldval) {
                if (newval) {
                    if(this.id){
                        this.getData();
                    }
                }
            }
        },
        methods: {
            getData(){
                this.$axios.get('/major/findById/'+this.id).then(res=>{
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
                this.$axios.post('/major/save',{
                    "projectId":this.$root.getStorage('projectId'),
                    "remarks": info.remarks,
                    "majorCode": info.majorCode,
                    "majorName": info.majorName,
                }).then(res=>{
                    this.$message.success('新增专业成功')
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            editSubmit(){
                var info=this.info;
                this.$axios.post('/major/update',{
                    "id":this.id,
                    "remarks": info.remarks,
                    "majorCode": info.majorCode,
                    "majorName": info.majorName,
                }).then(res=>{
                    this.$message.success('编辑专业成功')
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