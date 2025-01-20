<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="800px"
    >
        <div slot="title" class="comm-toast-title-one">{{id==''?'新增':'编辑'}}供应商</div>

        <el-card shadow="never">
            <el-form :model="info" :rules="rules" ref="ruleForm" label-width="120px"  size="mini" :show-message="false">
                <div class="flex-n fac">
                    <el-form-item label="供应商编号" prop="supplierNo">
                        <el-input maxlength="32" class="w-220" v-model="info.supplierNo" placeholder="请输入供应商编号"></el-input>
                    </el-form-item>
                    <el-form-item label="供应商名称" prop="supplierName">
                        <el-input maxlength="32" class="w-220" v-model="info.supplierName" placeholder="请输入供应商名称"></el-input>
                    </el-form-item>
                </div>

                <div class="flex-n fac">
                    <el-form-item label="状态" prop="status">
                        <el-radio-group class="w-220" v-model="info.status">
                            <el-radio label="0">冻结</el-radio>
                            <el-radio label="1">启用</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="电话" prop="mobile">
                        <el-input maxlength="11"  class="w-220" type="number" v-model="info.mobile" placeholder="请输入电话"></el-input>
                    </el-form-item>
                </div>

                <div class="flex-n fac">
                    <el-form-item label="传真" prop="fax">
                        <el-input maxlength="32" class="w-220" v-model="info.fax" placeholder="请输入传真"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="mail">
                        <el-input maxlength="32" class="w-220" v-model="info.mail" placeholder="请输入邮箱"></el-input>
                    </el-form-item>
                </div>
                <div class="flex-n fac">
                <el-form-item label="联系人" prop="contacts">
                    <el-input maxlength="32" class="w-220" v-model="info.contacts" placeholder="请输入联系人"></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="address">
                    <el-input maxlength="32" class="w-220" v-model="info.address" placeholder="请输入地址"></el-input>
                </el-form-item>
                </div>

                <el-form-item label="描述" prop="remarks">
                    <el-input
                            type="textarea"
                            maxlength="256"
                            :rows="5"
                            show-word-limit
                            placeholder="请输入描述"
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
        name: "supplier-manage",
        props: {
            visible: Boolean,
            id: String
        },
        data() {
            return {
                list: [],
                info:  {
                    supplierNo:'',
                    supplierName:'',
                    status:'1',
                    mobile:'',
                    fax:'',
                    mail:'',
                    contacts:'',
                    address:'',
                    remarks:''
                },
                rules: {
                    supplierNo: [{required: true, message: '', trigger: 'blur'}],
                    supplierName: [{required: true, message: '', trigger: 'blur'}],
                    status: [{required: true, message: '', trigger: 'blur'}]

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
                this.$axios.get('/supplier/findById/'+this.id).then(res=>{
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
                this.$axios.post('/supplier/save',{
                    // "belongProjectId":this.$root.getStorage('projectId'),
                    supplierNo:info.supplierNo,
                    supplierName:info.supplierName,
                    status:info.status,
                    mobile:info.mobile,
                    fax:info.fax,
                    mail:info.mail,
                    contacts:info.contacts,
                    address:info.address,
                    remarks:info.remarks
                }).then(res=>{
                    this.$message.success('新增供应商成功')
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            editSubmit(){
                var info=this.info;
                this.$axios.post('/supplier/update',{
                    "id":this.id,
                    supplierNo:info.supplierNo,
                    supplierName:info.supplierName,
                    status:info.status,
                    mobile:info.mobile,
                    fax:info.fax,
                    mail:info.mail,
                    contacts:info.contacts,
                    address:info.address,
                    remarks:info.remarks
                }).then(res=>{
                    this.$message.success('编辑供应商成功')
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