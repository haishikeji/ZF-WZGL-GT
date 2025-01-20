<template>
    <el-dialog
            class="order-manage"
            :visible.sync="visible"
            :before-close="resetForm"
            width="800px"
    >
        <div slot="title" class="comm-toast-title-one">{{id==''?'新增':'编辑'}}物资</div>

        <el-card shadow="never">
            <el-form :model="info" :rules="rules" ref="ruleForm" label-width="120px"  size="mini" :show-message="false">
                <div class="flex-n fac">
                    <el-form-item label="物资编号" prop="goodCode">
                        <el-input maxlength="32" class="w-220" v-model="info.goodCode" placeholder="请输入物资编号"></el-input>
                    </el-form-item>
                    <el-form-item label="物资名称" prop="goodName">
                        <el-input maxlength="32" class="w-220" v-model="info.goodName" placeholder="请输入物资名称"></el-input>
                    </el-form-item>
                </div>
                <div class="flex-n fac" v-if="id==''">
                    <el-form-item label="计量单位" prop="measureUnit">
                        <el-input maxlength="32" class="w-220" v-model="info.measureUnit" placeholder="请输入规格型号"></el-input>
                    </el-form-item>
                    <el-form-item label="检测单位" prop="testUnit">
                        <el-input maxlength="32" class="w-220" v-model="info.testUnit" placeholder="请输入检测单位"></el-input>
                    </el-form-item>
                    <!--<el-form-item label="换算变量" prop="specsModelName">-->
                        <!--<el-input maxlength="32" class="w-220" v-model="info.specsModelName" placeholder="请输入规格型号"></el-input>-->
                    <!--</el-form-item>-->
                </div>
                <el-form-item label="规格型号" prop="specsModelName">
                    <el-input maxlength="32" class="w-220" v-model="info.specsModelName" placeholder="请输入规格型号"></el-input>
                </el-form-item>
                <el-form-item label="专业" prop="majorIds">
                    <el-select class="ele-select w-560" multiple   clearable v-model="info.majorIds" placeholder="请选择">
                        <el-option
                                v-for="item in majorList"
                                :key="item.id"
                                :label="item.majorName"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <div class="flex-n fac">
                    <el-form-item label="库存上限值" prop="stockUpper">
                        <el-input-number v-model="info.stockUpper" class="w-220" :min="info.stockLower" :step="1" step-strictly></el-input-number>
                    </el-form-item>
                    <el-form-item label="库存下限值" prop="stockLower">
                        <el-input-number v-model="info.stockLower" class="w-220" :max="info.stockUpper" :step="1" step-strictly></el-input-number>
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
        name: "material-manage",
        props: {
            visible: Boolean,
            id: String,
            goodType:String,
        },
        data() {
            return {
                list: [],
                  majorList: [],
                info: {
                    goodCode: '',
                    goodName: '',
                    specsModelName:'',
                    stockUpper:0,
                    stockLower:0,
                    remarks:'',
                    measureUnit:'',
                    testUnit:'',
                    majorId:'',
                    majorIds: []
                },
                rules: {
                    goodName: [{required: true, message: '', trigger: 'blur'}],
                    measureUnit: [{required: true, message: '', trigger: 'blur'}],
                    majorIds: [{required: true, message: '', trigger: 'blur'}]
                },
            }
        },
        watch: {
            visible(newval, oldval) {
                this.getMajorList();
                if (newval) {
                    if(this.id){
                        this.getData();
                    }
                }
            }
        },

        methods: {
            getData(){
                this.$axios.get('/good/findById/'+this.id).then(res=>{
                    console.log('111111')
                    console.log(res.data.majorId.split(','))
                    this.info=res.data;
                     this.$set(this.info,'majorIds',res.data.majorId.split(','))
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            getMajorList() {
            this.$axios.post('/major/findList', {
                "projectId":this.$root.getStorage('projectId'),
            }).then(res => {
                console.log(res.data.list)
                this.majorList = res.data.list;
            }).catch(err => {
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
                this.$axios.post('/good/save',{
                    goodCode: info.goodCode,
                    goodType:this.goodType,
                    goodName: info.goodName,
                    specsModelName:info.specsModelName,
                    stockUpper:info.stockUpper,
                    stockLower:info.stockLower,
                    measureUnit:info.measureUnit,
                    testUnit:info.testUnit,
                    projectId: this.$root.getStorage('projectId'),
                    majorIds:info.majorIds.join(','),
                    remarks:info.remarks
                }).then(res=>{
                    this.$message.success('新增物资成功');
                    this.resetForm();
                }).catch(err=>{
                    this.$message.error(err.msg)
                })
            },
            editSubmit(){
                var info=this.info;
                this.$axios.post('/good/update',{
                    "id":this.id,
                    goodCode: info.goodCode,
                    goodType:info.goodType,
                    goodName: info.goodName,
                    specsModelName:info.specsModelName,
                    stockUpper:info.stockUpper,
                    stockLower:info.stockLower,
                    measureUnit:info.measureUnit,
                    testUnit:info.testUnit,
                    majorIds:info.majorIds.join(','),
                    remarks:info.remarks
                }).then(res=>{
                    this.$message.success('编辑物资成功');
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