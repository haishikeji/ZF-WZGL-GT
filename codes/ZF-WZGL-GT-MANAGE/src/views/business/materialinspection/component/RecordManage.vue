<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="400px"
            append-to-body
    >
        <div slot="title" class="comm-toast-title-one">{{id==''?'新增':'编辑'}}检测记录</div>

        <el-card shadow="never">
            <el-form :model="info" :rules="rules" ref="ruleForm" label-width="110px"  size="mini" :show-message="false">

                    <el-form-item label="物资检测数量" prop="checkNum">
                        <el-input-number v-model="info.checkNum" :step="1" step-strictly></el-input-number>                    </el-form-item>
                <el-form-item label="检测日期" prop="checkTime">
                    <el-date-picker
                            v-model="info.checkTime"
                            value-format="yyyy-MM-dd"
                            type="date"
                            class="w-210"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <!--<el-form-item label="检测人名称" prop="createUserName">-->
                    <!--<el-input maxlength="32"  v-model="info.createUserName" placeholder="请输入检测人名称"></el-input>-->

                <!--</el-form-item>-->
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
        name: "record-manage",
        props: {
            visible: Boolean,
            id:String,
            state:String
        },
        data() {
            return {
                info: {
                    checkNum: '',
                    checkTime: '',
                },
                rules: {
                    checkNum: [{required: true, message: '', trigger: 'blur'}],
                    checkTime: [{required: true, message: '', trigger: 'blur'}]
                },
            }
        },
        watch: {
            visible(newval, oldval) {
                if (newval) {
                    if(this.state=='1'){
                        this.getData();
                    }
                }
            }
        },
        methods: {
            getData(){
                this.$axios.get('/goodCheck/findLogById/'+this.id).then(res=>{
                    console.log(res.data);
                    this.info=res.data.list[0];
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            submit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.state == '0') {
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
                this.$axios.post('/goodCheck/save',{
                    "projectId":this.$root.getStorage('projectId'),
                    "goodId": this.id,
                    "checkNum": info.checkNum,
                    "checkTime": info.checkTime
                }).then(res=>{
                    this.$message.success('新增检测记录成功')
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            editSubmit(){
                var info=this.info;
                this.$axios.post('/goodCheck/update',{
                    "id":this.id,
                    "checkNum": info.checkNum,
                    "checkTime": info.checkTime
                }).then(res=>{
                    this.$message.success('编辑检测记录成功')
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