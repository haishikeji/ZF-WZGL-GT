<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="440px"
    >
        <div slot="title" class="comm-toast-title-one">{{type==0?'新增':'编辑'}}仓库网格</div>

        <el-card shadow="never">
            <el-form :model="info" :rules="rules" ref="ruleForm" label-width="60px" size="mini" :show-message="false">
                <el-form-item label="仓库">
                    <el-input disabled v-model="warehouse"></el-input>
                </el-form-item>
                <el-form-item label="行号" prop="girdRowsCount">
                    <el-input maxlength="32" type="number" v-model="info.girdRowsCount" placeholder="请输入行号"></el-input>
                </el-form-item>
                <el-form-item label="列号" prop="girdColumnsCount">
                    <el-select v-model="info.girdColumnsCount" style="width: 100%" placeholder="请选择">
                        <el-option
                                v-for="item in list"
                                :key="item"
                                :label="item"
                                :value="item">
                        </el-option>
                    </el-select>
                    <!--<el-input maxlength="32" type="number" v-model="info.remark" placeholder="请输入列号"></el-input>-->
                </el-form-item>
                <div class="tips"><span>*</span>库位创建后，默认为启用状态，可以进入图标模式中修改</div>
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
        name: "grid-manage",
        props: {
            visible: Boolean,
            id: String,
            type: {
                type: String | Number,
                default: 0
            },
            warehouse: String
        },
        data() {
            return {
                list: ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'],

                info: {
                    girdColumnsCount: '',
                    girdRowsCount: '',
                },
                rules: {
                    girdRowsCount: [{required: true, message: '', trigger: 'blur'}],
                    girdColumnsCount: [{required: true, message: '', trigger: 'blur'}]
                },
            }
        },
        watch: {
            visible(newval, oldval) {
                if (newval) {

                }
            }
        },
        methods: {
            submit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.type == 0) {
                            this.addSubmit();
                        } else {
                            // this.editSubmit();
                        }
                    } else {
                        this.$message.error('表单填写有误');
                        return false;
                    }
                });
            },
            addSubmit(){
                var info=this.info;
                this.$axios.post('/storeGrid/initStoreGrid',{
                    "storeId":this.id,
                    "girdColumnsCount": info.girdColumnsCount,
                    "girdRowsCount": info.girdRowsCount
                }).then(res=>{
                    this.$message.success('新增仓库网格成功')
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            editSubmit(){
                var info=this.info;
                this.$axios.post('storeGrid/moreStoreGrid',{
                    "storeId":this.id,
                    "girdColumnsCount": info.girdColumnsCount,
                    "girdRowsCount": info.girdRowsCount
                }).then(res=>{
                    this.$message.success('编辑仓库网格成功')
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

<style scoped lang="scss">
    .tips {
        color: #999;
        font-size: 12px;
        margin-left: 30px;
        span {
            color: #eb1c1c;
        }
    }
</style>